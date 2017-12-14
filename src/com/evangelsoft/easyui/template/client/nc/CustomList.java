package com.evangelsoft.easyui.template.client.nc;

/*
 * Created on 2005-12-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 特定于公式编辑器的列表组件。响应列表项选取，双击和键Enter事件。
 *
 * @author ljian
 */
public class CustomList extends JList<Object> {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	FormulaSpecifiedPanel ancestor;

    HashMap hsFormulaItems = null;

    public CustomList(FormulaSpecifiedPanel a, HashMap formulaItems) {
        this.ancestor = a;
        updateModel(formulaItems);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setVisibleRowCount(7);
        addListSelectionListener(createListSelectionListener());
        addMouseListener(createMouseAdapter());
        addKeyListener(createKeyAdapter());
    }

    public MouseAdapter createMouseAdapter() {
        MouseAdapter ma = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //			        notifyAncestor(e);
                    int index = locationToIndex(e.getPoint());
                    String item = (String) getModel().getElementAt(index);
                    ancestor.notifyItemSelected((FormulaItem) hsFormulaItems
                            .get(item));
                }
            }
        };
        return ma;
    }

    public KeyAdapter createKeyAdapter() {
        KeyAdapter ka = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    int index = CustomList.this.getSelectedIndex();
                    String item = (String) getModel().getElementAt(index);
                    ancestor.notifyItemSelected((FormulaItem) hsFormulaItems
                            .get(item));
                }
            }
        };
        return ka;
    }

    public ListSelectionListener createListSelectionListener() {
        ListSelectionListener ls = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                //                if (!e.getValueIsAdjusting()) {
                //                    String item =
                // (String)((JList)e.getSource()).getModel().getElementAt(e.getFirstIndex());
                //                    ancestor.notifyItemFocused((FormulaItem) hsFormulaItems
                //                          .get(item));
                //                }
                if (e.getValueIsAdjusting() == false) {
                    String item = (String) CustomList.this.getSelectedValue();
                    if (item != null) {
                        ancestor.notifyItemFocused((FormulaItem) hsFormulaItems
                                .get(item));
                    }
                }
            }

        };
        return ls;
    }

    public void updateModel(HashMap items) {
        if (items != null) {
            hsFormulaItems = items;

            DefaultListModel listModel = new DefaultListModel();

            Set keys = hsFormulaItems.keySet();
            TreeSet set = new TreeSet(getComparator());
            set.addAll(keys);
            Iterator i = set.iterator();
            while (i.hasNext()) {
            	listModel.addElement(i.next());
            }

            setModel(listModel);
        }
    }

    public Map getFormulaItems() {
        return hsFormulaItems;
    }

    private Comparator getComparator() {
        Comparator comp = new java.util.Comparator() {
            public int compare(Object o1, Object o2) {
                if (o1 != null && o2 != null) {
                    return o1.toString().compareTo(o2.toString());
                } else {
                    return -1;
                }
            }
        };
        return comp;
    }
}
