package com.evangelsoft.easyui.template.client.nc;

/**
 * Created by IntelliJ IDEA.
 * User: cch
 * Date: 2004-9-16
 * Time: 13:32:43
 * ����һЩ���ó���
 * @nopublish
 */
public interface IFormulaConstant
{
    //Nullֵ ֱ����Null���  By CCH 20060525
//  public final static Double dbl_null = new Double(Double.MIN_VALUE);
    //POWERֵ
    public final static int INT_POWER = 9;
    //�������ͳ���
    public final static int FUN_STRING = 0; //�ַ�������
    public final static int FUN_MATH = 1;   //��ѧ����
    public final static int FUN_DATE = 2;   //���ں���
    public final static int FUN_DB = 3;     //���ݿ⺯��
    public final static int FUN_PRINT = 4;  //��ӡ����
    public final static int FUN_GL = 5;     //�����ƺ���
    public final static int FUN_CUSTOM = 9; //�Զ��庯�� 
    public final static int FUN_COLLECT=10; //�����ຯ��
}
