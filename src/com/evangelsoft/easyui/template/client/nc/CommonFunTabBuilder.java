/*
 * Created on 2005-12-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.util.HashMap;


/**
 * @author ljian
 */
public class CommonFunTabBuilder extends AbstractTabBuilder {

    public CommonFunTabBuilder(FormulaWordSorter fws) {
        super(fws);
    }

    static String[] names = {"iif", "getColValue",
    	"or","and"};

    static String[] sigs = {"iif(, , )", "getColValue(, , , )","||","&&"};

    static String[] hintMsg = {"iif(condition,thenvalue,elsevalue)�ж�����condition�Ƿ�����,������㷵�ص�һ��ֵthenvalue,��������㷵�صڶ���ֵelsevalue",
            "getColValue(String tableName, String colName, String pkName, String refPkName)�õ���tableName�У���pkName= refPkNameʱ����colName��ֵ",
            "����",
            "����"};

    public HashMap getHSFormulaItems() {
        HashMap hsFormulaItems = new HashMap();
        for (int i = 0; i < names.length; i++) {
            hsFormulaItems.put(names[i], new FunctionFormulaItem(names[i], sigs[i], hintMsg[i]));
        }
        return hsFormulaItems;
    }

    public String getTabName() {
        return "����";
    }
}