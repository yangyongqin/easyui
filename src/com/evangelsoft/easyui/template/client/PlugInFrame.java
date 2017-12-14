package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.lang.StringUtils;

import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.EditAdapter;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.type.ActionBeforeAfter;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.client.WireWorker;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionJointNode;
import com.evangelsoft.econnect.condutil.ConditionLeafNode;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.TransientRecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.swing.JInternalDialog;
import com.evangelsoft.workbench.types.BoolStr;


public class PlugInFrame extends BaseSingleDataSetFrame{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1781377076778218255L;
	JSplitPane mainPane;//主分割面板
	private JPanel treePanel;
	private JTree tree;//树
	private DefaultMutableTreeNode topNode;//顶级 跟节点

	public PlugInFrame(){
		this.filterButton.setVisible(false);
		this.refreshButton.setVisible(false);
		this.setTitle("插件管理");
		mainPane=new JSplitPane();
		topNode=new DefaultMutableTreeNode();
		tree = new JTree(topNode);
		//		tree.setRootVisible(true);//隐藏跟节点
		tree.setShowsRootHandles(true);
		//设置单选
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//		tree.expandRow(0);
		//		tree.setRootVisible(false);
		treePanel=new JPanel();
		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree);
		treePanel.setEnabled(false);

		tree.setVisibleRowCount(30);
		tree.addTreeSelectionListener(new ButtonTreeSelectionListener());
		mainPane.setLeftComponent(treePanel);
		this.getContentPane().add(mainPane);
		mainPane.setDividerLocation(200);
		mainPane.setRightComponent(listPanel);
		listPanel.setBorder(null);
		this.listPanel.add(this.headerPanel,BorderLayout.NORTH);
		this.listPanel.add(this.footerPanel,BorderLayout.SOUTH);
		this.dataSet.addEditListener(new EditAdapter(){

			@Override
			public void inserted(DataSet dataSet) {
				//如果选中的是按钮，如果选中的
				if(tree.getSelectionCount()<=0){
					PlugInFrame.this.cancelAction.actionPerformed(null);
					throw new java.lang.RuntimeException("请选中操作按钮后再操作！");
				}
//				dataSet.getColumn("BUTTON_CODE").setEditable(false);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node!=null){
					if(node.getUserObject()!=null){
						if(node.getUserObject() instanceof TempEntity){
							TempEntity tempEntity=( TempEntity)node.getUserObject();
							if(tempEntity.getButtonCode()==null){
								PlugInFrame.this.cancelAction.actionPerformed(null);
								throw new java.lang.RuntimeException("请选中操作按钮后再操作！");
							}

//							changeTreepath(tempEntity.getTemplatId(),tempEntity.getButtonCode(),tempEntity.getBeforeOrAfter());
							PlugInFrame.this.dataSet.setString("BUTTON_CODE", tempEntity.getButtonCode());
							PlugInFrame.this.dataSet.setBigDecimal("TEMPLATE_ID", tempEntity.getTemplatId());
							PlugInFrame.this.dataSet.setString("IS_ENABLE",BoolStr.TRUE );
							PlugInFrame.this.dataSet.setString("IS_TRAN",BoolStr.TRUE );
							if(tempEntity.getBeforeOrAfter()!=null){
								PlugInFrame.this.dataSet.setString("BEFORE_AFTER",tempEntity.getBeforeOrAfter());
								PlugInFrame.this.dataSet.getColumn("BEFORE_AFTER$DESC").setEditable(false);
								PlugInFrame.this.dataSet.getColumn("BEFORE_AFTER").setEditable(false);
							}else{
								PlugInFrame.this.dataSet.getColumn("BEFORE_AFTER$DESC").setEditable(true);
								PlugInFrame.this.dataSet.getColumn("BEFORE_AFTER").setEditable(true);
							}
						}

					}
				}
				super.inserted(dataSet);
			}


		});
		//		this.setPreferredSize(new Dimension(800, 600));
		//		tree.setEditable(false);
		//		DefaultMou
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(userKeyEventDispatcher);
		tree.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if(PlugInFrame.this.isModified()){
					e.consume();
					treePanel.setEnabled(false);
					tree.setEnabled(false);
				}
				e.getComponent();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
//				e.consume();
//				treePanel.setEnabled(true);
//				tree.setEnabled(true);
			}

		});

		tree.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseExited(MouseEvent e) {
				treePanel.setEnabled(true);
				tree.setEnabled(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(PlugInFrame.this.isModified()){
					e.consume();
					treePanel.setEnabled(false);
					tree.setEnabled(false);
				}
			}


		});
		pack();
	}

	public void init(){

	}


	public static void main(String[] args) {
		PlugInFrame frame=new PlugInFrame();
		JInternalDialog.showAsDialog(null, frame);
	}
	@Override
	protected Object prepareData() throws Exception {
		//查询所有的模板，查询所有的按钮

		keyColumns = new String[] { "PLUGIN_NMAE" };
		SysTemplate sysTemplate=(SysTemplate)(new RMIProxy(Consumer.getDefaultConsumer().getSession())).newStub(SysTemplate.class);
		VariantHolder<Object> data=new VariantHolder<Object>();
		data.value=new  TransientRecordSet();
		VariantHolder<String> errMsg=new VariantHolder<String>();
		if(!sysTemplate.list(null, data, errMsg)){
			throw new RuntimeException(errMsg.value);
		}
		//得到所有模板
		RecordSet set=(RecordSet)data.value;
		HashMap<Object, DefaultMutableTreeNode> tempMap=new HashMap<Object, DefaultMutableTreeNode>();
		for(int i=0;i<set.recordCount();i++){
			TempEntity entity=new TempEntity();
			entity.setTemplatId(set.getRecord(i).getField("TEMPLATE_ID").getNumber());
			entity.setCode(set.getRecord(i).getField("TEMPLATE_NUM").getString());
			entity.setName(set.getRecord(i).getField("TEMPLATE_NAME").getString());

			//			CodeValue codeValue=new CodeValue(set.getRecord(i).getField("TEMPLATE_ID").getNumber(), "["+set.getRecord(i).getField("TEMPLATE_NUM").getString()+"]"+   set.getRecord(i).getField("TEMPLATE_NAME").getString());
			DefaultMutableTreeNode treeNodedtl = new DefaultMutableTreeNode(entity);
			topNode.add(treeNodedtl);
			//			tempMap.get(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber()).add(treeNodedtl);
			tempMap.put(set.getRecord(i).getField("TEMPLATE_ID").getNumber(), treeNodedtl);
		}
		//s得到所有模板按钮
		data.value=new  TransientRecordSet();
		if(!sysTemplate.getAllButton(null, data, errMsg)){
			throw new RuntimeException(errMsg.value);
		}
		RecordSet buttonSet=(RecordSet)data.value;
		for(int i=0;i<buttonSet.recordCount();i++){
			if(tempMap.containsKey(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber())){
				//				CodeValue codeValue=new CodeValue(buttonSet.getRecord(i).getField("BUTTON_CODE").getString(), "["+ buttonSet.getRecord(i).getField("BUTTON_CODE").getString()+"]"+  buttonSet.getRecord(i).getField("BUTTON_NAME").getString());
				TempEntity entity=new TempEntity();
				entity.setTemplatId(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber());
				entity.setCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				entity.setName(buttonSet.getRecord(i).getField("BUTTON_NAME").getString());
				entity.setButtonCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				DefaultMutableTreeNode treeNodedtl = new DefaultMutableTreeNode(entity);
				tempMap.get(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber()).add(treeNodedtl);
				//创建一一前一后俩个按钮

				TempEntity beforeEentity=new TempEntity();
				beforeEentity.setTemplatId(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber());
				beforeEentity.setCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				beforeEentity.setName("之前");
				beforeEentity.setBeforeOrAfter(ActionBeforeAfter.BEFORE);
				beforeEentity.setButtonCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				DefaultMutableTreeNode beforeNote = new DefaultMutableTreeNode(beforeEentity);
				//				tempMap.get(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber()).add(treeNodedtl);
				treeNodedtl.add(beforeNote);


				TempEntity afterEentity=new TempEntity();
				afterEentity.setTemplatId(buttonSet.getRecord(i).getField("TEMPLATE_ID").getNumber());
				afterEentity.setCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				//
				afterEentity.setBeforeOrAfter(ActionBeforeAfter.AFTER);
				afterEentity.setName("之后");
				afterEentity.setButtonCode(buttonSet.getRecord(i).getField("BUTTON_CODE").getString());
				DefaultMutableTreeNode afterNote = new DefaultMutableTreeNode(afterEentity);
				treeNodedtl.add(afterNote);

			}
		}
		tree.expandRow(0);
		tree.setRootVisible(false);
		return super.prepareData();
	}

	public class ButtonTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			//获取最后被选中的
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if(node!=null){
				if(node.getUserObject()!=null){
					if(node.getUserObject() instanceof TempEntity){
						TempEntity tempEntity=( TempEntity)node.getUserObject();
						changeTreepath(tempEntity.getTemplatId(),tempEntity.getButtonCode(),tempEntity.getBeforeOrAfter());
					}
				}
			}

		}
	}

	//改变节点触发调用后台
	public void changeTreepath(BigDecimal tempId,String buttonCode,String beforeOrAfter){
		//		ConditionTree ct=new ConditionTree();

		dataSet.empty();
		dataSet.reset();

		filterTree.clear();
		sortList.clear();

		filterTree.setRoot(new ConditionJointNode(ConditionJointNode.JOIN_TYPE_AND));
		ConditionLeafNode node;
		node = new ConditionLeafNode("TEMPLATE_ID", ConditionItem.DATA_TYPE_NUMBER,ConditionItem.OPERATOR_EQUAL);
		node.setNumber(tempId);
		filterTree.addChildLast(filterTree.getRoot(), node);
		if(StringUtils.isNotEmpty(buttonCode)){
			node = new ConditionLeafNode("BUTTON_CODE", ConditionItem.DATA_TYPE_STRING,ConditionItem.OPERATOR_EQUAL);
			node.setString(buttonCode);
			filterTree.addChildLast(filterTree.getRoot(), node);
		}
		if(StringUtils.isNotEmpty(beforeOrAfter)){
			node = new ConditionLeafNode("BEFORE_AFTER", ConditionItem.DATA_TYPE_STRING,ConditionItem.OPERATOR_EQUAL);
			node.setString(beforeOrAfter);
			filterTree.addChildLast(filterTree.getRoot(), node);
		}
		//		this.refreshButton.doClick();

		beforeRefresh();

		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("bound", boundTree);
		filter.put("filter", filterTree);
		filter.put("sort", sortList);
		class Worker implements WireWorker.Worker {
			private Object filter;

			public Worker(Object filter) {
				super();
				this.filter = filter;
			}

			public Object work() throws Throwable {
				Object rs = listEntity(filter);
				return rs;
			}
		}
		;
		class Hook implements WireWorker.Hook {
			public void hook(Object value) {
				worker.setHook(null);
				worker.setCorrector(null);
				worker.setResumer(null);

				DataSetHelper.loadFromRecordSet(dataSet, (RecordSet) value);
				//				modified = false;

//				buildUniqueIndex();

				if (filterAutoHiddenMenuItem.isSelected()) {
					filterMenuItem.setSelected(false);
					filterPanel.setVisible(false);
					listPane.resetToPreferredSizes();
				}

				if (!dataSet.isEmpty()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dataSet.first();
						}
					});
				} else {
					clearInlet();
					showStatus();
				}

				afterRefresh();
			}
		}

		class Corrector implements WireWorker.Corrector {
			public void correct(Throwable e) {
				worker.setHook(null);
				worker.setCorrector(null);
				worker.setResumer(null);

				showStatus();
			}
		}

		class Resumer implements WireWorker.Resumer {
			public void resume() {
				worker.setHook(null);
				worker.setCorrector(null);
				worker.setResumer(null);

				showStatus();
			}
		}

		if (!worker.isIdle()) {
			return;
		}
		worker.setWorker(new Worker(filter));
		worker.setHook(new Hook());
		worker.setCorrector(new Corrector());
		worker.setResumer(new Resumer());
		worker.start();

	}
	/**
	 * @author 模板
	 *
	 */
	public class TempEntity{
		private BigDecimal templatId;
		private String buttonCode;
		private String beforeOrAfter;
		private String code;
		private String name;
		public BigDecimal getTemplatId() {
			return templatId;
		}
		public void setTemplatId(BigDecimal templatId) {
			this.templatId = templatId;
		}
		public String getButtonCode() {
			return buttonCode;
		}
		public void setButtonCode(String buttonCode) {
			this.buttonCode = buttonCode;
		}
		public String getBeforeOrAfter() {
			return beforeOrAfter;
		}
		public void setBeforeOrAfter(String beforeOrAfter) {
			this.beforeOrAfter = beforeOrAfter;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "[" + code + "]" + name + "";
		}

	}

	private UserKeyEventDispatcher userKeyEventDispatcher=new UserKeyEventDispatcher();
	public class UserKeyEventDispatcher implements  KeyEventDispatcher{

		public boolean dispatchKeyEvent(KeyEvent e) {
			return e.isConsumed();
		}

	}
}

