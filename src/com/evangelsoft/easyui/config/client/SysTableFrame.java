package com.evangelsoft.easyui.config.client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.borland.dbswing.JdbTextField;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.config.intf.SysTable;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionJointNode;
import com.evangelsoft.econnect.condutil.ConditionLeafNode;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.config.client.SysCodeHelper;
import com.evangelsoft.workbench.framebase.MasterDetailFrame;
import com.evangelsoft.workbench.types.BoolStr;

public class SysTableFrame extends MasterDetailFrame{

	public SysTableFrame(){
		super();
		try{
			init();
		}catch(Exception e){
			e.printStackTrace();
		}
		pack();
	}

	JLabel tableidLabel;
	JTextField tableIdField;

	JLabel tableNameLabel;
	JTextField tableNameField;



	JLabel masterTableidLabel;
	JdbTextField masterTableIdField;

	JLabel masterTableNameLabel;
	JdbTextField masterTableNameField;


	JLabel masterRemaksLabel;
	JdbTextField masterRemaksField;

	private StorageDataSet booleanDataSet;
	private StorageDataSet dataTYpeDataSet;
	private StorageDataSet charCaseataSet;

	protected void prepared(Object arg0) throws Exception {
		DataSetHelper.loadFromRecordSet(booleanDataSet,
				SysCodeHelper.getRecordSet(BoolStr.ID_STRING));
		DataSetHelper.loadFromRecordSet(dataTYpeDataSet,
				SysCodeHelper.getRecordSet("DATA_TYPE"));
		DataSetHelper.loadFromRecordSet(charCaseataSet ,
				SysCodeHelper.getRecordSet("CHAR_CASE"));
	}

	protected Object prepareData() throws Exception {

		// 指定当前窗口处理的实体对应的类。
		entityClass = SysTable.class;
		// 指定主数据集的键值，用于获取明细数据集，以及重复判断。
		keyColumns = new String[] { "TABLE_ID" };
		// 指定明细数据集的键值，用于重复判断。
//		detailKeyColumns.put(detailDataSet, new String[] { "COLUMN_ID" });
		return super.prepareData();
	}

	//初始化界面
	void init(){
		setTitle(DataModel.getDefault().getCaption("SYS_TABLE"));
		listTablePane.setPreferredSize(new Dimension(
				listTable.getRowHeight() * 12, listTable.getRowHeight() * 10));
		detailPane.setPreferredSize(new Dimension(detailTable
				.getRowHeight() * 30, detailTable.getRowHeight() * 18));
		booleanDataSet=new StorageDataSet();
		dataTYpeDataSet=new StorageDataSet();
		charCaseataSet=new StorageDataSet();


		Column tableId=new Column("SYS_TABLE.TABLE_ID");

		Column tableName=new Column("SYS_TABLE.TABLE_NAME");

		Column remarks=new Column("SYS_TABLE.REMARKS");
		masterDataSet.setColumns(new Column[]{tableId,tableName,remarks});

		//明细
		Column  detailCoulmnId=new Column("SYS_COLUMN.COLUMN_ID");


		Column detailCoulmnName=new Column("SYS_COLUMN.COLUMN_NAME");


		Column detailIsActive=new Column("SYS_COLUMN.ISACTIVE");
		detailIsActive.setVisible(com.borland.jb.util.TriStateProperty.FALSE);

		Column detailIsActiveDesc=new Column("SYS_COLUMN.ISACTIVE_DESC");
		detailIsActiveDesc.setPickList(new PickListDescriptor(
				booleanDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "ISACTIVE" },
				"DESCRIPTION", true));


		Column detailDataType=new Column("SYS_COLUMN.DATA_TYPE");
		detailDataType.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		Column detailDataTypeDesc=new Column("SYS_COLUMN.DATA_TYPE_DESC");
		detailDataTypeDesc.setPickList(new PickListDescriptor(
				dataTYpeDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DATA_TYPE" },
				"DESCRIPTION", true));

		Column detailLength=new Column("SYS_COLUMN.LENGTH");
		Column detailWidth=new Column("SYS_COLUMN.WIDTH");

		Column detailPrecise=new Column("SYS_COLUMN.PRECISE");
		Column detailNullable=new Column("SYS_COLUMN.NULLABLE");
		detailNullable.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		Column detailNullableDesc=new Column("SYS_COLUMN.NULLABLE_DESC");
		detailNullableDesc.setPickList(new PickListDescriptor(
				booleanDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "NULLABLE" },
				"DESCRIPTION", true));

		Column detailPkey=new Column("SYS_COLUMN.PKEY");

		detailPkey.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		Column detailPkeyDesc=new Column("SYS_COLUMN.PKEY_DESC");
		detailPkeyDesc.setPickList(new PickListDescriptor(
				booleanDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "PKEY" },
				"DESCRIPTION", true));

		Column detailDefaultValue=new Column("SYS_COLUMN.DEFAULT_VALUE");
		Column detailSequence=new Column("SYS_COLUMN.SEQUENCE");
		Column detailRemaks=new Column("SYS_COLUMN.REMARKS");
		Column charCase=new Column("SYS_COLUMN.CHAR_CASE");
		charCase.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		Column charCaseDesc=new Column("SYS_CODE_DESC.CHAR_CASE_DESC");
		charCaseDesc.setPickList(new PickListDescriptor(
				charCaseataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "CHAR_CASE" },
				"DESCRIPTION", true));

		detailDataSet.setColumns(new Column[]{detailCoulmnId,detailCoulmnName,detailIsActive,detailIsActiveDesc
				,detailDataType,detailDataTypeDesc,detailLength,detailWidth,detailPrecise,detailNullable,detailNullableDesc,detailPkey,detailPkeyDesc,detailDefaultValue,charCase,charCaseDesc
				,detailSequence,detailRemaks});


		tableidLabel=new JLabel();
		tableidLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.TABLE_ID"));
		tableIdField=new JTextField();


		tableNameLabel=new JLabel();
		tableNameLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.TABLE_NAME"));
		tableNameField=new JTextField();


		final GridBagLayout filterGridBagLayout = new GridBagLayout();
		filterGridBagLayout.rowHeights = new int[] { 5,5,5 };
		filterGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		filterSimplePanel.setLayout(filterGridBagLayout);
		filterSimplePanel.add(tableidLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		filterSimplePanel.add(tableIdField,
				new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		filterSimplePanel.add(tableNameLabel, new GridBagConstraints(1, 3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		filterSimplePanel.add(tableNameField,
				new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));



		masterTableidLabel=new JLabel();
		masterTableidLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.TABLE_ID"));
		masterTableIdField=new JdbTextField();
		masterTableIdField.setDataSet(masterDataSet);
		masterTableIdField.setColumnName("TABLE_ID");

		masterTableNameLabel=new JLabel();
		masterTableNameLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.TABLE_NAME"));
		masterTableNameField=new JdbTextField();
		masterTableNameField.setDataSet(masterDataSet);
		masterTableNameField.setColumnName("TABLE_Name");


		masterRemaksLabel=new JLabel();
		masterRemaksLabel.setText(DataModel.getDefault().getLabel("SYS_TABLE.REMARKS"));
		masterRemaksField=new JdbTextField();
		masterRemaksField.setDataSet(masterDataSet);
		masterRemaksField.setColumnName("REMARKS");

		filterGridBagLayout.rowHeights = new int[] { 5,5,5 };
		filterGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		masterPanel.setLayout(filterGridBagLayout);
		masterPanel.add(masterTableidLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterTableIdField,
				new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterTableNameLabel, new GridBagConstraints(4, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterTableNameField,
				new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		masterPanel.add(masterRemaksLabel, new GridBagConstraints(1,3, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		masterPanel.add(masterRemaksField,
				new GridBagConstraints(2, 3,4, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
	}

	@Override
	protected void buildFilter() {
		// 增加根节点
		filterTree.setRoot(new ConditionJointNode(
				ConditionJointNode.JOIN_TYPE_AND));
		ConditionLeafNode node;

		String billNum = tableIdField.getText().trim();
		if (billNum != null && billNum.trim().length() > 0) {
			node = new ConditionLeafNode("TABLE_ID",
					ConditionItem.DATA_TYPE_STRING,
					ConditionItem.OPERATOR_LIKE);
			node.setString(billNum);
			filterTree.addChildLast(filterTree.getRoot(), node);
		}

		String tableName = tableNameField.getText().trim();
		if (tableName != null) {
			node = new ConditionLeafNode("TABLE_NAME",
					ConditionItem.DATA_TYPE_STRING,
					ConditionItem.OPERATOR_LIKE);
			node.setString(tableName);
			filterTree.addChildLast(filterTree.getRoot(), node);
		}


	}


}
