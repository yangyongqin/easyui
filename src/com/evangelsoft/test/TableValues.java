package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**   
 *     注意：一般使用AbstractTableModel创建TableModel的实现，只有少量数据时使用DefaultTableModel， 
 */
public class TableValues extends AbstractTableModel {

	private static final long serialVersionUID = -8430352919270533604L;

	public final static int NAME = 0;

	public final static int GENDER = 1;

	public  List<String> columnNames = new ArrayList<String>(); 
	TableValues(){
		columnNames.add("姓名");
		columnNames.add("性别");
		values.add(new ArrayList<Object>());
		values.add(new ArrayList<Object>());
		values.get(0).add("张三");
		values.get(0).add(true);
		values.get(1).add("李四");
		values.get(1).add(false);
		values.get(0).add("加的列");
		values.get(1).add("加的列");
	}
	
	public List<List<Object>> values = new ArrayList<List<Object>>();

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return values.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return values.get(rowIndex).get(columnIndex);
	}

	/** 
	 * 设置列名 
	 */
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
	public void addColumn(){
		columnNames.add( "增加的列");
		
	}
}