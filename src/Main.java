import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(Frame.NORMAL);
        frame.setTitle("paint");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
}