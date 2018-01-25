package com.evangelsoft.easyui.print.client;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;

import javax.swing.JPanel;

public class PrintDisablePanel extends JPanel {

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	public PrintDisablePanel() {
		this.setBackground(SystemColor.BLUE);
	}

	/*@Override
	protected void paintComponent(Graphics g) {
		// 画俩条线，表示禁用的面板
		// 判断线条方向
		Graphics2D g2d = (Graphics2D) g;
		// 添加抗锯齿效果
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		g.drawLine(0, this.getHeight(), this.getWidth(), 0);
		g.drawLine(0, 0, this.getWidth(), this.getHeight());
		// 关闭抗齿距效果
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}*/
}
