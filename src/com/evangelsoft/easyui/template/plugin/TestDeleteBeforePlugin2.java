package com.evangelsoft.easyui.template.plugin;

import com.evangelsoft.easyui.plugin.intf.IPluginIListener;

public class TestDeleteBeforePlugin2 implements IPluginIListener
{

	@Override
	public Object run(Object obj) throws Exception {
		System.out.println("���ò����"+TestDeleteBeforePlugin2.class);
		return null;
	}

}
