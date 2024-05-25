import java.awt.Color;

import javax.swing.*;
import java.util.*;

public class SubToolTypePen {
	private MyCanvas canvas;
	private MyPanel panel;
	private int size;
	final Color backgroundColor = new Color(54, 54, 54);
	final Color textColor = new Color(174, 177, 183);

	public SubToolTypePen(int size, MyCanvas canvas){
		this.canvas = canvas;
		this.size = size;

		panel = new MyPanel(size, size);

		MyPanel linePanel =	new MyPanel(size-22,36);
		MyPanel dashPanel =	new MyPanel(size-22,36);
		MyPanel doubleLinePanel = new MyPanel(size-22,36);
		MyPanel straightPanel = new MyPanel(size-22, 36);
		MyPanel trianglePanel = new MyPanel(size-22, 36);

		JLabel lineLabel = new JLabel("実線");
		JLabel dashLabel = new JLabel("破線");
		JLabel doubleLineLabel = new JLabel("二重線");
		JLabel straightLabel = new JLabel("直線");
		JLabel triangleLabel = new JLabel("三角形");
		lineLabel.setForeground(textColor);
		dashLabel.setForeground(textColor);
		doubleLineLabel.setForeground(textColor);
		straightLabel.setForeground(textColor);
		triangleLabel.setForeground(textColor);
		
		PenTypePanel lineWavePanel = new PenTypePanel(size-20, PenTypePanel.LINE);
		PenTypePanel dashWavePanel = new PenTypePanel(size-20, PenTypePanel.DASH);
		PenTypePanel doubleLineWavePanel = new PenTypePanel(size-20, PenTypePanel.DOUBLE_LINE);
		PenTypePanel straightLinePanel = new PenTypePanel(size-20, PenTypePanel.STRAIGHT);
		PenTypePanel triangleLinePanel = new PenTypePanel(size-20, PenTypePanel.TRIANGLE);

		HashMap<MyPanel, PenTypePanel> typePanels = new HashMap<>();
		typePanels.put(linePanel, lineWavePanel);
		typePanels.put(dashPanel, dashWavePanel);
		typePanels.put(doubleLinePanel, doubleLineWavePanel);
		typePanels.put(straightPanel, straightLinePanel);
		typePanels.put(trianglePanel, triangleLinePanel);
		Set<MyPanel> keys = typePanels.keySet();
		for(MyPanel key : keys){
			key.addMouseListener(new PenTypePanelListener(canvas, typePanels.get(key)));
		}

		SpringLayout lineLayout = new SpringLayout();
		linePanel.setLayout(lineLayout);
		lineLayout.putConstraint(SpringLayout.WEST, lineWavePanel, -30, SpringLayout.WEST, linePanel);
		lineLayout.putConstraint(SpringLayout.NORTH, lineWavePanel, 2, SpringLayout.NORTH, linePanel);
		lineLayout.putConstraint(SpringLayout.EAST, lineLabel, -20, SpringLayout.EAST, linePanel);
		lineLayout.putConstraint(SpringLayout.SOUTH, lineLabel, -5, SpringLayout.SOUTH, linePanel);

		SpringLayout dashLayout = new SpringLayout();
		dashPanel.setLayout(dashLayout);
		dashLayout.putConstraint(SpringLayout.WEST, dashWavePanel, -30, SpringLayout.WEST, dashPanel);
		dashLayout.putConstraint(SpringLayout.NORTH, dashWavePanel, 2, SpringLayout.NORTH, dashPanel);
		dashLayout.putConstraint(SpringLayout.EAST, dashLabel, -20, SpringLayout.EAST, dashPanel);
		dashLayout.putConstraint(SpringLayout.SOUTH, dashLabel, -5, SpringLayout.SOUTH, dashPanel);
		
		SpringLayout doubleLineLayout = new SpringLayout();
		doubleLinePanel.setLayout(doubleLineLayout);
		linePanel.setLayout(lineLayout);
		doubleLineLayout.putConstraint(SpringLayout.WEST, doubleLineWavePanel, -30, SpringLayout.WEST, doubleLinePanel);
		doubleLineLayout.putConstraint(SpringLayout.NORTH, doubleLineWavePanel, 2, SpringLayout.NORTH, doubleLinePanel);
		doubleLineLayout.putConstraint(SpringLayout.EAST, doubleLineLabel, -20, SpringLayout.EAST, doubleLinePanel);
		doubleLineLayout.putConstraint(SpringLayout.SOUTH, doubleLineLabel, -5, SpringLayout.SOUTH, doubleLinePanel);

		SpringLayout straightLayout = new SpringLayout();
		straightPanel.setLayout(straightLayout);
		straightLayout.putConstraint(SpringLayout.WEST, straightLinePanel, -30, SpringLayout.WEST, straightPanel);
		straightLayout.putConstraint(SpringLayout.NORTH, straightLinePanel, 2, SpringLayout.NORTH, straightPanel);
		straightLayout.putConstraint(SpringLayout.EAST, straightLabel, -20, SpringLayout.EAST, straightPanel);
		straightLayout.putConstraint(SpringLayout.SOUTH, straightLabel, -5, SpringLayout.SOUTH, straightPanel);		

		SpringLayout triangleLayout = new SpringLayout();
		trianglePanel.setLayout(triangleLayout);
		triangleLayout.putConstraint(SpringLayout.WEST, triangleLinePanel, -30, SpringLayout.WEST, trianglePanel);
		triangleLayout.putConstraint(SpringLayout.NORTH, triangleLinePanel, 2, SpringLayout.NORTH, trianglePanel);
		triangleLayout.putConstraint(SpringLayout.EAST, triangleLabel, -20, SpringLayout.EAST, trianglePanel);
		triangleLayout.putConstraint(SpringLayout.SOUTH, triangleLabel, -5, SpringLayout.SOUTH, trianglePanel);

		SpringLayout parentLayout = new SpringLayout();
		panel.setLayout(parentLayout);
		parentLayout.putConstraint(SpringLayout.WEST, linePanel, 2, SpringLayout.WEST, panel);
		parentLayout.putConstraint(SpringLayout.NORTH, linePanel, 2, SpringLayout.NORTH, panel);
		parentLayout.putConstraint(SpringLayout.WEST, dashPanel, 2, SpringLayout.WEST, panel);
		parentLayout.putConstraint(SpringLayout.NORTH, dashPanel, 2, SpringLayout.SOUTH, linePanel);
		parentLayout.putConstraint(SpringLayout.WEST, doubleLinePanel, 2, SpringLayout.WEST, panel);
		parentLayout.putConstraint(SpringLayout.NORTH, doubleLinePanel, 2, SpringLayout.SOUTH, dashPanel);
		parentLayout.putConstraint(SpringLayout.WEST, straightPanel, 2, SpringLayout.WEST, panel);
		parentLayout.putConstraint(SpringLayout.NORTH, straightPanel, 2, SpringLayout.SOUTH, doubleLinePanel);
		parentLayout.putConstraint(SpringLayout.WEST, trianglePanel, 2, SpringLayout.WEST, panel);
		parentLayout.putConstraint(SpringLayout.NORTH, trianglePanel, 2, SpringLayout.SOUTH, straightPanel);


		panel.setBackground(new Color(41,41,41));
		linePanel.setBackground(backgroundColor);
		dashPanel.setBackground(backgroundColor);
		doubleLinePanel.setBackground(backgroundColor);
		straightPanel.setBackground(backgroundColor);
		trianglePanel.setBackground(backgroundColor);
		lineWavePanel.setOpaque(false);
		dashWavePanel.setOpaque(false);
		doubleLineWavePanel.setOpaque(false);
		straightLinePanel.setOpaque(false);
		triangleLinePanel.setOpaque(false);
		lineLabel.setOpaque(false);
		dashLabel.setOpaque(false);
		doubleLineLabel.setOpaque(false);
		straightLabel.setOpaque(false);
		straightLabel.setOpaque(false);

		linePanel.add(lineWavePanel);
		linePanel.add(lineLabel);
		linePanel.setVisible(true);

		dashPanel.add(dashWavePanel);
		dashPanel.add(dashLabel);
		dashPanel.setVisible(true);

		doubleLinePanel.add(doubleLineWavePanel);
		doubleLinePanel.add(doubleLineLabel);
		doubleLinePanel.setVisible(true);

		straightPanel.add(straightLinePanel);
		straightPanel.add(straightLabel);
		straightPanel.setVisible(true);

		trianglePanel.add(triangleLinePanel);
		trianglePanel.add(triangleLabel);
		trianglePanel.setVisible(true);

		panel.add(linePanel);
		panel.add(dashPanel);
		panel.add(doubleLinePanel);
		panel.add(straightPanel);
		panel.add(trianglePanel);
		panel.setVisible(true);
	}

	public MyPanel getPanel(){
		return this.panel;
	}
}
