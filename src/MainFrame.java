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
        tool.selectButton.addActionListener(this);
        tool.ovalButton.addActionListener(this);
        tool.rectangleButton.addActionListener(this);
        tool.lineButton.addActionListener(this);
        tool.colorButton.addActionListener(this);
        tool.strokeBox.addActionListener(this);
        tool.segmentButton.addActionListener(this);
        tool.textButton.addActionListener(this);
        tool.clearButton.addActionListener(this);
        tool.textField.addActionListener(this);
        tool.fontButton.addActionListener(this);
        saveFileChooser.setFileFilter(savFilter);
        saveFileChooser.setFileFilter(jpgFilter);
        openFileChooser.setFileFilter(savFilter);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(tool, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == tool.ovalButton) {
            board.setTool(PaintPanel.tools.OVAL);
        } else if (e.getSource() == tool.rectangleButton) {
            board.setTool(PaintPanel.tools.RECTANGLE);
        } else if (e.getSource() == tool.lineButton) {
            board.setTool(PaintPanel.tools.LINE);
        } else if (e.getSource() == tool.colorButton) {
            board.setFore(JColorChooser.showDialog(null, "调色板", Color.blue));
        } else if (e.getSource() == tool.strokeBox) {
            board.setStroke(tool.strokeBox.getSelectedIndex() + 1);
        } else if (e.getSource() == tool.segmentButton) {
            board.setTool(PaintPanel.tools.SEGMENT);
        } else if (e.getSource() == tool.selectButton) {
            board.setTool(PaintPanel.tools.SELECT);
        } else if (e.getSource() == tool.textButton) {
            board.setCurrentText(tool.textField.getText());
            board.setTool(PaintPanel.tools.TEXT);
        } else if (e.getSource() == tool.textField) {
            board.setCurrentText(tool.textField.getText());
        } else if (e.getSource() == tool.clearButton) {
            board.clear();
        } else if (e.getSource() == tool.fontButton) {
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

