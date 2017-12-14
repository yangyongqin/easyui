//package com.test.clinet;
//
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//
//import javax.swing.JLabel;
//
//import com.borland.dbswing.JdbTextField;
//import com.borland.dx.dataset.Column;
//import com.evangelsoft.econnect.DataModel;
//import com.evangelsoft.workbench.ct.tool.ColumnsHelp;
//import com.evangelsoft.workbench.framebase.MasterDetailFrame;
//import com.test.intf.Classes;
//
//public class ClassesFrame extends MasterDetailFrame{
//
//	public ClassesFrame(){
//		this.setTitle(DataModel.getDefault().getCaption("CLASSES"));
//		String[] master=new String[]{"CLASSESS_ID","CLASSESS_NUM","CLASSESS_NAME"};
//		String[] detail=new String[]{"STU_ID","CLASSESS_ID","STU_NUM","STU_NAME"};
//		masterDataSet.setColumns(getColumns("CLASSES", master));
//		masterDataSet.open();
//		detailDataSet.setColumns(getColumns("STUDENT", detail));
//		detailDataSet.open();
//		listTablePane.setPreferredSize(new Dimension(listTable.getRowHeight() * 12, listTable.getRowHeight() * 16));
//		detailPanel.setPreferredSize(new Dimension(detailTable.getRowHeight() * 30,detailTable.getRowHeight() * 16));
//
//		JLabel calssnum=new JLabel();
//		calssnum.setText(DataModel.getDefault().getLabel("CLASSES.CLASSESS_NUM"));
//
//		JLabel calssname=new JLabel();
//		calssname.setText(DataModel.getDefault().getLabel("CLASSES.CLASSESS_NAME"));
//
//
//		JdbTextField calssnumFIled=new JdbTextField();
//		calssnumFIled.setDataSet(masterDataSet);
//		calssnumFIled.setColumnName("CLASSESS_NUM");
//
//		JdbTextField calssnameFIled=new JdbTextField();
//		calssnameFIled.setDataSet(masterDataSet);
//		calssnameFIled.setColumnName("CLASSESS_NAME");
//
//
//		GridBagLayout masterLayout = new GridBagLayout();
//		masterLayout.rowHeights = (new int[] { 5,5,5});
//		masterLayout.columnWidths = (new int[] { 5, 5, 5, 5, 5,5});
//		masterPanel.setLayout(masterLayout);
//		//第一行 ，协议组织ID，协议组织名称
//		masterPanel.add(calssnum, new GridBagConstraints(1, 0, 1, 1,
//				0.0, 0.0, GridBagConstraints.WEST,
//				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//		masterPanel.add(calssnumFIled, new GridBagConstraints(2, 0, 1,
//				1, 1, 0.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//		masterPanel.add(calssname, new GridBagConstraints(4, 0, 1, 1,
//				0.0, 0.0, GridBagConstraints.WEST,
//				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//		masterPanel.add(calssnameFIled, new GridBagConstraints(5, 0, 1,
//				1, 1, 0.0, GridBagConstraints.CENTER,
//				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//
//		pack();
//	}
//
//	@Override
//	protected Object prepareData() throws Exception {
//		keyColumns = new String[] {"CLASSESS_ID"};
//		entityClass=Classes.class;
//		return super.prepareData();
//	}
//
//	public static Column[] getColumns(String tabelName,Object[] columnName){
//		Column[] columns=new Column[columnName.length];
//		for(int i=0;i<columnName.length;i++){
//			if(columnName[i] instanceof String) {
//				String column=(String)columnName[i];
//				columns[i]=new Column();
//				if(column.indexOf(".")>0){
//					columns[i].setModel(column);
//				}else{
//					columns[i].setModel(tabelName+"."+column);
//				}
//			}else if(columnName[i] instanceof String[]){
//				String[] column=(String[])columnName[i];
//				columns[i]=new Column();
//				if(column[0].indexOf(".")>0){
//					columns[i].setModel(column[0]);
//				}else{
//					columns[i].setModel(tabelName+"."+column[0]);
//				}
//				columns[i].setColumnName(column[1]);
//				if(column.length>2){
//					columns[i].setCaption(column[2]);
//				}
//				if(column.length>3){
//					columns[i].setWidth(Integer.parseInt(column[3]));
//				}
//			}else if(columnName[i] instanceof Object[]) {
//				Object[] obj=(Object[])columnName[i];
//				columns[i]=new Column();
//				columns[i].setColumnName((String)obj[0]);
//				columns[i].setCaption((String)obj[1]);
//				columns[i].setDataType((Integer)obj[2]);
//				columns[i].setScale((Integer)obj[3]);
//				columns[i].setPrecision(4);
//				columns[i].setWidth(5);
//			}
//		}
//		return columns;
//	}
//}
