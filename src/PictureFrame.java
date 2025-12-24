//Levi Metzger
//Shapes Phase 4

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PictureFrame extends JFrame{
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 500;
    private CanvasComponent canvas;
    private MouseHandler mouseListener;
    private KeyHandler keyListener;
    private JLabel menu;

    public PictureFrame(){
        setTitle("Draw Shapes");
        setLayout(new BorderLayout());

        canvas = new CanvasComponent();
        canvas.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(canvas);

        menu = new JLabel("(E)rase (U)ndo (T)rails (N)esting (L)ine (B)ox (O)val (C)olor (S)ave (R)estore");
        menu.setHorizontalAlignment(SwingConstants.CENTER);
        menu.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        menu.setFont(menu.getFont().deriveFont(16f));
        add(menu, BorderLayout.SOUTH);

        mouseListener = new MouseHandler();
        keyListener = new KeyHandler();
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        canvas.addKeyListener(keyListener);
        canvas.setFocusable(true);

        pack();
    }

    public class MouseHandler extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            canvas.setStartingPoint(e.getPoint());
            canvas.setEndPoint(canvas.getStartingPoint());
            canvas.addShape();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            canvas.setEndPoint(e.getPoint());
            if(!canvas.isTrails()){
                canvas.modifyShape();
            }
            else{
                canvas.addShape();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
            if(canvas.isNesting() && !canvas.getCurrentShapeType().equals(CanvasComponent.ShapeType.LINE)){
                canvas.addNested(canvas.getlastShape());
                repaint();
            }
        }
    }

    public class KeyHandler extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent k){
            String key = KeyEvent.getKeyText(k.getKeyCode());
            if(key.equals("E")){
                canvas.clearCanvas();
            }
            else if(key.equals("U")){
                if(canvas.getNumShapes() > 0){
                    canvas.undo();
                }
            }
            else if(key.equals("T")){
                canvas.setTrail(!canvas.isTrails());
                canvas.setNesting(false);
            }
            else if(key.equals("L")){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.LINE);
            }
            else if(key.equals("B")){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.BOX);
            }
            else if(key.equals("O")){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.OVAL);
            }
            else if(key.equals("C")){
                canvas.setColor(JColorChooser.showDialog(canvas, "Color Picker", Color.BLACK));
            }
            else if(key.equals("N")){
                canvas.setNesting(!canvas.isNesting());
                canvas.setTrail(false);
            }
            else if(key.equals("S")){
                canvas.save();
            }
            else if(key.equals("R")){
                canvas.restore();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new PictureFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
