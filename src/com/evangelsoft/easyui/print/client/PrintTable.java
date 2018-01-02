package com.evangelsoft.easyui.print.client;

import java.awt.SystemColor;

import javax.swing.JTable;

public class PrintTable extends JTable {

	/**
	 * @Fields serialVersionUID : °æ±¾ºÅ
	 */
	private static final long serialVersionUID = 1L;

	PrintDesignPanel panel;

	public PrintTable(PrintDesignPanel panel) {
		super();
		this.panel = panel;
	}

	public PrintDesignPanel getPanel() {
		return panel;
	}

	public void setPanel(PrintDesignPanel panel) {
		this.panel = panel;
	}

}
