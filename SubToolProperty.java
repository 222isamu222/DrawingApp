public class SubToolProperty {
	private int size;
	private MyCanvas canvas;
	private MyPanel panel;


	public SubToolProperty(int size, MyCanvas canvas){
		this.size = size;
		this.canvas = canvas;

		panel = new MyPanel(size, size);
	}
}
