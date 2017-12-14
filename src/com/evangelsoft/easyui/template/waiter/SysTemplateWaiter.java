package com.evangelsoft.easyui.template.waiter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.evangelsoft.easyui.template.homeintf.SysTemplateHome;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.db.ProvideHelper;
import com.evangelsoft.econnect.db.ResolveHelper;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.workbench.config.homeintf.SysRefNumberHome;
import com.evangelsoft.workbench.security.homeintf.SysUserPaHome;
import com.evangelsoft.workbench.types.Global;

public class SysTemplateWaiter implements SysTemplateHome{

	String MASTER_SQL="SELECT A.TEMPLATE_ID,A.TEMPLATE_NUM,A.TEMPLATE_NAME,A.MAIN_TABLE_ID,B.TABLE_NAME,MAIN_SQL,WHERE_SQL,GROUP_BY_SQL,SORT_SQL,A.MODE,A.VIEW_MODE,DEFAULT_WIDTH,DEFAULT_HEIGHT,A.PRIV_ID,C.DESCRIPTION PRIV_NAME,A.REMARKS,A.MAIN_KEY FROM SYS_TEMPLATE A"
			+" LEFT JOIN SYS_TABLE B ON  A.MAIN_TABLE_ID=B.TABLE_ID"
			+" LEFT JOIN SYS_PRIVILEGE C ON A.PRIV_ID=C.PRIV_ID";
	String DETAIL_SQL= "SELECT A.TABLE_ID,B.TABLE_NAME,A.REMARKS FROM SYS_TEMPLATE_DETAIL A LEFT JOIN SYS_TABLE B ON A.TABLE_ID=B.TABLE_ID LEFT JOIN SYS_TEMPLATE C ON A.TEMPLATE_ID=C.TEMPLATE_ID";

	String TABS_SQL= "SELECT A.* FROM SYS_TABS A LEFT JOIN SYS_TEMPLATE B ON A.TEMPLATE_ID=B.TEMPLATE_ID";

	String ATTRIBUTE_SQL="SELECT A.*,C.TABLE_ID,D.DESCRIPTION PRIV_NAME FROM SYS_ATTRIBUTE A LEFT JOIN SYS_TEMPLATE B ON A.TEMPLATE_ID=B.TEMPLATE_ID"
			+" LEFT JOIN  SYS_TABS C ON A.TEMPLATE_ID=C.TEMPLATE_ID AND  A.TABS_ID=C.TABS_ID "
			+" LEFT JOIN SYS_PRIVILEGE D ON A.PRIV_ID=D.PRIV_ID";

	String PRINT_SQL="SELECT A.* FROM SYS_TEMPLATE_PRINT A LEFT JOIN SYS_TEMPLATE B ON A.TEMPLATE_ID=B.TEMPLATE_ID";
	
	String BUTTON_SQL="SELECT A.* FROM SYS_TEMPLATE_BUTTON A LEFT JOIN SYS_TEMPLATE B ON A.TEMPLATE_ID=B.TEMPLATE_ID";
	//返回表结构
	public boolean getStructure(VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		try{
			RecordSet masterDs = ((RecordSet[]) data.value)[0];
			RecordSet detailDs = ((RecordSet[]) data.value)[1];
			RecordSet historyDs=null;
			if(((RecordSet[]) data.value).length>2){
				historyDs=((RecordSet[]) data.value)[2];
			}
			RecordSet attributeSet=null;
			if(((RecordSet[]) data.value).length>3){
				attributeSet=((RecordSet[]) data.value)[3];
			}
			RecordSet printSet=null;
			if(((RecordSet[]) data.value).length>4){
				 printSet=((RecordSet[]) data.value)[4];
			}
			RecordSet buttonSet=null;
			if(((RecordSet[]) data.value).length>5){
				buttonSet=((RecordSet[]) data.value)[5];
			}
			
			
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(MASTER_SQL
					+ " WHERE 1 <> 1");
			ResultSet rs = st.executeQuery();
			if (masterDs != null) {
				RecordSetHelper.loadFromResultSet(rs, masterDs);
			}
			if (detailDs != null) {
				st.prepare(DETAIL_SQL+ " WHERE 1 <> 1");
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			if (historyDs != null) {
				st.prepare(TABS_SQL+" WHERE 1<>1");
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			if (attributeSet != null) {
				st.prepare(ATTRIBUTE_SQL+" WHERE WHERE 1<>1");
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			if (printSet != null) {
				st.prepare(PRINT_SQL+" WHERE WHERE 1<>1");
				RecordSetHelper.loadFromResultSet(st.executeQuery(), printSet);
			}
			if (buttonSet != null) {
				st.prepare(BUTTON_SQL+" WHERE WHERE 1<>1");
				RecordSetHelper.loadFromResultSet(st.executeQuery(), printSet);
			}


			st.close();
			return true;
		} catch (Exception e) {
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}

	public boolean get(Object key, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		try {
			RecordSet masterDs = ((RecordSet[]) data.value)[0];
			RecordSet detailDs = ((RecordSet[]) data.value)[1];
			RecordSet historyDs=null;
			if(((RecordSet[]) data.value).length>2){
				historyDs=((RecordSet[]) data.value)[2];
			}
			RecordSet attributeSet=null;
			if(((RecordSet[]) data.value).length>3){
				attributeSet=((RecordSet[]) data.value)[3];
			}
			RecordSet printSet=null;
			if(((RecordSet[]) data.value).length>4){
				 printSet=((RecordSet[]) data.value)[4];
			}
			RecordSet buttonSet=null;
			if(((RecordSet[]) data.value).length>5){
				buttonSet=((RecordSet[]) data.value)[5];
			}

			boolean found;
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			if(key instanceof BigDecimal){
				st.prepare(MASTER_SQL+" WHERE TEMPLATE_ID=:TEMPLATE_ID");
				st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
			}else{
				st.prepare(MASTER_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID");
				st.setString("TEMPLATE_ID",(String)key);
			}
			ResultSet rs = st.executeQuery();
			if (masterDs != null) {
				RecordSetHelper.loadFromResultSet(rs, masterDs);
				found = masterDs.recordCount() > 0;
				if(!found){
					if(key instanceof BigDecimal){
						errMsg.value = MessageFormat.format("模板ID为【{0}】不存在！", key);
					}else{
						errMsg.value = MessageFormat.format("模板编号为【{0}】不存在！", key);
					}
					return false;
				}
			} else {
				found = rs.next();
				if (!found) {
					errMsg.value = MessageFormat.format("数据填充失败", key);
					return false;
				}
			}
			if (detailDs != null) {
				if(key instanceof BigDecimal){
					st.prepare(DETAIL_SQL+" WHERE A.TEMPLATE_ID=:TEMPLATE_ID");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(DETAIL_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			if (historyDs != null) {
				if(key instanceof BigDecimal){
					st.prepare(TABS_SQL+" WHERE A.TEMPLATE_ID=:TEMPLATE_ID ORDER BY SEQUENCE");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(TABS_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID ORDER BY SEQUENCE");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), historyDs);
			}

			if (attributeSet != null) {
				if(key instanceof BigDecimal){
					st.prepare(ATTRIBUTE_SQL+" WHERE A.TEMPLATE_ID=:TEMPLATE_ID ORDER BY A.TABS_ID,A.SEQUENCE");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(ATTRIBUTE_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID ORDER BY A.TABS_ID,A.SEQUENCE");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), attributeSet);
			}
			
			if (printSet != null) {
				if(key instanceof BigDecimal){
					st.prepare(PRINT_SQL+" WHERE A.TEMPLATE_ID=:TEMPLATE_ID ");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(PRINT_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID ");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), printSet);
			}
			
			if (buttonSet != null) {
				if(key instanceof BigDecimal){
					st.prepare(BUTTON_SQL+" WHERE A.TEMPLATE_ID=:TEMPLATE_ID ");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(BUTTON_SQL+" WHERE TEMPLATE_NUM=:TEMPLATE_ID ");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), buttonSet);
			}

			st.close();
			return found;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			throw new RemoteException(ExceptionFormat.format(e));
		}
	}

	public boolean list(Object filter, VariantHolder<Object> list,
			VariantHolder<String> errMsg) throws RemoteException {
		try {
			// 判断权限。
			//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
			//			.getWaiter(SysUserPaHome.class);

			//			if (!sysUserPa.validate(null, "HOTEL_PROTOCOL_UNIT_VIEW",Global.UNKNOWN_ID, errMsg)) {
			//			throw new Exception(errMsg.value);
			//			}
			//			HashMap<String, String> map = new HashMap<String, String>();
			//			map.put("UNIT_ID", "A.UNIT_ID");
			//			map.put("OPR_ID", "A.OPR_ID");
			//			map.put("OPR_NAME", "B.FULL_NAME");
			//			map.put("OPR_NUM", "C.PRSNL_NUM");
			//			map.put("REMARK", "A.REMARK");
			//			map.put("OP_TIME", "A.OP_TIME");
			//			map.put("STATUS", "A.STATUS");
			RecordSet ds = (RecordSet) list.value;
			Connection db = TxUnit.getConnection();
			NamedStatement st = new NamedStatement(db);
			ProvideHelper.composeSql(st, MASTER_SQL, "", "", "",
					"TEMPLATE_ID", filter, null);
			RecordSetHelper.loadFromResultSet(st.executeQuery(), ds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}
	public boolean add(Object key, Object data, VariantHolder<Object> newKey,
			VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg) {
		try {
			// 判断权限。
			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
					.getWaiter(SysUserPaHome.class);

			//			if (!sysUserPa.validate(null, "HOTEL_PROTOCOL_UNIT_ADD",Global.UNKNOWN_ID, errMsg)) {
			//			throw new Exception(errMsg.value);
			//			}
			SysRefNumberHome generator = (SysRefNumberHome) WaiterFactory.getWaiter(SysRefNumberHome.class);
			BigDecimal dromId=(BigDecimal) generator.hold("HOTEL_SHIFT_ID",Global.GLOBAL_ID, 1);
			// 直接采纳调用者提供的键值，不做修改。
			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];

			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table = "SYS_TEMPLATE";
			resolver.childTables = new String[] { "SYS_TEMPLATE_DETAIL" };
			resolver.option = ResolveHelper.OPTION_CHECK_DUPLICATE;

			HashMap<String, Object> keyValues = new HashMap<String, Object>();
			keyValues.put("TEMPLATE_ID", dromId);
			resolver.foreignKeyValues = keyValues;
			newKey.value = dromId;

			//			masterDelta.getNewRecord(0).getField("OPR_ID").setNumber((BigDecimal) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_ID));
			//			masterDelta.getNewRecord(0).getField("OPR_NAME").setString((String) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_NAME));
			//			masterDelta.getNewRecord(0).getField("OP_TIME").setTimestamp(((SysInformationHome) WaiterFactory
			//			.getWaiter(SysInformationHome.class)).now());
			//			masterDelta.getNewRecord(0).getField("OPR_NUM").setString((String) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_NUMBER));

			resolver.save(masterDelta);
			resolver.table = "SYS_TEMPLATE_DETAIL";
			resolver.childTables = null;
			resolver.save(detailDelta);



			DeltaRecordSet masterBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(masterDelta, masterBackfill);
			DeltaRecordSet detailBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(detailDelta, detailBackfill);

			DeltaRecordSet historyBackfill =null;
			DeltaRecordSet attributeBackfill =null;
			DeltaRecordSet printBackfill=null ;
			DeltaRecordSet buttonBackfill=null ;

			DeltaRecordSet historyDs=null;
			if(((DeltaRecordSet[]) data).length>2){
				historyDs=((DeltaRecordSet[]) data)[2];
				historyBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(historyDs, historyBackfill);
			}
			DeltaRecordSet attributeSet=null;
			if(((DeltaRecordSet[]) data).length>3){
				attributeSet=((DeltaRecordSet[]) data)[3];
//				resolver.table = "SYS_ATTRIBUTE";
//				resolver.childTables = null;
//				resolver.save(attributeSet);
				
				attributeBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(attributeSet, attributeBackfill);
			}
			DeltaRecordSet printSet=null;
			if(((DeltaRecordSet[]) data).length>4){
				printSet=((DeltaRecordSet[]) data)[4];
				resolver.table = "SYS_TEMPLATE_PRINT";
				resolver.childTables = null;
				resolver.save(printSet);
				printBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(printSet, printBackfill);
			}
			
			DeltaRecordSet buttonSet=null;
			if(((DeltaRecordSet[]) data).length>5){
				buttonSet=((DeltaRecordSet[]) data)[5];
				resolver.table = "SYS_TEMPLATE_BUTTON";
				resolver.childTables = null;
				resolver.save(buttonSet);
				buttonBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(printSet, printBackfill);
			}
			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill,historyBackfill,attributeBackfill,printBackfill,buttonBackfill};
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}

	public boolean modify(Object key, Object data,
			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg) throws RemoteException {
		try {
			// 判断权限。
			//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory.getWaiter(SysUserPaHome.class);
			//			if (!sysUserPa.validate(null, "HOTEL_PROTOCOL_UNIT_MODIFY",Global.UNKNOWN_ID, errMsg)) {
			//			throw new Exception(errMsg.value);
			//			}
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table = "SYS_TEMPLATE";
			resolver.childTables = new String[] { "SYS_TEMPLATE_DETAIL"};
			resolver.option = ResolveHelper.OPTION_CHECK_DUPLICATE;
			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];
			//如果有添加新行，则要给 新行添加ID
			HashMap<String, Object> keyValues = new HashMap<String, Object>();
			keyValues.put("TEMPLATE_ID",(BigDecimal)key);
			resolver.foreignKeyValues = keyValues;

			//			masterDelta.getNewRecord(0).getField("OPR_ID").setNumber((BigDecimal) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_ID));
			//			masterDelta.getNewRecord(0).getField("OPR_NUM").setString((String) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_NUMBER));
			//			masterDelta.getNewRecord(0).getField("OPR_NAME").setString((String) WaiterFactory.getSession()
			//			.getContext().getTopic(ContextTopic.USER_NAME));
			//			masterDelta.getNewRecord(0).getField("OP_TIME").setTimestamp(((SysInformationHome) WaiterFactory
			//			.getWaiter(SysInformationHome.class)).now());

			resolver.save(masterDelta);
			// 保存从表。
			if(detailDelta!=null){
				resolver.table = "SYS_TEMPLATE_DETAIL";
				resolver.childTables = null;
				resolver.save(detailDelta);
			}

			DeltaRecordSet masterBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(masterDelta, masterBackfill);
			DeltaRecordSet detailBackfill =null;
			if(detailDelta!=null){
				detailBackfill= new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(detailDelta, detailBackfill);
			}
			DeltaRecordSet historyBackfill = new DeltaRecordSet();
			DeltaRecordSet attributeBackfill = new DeltaRecordSet();
			DeltaRecordSet printBackfill = new DeltaRecordSet();
			DeltaRecordSet buttonBackfill=null ;

			DeltaRecordSet historyDs=null;
			if(((DeltaRecordSet[]) data).length>2){
				historyDs=((DeltaRecordSet[]) data)[2];
				resolver.table = "SYS_TABS";
				resolver.childTables = null;
				//				historyDs.getNewRecord(0).getField("TEMPLATE_ID").getNumber();
				resolver.foreignKeyValues = keyValues;
				resolver.save(historyDs);


				RecordSetHelper.saveToDeltaRecordSet(historyDs, historyBackfill);
			}
			DeltaRecordSet attributeSet=null;
			if(((DeltaRecordSet[]) data).length>3){
				attributeSet=((DeltaRecordSet[]) data)[3];
				resolver.table = "SYS_ATTRIBUTE";
				resolver.childTables = null;
				resolver.save(attributeSet);

				RecordSetHelper.saveToDeltaRecordSet(attributeSet, attributeBackfill);

			}
			DeltaRecordSet printSet=null;
			if(((DeltaRecordSet[]) data).length>4){
				printSet=((DeltaRecordSet[]) data)[4];
				resolver.table = "SYS_TEMPLATE_PRINT";
				resolver.childTables = null;
				resolver.save(printSet);
				printBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(printSet, printBackfill);
			}
			
			DeltaRecordSet buttonSet=null;
			if(((DeltaRecordSet[]) data).length>5){
				buttonSet=((DeltaRecordSet[]) data)[5];
				resolver.table = "SYS_TEMPLATE_BUTTON";
				resolver.childTables = null;
				resolver.save(buttonSet);
				buttonBackfill=new DeltaRecordSet();
				RecordSetHelper.saveToDeltaRecordSet(printSet, printBackfill);
			}
			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill,historyBackfill,attributeBackfill,printBackfill,buttonBackfill};
			return true;
		} catch (Exception e) {
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}
	public boolean remove(Object key, VariantHolder<String> errMsg)
			throws RemoteException {
		try{
			// 判断权限。
			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
					.getWaiter(SysUserPaHome.class);
			//			if (!sysUserPa.validate(null, "JX_DROM_DEL",Global.UNKNOWN_ID, errMsg)) {
			//			throw new Exception(errMsg.value);
			//			}
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table = "SYS_TEMPLATE";
			resolver.childTables = new String[] { "SYS_TEMPLATE_DETAIL","SYS_TABS","SYS_ATTRIBUTE","SYS_TEMPLATE_PRINT","SYS_TEMPLATE_BUTTON"};
			resolver.removeByKey(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}

	@Override
	public boolean getAllButton(Map<String, Object> filter, VariantHolder<Object> list, VariantHolder<String> errMsg) {
		try {
			// 判断权限。
			//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
			//			.getWaiter(SysUserPaHome.class);

			//			if (!sysUserPa.validate(null, "HOTEL_PROTOCOL_UNIT_VIEW",Global.UNKNOWN_ID, errMsg)) {
			//			throw new Exception(errMsg.value);
			//			}
			RecordSet ds = (RecordSet) list.value;
			Connection db = TxUnit.getConnection();
			NamedStatement st = new NamedStatement(db);
			ProvideHelper.composeSql(st, BUTTON_SQL, "", "", "",
					"A.TEMPLATE_ID", filter, null);
			RecordSetHelper.loadFromResultSet(st.executeQuery(), ds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}



}
