import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text extends Shape {
    private String context;
    private Font font;
    public Text(Color color, int type ,int x, int y, String context, Font font) {
        super(color, type, x, y, 0);
        this.context = context;
        this.font = font;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(font);
        g2.drawString(context, nwX, nwY);
    }

    public boolean contain(int x, int y) {
        return false;
    }
}
