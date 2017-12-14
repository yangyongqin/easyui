/*
 * Created on 2005-12-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.io.Serializable;

/**
 * @author ljian
 */
public class FormulaItem implements Serializable {

    String displayName;
    String inputSig;
    String hintMsg;
    
    public FormulaItem(String displayName) {
        super();
        this.displayName = displayName;
        this.inputSig = displayName;
        this.hintMsg = displayName;
    }
    
    public FormulaItem(String displayName, String hintMsg) {
        super();
        this.displayName = displayName;
        this.inputSig = displayName;
        this.hintMsg = hintMsg;
    }
    
    /**
     * @param displayName
     * @param inputName
     * @param hintMsg
     */
    public FormulaItem(String displayName, String inputSig, String hintMsg) {
        super();
        this.displayName = displayName;
        this.inputSig = inputSig;
        this.hintMsg = hintMsg;
    }
    /**
     * @return Returns the displayName.
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName The displayName to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    /**
     * @return Returns the hintMsg.
     */
    public String getHintMsg() {
        return hintMsg;
    }
    /**
     * @param hintMsg The hintMsg to set.
     */
    public void setHintMsg(String hintMsg) {
        this.hintMsg = hintMsg;
    }
    /**
     * @return Returns the inputName.
     */
    public String getInputSig() {
        return inputSig;
    }
    /**
     * @param inputName The inputName to set.
     */
    public void setInputSig(String inputSig) {
        this.inputSig = inputSig;
    }
    
    public String[] getKeyWord()
    {
    	return new String[]{getInputSig()};
    }
}
