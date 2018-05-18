/*
  程序的主窗口
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JFrame implements Serializable {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    private PaintPanel board;
    private ToolBar tool;
    private FontDialog fontDialog;
    private FileIO fileIO;

    public MainFrame() {
        board = new PaintPanel();
        tool = new ToolBar();
        fontDialog = new FontDialog(this);
        fileIO = new FileIO();
        tool.openButton.addActionListener(actionEvent -> board.readImage(fileIO.openFile(this)));
        tool.saveButton.addActionListener(actionEvent -> {
            File file = fileIO.saveFile(this);
            if(file.getName().endsWith(".jpg")) {
                try {
                    ImageIO.write(board.getImage(), "jpg", file);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "保存出错！请重试！");
                    e.printStackTrace();
                }
            } else if(file.getName().endsWith(".sav")) {
                board.writeImage(file);
            }
        });
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
        tool.fontButton.addActionListener(actionEvent -> {
            fontDialog.setVisible(true);
            board.setFont(fontDialog.getFont());
        });

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(tool, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
    }
}

