import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CanvasComponent extends JComponent {
    public enum ShapeType {LINE, BOX, OVAL};
    private ShapeType currentShape;
    private Point startingPoint;
    private Point endPoint;
    private Color color;
    private ArrayList<Shape> shapes;
    private boolean trail;
    private boolean nesting;
    private final int NEST_DECREMENT = 5;
    private final int NESTING_TERMINATION = 10;

    public CanvasComponent(){
        startingPoint = new Point(0,0);
        currentShape = ShapeType.LINE;
        color = Color.BLACK;
        shapes = new ArrayList<Shape>();
        trail = false;
        nesting = false;

        setFocusable(true);
    }

    /**
     * Adds a shape to the shapes ArrayList and paints it on the canvas
     */

    public void addShape(){
        switch(currentShape){
            case LINE:
                shapes.add(new Line(startingPoint, endPoint, color));
                break;
            case BOX:
                shapes.add(new Box(startingPoint, endPoint, color));
                break;
            case OVAL:
                shapes.add(new Oval(startingPoint, endPoint, color));
                break;                
        }
        repaint();
    }

    /**
     * Modifies the endpoint location of the last shape in the shapes ArrayList and paints it to
     * the canvas
     */

    public void modifyShape(){
        shapes.get(shapes.size() - 1).setPoint2(endPoint);
        repaint();
    }

    /**
     * Recursive function that creates nested shapes within a given shape
     * @param shape the shape within which to create the nested shapes
     */

    public void addNested(Shape shape){
        Point point1 = shape.getPoint1();
        Point point2 = shape.getPoint2();
        int leftX = (int)Math.min(point1.getX(), point2.getX());
        int rightX = (int)Math.max(point1.getX(), point2.getX());
        int topY = (int)Math.min(point1.getY(), point2.getY());
        int bottomY = (int)Math.max(point1.getY(), point2.getY());
        int width = rightX - leftX;
        int height = bottomY - topY;

        if(width < NESTING_TERMINATION || height < NESTING_TERMINATION){
            return;
        }
        else{
            Point point3 = new Point(leftX + NEST_DECREMENT, topY + NEST_DECREMENT);
            Point point4 = new Point(rightX - NEST_DECREMENT, bottomY - NEST_DECREMENT);
            Color color = shape.getColor();
            Shape smallerShape;

            if(currentShape.equals(ShapeType.BOX)){
                smallerShape = new Box(point3, point4, color);
            }
            else{
                smallerShape = new Oval(point3, point4, color);
            }
            addNested(smallerShape);
            shapes.add(smallerShape);
        }
        repaint();
    }

    /**
     * Saves the shape objects on the canvas to a file selected by the user.
     */

    public void save(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if(fileChooser.showSaveDialog(CanvasComponent.this) == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try(ObjectOutputStream ofStream = new ObjectOutputStream(new FileOutputStream(filePath))){
                ofStream.writeObject(shapes);
                JOptionPane.showMessageDialog(CanvasComponent.this, "Canvas successfully saved to  " + filePath, "Canvas saved" , JOptionPane.INFORMATION_MESSAGE);

            }
            catch(IOException e){
                JOptionPane.showMessageDialog(CanvasComponent.this, "Error: file save failed. " + e.getMessage(), "File save error" , JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads a file containing shape objects onto the canvas.
     */

    public void restore(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if(fileChooser.showOpenDialog(CanvasComponent.this) == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try(ObjectInputStream ifStream = new ObjectInputStream(new FileInputStream(filePath))){
                Object obj = ifStream.readObject();
                if(!(obj instanceof ArrayList<?> list)){
                    throw new IOException("Invalid file format.");
                }
                ArrayList<Shape> fileShapes = new ArrayList<>();
                for(Object o : list){
                    if(!(o instanceof Shape)){
                        throw new IOException("File contains invalid objects.");
                    }
                    fileShapes.add((Shape) o);
                }
                shapes = fileShapes;
                repaint();
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(CanvasComponent.this, "Error: file restore failed. " + e.getMessage(), "File read error" , JOptionPane.ERROR_MESSAGE);
            }
            catch(ClassNotFoundException c){
                JOptionPane.showMessageDialog(CanvasComponent.this, "Error: file does not contain readable objects. " + c.getMessage(), "File read error" , JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Iterates through the shapes ArrayList and paints all objects to the canvas. Draws and centers
     * a String that presents the keyboard input options.
     */

    @Override
    public void paintComponent(Graphics g){
        for(Shape s : shapes){
            s.draw(g);
        }
    }

    /**
     * Clears the canvas by deleting all shapes from the ArrayList
     */

    public void clearCanvas(){
        shapes.clear();
        repaint();
    }

    /**
     * Undoes the last shape creation by removing the last element from the ArrayList
     */

    public void undo(){
        if(shapes.size() > 0){
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    public ShapeType getCurrentShapeType() {
        return currentShape;
    }

    public void setCurrentShapeType(ShapeType currentShape) {
        this.currentShape = currentShape;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getlastShape() {
        return shapes.get(shapes.size() - 1);
    }

    public int getNumShapes(){
        return shapes.size();
    }

    public boolean isTrails() {
        return trail;
    }

    public void setTrail(boolean trail) {
        this.trail = trail;
    }

    public boolean isNesting() {
        return nesting;
    }

    public void setNesting(boolean nesting) {
        this.nesting = nesting;
    }
}