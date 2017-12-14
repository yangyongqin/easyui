package com.evangelsoft.easyui.template.client;

import java.awt.Component;

import javax.swing.JScrollPane;

/** * @author  —Ó”¿«’
 E-mail:
@date £∫2016-5-7 œ¬ŒÁ09:15:03
@version 1.0   * @since    */
public class UIScrollPane extends JScrollPane{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String pageId;
	public UIScrollPane(Component view,String pageId){
		super(view);
		this.pageId=pageId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
