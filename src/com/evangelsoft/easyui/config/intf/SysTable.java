package com.evangelsoft.easyui.config.intf;

import java.util.List;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.EntityListable;
import com.evangelsoft.econnect.plant.EntityWaiter;
import com.evangelsoft.econnect.session.RemoteException;

public interface SysTable extends  EntityWaiter, EntityListable{
	/**
	 * @param list
	 * @param data
	 * @param errMsg
	 * @return
	 * @throws RemoteException
	 */
	public boolean getList(String[] list, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException;

	public boolean getTable(Object key, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException;
}
