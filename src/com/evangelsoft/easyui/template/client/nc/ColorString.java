package com.evangelsoft.easyui.template.client.nc;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * 创建日期:(2003-2-27 12:06:29)
 * @author:赵继江
 */
public class ColorString {
	private String value;
	private Color foreColor;
	/** reserved fields */
	private Color backgroundColor;
	private Font font;
/**
 * StringWrapper 构造子注解.
 */
public ColorString() {
	super();
}
/**
 *
 * 创建日期:(2003-2-27 17:06:57)
 * @return java.awt.Color
 */
public java.awt.Color getForeColor() {
	return foreColor;
}
/**
 *
 * 创建日期:(2003-2-27 17:06:57)
 * @return java.lang.String
 */
public java.lang.String getValue() {
	return value;
}
/**
 *
 * 创建日期:(2003-2-27 17:06:57)
 * @param newForeColor java.awt.Color
 */
public void setForeColor(java.awt.Color newForeColor) {
	foreColor = newForeColor;
}
/**
 *
 * 创建日期:(2003-2-27 17:06:57)
 * @param newValue java.lang.String
 */
public void setValue(java.lang.String newValue) {
	value = newValue;
}
}
