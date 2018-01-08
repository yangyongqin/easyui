package com.evangelsoft.easyui.print.type;

import java.util.HashMap;
import java.util.List;

import com.evangelsoft.easyui.print.client.PrintPage;

public interface PrintDesignManage {

	public List<PrintDesignView> getLinkedPanel();

	public int getPageWidth();

	public int getPageHeight();

	public int getOrientation();

	public int getLeftMargin();

	public int getRightmargin();

	public int getTopMargin();

	public int getBottomMargin();

	public int getColNum();

	public int getColWidth();

	public HashMap<Integer, PrintDesignView> getPrintPanelMap();

	public PrintPage getPrintPage();

}
