package com.evangelsoft.easyui.template.client.nc;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cch
 * Date: 2004-9-28
 * Time: 8:49:56
 * ��ʽ�������ӿ�
 * @nopublish
 */
public interface IFormulaAnalyser
{
	/**
	 * ��ַ�����ʽ����
	 * @param formulas ��ʽ�ַ�������
	 * @return
	 */
    List split(String[] formulas);
}
