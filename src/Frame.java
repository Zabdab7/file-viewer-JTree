import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
// import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {

    public Frame() {

        JTree tree = new JTree(File.listRoots());

        tree.setFocusable(false);

        this.add(new JScrollPane(tree));

        JLabel label = new JLabel();
        this.add(label, BorderLayout.SOUTH);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                label.setText("Selection is: " + e.getPath());
            }
        });

        this.configure();
    }

    private void configure() {
        this.setSize(500, 625);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
