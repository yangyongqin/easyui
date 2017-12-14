package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**   
 *     ע�⣺һ��ʹ��AbstractTableModel����TableModel��ʵ�֣�ֻ����������ʱʹ��DefaultTableModel�� 
 */
public class TableValues extends AbstractTableModel {

	private static final long serialVersionUID = -8430352919270533604L;

	public final static int NAME = 0;

	public final static int GENDER = 1;

	public  List<String> columnNames = new ArrayList<String>(); 
	TableValues(){
		columnNames.add("����");
		columnNames.add("�Ա�");
		values.add(new ArrayList<Object>());
		values.add(new ArrayList<Object>());
		values.get(0).add("����");
		values.get(0).add(true);
		values.get(1).add("����");
		values.get(1).add(false);
		values.get(0).add("�ӵ���");
		values.get(1).add("�ӵ���");
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
	 * �������� 
	 */
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
	public void addColumn(){
		columnNames.add( "���ӵ���");
		
	}
}