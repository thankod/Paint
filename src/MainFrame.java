/*
  程序的主窗口
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainFrame extends JFrame implements Serializable {
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    private PaintPanel board;
    private Tools tool;
    private JToolBar mainToolBar;

    private FontDialog fontDialog;
    private FileIO fileIO;
    //private JLabel labelNow;




    public MainFrame() {
        board = new PaintPanel();;
        fontDialog = new FontDialog(this);
        fileIO = new FileIO();
        tool = new Tools();
        mainToolBar = new JToolBar();
        mainToolBar.setSize(new Dimension(700, 30));


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
        tool.fillCheckBox.addActionListener(actionEvent -> board.setFill(tool.fillCheckBox.isSelected()));
        tool.deleteCheckBox.addActionListener(actionEvent -> board.setDelete(tool.deleteCheckBox.isSelected()));

        tool.undoButton.addActionListener(actionEvent -> board.undo());
        tool.redoButton.addActionListener(actionEvent -> board.redo());

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

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


        add(mainToolBar, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);


    }
}

