/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import javax.swing.JPanel;


/**
 * ������,���������Ҫ��<code>addUIControls</code>��������<code>FormulaSpecifiedPanel</code>
 * ��������,��<code>FormulaSpecifiedPanel</code>��ͳһ�����¼���ת������ʽ�༭��崦��
 *
 * @author ljian
 */
public abstract class TabBuilder {
	FormulaSpecifiedPanel pnlTab = null;

    public abstract void addUIControls();

    public abstract void initalize();

    public JPanel getPnlTab() {
        return pnlTab;
    }

    public abstract String getTabName();
}
