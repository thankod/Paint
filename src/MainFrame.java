/**
 * 程序的主窗口
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JFrame implements ActionListener, Serializable {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    private PaintPanel board;
    private ToolBar tool;
    private FontDialog fontDialog;
    private JFileChooser saveFileChooser = new JFileChooser();
    private JFileChooser openFileChooser = new JFileChooser();
    private FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG图像", "jpg");
    private FileNameExtensionFilter savFilter = new FileNameExtensionFilter("SAV文件", "sav");
    public MainFrame() {
        board = new PaintPanel();
        tool = new ToolBar();
        fontDialog = new FontDialog(this);
        tool.openButton.addActionListener(this);
        tool.saveButton.addActionListener(this);
        tool.selectButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.SELECT));
        tool.ovalButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.OVAL));
        tool.rectangleButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.RECTANGLE));
        tool.lineButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.LINE));
        tool.colorButton.addActionListener(actionEvent ->  board.setFore(JColorChooser.showDialog(null, "调色板", Color.blue)));
        tool.strokeBox.addActionListener(actionEvent -> board.setStroke(tool.strokeBox.getSelectedIndex() + 1));
        tool.segmentButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.SEGMENT));
        tool.textButton.addActionListener(actionEvent -> {
            board.setCurrentText(tool.textField.getText());
            board.setTool(PaintPanel.tools.TEXT);
        });
        tool.clearButton.addActionListener(actionEvent -> board.clear());
        tool.textField.addActionListener(actionEvent -> board.setCurrentText(tool.textField.getText()));
        tool.fontButton.addActionListener(this);
        saveFileChooser.setFileFilter(savFilter);
        saveFileChooser.setFileFilter(jpgFilter);
        openFileChooser.setFileFilter(savFilter);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(tool, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
    }
    /**
     * 根据选择的控件执行对应的操作
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tool.fontButton) {
            fontDialog.setVisible(true);
            board.setFont(fontDialog.getFont());
        } else if (e.getSource() == tool.openButton) {
            openFileChooser.showOpenDialog(this);
            File file = openFileChooser.getSelectedFile();
            board.readImage(file);
        } else if (e.getSource() == tool.saveButton) {
            saveFileChooser.showSaveDialog(this);
            File file = saveFileChooser.getSelectedFile();
            try {
                if(saveFileChooser.getFileFilter() == jpgFilter) {
                    file = new File(file.getAbsoluteFile() + ".jpg");
                    ImageIO.write(board.getImage(), "jpg", file);
                } else if (saveFileChooser.getFileFilter() == savFilter) {
                    if(!file.getName().endsWith(".sav"))
                        file = new File(file.getAbsoluteFile() + ".sav");
                    board.writeImage(file);
                }
            } catch (IOException exce) {
                JOptionPane.showMessageDialog(this, "保存出错");
                exce.printStackTrace();
            }
        }
    }
}

