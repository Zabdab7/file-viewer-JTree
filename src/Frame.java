import javax.swing.JFrame;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {
        frameConfig();
    }

    public void frameConfig() {
        this.setSize(Frame.getScreenSize());
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

}
