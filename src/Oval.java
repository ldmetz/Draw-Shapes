import java.awt.*;

/**
 * An oval defined by two points and a color
 */

public class Oval extends Shape{

    /**
     * Constructs an Oval from two Point objects and a Color object
     * @param pPoint1 a Point object whose x coordinate defines one end of the Oval, and whose
     * y coordinate defines either the top or bottom of the Oval
     * @param pPoint2 a Point object whose (x,y) coordinates defines the sides of the Oval not 
     * defined by point1
     * @param pColor the color of the Oval
     */

    public Oval(Point pPoint1, Point pPoint2, Color pColor){
        super(pPoint1, pPoint2, pColor);
    }

    /**
     * Draws an oval. The width, height, x coordinate for the left end, and y coordinate for the
     * top of the oval are calculated and passed to the drawOval() method of the Graphics parameter.
     */

    @Override
    public void draw(Graphics g){
        int x1 = (int)Math.min(getPoint1().getX(), getPoint2().getX());
        int y1 = (int)Math.min(getPoint1().getY(), getPoint2().getY());
        int x2 = (int)Math.max(getPoint1().getX(), getPoint2().getX());
        int y2 = (int)Math.max(getPoint1().getY(), getPoint2().getY());
        int width = x2 - x1;
        int height = y2 - y1;
        g.setColor(getColor());
        g.drawOval(x1, y1, width, height);
    }
}
