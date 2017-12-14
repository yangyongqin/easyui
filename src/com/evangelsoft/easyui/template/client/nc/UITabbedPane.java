package com.evangelsoft.easyui.template.client.nc;

import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

/**
 * ����:UITabbedPane
 * 1.fieldTranslate�Ƿ���,Ĭ�Ϸ���
 * ����:����
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
	 * ��������:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getTitleAt(int no) {
	    return super.getTitleAt(no);
	}
	/**
	 *
	 * ��������:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getToolTipText() {
	    return super.getToolTipText();
	}
	/**
	 *
	 * ��������:(2001-4-27 19:13:52)
	 * @return java.lang.String
	 */
	public String getToolTipText(MouseEvent e) {
	    return super.getToolTipText(e);
	}
	/**
	 * ��ȡ translate ���� (boolean) ֵ.
	 * @return translate ����ֵ.
	 * @see #setTranslate
	 */
	public boolean isTranslate() {
		return fieldTranslate;
	}
	/**
	 *
	 * ��������:(2001-3-20 9:59:10)
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
	 * ���� translate ���� (boolean) ֵ.
	 * @param translate �µ�����ֵ.
	 * @see #getTranslate
	 */
	public void setTranslate(boolean translate) {
	}

//	public void removeListener() {
//		/** ȥ�����еļ������ù�ϵ*/
//		super.removeListener(listenerList);
//	}
}


