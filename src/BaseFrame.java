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

    // TODO = Shift variables from constructor into member variables
    private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Computer");
    private DefaultTreeModel treeModel;
    private JTree tree;
    private JLabel label = new JLabel();;

    public BaseFrame() {

        super("Zayan's File Explorer");

        for (File root : File.listRoots()) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileNode(root));
            rootTreeNode.add(rootNode);
            addNodes(rootNode, root);
        }

        this.treeModel = new DefaultTreeModel(rootTreeNode);

        this.tree = new JTree(treeModel);

        // Allows only a single node to be selected at one moment
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.tree.setFocusable(false);

        this.add(new JScrollPane(this.tree));

        this.add(this.label, BorderLayout.SOUTH);

        this.tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null || selectedNode.getUserObject() instanceof java.lang.String) return;

                FileNode node = (FileNode) selectedNode.getUserObject();

                label.setText("Selection is: " + e.getPath());

                if (selectedNode.getChildCount() == 0) {
                    addNodes(selectedNode, node.getFile());
                    treeModel.reload(selectedNode);
                }

                tree.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                            tree.setSelectionRow(row);
                            showPopup(e.getComponent(), e.getX(), e.getY(), node);
                        }
                    }
                });

            }
        });



        this.configure();
    }

    private void addNodes(DefaultMutableTreeNode parentNode, File parentFile) {
        File[] files = parentFile.listFiles();
        if (files == null) return;
        for (File file : files) parentNode.add(new DefaultMutableTreeNode(new FileNode(file)));
    }

    private void showPopup(Component component, int x, int y, FileNode node) {
        // TODO = Possibly make a MenuItem class to account for more items than just the DetailsFrame

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem detailsMenuItem = new JMenuItem("1. Details");

        popupMenu.add(detailsMenuItem);

        detailsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a frame to display details only if it has no children
                if(node.getFile().listFiles() == null) {
                    DetailsFrame df = new DetailsFrame(node);
                }
            }
        });

        popupMenu.show(component, x, y);
    }

    private void configure() {
        this.setSize(500, 625);
        this.setResizable(false);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}