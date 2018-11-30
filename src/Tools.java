/**
 * 工具栏类
 */
import javax.swing.*;
import java.awt.*;

class Tools {
    public JButton openButton = new JButton("打开");
    public JButton saveButton = new JButton("保存");
    public JButton selectButton = new JButton("选择");
    public JButton ovalButton = new JButton("圆形");
    public String[] lineSize = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    public JButton rectangleButton = new JButton("方形");
    public JButton lineButton = new JButton("画笔");
    public JComboBox<String> strokeBox = new JComboBox<>(lineSize);
    public JButton colorButton = new JButton("颜色");
    public JButton segmentButton = new JButton("直线");
    public JButton textButton = new JButton("文字");
    public JButton clearButton = new JButton("清屏");
    public JTextField textField = new JTextField("在此输入文字并按回车确定");
    public JButton fontButton = new JButton("字体");
    public JButton undoButton = new JButton("撤销");
    public JButton redoButton = new JButton("重做");
    public JCheckBox fillCheckBox = new JCheckBox("填充");
    public JCheckBox deleteCheckBox = new JCheckBox("删除");


    public Tools() {
        openButton.setToolTipText("打开已保存的文件");
        saveButton.setToolTipText("保存文件");
        selectButton.setToolTipText("选择一个图形");
        ovalButton.setToolTipText("画一个圆形");
        rectangleButton.setToolTipText("画一个方形");
        lineButton.setToolTipText("自由画线");
        strokeBox.setToolTipText("选择线宽");
        colorButton.setToolTipText("选择颜色");
        segmentButton.setToolTipText("直线");
        textButton.setToolTipText("输入文字");
        clearButton.setToolTipText("清屏");
        fontButton.setToolTipText("修改字体");
        fillCheckBox.setToolTipText("是否填充颜色");
        deleteCheckBox.setToolTipText("删除");
        undoButton.setToolTipText("撤销上次的操作");
        redoButton.setToolTipText("重做撤销的操作");
        textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//        openButton.setPreferredSize(new Dimension(110,30));
//        saveButton.setPreferredSize(new Dimension(40,30));
//        selectButton.setPreferredSize(new Dimension(40,30));
//        ovalButton.setPreferredSize(new Dimension(40,30));
//        rectangleButton.setPreferredSize(new Dimension(40,30));
//        lineButton.setPreferredSize(new Dimension(40,30));
//        colorButton.setPreferredSize(new Dimension(40,30));
//        textButton.setPreferredSize(new Dimension(40,30));
//        segmentButton.setPreferredSize(new Dimension(40,30));
//        clearButton.setPreferredSize(new Dimension(40,30));
//        fontButton.setPreferredSize(new Dimension(40,30));
//        fillCheckBox.setPreferredSize(new Dimension(70, 30));
//        deleteCheckBox.setPreferredSize(new Dimension(60, 30));

//        historyMain.setLayout(new BoxLayout(historyMain,  BoxLayout.PAGE_AXIS));
//        JPanel historyoptions = new JPanel();
//        historyoptions.setLayout(new BorderLayout());
    }

}