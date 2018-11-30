/*
  可以绘制图案的区域
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class PaintPanel extends JComponent implements Serializable {
    private ArrayList<Shape> shapes;
    private Shape currentShape;
    private String currentText;
    private Stack<Operation> operationStack;
    private Stack<Operation> redoStack;
    public enum tools {OVAL, RECTANGLE, LINE, SEGMENT, SELECT, TEXT}//0 圆形 1 方形 2 自由画线 3 橡皮 4 选择 5 线段
    public enum cursors {DEFAULT, CROSS, MOVE}// 0 普通鼠标 1 十字架 2 移动
    private Font font;
    private tools tool;
    private Color fore;
    private float stroke;
    private cursors cursor;
    private int x1 = 0;
    private int y1 = 0;
    private boolean fill;
    private boolean delete;

    public PaintPanel() {
        tool = tools.OVAL;
        fill = false;
        stroke = 3.0f;
        fore = Color.BLACK;
        font =  new Font("黑体", 0, 20);
        shapes = new ArrayList<>();
        currentShape = null;
        currentText = null;
        operationStack = new Stack<>();
        redoStack = new Stack<>();
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        cursor = cursors.CROSS;
    }

    public void setTool(tools t) {
        this.tool = t;
    }

    public void setCurrentText(String t) {
        this.currentText = t;
    }

    public void setStroke(float stroke) {
        this.stroke = stroke;
    }

    public void setFore(Color fore) {
        this.fore = fore;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getFore() {
        return fore;
    }

    public void setFill(boolean isFilled) {this.fill = isFilled;}

    public void setDelete(boolean del) {this.delete = del;}


    /**ArrayList
     * 将存储的所有图形绘制出来
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Shape s : shapes) {
            s.draw(g2);
        }

    }

    public void undo() {
        if(!operationStack.empty()) {
            Operation o = operationStack.pop();
            o.undo();
            redoStack.push(o);
            repaint();
        }
    }

    public void redo() {
        if(!redoStack.empty()) {
            Operation o = redoStack.pop();
            o.redo();
            operationStack.push(o);
            repaint();
        }
    }

    /**
     * 查找坐标(x,y)处是否有图形，如有，返回这个图形，否则返回null
     */
    public Shape find (int x, int y) {
        for (Shape r : shapes) {
            if (r.contain(x, y) && !r.isDeleted)
                return r;
        }
        return null;
    }

    /**
     * 将目前的内容画到一个BufferedImage中并返回
     */
    public BufferedImage getImage () {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.fillRect(0,0, image.getWidth(), image.getHeight());
        paint(graphics2d);
        return image;
    }
    /**
     * 清除所存的所有图形信息并重绘
     */
    public void clear () {
        shapes.clear();
        operationStack.clear();
        redoStack.clear();
        repaint();
    }

    /**
     * 将目前的图形信息保存到一个文件中
     */
    public void writeImage (File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(shapes);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException exce) {
            JOptionPane.showMessageDialog(this, "保存出错");
            exce.printStackTrace();
        }
    }
    /**
     * 从一个文件中读取图形信息，将其保存到程序中并重绘
     */
    public void readImage (File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            shapes = (ArrayList<Shape>) ois.readObject();
            repaint();
            ois.close();
            fis.close();
        } catch (IOException exce) {
            JOptionPane.showMessageDialog(this, "打开出错");
            exce.printStackTrace();
        } catch (ClassNotFoundException exce) {
            exce.printStackTrace();
        }
    }

    private class MouseHandler extends MouseAdapter {
        private int pressX;
        private int pressY;
        private int releasedX;
        private int releasedY;

        /**
         * 鼠标点击时执行的操作
         */
        public void mousePressed(MouseEvent mouseEvent) {
            switch (tool) {
                case OVAL:
                    currentShape = new Oval(fore, mouseEvent.getX(), mouseEvent.getY(), stroke, fill);
                    shapes.add(currentShape);
                    redoStack.clear();;
                    operationStack.push(new Operation(Operation.Type.ADD, currentShape));
                    break;
                case RECTANGLE:
                    currentShape = new Rectangle(fore, mouseEvent.getX(), mouseEvent.getY(), stroke, fill);
                    shapes.add(currentShape);
                    redoStack.clear();;
                    operationStack.push(new Operation(Operation.Type.ADD, currentShape));
                    break;
                case LINE:
                    pressX = x1 = mouseEvent.getX();
                    pressY = y1 = mouseEvent.getY();
                    currentShape = new LineSet(fore, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    redoStack.clear();;
                    operationStack.push(new Operation(Operation.Type.ADD, currentShape));
                    break;
                case SELECT:
                    pressX = x1 = mouseEvent.getX();
                    pressY = y1 = mouseEvent.getY();
                    if (cursor == cursors.MOVE) {
                        currentShape = find(mouseEvent.getX(), mouseEvent.getY());
                        if(delete) {
                            currentShape.setDeleted(true);
                            redoStack.clear();;
                            operationStack.push(new Operation(Operation.Type.DEL, currentShape));
                        }
                    }
                    break;
                case SEGMENT:
                    currentShape = new Line(fore, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    redoStack.clear();;
                    operationStack.push(new Operation(Operation.Type.ADD, currentShape));
                    break;
                case TEXT:
                    if(currentText == null)
                        break;
                    currentShape = new Text(fore, mouseEvent.getX(), mouseEvent.getY(), currentText, font);
                    shapes.add(currentShape);
                    redoStack.clear();;
                    operationStack.push(new Operation(Operation.Type.ADD, currentShape));
                    break;
                default:
                    break;
            }
            repaint();
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            if(tool == tools.SELECT) {
                releasedX = mouseEvent.getX();
                releasedY = mouseEvent.getY();
                redoStack.clear();;
                operationStack.push(new Operation(Operation.Type.MOV, currentShape, releasedX, releasedY, pressX, pressY));
            }
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        /**
         * 鼠标移动时执行的操作
         */
        public void mouseMoved(MouseEvent mouseEvent) {
            int x2 = mouseEvent.getX();
            int y2 = mouseEvent.getY();
            if(tool == tools.SELECT) {
                if (find(x2, y2) == null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//选择模式下如果找不到图形，就是默认鼠标指针
                    cursor = cursors.DEFAULT;
                } else {
                    currentShape = find(x2, y2);
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));//否则是移动鼠标指针
                    cursor = cursors.MOVE;
                }
            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));//非选择模式下一律为十字鼠标指针
                cursor = cursors.CROSS;
            }

        }
        /**
         * 鼠标拖拽时执行的操作
         */
        public void mouseDragged(MouseEvent mouseEvent) {
            if ((tool == tools.OVAL || tool == tools.RECTANGLE || tool == tools.SEGMENT) && currentShape != null) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                currentShape.setCurrentX(x);
                currentShape.setCurrentY(y);
                repaint();
            } else if (tool == tools.LINE) {
                int x2 = mouseEvent.getX();
                int y2 = mouseEvent.getY();
                Line l = new Line(fore, x1, y1, x2, y2, stroke);
                x1 = x2;
                y1 = y2;
                LineSet temp = (LineSet)currentShape;
                temp.addNew(l);
                repaint();
            } else if (tool == tools.SELECT) {
                if (cursor != cursors.MOVE) return;
                int x2 = mouseEvent.getX();
                int y2 = mouseEvent.getY();
                int dx = x2 - x1;
                int dy = y2 - y1;
                currentShape.moveShape(dx, dy);
                x1 = x2;
                y1 = y2;
                repaint();
            }

        }
    }
}