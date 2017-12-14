package com.evangelsoft.easyui.task.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.evangelsoft.easyui.tool.EasyDataSetHelper;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;

public class TaskDeployFrame extends com.evangelsoft.easyui.template.client.BaseSingleDataSetFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public TaskDeployFrame(){
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pack();
	}
	private JButton addButton,updateButton;
	private void initialize() throws Exception {
//		this.newButton.setVisible(false);
//		this.saveButton.setVisible(false);
//		this.saveButton.setVisible(false);

		addButton=new JButton();
		mainToolBar.add(addButton);
		addButton.setAction(new AddAction());
		addButton.setText("����");
		mainToolBar.addSeparator();

		updateButton=new JButton();
		mainToolBar.add(updateButton);
		updateButton.setAction(new UpdateAction());
		updateButton.setText("�༭");

	}

	/** Save action. */
	protected class AddAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			//�򿪲���ҳ��
			Column[] columns = TaskDeployFrame.this.dataSet.getColumns();
			RecordSet set= DeployAddDialog.select(TaskDeployFrame.this, null, columns, false);
			if(set!=null){
				DataSetHelper.resetStatus(dataSet);
				//�����Ķ�λ��һ��
				dataSet.first();
				dataSet.insertRow(true);
				/*List<String> ignoreList=new ArrayList<String>(){
					{
						this.add("DEPLOY_ID");
					}
				};*/
				List<String> ignoreList=	new ArrayList<String>();
				ignoreList.add("DEPLOY_ID");
				EasyDataSetHelper.loadRowFromRecord(dataSet, set.getRecord(0), false,null,ignoreList);
//				TaskDeployFrame.this.saveButton.doClick();
//				DataSetHelper.
			}
		}
	}


	@Override
	protected void showStatus() {
		super.showStatus();
		if (!dataSet.isEditingNewRow() && dataSet.isEmpty()) {
			// �����ڵ�ǰ����ʱ���������showRowStatus����Ҫ�ڴ˴������뵱ǰ�����йصĶ�����
			updateButton.setEnabled(false);
		}

	}

	@Override
	protected void showRowStatus() {
		updateButton.setEnabled(true);
		super.showRowStatus();
	}

	protected class UpdateAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			//�򿪲���ҳ��
			Column[] columns = TaskDeployFrame.this.dataSet.getColumns();
			RecordFormat format = new RecordFormat("@");
			DataSetHelper.saveMetaToRecordFormat(dataSet, format);
			Record localRecord = new Record(format);
			DataRow localDataRow = new DataRow(dataSet);
			dataSet.getDataRow(localDataRow);
			DataSetHelper.saveRowToRecord(localDataRow, localRecord);

			RecordSet set= DeployAddDialog.select(TaskDeployFrame.this, null, columns,localRecord, false);
			if(set!=null){
				DataSetHelper.resetStatus(dataSet);
				//�����Ķ�λ��һ��
				dataSet.first();
				dataSet.insertRow(true);
				/*List<String> ignoreList=new ArrayList<String>(){
					{
						this.add("DEPLOY_ID");
					}
				};*/
				List<String> ignoreList=	new ArrayList<String>();
				ignoreList.add("DEPLOY_ID");
				EasyDataSetHelper.loadRowFromRecord(dataSet, set.getRecord(0), false,null,ignoreList);
//				TaskDeployFrame.this.saveButton.doClick();
//				DataSetHelper.
			}
		}
	}

}
