/*
  实现选择字体功能的弹出窗口
 */
import javax.swing.*;
import java.awt.*;

public class FontDialog extends JDialog {

    private String[] sizeList = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "18", "20", "22", "24", "26", "28", "30"};
    private String[] fontList = {"宋体", "黑体", "楷体"};
    private String[] shapeList = {"通常", "加粗", "斜体"};
    private JComboBox<String> fontBox = new JComboBox<>(fontList);
    private JComboBox<String> sizeBox = new JComboBox<>(sizeList);
    private JComboBox<String> shapeBox = new JComboBox<>(shapeList);

    public FontDialog(MainFrame frame) {
        super(frame, "修改字体", true);
        setAlwaysOnTop(true);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 3));
        add(fontBox);
        add(sizeBox);
        add(shapeBox);
        fontBox.setSelectedIndex(1);
        sizeBox.setSelectedIndex(10);
    }

    /**
     * 返回目前选择的字体
     */
    public Font getFont() {
        return new Font(fontList[fontBox.getSelectedIndex()], shapeBox.getSelectedIndex(), Integer.valueOf(sizeList[sizeBox.getSelectedIndex()]));
    }
}
