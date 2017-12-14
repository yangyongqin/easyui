package com.evangelsoft.easyui.print.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * ClassName: PrintTableModel 
 * @Description: ��ӡ�������ģ��
 * @author yyq
 * @date 2017��11��4��
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintTableModel extends DefaultTableModel {

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 3470609634549290445L;

	Map<Integer, Object> objectMap = new HashMap<Integer, Object>();

	@Override
	public Object getValueAt(int row, int column) {
		try {
			if (!objectMap.containsKey(column)) {
				return "";
			}
			return objectMap.get(column);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	@Override
	public int getColumnCount() {
		return objectMap.size();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		objectMap.put(column, aValue);
		fireTableCellUpdated(row, column);
	}
	@Override
	public void addColumn(Object columnName) {
		// TODO Auto-generated method stub
		super.addColumn(columnName);
	}
	public String getColumnName(int columnIndex){
		return objectMap.get(columnIndex).toString();
	}
}
