/* ElementTree.java
 * See: http://stackoverflow.com/questions/9761843/adding-a-swing-tree-selection-listener-with-custom-tree-model
 * Tested in XMLSwingApp project
 * (ElementModel See CH."Creating a Data Model" in oracle:tree tutorial (GenealogyExample.java))
 */
package siima.app.model.tree;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ElementTree extends JTree {
	   	private ElementModel treemodel;

	    public ElementTree(ElementModel treemodel){ 
	    	super(treemodel);
	    	this.treemodel =treemodel;
	        // TreeSelectionListener 
	        this.addTreeSelectionListener(new TreeSelectionListener() {
	            public void valueChanged(TreeSelectionEvent e) {
	            	ElementNode node = (ElementNode) e
	                        .getPath().getLastPathComponent();
	            	if(node!=null){
	            		treemodel.setLastSelectedNode(node);
	            	}
	                System.out.println("--ElementTree:TreeSelectionListener: You selected " + node);
	            }
	        });
	        
	        getSelectionModel().setSelectionMode(
	                TreeSelectionModel.SINGLE_TREE_SELECTION);
	        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	        //Icon personIcon = null;
	        Icon openFolderIcon = new ImageIcon("./configure/images/basic_folder.png");
	        //Icon closedFolderIcon = new ImageIcon("./configure/images/closed_folder.png");
	        Icon leafIcon = new ImageIcon("./configure/images/leaf_folder.png");
	        renderer.setLeafIcon(leafIcon);
	        renderer.setClosedIcon(openFolderIcon);
	        renderer.setOpenIcon(openFolderIcon);
	        setCellRenderer(renderer);
	    }
	    

	    
	    /**
	     * Get the selected item in the tree, and call showAncestor with this
	     * item on the model.
	     
	    public void showAncestor(boolean b) {
	        Object newRoot = null;
	        TreePath path = getSelectionModel().getSelectionPath();
	        if (path != null) {
	            newRoot = path.getLastPathComponent();
	        }
	        ((GenealogyModel)getModel()).showAncestor(b, newRoot);
	    }
	    */
}
