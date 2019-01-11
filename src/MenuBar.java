import javax.swing.*;

public class MenuBar extends JMenuBar {
    public JMenu fileMenu = new JMenu("文件(F)");
    public JMenu editMenu = new JMenu("编辑(E)");
    public JMenu helpMenu = new JMenu("帮助(H)");
    public JMenuItem formMenu = new JMenu("格式(O)");
    public JMenuItem newItem = new JMenuItem("新建");
    public JMenuItem saveItem = new JMenuItem("保存");
    public JMenuItem openItem = new JMenuItem("打开");
    public JMenuItem aboutItem = new JMenuItem("关于");
    public JMenuItem undoItem = new JMenuItem("撤销");
    public JMenuItem redoItem = new JMenuItem("重做");
    public JMenuItem fontItem = new JMenuItem("字体");
    public JMenuItem colorItem = new JMenuItem("颜色");

    public MenuBar() {
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.setMnemonic('F');
        helpMenu.add(aboutItem);
        helpMenu.setMnemonic('H');
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.setMnemonic('E');
        formMenu.add(fontItem);
        formMenu.add(colorItem);
        formMenu.setMnemonic('O');
        add(fileMenu);
        add(editMenu);
        add(formMenu);
        add(helpMenu);
    }
}
