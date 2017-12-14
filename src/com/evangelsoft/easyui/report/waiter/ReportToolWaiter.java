package com.evangelsoft.easyui.report.waiter;

import com.evangelsoft.easyui.report.homeintf.ReportToolHome;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;

public class ReportToolWaiter implements ReportToolHome{

	@Override
	public boolean getStructure(String sql, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException {
		//检查sql是否有 INERT UPDATE，DELETE语句
		try{
			sql=sql.toUpperCase();
			if(sql.indexOf("INERT")!=-1||sql.indexOf("UPDATE")!=-1||sql.indexOf("DELETE")!=-1
					||sql.indexOf("ALTER")!=-1||sql.indexOf("DROP")!=-1||sql.indexOf("TRUNCATE")!=-1||sql.indexOf("REMOVE")!=-1){
				errMsg.value="不允许执行更改数据库数据的操作！";
				return false;
			}
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(sql);
			RecordSet set=new RecordSet();
			RecordSetHelper.loadFromResultSet(st.executeQuery(), set);
			data.value=set;
		}catch(Exception e){
			errMsg.value=e.getMessage();
			return false;
		}
		return true;
	}

}
