package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;

/** * @author  杨永钦
 E-mail:
@date ：2016-8-21 下午08:20:41
@version 1.0   * @since    */
public class TreePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -8290104788613064628L;

	// 树Map用于缓存，生成树
	private HashMap<String, DefaultMutableTreeNode> treeMap = new HashMap<String, DefaultMutableTreeNode>();

	// 树的根节点
	private DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("所有商品");;

	private JTree tree;

	List<Record> list;

	/**
	 * @Fields nodeIdfield : 节点ID
	 */
	private String nodeIdfield;

	/**
	 * @Fields parentfield : 父节点ID
	 */
	private String parentfield;

	/**
	 * @Fields nodeName : 显示名称
	 */
	private String nodeName;
	/**
	 * @Fields valueName : 值名称
	 */
	private String valueName;

	public TreePanel() {
		tree = new JTree(topNode);
		this.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(tree);
		tree.setVisibleRowCount(30);
		// tree.setRootVisible(false);
		this.add(pane, BorderLayout.CENTER);
	}

	public TreePanel(List<Record> list, String nodeIdfield, String parentfield, String nodeName,String valueName) {
		this();
		this.list = list;
		this.nodeIdfield = nodeIdfield;
		this.parentfield = parentfield;
		this.nodeName = nodeName;
		this.valueName=valueName;
		// topNode= new DefaultMutableTreeNode("所有商品");
		init();
	}

	public void updateTreeUI(List<Record> list, String nodeIdfield, String parentfield, String nodeName,String valueName) {
		this.list = list;
		this.nodeIdfield = nodeIdfield;
		this.parentfield = parentfield;
		this.nodeName = nodeName;
		this.valueName=valueName;
		init();
		updateUI();
	}

	private void init() {
		topNode.removeAllChildren();
		DefaultMutableTreeNode treeNodedtl = null;
		for (int i = 0; i < list.size(); i++) {
			Record vo = list.get(i);
			// 如果上级分类是空。直接添加到根目录上
			if (vo.getField(parentfield) == null || vo.getField(parentfield).isNull()
					|| "0".equals(vo.getField(parentfield).getAsObject())) {
				// 判断当前ID是否存在，因为子节点的父节点不存在时候，会构造一个父节点ID，但是并未显示正确的值
				if (!treeMap.containsKey(vo.getField(nodeIdfield).getAsObject())) {
					treeNodedtl = new DefaultMutableTreeNode();
					CodeValue lablevalue = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					treeNodedtl.setUserObject(lablevalue);// 节点赋值
					topNode.add(treeNodedtl);
				} else {
					// 如果已经存在，说明是子节点在前生成的父节点
					treeNodedtl = treeMap.get(vo.getField(nodeIdfield).getAsObject());
					CodeValue codeVale = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					treeNodedtl.setUserObject(codeVale);
					topNode.add(treeNodedtl);
				}
				treeMap.put(vo.getField(nodeIdfield).getAsObject().toString(), treeNodedtl);
			} else {
				// 如果父节点已经存在
				if (!treeMap.containsKey(vo.getField(parentfield).getAsObject())) {
					// 如果没有父节点，先构造父节点
					DefaultMutableTreeNode pranNodedtl = new DefaultMutableTreeNode();
					treeMap.put(vo.getField(parentfield).getAsObject().toString(), pranNodedtl);

					CodeValue codeVale = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					DefaultMutableTreeNode chileNode = new DefaultMutableTreeNode(codeVale);
					pranNodedtl.add(chileNode);
					treeMap.put(vo.getField(nodeIdfield).getAsObject().toString(), chileNode);
				} else {
					// 先获取父节点
					DefaultMutableTreeNode pranNodedtl = treeMap.get(vo.getField(parentfield).getAsObject());
					CodeValue codeVale = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					DefaultMutableTreeNode chileNode = new DefaultMutableTreeNode(codeVale);
					pranNodedtl.add(chileNode);
					treeMap.put(vo.getField(nodeIdfield).getAsObject().toString(), chileNode);
				}
			}
		}
		// this.getTree().addMouseListener(new
		// CheckBoxTreeNodeSelectionListener());
		// this.getTree().setModel(model);
		// this.getTree().setCellRenderer(new CheckBoxTreeCellRenderer());
		// 显示首节点
		tree.setShowsRootHandles(true);
		tree.expandRow(0);
		tree.setRootVisible(true);
		expandAll(this.tree, new TreePath(topNode), true);
	}

	private static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public static TreePanel createTree(List<Record> list, String nodeIdfield, String parentfield,
			String nodeName,String valueName) {
		TreePanel tree = new TreePanel(list, nodeIdfield, parentfield, nodeName,valueName);
		return tree;
	}

	public HashMap<String, DefaultMutableTreeNode> getTreeMap() {
		return treeMap;
	}

	public void setTreeMap(HashMap<String, DefaultMutableTreeNode> treeMap) {
		this.treeMap = treeMap;
	}

	public DefaultMutableTreeNode getTopNode() {
		return topNode;
	}

	public void setTopNode(DefaultMutableTreeNode topNode) {
		this.topNode = topNode;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}
}
