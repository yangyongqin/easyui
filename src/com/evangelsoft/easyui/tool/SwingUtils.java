package com.evangelsoft.easyui.tool;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;

public class SwingUtils {

	public static Point getMousePoint(final Component component) {
		final Point p = MouseInfo.getPointerInfo().getLocation();
		final Point los = component.getLocationOnScreen();
		return new Point(p.x - los.x, p.y - los.y);
	}
}
