package com.evangelsoft.easyui.template.plugin;

import org.apache.log4j.Logger;

import com.evangelsoft.easyui.plugin.intf.IPluginIListener;
import com.evangelsoft.easyui.template.homeintf.PlugInHome;
import com.evangelsoft.easyui.template.waiter.BaseMasterDetailWaiter;
import com.evangelsoft.easyui.type.ActionBeforeAfter;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.workbench.types.BoolStr;

/**
 * @author yyq
 *动作辅助类
 */
public class ActionHelp {
	static Logger logger=Logger.getLogger(BaseMasterDetailWaiter.class);
	/**
	 * 执行某动作之前
	 * @param funcId
	 * @param operate
	 * @param data
	 * @return
	 */
	public static boolean actionBefore(String funcId,String operate,Object data)throws RuntimeException{
		return action(funcId, operate, ActionBeforeAfter.BEFORE, data);
	}
	/**
	 * 执行某动作之后
	 * @param funcId
	 * @param operate
	 * @param data
	 * @return
	 */
	public static boolean actionAfter(String funcId,String operate,Object data)throws RuntimeException{
		return action(funcId, operate, ActionBeforeAfter.AFTER, data);
	}
	/**
	 * 执行某动作
	 * @param funcId 功能ID
	 * @param operate 操作
	 * @param beforeOrafter 执行前后
	 * @param data 数据
	 * @return
	 * @throws Exception 
	 */
	public static boolean action(Object funcId,String operate,String beforeOrafter,Object data) throws RuntimeException{
		//查询
		try{
			PlugInHome plugInHome = (PlugInHome) WaiterFactory.getWaiter(PlugInHome.class);
			VariantHolder<Object> list=new VariantHolder<Object>();
			VariantHolder<String> errMsg=new VariantHolder<String>();
			if(!plugInHome.getActionPlugIn(funcId, operate,beforeOrafter ,list, errMsg)){
				throw new RuntimeException(errMsg.value);
			}
			RecordSet listSet=(RecordSet)list.value;
			for(int i=0;i<listSet.recordCount();i++){
				if(Boolean.getBoolean(listSet.getRecord(i).getField("IS_ENABLE").getString())){
					//得到查找的类，执行
					try{
						Class<?> pluginClass = Class.forName(listSet.getRecord(0).getField("PLUGIN_CLASS").getString());
						IPluginIListener plugin=(IPluginIListener) pluginClass.newInstance();
						plugin.run(data);
					}catch(Exception e){
						logger.info("插件【"+listSet.getRecord(0).getField("PLUGIN_NMAE")+"】执行出错",e);
						//r如果是必须同步
						if(BoolStr.getBoolean( listSet.getRecord(i).getField("IS_TRAN").getString())){
							throw new RuntimeException(e);
						}
					}
					logger.info("插件【{}】执行完毕");
				}
			}
			return true;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
