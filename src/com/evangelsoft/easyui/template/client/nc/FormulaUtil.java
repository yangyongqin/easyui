package com.evangelsoft.easyui.template.client.nc;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA. User: hxr Date: 2004-4-30 Time: 11:10:49
 */
public class FormulaUtil {

    static void initFormulaWordSorter(FormulaWordSorter fws, CustomList list, int wordtype) {
        if (fws == null || list == null) {
            return;
        }


        Collection values = list.getFormulaItems().values();
		Iterator iter = values.iterator();
		while(iter.hasNext()) {
		    FormulaItem fi = (FormulaItem)iter.next();
		    String[] sigs = fi.getKeyWord();
	        for (int i = 0; i < sigs.length; i++)
			{
	        	addToKeyword(fws, wordtype, sigs[i]);
			}
		}
    }

	private static void addToKeyword(FormulaWordSorter fws, int wordtype, String sig)
	{
		if (wordtype == IWordSorter.DELIMITER)
			fws.addDeliminiter(sig);
		else if (wordtype == IWordSorter.OPERATOR)
			fws.addOperator(sig);
		else
			fws.addKeyword(sig);
	}
}
