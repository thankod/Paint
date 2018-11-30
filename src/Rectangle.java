import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Shape {

    public Rectangle(Color color, int x, int y, float stroke1, boolean isFilled) {
        super(color, x, y, stroke1, isFilled);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isDeleted) return;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        if(isFilled) {
            g2.fillRect(nwX, nwY, Math.abs(deltaX), Math.abs(deltaY));
        } else {
            g2.drawRect(nwX, nwY, Math.abs(deltaX), Math.abs(deltaY));
        }
    }



    /**
     * 通过Rectangle2D类的contains函数判断坐标(x,y)是否在此Rectangle中
     */
    public boolean contain(int x, int y) {
        Rectangle2D r1 = new Rectangle2D.Double(nwX, nwY, Math.abs(deltaX), Math.abs(deltaY));
        Rectangle2D r2 = new Rectangle2D.Double(nwX + stroke, nwY + stroke, Math.abs(deltaX) - 2 * stroke, Math.abs(deltaY) - 2 * stroke);
        if(isFilled) {
            return r1.contains(x, y);
        } else {
            return r1.contains(x, y) && !r2.contains(x, y);
        }
    }


}