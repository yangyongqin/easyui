package com.evangelsoft.easyui.template.intf;

import java.util.Map;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;

public interface BaseSingleDataSet {
	/**
	 *
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param data 空数据的集合
	 * @param errMsg 提示消息，返回结果为true时为空
	 * 查询结构，便于在其他引用构造
	 */
	public boolean getStructure(Map<String, Object> map, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException ;
	/**
	 *
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param key 单据主键
	 * @param data 用于返回查询到数据
	 * @param errMsg 提示消息，返回结果为true时为空
	 * @return boolean 返回操作执行结果
	 * @throws RemoteException
	 * 根据主键查询对应的单据信息
	 */
	public boolean get(Map<String, Object> map,Object key, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException;
	/**
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param filter 查询条件
	 * @param list 返回数据列表
	 * @param errMsg 错误提示
	 * @return boolean 返回操作执行结果
	 */
	public boolean list(Map<String, Object> map,Object filter, VariantHolder<Object> list,
			VariantHolder<String> errMsg)throws RemoteException  ;
	
	/**
	 * @param paramObject 集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param data 数据
	 * @param errMsg 提示消息
	 * @return
	 * @throws RemoteException
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	  public  boolean flush(Map<String, Object> map,Object data, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg)
	    throws RemoteException;
}
