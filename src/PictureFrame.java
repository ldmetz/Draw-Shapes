import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PictureFrame extends JFrame{
    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 500;
    private CanvasComponent canvas;
    private MouseHandler mouseListener;
    private ButtonGroup shapeButtonGroup;
    private JRadioButton lineButton;
    private JRadioButton boxButton;
    private JRadioButton ovalButton;
    private JToggleButton trailsToggle;
    private JToggleButton nestingToggle;

    public PictureFrame(){
        setTitle("Draw Shapes");
        setLayout(new BorderLayout());

        canvas = new CanvasComponent();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        add(canvas);

        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setBorder(BorderFactory.createEtchedBorder());
        JPanel actionButtonPanel = new JPanel();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> canvas.save());

        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(e -> canvas.restore());

        JButton eraseButton = new JButton("Erase");
        eraseButton.addActionListener(e -> canvas.clearCanvas());

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> canvas.undo());

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> canvas.setColor(JColorChooser.showDialog(canvas, "Color Picker", Color.BLACK)));

        actionButtonPanel.add(saveButton);
        actionButtonPanel.add(restoreButton);
        actionButtonPanel.add(eraseButton);
        actionButtonPanel.add(undoButton);
        actionButtonPanel.add(colorButton);
        mainButtonPanel.add(actionButtonPanel);

        JPanel effectsButtonPanel = new JPanel();

        trailsToggle = new JToggleButton("Trails");
        nestingToggle = new JToggleButton("Nesting");

        trailsToggle.addItemListener(e -> {
            canvas.setTrail(trailsToggle.isSelected());
            if(trailsToggle.isSelected()){
                nestingToggle.setSelected(false);
            }
        });

        nestingToggle.addItemListener(e -> {
            canvas.setNesting(nestingToggle.isSelected());
            if(nestingToggle.isSelected()){
                trailsToggle.setSelected(false);
            }
        });
        
        effectsButtonPanel.add(trailsToggle);
        effectsButtonPanel.add(nestingToggle);
        
        mainButtonPanel.add(effectsButtonPanel);

        shapeButtonGroup = new ButtonGroup();
        JPanel shapeButtonPanel = new JPanel();

        JLabel shapeLabel = new JLabel("Shape:");
        shapeLabel.setFont(shapeLabel.getFont().deriveFont(Font.BOLD));

        lineButton = new JRadioButton("Line");
        lineButton.setSelected(true);
        lineButton.addItemListener(e -> {
            if(lineButton.isSelected()){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.LINE);
            }
        });

        boxButton = new JRadioButton("Box");
        boxButton.addItemListener(e -> {
            if(boxButton.isSelected()){
                canvas.setCurrentShapeType(CanvasComponent.ShapeType.BOX);
            }
        });

        ovalButton = new JRadioButton("Oval");
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

        for(AbstractButton b : new AbstractButton[] {saveButton, restoreButton,
            eraseButton, undoButton, trailsToggle, nestingToggle, lineButton, 
            boxButton, ovalButton, colorButton}){
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