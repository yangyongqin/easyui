package com.evangelsoft.easyui.template.waiter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Map;

import com.evangelsoft.easyui.template.homeintf.SysQueryTemplateHome;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;

public class SysQueryTemplateWaiter implements SysQueryTemplateHome{

	String MASTER_SQL="select * from SYS_QUERY_TEMPLATE";
	String DETAIL_SQL="select A.* from SYS_QUERY_TEMPLATE_DETAL A INNER JOIN SYS_QUERY_TEMPLATE B ON A.QUERY_TEMPLATE_ID=B.QUERY_TEMPLATE_ID";


	public boolean getStructure(Map<String, Object> map,
			VariantHolder<Object> data, VariantHolder<String> errMsg)
					throws RemoteException {
		return false;
	}


	public boolean get(Map<String, Object> map, Object key,
			VariantHolder<Object> data, VariantHolder<String> errMsg)
					throws RemoteException {
		//调用审批流及其其他
		try {
			RecordSet masterDs = ((RecordSet[]) data.value)[0];
			RecordSet detailDs = ((RecordSet[]) data.value)[1];

			boolean found;
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			if(key instanceof BigDecimal){
				st.prepare(MASTER_SQL+" WHERE TEMPLATE_ID=:TEMPLATE_ID");
				st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
			}else{
				st.prepare(MASTER_SQL+" WHERE QUERY_TEMPLATE_NUM=:TEMPLATE_ID");
				st.setString("TEMPLATE_ID",(String)key);
			}
			ResultSet rs = st.executeQuery();
			if (masterDs != null) {
				RecordSetHelper.loadFromResultSet(rs, masterDs);
				found = masterDs.recordCount() > 0;
			} else {
				found = rs.next();
				if (!found) {
					errMsg.value = MessageFormat.format("数据填充失败", key);
					return false;
				}
			}
			if (detailDs != null) {
				if(key instanceof BigDecimal){
					st.prepare(DETAIL_SQL+" WHERE A.QUERY_TEMPLATE_ID=:TEMPLATE_ID");
					st.setBigDecimal("TEMPLATE_ID",(BigDecimal)key);
				}else{
					st.prepare(DETAIL_SQL+" WHERE B.QUERY_TEMPLATE_NUM=:TEMPLATE_ID");
					st.setString("TEMPLATE_ID",(String)key);
				}
				RecordSetHelper.loadFromResultSet(st.executeQuery(), detailDs);
			}
			st.close();
			return found;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			//			throw new RemoteException(ExceptionFormat.format(e));
			return false;
		}
		//调用审批流及其其他
	}


	public boolean list(Map<String, Object> map, Object filter,
			VariantHolder<Object> list, VariantHolder<String> errMsg) {
		return false;
	}


	public boolean add(Map<String, Object> map, Object key, Object data,
			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg) {
		return false;
	}


	public boolean modify(Map<String, Object> map, Object key, Object data,
			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg) {
		return false;
	}


	public boolean remove(Map<String, Object> map, Object key,
			VariantHolder<String> errMsg) throws RemoteException {
		return false;
	}

}
