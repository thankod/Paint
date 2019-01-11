import javax.swing.*;

public class MenuBar extends JMenuBar {
    public JMenu fileMenu = new JMenu("文件(F)");
    public JMenu helpMenu = new JMenu("帮助(H)");
    public JMenuItem newItem = new JMenuItem("新建");
    public JMenuItem saveItem = new JMenuItem("保存");
    public JMenuItem openItem = new JMenuItem("打开");
    public JMenuItem aboutItem = new JMenuItem("关于");

    public MenuBar() {
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.setMnemonic('F');
        helpMenu.add(aboutItem);
        helpMenu.setMnemonic('H');
        add(fileMenu);
        add(helpMenu);
    }
}
