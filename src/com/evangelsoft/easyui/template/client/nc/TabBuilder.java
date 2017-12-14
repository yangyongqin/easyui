/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import javax.swing.JPanel;


/**
 * 抽象父类,子类根据需要在<code>addUIControls</code>方法中往<code>FormulaSpecifiedPanel</code>
 * 中添加组件,在<code>FormulaSpecifiedPanel</code>中统一处理事件后，转发给公式编辑面板处理
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
