package com.evangelsoft.easyui.print.client;

import com.borland.dx.dataset.StorageDataSet;

public class PrintStorageDataSet {

	/**
	 * 数据集合
	 */
	private StorageDataSet dataSet;

	/**
	 * 表名
	 */
	private String tableId;

	/**
	 * 表名称描述
	 */
	private String tableDesc;

	public StorageDataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(StorageDataSet dataSet) {
		this.dataSet = dataSet;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}
