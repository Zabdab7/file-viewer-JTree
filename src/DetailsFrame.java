import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class DetailsFrame extends JFrame {

    private final FileNode node;
    private final JTextArea infoArea;

    public DetailsFrame(FileNode node) {

        super("Details for:" + node.getFile().getName());

        this.node = node;

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText(getDetails());

        this.add(new JScrollPane(this.infoArea));

        this.configure();
    }

    public String getDetails() {
        StringBuilder sb = new StringBuilder();

        File file = node.getFile();

        sb.append("Name: ").append(file.getName()).append("\n");
        sb.append("Path: ").append(file.getPath()).append("\n");
        sb.append("Size: ").append(file.length()).append(" bytes").append("\n");
        sb.append("Readable: ").append(file.canRead()).append("\n");
        sb.append("Writable: ").append(file.canWrite()).append("\n");
        sb.append("Executable: ").append(file.canExecute()).append("\n");
        sb.append("Is Directory: ").append(file.isDirectory()).append("\n");
        sb.append("Last Modified: ").append(file.lastModified()).append("\n");
        return sb.toString();

    }

    private void configure() {
        this.setSize(300, 400);

        this.setMinimumSize(new Dimension(300, 450));
        this.setMaximumSize(new Dimension(400, 550));

        EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
        this.getRootPane().setBorder(emptyBorder);

        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}
