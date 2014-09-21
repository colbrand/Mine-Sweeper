package com.colbrand.minesweeper;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *@version  1.02
 * @author Colbrand
 */
public class MineFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 330;
    private static final int DEFAULT_HEIGHT = 300;
    Image windowIcon;
    private JPanel inputPanel;
    public static JTextField textField;

    public MineFrame() {

        inputPanel = new JPanel();
        textField = new JTextField(3);
        textField.setHorizontalAlignment(JTextField.CENTER);
        JLabel label = new JLabel("Number of mines:");
        
        inputPanel.add(label);
        inputPanel.add(textField);
        

        Border b = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(b, "Mine Settings");
        inputPanel.setBorder(titled);

        add(inputPanel, BorderLayout.SOUTH);

        add(new MineComponent());
        pack();

        String iconLocation = "/com/colbrand/minesweeper/images/ms_icon.png";
        URL imageURL = getClass().getResource(iconLocation);
        windowIcon = new ImageIcon(imageURL, null).getImage();

        setIconImage(windowIcon);
        setTitle("Mine Sweeper");
        setLocationByPlatform(true);
        setResizable(false);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
