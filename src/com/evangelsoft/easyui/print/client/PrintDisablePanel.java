package com.evangelsoft.easyui.print.client;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;

import javax.swing.JPanel;

public class PrintDisablePanel extends JPanel {

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;

	public PrintDisablePanel() {
		this.setBackground(SystemColor.BLUE);
	}

	/*@Override
	protected void paintComponent(Graphics g) {
		// �������ߣ���ʾ���õ����
		// �ж���������
		Graphics2D g2d = (Graphics2D) g;
		// ��ӿ����Ч��
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		g.drawLine(0, this.getHeight(), this.getWidth(), 0);
		g.drawLine(0, 0, this.getWidth(), this.getHeight());
		// �رտ��ݾ�Ч��
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}*/
}
