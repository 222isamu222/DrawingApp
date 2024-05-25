import java.awt.*;
import javax.swing.*;

public class MyPanel extends JPanel{
	private int width;
	private int height;

	public MyPanel(){
		
	}

	public MyPanel(int width, int height){
		this.width = width;
		this.height = height;
	}

	@Override public Dimension getPreferredSize(){
		return new Dimension(width,height);
	}
}