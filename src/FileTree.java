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
import java.io.IOException;

public class FileTree extends JTree {

    String crumbs = "";
    private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Computer");
    private DefaultTreeModel treeModel;
    private FileNode selectedNode;

    public FileTree() {

        // TODO = Change Icon to work more cleanly
        // TODO = Make it so I don't have to click twice

        for (File root : File.listRoots()) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileNode(root));
            rootTreeNode.add(rootNode);
            addNodes(rootNode, root);
        }

        treeModel  = new DefaultTreeModel(rootTreeNode);

        this.setModel(treeModel);

        // Allows only a single node to be selected at one moment
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.setFocusable(false);

    }

    public void openChildrenNodes(TreeSelectionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.getLastSelectedPathComponent();
        if (selectedNode == null || selectedNode.getUserObject() instanceof java.lang.String) return;

        this.selectedNode = (FileNode) selectedNode.getUserObject();

        crumbs = "Selection is: " + e.getPath();

        if (selectedNode.getChildCount() == 0) {
            addNodes(selectedNode, this.selectedNode.getFile());
            treeModel.reload(selectedNode);
        }
    }

    public void makePopup(MouseEvent e, FileNode node) {
        if (SwingUtilities.isRightMouseButton(e)) {
            int row = this.getClosestRowForLocation(e.getX(), e.getY());
            this.setSelectionRow(row);
            showPopup(e.getComponent(), e.getX(), e.getY(), node);
        }
    }

    private void addNodes(DefaultMutableTreeNode parentNode, File parentFile) {
        File[] files = parentFile.listFiles();
        if (files == null) return;
        for (File file : files) parentNode.add(new DefaultMutableTreeNode(new FileNode(file)));
    }

    private void showPopup(Component component, int x, int y, FileNode node) {
        // TODO = Make this code look nicer especially after more code is added

        JPopupMenu popupMenu = new JPopupMenu();

        // Details Menu Item
        JMenuItem detailsMenuItem = new JMenuItem("1. Details");

        // Open a frame to display details only if it has no children
        detailsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(node.isFolder()) {
                    DetailsFrame df = new DetailsFrame(node);
                }
            }
        });

        JMenu openWithMenu = new JMenu("2. Open With");

        openWithMenu.add(new JMenuItem("Notepad") {{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(node.isFolder()) {
                        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", node.getFile().getPath());
                        try {
                            pb.start();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }});

        popupMenu.add(detailsMenuItem);
        popupMenu.add(openWithMenu);

        popupMenu.show(component, x, y);
    }

    public String getCrumbs() {
        return crumbs;
    }

    public FileNode getSelectedNode() {
        return this.selectedNode;
    }
}
