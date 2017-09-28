import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;

public class MainFrame extends JFrame implements ActionListener{
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    private PaintPanel board;
    private ToolBar tool;
    public MainFrame() {
        board = new PaintPanel();
        tool = new ToolBar();
        tool.ovalButton.addActionListener(this);
        tool.rectangleButton.addActionListener(this);
        tool.lineButton.addActionListener(this);
        tool.eraserButton.addActionListener(this);
        tool.colorButton.addActionListener(this);
        tool.strokeBox.addActionListener(this);
        tool.helpButton.addActionListener(this);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(tool, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == tool.ovalButton) {
            board.setTool(0);
        } else if (e.getSource() == tool.rectangleButton) {
            board.setTool(1);
        } else if (e.getSource() == tool.lineButton) {
            board.setTool(2);
        } else if (e.getSource() == tool.eraserButton) {
            board.setTool(3);
        } else if (e.getSource() == tool.colorButton) {
            Color color = JColorChooser.showDialog(null, "调色板", Color.blue);
            board.setFore(color);
        } else if (e.getSource() == tool.strokeBox) {
            board.setStroke(tool.strokeBox.getSelectedIndex() + 1);
        } else if (e.getSource() == tool.helpButton) {
            new HelpDialog(this).setVisible(true);
        }
    }
}

class ToolBar extends JToolBar {
    public JButton ovalButton = new JButton("圆形");
    public String str[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    public JButton rectangleButton = new JButton("方形");
    public JButton lineButton = new JButton("画笔");
    public JButton eraserButton = new JButton("橡皮");
    public JComboBox strokeBox = new JComboBox(str);
    public JButton colorButton = new JButton("颜色");
    public JButton helpButton = new JButton("帮助");
    public ToolBar(){
        setFloatable(false);
        ovalButton.setToolTipText("画一个圆形");
        rectangleButton.setToolTipText("画一个方形");
        lineButton.setToolTipText("自由画线");
        eraserButton.setToolTipText("擦除");
        strokeBox.setToolTipText("选择线宽");
        colorButton.setToolTipText("选择颜色");
        helpButton.setToolTipText("帮助");
        this.setLayout(new GridLayout(1, 6));
        add(ovalButton);
        add(rectangleButton);
        add(lineButton);
        add(eraserButton);
        add(strokeBox);
        add(colorButton);
        add(helpButton);
    }
}