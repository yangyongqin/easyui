package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.borland.dbswing.JdbTable;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.tool.ColumnsHelp;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.config.client.SysCodeFrame;
import com.evangelsoft.workbench.config.client.SysFunctionFrame;
import com.evangelsoft.workbench.config.client.SysPhraseFrame;
import com.evangelsoft.workbench.swing.JInternalDialog;
import com.evangelsoft.workbench.types.BoolStr;

/** * @author  杨永钦
 E-mail:
@date ：2016-5-15 下午07:09:38
@version 1.0   * @since    */
public class DataSourceConfigClient extends JInternalFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 头部
	 */
	private JPanel topPanel;
	private JLabel sourceLabel;
	private JComboBox<Object> sourceBox;
	//尾部
	private JButton confirmButton;
	private JButton cancelButton;
	private JPanel bottomJPanel;


	/**
	 * 中间控件
	 */
	private JPanel middleJPanel;
	//引用
	private  JPanel referenceJPanel;
	//	private JLabel iszdyLabel;
	//	private JCheckBox iszdyBox;
	private JLabel functionLabel;
	private JTextField functionField;
	private JButton functionButton;
	private JPanel functionJPanel;
	private JLabel zdyClassLabel;
	private JTextField zdyClassField;
	private JdbTable table;
	private StorageDataSet dataSet;
	private TableScrollPane tablePane;
	private JPanel tablePanel;;


	/**
	 * @author yyq
	 *下拉的面板
	 */
	CardLayout cradLayout= new  CardLayout();
	private JPanel comboJPanel;
	private JLabel returnType; //返回类型

	//普通下拉，键值是一个
	//	private JRadioButton commonlyButton;
	//	//键值对，显示是value，保存数据库是key
	//	private JRadioButton keyValueButton;
	private JComboBox<Object> returnTypeBox;//返回类型
	private JLabel comboIsedit;//下拉框是否允许编辑
	private JComboBox<CodeValue> comboIseditBox;



	//代码
	private JPanel codePanel;
	private JLabel codeLabel;
	private JButton codeButton;
	private JTextField codeField;
	//短语
	private JPanel phrasePanel;
	private JButton phraseButton;
	private JLabel phraseLabel;
	private JTextField phraseField;

	private JPanel listJPanel;
	private JPanel buttonJPanel;
	private JList<String> list;
	DefaultComboBoxModel<String> listModel=new DefaultComboBoxModel<String>();
	private JButton  addItem;
	private JButton updateItem;
	private JButton deleteItem;
	private JButton clearItem;

	private DataSourceConfig config;

	Record funcRecord;
	Record phraseRecord;
	Record codeType;

	/**
	 * 构造方法
	 * 参数：显示类型和原数据
	 */
	private String viewType;
	private Record value;
	private HashMap<String,String> map;

	DataSourceConfigClient(HashMap<String,String> map,String viewType,Record oldValue){
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.viewType=viewType;
		this.value=oldValue;
		this.map=map;
		topPanel=new JPanel();
		sourceLabel=new JLabel();
		sourceLabel.setText("值来源");
		sourceBox=new JComboBox<Object>();
		sourceBox.addItem(DataSourceConfig.codeValue);
		sourceBox.addItem(DataSourceConfig.phraseCodeValue);
		if(UIComponent.REFERENCE.equals(viewType)){
			sourceBox.addItem(DataSourceConfig.funcCodeValue);
		}
		sourceBox.addItem(DataSourceConfig.zdyCodeValue);
		sourceBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					String viewType =((CodeValue)DataSourceConfigClient.this.sourceBox
							.getSelectedItem()).code.toString();
					if(viewType.equals(DataSourceConfig.SOURCE_CODE)){
						codeButton.setEnabled(true);
						phraseButton.setEnabled(false);
						functionButton.setEnabled(false);
						addItem.setEnabled(false);
						deleteItem.setEnabled(false);
						updateItem.setEnabled(false);
						clearItem.setEnabled(false);
						zdyClassField.setEditable(false);
						//代码只能是键值，不允许修改
						returnTypeBox.setSelectedItem(DataSourceConfig.KEV_CODEVALUE);
						returnTypeBox .setEnabled(false);
						comboIseditBox.setSelectedItem(0);
						comboIseditBox.setEnabled(false);
					}else if(viewType.equals(DataSourceConfig.SOURCE_FUNCTION)){
						codeButton.setEnabled(false);
						phraseButton.setEnabled(false);
						functionButton.setEnabled(true);
						addItem.setEnabled(false);
						deleteItem.setEnabled(false);
						updateItem.setEnabled(false);
						clearItem.setEnabled(false);
						zdyClassField.setEditable(false);

						//						returnTypeBox.setSelectedItem(DataSourceConfig.KEV_CODEVALUE);
						returnTypeBox.setSelectedIndex(-1);
						returnTypeBox .setEnabled(false);
						comboIseditBox.setSelectedItem(-1);
						comboIseditBox.setEnabled(false);

					}else if(viewType.equals(DataSourceConfig.SOURCE_PHRASE)){
						codeButton.setEnabled(false);
						phraseButton.setEnabled(true);
						functionButton.setEnabled(false);
						addItem.setEnabled(false);
						deleteItem.setEnabled(false);
						updateItem.setEnabled(false);
						clearItem.setEnabled(false);
						zdyClassField.setEditable(false);


						returnTypeBox.setSelectedItem(DataSourceConfig.VALUE_CODEVALUE);
						returnTypeBox .setEnabled(false);
						//						comboIseditBox.setSelectedItem(-1);
						comboIseditBox.setEnabled(true);
					}else if(viewType.equals(DataSourceConfig.SOURCE_ZDY)){
						codeButton.setEnabled(false);
						phraseButton.setEnabled(false);
						functionButton.setEnabled(false);
						addItem.setEnabled(true);
						deleteItem.setEnabled(true);
						updateItem.setEnabled(true);
						clearItem.setEnabled(true);
						zdyClassField.setEditable(true);

						returnTypeBox .setEnabled(true);
						comboIseditBox.setEnabled(true);

					}
				}
			}
		});


		//		topPanel.add(sourceLabel);
		//		topPanel.add(sourceBox);


		final GridBagLayout topGridBagLayout = new GridBagLayout();
		topGridBagLayout.rowHeights = new int[] { 5,5,5 };
		topGridBagLayout.columnWidths=new int[] { 5,5,5,5 };
		topPanel.setLayout(topGridBagLayout);

		topPanel.add(sourceLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		topPanel.add(sourceBox, new GridBagConstraints(2,1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));


		codeLabel=new JLabel();
		codeLabel.setText("代码：");
		codePanel=new JPanel();
		codePanel.setLayout(new BorderLayout());
		codeButton=new JButton();
		codeButton.setMargin(new  Insets(0,0,0,0));
		codeButton.setIcon(new ImageIcon(DataSourceConfigClient.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/find.png")));
		codeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//弹窗代码选择
				SysCodeFrame frame=new SysCodeFrame();
				RecordSet set=frame.select(DataSourceConfigClient.this, null, false,true);
				if(set!=null){
					//选择代码生成改变页面属性
					codeField.setText(set.getRecord(0).getField("CODE_TYPE").getString());
				}
			}
		});
		codeField=new JTextField();
		codeField.setEditable(false);
		codePanel.add(codeButton,BorderLayout.EAST);
		codePanel.add(codeField);

		topPanel.add(codeLabel, new GridBagConstraints(1, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		topPanel.add(codePanel, new GridBagConstraints(2,3, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));


		//短语
		phraseLabel=new JLabel();
		phraseLabel.setText("短    语：");
		phrasePanel=new JPanel();
		phrasePanel.setLayout(new BorderLayout());
		phraseButton=new JButton();
		phraseButton.setMargin(new  Insets(1,1,1,1));
		phraseButton.setIcon(new ImageIcon(DataSourceConfigClient.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/find.png")));
		phraseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SysPhraseFrame frame=new SysPhraseFrame();
				RecordSet set=frame.select(DataSourceConfigClient.this, null, false,true);
				if(set!=null){
					//选择代码生成改变页面属性
					phraseField.setText(set.getRecord(0).getField("PH_TYPE").getString());
				}
			}
		});
		phraseField=new JTextField();
		phraseField.setEditable(false);
		phrasePanel.add(phraseButton,BorderLayout.EAST);
		phrasePanel.add(phraseField);

		topPanel.add(phraseLabel, new GridBagConstraints(4, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		topPanel.add(phrasePanel, new GridBagConstraints(5,3, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		this.getContentPane().add(topPanel,BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(450,450));


		//中间面板设置为卡片布局
		middleJPanel=new JPanel();
		middleJPanel.setLayout(cradLayout);
		this.getContentPane().add(middleJPanel);

		//是引用才显示的模板
		referenceJPanel=new JPanel();
		//		iszdyLabel=new JLabel();
		//		iszdyLabel.setText("是否自定义：");
		//		iszdyBox=new JCheckBox();
		zdyClassLabel=new JLabel();
		zdyClassLabel.setText("自定义类：");
		zdyClassField=new JTextField();
		final GridBagLayout filterGridBagLayout = new GridBagLayout();
		filterGridBagLayout.rowHeights = new int[] { 5 };
		filterGridBagLayout.columnWidths=new int[] { 5,5,5,5 };
		referenceJPanel.setLayout(filterGridBagLayout);
		middleJPanel.add(referenceJPanel);

		//		referenceJPanel.add(iszdyLabel, new GridBagConstraints(1, 1, 1, 1,
		//		0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		//		new Insets(0, 0, 0, 0), 0, 0));
		//		referenceJPanel.add(iszdyBox, new GridBagConstraints(2, 1, 1, 1,
		//		0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		//		new Insets(0, 0, 0, 0), 0, 0));

		referenceJPanel.add(zdyClassLabel, new GridBagConstraints(4, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		referenceJPanel.add(zdyClassField,
				new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		functionLabel=new JLabel();
		functionLabel.setText("功能：");
		functionField=new JTextField();
		functionField.setEditable(false);
		functionButton=new JButton();
		functionButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SysFunctionFrame frame=new SysFunctionFrame();
				RecordSet set = frame.select(DataSourceConfigClient.this, null, false,true);
				if(set!=null){
					functionField.setText(set.getRecord(0).getField("FUNC_ID").getString());
				}
			}
		});
		functionButton.setMargin(new Insets(1,1,1,1));
		functionButton.setIcon(new ImageIcon(DataSourceConfigClient.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/find.png")));
		functionJPanel=new JPanel();
		functionJPanel.setLayout(new BorderLayout());
		functionJPanel.add(functionButton,BorderLayout.EAST);
		functionJPanel.add(functionField,BorderLayout.CENTER);

		referenceJPanel.add(functionLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		referenceJPanel.add(functionJPanel, new GridBagConstraints(2,1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		//关于引用的模板,大概是个表格，还差个字段对照表，暂时没写没写，记得记得
		table=new JdbTable();
		dataSet=new StorageDataSet();
		table.setDataSet(dataSet);
		String[] column=new String[]{"COLUMN_ID","COLUMN_NAME","REF_COLUMN_ID","REF_COLUMN_NAME"};
		dataSet.setColumns(ColumnsHelp.getColumns("CONTRAST",column));
		dataSet.getColumn("COLUMN_ID").setEditable(false);
		dataSet.getColumn("COLUMN_NAME").setEditable(false);
		dataSet.open();
		//如果map传值不为空，则添加对应的属性
		if(map!=null){
			for(String key:map.keySet()){
				dataSet.insertRow(false);
				dataSet.setString("COLUMN_ID", key);
				dataSet.setString("COLUMN_NAME", map.get(key));
			}
		}
		tablePane=new TableScrollPane(table);
		tablePanel=new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(tablePane);
		referenceJPanel.add(tablePanel, new GridBagConstraints(1,5, 5, 1,
				1, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		//下拉框的面板
		comboJPanel=new JPanel();
		final GridBagLayout comboGridBagLayout = new GridBagLayout();
		comboGridBagLayout.rowHeights = new int[] { 5,5,5,5,5, };
		comboGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		comboJPanel.setLayout(comboGridBagLayout);


		returnType=new JLabel();
		returnType.setText("返回类型：");
		returnTypeBox=new JComboBox<Object>();
		returnTypeBox.addItem(DataSourceConfig.KEV_CODEVALUE);
		returnTypeBox.addItem(DataSourceConfig.VALUE_CODEVALUE);
		//设置数据模型，写啥暂时等等
		//		returnTypeBox.setModel(new DefaultComboBoxModel<Object>());


		comboJPanel.add(returnType, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		comboJPanel.add(returnTypeBox, new GridBagConstraints(2,1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		comboIsedit=new JLabel();
		comboIsedit.setText("下拉框是否允许编辑");
		comboIseditBox=new JComboBox<CodeValue>();
		comboIseditBox.addItem(new CodeValue("F","否"));
		comboIseditBox.addItem(new CodeValue("T","是"));
		comboJPanel.add(comboIsedit, new GridBagConstraints(4, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		comboJPanel.add(comboIseditBox, new GridBagConstraints(5,1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		middleJPanel.add(comboJPanel);



		//选项列表
		listJPanel=new JPanel();
		listJPanel.setLayout(new BorderLayout());
		list=new JList<String>();
		list.setModel(listModel);
		listModel.addListDataListener(new ListDataListener() {
			public void intervalRemoved(ListDataEvent e) {
			}
			public void intervalAdded(ListDataEvent e) {
			}
			public void contentsChanged(ListDataEvent e) {
				if(listModel.getSize()>0){
					//返回类型和数据来源不允许修改
					returnTypeBox.setEnabled(false);
					sourceBox.setEnabled(false);
				}else{
					returnTypeBox.setEnabled(true);
					sourceBox.setEnabled(true);
				}
			}
		});
		buttonJPanel=new JPanel();
		addItem=new JButton();
		addItem.setText("添加");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//新增选项
				addItem();
			}
		});
		updateItem=new JButton();
		updateItem.setText("修改");
		updateItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String str=	list.getSelectedValue();
				if(str!=null){
					updateItem(str);
				}
			}
		});
		deleteItem=new JButton();
		deleteItem.setText("删除");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取到当前选中，如果当前已经选中
				String str=	list.getSelectedValue();
				deleteItem(str);
			}
		});
		clearItem=new JButton();
		clearItem.setText("清除");
		buttonJPanel.add(addItem);
		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//				list.removeAll();
				listModel.removeAllElements();
			}
		});
		buttonJPanel.add(Box.createVerticalStrut(10));
		buttonJPanel.add(updateItem);
		buttonJPanel.add(Box.createVerticalStrut(10));
		buttonJPanel.add(deleteItem);
		buttonJPanel.add(Box.createVerticalStrut(10));
		buttonJPanel.add(clearItem);

		listJPanel.add(list);
		listJPanel.add(buttonJPanel,BorderLayout.EAST);
		buttonJPanel.setLayout(new BoxLayout( buttonJPanel,BoxLayout.Y_AXIS));

		listJPanel.setPreferredSize(new Dimension(300,250));

		comboJPanel.add(listJPanel, new GridBagConstraints(1,7, 5, 1,
				1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		//底部，确认和取消
		bottomJPanel=new  JPanel();
		confirmButton =new JButton();
		confirmButton.setText("确定");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取界面设置属性
				config=new DataSourceConfig();
				//得到值来源
				if(sourceBox.getSelectedIndex()>-1){
					String valueSource =((CodeValue)DataSourceConfigClient.this.sourceBox
							.getSelectedItem()).code.toString();
					config.setSourceValue(valueSource);
					if(codeField.getText().trim().length()>0){
						config.setCodeType(codeField.getText().trim());
					}
				}
				if(functionField.getText().trim().length()>0){
					config.setFuncId(functionField.getText().trim());
				}
				if(phraseField.getText().trim().length()>0){
					config.setPhraseType(phraseField.getText().trim());
				}
				//				config.setSource(source);
				//				config.setType(viewType);
				if(zdyClassField.getText().trim().length()>0){
					config.setZdyClass(zdyClassField.getText());
				}
				if(returnTypeBox.getSelectedIndex()>-1){

					String valueType =((CodeValue)DataSourceConfigClient.this.returnTypeBox
							.getSelectedItem()).code.toString();
					config.setValueType(valueType);

				}
				if(comboIseditBox.getSelectedIndex()>-1){
					String isedit =((CodeValue)DataSourceConfigClient.this.comboIseditBox
							.getSelectedItem()).code.toString();
					config.setIsEdit(isedit);
				}
				//				config.setZdyItems(new String[ DataSourceConfigClient.this.	listModel.getSize()]);
				StringBuffer buf=new StringBuffer();
				for(int i=0;i<listModel.getSize();i++){
					//					config.getZdyItems()[i]=listModel.getElementAt(i);
					if(i!=0){
						buf.append(",");
					}
					buf.append(listModel.getElementAt(i));
				}
				if(buf.length()>0){
					config.setZdyItems(buf.toString());
				}
				buf.setLength(0);
				//循环表格集合
				List<String> strList=new ArrayList<String>();
				dataSet.first();
				for(int i=0;i<dataSet.rowCount();i++){
					if(!dataSet.isNull("REF_COLUMN_ID")){
						if(buf.length()!=0){
							buf.append(",");
						}
						strList.add(dataSet.getString("COLUMN_ID")+"="+dataSet.getString("REF_COLUMN_ID"));
						buf.append(dataSet.getString("COLUMN_ID")+"="+dataSet.getString("REF_COLUMN_ID"));
					}
					dataSet.next();
				}
				if(buf.length()>0){
					config.setRefColumn(buf.toString());
				}
				//将当前对象转成json字符串，josn统一使用google的gson
				//list转成数组
				DataSourceConfigClient.this.dispose();
			}
		});
		cancelButton=new JButton();
		cancelButton.setText("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataSourceConfigClient.this.dispose();
			}
		});
		bottomJPanel.add(confirmButton);
		bottomJPanel.add(cancelButton);
		this.getContentPane().add(bottomJPanel,BorderLayout.SOUTH);

		//如果是下拉框
		if(UIComponent.COMBO.equals(viewType)){
			cradLayout.last(middleJPanel);
		}else{
			//如果是功能
			cradLayout.first(middleJPanel);
		}

		pack();
		sourceBox.setSelectedIndex(1);
		sourceBox.setSelectedIndex(0);
		//如果传过值不为空，则显示到对应的按钮上去
		if(oldValue!=null){
			//值来源
			sourceBox.setSelectedItem(DataSourceConfig.sourceMap.get(oldValue.getField("DATA_SOURCE").getString()));
			codeField.setText(oldValue.getField("CODE_TYPE").getString());
			phraseField.setText(oldValue.getField("PH_TYPE").getString());
			functionField.setText(oldValue.getField("FUNC_ID").getString());
			//值类型
			returnTypeBox.setSelectedItem(DataSourceConfig.returnTypeMap.get(oldValue.getField("VALUE_TYPE").getString()));
			//			}
			if(BoolStr.getBoolean(oldValue.getField("COMBO_IS_EDIT").getString())){
				comboIseditBox.setSelectedIndex(1);
			}else{
				comboIseditBox.setSelectedIndex(0);
			}
			if(DataSourceConfig.SOURCE_ZDY.equals(oldValue.getField("DATA_SOURCE").getString())){
				//获取自定义的选项
				String[] items=oldValue.getField("ZDY_ITEMS").getString().split(",");
				Object[] obj=new Object[items.length];
				for(int i=0;i<items.length;i++){
					listModel.addElement(items[i]);
				}
			}
			//如果是功能
//			if(DataSourceConfig.SOURCE_FUNCTION.equals(oldValue.getField("DATA_SOURCE").getString())){
				String[] items=oldValue.getField("REF_COLUMNS").getString().split(",");
				//				HashMap<String,String> keyMap=new HashMap<String, String>();
				for(int j=0;j<items.length;j++){
					String[] item=items[j].split("=");
					//					keyMap.put(item[0],item[1]);
					dataSet.first();
					for(int i=0;i<dataSet.rowCount();i++){
						if(item[0].equals(dataSet.getString("COLUMN_ID"))){
							dataSet.setString("REF_COLUMN_ID", item[1]);
							break;
						}
						dataSet.next();
					}
				}
//			}
		}
	}
	public static String config(){
		return null;
	}

	public static void main(String[] args) {
		DataSourceConfigClient frame=new DataSourceConfigClient(null,UIComponent.COMBO,null);
		//		frame.setVisible(true);
		JInternalDialog.showAsDialog(null, frame);
	}
	void addItem(){
		String viewType = ((CodeValue)this.returnTypeBox
				.getSelectedItem()).code.toString();
		String str= CodeValueDialog.select(this, "", viewType);
		if(str!=null){
			//如果是添加
			listModel.addElement(str);
		}
	}

	void updateItem(String value){
		String viewType = ((CodeValue)this.returnTypeBox
				.getSelectedItem()).code.toString();
		String str= CodeValueDialog.select(null, value, viewType);
		if(str!=null){
			int index=list.getSelectedIndex();
			listModel.removeElement(value);
			listModel.addElement(str);
			//			Vector xx=null;
			//			xx.add(index, element);

		}
	}
	void deleteItem(String value){
		listModel.removeElement(value);
	}
	void clearItem(String value){
		listModel.removeAllElements();
	}
	public static DataSourceConfig select(JComponent comm,HashMap<String,String> map,String viewType,Record value){
		DataSourceConfigClient client=new DataSourceConfigClient(map,viewType,value);
		JInternalDialog.showAsDialog(comm, client);
		if(client.config!=null){
			//			Gson gson = new GsonBuilder().serializeNulls().create();
			//			Gson gson=new Gson();
			return client.config;
		}
		return null;
	}
}
