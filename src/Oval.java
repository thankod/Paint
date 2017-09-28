import java.awt.*;

public class Oval extends Shape {

    public Oval(Color color,int type ,int x, int y, float stroke1) {
        super(color, type, x, y, stroke1);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(stroke));
        if (deltaX >= 0 && deltaY >= 0)
            g2.drawOval(startX, startY, deltaX, deltaY);
        if (deltaX < 0 && deltaY >= 0)
            g2.drawOval(startX + deltaX, startY, -deltaX, deltaY);
        if (deltaX < 0 && deltaY < 0)
            g2.drawOval(startX + deltaX, startY + deltaY, -deltaX, -deltaY);
        if (deltaX >= 0 && deltaY < 0)
            g2.drawOval(startX, startY + deltaY, deltaX, -deltaY);
    }
}
