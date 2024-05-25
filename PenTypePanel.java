import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class PenTypePanel extends JPanel {
	private final int WIDTH;
	private final int HEIGHT = 40;
	private final int PADDING = 50;
	public static final int LINE = 0;
	public static final int DASH = 1;
	public static final int DOUBLE_LINE = 2;
	public static final int STRAIGHT = 3;
	public static final int TRIANGLE = 4;
	final Color textColor = new Color(174, 177, 183);
	private int mode;

	public PenTypePanel(int width, int mode) {
		this.WIDTH = width;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.mode = mode;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2d.setColor(textColor);
		g2d.setStroke(bs);

		// sin波を描画
		double dx = (double) (WIDTH - 2 * PADDING) / (2 * Math.PI); // x軸の1単位あたりの幅
		double prevX = 0;
		double prevY1 = Math.sin(0) * (HEIGHT - 2 * PADDING) / 2 * 0.5 + HEIGHT / 2; // 最初のsin(0)のy座標
		double prevY2 = Math.sin(0) * (HEIGHT - 2 * PADDING) / 2 * 0.5 + HEIGHT / 2; // 最初のsin(0)のy座標


		switch (mode) {
			case LINE:
				for (double x = 0; x <= 2 * Math.PI; x += 0.01) {
					double y1 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2; // sin波のy座標
					double y2 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2 - 20; // 20ピクセル上にずらした線

					g2d.drawLine((int) (prevX * dx + PADDING), (int) prevY1, (int) (x * dx + PADDING), (int) y1);

					prevX = x;
					prevY1 = y1;
					prevY2 = y2;
				}
				break;

			case DASH:
				int tmp = 1;
				for (double x = 0; x <= 2 * Math.PI; x += 0.01) {
					double y1 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2; // sin波のy座標
					double y2 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2 - 20; // 20ピクセル上にずらした線

					if(tmp >= 0 && tmp++ <= 50){
						g2d.drawLine((int) (prevX * dx + PADDING), (int) prevY1, (int) (x * dx + PADDING), (int) y1);						
						if(tmp == 50) tmp = -50;
					}else{
						tmp++;
					}

					prevX = x;
					prevY1 = y1;
					prevY2 = y2;
				}
				break;

			case DOUBLE_LINE:
				for (double x = 0; x <= 2 * Math.PI; x += 0.01) {
					double y1 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2; // sin波のy座標
					double y2 = Math.sin(x) * (HEIGHT - 2 * PADDING) / 2 * 0.35 + HEIGHT / 2 - 5; // 20ピクセル上にずらした線

					g2d.drawLine((int) (prevX * dx + PADDING), (int) prevY1, (int) (x * dx + PADDING), (int) y1);
					g2d.drawLine((int) (prevX * dx + PADDING), (int) prevY2, (int) (x * dx + PADDING), (int) y2);

					prevX = x;
					prevY1 = y1;
					prevY2 = y2;
				}
				break;

			case STRAIGHT:
				g2d.drawLine((int)prevX+50, (int)prevY1, WIDTH-50, (int)prevY1);
				break;

			case TRIANGLE:
				int startX = (int) prevX + 100;
				int endX = (int) WIDTH - 100;
				int midX = (startX + endX) / 2;
				int midY = (int) prevY1 - 10;
				int buttomY = (int) prevY1 + 10;

				g2d.drawLine(startX, buttomY, endX, buttomY);
				g2d.drawLine(startX, buttomY, midX, midY);
				g2d.drawLine(midX, midY, endX, buttomY);
				break;

			default:
				break;
		}
		
	}

	public int getMode(){
		return this.mode;
	}

}