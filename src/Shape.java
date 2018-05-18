/*
  所有图形的抽象基类
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

public abstract class Shape extends JLabel implements Serializable {
    protected Color color;
    protected int nwX = 0;
    protected int nwY = 0;
    protected int startX = 0;
    protected int startY = 0;
    protected int currentX = 0;
    protected int currentY = 0;
    protected int endX = 0;
    protected int endY = 0;
    protected int deltaX = 0;
    protected int deltaY = 0;
    protected float stroke = 0;

    protected Shape(Color color1, int x, int y, float stroke1) {
        color = color1;
        stroke = stroke1;
        nwX = startX = endX = x;
        nwY = startY = endY = y;
        deltaX = 0;
        deltaY = 0;
    }

    protected Shape(Color color1, int startX1, int startY1, int endX1, int endY1, float stroke1) {
        color = color1;
        stroke = stroke1;
        startX = startX1;
        startY = startY1;
        endX = endX1;
        endY = endY1;
        deltaX = 0;
        deltaY = 0;
    }
    /**
     * 绘制这个图形的方法
     * @param g2 从PaintPanel里传来的Graphics2D对象
     */
    public abstract void draw(Graphics2D g2);

    /**
     * 判断这个图形是否包括某坐标
     * @param x x坐标
     * @param y y坐标
     */
    public abstract boolean contain(int x, int y);


    /**
     * 创建图形时通过目前的X，修改nwX、endX、deltaX
     * @param currentX 目前的X
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
        endX = this.currentX;
        deltaX = endX - startX;
        if (endX <= startX)
            nwX = endX;
        else
            nwX = startX;
    }

    public int getCurrentX() {
        return currentX;
    }

    /**
     * 创建图形时通过目前的Y，修改nwY、endY、deltaY
     * @param currentY 目前的Y
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
        endY = this.currentY;
        deltaY = endY - startY;
        if (endY <= startY)
            nwY = endY;
        else
            nwY = startY;
    }

    public int getCurrentY() {
        return currentY;
    }

    /**
     * 选中此图形后，通过移动的变化量x、y，修改各参数
     */
    public void moveShape(int x, int y) {
        startX += x;
        nwX += x;
        endX += x ;
        startY += y;
        nwY += y;
        endY += y;
    }
}