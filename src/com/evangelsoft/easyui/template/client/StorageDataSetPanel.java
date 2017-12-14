package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.beans.Beans;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.borland.dbswing.JdbCheckBox;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbDatePicker;
import com.borland.dbswing.JdbDateTimePicker;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextArea;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnAware;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.ExceptionEvent;
import com.borland.dx.dataset.ItemListDescriptor;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.tool.DataTypeTool;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.swing.JTimePicker;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientutil.CodeTable;
import com.evangelsoft.workbench.config.client.SysPhraseHelper;
import com.evangelsoft.workbench.types.BoolStr;

public class StorageDataSetPanel extends PagePanel{
	private static final long serialVersionUID = -5660215293354264715L;


	public static 	RecordFormat codeformat = new RecordFormat("@");
	static{
//		RecordFieldFormat format=;
		codeformat.appendField(new RecordFieldFormat("SYS_CODE_DTL.CODE"));
		codeformat.appendField(new RecordFieldFormat("SYS_CODE_DTL.DESCRIPTION"));
	}
	/**
	 *
	 */
	//	public static String STRING_ID="PAGE_TYPE";
	//	public static String TABLE_TYPE="TABLE";
	//	public static String PANEL_TYPE="PANEL";
	//	private JPanel panel=new JPanel();
	private JPanel tablePanel;
	private JToolBar tableBar;
	//	private String type;
	private TableScrollPane tableScrollPane;
	private JdbTable table;
	private StorageDataSet storageDataSet;


	private AbstractAction newAction;
	private AbstractAction batchAction;
	private AbstractAction deleteAction;
	private AbstractAction clearAction;
	private JButton newButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton batchButton;

	private List<Column> listColumn=new ArrayList<Column>();

	public StorageDataSetPanel(CodeValue codevalue,String type,String pos,StorageDataSet storageDataSet,boolean isAddButton){
		this( codevalue, type, null, pos, storageDataSet,isAddButton);
	}

	public StorageDataSetPanel(CodeValue codevalue,String type,JdbTable table,String pos,StorageDataSet storageDataSet,boolean isAddButton){
		this.type=type;
		if(codevalue!=null){
			pageId=codevalue.code.toString();
			pageName=codevalue.value.toString();
		}
		if(table==null){
			table=new JdbTable();
		}
		this.table=table;
		tableScrollPane=new TableScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(500,150));
		this.storageDataSet=storageDataSet;
		//如果是表格显示且位置在表体，则需要添加按钮
		if(isAddButton){
			newButton=new JButton();
			newAction=new NewAction();
			deleteAction=new DeleteAction();
			clearAction=new ClearAction();
			newButton.setAction(newAction);
			deleteButton=new JButton();
			deleteButton.setAction(deleteAction);
			clearButton=new JButton();
			clearButton.setAction(clearAction);
			batchButton=new JButton();
			tablePanel=new JPanel();
			tablePanel.setLayout(new BorderLayout());
			tablePanel.add(tableScrollPane);
			tableBar=new JToolBar();
			tableBar.add(newButton);
			tableBar.add(batchButton);
			batchButton.setIcon(new ImageIcon(StorageDataSetPanel.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/batchDetail.png")));
			tableBar.add(deleteButton);
			tableBar.add(clearButton);
			tablePanel.add(tableBar,BorderLayout.NORTH);
		}

		table.setDataSet(storageDataSet);
		storageDataSet.open();
		layout.rowHeights=new int[]{5,5,5};
		layout.columnWidths=new int[]{5,5,5};
		panel.setLayout(layout);
		if(TABLE_TYPE.equals(this.getType())){
			//			table.addMouseListener(this);
			//			table.getColumnModel().addColumn(new TableColumn());
			//			table.removeMouseListener(table);
			if(DesignFrame.BODY.equals(pos)){
				this.setViewportView(tablePanel);
			}else{
				this.setViewportView(tableScrollPane);
			}
			//			this.removeMouseListener(table);
		}else{
			this.setViewportView(panel);
		}

	}
	//	return super.add(comp);
	//	}
	@Override
	public UIComponent add(FieldRecord record) throws Exception {
		Column column = new Column();
		column.setColumnName(record.getField("COLUMN_ID").getString());
		column.setCaption(record.getField("COLUMN_NAME").getString());
		column.setScale(record.getField("PRECISE").getNumber().intValue());
		column.setPrecision(record.getField("LENGTH").getNumber().intValue()  );
		column.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
		column.setDataType(DataTypeTool.getType(  record.getField("DATA_TYPE").getString()));
		if(record.getField("CHAR_CASE").getString()!=null){
			if (record.getField("CHAR_CASE").getString().equalsIgnoreCase("U"))
				column.setCharacterCase(com.borland.dx.dataset.CharacterCase.upperCase);
			else if (record.getField("CHAR_CASE").getString().equalsIgnoreCase("L"))
				column.setCharacterCase(com.borland.dx.dataset.CharacterCase.lowerCase);
			else
				column.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);
			//			CharacterCase.
			//			com.borland.dx.dataset.CharacterCase cass=null;
			//			column.setCharacterCase(cass);
		}
		if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
			column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		}
		if(componentMap.containsKey(record.getField("TABS_ID").getString()+"#"+record.getField("COLUMN_ID").getString())){
			return null;
		}
		int lastCol=1;
		int allIndex=1;
		//		this.removeAll();//全部清空，重绘
		int width=1;
		for(UIComponent com:sortItem() ){
			width=com.getWidth();
			lastCol=lastCol+com.getWidth();
			allIndex=allIndex+com.getWidth();
			if(lastCol>colNum){
				allIndex=allIndex-(lastCol-colNum)+1;
				lastCol=1;
			}
		}
		int row=  (int) Math.ceil((allIndex+0.0)/colNum);
		int[] rowHeights=new int[row*2+1];
		for(int i=0;i<row*2+1;i++){
			rowHeights[i]=5;
		}
		int col=colNum*3+1;
		int[] columnWidths=new int[col];
		for(int i=0;i<col;i++){
			columnWidths[i]=5;
		}
		layout.rowHeights=rowHeights;
		layout.columnWidths=columnWidths;
		this.panel.setLayout(layout);
		ULabel label=new ULabel(this.pageId,record.getField("COLUMN_ID").getString());
		label.setText(record.getField("COLUMN_NAME").getString()+":");
		if(BoolStr.TRUE.equals( record.getField("IS_MUST").getString())){
			label.setForeground(SystemColor.activeCaption);
		}
		for(int i=0;i<colNum;i++){
			this.panel.add(new JLabel(),
					new GridBagConstraints((3*i)+2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		}
		width=record.getField("WIDTH")==null ||record.getField("WIDTH").getNumber()==null || record.getField("WIDTH").getNumber().compareTo(BigDecimal.ZERO)==0?1:record.getField("WIDTH").getNumber().intValue();
		//判断类型
		JComponent field=null;
		//判断显示方式，如果下拉
		if(UIComponent.BOOL.equals(record.getField("VIEW_TYPE").getString())){
			field=new JdbCheckBox();
			//			//column.setDataType(com.borland.dx.dataset.Variant.BOOLEAN);
		}else if(UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
			//如果是下拉框
			field=new JdbComboBox();
			//			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
			//			column.setItemList(new ItemListDescriptor(new Object[]{},true));
		}else if(UIComponent.DATE.equals(record.getField("VIEW_TYPE").getString())){
			//如果是日期
			field =new JdbDatePicker();
			//			//column.setDataType(com.borland.dx.dataset.Variant.DATE);
		}
		else if(UIComponent.DATATIME.equals(record.getField("VIEW_TYPE").getString())){
			//时间戳
			field =new JdbDateTimePicker();
			//			//column.setDataType(com.borland.dx.dataset.Variant.TIMESTAMP);
		}else if(UIComponent.IMAGE.equals(record.getField("VIEW_TYPE").getString())){
			//图片暂时没写，后面加后面加，记得记得
		}else if(UIComponent.OBJECT.equals(record.getField("VIEW_TYPE").getString())){
			//object暂时没写，后面加后面加，记得记得
			//			//column.setDataType(com.borland.dx.dataset.Variant.OBJECT);
		}else if(UIComponent.PASS_TEXT.equals(record.getField("VIEW_TYPE").getString())){
			field=new JdbTextField();
			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
		}else if(UIComponent.REFERENCE.equals(record.getField("VIEW_TYPE").getString())){
			field=new ReferencePanel();
			//			((ReferencePanel)field).setDataSet(storageDataSet);
			//			((ReferencePanel)field).setColumnName(record.getField("COLUMN_ID").getString());
			//			//column.setDataType(com.borland.dx.dataset.Variant.BOOLEAN);
			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
			column.setCustomEditable(true);
			EasyColumnCustomEditListener custListener= 	new EasyColumnCustomEditListener(record.getRecord());
			column.addColumnCustomEditListener(custListener);
			column.addColumnChangeListener(custListener);
			//			field=new JPanel();
			//			((JPanel)field).setLayout(new BorderLayout());
			//			JdbTextField btn=new JdbTextField();
			//			btn.setDataSet(storageDataSet);
			//			btn.setColumnName(record.getField("COLUMN_ID").getString());
			//			field.add(btn);
			//			((ReferencePanel)field).setDataSet(dataset);
			//			((ReferencePanel)field).setDataSet(dataset)
		}else if(UIComponent.TEXT.equals(record.getField("VIEW_TYPE").getString())){
			field=new JdbTextField();
			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
		}else if(UIComponent.TEXTAREA.equals(record.getField("VIEW_TYPE").getString())){
			field=new JdbTextArea();
			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
		}else if(UIComponent.TIME.equals(record.getField("VIEW_TYPE").getString())){
			field=new JTimePicker();
			//column.setDataType(com.borland.dx.dataset.Variant.TIME);
		}else{
			field=new JdbTextField();
			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
		}
		//如果是代码，则要虚拟一列
		if(DataSourceConfig.SOURCE_CODE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
			storageDataSet.addColumn(column);
			column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);//隐藏当前列
			//添加虚拟动态的列
			Column column2 = new Column();
			column2.setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
			column2.setCaption(record.getField("COLUMN_NAME").getString());
			column2.setScale(50);
			column2.setPrecision(0);
			column2.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
			//			column2.setDataType(DataType.TYPE_VARCHAR);
			column2.setDataType(com.borland.dx.dataset.Variant.STRING);
			column2.setCalcType(3);
			//			column2.set
			StorageDataSet storagedataset1 = CodeTable.defaultCodeTable.getDataSet(record.getField("CODE_TYPE").getString());
			column2.setPickList(new PickListDescriptor(storagedataset1, new String[] {
					"CODE"
			}, new String[] {
					"DESCRIPTION"
			}, new String[] {
					record.getField("COLUMN_ID").getString()
			}, "DESCRIPTION", true));
			if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
				column2.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
			}
			storageDataSet.addColumn(column2);
			((JdbComboBox)	field).setDataSet(storageDataSet);
			((JdbComboBox)	field).setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
		}else if(DataSourceConfig.SOURCE_PHRASE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
			//如果是下拉
			RecordSet set=	SysPhraseHelper.getRecordSet(record.getField("PH_TYPE").getString());
			boolean isedit=BoolStr.getBoolean(record.getField("COMBO_IS_EDIT").getString());
			String[] s=null;
			if(set!=null){
				s=new String[set.recordCount()];
				for(int i=0;i<set.recordCount();i++){
					s[i]=set.getRecord(i).getField("PHRASE").getString();
				}}
			((JComboBox<?>)	field).setEditable(isedit);
			column.setItemList(new ItemListDescriptor(s,isedit));
			storageDataSet.addColumn(column);
		}else if(DataSourceConfig.SOURCE_ZDY.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
			//如果是下拉，且是自定义的。。。
			//获取自定义值
			String[] items=record.getField("ZDY_ITEMS").getString().split(",");
			StorageDataSet dataset=new StorageDataSet();
//			DataSetHelper.loadMetaFromRecordFormat(dataset, codeformat);
			RecordSet set=new RecordSet(codeformat);
			if(DataSourceConfig.KEY_VALUE.equals(record.getField("VALUE_TYPE").getString())){
				storageDataSet.addColumn(column);
				column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);//隐藏当前列
//				dataset.open();
				for(int i=0;i<items.length;i++){
					String[] item=items[i].split("=");
//					dataset.insertRow(false);
//					dataset.setString("CODE", item[0]);
//					dataset.setString("DESCRIPTION", item[1]);
					Record red=set.append();
					red.getField("CODE").setString(item[0]);
					red.getField("DESCRIPTION").setString(item[1]);
				}
				DataSetHelper.loadFromRecordSet(dataset, set);
				Column column2 = new Column();
				column2.setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
				column2.setCaption(record.getField("COLUMN_NAME").getString());
				column2.setScale(50);
				column2.setPrecision(0);
				column2.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
				//			column2.setDataType(DataType.TYPE_VARCHAR);
				column2.setDataType(com.borland.dx.dataset.Variant.STRING);
				column2.setCalcType(3);
				column2.setPickList(new PickListDescriptor(dataset, new String[] {
						"CODE"
				}, new String[] {
						"DESCRIPTION"
				}, new String[] {
						record.getField("COLUMN_ID").getString()
				}, "DESCRIPTION", true));
				if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
					column2.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
				}
				storageDataSet.addColumn(column2);
				((JdbComboBox)	field).setDataSet(storageDataSet);
				((JdbComboBox)	field).setColumnName(column2.getColumnName());
			}else{
				boolean isedit=BoolStr.getBoolean(record.getField("COMBO_IS_EDIT").getString());
				((JdbComboBox)	field).setEditable(isedit);
				//用Object数组
//				String[] obj=new String[items.length];
//				for(int i=0;i<items.length;i++){
//				obj[i]=items[i];
//				}
				for(int i=0;i<items.length;i++){
					Record red=set.append();
					red.getField("CODE").setString(items[0]);
				}
				DataSetHelper.loadFromRecordSet(dataset, set);
				column.setItemList(new ItemListDescriptor(dataset, "CODE", isedit));
//				column.setItemList(new ItemListDescriptor(obj,isedit));
				storageDataSet.addColumn(column);
			}
		}else {
			storageDataSet.addColumn(column);
		}
		if(BoolStr.TRUE.equals(record.getField("IS_CARD_SHOW").getString())){
			//dbswing所有组件所有都继承了ColumnAware接口
			if(field instanceof ColumnAware){
				if(((ColumnAware)field).getColumnName()==null){
					((ColumnAware)field).setDataSet(storageDataSet);
					((ColumnAware)field).setColumnName(record.getField("COLUMN_ID").getString());
				}
			}else{
				if(((ReferencePanel)field).getColumnName()==null){
					((ReferencePanel)field).setDataSet(storageDataSet);
					((ReferencePanel)field).setColumnName(record.getField("COLUMN_ID").getString());
				}
			}
			this.panel.add(label, new GridBagConstraints(lastCol*3-2, row*2-1, 1, 1,
					0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
			this.panel.add(field,
					new GridBagConstraints(lastCol*3-1, row*2-1, width*3-2, 1, 1.0, 0.0,GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
			this.panel.add(bothLabel,
					new GridBagConstraints(lastCol*3-1, row*2, width*3-2, 1, 1.0, 1,GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));
			this.updateUI();
			UIComponent com=new UIComponent();
			com.setLable(label);
			com.setComponent(field);
			com.setSequence(getMaxSequence()+1);
			com.setWidth(width);
			componentMap.put(this.getPageId()+"#"+record.getField("COLUMN_ID").getString(), com);
			return com;
		}
		return null;
	}



	public void batchAdd(List<Record> recordList) throws Exception {
		int row=0;
		for(int j=0;j<recordList.size();j++){
			long startTime=System.currentTimeMillis();
			Record record=recordList.get(j);
			Column column = new Column();
			column.setColumnName(record.getField("COLUMN_ID").getString());
			column.setCaption(record.getField("COLUMN_NAME").getString());
			column.setScale(record.getField("PRECISE").getNumber().intValue());
			column.setPrecision(record.getField("LENGTH").getNumber().intValue()  );
			column.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
			column.setDataType(DataTypeTool.getType(  record.getField("DATA_TYPE").getString()));
			if(record.getField("CHAR_CASE").getString()!=null){
				if (record.getField("CHAR_CASE").getString().equalsIgnoreCase("U"))
					column.setCharacterCase(com.borland.dx.dataset.CharacterCase.upperCase);
				else if (record.getField("CHAR_CASE").getString().equalsIgnoreCase("L"))
					column.setCharacterCase(com.borland.dx.dataset.CharacterCase.lowerCase);
				else
					column.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

				//			CharacterCase.
				//			com.borland.dx.dataset.CharacterCase cass=null;
				//			column.setCharacterCase(cass);
			}
			if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
				column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
			}

			if(componentMap.containsKey(record.getField("TABS_ID").getString()+"#"+record.getField("COLUMN_ID").getString())){
				continue ;
			}

			//如果是代码，则要虚拟一列
			if(DataSourceConfig.SOURCE_CODE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				listColumn.add(column);
				column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);//隐藏当前列
				//添加虚拟动态的列
				Column column2 = new Column();
				column2.setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
				column2.setCaption(record.getField("COLUMN_NAME").getString());
				column2.setScale(50);
				column2.setPrecision(0);
				column2.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
				//			column2.setDataType(DataType.TYPE_VARCHAR);
				column2.setDataType(com.borland.dx.dataset.Variant.STRING);
				column2.setCalcType(3);
				//			column2.set
				StorageDataSet storagedataset1 = CodeTable.defaultCodeTable.getDataSet(record.getField("CODE_TYPE").getString());
				column2.setPickList(new PickListDescriptor(storagedataset1, new String[] {
						"CODE"
				}, new String[] {
						"DESCRIPTION"
				}, new String[] {
						record.getField("COLUMN_ID").getString()
				}, "DESCRIPTION", true));
				if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
					column2.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
				}

				listColumn.add(column2);
//				((JdbComboBox)	field).setDataSet(storageDataSet);
//				((JdbComboBox)	field).setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");

			}else if(DataSourceConfig.SOURCE_PHRASE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				//如果是下拉
				RecordSet set=	SysPhraseHelper.getRecordSet(record.getField("PH_TYPE").getString());
				boolean isedit=BoolStr.getBoolean(record.getField("COMBO_IS_EDIT").getString());
				String[] s=null;
				if(set!=null){
					s=new String[set.recordCount()];
					for(int i=0;i<set.recordCount();i++){
						s[i]=set.getRecord(i).getField("PHRASE").getString();
					}}
//				((JComboBox<?>)	field).setEditable(isedit);
				column.setItemList(new ItemListDescriptor(s,isedit));
				listColumn.add(column);

			}else if(DataSourceConfig.SOURCE_ZDY.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				//如果是下拉，且是自定义的。。。
				//获取自定义值
				String[] items=record.getField("ZDY_ITEMS").getString().split(",");
				StorageDataSet dataset=new StorageDataSet();
				//			DataSetHelper.loadMetaFromRecordFormat(dataset, codeformat);
				RecordSet set=new RecordSet(codeformat);
				if(DataSourceConfig.KEY_VALUE.equals(record.getField("VALUE_TYPE").getString())){
					listColumn.add(column);
					column.setVisible(com.borland.jb.util.TriStateProperty.FALSE);//隐藏当前列
					//				dataset.open();
					for(int i=0;i<items.length;i++){
						String[] item=items[i].split("=");
						Record red=set.append();
						red.getField("CODE").setString(item[0]);
						red.getField("DESCRIPTION").setString(item[1]);
					}
					DataSetHelper.loadFromRecordSet(dataset, set);

					Column column2 = new Column();
					column2.setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
					column2.setCaption(record.getField("COLUMN_NAME").getString());
					column2.setScale(record.getField("PRECISE").getNumber().intValue());
					column2.setPrecision(0);
					column2.setWidth(record.getField("DEFAULT_WIDTH").getNumber().intValue());
					//			column2.setDataType(DataType.TYPE_VARCHAR);
					column2.setDataType(com.borland.dx.dataset.Variant.STRING);
					column2.setCalcType(3);
					column2.setPickList(new PickListDescriptor(dataset, new String[] {
							"CODE"
					}, new String[] {
							"DESCRIPTION"
					}, new String[] {
							record.getField("COLUMN_ID").getString()
					}, "DESCRIPTION", true));
					if(!BoolStr.TRUE.equals(record.getField("IS_LIST_SHOW").getString())){
						column2.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
					}
					//是否允许编辑
					if(BoolStr.FALSE.equals(record.getField("IS_EDIT").getString())){
						column2.setEditable(false);
					}
					listColumn.add(column2);
//					((JdbComboBox)	field).setDataSet(storageDataSet);
//					((JdbComboBox)	field).setColumnName(column2.getColumnName());
				}else{
					boolean isedit=BoolStr.getBoolean(record.getField("COMBO_IS_EDIT").getString());
//					((JdbComboBox)	field).setEditable(isedit);
					//用Object数组
//					String[] obj=new String[items.length];
//					for(int i=0;i<items.length;i++){
//					obj[i]=items[i];
//					}
					for(int i=0;i<items.length;i++){
						Record red=set.append();
						red.getField("CODE").setString(items[0]);
					}
					DataSetHelper.loadFromRecordSet(dataset, set);

					column.setItemList(new ItemListDescriptor(dataset, "CODE", isedit));
//					column.setItemList(new ItemListDescriptor(obj,isedit));
					listColumn.add(column);

				}
			}else {
				listColumn.add(column);
			}

		}


		if(storageDataSet.getColumnCount()==0){
			this.storageDataSet.open();
			this.storageDataSet.close();
			this.storageDataSet.setColumns(listColumn.toArray(new Column[0]));
			this.storageDataSet.open();
		}else{
			storageDataSet.getColumns();
			ArrayList<Column> arrayList = new ArrayList<Column>(Arrays.asList(storageDataSet.getColumns()));
			arrayList.addAll(listColumn);
			listColumn=arrayList;
//			this.storageDataSet.open();
			this.storageDataSet.close();
			this.storageDataSet.setColumns(listColumn.toArray(new Column[0]));
			this.storageDataSet.open();
//			list.addAll(storageDataSet.getColumns());
//			for(int z=0;z<listColumn.size();z++){
//			this.storageDataSet.addColumn(listColumn.get(z));
//			}
		}


		for(int j=0;j<recordList.size();j++){
			Record record=recordList.get(j);

			int lastCol=1;
			int allIndex=1;
			//		this.removeAll();//全部清空，重绘
			int width=1;
			for(UIComponent com:sortItem() ){
				width=com.getWidth();
				lastCol=lastCol+com.getWidth();
				allIndex=allIndex+com.getWidth();
				if(lastCol>colNum){
					allIndex=allIndex-(lastCol-colNum)+1;
					lastCol=1;
				}
			}
			row=  (int) Math.ceil((allIndex+0.0)/colNum);

			this.panel.setLayout(layout);
			ULabel label=new ULabel(this.pageId,record.getField("COLUMN_ID").getString());
			label.setText(record.getField("COLUMN_NAME").getString()+":");
			if(BoolStr.TRUE.equals( record.getField("IS_MUST").getString())){
				label.setForeground(SystemColor.activeCaption);
			}


			for(int i=0;i<colNum;i++){
				this.panel.add(new JLabel(),
						new GridBagConstraints((3*i)+2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
			}
			width=record.getField("WIDTH")==null ||record.getField("WIDTH").getNumber()==null || record.getField("WIDTH").getNumber().compareTo(BigDecimal.ZERO)==0?1:record.getField("WIDTH").getNumber().intValue();
			//判断类型
			JComponent field=null;
			//判断显示方式，如果下拉
			if(UIComponent.BOOL.equals(record.getField("VIEW_TYPE").getString())){
				field=new JdbCheckBox();
				//			//column.setDataType(com.borland.dx.dataset.Variant.BOOLEAN);

			}else if(UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				//如果是下拉框
				field=new JdbComboBox();
				//			//column.setDataType(com.borland.dx.dataset.Variant.STRING);
				//			column.setItemList(new ItemListDescriptor(new Object[]{},true));

			}else if(UIComponent.DATE.equals(record.getField("VIEW_TYPE").getString())){
				//如果是日期
				field =new JdbDatePicker();
				//			//column.setDataType(com.borland.dx.dataset.Variant.DATE);
			}
			else if(UIComponent.DATATIME.equals(record.getField("VIEW_TYPE").getString())){
				//时间戳
				field =new JdbDateTimePicker();
				//			//column.setDataType(com.borland.dx.dataset.Variant.TIMESTAMP);

			}else if(UIComponent.IMAGE.equals(record.getField("VIEW_TYPE").getString())){
				//图片暂时没写，后面加后面加，记得记得
			}else if(UIComponent.OBJECT.equals(record.getField("VIEW_TYPE").getString())){
				//object暂时没写，后面加后面加，记得记得
				//			//column.setDataType(com.borland.dx.dataset.Variant.OBJECT);
			}else if(UIComponent.PASS_TEXT.equals(record.getField("VIEW_TYPE").getString())){
				field=new JdbTextField();
				//column.setDataType(com.borland.dx.dataset.Variant.STRING);
			}else if(UIComponent.REFERENCE.equals(record.getField("VIEW_TYPE").getString())){
				field=new ReferencePanel();

				//			((ReferencePanel)field).setDataSet(storageDataSet);
				//			((ReferencePanel)field).setColumnName(record.getField("COLUMN_ID").getString());
				//			//column.setDataType(com.borland.dx.dataset.Variant.BOOLEAN);
				//column.setDataType(com.borland.dx.dataset.Variant.STRING);
				Column column=storageDataSet.getColumn(record.getField("COLUMN_ID").getString());
				column.setCustomEditable(true);
				EasyColumnCustomEditListener custListener= 	new EasyColumnCustomEditListener(record);
				column.addColumnCustomEditListener(custListener);
				column.addColumnChangeListener(custListener);

				//			field=new JPanel();
				//			((JPanel)field).setLayout(new BorderLayout());
				//			JdbTextField btn=new JdbTextField();
				//			btn.setDataSet(storageDataSet);
				//			btn.setColumnName(record.getField("COLUMN_ID").getString());
				//			field.add(btn);
				//			((ReferencePanel)field).setDataSet(dataset);
				//			((ReferencePanel)field).setDataSet(dataset)
			}else if(UIComponent.TEXT.equals(record.getField("VIEW_TYPE").getString())){
				field=new JdbTextField();

			}else if(UIComponent.TEXTAREA.equals(record.getField("VIEW_TYPE").getString())){
				field=new JdbTextArea();

			}else if(UIComponent.TIME.equals(record.getField("VIEW_TYPE").getString())){
				field=new JTimePicker();
			}else{
				field=new JdbTextField();
			}

			//如果是代码，则要虚拟一列
			if(DataSourceConfig.SOURCE_CODE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){

				((JdbComboBox)	field).setDataSet(storageDataSet);
				((JdbComboBox)	field).setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");

			}else if(DataSourceConfig.SOURCE_PHRASE.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				//如果是下拉
				boolean isedit=BoolStr.getBoolean(record.getField("COMBO_IS_EDIT").getString());
				((JComboBox<?>)	field).setEditable(isedit);

			}else if(DataSourceConfig.SOURCE_ZDY.equals( record.getField("DATA_SOURCE").getString())&&UIComponent.COMBO.equals(record.getField("VIEW_TYPE").getString())){
				//如果是下拉，且是自定义的。。。
				//获取自定义值
				String[] items=record.getField("ZDY_ITEMS").getString().split(",");
				StorageDataSet dataset=new StorageDataSet();
				//			DataSetHelper.loadMetaFromRecordFormat(dataset, codeformat);
				if(DataSourceConfig.KEY_VALUE.equals(record.getField("VALUE_TYPE").getString())){
					((JdbComboBox)	field).setDataSet(storageDataSet);
					((JdbComboBox)	field).setColumnName(record.getField("COLUMN_ID").getString()+"$DESC");
				}else{
					((JdbComboBox)	field).setDataSet(storageDataSet);
					((JdbComboBox)	field).setColumnName(record.getField("COLUMN_ID").getString());
				}
			}
			if(BoolStr.TRUE.equals(record.getField("IS_CARD_SHOW").getString())){
				//dbswing所有组件所有都继承了ColumnAware接口
				if(field instanceof ColumnAware){

					if(((ColumnAware)field).getColumnName()==null){
						((ColumnAware)field).setDataSet(storageDataSet);
						((ColumnAware)field).setColumnName(record.getField("COLUMN_ID").getString());
					}
				}else{
					if(((ReferencePanel)field).getColumnName()==null){
						((ReferencePanel)field).setDataSet(storageDataSet);
						((ReferencePanel)field).setColumnName(record.getField("COLUMN_ID").getString());
					}
				}

				this.panel.add(label, new GridBagConstraints(lastCol*3-2, row*2-1, 1, 1,
						0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
				try{
					field.setVisible(true);
					this.panel.add(field,
							new GridBagConstraints(lastCol*3-1, row*2-1, width*3-2, 1, 1.0, 0.0,GridBagConstraints.CENTER,
									GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
				}
				catch(Exception e){
					e.printStackTrace();
				}

				UIComponent com=new UIComponent();
				com.setLable(label);
				com.setComponent(field);
				com.setSequence(getMaxSequence()+1);
				com.setWidth(width);
				componentMap.put(this.getPageId()+"#"+record.getField("COLUMN_ID").getString(), com);
			}
		}



		int[] rowHeights=new int[row*2+1];
		//
		for(int i=0;i<row*2+1;i++){
			rowHeights[i]=5;
		}
		int col=colNum*3+1;

		int[] columnWidths=new int[col];
		for(int i=0;i<col;i++){
			columnWidths[i]=5;
		}
		layout.rowHeights=rowHeights;
		layout.columnWidths=columnWidths;
		panel.setLayout(layout);

		this.panel.add(bothLabel,
				new GridBagConstraints(1, row*2, 0, 1, 0, 1,GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));


		this.updateUI();
	}


	public UIComponent remove(String attrId) {
		UIComponent com=componentMap.remove(attrId);
		this.panel.remove(com.getLable());//删除组件
		this.panel.remove(com.getComponent());//删除组件
		this.updateUI();
		return com;
	}
	public void clear(){
		componentMap.clear();
		this.panel.removeAll();
		this.panel.updateUI();
	}

	public JPanel getTablePanel() {
		return tablePanel;
	}
	public JToolBar getTableBar() {
		return tableBar;
	}
	public JButton getNewButton() {
		return newButton;
	}
	public JButton getDeleteButton() {
		return deleteButton;
	}
	public JButton getClearButton() {
		return clearButton;
	}
	public JButton getBatchButton() {
		return batchButton;
	}
	public int getIndex() {
		return index;
	}

	private class NewAction extends AbstractAction{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		NewAction()
		{
			super(null);
			if (!Beans.isDesignTime()) {
				putValue(
						"SmallIcon",
						new ImageIcon(
								StorageDataSetPanel.class
								.getClassLoader()
								.getResource(
								"com/evangelsoft/workbench/resources/buttons/addDetail.png")));
			}
			putValue("ShortDescription", DataModel.getDefault()
					.getCaption("NEW_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			try {
				storageDataSet.insertRow(false);
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(storageDataSet,
									table, ex));
					return;
				}
				throw ex;
			}
		}
	}
	private class DeleteAction extends AbstractAction{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		DeleteAction()
		{
			if (!Beans.isDesignTime()) {
				putValue(
						"SmallIcon",
						new ImageIcon(
								StorageDataSetPanel.class
								.getClassLoader()
								.getResource(
								"com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
			}
			putValue("ShortDescription", DataModel.getDefault()
					.getCaption("DELETE_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = table.getDataSet();
			if (dataSet == null) {
				dataSet = storageDataSet;
			}
			if ((!dataSet.isOpen()) || (
					(dataSet.isEmpty()) && (!dataSet.isEditingNewRow()))) {
				return;
			}
			dataSet.deleteRow();
		}
	}
	private class ClearAction extends AbstractAction{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		ClearAction()
		{
			super(null);
			if (!Beans.isDesignTime()) {
				putValue("SmallIcon",
						new ImageIcon(StorageDataSetPanel.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
			}
			putValue("ShortDescription", DataModel.getDefault()
					.getCaption("CLEAR_LINES"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = table.getDataSet();
			if (dataSet == null) {
				dataSet = storageDataSet;
			}
			if ((!dataSet.isOpen()) || (
					(dataSet.isEmpty()) && (!dataSet.isEditingNewRow()))) {
				return;
			}
			storageDataSet.deleteAllRows();
		}
	}
	@Override
	public void updateUI() {
		super.updateUI();
		//如果没有设置批量事件，则将批量按钮隐藏
		if(batchButton!=null&&batchButton.getAction()==null){
			batchButton.setVisible(false);
		}
	}


//	int getType(String type){
//	int i=DataType.getType(type);
//	int j;
//	switch (i)
//	{
//	case 1:
//	j = 10;
//	break;
//	case 11:
//	j = 13;
//	break;
//	case 12:
//	j = 14;
//	break;
//	case 13:
//	j = 15;
//	break;
//	case 21:
//	case 22:
//	case 23:
//	j = 16;
//	break;
//	case 31:
//	case 32:
//	case 33:
//	j = 12;
//	break;
//	default:
//	j = 0;
//	}
//	return j;
//	}

	public StorageDataSet getStorageDataSet() {
		return storageDataSet;
	}

	public void setStorageDataSet(StorageDataSet storageDataSet) {
		this.storageDataSet = storageDataSet;
	}

	public void setNewButton(JButton newButton) {
		this.newButton = newButton;
	}

	public AbstractAction getNewAction() {
		return newAction;
	}
	public void setNewAction(AbstractAction newAction) {
		this.newAction = newAction;
	}
	public AbstractAction getDeleteAction() {
		return deleteAction;
	}
	public void setDeleteAction(AbstractAction deleteAction) {
		this.deleteAction = deleteAction;
	}
	public AbstractAction getClearAction() {
		return clearAction;
	}
	public void setClearAction(AbstractAction clearAction) {
		this.clearAction = clearAction;
	}

	public AbstractAction getBatchAction() {
		return batchAction;
	}
	public void setBatchAction(AbstractAction batchAction) {
		this.batchAction = batchAction;
		this.batchButton.addActionListener(batchAction);
	}


}
