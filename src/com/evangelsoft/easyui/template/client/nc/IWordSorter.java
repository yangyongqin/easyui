package com.evangelsoft.easyui.template.client.nc;

import java.util.Set;

/**
 * 
 * ��������:(2003-2-27 11:55:48)
 * @author:�Լ̽�
 */
public interface IWordSorter extends IWordType {
/**
 * 
 * ��������:(2003-2-27 21:14:54)
 * @return java.util.Set
 */
Set getDelimiters();
public String getEndingAdhesiveDeliminiter(String deliminiter);
String getTransferMeaningDeliminiter();
/**
 * 
 * ��������:(2003-2-27 15:56:10)
 * @param wordType int
 */
int getWordType(String word);
public boolean isAdhesiveDeliminiter(String deliminiter);
}
