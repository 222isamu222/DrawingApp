import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

public class SubToolSize extends MyPanel{
	MyPanel sizePanel;
	MyCanvas canvas;
	Color backgroundColor = new Color(54,54,54);
	final Color fontColor = new Color(174, 177, 183);

	public SubToolSize(int parentWidth, MyCanvas canvas){
		this.canvas = canvas;

		sizePanel = new MyPanel(parentWidth - 30, (int) (parentWidth / 6 * 1.5)*5);
		sizePanel.setBackground(new Color(41, 41, 41));

		MyPanel[] panel = new MyPanel[30];
		CircularButton[] button = new CircularButton[30];
		JLabel[] label = new JLabel[30];

		GridLayout parentLayout = new GridLayout(5, 6);
		parentLayout.setHgap(2);
		parentLayout.setVgap(2);
		sizePanel.setLayout(parentLayout);

		int currentSize = 1;
		for(int i=0; i<label.length; i++){
			SpringLayout layout = new SpringLayout();
			panel[i] = new MyPanel(parentWidth / 6 - 30, (int) (parentWidth / 6 * 1.5));
			button[i] = new CircularButton();
			label[i] = new JLabel();

			panel[i].setLayout(layout);
			panel[i].setBackground(backgroundColor);
			label[i].setText(String.valueOf(currentSize));
			button[i].setPenSize(currentSize);
			currentSize++;
			label[i].setForeground(fontColor);

			if(currentSize >= 20){
				button[i].setPreferredSize(new Dimension(25, 25));
			}else{
				button[i].setPreferredSize(new Dimension(i+5, i+5));
			}
			SizeButtonListener buttonListener = new SizeButtonListener(button[i], canvas);
			button[i].addActionListener(buttonListener);
			SizePanelListener panelListener = new SizePanelListener(canvas, button[i]);
			panel[i].addMouseListener(panelListener);

			layout.putConstraint(SpringLayout.NORTH, button[i], 9, SpringLayout.NORTH, panel[i]);
			layout.putConstraint(SpringLayout.WEST, button[i], 7, SpringLayout.WEST, panel[i]);
			layout.putConstraint(SpringLayout.SOUTH, label[i], -3, SpringLayout.SOUTH, panel[i]);
			layout.putConstraint(SpringLayout.WEST, label[i], 12, SpringLayout.WEST, panel[i]);
			panel[i].add(button[i]);
			panel[i].add(label[i]);
			sizePanel.add(panel[i]);
		}


	}

	public MyPanel getSizePanel(){
		return sizePanel;
	}
}
