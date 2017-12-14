package com.evangelsoft.easyui.template.client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.commons.lang.StringUtils;

import com.borland.dbswing.JdbButton;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextArea;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.ColumnCustomEditListener;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.ExceptionEvent;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.config.client.SysTableFrame;
import com.evangelsoft.easyui.config.intf.SysTable;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.easyui.template.intf.BaseSingleDataSet;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.tool.ColumnsHelp;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.TransientRecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.workbench.clientdataset.DataAwareException;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.config.client.SysCodeHelper;
import com.evangelsoft.workbench.framebase.MasterDetailFrame;
import com.evangelsoft.workbench.security.client.SysPrivilegeFrame;
import com.evangelsoft.workbench.security.intf.SysPrivilege;

public class SysTemplateFrame extends MasterDetailFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 7429125198736132005L;

	public SysTemplateFrame(){
		super();
		try{
			initialize();
		}catch(Exception e){
			e.printStackTrace();
		}
		pack();
	}

	//过滤条件的控件
	JLabel templateidLabel;//模板编号
	JTextField templateIdField;

	JLabel templateNameLabel;//模板名称
	JTextField templateNameField;






	//主表绑定数据集
	JLabel masterTemplateNumLabel;//模板编号
	JdbTextField masterTemplateNumField;

	JLabel masterTemplateNumNameLabel;//模板名称
	JdbTextField masterTemplateNameField;



	JLabel tableIdLabel;//主表ID
	JdbTextField tableIdField;
	JdbButton tableIdButton;

	JLabel tableNameLabel;//主表名称
	JdbTextField tableNameField;


	JLabel masterModeLabel;//表单模式
	JdbComboBox masterModeField;

	JLabel masterViewModeLabel;
	JdbComboBox masterViewModeField;

	private JLabel defaultWidthLabel;//默认宽度
	private JdbTextField defaultWidthField;
	private JLabel defaultHeightLabel;//默认高度
	private JdbTextField defaultheightField;

	JLabel masterPrivNumLabel;//权限
	JdbButton privButton;
	JdbTextField masterPrivNumField;

	JLabel masterPrivNameLabel;//权限名称
	JdbTextField masterPrivNameField;

	JLabel masterRemaksLabel;//备注
	JdbTextField masterRemaksField;

	JLabel masterMainSqlLabel;//主表SQL
	JdbTextArea masterMainSqlsField;

	JLabel masterWhereSqlLabel;//固定条件
	JdbTextField masteWhereSqlField;
	JLabel masterGroupSqlLabel;//分组
	JdbTextField masterGroupSqlsField;
	JLabel masterSortSqlLabel;//排序
	JdbTextField masterSortSqlsField;


	JLabel masterMainKeyLabel;//主键
	JdbTextField masterMainKeyField;
	//	JLabel masterMainSqlLabel;//主表SQL
	//	JdbTextArea masterMainSqlsField;

	private StorageDataSet frameDataSet;//数据集
	private StorageDataSet viewModeDataSet;


	private JButton design;//设计


	@Override
	protected void prepared(Object arg0) throws Exception {
		//数据集设置初始数据
		DataSetHelper.loadFromRecordSet(frameDataSet,
				SysCodeHelper.getRecordSet("FRAME_MODE"));
		DataSetHelper.loadFromRecordSet(viewModeDataSet,
				SysCodeHelper.getRecordSet("VIEW_MODE"));
		DataSetHelper.loadFromRecordSet(dataTypeDataSet,
				SysCodeHelper.getRecordSet("DATA_TYPE"));
		DataSetHelper.loadFromRecordSet(boolStrDataSet,
				SysCodeHelper.getRecordSet("BOOLEAN"));
		DataSetHelper.loadFromRecordSet(componentDataSet,
				SysCodeHelper.getRecordSet("COMPONENT_TYPE"));
		DataSetHelper.loadFromRecordSet(pageTypeDataSet,
				SysCodeHelper.getRecordSet("PAGE_TYPE"));
		//大小写
		DataSetHelper.loadFromRecordSet(charCaseataSet ,
				SysCodeHelper.getRecordSet("CHAR_CASE"));
		DataSetHelper.loadFromRecordSet(valueTypeDaraSet ,
				SysCodeHelper.getRecordSet("VALUE_TYPE"));
		DataSetHelper.loadFromRecordSet(valueSourceDaraSet ,
				SysCodeHelper.getRecordSet("DEFAULT_VALUE_SOURCE"));
		DataSetHelper.loadFromRecordSet(printActionDaraSet ,
				SysCodeHelper.getRecordSet("PRINT_ACTION"));
	}

	@Override
	protected Object prepareData() throws Exception {

		// 指定当前窗口处理的实体对应的类。
		entityClass = SysTemplate.class;
		// 指定主数据集的键值，用于获取明细数据集，以及重复判断。
		keyColumns = new String[] { "TEMPLATE_ID" };
		// 指定明细数据集的键值，用于重复判断。
		detailKeyColumns.put(detailDataSet, new String[] { "TABLE_ID" });
		return super.prepareData();
	}
	Record tableRecord;
	Record privRecord;
	//初始化界面

	StorageDataSet tabsDataSet;//页签
	private JdbTable tabsTabel;
	StorageDataSet attributeDataSet;//属性
	private JdbTable attributeTabel;


	//打印数据集
	JPanel printpanel;
	JPanel printFooterPanel;
	JPanel printFooterRightPanel;
	JLabel printRowCountLabel;
	JToolBar printToolBar;
	StorageDataSet printDataSet;//页签
	Record printRecord;
	private JdbTable printTable;
	JButton addPrintButton;
	JButton deletePrintButton;
	JButton cleraPrintButton;


	//打印数据集
	JPanel buttonPanel;
	JPanel buttonFooterPanel;
	JPanel buttonFooterRightPanel;
	JLabel buttonRowCountLabel;
	JToolBar buttonToolBar;
	StorageDataSet buttonDataSet;//页签
	Record buttonRecord;
	JdbTable buttonTable;
	JButton addButton;
	JButton deleteButton;
	JButton cleraButton;



	private StorageDataSet dataTypeDataSet;//数据类型数据集
	private StorageDataSet boolStrDataSet;//bool类型数据集
	private StorageDataSet charCaseataSet;//大小写

	private StorageDataSet componentDataSet;//
	private StorageDataSet pageTypeDataSet;//页签类型
	private StorageDataSet valueTypeDaraSet;//值类型
	private StorageDataSet valueSourceDaraSet;//默认值来源

	private StorageDataSet printActionDaraSet;//默认值来源


	void initialize() throws Exception{

		//初始化数据集
		dataTypeDataSet=new StorageDataSet();
		boolStrDataSet=new StorageDataSet();
		componentDataSet=new StorageDataSet();
		pageTypeDataSet=new StorageDataSet();
		charCaseataSet=new StorageDataSet();
		valueTypeDaraSet=new StorageDataSet();
		valueSourceDaraSet=new StorageDataSet();
		printActionDaraSet=new StorageDataSet();

		//		this.saveButton.setAction(null);
		//		this.saveButton.setAction(saveAction);

		//设置title
		setTitle(DataModel.getDefault().getCaption("SYS_TEMPLATE"));
		//设置页面大小
		listTablePane.setPreferredSize(new Dimension(
				listTable.getRowHeight() * 15, listTable.getRowHeight() * 9));
		detailPane.setPreferredSize(new Dimension(detailTable
				.getRowHeight() * 25, detailTable.getRowHeight() * 9));
		frameDataSet=new StorageDataSet();
		viewModeDataSet=new StorageDataSet();

		//构建表数据列
		Column templateId=new Column("SYS_TEMPLATE.TEMPLATE_ID");

		Column templateNum=new Column("SYS_TEMPLATE.TEMPLATE_NUM");

		Column templateName=new Column("SYS_TEMPLATE.TEMPLATE_NAME");

		Column mainTableId=new Column("SYS_TEMPLATE.MAIN_TABLE_ID");
		mainTableId.addColumnChangeListener(new ColumnChangeAdapter(){

			public void validate(DataSet dataset, Column column, Variant value)
					throws Exception, DataSetException {
				if(value==null||value.getString()==null||"".equals(value.getString())){
					tableRecord=null;
					dataset.setAssignedNull("MAIN_TABLE_ID");
					dataset.setAssignedNull("TEMPLATE_NAME");
				} else if(value!=null&&!"".equals(value.getString())&&(tableRecord==null||!tableRecord.getField("TABLE_ID").getString().equals(value.getString()))){
					SysTable table=(SysTable)(new RMIProxy(
							Consumer.getDefaultConsumer().getSession()))
							.newStub(SysTable.class);
					VariantHolder<Object> data=new VariantHolder<Object>();
					data.value=new RecordSet();
					VariantHolder<String> errMsg=new VariantHolder<String>();
					if(!table.getTable(value.getString(), data, errMsg)){
						throw new DataAwareException(
								DataAwareException.INVALID_COLUMN_VALUE, errMsg.value, column, null);
					}
					tableRecord=((RecordSet)data.value).getRecord(0);

				}
				if(tableRecord!=null){
					dataset.setString("MAIN_TABLE_ID", tableRecord.getField("TABLE_ID").getString());
					dataset.setString("TABLE_NAME", tableRecord.getField("TABLE_NAME").getString());
				}

			}
		});
		mainTableId.addColumnCustomEditListener(new ColumnCustomEditListener(){
			public Variant customEdit(DataSet dataset, Column column) {
				SysTableFrame frame=new SysTableFrame();
				RecordSet set =frame.select(SysTemplateFrame.this, null, false,true);
				if(set!=null&&set.recordCount()>0){
					tableRecord=set.getRecord(0);
					//					dataset.setString("MAIN_TABLE_ID", tableRecord.getField("TABLE_ID").getString());
					//					dataset.setString("TABLE_NAME", tableRecord.getField("TABLE_NAME").getString());
				}
				Variant v=new Variant();
				v.setString(tableRecord.getField("TABLE_ID").getString());
				return v;
			}
		});
		mainTableId.setCustomEditable(true);

		Column mainTableName=new Column("SYS_TABLE.TABLE_NAME");
		Column mode=new Column("SYS_TEMPLATE.MODE");
		//模式，单表和多表
		Column modeDesc=new Column("SYS_TEMPLATE.MODE_DESC");
		modeDesc.setPickList(new PickListDescriptor(
				frameDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "MODE" },
				"DESCRIPTION", true));
		Column viewMode=new Column("SYS_TEMPLATE.VIEW_MODE");
		Column viewModeDesc=new Column("SYS_TEMPLATE.VIEW_MODE_DESC");
		viewModeDesc.setPickList(new PickListDescriptor(
				viewModeDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "VIEW_MODE" },
				"DESCRIPTION", true));
		Column privId=new Column("SYS_TEMPLATE.PRIV_ID");
		privId.addColumnCustomEditListener(new ColumnCustomEditListener(){
			//选择权限
			public Variant customEdit(DataSet arg0, Column arg1) {
				RecordSet rs = new RecordSet();
				if ((rs = new SysPrivilegeFrame().select(SysTemplateFrame.this, null, true))  != null) {
					privRecord=rs.getRecord(0);
					Variant v = new Variant(Variant.STRING);
					v.setString(privRecord.getField("PRIV_ID").getString());
					return v;
				}
				return null;
			}

		});
		privId.addColumnChangeListener(new ColumnChangeAdapter(){
			@Override
			public void validate(DataSet dataset, Column column, Variant value)
					throws Exception, DataSetException {
				// 权限值改变时候触发事件
				if(value.getString()!=null&&value.getString().trim().length()>0){
					if (privRecord == null|| !value.getString().equals(privRecord.getField("PRIV_ID").getString())) {
						SysPrivilege sysUnit = (SysPrivilege) new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(SysPrivilege.class);
						VariantHolder<Object> data = new VariantHolder<Object>();
						data.value = new RecordSet();
						VariantHolder<String> errMsg = new VariantHolder<String>();
						//						try {
						if (sysUnit.get(value.toString(),data, errMsg)) {
							privRecord = ((RecordSet) data.value).getRecord(0);
						} else {
							throw new DataAwareException(
									DataAwareException.INVALID_COLUMN_VALUE, errMsg.value
									, column, null);
						}
					}
					dataset.setString("PRIV_ID", privRecord.getField("PRIV_ID").getString());
					dataset.setString("PRIV_NAME", privRecord.getField("DESCRIPTION").getString());
				}else{
					dataset.setString("PRIV_ID", null);
					dataset.setString("PRIV_NAME", null);
				}
			}
		});

		Column privname=new Column("SYS_PRIVILEGE.PRIV_NAME");

		Column remarks=new Column("SYS_TEMPLATE.REMARKS");
		Column mainSql=new Column("SYS_TEMPLATE.MAIN_SQL");

		Column widthColumn=new Column("SYS_TEMPLATE.DEFAULT_WIDTH");
		Column heightColumn=new Column("SYS_TEMPLATE.DEFAULT_HEIGHT");

		//固定条件
		Column whereSql=new Column("SYS_TEMPLATE.WHERE_SQL");
		//分组SQL
		Column groupbySql=new Column("SYS_TEMPLATE.GROUP_BY_SQL");
		//排序sql
		Column sortSql=new Column("SYS_TEMPLATE.SORT_SQL");

		Column mainKey=new Column("SYS_TEMPLATE.MAIN_KEY");

		//数据集设置列
		masterDataSet.setColumns(new Column[]{templateId,templateNum,templateName
				,mainTableId,mainTableName,mode,modeDesc,widthColumn,heightColumn
				,viewMode,viewModeDesc,privId,privname,remarks,mainSql,whereSql,groupbySql,sortSql,mainKey});

		//明细
		Column  detailCoulmnId=new Column("SYS_TEMPLATE_DETAIL.TABLE_ID");
		detailCoulmnId.addColumnChangeListener(new ColumnChangeAdapter(){

			@Override
			public void validate(DataSet dataset, Column column, Variant value)
					throws Exception, DataSetException {
				if(value==null||"".equals(value.getString())){
					tableRecord=null;
					dataset.setAssignedNull("TABLE_ID");
					dataset.setAssignedNull("TABLE_NAME");
				} else if(value!=null&&!"".equals(value.getString())&&(tableRecord==null||!tableRecord.getField("TABLE_ID").getString().equals(value.getString()))){
					SysTable table=(SysTable)(new RMIProxy(
							Consumer.getDefaultConsumer().getSession()))
							.newStub(SysTable.class);
					VariantHolder<Object> data=new VariantHolder<Object>();
					data.value=new RecordSet[]{new RecordSet(),new RecordSet()};
					VariantHolder<String> errMsg=new VariantHolder<String>();
					if(!table.get(value.getString(), data, errMsg)){
						throw new DataAwareException(
								DataAwareException.INVALID_COLUMN_VALUE, errMsg.value, column, null);
					}
					dataset.setString("MAIN_TABLE_ID", tableRecord.getField("TABLE_ID").getString());
					dataset.setString("TABLE_NAME", tableRecord.getField("TABLE_NAME").getString());
				}
			}

		});
		detailCoulmnId.addColumnCustomEditListener(new ColumnCustomEditListener(){

			public Variant customEdit(DataSet dataset, Column arg1) {
				SysTableFrame frame=new SysTableFrame();
				RecordSet set =frame.select(SysTemplateFrame.this, null, false,true);
				if(set!=null&&set.recordCount()>0){
					tableRecord=set.getRecord(0);
					dataset.setString("MAIN_TABLE_ID", tableRecord.getField("TABLE_ID").getString());
					dataset.setString("TABLE_NAME", tableRecord.getField("TABLE_NAME").getString());
				}
				return null;
			}

		});


		//模板选择表 列
		Column detailCoulmnName=new Column("SYS_TABLE.TABLE_NAME");

		Column detailRemaks=new Column("SYS_TEMPLATE_DETAIL.REMARKS");
		detailDataSet.setColumns(new Column[]{detailCoulmnId,detailCoulmnName,detailRemaks});

		//模板 页签数据集模板
		tabsDataSet=new StorageDataSet();
		String[] tabscolumn=new String[]{"TABS_NAME","TABLE_ID","TABS_TYPE","TABS_TYPE_DESC","POSITION","POSITION_DESC"
				,"SEQUENCE","QUERY_SQL","REMARKS"};
		tabsDataSet.setColumns(ColumnsHelp.getColumns("SYS_TABS",tabscolumn));
		tabsDataSet.open();

		tabsTabel=new JdbTable();
		tabsTabel.setDataSet(tabsDataSet);
		JScrollPane tabsPane=new TableScrollPane(tabsTabel);
		detailPane.addTab("页签", null, tabsPane, null);
		//模板属性模板
		attributeDataSet=new StorageDataSet();
		String[] attributeColumn=new String[]{"TEMPLATE_ID","COLUMN_ID","TABS_ID","TABLE_ID","COLUMN_NAME","SEQUENCE","COL","ROW","LENGTH"
				,"PRECISE","CHAR_CASE","SYS_CODE_DESC.CHAR_CASE_DESC","IS_MUST","IS_MUST_DESC","EDIT_IS_MUST","EDIT_IS_MUST_DESC","DEFAULT_VALUE_SOURCE","DEFAULT_VALUE_SOURCE_DESC","DEFAULT_VALUE","DATA_LENGTH","DEFAULT_WIDTH","VIEW_COLOR","DATA_TYPE","DATA_TYPE_DESC","VIEW_TYPE","VIEW_TYPE_DESC","VIEW_FORMULA","EDITOR_FORMULA","VALIDATE_FORMULA"
				,"IS_TOTAL","IS_TOTAL_DESC","IS_CARD_SHOW","IS_CARD_SHOW_DESC","IS_LIST_SHOW","IS_LIST_SHOW_DESC"
				,"PH_TYPE","CODE_TYPE","FUNC_ID","ZDY_CLASS","ZDY_ITEMS","COMBO_IS_EDIT","COMBO_IS_EDIT_DESC","VALUE_TYPE","VALUE_TYPE_DESC","REF_COLUMNS","PRIV_ID","PRIV_NAME","DEF1","DEF2","DEF3"
				,"SEQUENCE"};
		attributeDataSet.setColumns(ColumnsHelp.getColumns("SYS_ATTRIBUTE",attributeColumn));

		attributeDataSet.getColumn("IS_MUST").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("EDIT_IS_MUST").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("DATA_TYPE").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("VIEW_TYPE").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("IS_TOTAL").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("IS_CARD_SHOW").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("IS_LIST_SHOW").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("COMBO_IS_EDIT").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("VALUE_TYPE").setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		attributeDataSet.getColumn("DEFAULT_VALUE_SOURCE").setVisible(com.borland.jb.util.TriStateProperty.FALSE);

		//构造下拉选项
		attributeDataSet.getColumn("IS_MUST_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_MUST" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("EDIT_IS_MUST_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "EDIT_IS_MUST" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("DATA_TYPE_DESC").setPickList(new PickListDescriptor(
				dataTypeDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DATA_TYPE" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("VIEW_TYPE_DESC").setPickList(new PickListDescriptor(
				viewModeDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "VIEW_TYPE" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("IS_TOTAL_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_TOTAL" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("IS_CARD_SHOW_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_CARD_SHOW" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("IS_LIST_SHOW_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_LIST_SHOW" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("CHAR_CASE_DESC").setPickList(new PickListDescriptor(
				charCaseataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "CHAR_CASE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("COMBO_IS_EDIT_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "COMBO_IS_EDIT" },
				"DESCRIPTION", true));


		attributeDataSet.getColumn("VALUE_TYPE_DESC").setPickList(new PickListDescriptor(
				valueTypeDaraSet  , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "VALUE_TYPE" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("DEFAULT_VALUE_SOURCE_DESC").setPickList(new PickListDescriptor(
				valueSourceDaraSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DEFAULT_VALUE_SOURCE" },
				"DESCRIPTION", true));


		attributeDataSet.getColumn("PRIV_ID").addColumnChangeListener(new ColumnChangeAdapter(){
			@Override
			public void changed(DataSet dataset, Column column, Variant value) {
				VariantHolder<String> errMsg = new VariantHolder<String>();

				try{
					if(value!=null&&value.getString()!=null&&value.getString().trim().length()>0){
						if (privRecord == null|| !value.getString().equals(privRecord.getField("PRIV_ID").getString())) {
							SysPrivilege sysUnit = (SysPrivilege) new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(SysPrivilege.class);
							VariantHolder<Object> data = new VariantHolder<Object>();
							data.value = new RecordSet();
							//						try {
							if (sysUnit.get(value.toString(),data, errMsg)) {
								privRecord = ((RecordSet) data.value).getRecord(0);
							} else {
								throw new DataAwareException(
										DataAwareException.INVALID_COLUMN_VALUE, errMsg.value
										, column, null);
							}
						}
						dataset.setString("PRIV_ID", privRecord.getField("PRIV_ID").getString());
						dataset.setString("PRIV_NAME", privRecord.getField("DESCRIPTION").getString());
					}else{
						dataset.setString("PRIV_ID", null);
						dataset.setString("PRIV_NAME", null);
					}
				}catch(Exception e){
					throw new DataAwareException(
							DataAwareException.INVALID_COLUMN_VALUE, errMsg.value
							, column, null);
				}
			}
		});
		attributeDataSet.getColumn("PRIV_ID").addColumnCustomEditListener(new ColumnCustomEditListener(){

			public Variant customEdit(DataSet arg0, Column arg1) {
				com.evangelsoft.workbench.security.client.SysPrivilegeFrame sysPriv=new SysPrivilegeFrame();
				RecordSet set=sysPriv.select(SysTemplateFrame.this, null, false,true);
				if(set!=null&&set.recordCount()>0){
					Variant v=new Variant();
					v.setString(set.getRecord(0).getField("PRIV_ID").getString());
					privRecord=set.getRecord(0);
					return v;
				}
				return null;
			}
		});
		attributeDataSet.getColumn("PRIV_ID").setCustomEditable(true);
		attributeDataSet.getColumn("PRIV_NAME").setEditable(false);
		attributeDataSet.open();

		//属性表格
		attributeTabel=new JdbTable();
		attributeTabel.setDataSet(attributeDataSet);
		JScrollPane attributePane=new TableScrollPane(attributeTabel);
		detailPane.addTab("属性", null, attributePane, null);





		printpanel=new JPanel();
		printpanel.setLayout(new BorderLayout());
		printDataSet=new StorageDataSet();
		printTable=new JdbTable();
		//
		String[] printcolumn=new String[]{"TEMPLATE_ID","PRINT_CODE","PRINT_NAME","ACTION","ACTION_DESC","XML","DESCRIPTION"};
		printDataSet.setColumns(ColumnsHelp.getColumns("SYS_TEMPLATE_PRINT",printcolumn));
		printDataSet.getColumn("ACTION").setVisible(com.borland.jb.util.TriStateProperty.FALSE);


		printDataSet.getColumn("ACTION_DESC").setPickList(new PickListDescriptor(
				printActionDaraSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "ACTION" },
				"DESCRIPTION", true));

		printDataSet.getColumn("PRINT_CODE").addColumnCustomEditListener(new ColumnCustomEditListener() {
			@Override
			public Variant customEdit(DataSet arg0, Column arg1) {
				BaseSingleDataSetFrame frame=new BaseSingleDataSetFrame();
				frame.setTemplateId("PRINT_CONFIG");
				RecordSet set=	frame.select(SysTemplateFrame.this, null, false);
				if(set!=null&&set.recordCount()>0){
					printRecord = set.getRecord(0);
					Variant v = new Variant(Variant.STRING);
					v.setString(printRecord.getField("PRINT_CODE").getString());
					return v;
				}
				return null;
			}
		} );
		printDataSet.getColumn("PRINT_CODE").addColumnChangeListener(new ColumnChangeAdapter() {
			@Override
			public void changed(DataSet arg0, Column column, Variant value) {
				if(value!=null&& StringUtils.isNotEmpty(value.getString())){
					if(printRecord==null||!printRecord.getField("PRINT_CODE").getString().equals(value.getString())){
						//执行查询
						printRecord=null;
						BaseSingleDataSet baseSingle=(BaseSingleDataSet)(new RMIProxy(
								Consumer.getDefaultConsumer().getSession()))
								.newStub(BaseSingleDataSet.class);
						VariantHolder<Object> data=new VariantHolder<Object>();
						data.value=new TransientRecordSet();
						VariantHolder<String> errMsg=new VariantHolder<String>();
						HashMap<String, Object> map=new HashMap<String, Object>();
						map.put(BaseMasterDetail.TEMPLATE_NUM, "PRINT_CONFIG");
						try {
							if(!baseSingle.get(map, value.getString(), data, errMsg)){
								throw new RemoteException(StringUtils.isEmpty(errMsg.value)?"打印编号【"+value+"】查询失败！":errMsg.value);
							}
							RecordSet set=(RecordSet)data.value;

							printRecord=set.getRecord(0);
						} catch (RemoteException e) {
							throw new DataAwareException(
									DataAwareException.INVALID_COLUMN_VALUE, e
									.getMessage(), column, null);
						}
					}
					if(printRecord!=null&&printRecord.getField("PRINT_CODE").getString().equals(value.getString())){
						printDataSet.setString("PRINT_CODE", printRecord.getField("PRINT_CODE").getString());
						printDataSet.setString("PRINT_NAME", printRecord.getField("PRINT_NAME").getString());
						printDataSet.setString("XML", printRecord.getField("XML").getString());
					}
				}
			}
		});
		printDataSet.getColumn("PRINT_CODE").setCustomEditable(true);
		printDataSet.open();
		printTable.setDataSet(printDataSet);
		JScrollPane printPane=new TableScrollPane(printTable);

		printpanel.add(printPane);
		printFooterPanel=new JPanel();
		printpanel.add(printFooterPanel, BorderLayout.SOUTH);
		printToolBar=new JToolBar();
		printRowCountLabel=new JLabel();
		printFooterPanel.setLayout(new BorderLayout());
		printFooterRightPanel=new JPanel();
		printFooterPanel.add(printFooterRightPanel);


		printpanel.add(printToolBar, BorderLayout.NORTH);
		addPrintButton=new JButton();
		deletePrintButton=new JButton();
		cleraPrintButton=new JButton();

		addPrintButton.setAction(printNewAction);
		deletePrintButton.setAction(printDeleteAction);
		cleraPrintButton.setAction(printClearAction);
		printToolBar.add(addPrintButton);
		printToolBar.add(deletePrintButton);
		printToolBar.add(cleraPrintButton);

		detailPane.addTab("打印设置", null, printpanel, null);











		buttonPanel=new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonDataSet=new StorageDataSet();
		buttonTable=new JdbTable();
		//
		String[] buttoncolumn=new String[]{"BUTTON_CODE","BUTTON_NAME","DESCRIPTION"};
		buttonDataSet.setColumns(ColumnsHelp.getColumns("SYS_TEMPLATE_BUTTON",buttoncolumn));
//		printDataSet.getColumn("ACTION").setVisible(com.borland.jb.util.TriStateProperty.FALSE);

		buttonTable.setDataSet(buttonDataSet);

		JScrollPane buttonPane=new TableScrollPane(buttonTable);

		buttonPanel.add(buttonPane);
		buttonFooterPanel=new JPanel();
		buttonPanel.add(buttonFooterPanel, BorderLayout.SOUTH);
		buttonToolBar=new JToolBar();
		buttonRowCountLabel=new JLabel();
		buttonFooterPanel.setLayout(new BorderLayout());
		buttonFooterRightPanel=new JPanel();
		buttonFooterPanel.add(buttonFooterRightPanel);


		buttonPanel.add(buttonToolBar, BorderLayout.NORTH);
		addButton=new JButton();
		deleteButton=new JButton();
		cleraButton=new JButton();

		addButton.setAction(buttonNewAction);
		deleteButton.setAction(buttonDeleteAction);
		cleraButton.setAction(buttonClearAction);
		buttonToolBar.add(addButton);
		buttonToolBar.add(deleteButton);
		buttonToolBar.add(cleraButton);

		detailPane.addTab("按钮设置", null, buttonPanel, null);



		templateidLabel=new JLabel();//模板ID
		templateidLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.TEMPLATE_ID"));
		templateIdField=new JTextField();


		templateNameLabel=new JLabel();//模板名称
		templateNameLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.TEMPLATE_NAME"));
		templateNameField=new JTextField();

		//过滤条件布局
		final GridBagLayout filterGridBagLayout = new GridBagLayout();
		filterGridBagLayout.rowHeights = new int[] { 5,5,5 };
		filterGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		filterSimplePanel.setLayout(filterGridBagLayout);

		//添加模板ID
		filterSimplePanel.add(templateidLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		filterSimplePanel.add(templateIdField,
				new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		//添加模板名称查询

		filterSimplePanel.add(templateNameLabel, new GridBagConstraints(1, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		filterSimplePanel.add(templateNameField,
				new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		//主数据集绑定的控件
		masterTemplateNumLabel=new JLabel();// 模板编号
		masterTemplateNumLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.TEMPLATE_NUM"));
		masterTemplateNumField=new JdbTextField();
		masterTemplateNumField.setDataSet(masterDataSet);
		masterTemplateNumField.setColumnName("TEMPLATE_NUM");

		masterTemplateNumNameLabel=new JLabel();//模板名称
		masterTemplateNumNameLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.TEMPLATE_NAME"));
		masterTemplateNameField=new JdbTextField();
		masterTemplateNameField.setDataSet(masterDataSet);
		masterTemplateNameField.setColumnName("TEMPLATE_NAME");




		tableIdLabel=new JLabel();//主表ID
		tableIdLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.MAIN_TABLE_ID"));
		tableIdButton=new JdbButton();
		tableIdButton.setDataSet(masterDataSet);
		tableIdButton.setColumnName("MAIN_TABLE_ID");

		tableIdField=new JdbTextField();
		tableIdField.setDataSet(masterDataSet);
		tableIdField.setColumnName("MAIN_TABLE_ID");
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(tableIdField,BorderLayout.CENTER);
		panel.add(tableIdButton,BorderLayout.EAST);
		tableIdButton
		.setIcon(new ImageIcon(
				SysTemplateFrame.class.getClassLoader().getResource(
						"com/evangelsoft/workbench/resources/buttons/find.png")));
		tableIdButton.setMargin(new Insets(0, 0, 0, 0));

		tableNameLabel=new JLabel();//主表名称
		tableNameLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.TABLE_NAME"));
		tableNameField=new JdbTextField();
		tableNameField.setDataSet(masterDataSet);
		tableNameField.setColumnName("TABLE_NAME");


		masterModeLabel=new JLabel();//模式
		masterModeLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.MODE_DESC"));
		masterModeField=new JdbComboBox();
		masterModeField.setDataSet(masterDataSet);
		masterModeField.setColumnName("MODE_DESC");

		masterViewModeLabel=new JLabel();//显示方式
		masterViewModeLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.VIEW_MODE_DESC"));
		masterViewModeField=new JdbComboBox();
		masterViewModeField.setDataSet(masterDataSet);
		masterViewModeField.setColumnName("VIEW_MODE_DESC");

		//默认宽度
		defaultWidthLabel=new JLabel();
		defaultWidthLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.DEFAULT_WIDTH"));
		defaultWidthField=new JdbTextField();
		defaultWidthField.setDataSet(masterDataSet);
		defaultWidthField.setColumnName("DEFAULT_WIDTH");

		//默认高度
		defaultHeightLabel=new JLabel();
		defaultHeightLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.DEFAULT_HEIGHT"));
		defaultheightField=new JdbTextField();
		defaultheightField.setDataSet(masterDataSet);
		defaultheightField.setColumnName("DEFAULT_HEIGHT");


		masterPrivNumLabel=new JLabel();//权限id
		masterPrivNumLabel.setText(DataModel.getDefault().getLabel("SYS_PRIVILEGE.PRIV_ID"));
		masterPrivNumField =new JdbTextField();
		masterPrivNumField.setDataSet(masterDataSet);
		masterPrivNumField.setColumnName("PRIV_ID");
		privButton=new JdbButton();
		privButton.setDataSet(masterDataSet);
		privButton.setColumnName("PRIV_ID");
		privButton.setMargin(new Insets(0,0,0,0));
		privButton
		.setIcon(new ImageIcon(
				SysTemplateFrame.class.getClassLoader().getResource(
						"com/evangelsoft/workbench/resources/buttons/find.png")));


		JPanel privPanel=new JPanel();
		privPanel.setLayout(new BorderLayout());
		privPanel.add(privButton,BorderLayout.EAST);
		privPanel.add(masterPrivNumField,BorderLayout.CENTER);

		masterPrivNameLabel=new JLabel();
		masterPrivNameLabel.setText(DataModel.getDefault().getLabel("SYS_PRIVILEGE.PRIV_NAME"));
		masterPrivNameField=new JdbTextField();
		masterPrivNameField.setDataSet(masterDataSet);
		masterPrivNameField.setColumnName("PRIV_NAME");




		masterMainSqlLabel=new JLabel();
		masterMainSqlLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.MAIN_SQL"));
		masterMainSqlsField=new JdbTextArea();
		masterMainSqlsField.setDataSet(masterDataSet);
		masterMainSqlsField.setColumnName("MAIN_SQL");

		//固定条件
		masterWhereSqlLabel=new JLabel();
		masterWhereSqlLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.WHERE_SQL"));
		masteWhereSqlField=new JdbTextField();
		masteWhereSqlField.setDataSet(masterDataSet);
		masteWhereSqlField.setColumnName("WHERE_SQL");

		//分组
		masterGroupSqlLabel=new JLabel();
		masterGroupSqlLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.GROUP_BY_SQL"));
		masterGroupSqlsField=new JdbTextField();
		masterGroupSqlsField.setDataSet(masterDataSet);
		masterGroupSqlsField.setColumnName("GROUP_BY_SQL");
		//排序
		masterSortSqlLabel=new JLabel();
		masterSortSqlLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.SORT_SQL"));
		masterSortSqlsField=new JdbTextField();
		masterSortSqlsField.setDataSet(masterDataSet);
		masterSortSqlsField.setColumnName("SORT_SQL");






		masterRemaksLabel=new JLabel();
		masterRemaksLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.REMARKS"));
		masterRemaksField=new JdbTextField();
		masterRemaksField.setDataSet(masterDataSet);
		masterRemaksField.setColumnName("REMARKS");


		filterGridBagLayout.rowHeights = new int[] { 5,5,5,5,5,5,5,5,5,5,5 ,5,5,5,5,5,5,5};
		filterGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		masterPanel.setLayout(filterGridBagLayout);
		masterPanel.add(masterTemplateNumLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterTemplateNumField,
				new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterTemplateNumNameLabel, new GridBagConstraints(4, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterTemplateNameField,
				new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));





		masterPanel.add(tableIdLabel, new GridBagConstraints(1, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(panel ,
				new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(tableNameLabel, new GridBagConstraints(4, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(tableNameField,
				new GridBagConstraints(5, 3, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));



		masterPanel.add(masterModeLabel, new GridBagConstraints(1, 5, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterModeField,
				new GridBagConstraints(2, 5, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterViewModeLabel, new GridBagConstraints(4,5, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterViewModeField,
				new GridBagConstraints(5, 5, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));




		//默认宽度和默认高度设置
		masterPanel.add(defaultWidthLabel, new GridBagConstraints(1,7, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(defaultWidthField,
				new GridBagConstraints(2, 7, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(defaultHeightLabel, new GridBagConstraints(4, 7, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(defaultheightField,
				new GridBagConstraints(5, 7, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));



		//权限设置
		masterPanel.add(masterPrivNumLabel, new GridBagConstraints(1,9, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(privPanel,
				new GridBagConstraints(2, 9, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterPrivNameLabel, new GridBagConstraints(4, 9, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterPrivNameField,
				new GridBagConstraints(5, 9, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterRemaksLabel, new GridBagConstraints(1,11, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterRemaksField,
				new GridBagConstraints(2, 11,4, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		masterPanel.add(masterMainSqlLabel, new GridBagConstraints(1,13, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		JPanel scrollPanel=new JPanel();

		scrollPanel.setLayout(new BorderLayout());
		JScrollPane scroll=new JScrollPane();
		scrollPanel.add(masterMainSqlsField);
		scroll.setViewportView(scrollPanel);
		scroll.setPreferredSize(new Dimension(80,50));

		//		scroll.setPreferredSize(new Dimension(0,40));
		masterPanel.add(scroll,
				new GridBagConstraints(2, 13,4, 1, 1.0, 1,GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));


		//		JdbTextField field=new JdbTextField();
		//		field.setDataSet(attributeDataSet);
		//		field.setColumnName("ATTRIBUTE_ID");
		//		masterPanel.add(field,
		//				new GridBagConstraints(2, 10,4, 1, 1.0, 0.0,GridBagConstraints.CENTER,
		//						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		//固定条件
		masterPanel.add(masterWhereSqlLabel, new GridBagConstraints(1,15, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		masterPanel.add(masteWhereSqlField, new GridBagConstraints(2,15, 4,  1, 1.0, 0.0,GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		//分组
		masterPanel.add(masterGroupSqlLabel, new GridBagConstraints(1,17, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		masterPanel.add(masterGroupSqlsField, new GridBagConstraints(2,17, 1,  1, 1.0, 0.0,GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		//排序
		masterPanel.add(masterSortSqlLabel, new GridBagConstraints(4,17, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		masterPanel.add(masterSortSqlsField, new GridBagConstraints(5,17, 1,  1, 1.0, 0.0,GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		masterMainKeyLabel=new JLabel();
		masterMainKeyLabel.setText(DataModel.getDefault().getLabel("SYS_TEMPLATE.MAIN_KEY"));
		masterMainKeyField=new JdbTextField();
		masterMainKeyField.setDataSet(masterDataSet);
		masterMainKeyField.setColumnName("MAIN_KEY");


		masterPanel.add(masterMainKeyLabel, new GridBagConstraints(1,19, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		masterPanel.add(masterMainKeyField, new GridBagConstraints(2,19, 4,  1, 1.0, 0.0,GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		design=new JButton();
		//		design.setIcon(new ImageIcon(
		//		SysTemplateFrame.class.getClassLoader().getResource(
		//		"com/evangelsoft/workbench/resources/buttons/design.png")));
		design.setMargin(new Insets(0, 0, 0, 0));
		this.mainToolBar.addSeparator();
		this.mainToolBar.add(design);
		design.setAction(new DesignAction());
	}
	class DesignAction extends AbstractAction{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		DesignAction() {
			if (!java.beans.Beans.isDesignTime())
				putValue(
						"SmallIcon",
						new ImageIcon(getClass()
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/design.png")));
			putValue("ShortDescription",
					"设计");
		}

		public void actionPerformed(ActionEvent arg0) {
			//打开设计模板
			//			new DesignFrame().showByKeyString(masterDataSet.getString("TEMPLATE_NUM"));
			//			new DesignFrame().showByKey(SysTemplateFrame.this,masterDataSet.getBigDecimal("TEMPLATE_ID"));
			new DesignFrame().showByKey(SysTemplateFrame.this,buildKey());

		}
	}
	@Override
	protected void detailBatch() {
		SysTableFrame frame=new SysTableFrame();
		RecordSet set =frame.select(SysTemplateFrame.this, null, true,true);
		if(set!=null&&set.recordCount()>0){
			for(int i=0;i<set.recordCount();i++){
				tableRecord=set.getRecord(i);
				detailDataSet.insertRow(false);
				detailDataSet.setString("TABLE_ID", tableRecord.getField("TABLE_ID").getString());
				detailDataSet.setString("TABLE_NAME", tableRecord.getField("TABLE_NAME").getString());
				detailDataSet.setString("REMARKS", tableRecord.getField("REMARKS").getString());
			}
		}
	}

	@Override
	protected void showRowStatus() {
		super.showRowStatus();
		//编辑状态不让设计
		this.design.setEnabled(!this.cancelButton.isEnabled());
	}
	protected void showStatus() {
		super.showStatus();
		if (!masterDataSet.isEditingNewRow() && masterDataSet.isEmpty()) {
			// 不存在当前单据时，不会调用showRowStatus，须要在此处屏蔽与当前单据有关的动作。
			this.design.setEnabled(false);
		}
	}

	@Override
	protected void linkDetailDataSets() {
		//		super.linkDetailDataSets();
		this.detailDataSets.add(this.detailDataSet);
		this.detailNewActions.put(this.detailDataSet, this.detailNewAction);
		this.detailBatchActions.put(this.detailDataSet, this.detailBatchAction);
		this.detailDeleteActions.put(this.detailDataSet, this.detailDeleteAction);
		this.detailClearActions.put(this.detailDataSet, this.detailClearAction);
		this.detailRowCountLabels.put(this.detailDataSet, this.detailRowCountLabel);

		detailDataSets.add(tabsDataSet);
		detailDataSets.add(attributeDataSet);
		detailDataSets.add(printDataSet);
		detailNewActions.put(printDataSet, printNewAction);
		detailDeleteActions.put(printDataSet, printDeleteAction);
		detailClearActions.put(printDataSet, printClearAction);
		detailRowCountLabels.put(printDataSet, printRowCountLabel);

		detailDataSets.add(buttonDataSet);
		detailNewActions.put(buttonDataSet, buttonNewAction);
		detailDeleteActions.put(buttonDataSet, buttonDeleteAction);
		detailClearActions.put(buttonDataSet, buttonClearAction);
		detailRowCountLabels.put(buttonDataSet, buttonRowCountLabel);

	}

	@Override
	protected void validateMaster(ReadWriteRow paramReadWriteRow,
			ReadRow paramReadRow) throws Exception {
		super.validateMaster(paramReadWriteRow, paramReadRow);
		//验证必要数据
	}
	private class PrintNewAction extends AbstractAction {
		/**
		 * 版本号
		 */
		private static final long serialVersionUID = -8756828444195254748L;

		PrintNewAction() {
			//			super(DataModel.getDefault().getCaption("NEW_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/addDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("NEW_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			try {
				printDataSet.insertRow(false);
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(printDataSet,
									printTable, ex));
					return;
				} else {
					throw ex;
				}
			}
		}
	}

	private PrintNewAction printNewAction = new PrintNewAction();

	private class PrintDeleteAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		PrintDeleteAction() {
			//			super(DataModel.getDefault().getCaption("DELETE_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("DELETE_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = printTable.getDataSet();
			if (dataSet == null) {
				dataSet = printDataSet;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			dataSet.deleteRow();
		}
	}

	private PrintDeleteAction printDeleteAction = new PrintDeleteAction();

	private class PrintClearAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = -4138891974369942016L;

		PrintClearAction() {
			//			super(DataModel.getDefault().getCaption("CLEAR_LINES"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("CLEAR_LINES"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = printTable.getDataSet();
			if (dataSet == null) {
				dataSet = printDataSet;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			printDataSet.deleteAllRows();
		}
	}

	private PrintClearAction printClearAction = new PrintClearAction();







	private class ButtonNewAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		ButtonNewAction() {
			//			super(DataModel.getDefault().getCaption("NEW_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/addDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("NEW_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			try {
				buttonDataSet.insertRow(false);
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(printDataSet,
									printTable, ex));
					return;
				} else {
					throw ex;
				}
			}
		}
	}

	private ButtonNewAction buttonNewAction = new ButtonNewAction();

	private class ButtonDeleteAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		ButtonDeleteAction() {
			//			super(DataModel.getDefault().getCaption("DELETE_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("DELETE_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = buttonTable.getDataSet();
			if (dataSet == null) {
				dataSet = buttonDataSet;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			dataSet.deleteRow();
		}
	}

	private ButtonDeleteAction buttonDeleteAction = new ButtonDeleteAction();

	private class ButtonClearAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		ButtonClearAction() {
			//			super(DataModel.getDefault().getCaption("CLEAR_LINES"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								SysTemplateFrame.class
								.getClassLoader()
								.getResource(
										"com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("CLEAR_LINES"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = printTable.getDataSet();
			if (dataSet == null) {
				dataSet = buttonDataSet;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			buttonDataSet.deleteAllRows();
		}
	}

	private ButtonClearAction buttonClearAction = new ButtonClearAction();

}
