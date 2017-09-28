import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(Color color, int type , int x, int y, float stroke1) {
        super(color, type, x, y, stroke1);
    }
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        if (deltaX >= 0 && deltaY >= 0)
            g2.drawRect(startX, startY, deltaX, deltaY);
        if (deltaX < 0 && deltaY >= 0)
            g2.drawRect(startX + deltaX, startY, -deltaX, deltaY);
        if (deltaX < 0 && deltaY < 0)
            g2.drawRect(startX + deltaX, startY + deltaY, -deltaX, -deltaY);
        if (deltaX >= 0 && deltaY < 0)
            g2.drawRect(startX, startY + deltaY, deltaX, -deltaY);
    }
}
