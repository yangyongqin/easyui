package com.evangelsoft.easyui.print.client;

import java.util.HashMap;

public class PrintDataManage {

	/**
	 * @Fields mainPrintStorageDataSet : �����ݼ���
	 */
	private PrintStorageDataSet mainPrintStorageDataSet;

	/**
	 * @Fields listPrintStorageDataSet : �б����ݼ�
	 */
	private PrintStorageDataSet listPrintStorageDataSet;

	/**
	 * @Fields printSet : ��ӡ���ݼ�
	 */
	HashMap<String, PrintStorageDataSet> printSet = new HashMap<String, PrintStorageDataSet>();

	public PrintStorageDataSet getMainPrintStorageDataSet() {
		return mainPrintStorageDataSet;
	}

	public void setMainPrintStorageDataSet(PrintStorageDataSet mainPrintStorageDataSet) {
		this.mainPrintStorageDataSet = mainPrintStorageDataSet;
		this.printSet.put(mainPrintStorageDataSet.getTableId(), mainPrintStorageDataSet);
	}

	public PrintStorageDataSet getListPrintStorageDataSet() {
		return listPrintStorageDataSet;
	}

	public void setListPrintStorageDataSet(PrintStorageDataSet listPrintStorageDataSet) {
		this.listPrintStorageDataSet = listPrintStorageDataSet;
		this.printSet.put(listPrintStorageDataSet.getTableId(), listPrintStorageDataSet);
	}

	public HashMap<String, PrintStorageDataSet> getPrintSet() {
		return printSet;
	}

	public void setPrintSet(HashMap<String, PrintStorageDataSet> printSet) {
		this.printSet = printSet;
	}

	public PrintDataManage(PrintStorageDataSet mainPrintStorageDataSet, PrintStorageDataSet listPrintStorageDataSet,
			HashMap<String, PrintStorageDataSet> printSet) {
		super();
		this.mainPrintStorageDataSet = mainPrintStorageDataSet;
		this.listPrintStorageDataSet = listPrintStorageDataSet;
		this.printSet = printSet;
		this.printSet.put(mainPrintStorageDataSet.getTableId(), mainPrintStorageDataSet);
		this.printSet.put(listPrintStorageDataSet.getTableId(), listPrintStorageDataSet);
	}

	public PrintDataManage(PrintStorageDataSet mainPrintStorageDataSet, PrintStorageDataSet listPrintStorageDataSet) {
		super();
		this.mainPrintStorageDataSet = mainPrintStorageDataSet;
		this.listPrintStorageDataSet = listPrintStorageDataSet;
		this.printSet.put(mainPrintStorageDataSet.getTableId(), mainPrintStorageDataSet);
		this.printSet.put(listPrintStorageDataSet.getTableId(), listPrintStorageDataSet);
	}

	public PrintDataManage(PrintStorageDataSet printStorageDataSet) {
		super();
		this.mainPrintStorageDataSet = printStorageDataSet;
		this.listPrintStorageDataSet = printStorageDataSet;
		this.printSet.put(mainPrintStorageDataSet.getTableId(), mainPrintStorageDataSet);
		this.printSet.put(listPrintStorageDataSet.getTableId(), listPrintStorageDataSet);
	}

	public void addPrintDataManage(PrintStorageDataSet printStorageDataSet) {
		this.printSet.put(printStorageDataSet.getTableId(), printStorageDataSet);
	}

	public PrintDataManage() {
		super();
	}

}
