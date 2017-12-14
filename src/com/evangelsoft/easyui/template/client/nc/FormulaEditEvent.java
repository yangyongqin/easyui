/*
 * Created on 2005-12-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.util.EventObject;

/**
 * @author ljian
 */
public class FormulaEditEvent extends EventObject {
    
    FormulaItem formulaItem;

    /**
     * @param source
     */
    public FormulaEditEvent(Object source) {
        super(source);
        // TODO Auto-generated constructor stub
    }
    
    public FormulaEditEvent(Object source, FormulaItem formulaItem) {
        super(source);
        this.formulaItem = formulaItem;    
    }
    
    public String getInputSig() {
        return formulaItem.getInputSig();
    } 
    
    public String getHintMsg() {
        return formulaItem.getHintMsg();    
    }

}
