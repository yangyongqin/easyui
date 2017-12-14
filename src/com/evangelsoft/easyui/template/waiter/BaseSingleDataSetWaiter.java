package com.evangelsoft.easyui.template.waiter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.evangelsoft.easyui.template.homeintf.BaseSingleDataSetHome;
import com.evangelsoft.easyui.template.homeintf.SysTemplateHome;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.econnect.dataformat.DataState;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.RecordSetHelper;
import com.evangelsoft.econnect.dataformat.TransientRecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.db.NamedStatement;
import com.evangelsoft.econnect.db.ProvideHelper;
import com.evangelsoft.econnect.db.ResolveHelper;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.workbench.homeintf.SysInformationHome;
import com.evangelsoft.workbench.types.ContextTopic;

public class BaseSingleDataSetWaiter implements BaseSingleDataSetHome{

	public String getSql(){
		return null;
	}
	 Logger logger=Logger.getLogger(BaseSingleDataSetWaiter.class);

	BigDecimal userId = (BigDecimal) WaiterFactory.getSession().getContext().getTopic(ContextTopic.USER_ID);

	public boolean getStructure(Map<String, Object> map,
			VariantHolder<Object> data, VariantHolder<String> errMsg)throws RemoteException  {
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		//查询模板
		try{
			String sql=this.getSql();;
			if(StringUtils.isEmpty(this.getSql())){
				SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
				VariantHolder<Object> templateData=new VariantHolder<Object>();
				templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
				if(!templateHome.get(template, templateData, errMsg)){
				}
				RecordSet[] sets=(RecordSet[])templateData.value;
				//获取主表

				Record mainRecord=sets[0].getRecord(0);

				if(!mainRecord.getField("MAIN_SQL").isNull()){
					sql=mainRecord.getField("MAIN_SQL").getString();
				}else{
					sql="SELECT * FROM "+mainRecord.getField("MAIN_TABLE_ID").getString();
				}
			}
			//主表结构
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			st.prepare(sql+ " WHERE 1 <> 1");
			RecordSet mainRecordSet =new  TransientRecordSet();
			logger.debug(st.getJdbcSql());
			RecordSetHelper.loadFromResultSet(st.executeQuery(), mainRecordSet);
			//获取页签表
			data.value=mainRecordSet;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
		return true;
	}


	public boolean get(Map<String, Object> map, Object key,
			VariantHolder<Object> data, VariantHolder<String> errMsg)
	throws RemoteException {
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
			return false;
		}
		//查询模板
		try{
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
				return false;
			}
			RecordSet[] sets=(RecordSet[])templateData.value;
			//获取主表


			Record mainRecord=sets[0].getRecord(0);
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			String sql="";
			if(!mainRecord.getField("MAIN_SQL").isNull()){
				sql=mainRecord.getField("MAIN_SQL").getString();
			}else{
				sql="SELECT * FROM "+mainRecord.getField("MAIN_TABLE_ID").getString();
			}
			StringBuffer whereBuffer=new StringBuffer( " where 1=1 ") ;
			if(key instanceof Map){
				Map<String,Object > keyMap=(Map<String,Object >)key;
				if(keyMap.size()>0){
					for(String key2:keyMap.keySet()){
						whereBuffer.append(" and "+key2+"='"+keyMap.get(key2)+"'");
					}
				}
			}
			//执行主表查询语句
			sql+=whereBuffer.toString();
			if(!mainRecord.getField("WHERE_SQL").isNull()){
				sql+=" "+mainRecord.getField("WHERE_SQL").getString();
			}
			st.prepare(sql);
			RecordSet mainRecordSet =new  TransientRecordSet();
			RecordSetHelper.loadFromResultSet(st.executeQuery(), mainRecordSet);
			data.value=mainRecordSet;
			return mainRecordSet.recordCount()>0;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
	}


	public boolean list(Map<String, Object> map, Object filter,
			VariantHolder<Object> list, VariantHolder<String> errMsg) throws RemoteException{
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		//查询模板
		try{
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
				throw new  RemoteException(errMsg.value);
			}
			RecordSet[] sets=(RecordSet[])templateData.value;
			//获取主表
			Record mainRecord=sets[0].getRecord(0);
			NamedStatement st = new NamedStatement(TxUnit.getConnection());
			String sql="";
			if(!mainRecord.getField("MAIN_SQL").isNull()){
				sql=mainRecord.getField("MAIN_SQL").getString();
			}else{
				sql="SELECT * FROM "+mainRecord.getField("MAIN_TABLE_ID").getString();
			}
			//主表结构
			ProvideHelper.composeSql(st, sql,mainRecord.getField("WHERE_SQL").getString() , mainRecord.getField("GROUP_BY_SQL").getString(),
					mainRecord.getField("SORT_SQL").getString(),""/*默认排序字段，如果没有其他排序，则取当前这个排序*/, filter, null);
			TransientRecordSet set=new TransientRecordSet();
			//测试阶段先打印下sql
			logger.debug(st.getJdbcSql());
			RecordSetHelper.loadFromResultSet(st.executeQuery(), set);
			list.value=set;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
		return true;
	}

	@Override
	public boolean flush(Map<String, Object> map,Object data, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg)
	throws RemoteException {
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		//查询模板
		try{
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
				throw new  RemoteException(errMsg.value);
			}
			RecordSet[] sets=(RecordSet[])templateData.value;
			//获取主表
			Record mainRecord=sets[0].getRecord(0);
			//先保存主表
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table=mainRecord.getField("MAIN_TABLE_ID").getString();

			DeltaRecordSet dataRecordSet=(DeltaRecordSet)data;
			SysInformationHome sysInformation = (SysInformationHome) WaiterFactory
			.getWaiter(SysInformationHome.class);
			Date now = sysInformation.now();
			for(int i=0;i<dataRecordSet.recordCount();i++){
				if(dataRecordSet.getState(i)== DataState.INSERTED){
					if(dataRecordSet.getNewRecord(i).getField("CREATE_ID")!=null){
						dataRecordSet.getNewRecord(i).getField("CREATE_ID").setNumber( userId);
					}
					if(dataRecordSet.getNewRecord(i).getField("CREATE_TIME")!=null){
						dataRecordSet.getNewRecord(i).getField("CREATE_TIME").setTimestamp(now);
					}
					if(dataRecordSet.getNewRecord(i).getField("UPDATE_ID")!=null){
						dataRecordSet.getNewRecord(i).getField("UPDATE_ID").setNumber( userId);
					}
					if(dataRecordSet.getNewRecord(i).getField("UPDATE_TIME")!=null){
						dataRecordSet.getNewRecord(i).getField("UPDATE_TIME").setTimestamp(now);
					}
					if(dataRecordSet.getNewRecord(i).getField("OPR_ID")!=null){
						dataRecordSet.getNewRecord(i).getField("OPR_ID").setNumber( userId);
					}
					if(dataRecordSet.getNewRecord(i).getField("OPR_TIME")!=null){
						dataRecordSet.getNewRecord(i).getField("OPR_TIME").setTimestamp(now);
					}
				}else if(dataRecordSet.getState(i)== DataState.MODIFIED){
					if(dataRecordSet.getNewRecord(i).getField("UPDATE_ID")!=null){
						dataRecordSet.getNewRecord(i).getField("UPDATE_ID").setNumber( userId);
					}
					if(dataRecordSet.getNewRecord(i).getField("UPDATE_TIME")!=null){
						dataRecordSet.getNewRecord(i).getField("UPDATE_TIME").setTimestamp(now);
					}
					if(dataRecordSet.getNewRecord(i).getField("OPR_ID")!=null){
						dataRecordSet.getNewRecord(i).getField("OPR_ID").setNumber( userId);
					}
					if(dataRecordSet.getNewRecord(i).getField("OPR_TIME")!=null){
						dataRecordSet.getNewRecord(i).getField("OPR_TIME").setTimestamp(now);
					}
				}
			}
			resolver.save(dataRecordSet);
			DeltaRecordSet masterBackfill = new DeltaRecordSet();
			RecordSetHelper.saveToDeltaRecordSet(dataRecordSet, masterBackfill);
			dataBackfill.value = masterBackfill;
			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}

}
