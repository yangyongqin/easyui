//package com.test.waiter;
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.HashMap;
//
//import com.evangelsoft.crosslink.types.Global;
//import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
//import com.evangelsoft.econnect.dataformat.RecordSet;
//import com.evangelsoft.econnect.dataformat.RecordSetHelper;
//import com.evangelsoft.econnect.dataformat.VariantHolder;
//import com.evangelsoft.econnect.db.NamedStatement;
//import com.evangelsoft.econnect.db.ProvideHelper;
//import com.evangelsoft.econnect.db.ResolveHelper;
//import com.evangelsoft.econnect.plant.TxUnit;
//import com.evangelsoft.econnect.plant.WaiterFactory;
//import com.evangelsoft.econnect.session.RemoteException;
//import com.evangelsoft.econnect.util.ExceptionFormat;
//import com.evangelsoft.workbench.config.homeintf.SysRefNumberHome;
//import com.evangelsoft.workbench.homeintf.SysInformationHome;
//import com.evangelsoft.workbench.security.homeintf.SysUserPaHome;
//import com.evangelsoft.workbench.types.BoolStr;
//import com.evangelsoft.workbench.types.ContextTopic;
//import com.test.homeintf.ClassesHome;
//
//public class ClassesWaiter implements ClassesHome{
//
//	String MASTER_SQL="SELECT CLASSESS_ID,CLASSESS_NUM,CLASSESS_NAME FROM CLASSES A";
//	String DETAIL_SQL="SELECT STU_ID,STU_NUM,STU_NAME FROM STUDENT A ";
//	public boolean getStructure(VariantHolder<Object> data,
//			VariantHolder<String> errMsg) throws RemoteException {
//		try{
//			RecordSet masterDs = ((RecordSet[]) data.value)[0];
//			RecordSet detailDs = ((RecordSet[]) data.value)[1];
//
//			NamedStatement st = new NamedStatement(TxUnit.getConnection());
//			st.prepare(MASTER_SQL
//					+ " WHERE 1 <> 1");
//			ResultSet rs = st.executeQuery();
//			if (masterDs != null) {
//				RecordSetHelper.loadFromResultSet(rs, masterDs);
//			}
//
//			if (detailDs != null) {
//				st.prepare(DETAIL_SQL+ " WHERE 1 <> 1");
//				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
//			}
//			st.close();
//			return true;
//		} catch (Exception e) {
//			ExceptionFormat.format(e, errMsg);
//			return false;
//		}
//	}
//
//	public boolean get(Object key, VariantHolder<Object> data,
//			VariantHolder<String> errMsg) throws RemoteException {
//		BigDecimal id=(BigDecimal)key;
//		boolean found = true;
//		try {
//			RecordSet masterDs = ((RecordSet[]) data.value)[0];
//			RecordSet detailDs = ((RecordSet[]) data.value)[1];
//			NamedStatement st = new NamedStatement(TxUnit.getConnection());
//			st.prepare(MASTER_SQL+" WHERE CLASSESS_ID=:CLASSESS_ID");
//			st.setBigDecimal("CLASSESS_ID", id);
//			ResultSet rs = st.executeQuery();
//
//			if (masterDs != null) {
//				RecordSetHelper.loadFromResultSet(rs, masterDs);
//				found = masterDs.recordCount() > 0;
//			} else {
//				found = rs.next();
//			}
//			if (detailDs != null) {
//				st.prepare(DETAIL_SQL+" WHERE A.CLASSESS_ID=:CLASSESS_ID ");
//				st.setBigDecimal("CLASSESS_ID",id);
//				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
//			}
//			return found;
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExceptionFormat.format(e, errMsg);
//			return false;
//		}
//
//	}
//	public boolean list(Object filter, VariantHolder<Object> list,
//			VariantHolder<String> errMsg) throws RemoteException {
//		try {
//			// 判断权限。
//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
//			.getWaiter(SysUserPaHome.class);
//
////			if (!sysUserPa.validate(null, "CLASSES_VIEW",Global.UNKNOWN_ID, errMsg)) {
////				throw new Exception(errMsg.value);
////			}
//			HashMap<String, String> map = new HashMap<String, String>();
////			map.put("UNIT_ID", "A.UNIT_ID");
////			map.put("OPR_ID", "A.OPR_ID");
////			map.put("OPR_NAME", "B.FULL_NAME");
////			map.put("OPR_NUM", "C.PRSNL_NUM");
////			map.put("REMARK", "A.REMARK");
////			map.put("OP_TIME", "A.OP_TIME");
////			map.put("STATUS", "A.STATUS");
//			RecordSet ds = (RecordSet) list.value;
//			Connection db = TxUnit.getConnection();
//			NamedStatement st = new NamedStatement(db);
//			ProvideHelper.composeSql(st, MASTER_SQL, "", "", "",
//					"CLASSESS_ID", filter, map);
////			st.setBigDecimal("UNIT_ID", (BigDecimal) WaiterFactory.getSession()
////					.getContext().getTopic(ContextTopic.OWNER_ID));
//			RecordSetHelper.loadFromResultSet(st.executeQuery(), ds);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExceptionFormat.format(e, errMsg);
//			return false;
//		}
//	}
//
//	public boolean add(Object key, Object data, VariantHolder<Object> newKey,
//			VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg) {
//		try {
//			// 判断权限。
//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
//			.getWaiter(SysUserPaHome.class);
//
////			if (!sysUserPa.validate(null, "CLASSES_ADD",Global.UNKNOWN_ID, errMsg)) {
////				throw new Exception(errMsg.value);
////			}
//			BigDecimal unitId=	(BigDecimal) WaiterFactory.getSession().getContext().getTopic(ContextTopic.OWNER_ID);
//			SysRefNumberHome generator = (SysRefNumberHome) WaiterFactory.getWaiter(SysRefNumberHome.class);
//			BigDecimal id=(BigDecimal) generator.hold("HOTEL_SHIFT_ID",Global.GLOBAL_ID, 1);
//			// 直接采纳调用者提供的键值，不做修改。
//			newKey.value = id;
//			ResolveHelper resolver = new ResolveHelper();
//			resolver.db = TxUnit.getConnection();
//			resolver.table = "CLASSES";
//			resolver.childTables = new String[] { "STUDENT" };
//			resolver.option = ResolveHelper.OPTION_CHECK_DUPLICATE;
//			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
//			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];
//			HashMap<String, Object> keyValues = new HashMap<String, Object>();
//			keyValues.put("CLASSESS_ID", id);
//			resolver.foreignKeyValues = keyValues;
//
//
//			resolver.save(masterDelta);
//			resolver.table = "STUDENT";
//			resolver.childTables = null;
//			for(int i=0;i<detailDelta.recordCount();i++){
//				detailDelta.getNewRecord(0).getField("STU_ID").setNumber((BigDecimal) generator.hold("HOTEL_SHIFT_ID",unitId, 1));
//			}
//			resolver.save(detailDelta);
//			DeltaRecordSet masterBackfill = new DeltaRecordSet();
//			RecordSetHelper.saveToDeltaRecordSet(masterDelta, masterBackfill);
//			DeltaRecordSet detailBackfill = new DeltaRecordSet();
//			RecordSetHelper.saveToDeltaRecordSet(detailDelta, detailBackfill);
//			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill,null};
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExceptionFormat.format(e, errMsg);
//			TxUnit.setRollback();
//			return false;
//		}
//	}
//
//	public boolean modify(Object key, Object data,
//			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
//			VariantHolder<String> errMsg) throws RemoteException {
//		try {
//			// 判断权限。
////			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory.getWaiter(SysUserPaHome.class);
////			if (!sysUserPa.validate(null, "CLASSES_MODIFY",Global.UNKNOWN_ID, errMsg)) {
////			throw new Exception(errMsg.value);
////			}
//			ResolveHelper resolver = new ResolveHelper();
//			resolver.db = TxUnit.getConnection();
//			resolver.table = "CLASSES";
//			resolver.childTables = new String[] { "STUDENT"};
//			resolver.option = ResolveHelper.OPTION_CHECK_DUPLICATE;
//			DeltaRecordSet masterDelta = ((DeltaRecordSet[]) data)[0];
//			DeltaRecordSet detailDelta = ((DeltaRecordSet[]) data)[1];
//			//如果有添加新行，则要给 新行添加ID
//			HashMap<String, Object> keyValues = new HashMap<String, Object>();
//			keyValues.put("CLASSESS_ID",(BigDecimal)key );
//			resolver.foreignKeyValues = keyValues;
//
//
//			resolver.save(masterDelta);
//			// 保存从表。
//			SysRefNumberHome generator = (SysRefNumberHome) WaiterFactory.getWaiter(SysRefNumberHome.class);
//			if(detailDelta!=null){
//				resolver.table = "STUDENT";
//				resolver.childTables = null;
//				for(int i=0;i<detailDelta.recordCount();i++){
//					detailDelta.getNewRecord(0).getField("STU_ID").setNumber((BigDecimal) generator.hold("HOTEL_SHIFT_ID",Global.GLOBAL_ID, 1));
//				}
//				resolver.save(detailDelta);
//			}
//
//			DeltaRecordSet masterBackfill = new DeltaRecordSet();
//			RecordSetHelper.saveToDeltaRecordSet(masterDelta, masterBackfill);
//			DeltaRecordSet detailBackfill =null;
//
//			if(detailDelta!=null){
//
//				detailBackfill= new DeltaRecordSet();
//				RecordSetHelper.saveToDeltaRecordSet(detailDelta, detailBackfill);
//			}
//			dataBackfill.value = new DeltaRecordSet[] { masterBackfill,detailBackfill};
//			return true;
//		} catch (Exception e) {
//			ExceptionFormat.format(e, errMsg);
//			TxUnit.setRollback();
//			return false;
//		}
//	}
//
//	public boolean remove(Object key, VariantHolder<String> errMsg)
//	throws RemoteException {
//		try{
//			// 判断权限。
//			SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory
//			.getWaiter(SysUserPaHome.class);
////			if (!sysUserPa.validate(null, "JX_DROM_DEL",Global.UNKNOWN_ID, errMsg)) {
////			throw new Exception(errMsg.value);
////			}
//			ResolveHelper resolver = new ResolveHelper();
//			resolver.db = TxUnit.getConnection();
//			resolver.table = "CLASSES";
//			resolver.childTables = new String[] { "STUDENT"};
//			resolver.removeByKey(key);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExceptionFormat.format(e, errMsg);
//			TxUnit.setRollback();
//			return false;
//		}
//	}
//
//
//
//}
