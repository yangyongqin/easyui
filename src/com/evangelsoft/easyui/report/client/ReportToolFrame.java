package com.evangelsoft.easyui.report.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import com.evangelsoft.crosslink.jxhumanresource.document.intf.JxForfeit;
import com.evangelsoft.easyui.report.intf.ReportTool;
import com.evangelsoft.easyui.template.client.BaseSingleDataSetFrame;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.client.WireWorker.Hook;
import com.evangelsoft.econnect.client.WireWorker.Worker;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.sun.org.apache.bcel.internal.generic.ISUB;

/**
 * @author yyq
 *
 */
public class ReportToolFrame extends BaseSingleDataSetFrame
{
	public ReportToolFrame(){
		super();
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private JTextArea sqlArea;
	private JButton execButton;
	private void initialize() throws Exception {
		this.refreshButton.setVisible(false);
	}

	//此页面不允许保存
	protected boolean beforeSave() {
		return false;
	}

	@Override
	protected void prepared(Object data) throws Exception {
		super.prepared(data);
//		this.filterSimplePanel.setVisible(true);
		filterPanel.removeAll();
		sqlArea=new JTextArea();
		sqlArea.setPreferredSize(new Dimension(550,250));
		execButton=new JButton();
		execButton.setText("执行");
		execButton.addActionListener(new ExecSqlActionListener());
		filterPanel.setLayout(new BorderLayout());
		this.filterPanel.add(sqlArea);
		this.filterPanel.add(execButton,BorderLayout.EAST);
//		filterConditionsPanel.setVisible(true);
//		filterPanel.setVisible(true);
//		filterButton.setVisible(true);
		filterModePanel.removeAll();

		filterModePanel.setVisible(true);

		listPane.setTopComponent(filterPanel);
		listPane.setDividerSize(5);
	}
	private class ExecSqlActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//
			worker.setWorker(new Worker() {
				public Object work() throws Throwable {
//					String methodName = "";
//					Method method = JxForfeit.class.getMethod(methodName,new Class[] { Object.class, VariantHolder.class,
//					VariantHolder.class });
//					VariantHolder<String> errMsg = new VariantHolder<String>();
//					VariantHolder<Object> feedback = new VariantHolder<Object>();
//					feedback.value = new TransientRecordSet[] {new TransientRecordSet(), null, null };
//					JxForfeit paylist = (JxForfeit) (new RMIProxy(Consumer
//					.getDefaultConsumer().getSession()))
//					.newStub(JxForfeit.class);
//					if (!((Boolean) method.invoke(paylist, buildKey(), feedback,errMsg))) {
//					throw new RemoteException(errMsg.value);
//					}
					ReportTool report=(ReportTool) (new RMIProxy(Consumer
							.getDefaultConsumer().getSession()))
							.newStub(ReportTool.class);
					VariantHolder<Object>data=new VariantHolder<Object>();
					VariantHolder<String>errMsg=new VariantHolder<String>();
					if(!report.getStructure(sqlArea.getText(), data, errMsg)){
						throw new RuntimeException(errMsg.value);
					}
					return (Object) data.value;
				}
			});
			worker.setHook(new Hook() {
				public void hook(Object feedback) {


					worker.setHook(null);
					RecordSet set=(RecordSet)feedback;
					RecordFormat format = set.getFormat();
					ArrayList<RecordFieldFormat> flieds = format.getFields();
					if(ReportToolFrame.this.isModified()){
						dataSet.deleteAllRows();
					}

//					dataSet.deleteAllRows();
					for(int i=0;i<flieds.size();i++){
//						dataSet.clearStatus();
						// 点击的是checkbox才执行

						RecordFieldFormat flied=flieds.get(i);
//						dataSet.clearStatus();
						dataSet.insertRow(false);
						dataSet.setString("FLD_ID",flied.getName() );
						dataSet.setBigDecimal("LINE_NUM",new BigDecimal( flied.getIndex()+1));
						dataSet.setString("FLD_NAME",flied.getName());
						dataSet.setString("DATA_TYPE",flied.getTypeDescription());
						dataSet.setBigDecimal("DATA_LEN",new BigDecimal( flied.getLength()));
						dataSet.setBigDecimal("DATA_DEC",new BigDecimal( flied.getDecimal()));
						dataSet.setBigDecimal("WIDTH",new BigDecimal(flied.getLength()));
						dataSet.setString("CHAR_CASE","N");
//						dataSet.setBigDecimal("FLD_ID",new BigDecimal( flied.getIndex()));
//						dataSet.setBigDecimal("FLD_ID",new BigDecimal( flied.getIndex()));
//						dataSet.setBigDecimal("FLD_ID",new BigDecimal( flied.getIndex()));
//						flied.getLabel();
//						flied.getOrdinal();
//						flied.getDecimal();
//						flied.getType();
//						flied.getWidth();
//						flied.getTypeDescription();
//						flied.getLength();
//						flied.getDecimal();

//						loading = true;
					}
//					loadEntity((RecordSet[]) feedback);
//					showStatus();
				}
			});
			worker.start();

		}


	}
}