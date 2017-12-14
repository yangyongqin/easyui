package com.evangelsoft.easyui.template.intf;

import java.util.Map;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.EntityListable;
import com.evangelsoft.econnect.plant.EntityWaiter;

public interface SysTemplate extends  EntityWaiter, EntityListable{

	public boolean getAllButton(Map<String,Object> filter, VariantHolder<Object> list,
			VariantHolder<String> errMsg);
}
