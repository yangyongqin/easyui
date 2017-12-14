package com.evangelsoft.easyui.template.plugin;

import com.evangelsoft.easyui.plugin.intf.IPluginIListener;

public class TestDeleteBeforePlugin implements IPluginIListener
{

	@Override
	public Object run(Object obj) throws Exception {
		
		System.out.println("µ÷ÓÃ²å¼þ£º"+TestDeleteBeforePlugin.class);
		return null;
	}

}
