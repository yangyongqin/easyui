/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import javax.swing.JPanel;


/**
 * @author ljian
 */
public class FormulaSpecifiedPanel extends JPanel {

    public void addFormularListener(FormulaListener l) {
        listenerList.add(FormulaListener.class, l);
    }

    public void notifyItemSelected(FormulaItem item) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == FormulaListener.class) {
                FormulaEditEvent eve = new FormulaEditEvent(this, item);
                ((FormulaListener) listeners[i + 1]).itemSelected(eve);
            }
        }
    }

    public void notifyItemFocused(FormulaItem item) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == FormulaListener.class) {
                FormulaEditEvent eve = new FormulaEditEvent(this, item);
                ((FormulaListener) listeners[i + 1]).itemFocused(eve);
            }
        }
    }
}