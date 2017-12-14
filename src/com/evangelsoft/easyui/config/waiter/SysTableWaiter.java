package com.evangelsoft.easyui.config.waiter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import com.evangelsoft.easyui.config.homeintf.SysTableHome;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.db.ProvideHelper;
import com.evangelsoft.econnect.db.ResolveHelper;
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.workbench.security.homeintf.SysUserPaHome;

public class SysTableWaiter implements SysTableHome{

	String MASTER_SQL="SELECT * FROM SYS_TABLE";
	String DETAIL_SQL= "SELECT * FROM SYS_COLUMN ";

	//返回表结构
	public boolean getStructure(VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		try{
			RecordSet masterDs = ((RecordSet[]) data.value)[0];
			RecordSet detailDs = ((RecordSet[]) data.value)[1];
			RecordSet historyDs=null;
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
			boolean found;
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(MASTER_SQL+" WHERE TABLE_ID=:TABLE_ID");
			st.setString("TABLE_ID",(String)key);
			ResultSet rs = st.executeQuery();
			if (masterDs != null) {
				RecordSetHelper.loadFromResultSet(rs, masterDs);
				found = masterDs.recordCount() > 0;
			} else {
				found = rs.next();
			}
			if (detailDs != null) {
				st.prepare(DETAIL_SQL+" WHERE TABLE_ID=:TABLE_ID ORDER BY SEQUENCE");
				st.setString("TABLE_ID",(String)key);
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			if (!found) {
				errMsg.value = MessageFormat.format("未查到表【{0}】！！", key);
			}
			st.close();
			return found;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			throw new RemoteException(ExceptionFormat.format(e));
		}
	}
	public boolean getList(String[] list, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		try {
			RecordSet detailDs = ((RecordSet) data.value);
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			StringBuffer buf=new StringBuffer();
			for(int i=0;i<list.length;i++){
				if(i!=0){
					buf.append(",");
				}
				buf.append("'"+list[i]+"'");
			}
			st.prepare(DETAIL_SQL+" WHERE TABLE_ID IN("+buf.toString()+") ORDER BY TABLE_ID, SEQUENCE");

			RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			return true;
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
					"TABLE_ID", filter, null);
			RecordSetHelper.loadFromResultSet(st.executeQuery(), ds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean add(Object key, Object data, VariantHolder<Object> newKey,
			VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg) {
		try {
			// 判断权限。
			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
			.getWaiter(SysUserPaHome.class);

//			if (!sysUserPa.validate(null, "HOTEL_PROTOCOL_UNIT_ADD",Global.UNKNOWN_ID, errMsg)) {
//			throw new Exception(errMsg.value);
//			}
//			BigDecimal unitId=	(BigDecimal) WaiterFactory.getSession().getContext().getTopic(ContextTopic.OWNER_ID);
//			SysRefNumberHome generator = (SysRefNumberHome) WaiterFactory.getWaiter(SysRefNumberHome.class);
//			BigDecimal dromId=(BigDecimal) generator.hold("HOTEL_SHIFT_ID",unitId, 1);
			// 直接采纳调用者提供的键值，不做修改。
			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];
//			newKey.value = masterDelta.getNewRecord(0).getField("TABLE_ID").getString();
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table = "SYS_TABLE";
			resolver.childTables = new String[] { "SYS_COLUMN" };
			resolver.option = ResolveHelper.OPTION_CHECK_DUPLICATE;

			HashMap<String, Object> keyValues = new HashMap<String, Object>();
			keyValues.put("TABLE_ID", masterDelta.getNewRecord(0).getField("TABLE_ID").getString());
			resolver.foreignKeyValues = keyValues;

//			masterDelta.getNewRecord(0).getField("OPR_ID").setNumber((BigDecimal) WaiterFactory.getSession()
//			.getContext().getTopic(ContextTopic.USER_ID));
//			masterDelta.getNewRecord(0).getField("OPR_NAME").setString((String) WaiterFactory.getSession()
//			.getContext().getTopic(ContextTopic.USER_NAME));
//			masterDelta.getNewRecord(0).getField("OP_TIME").setTimestamp(((SysInformationHome) WaiterFactory
//			.getWaiter(SysInformationHome.class)).now());
//			masterDelta.getNewRecord(0).getField("OPR_NUM").setString((String) WaiterFactory.getSession()
//			.getContext().getTopic(ContextTopic.USER_NUMBER));

			resolver.save(masterDelta);
			resolver.table = "SYS_COLUMN";
			resolver.childTables = null;
			resolver.save(detailDelta);

			DeltaRecordSet masterBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(masterDelta, masterBackfill);
			DeltaRecordSet detailBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(detailDelta, detailBackfill);
			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill};
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
			resolver.table = "SYS_TABLE";
			resolver.childTables = new String[] { "SYS_COLUMN"};
//			resolver.option = ResolveHelper.;
			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];
			//如果有添加新行，则要给 新行添加ID
			HashMap<String, Object> keyValues = new HashMap<String, Object>();
			keyValues.put("TABLE_ID",masterDelta.getNewRecord(0).getField("TABLE_ID").getString());
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
				resolver.table = "SYS_COLUMN";
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
			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill};
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
			resolver.table = "SYS_TABLE";
			resolver.childTables = new String[] { "SYS_COLUMN"};
			resolver.removeByKey(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}

	public boolean getTable(Object key, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		try {
			RecordSet masterDs =(RecordSet)  data.value;
			boolean found;
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(MASTER_SQL+" WHERE TABLE_ID=:TABLE_ID");
			st.setString("TABLE_ID",(String)key);
			ResultSet rs = st.executeQuery();
			if (masterDs != null) {
				RecordSetHelper.loadFromResultSet(rs, masterDs);
				found = masterDs.recordCount() > 0;
			} else {
				found = rs.next();
			}
			if (!found) {
				errMsg.value = MessageFormat.format("未查到表【{0}】！！", key);
			}
			st.close();
			return found;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			throw new RemoteException(ExceptionFormat.format(e));
		}
	}



}
