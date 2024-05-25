import java.awt.event.*;

public class PenTypePanelListener implements MouseListener {
	private MyCanvas canvas;
	private PenTypePanel panel;


	public PenTypePanelListener(MyCanvas canvas, PenTypePanel panel){
		this.canvas = canvas;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		canvas.setPenMode(panel.getMode());
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}
}
