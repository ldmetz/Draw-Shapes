//Levi Metzger
//Shapes Phase 4

import java.awt.*;
import java.io.Serializable;

/**
 * An abstract class that defines a shape with two points and a color
 */

public abstract class Shape implements Serializable{
    private Point point1;
    private Point point2;
    private Color color;

    /**
     * Constructs a shape with both points at (0,0) and black color
     */

    public Shape(){
        point1 = new Point(0,0);
        point2 = new Point(0,0);
        color = Color.BLACK;
    }

    /**
     * Constructs a Shape object from two points and a color
     * @param pPoint1
     * @param pPoint2
     * @param pColor
     */

    public Shape(Point pPoint1, Point pPoint2, Color pColor){
        point1 = pPoint1;
        point2 = pPoint2;
        color = pColor;
    }

    /**
     * Gets the shape's first point
     * @return point1
     */

    public Point getPoint1(){
        return point1;
    }

    /**
     * Gets the shape's second point
     * @return point2
     */

    public Point getPoint2(){
        return point2;
    }

    /**
     * Gets the shape's color
     * @return color
     */

    public Color getColor(){
        return color;
    }

    /**
     * Sets the shape's first point
     * @param pPoint1
     */

    public void setPoint1(Point pPoint1){
        point1 = pPoint1;
    }

    /**
     * Sets the shape's second point
     * @param pPoint2
     */

    public void setPoint2(Point pPoint2){
        point2 = pPoint2;
    }

    /**
     * Sets the shape's color
     * @param pColor
     */

    public void setColor(Color pColor){
        color = pColor;
    }

    /**
     * Draws a shape
     * @param g the Graphics object that draws the shape
     */

    public abstract void draw(Graphics g);
}
