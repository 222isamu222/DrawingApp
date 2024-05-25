import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ButtonListener implements MouseListener {
	ButtonManager buttonManager;
	JButton targetButton;
	String button_name;
	MyCanvas canvas;
	LineBorder border = new LineBorder(Color.BLACK, 2);
	Color enteredColor = new Color(103,113,135);
	Boolean isClicked;
	
	public ButtonListener(JButton targetButton, ButtonManager buttonManager, String button_name, MyCanvas canvas){
		this.buttonManager = buttonManager;
		this.targetButton = targetButton;
		this.button_name = button_name;
		this.canvas = canvas;
		targetButton.setBackground(enteredColor);
		targetButton.setBorder(border);
		isClicked = false;
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {
		isClicked = !isClicked;
		if(!isClicked){
			targetButton.setContentAreaFilled(false);
			targetButton.setBorderPainted(false);
		}else{
			targetButton.setContentAreaFilled(true);
			targetButton.setBorderPainted(true);
			canvas.setCurrentMode(button_name);
		}
		buttonManager.checkClicked(button_name);
	}
	public void mouseExited(MouseEvent e) {		
		if(!isClicked){
			targetButton.setContentAreaFilled(false);
			targetButton.setBorderPainted(false);
		}
		
	}

	public void mouseMoved(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		targetButton.setContentAreaFilled(true);
		targetButton.setBorderPainted(true);
	}

}
