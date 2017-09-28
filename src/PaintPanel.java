import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class PaintPanel extends JComponent {
    private static final int SIDELENGTH = 100;
    private ArrayList<Shape> shapes;
    private Shape currentShape;
    private int tool;
    private Color fore;
    private float stroke;
    private int x1 = 0;
    private int x2 = 0;
    private int y1 = 0;
    private int y2 = 0;
    public PaintPanel() {
        tool = 0;
        stroke = 3.0f;
        fore = Color.BLACK;
        shapes = new ArrayList<>();
        currentShape = null;
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public void setTool(int t) {
        this.tool = t;
    }

    public void setStroke(float stroke) {
        this.stroke = stroke;
    }

    public void setFore(Color fore) {
        this.fore = fore;
    }

    public Color getFore() {
        return fore;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Shape s : shapes)
            s.draw(g2);
    }

    private class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            switch (tool) {
                case 0:
                    currentShape = new Oval(fore, 0, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    break;
                case 1:
                    currentShape = new Rectangle(fore, 1, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    break;
                case 2:
                    x1 = mouseEvent.getX();
                    y1 = mouseEvent.getY();
                    break;
                case 3:
                    x1 = mouseEvent.getX();
                    y1 = mouseEvent.getY();
                default:
                    break;
            }
            repaint();
        }

        public void mouseClicked(MouseEvent mouseEvent) {

        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent mouseEvent) {

        }

        public void mouseDragged(MouseEvent mouseEvent) {

            if ((tool != 2 && tool != 3) && currentShape != null) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                currentShape.setCurrentX(x);
                currentShape.setCurrentY(y);
                repaint();
            } else if (tool == 2) {
                int x2 = mouseEvent.getX();
                int y2 = mouseEvent.getY();
                Line l = new Line(fore, 2, x1, y1, x2, y2, stroke);
                x1 = x2;
                y1 = y2;
                shapes.add(l);
                repaint();
            } else if (tool == 3) {
                int x2 = mouseEvent.getX();
                int y2 = mouseEvent.getY();
                Line l = new Line(getBackground(), 2, x1, y1, x2, y2, stroke);
                x1 = x2;
                y1 = y2;
                shapes.add(l);
                repaint();
            }

        }
    }
}
