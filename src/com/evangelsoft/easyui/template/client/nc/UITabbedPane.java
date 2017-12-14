package com.evangelsoft.easyui.template.client.nc;

import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

/**
 * 功能:UITabbedPane
 * 1.fieldTranslate是否翻译,默认翻译
 * 作者:张扬
 */
public class UITabbedPane extends javax.swing.JTabbedPane {
	private boolean fieldTranslate = true;

    public UITabbedPane() {
        super(JTabbedPane.TOP);
    }

    public UITabbedPane(int tabPlacement) {
        super(tabPlacement);
    }

    public UITabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    public void updateUI() {
        setUI(new UITabbedPaneUI());
    }
	/**
	 *
	 * 创建日期:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getTitleAt(int no) {
	    return super.getTitleAt(no);
	}
	/**
	 *
	 * 创建日期:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getToolTipText() {
	    return super.getToolTipText();
	}
	/**
	 *
	 * 创建日期:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getToolTipText(MouseEvent e) {
	    return super.getToolTipText(e);
	}
	/**
	 * 获取 translate 特性 (boolean) 值.
	 * @return translate 特性值.
	 * @see #setTranslate
	 */
	public boolean isTranslate() {
		return fieldTranslate;
	}
	/**
	 *
	 * 创建日期:(2001-3-20 9:59:10)
	 */
	protected void processKeyEvent(java.awt.event.KeyEvent e) {
		if (isEnabled()) {
			if (e.getID() == java.awt.event.KeyEvent.KEY_PRESSED) {
				if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
					e.consume();
					return;
				}
			}
		}
		super.processKeyEvent(e);
	}
	/**
	 * 设置 translate 特性 (boolean) 值.
	 * @param translate 新的特性值.
	 * @see #getTranslate
	 */
	public void setTranslate(boolean translate) {
	}

//	public void removeListener() {
//		/** 去掉所有的监听引用关系*/
//		super.removeListener(listenerList);
//	}
}


