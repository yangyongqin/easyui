package com.evangelsoft.easyui.template.client.nc;

import java.util.Set;

/**
 * 
 * 创建日期:(2003-2-27 11:55:48)
 * @author:赵继江
 */
public interface IWordSorter extends IWordType {
/**
 * 
 * 创建日期:(2003-2-27 21:14:54)
 * @return java.util.Set
 */
Set getDelimiters();
public String getEndingAdhesiveDeliminiter(String deliminiter);
String getTransferMeaningDeliminiter();
/**
 * 
 * 创建日期:(2003-2-27 15:56:10)
 * @param wordType int
 */
int getWordType(String word);
public boolean isAdhesiveDeliminiter(String deliminiter);
}
