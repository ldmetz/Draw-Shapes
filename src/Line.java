import java.awt.*;

/**
 * A line defined by two points and a color
 */

public class Line extends Shape{

    /**
     * Constructs a Line from two Point objects and a Color object
     * @param pPoint1 the Point object defining one end of the line
     * @param pPoint2 the Point object defining the other end of the line
     * @param pColor the color of the line
     */

    public Line(Point pPoint1, Point pPoint2, Color pColor){
        super(pPoint1, pPoint2, pColor);
    }

    /**
     * Draws a line. The x and y coordinates are retrieved from both Points and passed to the 
     * drawLine() method of the Graphics parameter.
     */

    @Override
    public void draw(Graphics g){
        int x1 = (int)getPoint1().getX();
        int y1 = (int)getPoint1().getY();
        int x2 = (int)getPoint2().getX();
        int y2 = (int)getPoint2().getY();
        g.setColor(getColor());
        g.drawLine(x1, y1, x2, y2);
    }
}
