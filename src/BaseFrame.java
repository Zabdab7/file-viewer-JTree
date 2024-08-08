import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class BaseFrame extends JFrame {

    FileTree tree = new FileTree();
    private JLabel label = new JLabel(tree.getCrumbs());

    public BaseFrame() {

        super("Zayan's File Explorer");

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                tree.openChildrenNodes(treeSelectionEvent);

                tree.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent mouseEvent) {
                        tree.makePopup(mouseEvent, tree.getSelectedNode());
                    }
                });
                label.setText(tree.getCrumbs());
            }
        });

        this.add(new JScrollPane(this.tree));

        this.add(this.label, BorderLayout.SOUTH);

        this.configure();
    }

    private void configure() {
        this.setResizable(false);
        this.setVisible(true);
        this.setSize(500, 625);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }
}