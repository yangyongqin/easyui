package com.evangelsoft.easyui.template.client.nc;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cch
 * Date: 2004-9-28
 * Time: 8:49:56
 * 公式分析器接口
 * @nopublish
 */
public interface IFormulaAnalyser
{
	/**
	 * 拆分分析公式数组
	 * @param formulas 公式字符串数组
	 * @return
	 */
    List split(String[] formulas);
}
