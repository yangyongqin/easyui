package com.evangelsoft.easyui.print.type;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.evangelsoft.workbench.types.BoolStr;

public class PrintItemTool {

	static Map<String, Method> methodMap = new HashMap<String, Method>();
	static {
		// 根据注解获取反射信息
		Method[] methods = PrintItem.class.getDeclaredMethods();
		for (Method method : methods) {
			DataColumn loginTokenAnnotation = method.getDeclaringClass().getAnnotation(DataColumn.class);

			if (loginTokenAnnotation == null) {
				loginTokenAnnotation = method.getAnnotation(DataColumn.class);
			}
			if (loginTokenAnnotation != null) {
				methodMap.put(loginTokenAnnotation.dataColumn(), method);
			}
		}
	}

	public static void setValue(List<PrintItem<?>> itemList, String columnName, Object value) {
		if (itemList != null && value != null && methodMap.containsKey(columnName)) {
			Method method = methodMap.get(columnName);
			try {
				// 如果是boolean类型 ，则将值转换为boolean类型
				Object newValue = value;
				if (value instanceof String
						&& method.getParameterTypes().length == 1
						&& (method.getParameterTypes()[0] == Boolean.class || method.getParameterTypes()[0] == boolean.class)) {
					// method.invoke(item,
					// BoolStr.getBoolean(value.toString()));
					newValue = value.toString();
				}
				// 如果值类型是BigDecimal 而方法是int，需要将值转换为int
				else if (value instanceof BigDecimal
						&& method.getParameterTypes().length == 1
						&& (method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == Integer.class)) {

					newValue = ((BigDecimal) value).intValue();
				}
				// 如果值类型是BigDecimal 而方法是String，需要将值转换为String
				else if (value instanceof BigDecimal && method.getParameterTypes().length == 1
						&& method.getParameterTypes()[0] == String.class) {
					newValue = value.toString();
				}
				// 循环赋值
				for (PrintItem<?> item : itemList) {
					method.invoke(item, newValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Description: 克隆打印对象
	 * @param entity
	 * @return   
	 * @return T  
	 * @throws
	 * @author yyq
	 * @date 2017年12月17日
	 */
	public <T extends PrintItem> T clone(T entity) {
		return null;
	}

	public static void setValue(PrintItem item, String columnName, Object value) {
		if (item != null && value != null && methodMap.containsKey(columnName)) {
			Method method = methodMap.get(columnName);
			try {
				// 如果是boolean类型 ，则将值转换为boolean类型
				if (value instanceof String
						&& method.getParameterTypes().length == 1
						&& (method.getParameterTypes()[0] == Boolean.class || method.getParameterTypes()[0] == int.class)) {
					method.invoke(item, BoolStr.getBoolean(value.toString()));
				}
				// 如果值类型是BigDecimal 而方法是int，需要将值转换为int
				else if (value instanceof BigDecimal
						&& method.getParameterTypes().length == 1
						&& (method.getParameterTypes()[0] == Integer.class || method.getParameterTypes()[0] == int.class)) {
					method.invoke(item, ((BigDecimal) value).intValue());
				}
				// 如果值类型是BigDecimal 而方法是String，需要将值转换为String
				else if (value instanceof BigDecimal && method.getParameterTypes().length == 1
						&& (method.getParameterTypes()[0] == String.class)) {
					method.invoke(item, value.toString());
				} else {
					method.invoke(item, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void copy(PrintItem<?> dataSource, PrintItem<?> item) {
		item.setBorder(dataSource.getBorder());
//		item.setElementHorizontalAlignment(dataSource.getElementHorizontalAlignment());
//		item.setElementVerticalAlignment(dataSource.getElementVerticalAlignment());
		item.setFontName(dataSource.getFontName());;
		item.setFontSize(dataSource.getFontSize());
		item.setHeight(dataSource.getHeight());
		// item.setHorizontalAlignment(dataSource.gete);
		item.setIsBold(dataSource.getIsBold());
		item.setIsitalic(dataSource.getIsitalic());
		item.setIsUnderline(dataSource.getIsUnderline());
		item.setSize(dataSource.getSize());
		item.setText(dataSource.getText());
		// item.setVerticalAlignment(dataSource.getV);
		item.setWidth(dataSource.getWidth());
		item.setHorizontalAlignment(dataSource.getHorizontalAlignment());
		item.setVerticalAlignment(dataSource.getVerticalAlignment());
	}

}
