import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text extends Shape {
    private String context;
    private Font font;
    public Text(Color color, int x, int y, String context, Font font) {
        super(color, x, y, 0, true);
        this.context = context;
        this.font = font;
    }
    public void draw(Graphics2D g2) {
        if(isDeleted) return;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setFont(font);
        g2.drawString(context, nwX, nwY);
    }

    public boolean contain(int x, int y) {
        Rectangle2D r1 = new Rectangle2D.Double(nwX, nwY - font.getSize(), Math.abs( (font.getSize()) * context.length()), Math.abs(font.getSize()));
        return r1.contains(x, y);
    }

}
