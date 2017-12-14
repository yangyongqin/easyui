//package com.evangelsoft.easyui.template.client.nc;
//
///**
// * 载入用户默认风格。
// */
//public class DefaultStyle extends Style {
///**
// * DefaultStyle 构造子注解。
// */
//public DefaultStyle() {
//	super();
//}
///**
// * 此处插入方法说明。
// * 创建日期：(2001-6-25 16:41:29)
// * @param param int
// */
//public DefaultStyle(String pk_user) {
//	super(pk_user);
//}
///**
// * initStyle 方法注解。
// */
//protected void initStyle(String pk_user) {
////	CtrlPropVO[] vos = null;
////	try {
//		// mobified by leijun 10/10/2003  客户端远程调用和并用
//		//vos = UserStyleBO_Client.queryByUserSelected(pk_user, "Y");
////		vos = (CtrlPropVO[])nc.ui.sm.cmenu.Desktop.getApplet().getLoginSessBean().get("USERSTYLE");
////		if(vos == null)
////			vos = UserStyleBO_Client.queryByUserSelected(pk_user, "Y");
//
////		Color color = null;
////		Font font = null;
////		if (vos != null && vos.length > 0) {
////			for (int i = 0; i < vos.length; i++) {
////				switch (vos[i].getType().intValue()) {
////					case 0 :
////						//颜色
////						color = UserStyleUI.getColor(vos[i].getValue());
////						javax.swing.UIManager.put(vos[i].getCtrlKey().trim(), color);
////						break;
////					case 1 :
////						font = UserStyleUI.getFont(vos[i].getValue());
////						javax.swing.UIManager.put(vos[i].getCtrlKey().trim(), font);
////						break;
////					case 2 :
////						break;
////						//
////						//
////				}
////			}
////		}
////	} catch (Exception e) {
////	}
//}
//}
