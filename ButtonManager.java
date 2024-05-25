import java.util.*;

public class ButtonManager {
	List<ButtonListener> listeners = new ArrayList<ButtonListener>();
	private String currentClickedButton;
	private Main a;

	public void setListeners(List<ButtonListener> listeners, Main a){
		this.listeners = listeners;
		this.a = a;

		currentClickedButton = "none";
	}

	public void checkClicked(String callerName){
		// if (!currentClickedButton.equals(callerName)){
		// 	currentClickedButton = callerName;
		// 	a.switchSubToolPalette(currentClickedButton);
		// }
		for(ButtonListener listener : listeners){
			if(!listener.button_name.equals(callerName) && listener.isClicked){
				listener.isClicked = false;
				listener.targetButton.setContentAreaFilled(false);
				listener.targetButton.setBorderPainted(false);
			}
		}
	}

	public String getCurrentClickedButton(){ return this.currentClickedButton; }

}
