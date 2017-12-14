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
 *����������
 */
public class ActionHelp {
	static Logger logger=Logger.getLogger(BaseMasterDetailWaiter.class);
	/**
	 * ִ��ĳ����֮ǰ
	 * @param funcId
	 * @param operate
	 * @param data
	 * @return
	 */
	public static boolean actionBefore(String funcId,String operate,Object data)throws RuntimeException{
		return action(funcId, operate, ActionBeforeAfter.BEFORE, data);
	}
	/**
	 * ִ��ĳ����֮��
	 * @param funcId
	 * @param operate
	 * @param data
	 * @return
	 */
	public static boolean actionAfter(String funcId,String operate,Object data)throws RuntimeException{
		return action(funcId, operate, ActionBeforeAfter.AFTER, data);
	}
	/**
	 * ִ��ĳ����
	 * @param funcId ����ID
	 * @param operate ����
	 * @param beforeOrafter ִ��ǰ��
	 * @param data ����
	 * @return
	 * @throws Exception 
	 */
	public static boolean action(Object funcId,String operate,String beforeOrafter,Object data) throws RuntimeException{
		//��ѯ
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
					//�õ����ҵ��ִ࣬��
					try{
						Class<?> pluginClass = Class.forName(listSet.getRecord(0).getField("PLUGIN_CLASS").getString());
						IPluginIListener plugin=(IPluginIListener) pluginClass.newInstance();
						plugin.run(data);
					}catch(Exception e){
						logger.info("�����"+listSet.getRecord(0).getField("PLUGIN_NMAE")+"��ִ�г���",e);
						//r����Ǳ���ͬ��
						if(BoolStr.getBoolean( listSet.getRecord(i).getField("IS_TRAN").getString())){
							throw new RuntimeException(e);
						}
					}
					logger.info("�����{}��ִ�����");
				}
			}
			return true;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
