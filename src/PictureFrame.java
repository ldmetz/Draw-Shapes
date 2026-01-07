//Levi Metzger
//Shapes Phase 4

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;
import java.awt.*;

public class PictureFrame extends JFrame{
    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 500;
    private CanvasComponent canvas;
    private MouseHandler mouseListener;
    private JPanel mainButtonPanel;
    private JButton saveButton;
    private JButton restoreButton;
    private JButton eraseButton;
    private JButton undoButton;
    private ButtonGroup shapeButtonGroup;
    private JToggleButton lineButton;
    private JToggleButton boxButton;
    private JToggleButton ovalButton;
    private ButtonGroup toggleGroup;
    private JToggleButton trailsToggle;
    private JToggleButton nestingToggle;
    private JButton colorButton;

    public PictureFrame(){
        setTitle("Draw Shapes");
        setLayout(new BorderLayout());

        canvas = new CanvasComponent();
        canvas.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(canvas);

        mainButtonPanel = new JPanel();
        mainButtonPanel.setBorder(new EtchedBorder());

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> canvas.save());

        restoreButton = new JButton("Restore");
        restoreButton.addActionListener(e -> canvas.restore());

        eraseButton = new JButton("Erase");
        eraseButton.addActionListener(e -> canvas.clearCanvas());

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());

        mainButtonPanel.add(saveButton);
        mainButtonPanel.add(restoreButton);
        mainButtonPanel.add(eraseButton);
        mainButtonPanel.add(undoButton);

        shapeButtonGroup = new ButtonGroup();

        lineButton = new JToggleButton("Line");
        lineButton.setSelected(true);
        lineButton.addItemListener(e -> {
            if(lineButton.isSelected()){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.LINE);
            }
        });

        boxButton = new JToggleButton("Box");
        boxButton.addItemListener(e -> {
            if(boxButton.isSelected()){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.BOX);
            }
        });

        ovalButton = new JToggleButton("Oval");
        ovalButton.addItemListener(e -> {
            if(ovalButton.isSelected()){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.OVAL);
            }
        });

        shapeButtonGroup.add(lineButton);
        shapeButtonGroup.add(boxButton);
        shapeButtonGroup.add(ovalButton);

        mainButtonPanel.add(lineButton);
        mainButtonPanel.add(boxButton);
        mainButtonPanel.add(ovalButton);

        toggleGroup = new ButtonGroup();
        trailsToggle = new JToggleButton("Trails");
        nestingToggle = new JToggleButton("Nesting");

        trailsToggle.addItemListener(e -> canvas.setTrail(e.getStateChange() == ItemEvent.SELECTED));
        nestingToggle.addItemListener(e -> canvas.setNesting(e.getStateChange() == ItemEvent.SELECTED));

        toggleGroup.add(trailsToggle);
        toggleGroup.add(nestingToggle);
        
        mainButtonPanel.add(trailsToggle);
        mainButtonPanel.add(nestingToggle);

        colorButton = new JButton("Color");
        colorButton.addActionListener(e -> canvas.setColor(JColorChooser.showDialog(canvas, "Color Picker", Color.BLACK)));
        mainButtonPanel.add(colorButton);

        for(AbstractButton b : new AbstractButton[] {saveButton, restoreButton, eraseButton, undoButton, lineButton, 
            boxButton, ovalButton, trailsToggle, nestingToggle, colorButton}){
            b.setFocusPainted(false);
            b.setBorderPainted(true);
        }

        add(mainButtonPanel, BorderLayout.SOUTH);

        mouseListener = new MouseHandler();
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
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

    public static void main(String[] args) throws Exception {
        JFrame frame = new PictureFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
