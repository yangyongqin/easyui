package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.borland.dx.dataset.DataSet;
import com.evangelsoft.easyui.tool.ClientVariableParser;
import com.evangelsoft.easyui.type.DefaultValue;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionJointNode;
import com.evangelsoft.econnect.condutil.ConditionLeafNode;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.panelbase.ConditionLeafNodeEditorPanel;
import com.evangelsoft.workbench.panelbase.ConditionValuePanel;

/** * @author  杨永钦
 E-mail:
@date ：2016-7-10 下午06:24:25
@version 1.0   * @since    */
public class UIConditionLeafNodePanel extends JPanel{
	private static ResourceBundle L = ResourceBundle.getBundle(ConditionLeafNodeEditorPanel.class.getPackage().getName() + ".Res");
	//存放数据模式按钮的集合
//	private HashMap<String,JToggleButton> dataModeMap=new HashMap<String, JToggleButton>();
//	//存放公式模式按钮的集合
//	private HashMap<String,JToggleButton> formulaModeMap=new HashMap<String, JToggleButton>();
//	//存放值组件的集合
//	private HashMap<String,ConditionValuePanel> valueMap=new HashMap<String, ConditionValuePanel>();
//	//存放值2组件的集合
//	private HashMap<String,ConditionValuePanel> value2Map=new HashMap<String, ConditionValuePanel>();
//	//
//	//存放操作符按键的集合
//	private HashMap<String,JComboBox<String>> operatorMap=new HashMap<String, JComboBox<String>>();

	private HashMap<String,UIConditionLeafNodeBean> conditionLeafNodeMap=new HashMap<String,UIConditionLeafNodeBean>();




	//存放真实列和因为键值可能重复问题键对照集合
	private HashMap<String,String> keyColumnMap=new HashMap<String,String>();
	//存放可以和下标的，用于删除
	private List<String> keyIndexList=new ArrayList<String>();


	//界面显示的所有items
	protected ArrayList<ConditionItem> conditionItems ;
	//用map集合存，取值更方便
	protected HashMap<String,ConditionItem> conditionItemMap=new HashMap<String, ConditionItem>() ;

	public ArrayList<ConditionItem> getConditionItems() {
		return conditionItems;
	}
	//	private RecordSet set;
	public void setConditionItems(ArrayList<ConditionItem> conditionItems) {
		this.conditionItems = conditionItems;
	}
	static ConditionItem item=new ConditionItem(null,null,0,0,0,0,null);
	GridBagLayout layout=new GridBagLayout();

	GridBagLayout valueLayout;//值

	private JLabel fitLabel;

	public	UIConditionLeafNodePanel(ArrayList<ConditionItem> conditionItems,RecordSet set) throws  Exception{
		fitLabel=new JLabel();
		this.conditionItems=conditionItems;
		//		this.set=set;
		UIFlowLayout localFlowLayout = new UIFlowLayout();
		localFlowLayout.setAlignment(0);

		setLayout(localFlowLayout);

		layout.columnWidths=new int[]{5,5,5,5,5,5,5,5,5,5,5,5,5};
		int[] height=new int[set.recordCount()];
		for(int i=0;i<height.length;i++){
			height[i]=5;
		}
		layout.rowHeights=height;
		for(int i=0;i<conditionItems.size();i++){
			conditionItemMap.put(conditionItems.get(i).name, conditionItems.get(i));
		}

		setLayout(layout);
		valueLayout=new GridBagLayout();
		valueLayout.columnWidths=new int[]{5,5};
		valueLayout.rowHeights=new int[]{5,5};

		for(int k=0;k<set.recordCount();k++){
			addItem(set.getRecord(k));
		}
		//添加一个组件，填充整个面板，使上面的按钮显示在上面
		add(fitLabel, new GridBagConstraints(6, (set.recordCount())*2+1, 4, 1, 3.0D, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}
//	public void setNodesExpandedListener(ConditionLeafNodesExpandedListener paramConditionLeafNodesExpandedListener)
//	{
//	for(String key:valueMap.keySet()){
//	valueMap.get(key).setNodesExpandedListener(paramConditionLeafNodesExpandedListener);
//	value2Map.get(key).setNodesExpandedListener(paramConditionLeafNodesExpandedListener);
//	}
//	}

	private OperatorComboItemListener operatorListener=new OperatorComboItemListener();
	private class OperatorComboItemListener
	implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == 1)
			{
				/**
				 * 还要改的，还要改的
				 */
				JComboBox<String> box= (JComboBox<String>)e.getSource();

				//				ConditionItem localConditionItem =( ConditionItem)box.getClientProperty("condition");
				String colId=(String)box.getClientProperty("COLUMN_ID");
				UIConditionLeafNodeBean bean=conditionLeafNodeMap.get(colId);

				bean.getDataModeButton().setVisible(false);
				bean.getExpremodeButton().setVisible(false);
				bean.getConditionPanel().setVisible(false);
				bean.getCondition2Panel().setVisible(false);

				String str = conditionItemMap.get(bean.getColumnId()).operators.get(box.getSelectedIndex());


				if ((!str.equals("ISN")) && (!str.equals("INN")))
				{
					//					UiConditionLeafNodeEditorPanel.this.G.setVisible(true);
					//					UiConditionLeafNodeEditorPanel.this.I.setVisible(true);
					bean.getDataModeButton().setVisible(true);
					bean.getExpremodeButton().setVisible(true);
					//					UiConditionLeafNodeEditorPanel.this.D.setVisible(true);
					//					UiConditionLeafNodeEditorPanel.this.condition.setVisible(true);
					bean.getConditionPanel().setVisible(true);


					if (str.equals("BT"))
					{
						//						UiConditionLeafNodeEditorPanel.this.B.setVisible(true);
						//						UiConditionLeafNodeEditorPanel.this.condition2.setVisible(true);
						bean.getCondition2Panel().setVisible(true);
					}
				}
				//				UiConditionLeafNodeEditorPanel.this.H.setVisible((!UiConditionLeafNodeEditorPanel.this.condition.isVisible()) && (!UiConditionLeafNodeEditorPanel.this.condition2.isVisible()));
			}
		}

	}
	ExpressionButtonActionListener expressionListener=new ExpressionButtonActionListener();
	private class ExpressionButtonActionListener
	implements ActionListener
	{
		private ExpressionButtonActionListener()
		{
		}

		public void actionPerformed(ActionEvent e)
		{
			JComponent box= (JComponent)e.getSource();

			String colId=(String)box.getClientProperty("COLUMN_ID");
			UIConditionLeafNodeBean bean=conditionLeafNodeMap.get(colId);
			bean.getCondition2Panel().setMode(ConditionValuePanel.Mode.EXPRESSION);
			bean.getConditionPanel().setMode(ConditionValuePanel.Mode.EXPRESSION);
		}
	}

	DataButtonActionListener dataButtonAction=new DataButtonActionListener();
	private class DataButtonActionListener
	implements ActionListener
	{
		private DataButtonActionListener()
		{
		}
		public void actionPerformed(ActionEvent e)
		{
			JComponent box= (JComponent)e.getSource();

			String colId=(String)box.getClientProperty("COLUMN_ID");
			UIConditionLeafNodeBean bean=conditionLeafNodeMap.get(colId);
			bean.getConditionPanel().setMode(ConditionValuePanel.Mode.DATA);
			bean.getCondition2Panel().setMode(ConditionValuePanel.Mode.DATA);
		}
	}

	public ConditionTree getTree() throws Exception{
		//条件树
		ConditionTree conditiontree = new ConditionTree();
		conditiontree.setRoot(new ConditionJointNode(
				ConditionJointNode.JOIN_TYPE_AND));

		//		boolean isfirst=true;
//		UIConditionLeafNodeBean bean=conditionLeafNodeMap.get(colId);


		for(String key:conditionLeafNodeMap.keySet()){
			ConditionLeafNode localConditionLeafNode = new ConditionLeafNode();
			ConditionItem localConditionItem = conditionItemMap.get(conditionLeafNodeMap.get(key).getColumnId());
			localConditionLeafNode.name = localConditionItem.name;
			//如果是数据模式
			if (conditionLeafNodeMap.get(key).getDataModeButton().isSelected()){
				localConditionLeafNode.dataType = localConditionItem.dataType;
			}   else{
				localConditionLeafNode.dataType = 101;
			}
			if (localConditionItem != null){
				localConditionLeafNode.operator = ((String)localConditionItem.operators.get(conditionLeafNodeMap.get(key).getOperatorBox().getSelectedIndex()));
			}
			//验证是否已经赋值，如果未赋值则跳过此循环
			if(ConditionItem.OPERATOR_IS_NULL.equals( localConditionLeafNode.operator)||ConditionItem.OPERATOR_IS_NOT_NULL.equals(localConditionLeafNode.operator)){
				conditiontree.addChildLast(conditiontree.getRoot(), localConditionLeafNode);
				continue;
			}//如果未赋值
			else if(conditionLeafNodeMap.get(key).getConditionPanel().getValue()==null||"".equals(conditionLeafNodeMap.get(key).getConditionPanel().getValue())){
				continue;
			}
			for (int i = 0; i < 2; i++)
			{
				ConditionValuePanel localConditionValuePanel = i == 0 ? this.conditionLeafNodeMap.get(key).getConditionPanel() : this.conditionLeafNodeMap.get(key).getCondition2Panel();
				if (localConditionValuePanel.isVisible())
				{
					Object localObject = localConditionValuePanel.getValue();
					if (conditionLeafNodeMap.get(key).getDataModeButton().isSelected())
					{
						if (localObject != null)
							if (localConditionItem.dataType == 22)
								localConditionLeafNode.setString(i, (String)localObject);
							else if (localConditionItem.dataType == 1)
								localConditionLeafNode.setNumber(i, (BigDecimal)localObject);
							else if (localConditionItem.dataType == 11)
								localConditionLeafNode.setDate(i, (Date)localObject);
							else if (localConditionItem.dataType == 12)
								localConditionLeafNode.setTime(i, (Date)localObject);
							else if (localConditionItem.dataType == 13)
								localConditionLeafNode.setTimpstamp(i, (Date)localObject);
					}
					else if (localConditionLeafNode.dataType == 101)
						localConditionLeafNode.setExpression(i, (String)localObject);
				}
			}
			//添加到条件树，返回给页面，如果已经有条件了，则追加
			conditiontree.addChildLast(conditiontree.getRoot(), localConditionLeafNode);
			//且判断是否合法是否必腰取值
//			if(conditiontree.getRoot()==null){
//			conditiontree.setRoot(localConditionLeafNode);
//			}else{
//			}
		}
		return conditiontree;
	}
	void addItem(Record record) throws Exception{
		//根据操作符号来计算
		int k=conditionLeafNodeMap.size();

		if(layout.rowHeights.length<k){
			int[] height=new int[layout.rowHeights.length+4];
			for(int i=0;i<height.length;i++){
				height[i]=5;
			}
			layout.rowHeights=height;
			this.setLayout(layout);
		}

		UIConditionLeafNodeBean bean=new UIConditionLeafNodeBean();
		String key=record.getField("COLUMN_ID").getString();
		bean.setColumnId(key);
		//循环判断，如果有相同的列，则取采用列ID+#+下标的方式组成新的
		if(keyColumnMap.containsKey(key)){
			key+="#"+(keyColumnMap.size()+1);
		}
		bean.setKey(key);
		bean.setIndex(k);
		JButton btn=new JButton();
		btn.setMargin(new Insets(0,0,0,0));
		btn.setText("X");
		btn.setForeground(SystemColor.RED);
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.setRolloverEnabled(true);
		btn.putClientProperty("COLUMN_ID",key);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JButton btn=(JButton)e.getSource();
				String colId=(String)btn.getClientProperty("COLUMN_ID");
				removeItem(colId);
			}
		});
		//不显示边框
		btn.setBorderPainted(false);
		btn.addMouseListener(buttonMouse);
		bean.setCancelBtn(btn);

		keyColumnMap.put(key, record.getField("COLUMN_ID").getString());
		keyIndexList.add(key);
		JLabel label=new JLabel();
		label.setText(record.getField("COLUMN_NAME").getString());
		bean.setTextLabel(label);
		//添加到其他列到公式模式选择下拉
		ConditionItem	localConditionItem1=conditionItemMap.get(record.getField("COLUMN_ID").getString());
		if(localConditionItem1==null){
			return;
		}
		if (conditionItemMap.get(record.getField("COLUMN_ID").getString()).fields == null)
		{
			conditionItemMap.get(record.getField("COLUMN_ID").getString()).fields = new HashMap();
			for(int i=0;i<conditionItems.size();i++){
				ConditionItem localConditionItem2 =conditionItems.get(i);
				if ((!localConditionItem2.name.equals(localConditionItem1.name)) && ((localConditionItem2.dataType == localConditionItem1.dataType) || (((localConditionItem2.dataType == 11) || (localConditionItem2.dataType == 13)) && ((localConditionItem1.dataType == 11) || (localConditionItem1.dataType == 13)))))
					localConditionItem1.fields.put(localConditionItem2.name, localConditionItem2.caption);
			}
		}
		//操作符号
		JComboBox<String> operatorCombo=new JComboBox<String>();
//		operatorMap.put(key,operatorCombo);
		bean.setOperatorBox(operatorCombo);//添加都对象管理
		operatorCombo.putClientProperty("COLUMN_ID",key);
		for (int j = 0; j < conditionItemMap.get(record.getField("COLUMN_ID").getString()).operators.size(); j++){
			operatorCombo.addItem(item.getOperatorCaption(conditionItemMap.get(record.getField("COLUMN_ID").getString()).operators.get(j)));
		}
		operatorCombo.addItemListener(operatorListener);
		operatorCombo.setSelectedIndex(-1);

		//数据模式和公式模式
		JPanel dataPanel=new JPanel();
		dataPanel.setLayout(new BorderLayout());
		ButtonGroup group = new ButtonGroup();//分组
		JToggleButton dataModeButton=new JToggleButton();
		dataModeButton.addActionListener(dataButtonAction);
		dataModeButton.putClientProperty("COLUMN_ID",key);
		dataModeButton.setToolTipText(L.getString("DATA_MODE"));
		dataModeButton.putClientProperty("COLUMN_ID",key);
		group.add(dataModeButton);
		dataModeButton.setIcon(new ImageIcon(ConditionLeafNodeEditorPanel.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/data.png")));
		dataModeButton.setSelected(true);
		dataModeButton.setMargin(new Insets(0, 0, 0, 0));
		JPanel	I = new JPanel();
		I.setLayout(new BorderLayout());
		I.add(dataModeButton, "West");
		bean.setModePanel(I);
//		dataModeMap.put(key,dataModeButton);
		bean.setDataModeButton(dataModeButton);

		JToggleButton   expremodeButton = new JToggleButton();
		expremodeButton.setToolTipText(L.getString("EXPRESSION_MODE"));
		expremodeButton.setIcon(new ImageIcon(ConditionLeafNodeEditorPanel.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/field.png")));
		expremodeButton.addActionListener(expressionListener);
		expremodeButton.setMargin(new Insets(0, 0, 0, 0));
		group.add(expremodeButton);
		I.add(expremodeButton, "East");
//		formulaModeMap.put(key, expremodeButton);
		bean.setExpremodeButton(expremodeButton);
		expremodeButton.putClientProperty("COLUMN_ID",key);

		//值控件
		JPanel valuePanel=new JPanel();
		ConditionValuePanel condition2=new ConditionValuePanel();
		condition2.putClientProperty("COLUMN_ID",key);
		condition2.setItem( conditionItemMap.get(record.getField("COLUMN_ID").getString()));
		condition2.setMode(ConditionValuePanel.Mode.DATA);
		//要添加默认值
		ConditionValuePanel condition=new ConditionValuePanel();
		condition.putClientProperty("COLUMN_ID",key);
		valuePanel.setLayout(valueLayout);
		condition.setItem(conditionItemMap.get(record.getField("COLUMN_ID").getString()));
		condition.setMode(ConditionValuePanel.Mode.DATA);
		valuePanel.add(condition, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
//		valueMap.put(key, condition);
		bean.setConditionPanel(condition);
		valuePanel.add(condition2, new GridBagConstraints(1, 1, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
//		value2Map.put(key, condition2);
		bean.setCondition2Panel(condition2);
		bean.setValuePanel(valuePanel);

		//如果默认值不为空，则设置默认值
		if(!record.getField("DEFAULT_VALUE").isNull()&&(record.getField("DEFAULT_VALUE").getString().trim().length()>0)){
//			obj=	ClientVariableParser.getObject(record.getField("DEFAULT_VALUE").getString());
			Object	obj=DefaultValue.getValue( record.getField("DEFAULT_VALUE_SOURCE").getString(), record.getField("DEFAULT_VALUE").getString(), null);
			condition.setValue(obj);
			condition2.setValue(obj);
		}
		//是否锁定，锁定不允许编辑和删除
		if(!record.getField("DEFAULT_VALUE").isNull()){
			condition.setEnabled(false);
			condition2.setEnabled(false);
		}
		//添加按钮到对应的位置
		this.add(bean.getCancelBtn(), new GridBagConstraints(0, (k*2)+1, 1, 1, 0.0D, 0.0D,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(bean.getTextLabel(), new GridBagConstraints(2, (k*2)+1, 1, 1, 0.0D, 0.0D,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(bean.getOperatorBox(), new GridBagConstraints(3, (k*2)+1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.add(bean.getModePanel() , new GridBagConstraints(5, (k*2)+1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		//值面板
		this.add(bean.getValuePanel(), new GridBagConstraints(7, (k*2)+1, 4, 1, 3.0D, 0.0D, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(fitLabel, new GridBagConstraints(7, (k+1)*2+1, 4, 1, 3.0D, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		//添加强制换行标志
		conditionLeafNodeMap.put(key, bean);

		operatorCombo.setSelectedIndex(0);
	}
	//根据下标删除对应的数据，下标下面的控件要上移
	void removeItem(String key){
		//先删除所选中的控件
//		dataModeMap .get(keyIndexList.get(index));
		UIConditionLeafNodeBean bean=conditionLeafNodeMap.get(key);
		//当前面板删除控件
		this.remove(bean.getCancelBtn());
		this.remove(bean.getDataModeButton());
		this.remove(bean.getExpremodeButton());
		this.remove(bean.getOperatorBox());
		this.remove(bean.getTextLabel());
		this.remove(bean.getValuePanel());
//		this.remove(bean.getConditionPanel());
		this.remove(bean.getModePanel());
		//在当前下标下面的所有控件均要上移

		keyIndexList.remove(bean.getIndex());
		for(int i=bean.getIndex();i<keyIndexList.size();i++){
			UIConditionLeafNodeBean tempBean=conditionLeafNodeMap.get(keyIndexList.get(i));
			tempBean.setIndex(i);
			this.add(tempBean.getCancelBtn(), new GridBagConstraints(0, (i)*2+1, 1, 1, 0.0D, 0.0D,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			this.add(tempBean.getTextLabel(), new GridBagConstraints(2, (i)*2+1, 1, 1, 0.0D, 0.0D,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			this.add(tempBean.getOperatorBox(), new GridBagConstraints(3, (i)*2+1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
			this.add(tempBean.getModePanel() , new GridBagConstraints(5, (i)*2+1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
			//值面板
			this.add(tempBean.getValuePanel(), new GridBagConstraints(7, (i)*2+1, 4, 1, 3.0D, 0.0D, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
//		this.add(fitLabel, new GridBagConstraints(7, (k+1)*2+1, 4, 1, 3.0D, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.updateUI();
		conditionLeafNodeMap.remove(key);
	}
	private ButtonMouse buttonMouse=new ButtonMouse();
	private class ButtonMouse extends MouseAdapter{
		//鼠标移动到上面改变样式
		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btn=(JButton)e.getSource();
			btn.setBorderPainted(true);
		}
		//鼠标离开改变样式
		@Override
		public void mouseExited(MouseEvent e) {
			JButton btn=(JButton)e.getSource();
			btn.setBorderPainted(false);
		}
	}
}
