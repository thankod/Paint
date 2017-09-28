import java.awt.*;

public class Line extends Shape {
    protected Line(Color color1, int type1, int startX1, int startY1, int endX1, int endY1, float stroke1) {
        super(color1, type1, startX1, startY1, endX1, endY1, stroke1);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawLine(startX, startY, endX, endY);
    }
}


