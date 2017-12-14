package com.evangelsoft.easyui.template.client;

import javax.swing.JLabel;

/** * @author  —Ó”¿«’
 E-mail:
@date £∫2016-3-28 œ¬ŒÁ08:42:02
@version 1.0   * @since    */
public class ULabel extends JLabel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String pageId;
	private String attr;
	ULabel(String pageId,String attr){
		this.attr=attr;
		this.pageId=pageId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
}
