import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Oval extends Shape {

    public Oval(Color color,int type ,int x, int y, float stroke1) {
        super(color, type, x, y, stroke1);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawOval(nwX, nwY, Math.abs(deltaX), Math.abs(deltaY));
    }

    public boolean contain(int x, int y) {
        Ellipse2D e1 = new Ellipse2D.Double(nwX - stroke, nwY - stroke, Math.abs(deltaX) + 2 * stroke, Math.abs(deltaY) + 2 * stroke);
        Ellipse2D e2 = new Ellipse2D.Double(nwX + stroke, nwY + stroke, Math.abs(deltaX) - 2 * stroke, Math.abs(deltaY) - 2 * stroke);
        return (e1.contains(x, y) && !e2.contains(x, y));
    }

}