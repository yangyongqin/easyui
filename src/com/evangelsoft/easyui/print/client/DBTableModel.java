package com.evangelsoft.easyui.print.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class DBTableModel extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5927010114400079869L;

	// private List<String> columnNames=new ArrayList<String>();

	private List<String> captionNames = new ArrayList<String>();

	HashMap<Integer, Object> map = new HashMap<Integer, Object>();

	private JTable table;

	public DBTableModel(JTable table) {
		this.table = table;
	}

	@Override
	public int getColumnCount() {
		return captionNames.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return this.map.get(column);
	}

	@Override
	public String getColumnName(int column) {
		if (column >= this.captionNames.size()) {
			return "X" + column;
		}
		return this.captionNames.get(column);
	}

	public void addColumn(String columnName, Object value) {
		this.captionNames.add(columnName);
		this.map.put(captionNames.size() - 1, value);
	};

	public void addColumn(Object columnName) {
		this.captionNames.add(columnName.toString());
		this.map.put(captionNames.size() - 1, "ֵ" + captionNames.size());
		fireTableStructureChanged();
		fireTableDataChanged();
	};

	@Override
	public void setValueAt(Object value, int row, int column) {
		try {

			this.map.put(column, value);
			fireTableCellUpdated(row, column);
		} catch (RuntimeException e) {
		}
		table.revalidate();
		table.repaint();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// if (column == 0) {
		// return true;
		// }
		return false;
	}

	public List<String> getCaptionNames() {
		return captionNames;
	}

	public void setCaptionNames(List<String> captionNames) {
		this.captionNames = captionNames;
	}

	@Override
	public int getRowCount() {
		return 1;
	}

}
