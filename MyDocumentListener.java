import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.*;

public class MyDocumentListener implements DocumentListener {
	private JTextField textField;
	private String inputDataLabel;
	private MyCanvas canvas;
	private int value;
	private JPanel panel;

	public MyDocumentListener(JTextField textField, MyCanvas canvas, String inputDataLabel, JPanel panel){
		this.textField = textField;
		this.canvas = canvas;
		this.inputDataLabel = inputDataLabel;
		this.panel = panel;
	}
	public MyDocumentListener(JTextField textField){
		this.textField = textField;
	}

	@Override
	public void insertUpdate(DocumentEvent e){ updateValue(); }
	@Override
	public void removeUpdate(DocumentEvent e){ updateValue(); }
	@Override
	public void changedUpdate(DocumentEvent e){ updateValue(); }
	
	private void updateValue(){
		if(!textField.getText().isEmpty()){
			try{
				value = Integer.parseInt(textField.getText());
			}catch(NumberFormatException e){
				
			}
			if(inputDataLabel.equals("width")){
				canvas.setSizeProperty(value, canvas.getHeight());			
				panel.setSize(new Dimension(value/10, canvas.getHeight()/10));
			}else if(inputDataLabel.equals("height")){
				canvas.setSizeProperty(canvas.getWidth(), value);
				panel.setSize(new Dimension(canvas.getWidth()/10, value/10));
			}

		}

	}
}
