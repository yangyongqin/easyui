package com.evangelsoft.easyui.template.client.nc;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * 创建日期:(2003-2-27 21:37:22)
 * @author:赵继江
 */
public abstract class AbstractWordSorter implements IWordSorter {
	protected HashSet deliminiters = null;
	protected HashSet operators = null;
	protected HashSet keywords = null;
/**
 * JavaWordSorter 构造子注解.
 */
public AbstractWordSorter() {
	super();
	initWordSet();
}
/**
 *
 * 创建日期:(2003-2-27 21:14:54)
 * @return java.util.Set
 */
public java.util.Set getDelimiters() {
	return deliminiters;
}
/**
 *     结束粘连分割符的分割符.如:默认情况下,引号的结束符为引
 * 号; /*的结束符为 * /.
 *
 * 创建日期:(2003-3-2 16:14:08)
 * @return java.lang.String 返回为null时,表示粘连分割符的作用域为到本行结束
 * @param deliminiter java.lang.String
 */
public String getEndingAdhesiveDeliminiter(String deliminiter) {
	if (deliminiter.equals("\"")) {
		return deliminiter;
	}
	else if (deliminiter.equals("/*")) {
		return "*/";
	}
	//返回为null,表示粘连分割符的作用域为到本行结束
	return null;
}
/**
 *      转义分割符.在字符串中,在转义分割符后出现的字符失去分割
 *  符的含义,作为普通字符处理.
 *
 * 创建日期:(2003-3-2 15:55:12)
 * @return java.lang.String
 */
public String getTransferMeaningDeliminiter() {

	//默认的转移分割符:
	return "\\";
}
/**
 *
 * 创建日期:(2003-2-27 21:46:36)
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
 * 创建日期:(2003-2-27 15:56:10)
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
 * 创建日期:(2003-2-27 21:53:49)
 */
protected abstract void initWordSet();
/**
 * 是否粘连分割符.
 *
 * 创建日期:(2003-3-2 15:45:59)
 * @return boolean
 * @param deliminiter java.lang.String
 */
public boolean isAdhesiveDeliminiter(String deliminiter) {
	return false;
}
}
