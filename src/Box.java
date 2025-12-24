//Levi Metzger
//Shapes Phase 4

import java.awt.*;

/**
 * A rectangular box defined by two points and a color
 */

public class Box extends Shape {

    /**
     * Constructs a Box from two Point objects and a Color object
     * @param pPoint1 the Point defining one corner of the box
     * @param pPoint2 the Point defining a second corner of the box opposite point1
     * @param pColor the color of the Box
     */

    public Box(Point pPoint1, Point pPoint2, Color pColor){
        super(pPoint1, pPoint2, pColor);
    }

    /**
     * Draws a rectangular box. The width, height, and (x,y) coordinates for the top left corner of
     * the box are calculated and passed to the drawRect() method of the Graphics parameter.
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
        g.drawRect(x1, y1, width, height);
    }
}
