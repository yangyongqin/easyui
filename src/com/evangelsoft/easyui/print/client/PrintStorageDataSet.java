package com.evangelsoft.easyui.print.client;

import com.borland.dx.dataset.StorageDataSet;

public class PrintStorageDataSet {

	/**
	 * ���ݼ���
	 */
	private StorageDataSet dataSet;

	/**
	 * ����
	 */
	private String tableName;

	/**
	 * ����������
	 */
	private String tableDesc;

	public StorageDataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(StorageDataSet dataSet) {
		this.dataSet = dataSet;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

}
