package com.evangelsoft.easyui.print.client;

import javax.swing.JTable;

import com.evangelsoft.easyui.print.type.PrintDesignView;

public class PrintTable extends JTable {

	/**
	 * @Fields serialVersionUID : °æ±¾ºÅ
	 */
	private static final long serialVersionUID = 1L;

	PrintDesignView panel;

	public PrintTable(PrintDesignView panel) {
		super();
		this.panel = panel;
	}

	public PrintDesignView getPanel() {
		return panel;
	}

	public void setPanel(PrintDesignView panel) {
		this.panel = panel;
	}

}
