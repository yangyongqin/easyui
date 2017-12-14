package com.evangelsoft.easyui.print.client;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.dataformat.Record;

public class SourceItemTransferable implements Transferable,Serializable {
	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 只接受指定类型的数据
	 */
	public static final DataFlavor FILTER_CREAT_FLAVOR = new DataFlavor(FieldRecord.class, ":record");

	private Record record;
	public	SourceItemTransferable(Record record){
		this.record=record;
	}

	private ArrayList<DataFlavor> aflavor = new ArrayList<DataFlavor>();

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return aflavor.contains(flavor);
	}
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] f = new DataFlavor[aflavor.size()];
		aflavor.toArray(f);
		return f;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (FILTER_CREAT_FLAVOR.equals(flavor))
			return record;

		throw new UnsupportedFlavorException(flavor);
	}

}
