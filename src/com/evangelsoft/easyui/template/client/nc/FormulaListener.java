/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.util.EventListener;


/**
 * @author ljian
 */
public interface FormulaListener extends EventListener {

    // ����
    public void itemSelected(FormulaEditEvent e);

    // ��ʾ
    public void itemFocused(FormulaEditEvent e);
}
