/**
 * 可以绘制图案的区域
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class PaintPanel extends JComponent implements Serializable {
    private ArrayList<Shape> shapes;
    private ArrayList<Line> lines;
    private Shape currentShape;
    private String currentText;
    public enum tools {OVAL, RECTANGLE, LINE, SEGMENT, SELECT, TEXT}//0 圆形 1 方形 2 自由画线 3 橡皮 4 选择 5 线段
    public enum cursors {DEFAULT, CROSS, MOVE}// 0 普通鼠标 1 十字架 2 移动
    private Font font;
    private tools tool;
    private Color fore;
    private float stroke;
    private cursors cursor;
    private int x1 = 0;
    private int y1 = 0;

    public PaintPanel() {
        tool = tools.OVAL;
        stroke = 3.0f;
        fore = Color.BLACK;
        font =  new Font("黑体", 0, 20);
        shapes = new ArrayList<>();
        lines = new ArrayList<>();
        currentShape = null;
        currentText = null;
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

    /**
     * 将存储的所有图形绘制出来
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Shape s : shapes)
            s.draw(g2);
        for (Line l : lines)
            l.draw(g2);
    }

    /**
     * 查找坐标(x,y)处是否有图形，如有，返回这个图形，否则返回null
     */
    public Shape find (int x, int y) {
        for (Shape r : shapes) {
            if (r.contain(x, y))
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
        lines.clear();
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
            oos.writeObject(lines);
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
            lines = (ArrayList<Line>) ois.readObject();
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
        /**
         * 鼠标点击时执行的操作
         */
        public void mousePressed(MouseEvent mouseEvent) {
            switch (tool) {
                case OVAL:
                    currentShape = new Oval(fore, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    break;
                case RECTANGLE:
                    currentShape = new Rectangle(fore, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    break;
                case LINE:
                    x1 = mouseEvent.getX();
                    y1 = mouseEvent.getY();
                    break;
                case SELECT:
                    x1 = mouseEvent.getX();
                    y1 = mouseEvent.getY();
                    if (cursor == cursors.MOVE) {
                        currentShape = find(mouseEvent.getX(), mouseEvent.getY());
                    }
                    break;
                case SEGMENT:
                    currentShape = new Line(fore, mouseEvent.getX(), mouseEvent.getY(), stroke);
                    shapes.add(currentShape);
                    break;
                case TEXT:
                    if(currentText == null)
                        break;
                    currentShape = new Text(fore, mouseEvent.getX(), mouseEvent.getY(), currentText, font);
                    shapes.add(currentShape);
                    break;
                default:
                    break;
            }
            repaint();
        }

        public void mouseClicked(MouseEvent mouseEvent) {

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
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//选择模式下如果找不到图形，就是默认形状
                    cursor = cursors.DEFAULT;
                } else {
                    currentShape = find(x2, y2);
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));//否则是移动形状
                    cursor = cursors.MOVE;
                }
            }
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
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
                lines.add(l);
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