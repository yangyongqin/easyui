package com.evangelsoft.easyui.print.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PrintContentPanel extends JPanel {

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;

	PrintContentPanel() {
		this.setBackground(SystemColor.white);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g2d.setColor(new Color(0, 0, 0));
		g2d.setFont(new Font("����", 0, 18));// ���ú�ɫ����,ͬ������
		g2d.drawString(watermark, (this.getWidth() - 100) / 2, (this.getHeight() + 15) / 2);// ����ˮӡ������ˮӡ���Ʒ�ʽ�����Լ��������޸�
		g.drawImage(bi, 0, 0, this);
	}

	String watermark;

	public void setWatermark(String watermark) {
		this.watermark = watermark;
		this.repaint();
	}
}
