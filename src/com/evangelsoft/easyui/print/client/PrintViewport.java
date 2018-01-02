package com.evangelsoft.easyui.print.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JViewport;

public class PrintViewport extends JViewport {

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g2d.setColor(new Color(0, 0, 0));// 设置黑色字体,同样可以
		g2d.drawString("我是表格", (this.getWidth() - 100) / 2, (this.getHeight() + 15) / 2);// 绘制水印，具体水印绘制方式根据自己的需求修改
		g.drawImage(bi, 0, 0, this);
	}
}
