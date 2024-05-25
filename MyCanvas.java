import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JViewport;

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, MouseWheelListener{
	private Graphics2D g2d;
	private BufferedImage cImage;
	private String currentMode;
	private int x, y, xx, yy;	//xx, yy:n-1 x,y:n
	private int[] clickedX, clickedY;
	private int width, height;
	private int penSize;
	private int penMode;
	private Color initialColor;
	private Color currentColor;
	private int dashCount = 0;
	private int dashInterval = 5;
	private int doubleLinePadding = 5;
	private int padding = penSize + doubleLinePadding;
	private int judgeClickEvent = 0;
	private String mouseEvent;
	private String fileName;
	private StringBuilder inputText;
	private double scale;
	
	private JViewport view;

	public MyCanvas(int width, int height){
		currentMode = "none";
		mouseEvent = "none";
		x = -1;
		y = -1;
		xx = -1;
		yy = -1;
		clickedX = new int[3];
		clickedY = new int[3];
		penSize = 4;
		penMode = PenTypePanel.LINE;
		fileName = "image";
		scale = 1.0;


		this.width = width;
		this.height = height;
		initialColor = Color.WHITE;
		currentColor = Color.BLACK;

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setBackground(Color.WHITE);


		inputText = new StringBuilder();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				inputText.append(c);

				g2d.setColor(currentColor);
				g2d.setFont(new Font("Arial", Font.PLAIN, 16));
				g2d.drawString(inputText.toString(), x, y);

				repaint();
			}
		});
		setFocusable(true);
		requestFocus();
	}

	//描画時のちらつきをなくす処理：updateメソッドはバックバッファに描画
	@Override
	public void update(Graphics g){
		paint(g);
	}

	//paintでフロントバッファに描画
	@Override
	public void paint(Graphics g){
		if(currentMode.equals("draw")){
			g2d.setColor(currentColor);
			paintProcess();
		}else if(currentMode.equals("iraser")){
			g2d.setColor(initialColor);
			paintProcess();
		}else if(currentMode.equals("rainbow")){
			g2d.setColor(createRainbowColor());
			paintProcess();
		}else if (currentMode.equals("fill") && mouseEvent.equals("Pressed")) {
			initialColor = currentColor;
			resizeCanvas();
		}

		g.drawImage(cImage,0,0,null);

		
	}

	private Color createRainbowColor(){
		float hue = (float) Math.random();
		float saturation = 1.0f; 
		float brightness = 1.0f; 

		return Color.getHSBColor(hue, saturation, brightness);
	}

	private void paintProcess(){
		BasicStroke bs = new BasicStroke(penSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(bs);

		if(penMode == PenTypePanel.LINE || currentMode.equals("iraser") || currentMode.equals("rainbow")){
			if (x >= 0 && y >= 0 && xx >= 0 && yy >= 0) {
				g2d.drawLine(xx, yy, x, y);
			}
		}
		if(penMode == PenTypePanel.DASH){
			if (dashCount >= 0 && dashCount++ <= dashInterval && x >= 0 && y >= 0 && xx >= 0 && yy >= 0) {
				g2d.drawLine(xx, yy, x, y);
				if (dashCount == dashInterval){
					dashCount = -dashInterval;					
				}
			} else {
				dashCount++;
			}
		}
		if(penMode == PenTypePanel.DOUBLE_LINE){
			if(x >= 0 && y >= 0 && xx >= 0 && yy >= 0){
				g2d.drawLine(xx, yy, x, y);
				g2d.drawLine(xx + padding, yy+padding, x+padding, y+padding);
			}
		}
		if(penMode == PenTypePanel.STRAIGHT){
			if (clickedX[0] >= 0 && clickedY[0] >= 0 && clickedX[1] >= 0 && clickedY[1] >= 0 && mouseEvent.equals("Clicked")) {
				g2d.drawLine(clickedX[0], clickedY[0], clickedX[1], clickedY[1]);
				judgeClickEvent = 0;
			}
		}
		if(penMode == PenTypePanel.TRIANGLE){
			if (clickedX[0] >= 0 && clickedY[0] >= 0 && clickedX[1] >= 0 && clickedY[1] >= 0 && mouseEvent.equals("Clicked")) {
				g2d.drawLine(clickedX[0], clickedY[0], clickedX[1], clickedY[1]);
				g2d.drawLine(clickedX[1], clickedY[1], clickedX[2], clickedY[2]);
				g2d.drawLine(clickedX[2], clickedY[2], clickedX[0], clickedY[0]);
				judgeClickEvent = 0;
			}
		}

	}

	@Override public void mouseDragged(MouseEvent e) {
		xx = x;
		yy = y;

		Point point = e.getPoint();
		x = point.x;
		y = point.y;

		// if(currentMode.equals("pan")){
		// 	panProcess(point);
		// }else{
		// 	repaint();
		// }
		mouseEvent = "Dragged";

		repaint();
	}
	
	@Override public void mousePressed(MouseEvent e) {
		Point point = e.getPoint();
		x = point.x;
		y = point.y;

		mouseEvent = "Pressed";

		repaint();
	}

	@Override public void mouseReleased(MouseEvent e) {
		xx = -1;
		yy = -1;
	}

	public void mouseClicked(MouseEvent e) {
		mouseEvent = "Clicked";
		if(penMode == PenTypePanel.STRAIGHT){
			clickedX[judgeClickEvent] = e.getX();
			clickedY[judgeClickEvent] = e.getY();
			if(judgeClickEvent == 1){
				repaint();
			}
			judgeClickEvent++;
		}else if(penMode == PenTypePanel.TRIANGLE){
			clickedX[judgeClickEvent] = e.getX();
			clickedY[judgeClickEvent] = e.getY();
			if(judgeClickEvent == 2){
				repaint();
			}
			judgeClickEvent++;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e){
		int notches = e.getWheelRotation();
		double newScale = scale + notches * 0.025;
		if(newScale > 0.5 && newScale <= 4){
			scale = newScale;
			cImage = scaleImage(cImage, newScale, newScale);
			this.setSize((int)(width*scale), (int)(height*scale));
			repaint();
		}
	}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}

	public void setSizeProperty(int width, int height){
		this.width = width;
		this.height = height;
	}
	public void setInitialColor(Color initialColor){ this.initialColor = initialColor; }
	public void setCurrentColor(Color currentColor) { this.currentColor = currentColor; }
	public void setViewport(JViewport view){ this.view = view; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public String getCurrentMode() { return this.currentMode; }
	public Color getCurrentColor() { return this.currentColor; }
	public void setCurrentMode(String mode) { this.currentMode = mode; }
	public void setPenSize(int penSize) { this.penSize = penSize; }
	public void setPenMode(int penMode) { this.penMode = penMode; System.out.println(penMode);}
	public BufferedImage getBufferedImage() { return this.cImage; }
	public void setBufferedImage(BufferedImage cImage) { 
		this.cImage = cImage;
		currentMode = "load";
		repaint();
	}
	public void setFileName(String fileName) { this.fileName = fileName; }
	public String getFileName() { return this.fileName; }
	public void resizeCanvas(){
		scale=1.0;
		//新規作成でサイズが前と同じときに変更されないから一回1,1に設定
		this.setSize(1,1);
		this.setSize(width, height);
		cImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) cImage.getGraphics();
		g2d.setColor(initialColor);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(currentColor);
	}
	// public void panProcess(Point point){
	// 		Point pp = new Point();
	// 		Point viewPoint = view.getViewPosition();

	// 		viewPoint.translate(pp.x - point.x, pp.y - point.y);
	// 		pp.setLocation(viewPoint);

	// }

	public BufferedImage scaleImage(BufferedImage originalImage, double scaleX, double scaleY) {
		int scaledWidth = (int) (originalImage.getWidth() * scaleX);
		int scaledHeight = (int) (originalImage.getHeight() * scaleY);

		if(scaledWidth >= 1 && scaledHeight >= 1){
			BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());
			g2d = (Graphics2D)scaledImage.getGraphics();

			AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
			g2d.drawRenderedImage(originalImage, at);

			g2d.dispose();
			return scaledImage;
		}
		return originalImage;
	}
}