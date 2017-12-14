package com.evangelsoft.easyui.config.intf;

import java.math.BigDecimal;

import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.workbench.config.intf.SysRefNumber;

/** * @author  —Ó”¿«’
 E-mail:
@date £∫2016-6-10 œ¬ŒÁ09:18:56
@version 1.0   * @since    */
public interface SysRefNumberExt extends SysRefNumber {
	 @TxMode(2)
	  public abstract Object fetch(String paramString, BigDecimal paramBigDecimal, int paramInt)
	    throws Exception;
}
