package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang.StringUtils;

import com.borland.dbswing.JdbLabel;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataChangeAdapter;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetAware;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.EditAdapter;
import com.borland.dx.dataset.EditListener;
import com.borland.dx.dataset.ExceptionEvent;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.NavigationListener;
import com.borland.dx.dataset.ProviderHelp;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.RowStatus;
import com.borland.dx.dataset.Sort;
import com.borland.dx.dataset.SortDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.TdDataSet;
import com.borland.dx.dataset.Variant;
import com.borland.jb.util.ErrorResponse;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.easyui.template.intf.BaseSingleDataSet;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.type.DefaultValue;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.client.WireWorker;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.condutil.SortItem;
import com.evangelsoft.econnect.condutil.SortList;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.EntityMonoEditable;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.workbench.clientdataset.DataSetExceptionAdapter;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientutil.ConditionItemsHelper;
import com.evangelsoft.workbench.clientutil.ProgressPanel;
import com.evangelsoft.workbench.clientutil.SortItemsHelper;
import com.evangelsoft.workbench.clientutil.VisibleWireWorker;
import com.evangelsoft.workbench.panelbase.ConditionTreePanel;
import com.evangelsoft.workbench.panelbase.SortListPanel;
import com.evangelsoft.workbench.swing.JInternalDialog;

/**
 * The class SingleDataSetFrame is a templet frame of single data set
 * architecture. This frame contains one base data set, implementing querying,
 * editing of the base data set. Developer may reuse the frame in a simple way.<br>
 * <p>
 * An inherited single data set frame is always built step by step as following:<br>
 * 1. Build columns of data set in UI designer, then bind these columns with
 * <b>dataSet</b>.<br>
 * 2. Place filter fields on <b>filterSimplePanel</b>.<br>
 * 3. Override methods <b>prepareData</b> and <b>prepared</b>, which is called
 * when the frame is first opened. Work about preparing is finished here. Field
 * <b>entityClass</b> should be assigned to the interface which this frame
 * supports. Functions in the frame will be auto-implemented based on the
 * interface appointed. <b>keyColumns</b> indicates the primary key of the data
 * set, and it's required to fetch inlet data. <b>uniqueColumns</b> is used to
 * forbid duplicate rows at client side. If <b>uniqueColumns</b> is null,
 * <b>keyColumns<b> will be taken as its default. <br>
 * 4. Override method <b>checkPrivileges</b>, which is called after
 * <b>prepareData</b>. Privileges checking is finished here.<br>
 * 5. Override method <b>buildFilter</b>, compose <b>filterTree</b> based on
 * the content of filter fields.<br>
 * 6. Override method <b>validateData</b> to do some data checking. Exception
 * can be thrown here to terminate post action. Default value setting may be
 * completed here.<br>
 * <p>
 * Inlet can be plugged into the frame if needed. An inlet is an extra area
 * displaying message linked to current row in the <b>dataSet</b>. Single data
 * set with inlet linked to it is somewhat like master-detail architecture, but
 * it's a loose master-detail relationship, which can be combined and unbound
 * momentarily. Inlet is similar to property dialog. When a property dialog is
 * opened, the base frame is untouchable until the dialog returns. Since a modal
 * dialog will block the whole user interface, that is, user can't do anything
 * else when a modal dialog is opened, we replace property dialog with inlet,
 * which is placed on the base frame. Extra control is implemented in the
 * architecture to prevent conflicts between the base frame and the inlet on it.
 * The usage of inlet is described as below:<br>
 * 1. Place a container (such as JPanel) on the frame as a inlet, and then place
 * some components on the container, just like frame designing. <br>
 * 2. Place a component (such as JToggleButton on the right-most of the menu
 * bar) to control whether this inlet is visible or not.<br>
 * 3. Implement data manipulation of the inlet. If data of inlet changed, call
 * <b>setInletModified</b> to inform the base frame.<br>
 * 4. Override method <b>listInlet</b>, which retrieves inlet data and displays
 * it on the frame. Action listInletAction calls listInlet when action performs.
 * Developer always binds this action with certain component (such as refresh
 * button) on inlet container. If <b>listInlet</b> is a time consuming or RMI
 * work, it's recommended that it runs in a background thread. Wire worker
 * object <b>worker</b> is always used in this situation.<br>
 * 5. Override method <b>clearInlet</b>, which clears current inlet data. It's
 * call automatically when there is no valid row on the base data set.<br>
 * 6. Override method <b>flushInlet</b>, which saves inlet data back into
 * server. Action flushInletAction calls flushInlet when action performs.
 * Developer always binds this action with contain component (such as save
 * button) on inlet container. If <b>listInlet</b> is a time consuming or RMI
 * work, it's recommended that it runs in a background thread. Wire worker
 * object <b>worker</b> is always used in this situation.<br>
 * <br>
 *
 * @author Mountain
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BaseSingleDataSetFrame extends JInternalFrame implements EditListener{

	HashMap<String,StorageDataSetPanel> dataSetMap=new HashMap<String, StorageDataSetPanel>();


	// @wb: begin of declaration
	public JLabel maxRowsLabel;
	public JSpinner maxRowsSpinner;
	protected JPanel mainToolBarControlPaddingPanel;
	/** Select action. */
	protected class SelectAction extends AbstractAction {
		public SelectAction() {
			super(DataModel.getDefault().getCaption("SELECT"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/select.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"SELECT"));
		}

		public void actionPerformed(ActionEvent e) {
			if (listTable.getSelectedRow() < 0) {
				return;
			}

			RecordFormat format = new RecordFormat("@");
			DataSetHelper.saveMetaToRecordFormat(dataSet, format);
			selections = new RecordSet(format);
			selections.setTrace(false);
			DataRow row = new DataRow(dataSet);
			int[] rows = listTable.getSelectedRows();
			for (int i : rows) {
				listTable.getDataSet().getDataRow(i, row);
				Record record = new Record(format);
				DataSetHelper.saveRowToRecord(row, record);
				selections.append(record);
			}

			BaseSingleDataSetFrame.this
			.fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
		}
	}

	public static int DEFAULT_MAX_ROWS = 1000;
	public static int MANDATORY_MAX_ROWS = 0;

	public static String DEFAULT_HOT_KEYS;;

	protected SelectAction selectAction = new SelectAction();

	/** Close action. */
	protected class CloseAction extends AbstractAction {
		public CloseAction() {
			super(DataModel.getDefault().getCaption("CLOSE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/close.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"CLOSE"));
		}

		public void actionPerformed(ActionEvent e) {
			BaseSingleDataSetFrame.this
			.fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
		}
	};

	protected CloseAction closeAction = new CloseAction();

	/** Filter action. */
	protected class FilterAction extends AbstractAction {
		public FilterAction() {
			super(DataModel.getDefault().getCaption("FILTER"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/filter.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"FILTER"));
		}

		public void actionPerformed(ActionEvent e) {
			filterMenu.show(filterButton, 0, filterButton.getHeight());
		}
	};

	protected FilterAction filterAction = new FilterAction();

	/** Refresh action. */
	protected class RefreshAction extends AbstractAction {
		public RefreshAction() {
			super(DataModel.getDefault().getCaption("REFRESH"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/refresh.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"REFRESH"));
		}

		public void actionPerformed(ActionEvent e) {
			dataSet.empty();
			dataSet.reset();

			filterTree.clear();
			sortList.clear();
			try {
				if (filterSimpleMenuItem.isSelected()) {
					buildFilter();
				} else {
					filterTree = conditionTreePanel.getTree();
					sortList = sortListPanel.getList();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BaseSingleDataSetFrame.this,
						ExceptionFormat.format(ex), getTitle(),
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			HashMap<String, Object> filter = new HashMap<String, Object>();
			filter.put("bound", boundTree);
			filter.put("filter", filterTree);
			filter.put("sort", sortList);

			beforeRefresh();

			class Worker implements WireWorker.Worker {
				private Object filter;

				public Worker(Object filter) {
					super();
					this.filter = filter;
				}

				public Object work() throws Throwable {
					Object rs = listEntity(filter);
					return rs;
				}
			}
			;
			class Hook implements WireWorker.Hook {
				public void hook(Object value) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					DataSetHelper.loadFromRecordSet(dataSet, (RecordSet) value);
					modified = false;

					buildUniqueIndex();

					if (filterAutoHiddenMenuItem.isSelected()) {
						filterMenuItem.setSelected(false);
						filterPanel.setVisible(false);
						listPane.resetToPreferredSizes();
					}

					if (!dataSet.isEmpty()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								rowId = -1;
								dataSet.first();
							}
						});
					} else {
						rowId = -1;
						clearInlet();
						showStatus();
					}

					afterRefresh();
				}
			}

			class Corrector implements WireWorker.Corrector {
				public void correct(Throwable e) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					showStatus();
				}
			}

			class Resumer implements WireWorker.Resumer {
				public void resume() {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					showStatus();
				}
			}

			if (!worker.isIdle()) {
				return;
			}
			worker.setWorker(new Worker(filter));
			worker.setHook(new Hook());
			worker.setCorrector(new Corrector());
			worker.setResumer(new Resumer());
			worker.start();
		}
	};

	protected RefreshAction refreshAction = new RefreshAction();





	/** New action. */
	protected class NewAction extends AbstractAction {
		public NewAction() {
			super(DataModel.getDefault().getCaption("NEW"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/add.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"NEW"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen()) {
				return;
			}
			if (!((DataSet)dataSet).atLast())
				((DataSet)dataSet).last();

			try {
				dataSet.insertRow(false);
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(dataSet, listTable, ex));
					return;
				} else {
					throw ex;
				}
			}

			if (isModified()) {
				showStatus();
			} else {
				setModified(true);
			}
		}
	};

	protected NewAction newAction = new NewAction();

	/** Save action. */
	protected class SaveAction extends AbstractAction {
		public SaveAction() {
			super(DataModel.getDefault().getCaption("SAVE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/save.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"SAVE"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || !isModified() && dataSet.isEmpty()) {
				return;
			}

			try {
				if (tdDataSet != null) {
					tdDataSet.post();
				} else {
					dataSet.post();
				}
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(dataSet, listTable, ex));
					return;
				} else {
					throw ex;
				}
			}

			if(!beforeSave()){
				return;
			}

			DeltaRecordSet data = new DeltaRecordSet();
			DataSetHelper.saveToDeltaRecordSet(dataSet, data, true, false);

			class Worker implements WireWorker.Worker {
				private Object data;

				public Worker(Object data) {
					super();
					this.data = data;
				}

				public Object work() throws Throwable {
					return flushEntity(data);
				}
			}

			class Hook implements WireWorker.Hook {
				public void hook(Object value) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					if (value != null) {
						DataSetHelper.backfillFromDeltaRecordSet(dataSet,
								(DeltaRecordSet) value);
					}
					dataSet.resetPendingStatus(true);
					ProviderHelp.endResolution(dataSet);
					// DataSetHelper.resetStatus(dataSet);
					dataSet.mergeChanges(false);

					modified = false;

					// Trigger event to display selected row in table control.
					dataSet.goToRow(dataSet.getLongRow());

					showStatus();

					afterSave();
				}
			}

			class Corrector implements WireWorker.Corrector {
				public void correct(Throwable e) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					dataSet.resetPendingStatus(false);
					ProviderHelp.endResolution(dataSet);

					// showStatus();
				}
			}

			class Resumer implements WireWorker.Resumer {
				public void resume() {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					dataSet.resetPendingStatus(false);
					ProviderHelp.endResolution(dataSet);

					// showStatus();
				}
			}

			worker.setHook(new Hook());
			worker.setCorrector(new Corrector());
			worker.setResumer(new Resumer());
			worker.setWorker(new Worker(data));
			worker.start();
		}
	};

	protected SaveAction saveAction = new SaveAction();

	/** Cancel action. */
	protected class CancelAction extends AbstractAction {
		public CancelAction() {
			super(DataModel.getDefault().getCaption("CANCEL"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/cancel.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"CANCEL"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || !isModified()) {
				return;
			}

			dataSet.cancel();
			dataSet.reset();

			if (!dataSet.isEmpty()) {
				if (dataSet.getLongRow() <= 0) {
					dataSet.first();
				} else if (rowId == Long.MAX_VALUE) {
					// Cancel from inserting, trigger the navigated event.
					dataSet.goToRow(dataSet.getLongRow() - 1);
				} else {
					// Trigger event to display selected row in table control.
					dataSet.goToRow(dataSet.getLongRow());
				}
			} else {
				clearInlet();
			}

			setModified(false);
		}
	};

	protected CancelAction cancelAction = new CancelAction();

	/** Delete action. */
	protected class DeleteAction extends AbstractAction {
		public DeleteAction() {
			super(DataModel.getDefault().getCaption("DELETE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/delete.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"DELETE"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || dataSet.isEmpty()
					&& !dataSet.isEditingNewRow()) {
				return;
			}

			if (isEntityFlushable()) {
				try {
					dataSet.deleteRow();
					if (dataSet.isEmpty()) {
						clearInlet();
					}
				} catch (DataSetException ex) {
					if (DataSetException.getExceptionListeners() != null) {
						DataSetException.getExceptionListeners().dispatch(
								new ExceptionEvent(dataSet, listTable, ex));
						return;
					} else {
						throw ex;
					}
				}
				setModified(true);
			} else if (isEntityMonoEditable()) {
				if (JOptionPane
						.showConfirmDialog(BaseSingleDataSetFrame.this, DataModel
								.getDefault().getSentence("MSG_DELETE_PROMPT"),
								getTitle(), JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}

				class Worker implements WireWorker.Worker {
					public Object work() throws Throwable {
						removeEntity(buildKey());

						return null;
					}
				}

				class Hook implements WireWorker.Hook {
					public void hook(Object value) {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);

						dataSet.deleteRow();
						// DataSetHelper.resetStatus(dataSet);
						dataSet.mergeChanges(false);
						if (dataSet.isEmpty()) {
							clearInlet();
						}
						modified = false;
						showStatus();
					}
				}

				class Corrector implements WireWorker.Corrector {
					public void correct(Throwable e) {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);

						// showStatus();
					}
				}

				class Resumer implements WireWorker.Resumer {
					public void resume() {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);

						// showStatus();
					}
				}

				worker.setWorker(new Worker());
				worker.setHook(new Hook());
				worker.setCorrector(new Corrector());
				worker.setResumer(new Resumer());
				worker.start();
			}
		}
	};

	protected DeleteAction deleteAction = new DeleteAction();

	/** First action. */
	protected class FirstAction extends AbstractAction {
		public FirstAction() {
			super(DataModel.getDefault().getCaption("FIRST"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/first.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"FIRST"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || dataSet.atFirst()) {
				return;
			}
			dataSet.first();
		}
	};

	protected FirstAction firstAction = new FirstAction();

	/** Prior action. */
	protected class PriorAction extends AbstractAction {
		public PriorAction() {
			super(DataModel.getDefault().getCaption("PRIOR"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/previous.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"PRIOR"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || dataSet.atFirst()) {
				return;
			}
			dataSet.prior();
		}
	};

	protected PriorAction priorAction = new PriorAction();

	/** Next action. */
	protected class NextAction extends AbstractAction {
		public NextAction() {
			super(DataModel.getDefault().getCaption("NEXT"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/next.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"NEXT"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || dataSet.atLast()) {
				return;
			}
			dataSet.next();
		}
	};

	protected NextAction nextAction = new NextAction();

	/** Last action. */
	protected class LastAction extends AbstractAction {
		public LastAction() {
			super(DataModel.getDefault().getCaption("LAST"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/last.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"LAST"));
		}

		public void actionPerformed(ActionEvent e) {
			if (!dataSet.isOpen() || dataSet.atLast()) {
				return;
			}
			dataSet.last();
		}
	};

	protected LastAction lastAction = new LastAction();

	/** List inlet action. */
	protected class ListInletAction extends AbstractAction {
		public ListInletAction() {
			super(DataModel.getDefault().getCaption("REFRESH"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/refreshDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"REFRESH"));
		}

		public void actionPerformed(ActionEvent e) {
			if (listInlet()) {
				showStatus();
			}
		}
	};

	protected ListInletAction listInletAction = new ListInletAction();

	/** List inlet action. */
	protected class FlushInletAction extends AbstractAction {
		public FlushInletAction() {
			super(DataModel.getDefault().getCaption("SAVE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								BaseSingleDataSetFrame.class
								.getClass()
								.getResource(
								"/com/evangelsoft/workbench/resources/buttons/saveDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault().getCaption(
			"SAVE"));
		}

		public void actionPerformed(ActionEvent e) {
			if (flushInlet()) {
				showStatus();
			}
		}
	}
	QueryAction queryAction=new QueryAction();
	protected class QueryAction extends AbstractAction
	{
		public QueryAction()
		{
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/easyui/resources/buttons/query.png")));
			putValue("ShortDescription", "查询");
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{

			HashMap<String,Object> map=null;
			try{
				map=queryDialog.getFilterMap();
				if(map==null){
					return ;
				}
				map.put("bound", boundTree);
				//						map.put("filter", filterTree);
				//						map.put("sort", sortList);
				map.put("maxRows", (Integer)maxRowsSpinner.getValue());
			}catch(Exception e){
				e.printStackTrace();
			}
			if (!worker.isIdle())
				return;

			dataSet.empty();
			dataSet.reset();

			filterTree.clear();
			sortList.clear();
			try {
				if (filterSimpleMenuItem.isSelected()) {
					buildFilter();
				} else {
					filterTree = conditionTreePanel.getTree();
					sortList = sortListPanel.getList();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BaseSingleDataSetFrame.this,
						ExceptionFormat.format(ex), getTitle(),
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			//			HashMap<String, Object> filter = new HashMap<String, Object>();
			//			filter.put("bound", boundTree);
			//			filter.put("filter", filterTree);
			//			filter.put("sort", sortList);

			beforeRefresh();

			class Worker implements WireWorker.Worker {
				private Object filter;

				public Worker(Object filter) {
					super();
					this.filter = filter;
				}

				public Object work() throws Throwable {
					Object rs = listEntity(filter);
					return rs;
				}
			}
			;
			class Hook implements WireWorker.Hook {
				public void hook(Object value) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					DataSetHelper.loadFromRecordSet(dataSet, (RecordSet) value);
					modified = false;

					buildUniqueIndex();

					if (filterAutoHiddenMenuItem.isSelected()) {
						filterMenuItem.setSelected(false);
						filterPanel.setVisible(false);
						listPane.resetToPreferredSizes();
					}

					if (!dataSet.isEmpty()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								rowId = -1;
								dataSet.first();
							}
						});
					} else {
						rowId = -1;
						clearInlet();
						showStatus();
					}

					afterRefresh();
				}
			}

			class Corrector implements WireWorker.Corrector {
				public void correct(Throwable e) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					showStatus();
				}
			}

			class Resumer implements WireWorker.Resumer {
				public void resume() {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);

					showStatus();
				}
			}

			if (!worker.isIdle()) {
				return;
			}
			worker.setWorker(new Worker(map));
			worker.setHook(new Hook());
			worker.setCorrector(new Corrector());
			worker.setResumer(new Resumer());
			worker.start();
		}

	}

	protected FlushInletAction flushInletAction = new FlushInletAction();

	/** Main data set. */
	protected StorageDataSet dataSet;
	/** TdDataSet bound to dataSet. */
	protected TdDataSet tdDataSet = null;
	/** Header panel. */
	protected JPanel headerPanel;
	/**
	 * Panel used to display title message of list area. Developer may place
	 * components here in inherited frame.
	 */
	protected JPanel titlePanel;
	/** Main split pane. */
	protected JSplitPane mainPane;
	/** Footer panel. */
	protected JPanel footerPanel;
	/** Progress panel. */
	protected JPanel progressPanel;
	/**
	 * Panel of footer message. Developers may place extra components here in
	 * inherited frames.
	 */
	protected JPanel footerExtendedPanel;
	/** List row count label. */
	protected JLabel listRowCountLabel;
	/** List row count prompt label. */
	protected JLabel listRowCountPromptLabel;
	/** Panel of footer fixed content. */
	protected JPanel footerFixedPanel;
	/** Panel of list footer. */
	protected ListFooterPanel listFooterPanel;
	/** List table. */
	protected JdbTable listTable;
	/** Scroll pane of list table. */
	protected TableScrollPane listTablePane;
	/** Panel of filter conditions. */
	protected JPanel filterConditionsPanel;
	/** Panel of filter mode. */
	protected JPanel filterModePanel;
	/** Panel of simple filter content. */
	protected JPanel filterSimplePanel;
	/** Panel of sort list. */
	protected SortListPanel sortListPanel;
	/** Panel of condition tree. */
	protected ConditionTreePanel conditionTreePanel;
	/** Split pane of advanced filter condition. */
	protected JSplitPane filterAdvancedPane;
	/** Panel of inlet area. */
	protected JPanel inletPanel;
	/** Panel of list area. */
	protected JPanel listPanel;
	/** Panel of filter. */
	protected JPanel filterPanel;
	/** List split pane. */
	protected JSplitPane listPane;
	/** Select button. */
	protected JButton selectButton;
	/** Close button. */
	protected JButton closeButton;
	/** Filter menu button. */
	protected JButton filterButton;
	/** Filter menu. */
	protected JPopupMenu filterMenu;
	/** Filter auto hidden menu item. */
	protected JCheckBoxMenuItem filterAutoHiddenMenuItem;
	/** Filter advanced mode menu item. */
	protected JRadioButtonMenuItem filterAdvancedMenuItem;
	/** Filter simple mode menu item. */
	protected JRadioButtonMenuItem filterSimpleMenuItem;
	/** Filter menu item. */
	protected JCheckBoxMenuItem filterMenuItem;
	/** Refresh button. */
	protected JButton refreshButton;

	/** Refresh button. */
	protected JButton queryButton;

	/** New button. */
	protected JButton newButton;
	/** Save button. */
	protected JButton saveButton;
	/** Cancel button. */
	protected JButton cancelButton;
	/** Delete button. */
	protected JButton deleteButton;
	/** First button. */
	protected JButton firstButton;
	/** Prior button. */
	protected JButton priorButton;
	/** Next button. */
	protected JButton nextButton;
	/** Last button. */
	protected JButton lastButton;
	/** Main tool bar. */
	protected JToolBar mainToolBar;

	JPanel mainToolBarControlPanel;
	/** Filter mode button group. */
	protected ButtonGroup filterModeGroup = new ButtonGroup();

	// @wb: end of declaration

	/** Default wire worker. */
	protected VisibleWireWorker worker = new VisibleWireWorker();

	/**
	 * List of components bound with <b>dataSet<b>. It's a key-value map, key
	 * is data-aware component, value is whether it's originally editable.
	 */
	protected HashMap<DataSetAware, Boolean> dataSetComponents = new HashMap<DataSetAware, Boolean>();

	/**
	 * Entity class which the frame representing. If entityClass is absent,
	 * developer should code to implement functions which are originally
	 * implemented based on entityClass.
	 */
	protected Class<?> entityClass;

	/** Key columns of data set. */
	protected String[] keyColumns;

	/**
	 * Unique columns of data set. If it is null, keyColumns will be taken as
	 * its default.
	 */
	protected String[] uniqueColumns;

	/** Whether to check duplicate when saving. */
	protected boolean duplicateChecking = true;

	/** Privilege view. */
	protected boolean canView = true;

	/** Privilege insert. */
	protected boolean canInsert = true;

	/** Privilege modify. */
	protected boolean canModify = true;

	/** Privilege delete. */
	protected boolean canDelete = true;

	/** Indicator of loading of data set. */
	protected boolean loading = false;

	/** Bound condition tree. */
	protected ConditionTree boundTree = new ConditionTree();

	/** Filter condition tree. */
	protected ConditionTree filterTree = new ConditionTree();

	/** Sort list of data set. */
	protected SortList sortList = new SortList();

	/** Enable advanced filter mode or not. */
	protected boolean advancedFilterModeEnabled = true;

	/** Inlet panel visible or not when frame opened. */
	protected boolean inletVisibleWhenOpened = false;

	/** Condition items available in advanced query mode. */
	protected ArrayList<ConditionItem> conditionItems = new ArrayList<ConditionItem>();

	/** Sort items available in advanced query mode. */
	protected ArrayList<SortItem> sortItems = new ArrayList<SortItem>();

	/** Resource strings. */
	//	 private static ResourceBundle res = ResourceBundle
	//	 .getBundle(SingleDataSetFrame.class.getPackage().getName() + ".Res");
	//	private static final ResourceBundle frameRes = ResourceBundle
	//			.getBundle("com.evangelsoft.workbench.framebase.SingleDataSetFrame.Res");

	/**
	 *参数map，用于模板，审批流和功能插件等等
	 */
	protected HashMap<String,Object> pranMap=new HashMap<String, Object>();

	// 查询模板
	protected QueryDialog queryDialog;


	/** Variable indicates first opened or not. */
	private boolean firstOpened = true;

	/** Variable indicates whether current frame in select mode or not. */
	private boolean selecting = false;

	/** Record set of records selected. */
	protected RecordSet selections = null;

	/** Variable indicates whether to refresh data after frame opened. */
	protected boolean refreshWhenOpened = false;

	/** Variable indicates data modified or not. */
	private boolean modified = false;

	/** Variable indicates inlet modified or not. */
	private boolean inletModified = false;

	/**
	 * Current row id of data set. When data set is sorted, rows positition will
	 * be changed and navigated event will be triggered when posting or changing
	 * sort order. This variable is used to distinguish this kind of navigated
	 * event from common situations, such as first, last, and so on.
	 */
	private long rowId = -1;

	/**
	 * 默认值管理map
	 */
	HashMap<String ,HashMap<String,DefaultValue>> initValueMap=new HashMap<String, HashMap<String,DefaultValue>>();
	private String tableId;

	/**
	 * Create the frame.
	 */
	public BaseSingleDataSetFrame(String  templateId) {
		this();
		if(templateId.split(",").length==2){
			this.templateId=templateId.split(",")[0];
			this.queryId=templateId.split(",")[1];
		}else{
			this.templateId=templateId;
			this.queryId=templateId;
		}

	}

	public BaseSingleDataSetFrame() {
		super();
		setBounds(0, 0, 600, 400);
		initialize();

		if (java.beans.Beans.isDesignTime()) {
			return;
		}
		//设置快捷键
		InputMap localInputMap = getInputMap(1);
		localInputMap.put(KeyStroke.getKeyStroke(120, 0), "SELECT");
		localInputMap.put(KeyStroke.getKeyStroke(82, 2), "REFRESH");
		localInputMap.put(KeyStroke.getKeyStroke(78, 2), "NEW");
		localInputMap.put(KeyStroke.getKeyStroke(83, 2), "SAVE");
		localInputMap.put(KeyStroke.getKeyStroke(90, 2), "CANCEL");
		localInputMap.put(KeyStroke.getKeyStroke(68, 2), "DELETE");
		localInputMap.put(KeyStroke.getKeyStroke(36, 2), "FIRST");
		localInputMap.put(KeyStroke.getKeyStroke(38, 2), "PRIOR");
		localInputMap.put(KeyStroke.getKeyStroke(40, 2), "NEXT");
		localInputMap.put(KeyStroke.getKeyStroke(35, 2), "LAST");
		localInputMap.put(KeyStroke.getKeyStroke('Q', 2), "QUERY");
		ActionMap localActionMap = getActionMap();
		localActionMap.put("SELECT", this.selectAction);
		localActionMap.put("REFRESH", this.refreshAction);
		localActionMap.put("NEW", this.newAction);
		localActionMap.put("SAVE", this.saveAction);
		localActionMap.put("CANCEL", this.cancelAction);
		localActionMap.put("DELETE", this.deleteAction);
		localActionMap.put("FIRST", this.firstAction);
		localActionMap.put("PRIOR", this.priorAction);
		localActionMap.put("NEXT", this.nextAction);
		localActionMap.put("LAST", this.lastAction);
		localActionMap.put("QUERY", this.queryAction);



		dataSet.addEditListener(new EditAdapter() {
			public void modifying(DataSet dataSet) {
				if (loading) {
					return;
				}
				setModified(true);
			}

			public void adding(DataSet dataSet, ReadWriteRow newRow)
			throws Exception {
				if (loading) {
					return;
				}
				validateData(newRow, null);
			}

			public void updating(DataSet dataSet, ReadWriteRow newRow,
					ReadRow oldRow) throws Exception {
				if (loading) {
					return;
				}
				validateData(newRow, oldRow);
			}
		});
		dataSet.addDataChangeListener(new DataChangeAdapter() {
			public void dataChanged(DataChangeEvent event) {
				setModified(true);
			}
		});
		dataSet.addNavigationListener(new NavigationListener() {
			public void navigated(NavigationEvent event) {
				if (loading) {
					return;
				}

				if ((dataSet.getStatus() & RowStatus.INSERTED) != 0) {
					// When a new row just inserted, getInternalRow returns
					// the previous row id, so it can't be adopted. This may be
					// a bug. If data set is sorted, posting may trigger
					// navigated event. Event under such situation should be
					// ignored.
					if (dataSet.isEditingNewRow()) {
						rowId = Long.MAX_VALUE;
					} else {
						rowId = dataSet.getInternalRow();
						return;
					}
				} else {
					// Navigated event trigger by sorting should be ignored.
					long currentRowId = dataSet.getInternalRow();
					if (rowId == currentRowId) {
						return;
					}
					rowId = currentRowId;
				}

				if (dataSet.isEmpty()
						|| ((dataSet.getStatus() & RowStatus.INSERTED) != 0)) {
					clearInlet();
					showStatus();
				} else {
					boolean found = false;
					try {
						BaseSingleDataSetFrame.this.getClass().getDeclaredMethod(
								"listInlet", new Class[0]);
						found = true;
					} catch (Exception e1) {
					}
					if (found) {
						listInletAction.actionPerformed(new ActionEvent(
								listInletAction, 0, (String) listInletAction
								.getValue(Action.ACTION_COMMAND_KEY)));
					} else {
						showStatus();
					}
				}
			}
		});

		listTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (!selecting) {
							return;
						}
						showStatus();
					}
				});

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameOpened(InternalFrameEvent e) {
				if (!firstOpened) {
					return;
				}

				DataSetExceptionAdapter.getDefaultAdapter().registerDataSets(
						new DataSet[] { dataSet }, BaseSingleDataSetFrame.this);

				worker.attachDesktop(getTitle(),
						ProgressPanel.OPTION_CANCELLABLE
						| ProgressPanel.OPTION_SHOW_MESSAGE,
						progressPanel,
						new Component[] { mainToolBar, listPane });

				HashMap<DataSet, HashMap<DataSetAware, Boolean>> list = new HashMap<DataSet, HashMap<DataSetAware, Boolean>>();
				list.put(dataSet, dataSetComponents);
				DataSetHelper.listDataAwareComponents(getContentPane(), list);

				worker.setHook(new WireWorker.Hook() {
					public void hook(Object value) {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);

						firstOpened = false;

						boolean success = true;
						try {
							prepared(value);
						} catch (Exception e) {
							success = false;
							JOptionPane.showMessageDialog(
									BaseSingleDataSetFrame.this, ExceptionFormat
									.format(e), getTitle(),
									JOptionPane.ERROR_MESSAGE);
						}

						if (!canView || !success) {
							BaseSingleDataSetFrame.this
							.fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
							return;
						}

						buildUniqueIndex();

						if (advancedFilterModeEnabled) {
							ConditionItemsHelper.load(conditionItems, dataSet);
							SortItemsHelper.load(sortItems, dataSet);
							prepareConditions();
							conditionTreePanel.setItems(conditionItems);
							sortListPanel.setItems(sortItems);
						}

						if (!java.beans.Beans.isDesignTime()) {
							if (filterSimplePanel.getComponentCount() == 0) {
								filterSimpleMenuItem.setEnabled(false);
							}
							if (!advancedFilterModeEnabled) {
								filterAdvancedMenuItem.setEnabled(false);
							} else {
								if (conditionItems.size() == 0) {
									conditionTreePanel.setVisible(false);
									filterAdvancedPane.setDividerSize(0);
									filterAdvancedPane
									.setBorder(new EmptyBorder(0, 0, 0,
											0));
								}
								if (sortItems.size() == 0) {
									sortListPanel.setVisible(false);
									filterAdvancedPane.setDividerSize(0);
									filterAdvancedPane
									.setBorder(new EmptyBorder(0, 0, 0,
											0));
								}
							}
							if (!filterConditionsPanel.isVisible()) {
								if (filterSimpleMenuItem.isEnabled()) {
									filterSimpleMenuItem.setSelected(true);
								} else {
									filterAdvancedMenuItem.setSelected(true);
								}
							}

							if (filterSimpleMenuItem.isEnabled()) {
								filterSimpleMenuItem.doClick();
							} else if (filterAdvancedMenuItem.isEnabled()) {
								filterAdvancedMenuItem.doClick();
							}
							if (!filterModePanel.isVisible()
									&& !filterSimpleMenuItem.isSelected()) {
								filterMenuItem.doClick();
							}
						}

						showStatus();

						if (refreshWhenOpened) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									refreshAction
									.actionPerformed(new ActionEvent(
											refreshAction,
											0,
											(String) refreshAction
											.getValue(Action.ACTION_COMMAND_KEY)));
								}
							});
						}
					}
				});
				worker.setCorrector(new WireWorker.Corrector() {
					public void correct(Throwable ex) {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						BaseSingleDataSetFrame.this
						.fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
					}
				});
				worker.setResumer(new WireWorker.Resumer() {
					public void resume() {
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						BaseSingleDataSetFrame.this
						.fireInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
					}
				});
				worker.setWorker(new WireWorker.Worker() {
					public Object work() throws Throwable {
						Object value = prepareData();
						checkPrivileges();

						return value;
					}
				});

				worker.start();
			}

			public void internalFrameClosing(InternalFrameEvent e) {
				if (isModified() || isInletModified()) {
					switch ((JOptionPane.showConfirmDialog(
							BaseSingleDataSetFrame.this, DataModel.getDefault()
							.getSentence(
							"MSG_EXIT_WITHOUT_SAVING_PROMPT"),
							getTitle(), JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE))) {
							case JOptionPane.YES_OPTION:
								worker.setCleaner(new WireWorker.Cleaner() {
									public void clean() {
										worker.setCleaner(null);
										if (!isModified() && !isInletModified()) {
											dispose();
										}
									}
								});
								if (isModified()) {
									saveAction
									.actionPerformed(new ActionEvent(
											saveAction,
											0,
											(String) saveAction
											.getValue(Action.ACTION_COMMAND_KEY)));
								} else {
									flushInletAction
									.actionPerformed(new ActionEvent(
											flushInletAction,
											0,
											(String) flushInletAction
											.getValue(Action.ACTION_COMMAND_KEY)));
								}
								break;
							case JOptionPane.NO_OPTION:
								worker.interrupt();
								dispose();
								break;
							case JOptionPane.CANCEL_OPTION:
								break;
							default:
								worker.interrupt();
							dispose();
							break;
					}
				} else {
					worker.interrupt();
					dispose();
				}
			}

			public void internalFrameClosed(InternalFrameEvent e) {
				DataSetExceptionAdapter.getDefaultAdapter().deregisterDataSets(
						new DataSet[] { dataSet });
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JInternalFrame#pack()
	 */
	public void pack() {
		if (java.beans.Beans.isDesignTime()) {
			return;
		}

		if (firstOpened) {
			selectButton.setVisible(selecting);
			// Decides the separator next to selectButton visible or not.
			mainToolBar.getComponent(
					mainToolBar.getComponentIndex(selectButton) + 1)
					.setVisible(selecting);
			if (this.mainToolBar.getComponentIndex(this.mainToolBarControlPanel) != this.mainToolBar.getComponentCount() - 1)
			{
				this.mainToolBar.remove(this.mainToolBarControlPanel);
				this.mainToolBar.add(this.mainToolBarControlPanel);
			}

			if (!selecting) {
				listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}

			if (filterModePanel.getComponentCount() == 0) {
				filterModePanel.setVisible(false);
			}

			if (filterSimplePanel.getComponentCount() == 0
					&& !advancedFilterModeEnabled) {
				filterConditionsPanel.setVisible(false);
			}

			if (filterModePanel.isVisible()
					&& !filterConditionsPanel.isVisible()) {
				if ((filterPanel.getLayout() instanceof BorderLayout)
						&& (((BorderLayout) filterPanel.getLayout())
								.getConstraints(filterConditionsPanel)
								.equals(BorderLayout.CENTER))) {
					filterPanel.remove(filterModePanel);
					filterPanel.remove(filterConditionsPanel);
					filterPanel.add(filterModePanel, BorderLayout.CENTER);
					filterPanel.add(filterConditionsPanel, BorderLayout.WEST);
				}
			} else if (!filterModePanel.isVisible()
					&& !filterConditionsPanel.isVisible()) {
				filterPanel.setVisible(false);
				filterButton.setVisible(false);
				listPane.setDividerSize(0);
				listPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			}

			if (inletPanel.getComponentCount() == 0 || !inletPanel.isVisible()
					|| (!inletVisibleWhenOpened && firstOpened)) {
				if (inletPanel.isVisible()) {
					inletPanel.setVisible(false);
				}
				mainPane.setRightComponent(null);
				if (inletPanel.getComponentCount() == 0) {
					// mainPane.setBorder(new EmptyBorder(0, 0, 0, 0));
					mainPane.setDividerSize(0);
				}
			}
		}

		mainPane.setDividerLocation(-1);

		super.pack();
	}

	/**
	 * Initialize user interface.
	 */
	private void initialize() {
		dataSet = new StorageDataSet(); // @wb:location=66,434

		setTitle(this.getClass().getSimpleName()); //$NON-NLS-1$
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		// setPreferredSize(new Dimension(600, 400));
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);

		mainPane = new JSplitPane();
		mainPane.setResizeWeight(1);
		mainPane.setOneTouchExpandable(true);
		getContentPane().add(mainPane);

		listPane = new JSplitPane();
		mainPane.setLeftComponent(listPane);
		listPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		listPane.setResizeWeight(0);
		listPane.setOneTouchExpandable(true);

		filterPanel = new JPanel();
		filterPanel.setLayout(new BorderLayout());
		listPane.setTopComponent(filterPanel);

		filterModePanel = new JPanel();
		filterModePanel.setBorder(new TitledBorder(null,
				DataModel.getDefault().getCaption("FILTER_MODE"), //$NON-NLS-1$
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		filterPanel.add(filterModePanel, BorderLayout.WEST);

		filterConditionsPanel = new JPanel();
		filterConditionsPanel.setLayout(new GridBagLayout());
		filterConditionsPanel.setBorder(new TitledBorder(null,
				DataModel.getDefault().getCaption("FILTER_CONDITIONS"), //$NON-NLS-1$
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		filterPanel.add(filterConditionsPanel);

		filterSimplePanel = new JPanel();
		filterConditionsPanel.add(filterSimplePanel, new GridBagConstraints(0,
				0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		filterAdvancedPane = new JSplitPane();
		filterConditionsPanel.add(filterAdvancedPane, new GridBagConstraints(0,
				1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		filterAdvancedPane.setVisible(false);
		filterAdvancedPane.setOneTouchExpandable(true);
		filterAdvancedPane.setResizeWeight(0.8);

		conditionTreePanel = new ConditionTreePanel();
		filterAdvancedPane.setLeftComponent(conditionTreePanel);

		sortListPanel = new SortListPanel();
		filterAdvancedPane.setRightComponent(sortListPanel);

		listPanel = new JPanel();
		listPane.setBottomComponent(listPanel);
		listPanel.setBorder(new TitledBorder(null,
				DataModel.getDefault().getCaption("LIST"), //$NON-NLS-1$
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		listPanel.setLayout(new BorderLayout());

		listTablePane = new TableScrollPane();
		listPanel.add(listTablePane, BorderLayout.CENTER);

		listTable = new JdbTable();
		listTable.setDataSet(dataSet);
		listTablePane.setViewportView(listTable);

		listFooterPanel = new ListFooterPanel(this.dataSet);
//		listFooterPanel.setLayout(new BorderLayout());
		listPanel.add(listFooterPanel, BorderLayout.SOUTH);

		inletPanel = new JPanel();
		mainPane.setRightComponent(inletPanel);

		footerPanel = new JPanel();
		footerPanel.setLayout(new BorderLayout());
		getContentPane().add(footerPanel, BorderLayout.SOUTH);

		progressPanel = new JPanel();
		progressPanel.setLayout(new BorderLayout());
		footerPanel.add(progressPanel, BorderLayout.WEST);

		footerExtendedPanel = new JPanel();
		footerPanel.add(footerExtendedPanel);

		footerFixedPanel = new JPanel();
		final GridBagLayout footerFixedPanelLayout = new GridBagLayout();
		footerFixedPanelLayout.rowHeights = new int[] { 5, 0, 5 };
		footerFixedPanelLayout.columnWidths = new int[] { 5, 0, 0, 5 };
		footerFixedPanel.setLayout(footerFixedPanelLayout);
		footerPanel.add(footerFixedPanel, BorderLayout.EAST);

		listRowCountPromptLabel = new JLabel();
		listRowCountPromptLabel.setText(DataModel.getDefault().getLabel(
		"ROW_COUNT"));
		footerFixedPanel.add(listRowCountPromptLabel, new GridBagConstraints(1,
				1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		listRowCountLabel = new JdbLabel();
		listRowCountLabel.setText("0");

		footerFixedPanel.add(listRowCountLabel, new GridBagConstraints(2, 1, 1,
				1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		getContentPane().add(headerPanel, BorderLayout.NORTH);

		mainToolBar = new JToolBar();
		headerPanel.add(mainToolBar, BorderLayout.NORTH);

		selectButton = new JButton();
		selectButton.setAction(selectAction);
		selectButton.setText("");
		mainToolBar.add(selectButton);

		mainToolBar.addSeparator();

		closeButton = new JButton();
		closeButton.setAction(closeAction);
		closeButton.setText("");
		mainToolBar.add(closeButton);

		mainToolBar.addSeparator();

		filterButton = new JButton();
		filterButton.setSelected(true);
		filterButton.setAction(filterAction);
		filterButton.setText("");
		mainToolBar.add(filterButton);

		filterMenu = new JPopupMenu();
		addPopup(filterButton, filterMenu);

		filterMenuItem = new JCheckBoxMenuItem();
		filterMenuItem.addActionListener(new FilterMenuItemActionListener());
		filterMenuItem.setSelected(true);
		filterMenuItem.setText(DataModel.getDefault().getCaption("FILTER_OPTION")); //$NON-NLS-1$
		filterMenu.add(filterMenuItem);

		filterAutoHiddenMenuItem = new JCheckBoxMenuItem();
		filterAutoHiddenMenuItem.setText(DataModel.getDefault().getCaption("AUTO_HIDDEN")); //$NON-NLS-1$
		filterMenu.add(filterAutoHiddenMenuItem);

		filterMenu.addSeparator();

		filterSimpleMenuItem = new JRadioButtonMenuItem();
		filterSimpleMenuItem
		.addActionListener(new FilterSimpleMenuItemActionListener());
		filterModeGroup.add(filterSimpleMenuItem);
		filterSimpleMenuItem.setSelected(true);
		filterSimpleMenuItem.setText(DataModel.getDefault().getCaption("SIMPLE_MODE")); //$NON-NLS-1$
		filterMenu.add(filterSimpleMenuItem);

		filterAdvancedMenuItem = new JRadioButtonMenuItem();
		filterAdvancedMenuItem
		.addActionListener(new FilterAdvancedMenuItemActionListener());
		filterModeGroup.add(filterAdvancedMenuItem);
		filterAdvancedMenuItem.setText(DataModel.getDefault().getCaption("ADVANCED_MODE")); //$NON-NLS-1$
		filterMenu.add(filterAdvancedMenuItem);

		refreshButton = new JButton();
		refreshButton.setAction(refreshAction);
		refreshButton.setText("");
		mainToolBar.add(refreshButton);

		mainToolBar.addSeparator();

		queryButton=new JButton();
		queryButton.setAction( queryAction);
		queryButton.setText("");
		mainToolBar.add(queryButton);
		mainToolBar.addSeparator();

		newButton = new JButton();
		newButton.setAction(newAction);
		newButton.setText("");
		mainToolBar.add(newButton);

		saveButton = new JButton();
		saveButton.setAction(saveAction);
		saveButton.setText("");
		mainToolBar.add(saveButton);

		cancelButton = new JButton();
		cancelButton.setAction(cancelAction);
		cancelButton.setText("");
		mainToolBar.add(cancelButton);

		mainToolBar.addSeparator();

		firstButton = new JButton();
		firstButton.setAction(firstAction);
		firstButton.setText("");
		mainToolBar.add(firstButton);

		priorButton = new JButton();
		priorButton.setAction(priorAction);
		priorButton.setText("");
		mainToolBar.add(priorButton);

		nextButton = new JButton();
		nextButton.setAction(nextAction);
		nextButton.setText("");
		mainToolBar.add(nextButton);

		lastButton = new JButton();
		lastButton.setAction(lastAction);
		lastButton.setText("");
		mainToolBar.add(lastButton);

		mainToolBar.addSeparator();

		deleteButton = new JButton();
		deleteButton.setAction(deleteAction);
		deleteButton.setText("");
		mainToolBar.add(deleteButton);

		//		titlePanel = new JPanel();
		//		titlePanel.setLayout(new BorderLayout());
		//		headerPanel.add(titlePanel);


		this.mainToolBarControlPanel = new JPanel();
		this.mainToolBarControlPanel.setVisible(true);
		GridBagLayout localGridBagLayout2 = new GridBagLayout();
		localGridBagLayout2.columnWidths = new int[] { 5,5,5,5 };
		this.mainToolBarControlPanel.setLayout(localGridBagLayout2);

		this.mainToolBarControlPaddingPanel = new JPanel();
		this.mainToolBarControlPanel.add(this.mainToolBarControlPaddingPanel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.maxRowsLabel = new JLabel();
		this.maxRowsLabel.setText(DataModel.getDefault().getLabel("MAX_ROWS"));
		this.mainToolBarControlPanel.add(this.maxRowsLabel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.maxRowsSpinner = new JSpinner();
		this.maxRowsSpinner.setValue(DEFAULT_MAX_ROWS);
		this.mainToolBarControlPanel.add(this.maxRowsSpinner, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 5), 0, 0));
		this.mainToolBar.add(this.mainToolBarControlPanel);
		this.titlePanel = new JPanel();
		this.titlePanel.setLayout(new BorderLayout());
		this.headerPanel.add(this.titlePanel);

	}

	/**
	 * The method show displays the frame, and refresh data automatically under
	 * the condition supplied. If parent component is supplied, the frame is
	 * positioned referring to the parent, and displayed under modal mode.
	 *
	 * @param parent
	 *            parent component.
	 * @param boundTree
	 *            bound tree which supplies the initial filter conditions.
	 */
	public void show(Component parent, ConditionTree boundTree) {
		if (boundTree != null) {
			this.boundTree.unmarshal(boundTree.marshal());
		}
		// boolean refreshWhenOpened = this.refreshWhenOpened;
		this.refreshWhenOpened = true;
		if (parent == null) {
			setVisible(true);
		} else {
			JInternalDialog.showAsDialog(parent, this);
		}
		// this.refreshWhenOpened = refreshWhenOpened;
	}

	/**
	 * The method show displays the frame for selecting. The frame is positioned
	 * referring to the parent component.
	 *
	 * @param parent
	 *            parent component.
	 * @param boundTree
	 *            bound tree which supplies the initial filter conditions.
	 * @param multiSelect
	 *            whether multi-select is enabled.
	 * @param refreshWhenOpened
	 *            refresh when opened or not.
	 * @return selections in RecordSet object.
	 */
	public RecordSet select(Component parent, ConditionTree boundTree,
			boolean multiSelect, boolean refreshWhenOpened) {
		if (boundTree != null) {
			this.boundTree.unmarshal(boundTree.marshal());
		}
		selecting = true;
		if (multiSelect) {
			listTable
			.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		boolean refreshWhenOpenedSaved = this.refreshWhenOpened;
		this.refreshWhenOpened = refreshWhenOpened;
		selections = null;
		pack();
		UIInternalDialog.showAsDialog(parent, this);
		this.refreshWhenOpened = refreshWhenOpenedSaved;
		return selections;
	}

	/**
	 * The method show displays the frame for selecting. The frame is positioned
	 * referring to the parent component, and data is refreshed automatically
	 * according to initial conditions when opened.
	 *
	 * @param parent
	 *            parent component.
	 * @param boundTree
	 *            bound tree which supplies the initial filter conditions.
	 * @param multiSelect
	 *            whether multi-select is enabled.
	 * @return selections in RecordSet object.
	 */
	public RecordSet select(Component parent, ConditionTree boundTree,
			boolean multiSelect) {
		return select(parent, boundTree, multiSelect, true);
	}

	/**
	 * Method showNavigator refreshes states of navigating components depending
	 * form state.
	 */
	protected void showNavigator() {
		boolean firstEnabled = false;
		boolean priorEnabled = false;
		boolean nextEnabled = false;
		boolean lastEnabled = false;

		DataSet uiDataSet = dataSet;
		if (tdDataSet != null) {
			uiDataSet = tdDataSet;
		}
		if (uiDataSet.isOpen() && !inletModified) {
			firstEnabled = !uiDataSet.atFirst();
			priorEnabled = !uiDataSet.atFirst();
			nextEnabled = !uiDataSet.atLast();
			lastEnabled = !uiDataSet.atLast();
		}

		// Ugly codes due to bug in swing.
		firstAction.setEnabled(!firstEnabled);
		priorAction.setEnabled(!priorEnabled);
		nextAction.setEnabled(!nextEnabled);
		lastAction.setEnabled(!lastEnabled);

		firstAction.setEnabled(firstEnabled);
		priorAction.setEnabled(priorEnabled);
		nextAction.setEnabled(nextEnabled);
		lastAction.setEnabled(lastEnabled);
	}

	/**
	 * Method canModifyRow decides whether current row can be modified. It's
	 * always been overridden in descendant classes.
	 *
	 * @return can modify current row or not.
	 */
	protected boolean canModifyRow() {
		return true;
	}

	/**
	 * Method canDeleteRow decides whether current row can be deleted. It's
	 * always been overridden in descendant classes.
	 *
	 * @return can delete current row or not.
	 */
	protected boolean canDeleteRow() {
		return true;
	}

	/**
	 * Method showRowStatus refreshes actions bound with row depending on
	 * current row value. It's always been overridden in descendant classes.
	 */
	protected void showRowStatus() {
	}

	/**
	 * Method showStatus refreshes status of components depending on frame
	 * state.
	 */
	protected void showStatus() {
		if (!SwingUtilities.isEventDispatchThread()) {
			return;
		}

		DataSet uiDs = dataSet;
		if (tdDataSet != null) {
			uiDs = tdDataSet;
		}

		boolean opened = uiDs.isOpen();
		boolean modified = isModified();
		boolean inletModified = isInletModified();

		boolean filterEnabled = !modified && !inletModified;
		boolean refreshEnabled = !modified && !inletModified;

		// Ugly codes due to bug in swing.
		filterAction.setEnabled(!filterEnabled);
		refreshAction.setEnabled(!refreshEnabled);

		filterAction.setEnabled(filterEnabled);
		refreshAction.setEnabled(refreshEnabled);

		if (opened) {
			uiDs.setEnableInsert(canInsert && !inletModified);
			uiDs
			.setEnableUpdate(((!uiDs.isEmpty() && canModify && canModifyRow()) || ((uiDs
					.getStatus() & RowStatus.INSERTED) != 0))
					&& !inletModified);
			uiDs
			.setEnableDelete(((!uiDs.isEmpty() && canDelete && canDeleteRow()) || ((uiDs
					.getStatus() & RowStatus.INSERTED) != 0))
					&& !inletModified);

			boolean selectEnabled = selecting
			&& (listTable.getSelectedRowCount() > 0) && !modified
			&& !inletModified;
			boolean newEnabled = canInsert && !inletModified;
			boolean saveEnabled = modified;
			boolean cancelEnabled = modified;
			boolean deleteEnabled = uiDs.isEnableDelete();

			// Ugly codes due to bug in swing.
			selectAction.setEnabled(!selectEnabled);
			newAction.setEnabled(!newEnabled);
			saveAction.setEnabled(!saveEnabled);
			cancelAction.setEnabled(!cancelEnabled);
			deleteAction.setEnabled(!deleteEnabled);

			selectAction.setEnabled(selectEnabled);
			newAction.setEnabled(newEnabled);
			saveAction.setEnabled(saveEnabled);
			cancelAction.setEnabled(cancelEnabled);
			deleteAction.setEnabled(deleteEnabled);

			// boolean enabled = (canModify || ((uiDs.getStatus() &
			// RowStatus.INSERTED) != 0))
			// && !inletModified;
			DataSetHelper.enableDataAwareComponents(dataSetComponents, uiDs
					.isEnableUpdate());
		} else {
			// Ugly codes due to bug in swing.
			selectAction.setEnabled(false);
			newAction.setEnabled(true);
			saveAction.setEnabled(true);
			cancelAction.setEnabled(true);

			selectAction.setEnabled(false);
			newAction.setEnabled(false);
			saveAction.setEnabled(false);
			cancelAction.setEnabled(false);

			DataSetHelper.enableDataAwareComponents(dataSetComponents, false);
		}

		showNavigator();
		showRowStatus();
		if (inletPanel.isVisible()) {
			showInletStatus();
		}

		listPane.setEnabled(!inletModified);
		listTable.setEnabled(!inletModified);

		if (opened) {
			int adjust = 0;
			if (dataSet != uiDs && uiDs instanceof TdDataSet) {
				if (((TdDataSet) uiDs).isInserting()) {
					adjust = 1;
				} else if (((TdDataSet) uiDs).isDeleting()) {
					adjust = -1;
				}
			}
			listRowCountLabel.setText(Integer.toString(uiDs.getRowCount()
					+ adjust));
		} else {
			listRowCountLabel.setText("0");
		}
	}

	/**
	 * Method buildUniqueIndex builds unique index according to keyColumns and
	 * uniqueColumns setting.
	 */
	protected void buildUniqueIndex() {
		if (!duplicateChecking) {
			return;
		}

		loading = true;
		try {
			String[] columns = null;
			if ((uniqueColumns != null) && (uniqueColumns.length > 0)) {
				columns = uniqueColumns;
			} else if ((keyColumns != null) && (keyColumns.length > 0)) {
				columns = keyColumns;
			}
			if (columns != null) {
				SortDescriptor savedIndex = dataSet.getSort();
				boolean[] descending = new boolean[columns.length];
				for (int i = 0; i < descending.length; i++) {
					descending[i] = false;
				}
				dataSet.setSort(new SortDescriptor("UIDX$", columns,
						descending, Locale.getDefault().toString(), Sort.UNIQUE
						| Sort.CASEINSENSITIVE));
				if (savedIndex != null) {
					dataSet.setSort(savedIndex);
				} else {
					dataSet.setSort(null);
				}
			}
		} finally {
			loading = false;
		}
	}

	/**
	 * Method prepareData does some preparing when the first time the frame is
	 * opened. It's always overridden by inherited frames.<br>
	 * Notice: This method runs in the background thread.
	 *
	 * @return data prepared.
	 * @throws Exception
	 *             if something wrong.
	 */
	protected Object prepareData() throws Exception {
		entityClass=BaseSingleDataSet.class;
		pranMap.put(BaseMasterDetail.TEMPLATE_ID, templateId);
		return null;
	}

	/**
	 * Method prepared does the following work after <b>prepareData</b>.
	 * Separating this method from <b>prepareData</b> is because the later runs
	 * in the background thread and isn't swing compatible.
	 *
	 * @param data
	 * @throws Exception
	 */
	protected void prepared(Object data) throws Exception {
		long start=System.currentTimeMillis();
		listPane.setTopComponent(null);
		listPane.setDividerSize(0);

		if(templateId!=null&&StringUtils.isNotEmpty(templateId.toString())){
			//初始化界面
			SysTemplate sysTemplate=(SysTemplate)(new RMIProxy(Consumer.getDefaultConsumer().getSession())).newStub(SysTemplate.class);
			VariantHolder<String> errMsg=new VariantHolder<String>();
			VariantHolder<Object> tempdata=new VariantHolder<Object>();
			tempdata.value=new RecordSet[]{new RecordSet(),new RecordSet(),new RecordSet(),new RecordSet()};
			if(!sysTemplate.get(this.templateId,tempdata , errMsg)){
				throw new Exception(errMsg.value);
			}
			long start1=System.currentTimeMillis();System.out.println("时间段1"+(start1-start));
			RecordSet[] datas=(RecordSet[])tempdata.value;
			Record tempRecord=datas[0].getRecord(0);

			this.setTitle(datas[0].getRecord(0).getField("TEMPLATE_NAME").getString());
			//设置主键
			String[] keys=tempRecord.getField("MAIN_KEY").getString().split(",");//此值不允许为空
			keyColumns =keys;

			// 初始化界面大小
			if(!datas[0].getRecord(0).getField("DEFAULT_WIDTH").isNull()&&!datas[0].getRecord(0).getField("DEFAULT_HEIGHT").isNull()){
				this.setPreferredSize(new Dimension(datas[0].getRecord(0).getField("DEFAULT_WIDTH").getNumber().intValue(),datas[0].getRecord(0).getField("DEFAULT_HEIGHT").getNumber().intValue()));
			}
			long start4=System.currentTimeMillis();System.out.println("时间段4:"+(start4-start1));

			//得到表格和汇总得tableID
			//		String tableId="";
			//		String footId="";
			//生成字段选项
			if(datas[3]!=null&&datas[3].recordCount()>0){
				tableId=datas[0].getRecord(0).getField("MAIN_TABLE_ID").getString();
				try{
					listFooterPanel.batchAdd(datas[3], datas[0].getRecord(0).getField("MAIN_TABLE_ID").getString());
				}catch(Exception e){
					e.printStackTrace();
				}

				long start3=System.currentTimeMillis();System.out.println("时间段3:"+(start3-start4));
				for(int i=0;i<datas[3].recordCount();i++){
					if(!datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").isNull()){
						//判断是否有默认值
						if(!datas[3].getRecord(i).getField("DEFAULT_VALUE").isNull()){
							//如果是变量
							if(initValueMap.containsKey(datas[3].getRecord(i).getField("TABLE_ID").getString())){
								initValueMap.get(datas[3].getRecord(i).getField("TABLE_ID").getString()).put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
										, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
							}else{
								HashMap<String,DefaultValue> map=new HashMap<String, DefaultValue>();
								map.put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
										, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
								initValueMap.put(datas[3].getRecord(i).getField("TABLE_ID").getString(),map);
							}
						}
					}
				}
//				for(int i=0;i<datas[3].recordCount();i++){
//				//如果页签ID和主表ID一样，添加到页脚显示
////				if(!datas[0].getRecord(0).getField("MAIN_TABLE_ID").getString().equals(datas[3].getRecord(i).getField("TABS_ID").getString())){
////				listFooterPanel.add(datas[3].getRecord(i), true);
////				}else{
////				listFooterPanel.add(datas[3].getRecord(i), false);
////				}


////				if(!datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").isNull()){
////				//判断是否有默认值
////				if(!datas[3].getRecord(i).getField("DEFAULT_VALUE").isNull()){
////				//如果是变量
////				if(initValueMap.containsKey(datas[3].getRecord(i).getField("TABLE_ID").getString())){
////				initValueMap.get(datas[3].getRecord(i).getField("TABLE_ID").getString()).put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
////				, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
////				}else{
////				HashMap<String,DefaultValue> map=new HashMap<String, DefaultValue>();
////				map.put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
////				, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
////				initValueMap.put(datas[3].getRecord(i).getField("TABLE_ID").getString(),map);
////				}
////				}
////				}

//				//				this.loadItem(dataSetMap.get(datas[3].getRecord(i).getField("TABS_ID").getString()),records[i]);
//				//				//如果值来源设置值，逐一判断
//				//				if(!datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").isNull()){
//				//					//判断是否有默认值
//				//					if(!datas[3].getRecord(i).getField("DEFAULT_VALUE").isNull()){
//				//						//如果是变量
//				//						if(initValueMap.containsKey(datas[3].getRecord(i).getField("TABLE_ID").getString())){
//				//							initValueMap.get(datas[3].getRecord(i).getField("TABLE_ID").getString()).put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
//				//									, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
//				//						}else{
//				//							HashMap<String,DefaultValue> map=new HashMap<String, DefaultValue>();
//				//							map.put(datas[3].getRecord(i).getField("COLUMN_ID").getString()
//				//									, new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE").getString(),dataSet));
//				//							initValueMap.put(datas[3].getRecord(i).getField("TABLE_ID").getString(),map);
//				//						}
//				//					}
//				//				}
//				}
			}

			long start2=System.currentTimeMillis();System.out.println("最后耗时"+(start2-start));
			//
			//		for(String key:dataSetMap.keySet()){
			//			dataSetKeyMap.put(dataSetMap.get(key).getStorageDataSet() ,key);
			//		}
			//
			//添加当前类
			dataSet.addEditListener(this);
			HashMap<DataSet, HashMap<DataSetAware, Boolean>> localObject1 = new HashMap<DataSet, HashMap<DataSetAware, Boolean>>();
			localObject1.put(this.dataSet, this.dataSetComponents);
			DataSetHelper.listDataAwareComponents(this.getContentPane(), localObject1);
			DataSetHelper.enableDataAwareComponents(this.dataSetComponents, false);
		}
		//
		//		//默认显示列表
		//		headPane.setViewType(UITabPanel.OTHER);
		//		headPane.updateUI();
		//
		if(queryId!=null&&StringUtils.isNotEmpty(queryId.toString())){
			queryDialog=new QueryDialog(queryId,dataSet);
			queryDialog.setLocationRelativeTo(this);
		}
		//查询初始化时间
		long start2x=System.currentTimeMillis();System.out.println("总耗时："+(start2x-start));
		pack();

	}

	/**
	 * Method prepareConditions prepares condition items and filter items.
	 */
	protected void prepareConditions() {
	}

	/**
	 * Method beforeRefresh is invoked before refreshing. It's always overridden
	 * by inherited frames.<br>
	 */
	protected void beforeRefresh() {
	}

	/**
	 * Method afterRefresh is invoked after refreshing. It's always overridden
	 * by inherited frames.<br>
	 */
	protected void afterRefresh() {
	}

	/**
	 * Method beforeSave is invoked before saving. It's always overridden by
	 * inherited frames.<br>
	 */
	protected boolean beforeSave()
	{
		return true;
	}

	/**
	 * Method afterSave is invoked after saving. It's always overridden by
	 * inherited frames.<br>
	 */
	protected void afterSave() {
	}

	/**
	 * Method checkPrivileges checks user privileges when the first time the
	 * frame is opened. Absence of privilege will disable certain actions in the
	 * frame. It's always overridden by inherited frames. <br>
	 * Notice: This method runs in the background thread.
	 *
	 * @throws Exception
	 *             if something wrong.
	 */
	protected void checkPrivileges() throws Exception {
	}

	/**
	 * Method buildFilter build filter condition tree based on conditions
	 * appointed by user. It's always overridden by inherited frames.
	 *
	 * @throws Exception
	 *             if something wrong.
	 */
	protected void buildFilter() throws Exception {
	}

	/**
	 * Method isEntityListable decides whether the field entityClass is a class
	 * implementing interface BaseSingleDataSet or not. If it's, actions based on
	 * interface BaseSingleDataSet will be enabled.
	 *
	 * @return is entity or not.
	 */
	protected boolean isEntityListable() {
		return (entityClass != null)
		&& BaseSingleDataSet.class.isAssignableFrom(entityClass);
	}

	/**
	 * Method isEntityFlushable decides whether the field entityClass is a class
	 * implementing interface BaseSingleDataSet or not. If it's, actions based on
	 * interface BaseSingleDataSet will be enabled.
	 *
	 * @return is entity or not.
	 */
	protected boolean isEntityFlushable() {
		return (entityClass != null)
		&& BaseSingleDataSet.class.isAssignableFrom(entityClass);
	}

	/**
	 * Method isEntityMonoEditable decides whether the field entityClass is a
	 * class implementing EntityMonoEditable. If it's, actions based on
	 * interface EntityMonoEditable will be enabled.
	 *
	 * @return is entity or not.
	 */
	protected boolean isEntityMonoEditable() {
		return (entityClass != null)
		&& EntityMonoEditable.class.isAssignableFrom(entityClass);
	}

	/**
	 * Method buildKey returns current key of current row based on key columns
	 * setting. It may be overridden in inherited frame to adapt to other
	 * situations.
	 *
	 * @return key value. If key is composed by more than one field, key value
	 *         should be an array.
	 */
	protected Object buildKey() {
		if (keyColumns == null || keyColumns.length == 0) {
			return null;
		}

		Object key[] = new Object[keyColumns.length];
		Variant v = new Variant();
		for (int i = 0; i < keyColumns.length; i++) {
			dataSet.getVariant(keyColumns[i], v);
			key[i] = v.getAsObject();
		}
		if (keyColumns.length > 1) {
			return key;
		} else {
			return key[0];
		}
	}

	/**
	 * Method isSelecting decides whether the frame is in selecting or not.
	 *
	 * @return selecting or not.
	 */
	protected boolean isSelecting() {
		return selecting;
	}

	/**
	 * Method isModified decides whether frame data is modified or not.
	 *
	 * @return modified or not.
	 */
	protected boolean isModified() {
		return modified;
	}

	/**
	 * Method setModified switches the modified status of the frame.
	 *
	 * @param modified
	 *            modified or not.
	 */
	protected void setModified(boolean modified) {
		if (this.modified == modified) {
			return;
		}
		this.modified = modified;
		showStatus();
	}

	/**
	 * Method isInletModified decides whether the inlet is modified or not.
	 *
	 * @return modified or not.
	 */
	protected boolean isInletModified() {
		return inletModified;
	}

	/**
	 * Method setInletModified switches the modified status of the inlet.
	 *
	 * @param modified
	 *            modified or not.
	 */
	protected void setInletModified(boolean modified) {
		if (this.inletModified == modified) {
			return;
		}
		this.inletModified = modified;
		showStatus();
	}

	/**
	 * Method listEntity retrieves entity list. It works in case of the frame is
	 * entity class compatible, otherwise, it should be overridden in inherited
	 * frames.
	 *
	 * @param filter
	 *            filter.
	 * @return entity list.
	 * @throws Exception
	 *             if something wrong.
	 */
	protected Object listEntity(Object filter) throws Exception {
		if (!isEntityListable()) {
			throw new Exception("Method listEntity should be overridden.");
		}
		VariantHolder<Object> data = new VariantHolder<Object>();
		data.value = new RecordSet();
		VariantHolder<String> errMsg = new VariantHolder<String>();
		BaseSingleDataSet entityWaiter = (BaseSingleDataSet) (new RMIProxy(Consumer
				.getDefaultConsumer().getSession())).newStub(entityClass);
		if (!entityWaiter.list( pranMap,filter, data, errMsg)) {
			throw new RemoteException(errMsg.value);
		}
		return data.value;
	}

	/**
	 * Method loadEntity refreshes entity data from the value specified.
	 *
	 * @param value
	 *            entity value.
	 */
	protected void loadEntity(Object value) {
		Record rc = ((RecordSet) value).getRecord(0);
		DataSetHelper.loadRowFromRecord(dataSet, rc, true);
		// DataSetHelper.resetStatus(dataSet);
		dataSet.mergeChanges(true);
	}

	/**
	 * Method validateData validates data of current row before post.
	 *
	 * @param newRow
	 *            new row data.
	 * @param oldRow
	 *            old row data, null if inserting.
	 * @throws Exception
	 *             if something wrong.
	 */
	protected void validateData(ReadWriteRow newRow, ReadRow oldRow)
	throws Exception {
	}

	/**
	 * Method flushEntity saves entity message. It works in case of the frame is
	 * flushable class compatible, otherwise, it should be overridden in
	 * inherited frames.
	 *
	 * @throws Exception
	 *             if something wrong.
	 */
	protected Object flushEntity(Object data) throws Exception {
		if (!isEntityFlushable()) {
			throw new Exception("Method flushEntity should be overridden.");
		}

		VariantHolder<Object> dataBackfill = new VariantHolder<Object>();
		VariantHolder<String> errMsg = new VariantHolder<String>();
		BaseSingleDataSet flushableWaiter = (BaseSingleDataSet) (new RMIProxy(
				Consumer.getDefaultConsumer().getSession()))
				.newStub(entityClass);
		if (!flushableWaiter.flush(pranMap,data, dataBackfill, errMsg)) {
			throw new RemoteException(errMsg.value);
		}

		return dataBackfill.value;
	}

	/**
	 * Method removeEntity deletes current entity. It works in case of the frame
	 * is entity class compatible, otherwise, it should be overridden in
	 * inherited frames.
	 *
	 * @param key
	 *            entity key.
	 * @throws Exception
	 *             if something wrong.
	 */
	protected void removeEntity(Object key) throws Exception {
		if (!isEntityMonoEditable()) {
			throw new Exception("Method removeEntity should be overridden.");
		}

		VariantHolder<Object> data = new VariantHolder<Object>();
		data.value = new RecordSet();
		VariantHolder<String> errMsg = new VariantHolder<String>();
		EntityMonoEditable entityWaiter = (EntityMonoEditable) (new RMIProxy(
				Consumer.getDefaultConsumer().getSession()))
				.newStub(entityClass);
		if (!entityWaiter.remove(key, errMsg)) {
			throw new RemoteException(errMsg.value);
		}
	}

	/**
	 * Method listInlet retrieves inlet data. It's always overridden in
	 * inherited frames. If listInlet starts a background thread, it returns
	 * false.
	 *
	 * @return true if execute synchronizedly.
	 */
	protected boolean listInlet() {
		return true;
	}

	/**
	 * Method clearInlet clears inlet data. It's always overridden in inherited
	 * frames.
	 */
	protected void clearInlet() {
	}

	/**
	 * Method flushInlet saves inlet data. It's always overridden in inherited
	 * frames. If flushInlet starts a background thread, it returns false.
	 *
	 * @return true if execute synchronizedly.
	 */
	protected boolean flushInlet() {
		return true;
	}

	/**
	 * Method showInletStatus refreshes status of components depending on frame
	 * state. It's always overridden in inherited frames.
	 */
	protected void showInletStatus() {
	}

	private class FilterMenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			filterPanel.setVisible(filterMenuItem.isSelected());
			listPane.resetToPreferredSizes();
		}
	}

	private class FilterSimpleMenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			filterSimplePanel.setVisible(true);
			filterAdvancedPane.setVisible(false);
			listPane.setResizeWeight(0);
			listPane.resetToPreferredSizes();
		}
	}

	private class FilterAdvancedMenuItemActionListener implements
	ActionListener {
		public void actionPerformed(ActionEvent e) {
			filterAdvancedPane.setVisible(true);
			filterSimplePanel.setVisible(false);
			listPane.setResizeWeight(0.5);
			listPane.resetToPreferredSizes();
		}
	};
	private Object templateId=null;//模板ID
	private Object queryId=null;//模板ID

	/**
	 * @return 模板ID或者编码
	 */
	public Object getTemplateId() {
		return templateId;
	}

	/**
	 * @param 设置模板ID或者编码
	 */
	public void setTemplateId(Object templateId) {
		this.templateId = templateId;
	}

	public void run(String  templateId){
		if(templateId.split(",").length==2){
			this.templateId=templateId.split(",")[0];
			this.queryId=templateId.split(",")[1];
		}else{
			this.templateId=templateId;
			this.queryId=templateId;
		}
		this.pack();
		setVisible(true);
	}
	public void addItem(){

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}

	@Override
	public void addError(DataSet arg0, ReadWriteRow arg1,
			DataSetException arg2, ErrorResponse arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void added(DataSet arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void adding(DataSet arg0, ReadWriteRow arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void canceling(DataSet arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteError(DataSet arg0, DataSetException arg1,
			ErrorResponse arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleted(DataSet arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleting(DataSet arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void editError(DataSet arg0, Column arg1, Variant arg2,
			DataSetException arg3, ErrorResponse arg4) {
		// TODO Auto-generated method stub

	}

	public void inserted(DataSet dataSet){
		//查找到
		if(initValueMap.containsKey(tableId)){
			for(String key:initValueMap.get(tableId).keySet()){
				//取到当前对象类型
				try {
					DefaultValue.setObjectValue(dataSet,  key  ,dataSet.getColumn(key).getDataType()  ,initValueMap.get(tableId).get(key).getValue());
				} catch (DataSetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				//				dataSet.setBigDecimal(key, (BigDecimal)initValueMap.get(tableId).get(key).getValue());
			}
		}
	}
	@Override
	public void inserting(DataSet arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifying(DataSet arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateError(DataSet arg0, ReadWriteRow arg1,
			DataSetException arg2, ErrorResponse arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updated(DataSet arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updating(DataSet arg0, ReadWriteRow arg1, ReadRow arg2)
	throws Exception {
		// TODO Auto-generated method stub

	}
}
