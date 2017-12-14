package com.evangelsoft.easyui.template.homeintf;

import com.evangelsoft.easyui.template.intf.PlugIn;
import com.evangelsoft.econnect.dataformat.VariantHolder;

public interface PlugInHome extends PlugIn{
	boolean getBeforePlugIn(Object funcId,String operate,VariantHolder<Object> list,VariantHolder<String> errMsg)throws Exception;
	boolean getAfterPlugIn(Object funcId,String operate,VariantHolder<Object> list,VariantHolder<String> errMsg)throws Exception;
	boolean getActionPlugIn(Object funcId,String operate,String beforeOrafter,VariantHolder<Object> list,VariantHolder<String> errMsg)throws Exception;
}
