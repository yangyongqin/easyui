package com.evangelsoft.easyui.template.waiter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.evangelsoft.easyui.template.homeintf.PlugInHome;
import com.evangelsoft.easyui.type.ActionBeforeAfter;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.TransientRecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.db.ProvideHelper;
import com.evangelsoft.econnect.db.ResolveHelper;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;

public class PlugInWaiter extends BaseSingleDataSetWaiter implements PlugInHome{

	String SQL="SELECT PLUGIN.*,BUTTON.BUTTON_NAME "
			+" FROM SYS_TEMPLATE_PLUGIN PLUGIN "
			+" INNER JOIN SYS_TEMPLATE_BUTTON BUTTON ON PLUGIN.TEMPLATE_ID=BUTTON.TEMPLATE_ID AND PLUGIN.BUTTON_CODE= BUTTON.BUTTON_CODE"
			+" LEFT JOIN SYS_TEMPLATE TEMPLATE  ON PLUGIN.TEMPLATE_ID=TEMPLATE.TEMPLATE_ID";

	@Override
	public String getSql() {
		return this.SQL;
	}

	@Override
	public boolean list(Map<String, Object> map, Object filter, VariantHolder<Object> list,
			VariantHolder<String> errMsg) throws RemoteException {
		try{
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			HashMap<String,String> filterMap=new HashMap<String,String>();
			filterMap.put("TEMPLATE_ID", "PLUGIN.TEMPLATE_ID");
			filterMap.put("BUTTON_CODE", "PLUGIN.BUTTON_CODE");
			filterMap.put("DESCRIPTION", "PLUGIN.DESCRIPTION");
			ProvideHelper.composeSql(st, SQL,"", "",
					"",""/*默认排序字段，如果没有其他排序，则取当前这个排序*/, filter, filterMap);
			TransientRecordSet set=new TransientRecordSet();
			//测试阶段先打印下sql
			System.out.println(st.getJdbcSql());
			RecordSetHelper.loadFromResultSet(st.executeQuery(), set);
			list.value=set;
			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}

	@Override
	public boolean flush(Map<String, Object> map, Object data, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg) throws RemoteException {
		try{
			//先保存主表
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table="SYS_TEMPLATE_PLUGIN";
			resolver.save((DeltaRecordSet)data);
			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}

	@Override
	public boolean getBeforePlugIn(Object funcId, String operate,VariantHolder<Object> list, VariantHolder<String> errMsg) throws Exception {
		return this.getActionPlugIn(funcId, operate, ActionBeforeAfter.BEFORE, list,errMsg);
	}

	@Override
	public boolean getAfterPlugIn(Object funcId, String operate,VariantHolder<Object> list, VariantHolder<String> errMsg) throws Exception {
		return this.getActionPlugIn(funcId, operate, ActionBeforeAfter.AFTER, list,errMsg);
	}

	@Override
	public boolean getActionPlugIn(Object funcId, String operate, String beforeOrafter,VariantHolder<Object> list, VariantHolder<String> errMsg) throws Exception {
		try{
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(SQL+"");
			StringBuilder str=new StringBuilder();
			if(funcId instanceof BigDecimal){
				str.append(" WHERE  PLUGIN.TEMPLATE_ID=:TEMPLATE_ID AND PLUGIN.BUTTON_CODE=:BUTTON_CODE AND BEFORE_AFTER=:BEFORE_AFTER");
				st.prepare(SQL+str);
				st.setBigDecimal("TEMPLATE_ID", (BigDecimal)funcId);
			}else {
				str.append(" WHERE  TEMPLATE.TEMPLATE_NUM=:TEMPLATE_ID AND PLUGIN.BUTTON_CODE=:BUTTON_CODE AND BEFORE_AFTER=:BEFORE_AFTER");
				st.prepare(SQL+str);
				st.setString("TEMPLATE_ID", funcId.toString());
			}
			st.setString("BUTTON_CODE", operate);
			st.setString("BEFORE_AFTER", beforeOrafter);
			
			TransientRecordSet set=new TransientRecordSet();
			//测试阶段先打印下sql
			System.out.println(st.getJdbcSql());
			RecordSetHelper.loadFromResultSet(st.executeQuery(), set);
			list.value=set;
			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}
}
