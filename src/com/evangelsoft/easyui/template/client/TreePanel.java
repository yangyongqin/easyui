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

/** * @author  ������
 E-mail:
@date ��2016-8-21 ����08:20:41
@version 1.0   * @since    */
public class TreePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -8290104788613064628L;

	// ��Map���ڻ��棬������
	private HashMap<String, DefaultMutableTreeNode> treeMap = new HashMap<String, DefaultMutableTreeNode>();

	// ���ĸ��ڵ�
	private DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("������Ʒ");;

	private JTree tree;

	List<Record> list;

	/**
	 * @Fields nodeIdfield : �ڵ�ID
	 */
	private String nodeIdfield;

	/**
	 * @Fields parentfield : ���ڵ�ID
	 */
	private String parentfield;

	/**
	 * @Fields nodeName : ��ʾ����
	 */
	private String nodeName;
	/**
	 * @Fields valueName : ֵ����
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
		// topNode= new DefaultMutableTreeNode("������Ʒ");
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
			// ����ϼ������ǿա�ֱ����ӵ���Ŀ¼��
			if (vo.getField(parentfield) == null || vo.getField(parentfield).isNull()
					|| "0".equals(vo.getField(parentfield).getAsObject())) {
				// �жϵ�ǰID�Ƿ���ڣ���Ϊ�ӽڵ�ĸ��ڵ㲻����ʱ�򣬻ṹ��һ�����ڵ�ID�����ǲ�δ��ʾ��ȷ��ֵ
				if (!treeMap.containsKey(vo.getField(nodeIdfield).getAsObject())) {
					treeNodedtl = new DefaultMutableTreeNode();
					CodeValue lablevalue = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					treeNodedtl.setUserObject(lablevalue);// �ڵ㸳ֵ
					topNode.add(treeNodedtl);
				} else {
					// ����Ѿ����ڣ�˵�����ӽڵ���ǰ���ɵĸ��ڵ�
					treeNodedtl = treeMap.get(vo.getField(nodeIdfield).getAsObject());
					CodeValue codeVale = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					treeNodedtl.setUserObject(codeVale);
					topNode.add(treeNodedtl);
				}
				treeMap.put(vo.getField(nodeIdfield).getAsObject().toString(), treeNodedtl);
			} else {
				// ������ڵ��Ѿ�����
				if (!treeMap.containsKey(vo.getField(parentfield).getAsObject())) {
					// ���û�и��ڵ㣬�ȹ��츸�ڵ�
					DefaultMutableTreeNode pranNodedtl = new DefaultMutableTreeNode();
					treeMap.put(vo.getField(parentfield).getAsObject().toString(), pranNodedtl);

					CodeValue codeVale = new CodeValue(vo.getField(nodeName).getAsObject().toString(),
							vo.getField(valueName).getAsObject());
					DefaultMutableTreeNode chileNode = new DefaultMutableTreeNode(codeVale);
					pranNodedtl.add(chileNode);
					treeMap.put(vo.getField(nodeIdfield).getAsObject().toString(), chileNode);
				} else {
					// �Ȼ�ȡ���ڵ�
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
		// ��ʾ�׽ڵ�
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
