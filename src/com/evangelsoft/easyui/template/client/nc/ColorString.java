package com.evangelsoft.easyui.template.client.nc;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * ��������:(2003-2-27 12:06:29)
 * @author:�Լ̽�
 */
public class ColorString {
	private String value;
	private Color foreColor;
	/** reserved fields */
	private Color backgroundColor;
	private Font font;
/**
 * StringWrapper ������ע��.
 */
public ColorString() {
	super();
}
/**
 *
 * ��������:(2003-2-27 17:06:57)
 * @return java.awt.Color
 */
public java.awt.Color getForeColor() {
	return foreColor;
}
/**
 *
 * ��������:(2003-2-27 17:06:57)
 * @return java.lang.String
 */
public java.lang.String getValue() {
	return value;
}
/**
 *
 * ��������:(2003-2-27 17:06:57)
 * @param newForeColor java.awt.Color
 */
public void setForeColor(java.awt.Color newForeColor) {
	foreColor = newForeColor;
}
/**
 *
 * ��������:(2003-2-27 17:06:57)
 * @param newValue java.lang.String
 */
public void setValue(java.lang.String newValue) {
	value = newValue;
}
}
