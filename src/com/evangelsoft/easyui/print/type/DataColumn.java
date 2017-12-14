package com.evangelsoft.easyui.print.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ClassName: DataColumn 
 * @Description: 自定义打印接口对象和数据集映射
 * @author yyq
 * @date 2017年12月3日
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
