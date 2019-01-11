/*
  普通线类，继承自Shape类，包含了很多Line
 */
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class LineSet extends Shape {
    private ArrayList<Line> lines;

    protected LineSet(Color color1, int startX1, int startY1, int endX1, int endY1, float stroke1) {
        super(color1, startX1, startY1, endX1, endY1, stroke1, false);
        lines = new ArrayList<>();
    }

    protected LineSet(Color color, int x, int y, float stroke1) {
        super(color, x, y, stroke1, false);
        lines = new ArrayList<>();
    }


    public void draw(Graphics2D g2) {
        if(isDeleted) return;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        for(Line l : lines) {
            l.draw(g2);
        }
    }

    public void addNew(Line l) {
        lines.add(l);
    }

    public boolean contain(int x, int y) { return false; }

}