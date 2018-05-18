import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileIO {
    private static JFileChooser saveFileChooser = new JFileChooser();
    private static JFileChooser openFileChooser = new JFileChooser();
    public static FileNameExtensionFilter savFilter = new FileNameExtensionFilter("SAV文件", "sav");
    public static FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG图像", "jpg");
    public FileIO() {
        saveFileChooser.setFileFilter(savFilter);
        saveFileChooser.setFileFilter(jpgFilter);
        openFileChooser.setFileFilter(savFilter);
    }
    public File saveFile(Component c) {
        saveFileChooser.showSaveDialog(c);
        File file = saveFileChooser.getSelectedFile();
        if(saveFileChooser.getFileFilter() == jpgFilter) {
            file = new File(file.getAbsoluteFile() + ".jpg");
        } else if (saveFileChooser.getFileFilter() == savFilter) {
            if(!file.getName().endsWith(".sav"))
                file = new File(file.getAbsoluteFile() + ".sav");
        }
        return file;

    }
    public File openFile(Component c) {
        openFileChooser.showOpenDialog(c);
        return openFileChooser.getSelectedFile();
    }
}
