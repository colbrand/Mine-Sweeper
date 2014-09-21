package com.colbrand.minesweeper;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *@version 1.0.1
 * @author Colbrand
 */
public class MineMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                JFrame myFrame = new MineFrame();
            }
        });
    }
}
