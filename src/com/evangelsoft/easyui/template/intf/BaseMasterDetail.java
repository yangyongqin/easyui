package com.evangelsoft.easyui.template.intf;

import java.util.Map;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;

public interface BaseMasterDetail {
	/**
	 * 模板ID
	 */
	String TEMPLATE_ID="TEMPLATE_ID";
	/**
	 * 模板编号
	 */
	String TEMPLATE_NUM="TEMPLATE_NUM";
	/**
	 * 操作码
	 */
	String OPERATE_CODE="OPERATE_CODE";
	/**
	 * 功能ID
	 */
	String FUNCTION_ID="FUNCTION_ID";
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
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param key 主表主键
	 * @param data 添加的数据集合列表
	 * @param newKey 返回的主键给调用
	 * @param dataBackfill 后台修改的数据，返填到前台
	 * @param errMsg 提示消息，返回结果为true时为空
	 * @return boolean 返回操作执行结果
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean add(Map<String, Object> map,Object key, Object data, VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg)throws RemoteException ;
	/**
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param key 主表主键
	 * @param data  修改数据集合列表
	 * @param newKey 新的主键
	 * @param dataBackfill 后台修改数据返填回去的数据
	 * @param errMsg 提示消息，返回结果为true时为空
	 * @return boolean 返回操作执行结果
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean modify(Map<String, Object> map,Object key,Object data, VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg) throws RemoteException ;

	/**
	 * @param map  集合，模板ID，及以后审批流和业务插件可能用到的N多参数，因参数暂不定，采用map方式
	 * @param key 主表主键
	 * @param errMsg 消息提示，结果返回true是为空
	 * @return 返回操作执行结果
	 * @throws RemoteException
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean remove(Map<String, Object> map,Object key, VariantHolder<String> errMsg)throws RemoteException;
}
