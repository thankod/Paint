import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileIO {
    private static JFileChooser saveFileChooser = new JFileChooser();
    private static JFileChooser openFileChooser = new JFileChooser();
    private static FileNameExtensionFilter savFilter = new FileNameExtensionFilter("SAV文件", "sav");
    private static FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG图像", "jpg");
    public FileIO() {
        saveFileChooser.setFileFilter(savFilter);
        saveFileChooser.setFileFilter(jpgFilter);
        openFileChooser.setFileFilter(savFilter);
    }
    public File saveFile(Component c, PaintPanel board) {
        saveFileChooser.showSaveDialog(c);
        File file = saveFileChooser.getSelectedFile();
        if(saveFileChooser.getFileFilter() == jpgFilter) {
            file = new File(file.getAbsoluteFile() + ".jpg");
            try {
                ImageIO.write(board.getImage(), "jpg", file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(c, "保存出错！请重试！");
                e.printStackTrace();
            }
        } else if (saveFileChooser.getFileFilter() == savFilter) {
            if(!file.getName().endsWith(".sav")) {
                file = new File(file.getAbsoluteFile() + ".sav");
                board.writeImage(file);
            }
        }
        return file;

    }
    public File openFile(Component c) {
        openFileChooser.showOpenDialog(c);
        return openFileChooser.getSelectedFile();
    }
}
