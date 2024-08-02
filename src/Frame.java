import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {

    public Frame() {

        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Computer");

        for (File root : File.listRoots()) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileNode(root));
            rootTreeNode.add(rootNode);
            addNodes(rootNode, root);
        }

        DefaultTreeModel treeModel = new DefaultTreeModel(rootTreeNode);

        JTree tree = new JTree(treeModel);

        // Allows only a single node to be selected at one moment
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.setFocusable(false);

        this.add(new JScrollPane(tree));

        JLabel label = new JLabel();
        this.add(label, BorderLayout.SOUTH);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null || selectedNode.getUserObject() == null) return;

                FileNode fileNode = (FileNode) selectedNode.getUserObject();

                label.setText("Selection is: " + e.getPath());

                if (selectedNode.getChildCount() == 0) {
                    addNodes(selectedNode, fileNode.getFile());
                    treeModel.reload(selectedNode);
                }

            }
        });

        this.configure();
    }

    private void addNodes(DefaultMutableTreeNode parentNode, File parentFile) {
        File[] files = parentFile.listFiles();
        if (files == null) return;
        for (File file : files) parentNode.add(new DefaultMutableTreeNode(new FileNode(file)));
    }

    private void configure() {
        this.setSize(500, 625);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}