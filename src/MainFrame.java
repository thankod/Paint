/*
  程序的主窗口
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.JOptionPane;


public class MainFrame extends JFrame implements Serializable {
    private PaintPanel board;
    private Tools tool;
    private JToolBar mainToolBar;
    private MenuBar menuBar;
    private FontDialog fontDialog;
    private FileIO fileIO;


    public MainFrame() {
        board = new PaintPanel();;
        fontDialog = new FontDialog(this);
        fileIO = new FileIO();
        tool = new Tools();
        mainToolBar = new JToolBar();
        menuBar = new MenuBar();
        mainToolBar.setSize(new Dimension(700, 30));
        this.getContentPane().setBackground(java.awt.Color.white);//将背景设为白色

        tool.openButton.addActionListener(actionEvent -> board.readImage(fileIO.openFile(this)));
        tool.saveButton.addActionListener(actionEvent -> fileIO.saveFile(this, board));

        tool.selectButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.SELECT));
        tool.ovalButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.OVAL));
        tool.rectangleButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.RECTANGLE));
        tool.lineButton.addActionListener(actionEvent -> board.setTool(PaintPanel.tools.LINE));
        tool.colorButton.addActionListener(actionEvent ->  board.setFore(JColorChooser.showDialog(null, "调色板", Color.BLACK)));
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
        tool.fillCheckBox.addActionListener(actionEvent -> board.setFill(tool.fillCheckBox.isSelected()));
        tool.deleteCheckBox.addActionListener(actionEvent -> board.setDelete(tool.deleteCheckBox.isSelected()));

        tool.undoButton.addActionListener(actionEvent -> board.undo());
        tool.redoButton.addActionListener(actionEvent -> board.redo());

        /*
          关闭窗口时检查是否已经保存
         */
        addWindowListener(new MyWindowAdapter());






        menuBar.newItem.addActionListener(actionEvent -> {
            if(board.isSaved()) {
                board.clear();
            } else {
                int op = JOptionPane.showConfirmDialog(null, "您还没有保存，是否保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                if(op == JOptionPane.YES_OPTION) {
                    fileIO.saveFile(null, board);
                    board.clear();
                } else if(op == JOptionPane.NO_OPTION) {
                    board.clear();
                }
            }
        });
        menuBar.saveItem.addActionListener(actionEvent -> fileIO.saveFile(this, board));
        menuBar.openItem.addActionListener(actionEvent -> board.readImage(fileIO.openFile(this)));
        menuBar.aboutItem.addActionListener(actionEvent ->
                JOptionPane.showMessageDialog(null, "本画图软件由东北大学计算机1609谢天 林智超 郑智佳共同完成", "关于", JOptionPane.INFORMATION_MESSAGE));
        menuBar.fontItem.addActionListener(actionEvent -> {
            fontDialog.setVisible(true);
            board.setFont(fontDialog.getFont());
        });
        menuBar.colorItem.addActionListener(actionEvent ->  board.setFore(JColorChooser.showDialog(null, "调色板", Color.BLACK)));
        menuBar.redoItem.addActionListener(actionEvent -> board.redo());
        menuBar.undoItem.addActionListener(actionEvent -> board.undo());



        setSize(1000, 1000);

        JToolBar barSaveOpen = new JToolBar();
        JToolBar barChoose = new JToolBar();
        JToolBar barShape = new JToolBar();
        JToolBar barText = new JToolBar();
        barSaveOpen.add(tool.saveButton);
        barSaveOpen.add(tool.openButton);
        barSaveOpen.add(tool.undoButton);
        barSaveOpen.add(tool.redoButton);

        barChoose.add(tool.selectButton);
        barChoose.add(tool.clearButton);
        barChoose.add(tool.deleteCheckBox);

        barShape.add(tool.colorButton);
        barShape.add(tool.lineButton);
        barShape.add(tool.rectangleButton);
        barShape.add(tool.ovalButton);
        barShape.add(tool.segmentButton);
        barShape.add(tool.strokeBox);
        barShape.add(tool.fillCheckBox);

        barText.add(tool.textButton);
        barText.add(tool.fontButton);
        barText.add(tool.textField);


        mainToolBar.add(barSaveOpen);
        mainToolBar.add(barChoose);
        mainToolBar.add(barShape);
        mainToolBar.add(barText);

        setJMenuBar(menuBar);
        add(board, BorderLayout.CENTER);
        add(mainToolBar, BorderLayout.NORTH);
    }

    private class MyWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            super.windowClosing(windowEvent);
            if(board.isSaved()) {
                System.exit(0);
            } else {
                int op = JOptionPane.showConfirmDialog(null, "您还没有保存，是否保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                if(op == JOptionPane.YES_OPTION) {
                    fileIO.saveFile(null, board);
                    System.exit(0);
                } else if(op == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }

        }
    }
}

