package com.evangelsoft.easyui.print.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JViewport;

import com.borland.dbswing.TableScrollPane;

public class PrintTableScrollPane extends TableScrollPane {

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	PrintTableScrollPane(Component component) {
		super(component);
		JViewport oldniew = this.getViewport();
		// 将旧的Viewport属性赋值于新的Viewport，不赋值界面不显示。。。
		JViewport newView = new PrintViewport();
		newView.setAlignmentX(oldniew.getAlignmentX());
		newView.setAlignmentY(oldniew.getAlignmentY());
		newView.setAutoscrolls(oldniew.getAutoscrolls());
		newView.setBackground(oldniew.getBackground());
		newView.setBorder(oldniew.getBorder());
		newView.setBounds(oldniew.bounds());
		newView.setComponentOrientation(oldniew.getComponentOrientation());
		newView.setComponentPopupMenu(oldniew.getComponentPopupMenu());
		// newView.setComponentZOrder(oldniew.get, index);
		newView.setCursor(oldniew.getCursor());
		newView.setDebugGraphicsOptions(oldniew.getDebugGraphicsOptions());
		newView.setDoubleBuffered(oldniew.isDoubleBuffered());
		newView.setEnabled(oldniew.isEnabled());
		newView.setDropTarget(oldniew.getDropTarget());
		newView.setExtentSize(oldniew.getExtentSize());
		newView.setIgnoreRepaint(oldniew.getIgnoreRepaint());
		newView.setInheritsPopupMenu(oldniew.getInheritsPopupMenu());
		newView.setInputVerifier(oldniew.getInputVerifier());
		newView.setLayout(oldniew.getLayout());
		newView.setLocale(oldniew.getLocale());
		newView.setLocation(oldniew.getLocation());
		newView.setMaximumSize(oldniew.getMaximumSize());
		newView.setMinimumSize(oldniew.getMaximumSize());
		newView.setName(oldniew.getName());
		newView.setNextFocusableComponent(oldniew.getNextFocusableComponent());
		newView.setPreferredSize(oldniew.getPreferredSize());
		newView.setScrollMode(oldniew.getScrollMode());
		newView.setUI(oldniew.getUI());
		newView.setVerifyInputWhenFocusTarget(oldniew.getVerifyInputWhenFocusTarget());
		newView.setView(oldniew.getView());
		newView.setViewPosition(oldniew.getViewPosition());
		newView.setViewSize(oldniew.getViewSize());
		newView.setDefaultLocale(oldniew.getDefaultLocale());
		this.setViewport(newView);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g2d.setColor(new Color(0, 0, 0));
		g2d.setFont( new Font("黑体", 0, 18));// 设置黑色字体,同样可以
		g2d.drawString("我是表格", (this.getWidth() - 100) / 2, (this.getHeight() + 15) / 2);// 绘制水印，具体水印绘制方式根据自己的需求修改
		g.drawImage(bi, 0, 0, this);
	}
}
