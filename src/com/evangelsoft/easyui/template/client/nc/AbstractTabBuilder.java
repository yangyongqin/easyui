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
 * <code>TabBuilder</code>��ĳ���ʵ�֣���<code>CustomList</code>����֯���������.
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
        // Ϊ������ͼ����б�
        CustomList list = new CustomList(pnlTab, getHSFormulaItems());
        getScrollPane().setViewportView(list);
        // ljian++ at 2006-02-08 ����������ʼ��Ϊ�ؼ���, ��ʹ����ʾ��ɫ
        FormulaUtil.initFormulaWordSorter(fws, list, -1);
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
        }
        return scrollPane;
    }

    /**
     * ����key = [FormulaItem.dispalyname], value =[FormulaItem]��Hashmap<br>
     * �����Լ�����Ҫ���й������Hashmap.ע��keyֵһ��Ҫ��FormulaItem.dispalynameһ����
     * @return
     */
    public abstract HashMap getHSFormulaItems();
}
