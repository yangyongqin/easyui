package com.evangelsoft.easyui.template.client;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.borland.dx.dataset.DataSet;
import com.evangelsoft.easyui.type.PrintAction;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.clientutil.JRDataSetDataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
public class PrintButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPopupMenu menu;
	RecordSet set;
	PrintButton(){
	}
	PrintButton(RecordSet set){
		this.set=set;
		init(set);
	}
	public	void init(RecordSet set){
		menu=new JPopupMenu();
		if(set!=null&&set.recordCount()>0){
			for(int i=0;i<set.recordCount();i++){
				final Record record=set.getRecord(i);
				PrintMenuItem item=	new PrintMenuItem(record);
				item.setText(record.getField("PRINT_NAME").getString());
				menu.add(item);
			}
		}
	}

	public void print(HashMap<String, Object> map,DataSet dataSet){
		this.map=map;
		this.dataSet=dataSet;
		menu.show(this, 10, this.getHeight());
	}

	private HashMap<String, Object> map;
	private DataSet dataSet;

	MenuItemAction itemAction=new MenuItemAction();

	public class MenuItemAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				PrintMenuItem item=(PrintMenuItem)e.getSource();
//				String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?><jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"printTest\" language=\"groovy\" columnCount=\"3\" printOrder=\"Horizontal\" pageWidth=\"612\" pageHeight=\"792\" columnWidth=\"190\" columnSpacing=\"1\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\"><parameter name=\"classesNo\" class=\"java.lang.String\"/><parameter name=\"classesName\" class=\"java.lang.String\"/><parameter name=\"title\" class=\"java.lang.String\"/><parameter name=\"footer\" class=\"java.lang.String\"/><field name=\"stuNo\" class=\"java.lang.String\"/><field name=\"stuName\" class=\"java.lang.String\"/><detail><band height=\"21\" splitType=\"Stretch\"><textField><reportElement x=\"0\" y=\"1\" width=\"117\" height=\"20\"/><textElement textAlignment=\"Center\"/><textFieldExpression class=\"java.lang.String\"><![CDATA[$F{stuNo}]]></textFieldExpression></textField><textField><reportElement x=\"117\" y=\"1\" width=\"117\" height=\"20\"/><textElement textAlignment=\"Center\"/><textFieldExpression class=\"java.lang.String\"><![CDATA[$F{stuName}]]></textFieldExpression></textField></band></detail></jasperReport>";
				
				String str=item.getRecord().getField("XML").getString();
				ByteArrayInputStream inStream = new ByteArrayInputStream( str.getBytes());
				//			JRDataSource dataSource = new JRBeanCollectionDataSource(dataset);
				
				JasperReport 	jr = JasperCompileManager.compileReport(inStream);
				JasperPrint jp=	JasperFillManager.fillReport(jr, map, new JRDataSetDataSource(dataSet));
				//			jp = JasperFillManager.fillReport(jr, map, dataSource);
				//¥Ú”°‘§¿¿
				if(PrintAction.PRINT.equals( item.getRecord().getField("ACTION").getString())){
					JasperPrintManager.printReport(jp, false);
				}else{
					JasperViewer.viewReport(jp,false);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	MenuItemAction menuItemAction=new MenuItemAction();
	public class PrintMenuItem extends JMenuItem{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Record record;
		PrintMenuItem(Record record){
			this.record=record;
			this.setAction(menuItemAction);
		}
		public Record getRecord() {
			return record;
		}
		public void setRecord(Record record) {
			this.record = record;
		}
	}
}
