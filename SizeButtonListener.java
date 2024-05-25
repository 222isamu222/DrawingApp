import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SizeButtonListener implements ActionListener {
	CircularButton sizeButton;
	MyCanvas canvas;

	public SizeButtonListener(CircularButton button, MyCanvas canvas){
		this.sizeButton = button;
		this.canvas = canvas;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		canvas.setPenSize(sizeButton.getPenSize());
	}
	
}
