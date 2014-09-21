package com.colbrand.minesweeper;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Colbrand
 */
public class MinePixel {

    private Rectangle2D pixelRect;
    public String pixVal;
    private double pixX;
    private double pixY;
    private Point2D pixCoords;
    private  Point2D NORTH;
    private Point2D SOUTH;
    private Point2D WEST;
    private Point2D EAST;
    private Point2D NORTH_WEST;
    private Point2D NORTH_EAST;
    private Point2D SOUTH_WEST;
    private Point2D SOUTH_EAST;
    private Point2D returnedPoint;

    public MinePixel(Rectangle2D rect, String value) {

        pixelRect = rect;
        pixVal = value;
        pixX = rect.getX();
        pixY = rect.getY();
        NORTH = new Point2D.Double(pixX + (MineComponent.rectSize / 2), pixY - (MineComponent.rectSize / 2));
        NORTH_WEST = new Point2D.Double(pixX - (MineComponent.rectSize / 2), pixY - (MineComponent.rectSize / 2));
        NORTH_EAST = new Point2D.Double(pixX + (MineComponent.rectSize + (MineComponent.rectSize / 2)), pixY - (MineComponent.rectSize / 2));
        WEST = new Point2D.Double(pixX - (MineComponent.rectSize / 2), pixY + (MineComponent.rectSize / 2));
        EAST = new Point2D.Double(pixX + (MineComponent.rectSize + (MineComponent.rectSize / 2)), pixY + (MineComponent.rectSize / 2));
        SOUTH_WEST = new Point2D.Double(pixX - (MineComponent.rectSize / 2), pixY + (MineComponent.rectSize + (MineComponent.rectSize / 2)));
        SOUTH = new Point2D.Double(pixX + (MineComponent.rectSize / 2), pixY + (MineComponent.rectSize + (MineComponent.rectSize / 2)));
        SOUTH_EAST = new Point2D.Double(pixX + (MineComponent.rectSize + (MineComponent.rectSize / 2)), pixY + (MineComponent.rectSize + (MineComponent.rectSize / 2)));
        pixCoords = new Point2D.Double(pixX, pixY);
    }

    public Rectangle2D getRect() {
        return pixelRect;
    }

    public String getVal() {
        return pixVal;
    }

    public double getX() {
        return pixX;
    }

    public double getY() {
        return pixY;
    }

    public Point2D getPoint() {
        return pixCoords;
    }

    public void setValue(String s) {
        pixVal = s;
    }

    public Point2D getNeigh(int i) {
        switch (i) {
            case 1:
                returnedPoint = NORTH;
                break;

            case 2:
                returnedPoint = SOUTH;
                break;
            case 3:
                returnedPoint = WEST;
                break;
            case 4:
                returnedPoint = EAST;
                break;
            case 5:
                returnedPoint = NORTH_WEST;
                break;
            case 6:
                returnedPoint = NORTH_EAST;
                break;
            case 7:
                returnedPoint = SOUTH_WEST;
                break;
            case 8:
                returnedPoint = SOUTH_EAST;
                break;
        }
        return returnedPoint;

    }
}
