import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /* #TODO 
        描画領域のスケーリング ← 無理そう
        描画領域の移動　←　行けそう：　ツールバーの作成(toolパネルにPanelをWESTでadd，手のマークのボタンを配置して移動モード)
     */ 
    final Color frame_backGround = new Color(45, 45, 45);
    final Color panel_backGround = new Color(62, 64, 66);
    final Color subToolPalette_backGround = new Color(56, 56, 56);
    final Color titleBar_backGround  = new Color(78, 78, 78);
    final Color fontColor = new Color(174,177,183);
    final Color menuColor = new Color(93,93,99);
    final Color borderColor = new Color(79,78,81);
    final LineBorder colorPanelBorder = new LineBorder(borderColor, 4, true);
    final int toolPaletteHGap = 2;
    final int toolWidth = 300;
    final int toolHeight = 0;
    final int propertyWidth = 275;
    final int propertyHeight = 0;
    final int toolPaletteWidth = 45;
    final int toolPaletteHeight = 0;
    final int subToolPaletteWidth = toolWidth - toolPaletteWidth - toolPaletteHGap;
    final int subToolPaletteHeight = 0;

    JFrame mainFrame;
    JPanel contentPane_main, contentPane_toolPalette;
    MyPanel tool, property, centerPanel, toolPalette, subToolPalette, penPanel, iraserPanel, currentColorPanel;
    SubToolTypePen typeContent;
    MyCanvas canvas;


    JScrollPane canvasPane;
    JViewport view;
    public static void main(String[] args){
        Main a = new Main();
        a.run(a);
    }

    public void run(Main a){
        SwingUtilities.invokeLater(() -> {
            buildingFramework();
            mainFrame.setVisible(true);
            buildingTitleBar();
            buildingToolPanel();
            buildingToolPalette(a);
            buildingSubToolPalette();
        });
        
    }

    public void buildingToolPanel(){
        toolPalette = new MyPanel(toolPaletteWidth,toolPaletteHeight);
        subToolPalette = new MyPanel(subToolPaletteWidth, 900);

        toolPalette.setBackground(panel_backGround);
        subToolPalette.setBackground(subToolPalette_backGround);

        BorderLayout layout = new BorderLayout();
        layout.setHgap(toolPaletteHGap);
        tool.setLayout(layout);
        tool.add(toolPalette, BorderLayout.WEST);
        tool.add(subToolPalette, BorderLayout.CENTER);

        tool.setVisible(true);
    }
    public void buildingToolPalette(Main a) {
        ImageIcon image_pen = new ImageIcon("src/images/pen.png");
        ImageIcon image_iraser = new ImageIcon("src/images/iraser.png");
        ImageIcon image_text = new ImageIcon("src/images/text.png");
        ImageIcon image_rainbow = new ImageIcon("src/images/rainbow.png");
        ImageIcon image_bucket = new ImageIcon("src/images/bucket.png");

        JButton button_pen = new JButton(image_pen);
        JButton button_iraser = new JButton(image_iraser);
        JButton button_text = new JButton(image_text);
        JButton button_rainbow = new JButton(image_rainbow);
        JButton button_bucket = new JButton(image_bucket);
        currentColorPanel = new MyPanel(35,35);

        Dimension size = new Dimension(35,35);
        button_pen.setMaximumSize(size);
        button_iraser.setMaximumSize(size);
        button_text.setMaximumSize(size);
        button_rainbow.setMaximumSize(size);
        button_bucket.setMaximumSize(size);

        button_pen.setContentAreaFilled(false);
        button_iraser.setContentAreaFilled(false);
        button_text.setContentAreaFilled(false);
        button_rainbow.setContentAreaFilled(false);
        button_bucket.setContentAreaFilled(false);
        button_pen.setBorderPainted(false);
        button_iraser.setBorderPainted(false);
        button_text.setBorderPainted(false);
        button_rainbow.setBorderPainted(false);
        button_bucket.setBorderPainted(false);
        currentColorPanel.setBackground(Color.BLACK);
        currentColorPanel.setBorder(colorPanelBorder);

        ButtonManager toolButtonManager = new ButtonManager();
        ButtonListener listener_pen = new ButtonListener(button_pen, toolButtonManager, "draw", canvas);
        ButtonListener listener_iraser = new ButtonListener(button_iraser, toolButtonManager, "iraser", canvas);
        ButtonListener listener_text = new ButtonListener(button_text, toolButtonManager, "text", canvas);
        ButtonListener listener_rainbow = new ButtonListener(button_rainbow, toolButtonManager, "rainbow", canvas);
        ButtonListener listener_bucket = new ButtonListener(button_bucket, toolButtonManager, "fill", canvas);
        button_pen.addMouseListener(listener_pen);
        button_iraser.addMouseListener(listener_iraser);
        button_text.addMouseListener(listener_text);
        button_rainbow.addMouseListener(listener_rainbow);
        button_bucket.addMouseListener(listener_bucket);

        List<ButtonListener> listenerList = new ArrayList<ButtonListener>();
        listenerList.add(listener_pen);
        listenerList.add(listener_iraser);
        listenerList.add(listener_text);
        listenerList.add(listener_rainbow);
        listenerList.add(listener_bucket);

        toolButtonManager.setListeners(listenerList, a);


        SpringLayout layout = new SpringLayout();
        toolPalette.setLayout(layout);

        layout.putConstraint(SpringLayout.WEST, button_pen, 5, SpringLayout.WEST, toolPalette);
        layout.putConstraint(SpringLayout.NORTH, button_pen, 5, SpringLayout.NORTH, toolPalette);
        layout.putConstraint(SpringLayout.WEST, button_iraser, 0, SpringLayout.WEST, button_pen);
        layout.putConstraint(SpringLayout.NORTH, button_iraser, 5, SpringLayout.SOUTH, button_pen);
        layout.putConstraint(SpringLayout.WEST, button_text, 0, SpringLayout.WEST, button_iraser);
        layout.putConstraint(SpringLayout.NORTH, button_text, 5, SpringLayout.SOUTH, button_iraser);
        layout.putConstraint(SpringLayout.WEST, button_rainbow, 0, SpringLayout.WEST, button_text);
        layout.putConstraint(SpringLayout.NORTH, button_rainbow, 5, SpringLayout.SOUTH, button_text);
        layout.putConstraint(SpringLayout.WEST, button_bucket, 0, SpringLayout.WEST, button_rainbow);
        layout.putConstraint(SpringLayout.NORTH, button_bucket, 5, SpringLayout.SOUTH, button_rainbow);
        layout.putConstraint(SpringLayout.SOUTH, currentColorPanel, -35, SpringLayout.SOUTH, toolPalette);
        layout.putConstraint(SpringLayout.WEST, currentColorPanel, 0, SpringLayout.WEST, button_pen);

        toolPalette.add(button_pen);
        toolPalette.add(Box.createVerticalGlue());
        toolPalette.add(button_iraser);
        toolPalette.add(Box.createVerticalGlue());
        toolPalette.add(button_text);
        toolPalette.add(Box.createVerticalGlue());
        toolPalette.add(button_rainbow);
        toolPalette.add(Box.createVerticalGlue());
        toolPalette.add(button_bucket);
        toolPalette.add(Box.createVerticalGlue());
        toolPalette.add(currentColorPanel);

        toolPalette.setVisible(true);
    }

    public void buildingSubToolPalette(){
        penPanel = new MyPanel();
        //BorderLayoutでCENTERにaddした後にCardLayoutを使いたかったけど無理なので，切り替えは断念．
        // iraserPanel = new MyPanel();

        BorderLayout parentLayout = new BorderLayout();
        subToolPalette.setLayout(parentLayout);

        penPanel.setOpaque(false);
        // iraserPanel.setOpaque(false);

        buildingPenPanel();
        // buildingIraserPanel();

        penPanel.setVisible(true);

        subToolPalette.add(penPanel, BorderLayout.CENTER);
    }
    // public void switchSubToolPalette(String currentClickedButtonName){
    //     if(currentClickedButtonName.equals("draw")){

    //     }else if(currentClickedButtonName.equals("iraser")){
    //         penPanel.setVisible(false);
    //         iraserPanel.setVisible(true);
    //     }
    // }
    // public void buildingIraserPanel(){
    //     MyPanel penSizePanel = new MyPanel(subToolPaletteWidth, (int) (subToolPaletteWidth * 0.75));
    //     penSizePanel.setOpaque(false);
    //     JLabel penSizeLabel = new JLabel("ブラシサイズ");
    //     JScrollPane penSizePane = new JScrollPane();
    //     JViewport penSizeView = new JViewport();

    //     penSizePane.setPreferredSize(new Dimension(subToolPaletteWidth, subToolPaletteWidth - 75));
    //     penSizePane.setOpaque(false);
    //     setComponentBackground(penSizePane, borderColor, fontColor);
    //     penSizeView.setOpaque(false);
    //     penSizeView.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    //     SubToolSize sizeContent = new SubToolSize(subToolPaletteWidth, canvas);
    //     penSizeView.setView(sizeContent.getSizePanel());
    //     penSizePane.setViewport(penSizeView);
    //     penSizePane.setBorder(null);
    //     setStyle(penSizeLabel, Color.WHITE, fontColor);

    //     SpringLayout iraserPanelLayout = new SpringLayout();
    //     iraserPanel.setLayout(iraserPanelLayout);
    //     iraserPanelLayout.putConstraint(SpringLayout.NORTH, penSizePanel, 0, SpringLayout.NORTH, subToolPalette);
    //     iraserPanelLayout.putConstraint(SpringLayout.WEST, penSizePanel, 0, SpringLayout.WEST, subToolPalette);

    //     SpringLayout penSizeLayout = new SpringLayout();
    //     penSizePanel.setLayout(penSizeLayout);
    //     penSizeLayout.putConstraint(SpringLayout.NORTH, penSizeLabel, 0, SpringLayout.NORTH, penSizePanel);
    //     penSizeLayout.putConstraint(SpringLayout.WEST, penSizeLabel, 0, SpringLayout.WEST, penSizePanel);
    //     penSizeLayout.putConstraint(SpringLayout.NORTH, penSizePane, 0, SpringLayout.SOUTH, penSizeLabel);
    //     penSizeLayout.putConstraint(SpringLayout.WEST, penSizePane, 0, SpringLayout.WEST, penSizeLabel);

    //     penSizePanel.add(penSizeLabel);
    //     penSizePanel.add(penSizePane);
    //     penSizePanel.setVisible(true);

    //     iraserPanel.add(penSizePanel);
    //     iraserPanel.setVisible(true);
    // }
    public void buildingPenPanel(){
        MyPanel penTypePanel = new MyPanel(subToolPaletteWidth, (int)(subToolPaletteWidth * 0.75));
        MyPanel penPropertyPanel = new MyPanel(subToolPaletteWidth, (int)(subToolPaletteWidth * 0.75));
        MyPanel penSizePanel = new MyPanel(subToolPaletteWidth, (int)(subToolPaletteWidth * 0.75));
        MyPanel penColorPanel = new MyPanel(subToolPaletteWidth, subToolPaletteWidth);
        JColorChooser colorPicker = new JColorChooser();
        JButton colorButton = new JButton("ApplyColor");

        penTypePanel.setOpaque(false);
        penPropertyPanel.setOpaque(false);
        penSizePanel.setOpaque(false);
        penColorPanel.setOpaque(false);
        colorPicker.setOpaque(false);
        colorPicker.setPreferredSize(new Dimension(subToolPaletteWidth, subToolPaletteWidth));
        setComponentBackground(colorPicker, subToolPalette_backGround, fontColor);
        colorButton.setPreferredSize(new Dimension(65,18));
        colorButton.setHorizontalAlignment(JButton.CENTER);
        setStyle(colorButton, titleBar_backGround, fontColor);

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                canvas.setCurrentColor(colorPicker.getColor());
                currentColorPanel.setBackground(canvas.getCurrentColor());
            }
        });


        Font font = new Font("MSゴシック", Font.PLAIN, 10);
        JLabel penTypeLabel = new JLabel("ツールタイプ");
        JLabel penPropertyLabel = new JLabel("ツールプロパティ");
        JLabel penSizeLabel = new JLabel("ブラシサイズ");
        JScrollPane penTypePane = new JScrollPane();
        JViewport penTypeView = new JViewport();
        JScrollPane penPropertyPane = new JScrollPane();
        JViewport penPropertyView = new JViewport();
        JScrollPane penSizePane = new JScrollPane();
        JViewport penSizeView = new JViewport();
        
        penTypePane.setPreferredSize(new Dimension(subToolPaletteWidth, subToolPaletteWidth-15));
        penTypePane.setOpaque(false);
        penTypeView.setOpaque(false);
        penTypeView.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    //subtooltypepen instance
        typeContent = new SubToolTypePen(subToolPaletteWidth, canvas);
        penTypeView.setView(typeContent.getPanel());
        penTypePane.setViewport(penTypeView);
        penTypePane.setBorder(null);

        penPropertyPane.setPreferredSize(new Dimension(subToolPaletteWidth, subToolPaletteWidth-15));
        penPropertyPane.setOpaque(false);
        penPropertyView.setOpaque(false);
        penPropertyView.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        // penPropertyView.setView();
        penPropertyPane.setViewport(penPropertyView);
        penPropertyPane.setBorder(null);

        penSizePane.setPreferredSize(new Dimension(subToolPaletteWidth, subToolPaletteWidth-75));
        penSizePane.setOpaque(false);
        setComponentBackground(penSizePane, borderColor, fontColor);
        penSizeView.setOpaque(false);
        penSizeView.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        SubToolSize sizeContent = new SubToolSize(subToolPaletteWidth, canvas);
        penSizeView.setView(sizeContent.getSizePanel());
        penSizePane.setViewport(penSizeView);
        penSizePane.setBorder(null);

        setStyle(penTypeLabel, Color.WHITE, fontColor);
        penTypeLabel.setMaximumSize(new Dimension(90, 13));
        setStyle(penPropertyLabel, Color.WHITE, fontColor);
        penPropertyLabel.setMaximumSize(new Dimension(90, 13));
        setStyle(penSizeLabel, Color.WHITE, fontColor);
        penPropertyLabel.setMaximumSize(new Dimension(90, 13));

        SpringLayout penPanelLayout = new SpringLayout();
        penPanel.setLayout(penPanelLayout);
        penPanelLayout.putConstraint(SpringLayout.NORTH, penTypePanel, 0, SpringLayout.NORTH, subToolPalette);
        penPanelLayout.putConstraint(SpringLayout.WEST, penTypePanel, 0, SpringLayout.WEST, subToolPalette);
        penPanelLayout.putConstraint(SpringLayout.NORTH, penPropertyPanel, 0, SpringLayout.SOUTH, penTypePanel);
        penPanelLayout.putConstraint(SpringLayout.WEST, penPropertyPanel, 0, SpringLayout.WEST, penTypePanel);
        penPanelLayout.putConstraint(SpringLayout.NORTH, penSizePanel, 0, SpringLayout.SOUTH, penPropertyPanel);
        penPanelLayout.putConstraint(SpringLayout.WEST, penSizePanel, 0, SpringLayout.WEST, penPropertyPanel);
        penPanelLayout.putConstraint(SpringLayout.NORTH, penColorPanel, 0, SpringLayout.SOUTH, penSizePanel);
        penPanelLayout.putConstraint(SpringLayout.WEST, penColorPanel, 0, SpringLayout.WEST, penSizePanel);

        SpringLayout penTypeLayout = new SpringLayout();
        penTypePanel.setLayout(penTypeLayout);
        penTypeLayout.putConstraint(SpringLayout.NORTH, penTypeLabel, 0, SpringLayout.NORTH, penTypePanel);
        penTypeLayout.putConstraint(SpringLayout.WEST, penTypeLabel, 0, SpringLayout.WEST, penTypePanel);
        penTypeLayout.putConstraint(SpringLayout.NORTH, penTypePane, 0, SpringLayout.SOUTH, penTypeLabel);
        penTypeLayout.putConstraint(SpringLayout.WEST, penTypePane, 0, SpringLayout.WEST, penTypeLabel);

        SpringLayout penPropertyLayout = new SpringLayout();
        penPropertyPanel.setLayout(penPropertyLayout);
        penPropertyLayout.putConstraint(SpringLayout.NORTH, penPropertyLabel, 0, SpringLayout.NORTH, penPropertyPanel);
        penPropertyLayout.putConstraint(SpringLayout.WEST, penPropertyLabel, 0, SpringLayout.WEST, penPropertyPanel);
        penPropertyLayout.putConstraint(SpringLayout.NORTH, penPropertyPane, 0, SpringLayout.SOUTH, penPropertyLabel);
        penPropertyLayout.putConstraint(SpringLayout.WEST, penPropertyPane, 0, SpringLayout.WEST, penPropertyLabel);

        SpringLayout penSizeLayout =  new SpringLayout();
        penSizePanel.setLayout(penSizeLayout);
        penSizeLayout.putConstraint(SpringLayout.NORTH, penSizeLabel, 0, SpringLayout.NORTH, penSizePanel);
        penSizeLayout.putConstraint(SpringLayout.WEST, penSizeLabel, 0, SpringLayout.WEST, penSizePanel);
        penSizeLayout.putConstraint(SpringLayout.NORTH, penSizePane, 0, SpringLayout.SOUTH, penSizeLabel);
        penSizeLayout.putConstraint(SpringLayout.WEST, penSizePane, 0, SpringLayout.WEST, penSizeLabel);

        BorderLayout penColorLayout = new BorderLayout();
        penColorPanel.setLayout(penColorLayout);

        penTypePanel.add(penTypeLabel);
        penTypePanel.add(penTypePane);
        penTypePanel.setVisible(true);

        penPropertyPanel.add(penPropertyLabel);
        penPropertyPanel.add(penPropertyPane);
        penPropertyPanel.setVisible(true);

        penSizePanel.add(penSizeLabel);
        penSizePanel.add(penSizePane);
        penSizePanel.setVisible(true);

        penColorPanel.add(BorderLayout.CENTER, colorPicker);
        penColorPanel.add(BorderLayout.NORTH, colorButton);
        penColorPanel.setVisible(true);

        penPanel.add(penTypePanel);
        penPanel.add(penPropertyPanel);
        penPanel.add(penSizePanel);
        penPanel.add(penColorPanel);
        penPanel.setVisible(true);
    }
    private void setComponentBackground(Container container, Color color, Color fontColor){
        Component[] components = container.getComponents();
        for(Component component : components){
            if(component instanceof Container){
                setComponentBackground((Container)component, color, fontColor);
            }
            setStyle(component, color, fontColor);
        }
    }

    public void buildingFramework(){
        mainFrame = new JFrame();
        mainFrame.setSize(1800, 900);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("PaintSoft");
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setBounds(0, 0, 1800, 900);

        contentPane_main = (JPanel) mainFrame.getContentPane();
        centerPanel = new MyPanel();
        tool = new MyPanel(toolWidth, toolHeight);
        property = new MyPanel(propertyWidth, propertyHeight);

        contentPane_main.setBackground(frame_backGround);
        centerPanel.setBackground(frame_backGround);
        tool.setBackground(frame_backGround);
        property.setBackground(panel_backGround);

        BorderLayout layout = new BorderLayout();
        layout.setHgap(15);
        mainFrame.setLayout(layout);

        contentPane_main.add(tool, BorderLayout.WEST);
        contentPane_main.add(centerPanel, BorderLayout.CENTER);
        contentPane_main.add(property, BorderLayout.EAST);

        mainFrame.setVisible(true);


        //pan parameter
        // canvas = new MyCanvas(1, 1);
        // canvasPane = new JScrollPane();
        // view = new JViewport();

        // canvasPane.setPreferredSize(new Dimension(1, 1));
        // view.setBackground(frame_backGround);
        // view.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        

        // view.setView(canvas);
        // canvasPane.setViewport(view);

        centerPanel.setLayout(null);
        canvas = new MyCanvas(1,1);
        canvas.setBounds(0, 0, 100, 100);

        centerPanel.add(canvas);
    }
    public void buildingTitleBar() {
        // Component
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("ファイル(F)");
        JMenuItem menuItem1_new = new JMenuItem("新規"); // TODO:JDialog使ってポップアップウィンドウを表示，Canvasの作成サイズを入力する
        JMenuItem menuItem1_save = new JMenuItem("保存");
        JMenuItem menuItem1_load = new JMenuItem("開く");
        JMenu menu2 = new JMenu("編集");

        // ClickedProcess
        menuItem1_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewCanvas();
            }
        });
        menuItem1_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                BufferedImage bufferedImage = canvas.getBufferedImage();

                fileChooser.setSelectedFile(new File(canvas.getFileName()));

                int result = fileChooser.showSaveDialog(mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();

                    // ファイルが.png拡張子で終わっていない場合は、拡張子を追加
                    if (!filePath.toLowerCase().endsWith(".png")) {
                        file = new File(filePath + ".png");
                    }

                    try {
                        ImageIO.write(bufferedImage, "png", file);
                        JOptionPane.showMessageDialog(mainFrame, "Image saved successfully!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame, "Failed to save image!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        menuItem1_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(mainFrame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        // 画像ファイルをBufferedImageに読み込む
                        BufferedImage image = ImageIO.read(file);
                        canvas.setBufferedImage(image);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame, "Failed to load image!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Style
        menuBar.setBackground(panel_backGround);
        menu1.setForeground(fontColor);
        menu2.setForeground(fontColor);
        menuBar.setBorder(new LineBorder(frame_backGround, 2));
        setStyle(menuItem1_new, menuColor, fontColor);
        setStyle(menuItem1_save, menuColor, fontColor);
        setStyle(menuItem1_load, menuColor, fontColor);

        // KeybordNemonic
        menu1.setMnemonic(KeyEvent.VK_F);

        // add
        menuBar.add(menu1);
        menu1.add(menuItem1_new);
        menu1.add(menuItem1_save);
        menu1.add(menuItem1_load);
        menuBar.add(menu2);

        mainFrame.setJMenuBar(menuBar);
    }

    // public void setCanvasView(){
    //     canvasPane.setPreferredSize(new Dimension(canvas.getWidth()/2, canvas.getHeight()/2));
    //     view.setView(canvas);
    //     canvasPane.setViewport(view);
    // }
    public void createNewCanvas() {
        JDialog customDialog = new JDialog(mainFrame, "New");
        MyPanel filePanel = new MyPanel(0, 80);
        MyPanel decorationPanel_1 = new MyPanel(1, 1);
        MyPanel decorationPanel_2 = new MyPanel(1, 1);
        MyPanel decorationPanel_3 = new MyPanel(1, 1);
        JPanel canvasPanel = new JPanel();
        JPanel dialogcontentPane_main = (JPanel) customDialog.getContentPane();

        // size
        customDialog.setSize(600, 400);

        // style
        customDialog.setResizable(false);
        setStyle(dialogcontentPane_main, panel_backGround, fontColor);
        setStyle(canvasPanel, frame_backGround, fontColor);
        setStyle(filePanel, panel_backGround, fontColor);
        setStyle(decorationPanel_1, panel_backGround, fontColor);
        setStyle(decorationPanel_2, panel_backGround, fontColor);
        setStyle(decorationPanel_3, panel_backGround, fontColor);

        // layout
        BorderLayout layout = new BorderLayout();
        layout.setVgap(7);
        layout.setHgap(7);
        customDialog.setLayout(layout);

        // filepanel content
        SpringLayout fileLayout = new SpringLayout();
        filePanel.setLayout(fileLayout);
        JTextField filename = new JTextField(30);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        JLabel filenameLabel = new JLabel("FileName");

        setStyle(filenameLabel, frame_backGround, fontColor);

        

        // size
        filename.setSize(30, 300);
        okButton.setPreferredSize(new Dimension(75, 25));
        cancelButton.setPreferredSize(new Dimension(75, 25));

        // event process
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.resizeCanvas();
                // setCanvasView();
                canvas.setFileName(filename.getText());
                mainFrame.setVisible(true);
                customDialog.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customDialog.dispose();
            }
        });

        // layout
        fileLayout.putConstraint(SpringLayout.WEST, filenameLabel, 25, SpringLayout.WEST, filePanel);
        fileLayout.putConstraint(SpringLayout.NORTH, filenameLabel, 40, SpringLayout.NORTH, filePanel);
        fileLayout.putConstraint(SpringLayout.WEST, filename, 25, SpringLayout.EAST, filenameLabel);
        fileLayout.putConstraint(SpringLayout.NORTH, filename, 40, SpringLayout.NORTH, filePanel);
        fileLayout.putConstraint(SpringLayout.EAST, okButton, -3, SpringLayout.EAST, filePanel);
        fileLayout.putConstraint(SpringLayout.NORTH, okButton, 3, SpringLayout.NORTH, filePanel);
        fileLayout.putConstraint(SpringLayout.EAST, cancelButton, -3, SpringLayout.EAST, filePanel);
        fileLayout.putConstraint(SpringLayout.NORTH, cancelButton, 2, SpringLayout.SOUTH, okButton);

        filePanel.add(filenameLabel);
        filePanel.add(filename);
        filePanel.add(okButton);
        filePanel.add(cancelButton);

        // build
        customDialog.add(filePanel, BorderLayout.NORTH);
        customDialog.add(canvasPanel, BorderLayout.CENTER);
        customDialog.add(decorationPanel_1, BorderLayout.WEST);
        customDialog.add(decorationPanel_2, BorderLayout.EAST);
        customDialog.add(decorationPanel_3, BorderLayout.SOUTH);

        // canvaspanel content
        SpringLayout canvasLayout = new SpringLayout();
        JLabel canvasLabel = new JLabel("Canvas (0 < width,height <= 2000)");
        JLabel widthLabel = new JLabel("width:");
        JLabel heightLabel = new JLabel("Height:");
        JLabel colorLabel = new JLabel("Paper color");
        JTextField widthField = new JTextField(6);
        JTextField heightField = new JTextField(6);
        JPanel colorPanel = new JPanel();
        JPanel samplePanel = new JPanel();

        setStyle(widthLabel, frame_backGround, fontColor);
        setStyle(heightLabel, frame_backGround, fontColor);
        setStyle(colorLabel, frame_backGround, fontColor);
        setStyle(canvasLabel, frame_backGround, fontColor);


        // event process
        widthField.setDocument(new SizeDocument());
        heightField.setDocument(new SizeDocument());
        widthField.getDocument().addDocumentListener(new MyDocumentListener(widthField, canvas, "width", samplePanel));
        heightField.getDocument().addDocumentListener(new MyDocumentListener(heightField, canvas, "height", samplePanel));

        colorPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                JColorChooser colorChooser = new JColorChooser();
                Color selectedColor = colorChooser.showDialog(customDialog, "select color", Color.WHITE);
                if (selectedColor != null) {
                    colorPanel.setBackground(selectedColor);
                    samplePanel.setBackground(selectedColor);
                    canvas.setInitialColor(selectedColor);
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }
        });

        // style
        widthField.setPreferredSize(new Dimension(75, 20));
        heightField.setPreferredSize(new Dimension(75, 20));
        colorPanel.setPreferredSize(new Dimension(80, 20));
        
        colorPanel.setBorder(colorPanelBorder);
        widthField.setText("1600");
        heightField.setText("1200");
        samplePanel.setPreferredSize(new Dimension(Integer.parseInt(widthField.getText()) / 10,
                Integer.parseInt(heightField.getText()) / 10));

        // layout
        canvasPanel.setLayout(canvasLayout);
        canvasLayout.putConstraint(SpringLayout.WEST, canvasLabel, 10, SpringLayout.WEST, canvasPanel);
        canvasLayout.putConstraint(SpringLayout.NORTH, canvasLabel, 10, SpringLayout.NORTH, canvasPanel);
        canvasLayout.putConstraint(SpringLayout.WEST, widthLabel, 80, SpringLayout.WEST, canvasPanel);
        canvasLayout.putConstraint(SpringLayout.NORTH, widthLabel, 5, SpringLayout.SOUTH, canvasLabel);
        canvasLayout.putConstraint(SpringLayout.NORTH, heightLabel, 10, SpringLayout.SOUTH, widthLabel);
        canvasLayout.putConstraint(SpringLayout.EAST, heightLabel, 0, SpringLayout.EAST, widthLabel);
        canvasLayout.putConstraint(SpringLayout.NORTH, colorLabel, 15, SpringLayout.SOUTH, heightLabel);
        canvasLayout.putConstraint(SpringLayout.EAST, colorLabel, 0, SpringLayout.EAST, heightLabel);
        canvasLayout.putConstraint(SpringLayout.WEST, widthField, 8, SpringLayout.EAST, widthLabel);
        canvasLayout.putConstraint(SpringLayout.NORTH, widthField, 0, SpringLayout.NORTH, widthLabel);
        canvasLayout.putConstraint(SpringLayout.WEST, heightField, 0, SpringLayout.WEST, widthField);
        canvasLayout.putConstraint(SpringLayout.NORTH, heightField, 0, SpringLayout.NORTH, heightLabel);
        canvasLayout.putConstraint(SpringLayout.NORTH, colorPanel, 0, SpringLayout.NORTH, colorLabel);
        canvasLayout.putConstraint(SpringLayout.WEST, colorPanel, 0, SpringLayout.WEST, widthField);
        canvasLayout.putConstraint(SpringLayout.NORTH, samplePanel, 0, SpringLayout.NORTH, widthLabel);
        canvasLayout.putConstraint(SpringLayout.WEST, samplePanel, 175, SpringLayout.EAST, widthLabel);

        // build
        canvasPanel.add(canvasLabel);
        canvasPanel.add(widthLabel);
        canvasPanel.add(heightLabel);
        canvasPanel.add(colorLabel);
        canvasPanel.add(widthField);
        canvasPanel.add(heightField);
        canvasPanel.add(colorPanel);
        canvasPanel.add(samplePanel);

        customDialog.setVisible(true);
    }
    private void setStyle(Component component, Color backgroundColor, Color textColor) {
        component.setBackground(backgroundColor);
        component.setForeground(textColor);
    }

}
