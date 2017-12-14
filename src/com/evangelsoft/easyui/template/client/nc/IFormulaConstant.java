package com.evangelsoft.easyui.template.client.nc;

/**
 * Created by IntelliJ IDEA.
 * User: cch
 * Date: 2004-9-16
 * Time: 13:32:43
 * 设置一些公用常量
 * @nopublish
 */
public interface IFormulaConstant
{
    //Null值 直接用Null表达  By CCH 20060525
//  public final static Double dbl_null = new Double(Double.MIN_VALUE);
    //POWER值
    public final static int INT_POWER = 9;
    //函数类型常量
    public final static int FUN_STRING = 0; //字符串函数
    public final static int FUN_MATH = 1;   //数学函数
    public final static int FUN_DATE = 2;   //日期函数
    public final static int FUN_DB = 3;     //数据库函数
    public final static int FUN_PRINT = 4;  //打印函数
    public final static int FUN_GL = 5;     //财务会计函数
    public final static int FUN_CUSTOM = 9; //自定义函数 
    public final static int FUN_COLLECT=10; //集合类函数
}
