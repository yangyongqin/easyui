package com.evangelsoft.easyui.print.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * ClassName: PrintTableModel 
 * @Description: 打印表格数据模型
 * @author yyq
 * @date 2017年11月4日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintTableModel extends DefaultTableModel {

	/**
	 * @Fields serialVersionUID : 版本号
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
