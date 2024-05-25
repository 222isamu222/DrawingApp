import java.awt.event.*;

public class SizePanelListener implements MouseListener {
	private MyCanvas canvas;
	private CircularButton button;


	public SizePanelListener(MyCanvas canvas, CircularButton button){
		this.canvas = canvas;
		this.button = button;
	}
	@Override
	public void mouseClicked(MouseEvent e){
		canvas.setPenSize(button.getPenSize());
	}
	public void mouseReleased(MouseEvent e){
		
	}
	public void mouseExited(MouseEvent e){
		
	}
	public void mouseEntered(MouseEvent e){
		
	}
	public void mousePressed(MouseEvent e){

	}
}
