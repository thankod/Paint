import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {
    private static final Font font = new Font("微软雅黑", 0, 20);
    private JLabel label;
    private JPanel panel;
    public HelpDialog(MainFrame frame) {
        super(frame, "帮助", true);
        setBounds(200,220,200,200);
        panel = new JPanel();
        label = new JLabel("用来学习swing，熟悉java");
        label.setFont(font);
        panel.add(label);
        add(panel);
    }
}
