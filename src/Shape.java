import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

public abstract class Shape extends JLabel implements Serializable {
    protected Color color;
    protected int type;//0 oval 1 rect 2 line
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

    protected Shape(Color color1,int type1 ,int x, int y, float stroke1) {
        color = color1;
        type = type1;
        stroke = stroke1;
        nwX = startX = endX = x;
        nwY = startY = endY = y;
        deltaX = 0;
        deltaY = 0;
    }

    protected Shape(Color color1,int type1 ,int startX1, int startY1, int endX1, int endY1, float stroke1) {
        color = color1;
        type = type1;
        stroke = stroke1;
        startX = startX1;
        startY = startY1;
        endX = endX1;
        endY = endY1;
        deltaX = 0;
        deltaY = 0;
    }

    public abstract void draw(Graphics2D g2);

    public abstract boolean contain(int x, int y);

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
        endX = this.currentX;
        deltaX = endX - startX;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
        endY = this.currentY;
        deltaY = endY - startY;
        if (endX <= startX)
            nwX = endX;
        else
            nwX = startX;

        if (endY <= startY)
            nwY = endY;
        else
            nwY = startY;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void moveShape(int x, int y) {
        startX += x;
        nwX += x;
        endX += x ;
        startX += y;
        nwY += y;
        endY += y;
    }
}