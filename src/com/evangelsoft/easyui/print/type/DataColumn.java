package com.evangelsoft.easyui.print.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ClassName: DataColumn 
 * @Description: �Զ����ӡ�ӿڶ�������ݼ�ӳ��
 * @author yyq
 * @date 2017��12��3��
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
 
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataColumn {

	String dataColumn();
}
