package com.evangelsoft.easyui.report.intf;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.session.RemoteException;

public interface ReportTool {
	/**
	 * �õ���ṹ
	 * @param data
	 * @param errMsg
	 * @return
	 * @throws RemoteException
	 */
	public boolean getStructure(String sql,VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException ;
}
