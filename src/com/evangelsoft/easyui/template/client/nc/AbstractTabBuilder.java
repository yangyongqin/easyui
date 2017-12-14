/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * <code>TabBuilder</code>类的抽象实现，用<code>CustomList</code>来组织界面和数据.
 *
 * @author ljian
 */
public abstract class AbstractTabBuilder extends TabBuilder {

    JScrollPane scrollPane = null;

    JPanel hintMsgPane = null;

    DefaultListModel listModel = null;

    HashMap hsFormulaItems = null;

    FormulaWordSorter fws = null;

    public AbstractTabBuilder(FormulaWordSorter fws) {
        super();
        this.fws = fws;
    }

    public void addUIControls() {
        pnlTab = new FormulaSpecifiedPanel();
        pnlTab.setLayout(new BorderLayout());
        pnlTab.add(getScrollPane(), BorderLayout.CENTER);
    }

    public void initalize() {
        // 为滑动视图添加列表
        CustomList list = new CustomList(pnlTab, getHSFormulaItems());
        getScrollPane().setViewportView(list);
        // ljian++ at 2006-02-08 将函数名初始化为关键字, 以使它显示蓝色
        FormulaUtil.initFormulaWordSorter(fws, list, -1);
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
        }
        return scrollPane;
    }

    /**
     * 返回key = [FormulaItem.dispalyname], value =[FormulaItem]的Hashmap<br>
     * 根据自己的需要自行构造这个Hashmap.注意key值一定要与FormulaItem.dispalyname一样。
     * @return
     */
    public abstract HashMap getHSFormulaItems();
}
