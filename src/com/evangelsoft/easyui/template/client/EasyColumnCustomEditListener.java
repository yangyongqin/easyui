package com.evangelsoft.easyui.template.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.ColumnCustomEditListener;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.type.DefaultValue;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.workbench.config.client.SysCodeSingleFrame;
import com.evangelsoft.workbench.config.client.SysPhraseSingleFrame;
import com.evangelsoft.workbench.config.intf.SysFunction;
import com.evangelsoft.workbench.framebase.MasterDetailFrame;
import com.evangelsoft.workbench.framebase.SingleDataSetFrame;

/** * @author  杨永钦
 E-mail:
@date ：2016-5-8 下午01:53:34
@version 1.0   * @since    */
public class EasyColumnCustomEditListener extends ColumnChangeAdapter implements ColumnCustomEditListener{

	//接收一个万能参数，暂时还不知道咋弄.
	private Record record;
	private  Class<?> cls;//类
	private	String methodName;
	private Method method;//方法
	private String parameter;//参数值
	private Object obj;


	private Record tempRecord;//临时变量

	private HashMap<String,String> keyValueMap=new HashMap<String, String>();
	EasyColumnCustomEditListener(Record record){
		this.record=record;
		//根据值解析
		if(DataSourceConfig.SOURCE_ZDY.equals(record.getField("DATA_SOURCE").getString())){

		}else if(DataSourceConfig.SOURCE_PHRASE.equals(record.getField("DATA_SOURCE").getString())){

		}else if(DataSourceConfig.SOURCE_CODE.equals(record.getField("DATA_SOURCE").getString())){

		}else if(DataSourceConfig.SOURCE_FUNCTION.equals(record.getField("DATA_SOURCE").getString())){
		}
	}
	//点击触发时间，根据参数显示对应的弹窗，先这么写着
	public Variant customEdit(DataSet dataset, Column column) {
		RecordSet set=null;

		if(DataSourceConfig.SOURCE_ZDY.equals(record.getField("DATA_SOURCE").getString())||DataSourceConfig.SOURCE_FUNCTION.equals(record.getField("DATA_SOURCE").getString())){
			try {

				if(cls==null){//为空说明是第一次执行，需要加载运行的class
					if(DataSourceConfig.SOURCE_FUNCTION.equals(record.getField("DATA_SOURCE").getString())){
						SysFunction sysfunc=(SysFunction)(new RMIProxy(Consumer.getDefaultConsumer().getSession()))	.newStub(SysFunction.class);
						VariantHolder<Object> data=new VariantHolder<Object>();
						data.value=new  RecordSet();
						VariantHolder<String> errMsg=new VariantHolder<String>();
						try {
							if(sysfunc.get(record.getField("FUNC_ID").getString(), data, errMsg)){
								RecordSet tempSet=(RecordSet)data.value;
								cls=Class.forName(tempSet.getRecord(0).getField("MODULE").getString());
								if(!StringUtils.isEmpty(  tempSet.getRecord(0).getField("MODULE").getString())){
									methodName=  tempSet.getRecord(0).getField("METHOD").getString();
								}
								if(!StringUtils.isEmpty(  tempSet.getRecord(0).getField("PARM_STR").getString())){
									parameter=  tempSet.getRecord(0).getField("PARM_STR").getString();
								}
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}

					}else{
						cls=Class.forName(record.getField("ZDY_CLASS").getString());
					}
					obj=cls.newInstance();
					if(StringUtils.isNotEmpty(methodName)){
						if(parameter==null){
							method=cls.getMethod(methodName);
							method.invoke(obj);
						}else{
							method=cls.getMethod(methodName,String.class);
							method.invoke(obj, parameter);
						}
					}
				}
				if(obj instanceof MasterDetailFrame){
					set=((MasterDetailFrame)obj).select(null, null, false, true);
				}else if(obj instanceof SingleDataSetFrame){
					set=((SingleDataSetFrame)obj).select(null, null, false, true);
				}else if(obj instanceof UMasterDetailFrame){
					set=((UMasterDetailFrame)obj).select(null, null, false, true);
				}else if(obj instanceof BaseSingleDataSetFrame){
					set=((BaseSingleDataSetFrame)obj).select(null, null, false, true);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(DataSourceConfig.SOURCE_PHRASE.equals(record.getField("DATA_SOURCE").getString())){
			com.evangelsoft.workbench.config.client.SysPhraseSingleFrame  codeFrame=new SysPhraseSingleFrame();
			codeFrame.config(record.getField("PH_TYPE").getString());

//			ConditionTree ct = new ConditionTree();
//			ct.setRoot(new ConditionJointNode("AND"));
//			ConditionLeafNode node = new ConditionLeafNode("CODE", ConditionItem.DATA_TYPE_STRING, ConditionItem.OPERATOR_EQUAL);
//			node.setString(record.getField("PH_TYPE").getString());
//			ct.addChildLast(ct.getRoot(), node);
			set=codeFrame.select(null, null,false, false);
		}else if(DataSourceConfig.SOURCE_CODE .equals(record.getField("DATA_SOURCE").getString())){
			//如果是代码，则弹窗代码窗口
			com.evangelsoft.workbench.config.client.SysCodeSingleFrame  codeFrame=new SysCodeSingleFrame();
			codeFrame.config(record.getField("CODE_TYPE").getString());
			set=codeFrame.select(null, null, false,false);
		}
		/*else if(DataSourceConfig.SOURCE_FUNCTION.equals(record.getField("DATA_SOURCE").getString())){
			//获取代码
			SysFunction sysfunc=(SysFunction)(new RMIProxy(Consumer.getDefaultConsumer().getSession()))	.newStub(SysFunction.class);
			VariantHolder<Object> data=new VariantHolder<Object>();
			data.value=new  RecordSet();
			VariantHolder<String> errMsg=new VariantHolder<String>();
			try {
				sysfunc.get(record.getField("PY_TYPE").getString(), data, errMsg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			try {
				cls=Class.forName(record.getField("MODULE").getString());
				Object obj=cls.newInstance();
				if(obj instanceof MasterDetailFrame){
					set=((MasterDetailFrame)obj).select(null, null, true, false);
				}else if(obj instanceof SingleDataSetFrame){
					set=((SingleDataSetFrame)obj).select(null, null, true, false);
				}else if(obj instanceof UMasterDetailFrame){
					set=((UMasterDetailFrame)obj).select(null, null, true, false);
				}else if(obj instanceof BaseSingleDataSetFrame){
					set=((BaseSingleDataSetFrame)obj).select(null, null, true, false);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}*/
		//获取映射的键值
		if(!record.getField("REF_COLUMNS").isNull()){
			String[] items=record.getField("REF_COLUMNS").getString().split(",");
			for(int i=0;i<items.length;i++){
				String[] item=items[i].split("=");
				keyValueMap.put( item[0],  item[1]);
			}
		}
//		//根据配置的的赋值设置取值
//		if(set!=null&&set.recordCount()>0){
//		Record tempReocr=set.getRecord(0);
//		for(String key:keyValueMap.keySet()){
////		dataset.setObject(keyValueMap.get(key), tempReocr.getField(key).getAsObject());
//		DefaultValue.setObjectValue(dataset,  keyValueMap.get(key)  ,dataset.getColumn(keyValueMap.get(key)).getDataType() ,tempReocr.getField(key).getAsObject());
//		}
//		}
		if(set!=null&&set.recordCount()>0){
			tempRecord=set.getRecord(0);
			Variant v=new Variant();
			if(keyValueMap.get( column.getColumnName())!=null){
				DefaultValue.setObjectValue(v, column.getDataType() ,tempRecord.getField(keyValueMap.get( column.getColumnName())).getAsObject());
			}
			return v;
		}
		return null;
	}
	@Override
	public void changed(DataSet dataset, Column arg1, Variant arg2) {
//		super.changed(arg0, arg1, arg2);

		//获取映射的键值
//		if(!record.getField("REF_COLUMNS").isNull()){
//		String[] items=record.getField("REF_COLUMNS").getString().split(",");
//		for(int i=0;i<items.length;i++){
//		String[] item=items[i].split("=");
//		keyValueMap.put(item[1], item[0]);
//		}
//		}
//		if(tempRecord!=null){
//		for(String key:keyValueMap.keySet()){
//		DefaultValue.setObjectValue(dataset,  keyValueMap.get(key)  ,dataset.getColumn(keyValueMap.get(key)).getDataType() ,tempRecord.getField(key).getAsObject());
//		}
//		}
		//根据配置的的赋值设置取值
//		if(set!=null&&set.recordCount()>0){
//		Record tempReocr=set.getRecord(0);
//		for(String key:keyValueMap.keySet()){
////		dataset.setObject(keyValueMap.get(key), tempReocr.getField(key).getAsObject());
//		DefaultValue.setObjectValue(dataset,  keyValueMap.get(key)  ,dataset.getColumn(keyValueMap.get(key)).getDataType() ,tempReocr.getField(key).getAsObject());
//		}
//		}

	}
	@Override
	public void validate(DataSet dataset, Column arg1, Variant arg2)
	throws Exception, DataSetException {
		super.validate(dataset, arg1, arg2);
		if(!record.getField("REF_COLUMNS").isNull()){
			keyValueMap.clear();
			String[] items=record.getField("REF_COLUMNS").getString().split(",");
			for(int i=0;i<items.length;i++){
				String[] item=items[i].split("=");
				keyValueMap.put(item[1], item[0]);
			}
		}
		if(tempRecord!=null){
			for(String key:keyValueMap.keySet()){
				DefaultValue.setObjectValue(dataset,  keyValueMap.get(key)  ,dataset.getColumn(keyValueMap.get(key)).getDataType() ,tempRecord.getField(key).getAsObject());
			}
		}
	}



}
