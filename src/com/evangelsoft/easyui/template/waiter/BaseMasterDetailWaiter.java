package com.evangelsoft.easyui.template.waiter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.evangelsoft.easyui.template.homeintf.BaseMasterDetailHome;
import com.evangelsoft.easyui.template.homeintf.SysTemplateHome;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.easyui.template.plugin.ActionHelp;
import com.evangelsoft.easyui.type.ActionBeforeAfter;
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
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.workbench.homeintf.SysInformationHome;
import com.evangelsoft.workbench.types.ContextTopic;

public class BaseMasterDetailWaiter implements BaseMasterDetailHome{

	Logger logger=Logger.getLogger(BaseMasterDetailWaiter.class);
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
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
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
			st.prepare(sql+ " WHERE 1 <> 1");
			LinkedHashMap<String, RecordSet> dataMap=new LinkedHashMap<String, RecordSet>();
			RecordSet mainRecordSet =new  TransientRecordSet();
			RecordSetHelper.loadFromResultSet(st.executeQuery(), mainRecordSet);
			dataMap.put(mainRecord.getField("MAIN_TABLE_ID").getString(),mainRecordSet);
			//获取页签表
			Map<String,String> tableMap=new HashMap<String, String>();
			for(int i=0;i<sets[2].recordCount();i++){
				if(!tableMap.containsKey(sets[2].getRecord(i).getField("TABLE_ID").getString())){
					tableMap.put(sets[2].getRecord(i).getField("TABLE_ID").getString(), sets[2].getRecord(i).getField("QUERY_SQL").getString());
					//根据sql查询值
					sql="";
					if(!sets[2].getRecord(i).getField("QUERY_SQL").isNull()){
						sql=sets[2].getRecord(i).getField("QUERY_SQL").getString();
					}else{
						sql="SELECT * FROM "+sets[2].getRecord(i).getField("TABLE_ID").getString();
					}
					st.prepare(sql+" where 1<>1");
					RecordSet detailSet =new  TransientRecordSet();
					RecordSetHelper.loadFromResultSet(st.executeQuery(), detailSet);
					dataMap.put(sets[2].getRecord(i).getField("TABLE_ID").getString(),detailSet);
				}
			}
			data.value=dataMap;
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
		}
		//查询模板
		try{
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
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
//						sql+=" and "+key2+"='"+keyMap.get(key2)+"'";
						whereBuffer.append(" and "+key2+"='"+keyMap.get(key2)+"'");
					}
				}
			}
			//执行主表查询语句
			sql+=whereBuffer.toString();
			if(!mainRecord.getField("WHERE_SQL").isNull()){
				sql+=" "+mainRecord.getField("WHERE_SQL").getString();
			}
			st.prepare(SqlTool.sqlHandle(sql));
			LinkedHashMap<String, RecordSet> dataMap=new LinkedHashMap<String, RecordSet>();
			RecordSet mainRecordSet =new  TransientRecordSet();
			RecordSetHelper.loadFromResultSet(st.executeQuery(), mainRecordSet);
			dataMap.put(mainRecord.getField("MAIN_TABLE_ID").getString(),mainRecordSet);
			//获取页签表
//			Map<String,String> tableMap=new HashMap<String, String>();
			//记录已经查询过的表
			Set<String> set=new HashSet<String>();
			set.add(mainRecord.getField("MAIN_TABLE_ID").getString());
			for(int i=0;i<sets[2].recordCount();i++){
				if(!set.contains(sets[2].getRecord(i).getField("TABLE_ID").getString())){
					set.add(sets[2].getRecord(i).getField("TABLE_ID").getString());
					//根据sql查询值
					sql="";
					if(!sets[2].getRecord(i).getField("QUERY_SQL").isNull()){
						sql=sets[2].getRecord(i).getField("QUERY_SQL").getString()+whereBuffer.toString();
					}else{
						sql="SELECT * FROM "+sets[2].getRecord(i).getField("TABLE_ID").getString()+whereBuffer.toString();
					}
					if(!sets[2].getRecord(i).getField("WHERE_SQL").isNull()){
						String where=sets[2].getRecord(i).getField("WHERE_SQL").getString();
						//将where 条件进行处理，判断是否有需要处理的值
						sql+=" "+where;
						//判断
					}
					st.prepare(SqlTool.sqlHandle(sql));


					RecordSet detailSet =new  TransientRecordSet();
					RecordSetHelper.loadFromResultSet(st.executeQuery(), detailSet);
					dataMap.put(sets[2].getRecord(i).getField("TABLE_ID").getString(),detailSet);
				}
			}
			data.value=dataMap;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			return false;
		}
		return true;
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
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean add(Map<String, Object> map, Object key, Object data,
			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg) throws RemoteException{
		//操作之前
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.BEFORE, data);
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
			Map<String,DeltaRecordSet> dataMap = (Map<String,DeltaRecordSet>)data;
			//先保存主表
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table=mainRecord.getField("MAIN_TABLE_ID").getString();
			DeltaRecordSet maindata = dataMap.get(mainRecord.getField("MAIN_TABLE_ID").getString());
			newKey.value=key;
			SysInformationHome sysInformation = (SysInformationHome) WaiterFactory
			.getWaiter(SysInformationHome.class);
			Date now = sysInformation.now();

			if(maindata.getNewRecord(0).getField("CREATE_ID")!=null){
				maindata.getNewRecord(0).getField("CREATE_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("CREATE_TIME")!=null){
				maindata.getNewRecord(0).getField("CREATE_TIME").setTimestamp(now);
			}
			if(maindata.getNewRecord(0).getField("UPDATE_ID")!=null){
				maindata.getNewRecord(0).getField("UPDATE_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("UPDATE_TIME")!=null){
				maindata.getNewRecord(0).getField("UPDATE_TIME").setTimestamp(now);
			}
			if(maindata.getNewRecord(0).getField("OPR_ID")!=null){
				maindata.getNewRecord(0).getField("OPR_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("OPR_TIME")!=null){
				maindata.getNewRecord(0).getField("OPR_TIME").setTimestamp(now);
			}
			resolver.save(maindata);

			for(String str:dataMap.keySet()){
				if(str!=null&&!str.equals(mainRecord.getField("MAIN_TABLE_ID").getString())){
					resolver.table=str;
					DeltaRecordSet dataRecordSet=dataMap.get(str);


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
						}
					}

					resolver.save(dataRecordSet);
				}
			}

			ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.AFTER, data);

			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}


	}


	public boolean modify(Map<String, Object> map, Object key, Object data,
			VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill,
			VariantHolder<String> errMsg)throws RemoteException {
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.BEFORE, data);

		//查询模板
		try{
			SysTemplateHome templateHome = (SysTemplateHome) WaiterFactory.getWaiter(SysTemplateHome.class);
			VariantHolder<Object> templateData=new VariantHolder<Object>();
			templateData.value=	new RecordSet[]{new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet(),new TransientRecordSet()};
			if(!templateHome.get(template, templateData, errMsg)){
				throw new  RemoteException(errMsg.value);
			}

			SysInformationHome sysInformation = (SysInformationHome) WaiterFactory
			.getWaiter(SysInformationHome.class);
			Date now = sysInformation.now();


			RecordSet[] sets=(RecordSet[])templateData.value;
			//获取主表
			Record mainRecord=sets[0].getRecord(0);
			Map<String,DeltaRecordSet> dataMap = (Map<String,DeltaRecordSet>)data;
			//先保存主表
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();
			resolver.table=mainRecord.getField("MAIN_TABLE_ID").getString();

			DeltaRecordSet maindata=dataMap.get(mainRecord.getField("MAIN_TABLE_ID").getString());

			if(maindata.getNewRecord(0).getField("CREATE_ID")!=null){
				maindata.getNewRecord(0).getField("CREATE_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("CREATE_TIME")!=null){
				maindata.getNewRecord(0).getField("CREATE_TIME").setTimestamp(now);
			}
			if(maindata.getNewRecord(0).getField("UPDATE_ID")!=null){
				maindata.getNewRecord(0).getField("UPDATE_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("UPDATE_TIME")!=null){
				maindata.getNewRecord(0).getField("UPDATE_TIME").setTimestamp(now);
			}
			if(maindata.getNewRecord(0).getField("OPR_ID")!=null){
				maindata.getNewRecord(0).getField("OPR_ID").setNumber( userId);
			}
			if(maindata.getNewRecord(0).getField("OPR_TIME")!=null){
				maindata.getNewRecord(0).getField("OPR_TIME").setTimestamp(now);
			}
			resolver.save(maindata);


			for(String str:dataMap.keySet()){
				if(!str.equals(mainRecord.getField("MAIN_TABLE_ID").getString())){
					resolver.table=str;
					DeltaRecordSet dataRecordSet=dataMap.get(str);

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
				}
			}
			ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.AFTER, data);
		}catch(Exception e){
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
		return true;
	}


	public boolean remove(Map<String, Object> map, Object key,
			VariantHolder<String> errMsg) throws RemoteException {
//		try{
//		// 判断权限。
//		SysUserPaHome sysUserPa = (SysUserPaHome) WaiterFactory.getWaiter(SysUserPaHome.class);

//		ResolveHelper resolver = new ResolveHelper();
//		resolver.db = TxUnit.getConnection();
//		resolver.table = "QUERY_TEMPLATE";
//		resolver.childTables = new String[] { "SYS_TEMPLATE_DETAIL","SYS_TABS","SYS_ATTRIBUTE"};
//		LinkedHashMap<String,Object> linkMap=(LinkedHashMap<String,Object>)key;
//		Object[] objs=new Object[linkMap.size()];
//		int i=0;
//		for(Object obj:linkMap.values()){
//		objs[i]=obj;
//		i++;
//		}
//		resolver.removeByKey(objs);
//		return true;
//		} catch (Exception e) {
//		ExceptionFormat.format(e, errMsg);
//		TxUnit.setRollback();
//		return false;
//		}
		Object template= map.get(BaseMasterDetail.TEMPLATE_ID);
		if(template==null){
			template= map.get(BaseMasterDetail.TEMPLATE_NUM);
		}
		if(template==null){
			errMsg.value="模板参数不允许为空";
		}
		ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.BEFORE, key);
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
			ResolveHelper resolver = new ResolveHelper();
			resolver.db = TxUnit.getConnection();

			String mainTable=mainRecord.getField("MAIN_TABLE_ID").getString();
			resolver.table = mainTable;
			Set<String> tables=new HashSet<String>();
			for(int i=0;i<sets[2].recordCount();i++){
				String str=mainRecord.getField("MAIN_TABLE_ID").getString();
				if(!str.equals(mainRecord.getField("MAIN_TABLE_ID").getString())){
					tables.add(str);
				}
			}
			resolver.childTables=tables.toArray(new String[0]);
			LinkedHashMap<String,Object> keyMap=(LinkedHashMap<String,Object>)key;
			Object[] obj=keyMap.values().toArray();
			resolver.removeByKey(obj);
			ActionHelp.action(template, map.get(BaseMasterDetail.OPERATE_CODE).toString(), ActionBeforeAfter.AFTER, key);
			return true;
		}catch(Exception e){
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}


	}
}
