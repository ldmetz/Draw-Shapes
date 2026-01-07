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
    private JPanel mainButtonPanel;
    private JPanel actionButtonPanel;
    private JButton saveButton;
    private JButton restoreButton;
    private JButton eraseButton;
    private JButton undoButton;
    private ButtonGroup shapeButtonGroup;
    private JPanel shapeButtonPanel;
    private JLabel shapeLabel;
    private JToggleButton lineButton;
    private JToggleButton boxButton;
    private JToggleButton ovalButton;
    private ButtonGroup toggleGroup;
    private JPanel effectsButtonPanel;
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
        mainButtonPanel.setBorder(BorderFactory.createEtchedBorder());

        actionButtonPanel = new JPanel();
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> canvas.save());

        restoreButton = new JButton("Restore");
        restoreButton.addActionListener(e -> canvas.restore());

        eraseButton = new JButton("Erase");
        eraseButton.addActionListener(e -> canvas.clearCanvas());

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());

        colorButton = new JButton("Color");
        colorButton.addActionListener(e -> canvas.setColor(JColorChooser.showDialog(canvas, "Color Picker", Color.BLACK)));

        actionButtonPanel.add(saveButton);
        actionButtonPanel.add(restoreButton);
        actionButtonPanel.add(eraseButton);
        actionButtonPanel.add(undoButton);
        actionButtonPanel.add(colorButton);
        mainButtonPanel.add(actionButtonPanel);

        shapeButtonGroup = new ButtonGroup();
        shapeButtonPanel = new JPanel();
        shapeLabel = new JLabel("Shape:");
        shapeLabel.setFont(shapeLabel.getFont().deriveFont(Font.BOLD));

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

        shapeButtonPanel.add(shapeLabel);
        shapeButtonPanel.add(lineButton);
        shapeButtonPanel.add(boxButton);
        shapeButtonPanel.add(ovalButton);

        mainButtonPanel.add(shapeButtonPanel);

        toggleGroup = new ButtonGroup();
        effectsButtonPanel = new JPanel();

        trailsToggle = new JToggleButton("Trails");
        nestingToggle = new JToggleButton("Nesting");
        trailsToggle.addItemListener(e -> canvas.setTrail(e.getStateChange() == ItemEvent.SELECTED));
        nestingToggle.addItemListener(e -> canvas.setNesting(e.getStateChange() == ItemEvent.SELECTED));

        toggleGroup.add(trailsToggle);
        toggleGroup.add(nestingToggle);
        
        effectsButtonPanel.add(trailsToggle);
        effectsButtonPanel.add(nestingToggle);
        mainButtonPanel.add(effectsButtonPanel);

        for(AbstractButton b : new AbstractButton[] {saveButton, restoreButton, eraseButton, undoButton, lineButton, 
            boxButton, ovalButton, trailsToggle, nestingToggle, colorButton}){
            b.setFocusPainted(false);
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
            }
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new PictureFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
