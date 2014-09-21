package com.colbrand.minesweeper;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
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
