//package com.evangelsoft.easyui.print.client;
//
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.Point;
//import java.awt.event.MouseListener;
//
//import javax.swing.border.AbstractBorder;
//import javax.swing.border.Border;
//import javax.swing.table.TableColumn;
//
//import com.evangelsoft.easyui.print.type.PrintItem;
//
//public class PrintTableColumn extends TableColumn {
//
//	/**
//	 * @Fields serialVersionUID : °æ±¾ºÅ
//	 */
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @Fields uniqueId : Î¨Ò»id
//	 */
//	private int uniqueId;
//
////	private int width;
////
////	private int height;
//
//	private int fontSize;
//
//	private String fontName;
//
//	public int getUniqueId() {
//		return uniqueId;
//	}
//
//	PrintElementSource printSoucre;
//
//	public PrintTableColumn(PrintElementType printElementType, PrintDesignPanel panel) {
//		super();
//	}
//
//	public void setUniqueId(int uniqueId) {
//		this.uniqueId = uniqueId;
//	}
//
//	public int getWidth() {
//		return width;
//	}
////
////	public void setWidth(int width) {
////		this.width = width;
////	}
////
////	public int getHeight() {
////		return height;
////	}
////
////	public void setHeight(int height) {
////		this.height = height;
////	}
//
//	public int getFontSize() {
//		return fontSize;
//	}
//
//	public void setFontSize(int fontSize) {
//		this.fontSize = fontSize;
//	}
//
//	public String getFontName() {
//		return fontName;
//	}
//
//	public void setFontName(String fontName) {
//		this.fontName = fontName;
//	}
//
//
//	@Override
//	public Border getBorder() {
//		return null;
//	}
//
//	@Override
//	public double setZoomRatio() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public double getZoomRatio() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setBorder(Border border) {
//		
//	}
//
//	@Override
//	public Point getLocation() {
//		return null;
//	}
//
//	@Override
//	public void setLocation(Point point) {
//		
//	}
//
//	@Override
//	public void setVerticalAlignment(int alignment) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setHorizontalAlignment(int alignment) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setIsBold(boolean boolStr) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setIsitalic(boolean boolStr) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setIsstrikethrough(boolean boolStr) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setIsUnderline(boolean boolStr) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setElementHorizontalAlignment(String alignment) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setElementVerticalAlignment(String alignment) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Dimension getSize() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Container getParent() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setSize(Dimension dimension) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void addMouseListener(MouseListener l) {
//	}
//
//	@Override
//	public int getHeight() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setHeight(int height) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean getIsBold() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean getIsitalic() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean getIsstrikethrough() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean getIsUnderline() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public String getElementHorizontalAlignment() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getElementVerticalAlignment() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getType() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PrintPage getPrintPage() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PrintDesignPanel getParentPanel() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
