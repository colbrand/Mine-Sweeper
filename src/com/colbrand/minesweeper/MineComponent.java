package com.colbrand.minesweeper;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * @version 1.02
 * @author Colbrand
 */
public class MineComponent extends JComponent {

    private ArrayList<PanelImage> overlayPanel;
    private ArrayList<MinePixel> grid;
    private ArrayList<MinePixel> pixelZero;
    private ArrayList<MineMarker> markerLocations;

    private Rectangle2D current;
    public static int rectSize = 20;
    private int numberOfMines;
    private int counter = 0;
    private boolean gameStart = true;
    private MinePixel currentPixel;
    PanelImage currentPanelImage;
    private MineMarker currentMineMarker;

    private Font myFont;

    public MineComponent() {
        grid = new ArrayList<>();
        overlayPanel = new ArrayList<>();
        pixelZero = new ArrayList<>();
        markerLocations = new ArrayList<>();

        myFont = new Font("SansSerif", Font.BOLD, 10);

        startGame();

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionListenerHandler());

    }

    public void startGame() {

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 10; y++) {
                current = new Rectangle2D.Double(x * 20 + 10, y * 20 + 10, rectSize, rectSize);

                grid.add(new MinePixel(current, null));
                Point2D panelPoint = new Point2D.Double(x * 20 + 10, y * 20 + 10);
                overlayPanel.add(new PanelImage(panelPoint));

            }
        }

    }

    public void checkMineNumber(Point2D p) {
        try {
            numberOfMines = Integer.parseInt(MineFrame.textField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter an integer between 1-80");
            return;
        }
        if (numberOfMines != 0 && numberOfMines < 81) {
            setTable(p);
        } else {
            JOptionPane.showMessageDialog(this, "Enter an integer between 1-80");
        }

    }

    public void setTable(Point2D p) {

        MineFrame.textField.setEnabled(false);
        for (MinePixel k : grid) {
            if (k.getRect().contains(p)) {
                currentPixel = k;
            }
        }

        for (int i = 0; i < numberOfMines; i++) {
            Random rnd = new Random();

            int rndNumb = rnd.nextInt(150);

            while (grid.get(rndNumb).getVal() == "m") {
                rndNumb = rnd.nextInt(150);
            }
            for (int o = 1; o < 9; o++) {
                while (grid.get(rndNumb).getRect().contains(currentPixel.getNeigh(o)) || grid.get(rndNumb).getRect().contains(p)) {
                    rndNumb = rnd.nextInt(150);
                }
            }

            grid.get(rndNumb).setValue("m");

        }

        for (MinePixel e : grid) {
            if (e.getVal() != "m") {
                String valString = getOverlay(e);
                e.setValue(valString);
            }
        }

        currentPixel = findMinePixel(p);
        removePanel(findPanelImage(p));
        gameStart = false;

        openCells(currentPixel);
        repaint();

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (MinePixel m : grid) {

            g2.setColor(Color.BLACK);
            g2.draw(m.getRect());
            if (m.getVal() != null) {
                if (m.getVal() == "m") {
                    g2.setColor(Color.RED);
                    g2.fill(m.getRect());

                } else {
                    g2.setFont(myFont);
                    if (m.getVal().equalsIgnoreCase("0")) {
                        g2.drawString("", (int) m.getX() + 8, (int) m.getY() + 15);
                    } else {
                        g2.drawString(m.getVal(), (int) m.getX() + 8, (int) m.getY() + 15);
                    }
                }
            }
        }
        for (PanelImage e : overlayPanel) {
            g2.drawImage(e.getImage(), (int) e.getImagePoint().getX(), (int) e.getImagePoint().getY(), null);
        }
        for (MineMarker e : markerLocations) {
            g2.drawImage(e.getMarkerImage(), (int) e.getMarkerPoint().getX(), (int) e.getMarkerPoint().getY(), null);
        }

        g2.setColor(Color.RED);
        g2.drawString(mineCalc(), 5, 10);

        String markerCount = Integer.toString(markerLocations.size());
        g2.drawString(markerCount, 20, 10);

    }

    public String getOverlay(MinePixel mp) {
        int counterVal = 0;
        for (int i = 1; i < 9; i++) {
            if (mp.getVal() == "m") {
                return "m";
            }
            for (MinePixel e : grid) {
                if (mp.getPoint() != e.getPoint()) {
                    if (e.getRect().contains(mp.getNeigh(i))) {
                        if (e.getVal() == "m") {
                            counterVal++;
                        }
                    }
                }
            }
        }
        String sVal = Integer.toString(counterVal);
        return sVal;

    }

    public String mineCalc() {
        int mineCounter = 0;
        for (MinePixel e : grid) {
            if (e.getVal() == "m") {
                mineCounter++;
            }
        }
        String mineString = Integer.toString(mineCounter);
        return mineString;
    }

    public PanelImage findPanelImage(Point2D p) {

        for (PanelImage e : overlayPanel) {
            if (e.getImageRect().contains(p)) {
                return e;
            }
        }
        return null;

    }

    public MinePixel findMinePixel(Point2D p) {
        for (MinePixel e : grid) {
            if (e.getRect().contains(p)) {
                return e;

            }
        }
        return null;
    }

    public String findMinePixelValue(MinePixel m) {
        return m.getVal();
    }

    public void removePanel(PanelImage p) {
        if (p == null) {
            return;
        }

        overlayPanel.remove(p);
        repaint();

    }

    public void removePanelFromMinePixel(MinePixel m) {
        removePanel(findPanelImage(m.getPoint()));
    }

    public MineMarker findMineMarker(Point2D p) {
        for (MineMarker e : markerLocations) {
            if (e.getMarkerRect().contains(p)) {
                return e;
            }
        }
        return null;
    }

    public void setMarker(Point2D p) {
        currentPanelImage = findPanelImage(p);
        currentMineMarker = new MineMarker(currentPanelImage.getImagePoint());

        markerLocations.add(currentMineMarker);
        repaint();

    }

    public void removeMarker(MineMarker m) {
        if (m == null) {
            return;
        }
        markerLocations.remove(m);
        repaint();
    }

    public void checkMines(Point2D p) {
        currentPixel = findMinePixel(p);
        if (currentPixel.getVal().equalsIgnoreCase("m")) {
            JOptionPane.showMessageDialog(this, "You stepped on a mine!!!");
            System.exit(0);
        }
    }

    public void checkGameStatus(Point2D p) {
        int markerCounter = 0;
        if (markerLocations.size() == numberOfMines) {
            for (MineMarker m : markerLocations) {
                currentPixel = findMinePixel(m.getMarkerPoint());
                if (currentPixel.getVal().equalsIgnoreCase("m")) {
                    m.setMineStatus(1);
                } else {
                    m.setMineStatus(0);
                }
            }
            for (MineMarker m : markerLocations) {
                if (m.getMineStatus() == 1) {
                    markerCounter++;
                }
            }
            if (markerCounter == numberOfMines) {
                JOptionPane.showMessageDialog(this, "You cleared the mines!!!");
                System.exit(0);
            }
        }

    }

    public void openCells(MinePixel m) {

        if (m.getVal().equalsIgnoreCase("0")) {
            if (!pixelZero.contains(m)) {
                pixelZero.add(m);
                for (int i = 1; i < 9; i++) {
                    currentPixel = findMinePixel(m.getNeigh(i));
                    if (currentPixel != null) {

                        removePanelFromMinePixel(currentPixel);
                        if (currentPixel.getVal().equalsIgnoreCase("0")) {
                            openCells(currentPixel);

                        }
                    }
                }

            }
        }

    }

    private static class PanelImage {

        private Image panelImg;
        private Point2D imagePoint;
        private Rectangle2D imageRect;

        public PanelImage(Point2D p) {
            String imageLocation = "/com/colbrand/minesweeper/images/panel.png";
            URL imageURL = getClass().getResource(imageLocation);
            panelImg = new ImageIcon(imageURL).getImage();

            imagePoint = p;
            imageRect = new Rectangle2D.Double(p.getX(), p.getY(), 20, 20);
        }

        public Image getImage() {
            return panelImg;
        }

        public Point2D getImagePoint() {
            return imagePoint;
        }

        public Rectangle2D getImageRect() {
            return imageRect;
        }
    }

    public class MineMarker {

        private Image mineMarkerCover;
        private Point2D markerPoint;
        private Rectangle2D markerRect;
        private int mineStatus;

        public MineMarker(Point2D p) {
            String mineMarkerImage = "/com/colbrand/minesweeper/images/marker.png";
            URL mineMarkerURL = getClass().getResource(mineMarkerImage);
            mineMarkerCover = new ImageIcon(mineMarkerURL).getImage();

            mineStatus = 0;
            markerPoint = p;
            markerRect = new Rectangle2D.Double(p.getX(), p.getY(), 20, 20);

        }

        public int getMineStatus() {
            return mineStatus;
        }

        public void setMineStatus(int a) {
            mineStatus = a;
        }

        public Rectangle2D getMarkerRect() {
            return markerRect;
        }

        public Point2D getMarkerPoint() {
            return markerPoint;
        }

        public Image getMarkerImage() {
            return mineMarkerCover;
        }
    }

    private class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (gameStart == true) {
                    checkMineNumber(e.getPoint());
                    /*setTable(e.getPoint());
                     removePanel(findPanelImage(e.getPoint()));
                     gameStart = false;
                     repaint();*/

                } else {
                    if (findMineMarker(e.getPoint()) != null && findPanelImage(e.getPoint()) != null) {
                        removeMarker(findMineMarker(e.getPoint()));
                        repaint();
                    } else {
                        removePanel(findPanelImage(e.getPoint()));

                        currentPixel = findMinePixel(e.getPoint());
                        openCells(currentPixel);
                        checkMines(e.getPoint());
                        repaint();
                    }
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                currentMineMarker = findMineMarker(e.getPoint());
                currentPixel = findMinePixel(e.getPoint());
                if (currentMineMarker == null && markerLocations.size() < numberOfMines && findPanelImage(e.getPoint()) != null) {
                    setMarker(e.getPoint());

                    checkGameStatus(e.getPoint());
                    repaint();
                }

            }
        }
    }

    private class MouseMotionListenerHandler implements MouseMotionListener {

        public void mouseMoved(MouseEvent e) {

            if (findPanelImage(e.getPoint()) != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            if (findMineMarker(e.getPoint()) != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
            if (findPanelImage(e.getPoint()) == null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

        }

        public void mouseDragged(MouseEvent e) {
            //Not Defined
        }
    }

}
