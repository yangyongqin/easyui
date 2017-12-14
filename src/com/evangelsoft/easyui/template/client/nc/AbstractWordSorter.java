package com.evangelsoft.easyui.template.client.nc;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * ��������:(2003-2-27 21:37:22)
 * @author:�Լ̽�
 */
public abstract class AbstractWordSorter implements IWordSorter {
	protected HashSet deliminiters = null;
	protected HashSet operators = null;
	protected HashSet keywords = null;
/**
 * JavaWordSorter ������ע��.
 */
public AbstractWordSorter() {
	super();
	initWordSet();
}
/**
 *
 * ��������:(2003-2-27 21:14:54)
 * @return java.util.Set
 */
public java.util.Set getDelimiters() {
	return deliminiters;
}
/**
 *     ����ճ���ָ���ķָ��.��:Ĭ�������,���ŵĽ�����Ϊ��
 * ��; /*�Ľ�����Ϊ * /.
 *
 * ��������:(2003-3-2 16:14:08)
 * @return java.lang.String ����Ϊnullʱ,��ʾճ���ָ����������Ϊ�����н���
 * @param deliminiter java.lang.String
 */
public String getEndingAdhesiveDeliminiter(String deliminiter) {
	if (deliminiter.equals("\"")) {
		return deliminiter;
	}
	else if (deliminiter.equals("/*")) {
		return "*/";
	}
	//����Ϊnull,��ʾճ���ָ����������Ϊ�����н���
	return null;
}
/**
 *      ת��ָ��.���ַ�����,��ת��ָ������ֵ��ַ�ʧȥ�ָ�
 *  ���ĺ���,��Ϊ��ͨ�ַ�����.
 *
 * ��������:(2003-3-2 15:55:12)
 * @return java.lang.String
 */
public String getTransferMeaningDeliminiter() {

	//Ĭ�ϵ�ת�Ʒָ��:
	return "\\";
}
/**
 *
 * ��������:(2003-2-27 21:46:36)
 * @return java.util.Set
 * @param wordType int
 */
private Set getWordSet(int wordType) {

	if (wordType == IWordType.DELIMITER) {
		return deliminiters;
	}
	else if (wordType == IWordType.OPERATOR) {
		return operators;
	}
	else if (wordType == IWordType.KEYWORD) {
		return keywords;
	}
	return null;
}
/**
 *
 * ��������:(2003-2-27 15:56:10)
 * @param wordType int
 */
public int getWordType(String word) {
	for (int i = IWordType.MIN_TYPE; i <= IWordType.MAX_TYPE; i++) {
		Set wordSet = getWordSet(i);
		if (wordSet != null) {
			if (wordSet.contains(word)) {
				return i;
			}
		}
	}
	return IWordType.DEFAULT;
}
/**
 *
 * ��������:(2003-2-27 21:53:49)
 */
protected abstract void initWordSet();
/**
 * �Ƿ�ճ���ָ��.
 *
 * ��������:(2003-3-2 15:45:59)
 * @return boolean
 * @param deliminiter java.lang.String
 */
public boolean isAdhesiveDeliminiter(String deliminiter) {
	return false;
}
}
