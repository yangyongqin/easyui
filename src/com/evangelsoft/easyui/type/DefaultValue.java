package com.evangelsoft.easyui.type;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.crosslink.types.EnvironmentTopic;
import com.evangelsoft.easyui.config.intf.SysRefNumberExt;
import com.evangelsoft.easyui.tool.ClientVariableParser;
import com.evangelsoft.easyui.tool.DateUtil;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.workbench.config.client.SysParameterHelper;

/** * @author  杨永钦
 E-mail:
@date ：2016-6-10 下午03:58:32
@version 1.0   * @since    */
public class DefaultValue {
	public static String SOURCE_CONSTANT="CONSTANT";//常量
	public static String SOURCE_VARIABLE="VARIABLE";//参数
	public static String SOURCE_PARAMETER="PARAMETER";//参数
	public static String SOURCE_SEQUENCE="SEQUENCE";//序号，编号
	public static String  SOURCE_ZB="ZB";//主表

	private String type;//默认值类别
	private String defValue;//默认值公司
	private DataSet dataSet;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public DefaultValue(String type, String defValue,DataSet dataSet) {
		super();
		this.type = type;
		this.defValue = defValue;
		this.dataSet=dataSet;
	}
	public Object getValue()throws Exception{
		if(SOURCE_CONSTANT.equals(type)){
			return defValue;
		}else if(SOURCE_VARIABLE.equals(type)){
			//如果是变量
			return ClientVariableParser.getObject(defValue);
		}else if(SOURCE_PARAMETER.equals(type)){
			//如果是参数，调用前端取参数的方法
			return SysParameterHelper.getValue("defValue");
		}else if(SOURCE_SEQUENCE.equals(type)){
			//如果是用编号
			SysRefNumberExt refNumber=(SysRefNumberExt)(new RMIProxy(Consumer.getDefaultConsumer().getSession())).newStub(SysRefNumberExt.class);
			return refNumber.fetch(defValue,(BigDecimal) Consumer.getDefaultConsumer().getEnv(EnvironmentTopic.OWNER_ID), 1);
		}else if(SOURCE_ZB.equals(type)){
			return getDataSetValue(dataSet,defValue);
		}
		return null;
	}
	public static Object getValue(String type,String defValue,DataSet dataSet)throws Exception{
		if(SOURCE_CONSTANT.equals(type)){
			return defValue;
		}else if(SOURCE_VARIABLE.equals(type)){
			//如果是变量
			return ClientVariableParser.getObject(defValue);
		}else if(SOURCE_PARAMETER.equals(type)){
			//如果是参数，调用前端取参数的方法
			return SysParameterHelper.getValue("defValue");
		}else if(SOURCE_SEQUENCE.equals(type)){
			//如果是用编号
			SysRefNumberExt refNumber=(SysRefNumberExt)(new RMIProxy(Consumer.getDefaultConsumer().getSession())).newStub(SysRefNumberExt.class);
			return refNumber.fetch(defValue,(BigDecimal) Consumer.getDefaultConsumer().getEnv(EnvironmentTopic.OWNER_ID), 1);
		}else if(SOURCE_ZB.equals(type)){
			return getDataSetValue(dataSet,defValue);
		}
		return null;
	}
	public static Object getDataSetValue(DataSet dataset ,String columnId){
		if(dataset==null||columnId==null){
			return null;
		}
		int type= dataset.getColumn(columnId).getDataType();
		if(type==Variant.BYTE){
			return dataset.getByte(columnId);
		}else if(type==Variant.SHORT){
			return	dataset.getShort(columnId);
		}else if(type==Variant.INT){
			return	dataset.getInt(columnId);
		}else if(type==Variant.LONG){
			return	dataset.getInt(columnId);
		}else if(type==Variant.FLOAT){
			return	dataset.getInt(columnId);
		}else if(type==Variant.DOUBLE){
			return	dataset.getInt(columnId);
		}else if(type==Variant.BIGDECIMAL){
			return	dataset.getBigDecimal(columnId);

		}else if(type==Variant.BOOLEAN){
			return	dataset.getBoolean(columnId);

		}else if(type==Variant.INPUTSTREAM){
			return	dataset.getInputStream(columnId);

		}else if(type==Variant.DATE){
			return	dataset.getDate(columnId);

		}else if(type==Variant.TIME){
			return	dataset.getTime(columnId);

		}else if(type==Variant.TIMESTAMP){
			return	dataset.getTimestamp(columnId);

		}else if(type==Variant.STRING){
			return	dataset.getString(columnId);
		}else if(type==Variant.OBJECT){
			return	dataset.getObject(columnId);
		}else if(type==Variant.BYTE_ARRAY){
			return	dataset.getArrayLength(columnId);
		}
		return  null;
	}
	public static void setObjectValue(DataSet dataSet,String colId, int type ,Object value){
		if(value==null){
			return;
		}
		if(type==Variant.BYTE){
			//			return objectToByte(value);
			dataSet.setByte(colId,(Byte) value);
		}else if(type==Variant.SHORT){
			dataSet.setShort(colId, Short.parseShort(value.toString()));
		}else if(type==Variant.INT){
			dataSet.setInt(colId, Integer.parseInt(value.toString()));

		}else if(type==Variant.LONG){
			//			return	Long.parseLong(value.toString());
			dataSet.setLong(colId, Long.parseLong(value.toString()));

		}else if(type==Variant.FLOAT){
			dataSet.setFloat(colId, Float.parseFloat(value.toString()));

		}else if(type==Variant.DOUBLE){
			dataSet.setDouble(colId, Double.parseDouble(value.toString()));
		}else if(type==Variant.BIGDECIMAL){
			if(value instanceof BigDecimal){
				dataSet.setBigDecimal(colId, (BigDecimal)value);
			}
//			dataSet.setBigDecimal(colId, new BigDecimal(value.toString()));


		}else if(type==Variant.BOOLEAN){
			//暂时先不管
		}else if(type==Variant.INPUTSTREAM){
			//暂时先不管
		}else if(type==Variant.DATE){
			if(value instanceof Date){

				dataSet.setDate(colId, 	new java.sql.Date(((Date)value).getTime()));
			}else if(value instanceof java.sql.Date){
				dataSet.setDate(colId, 	(java.sql.Date)value);
			}else if(value instanceof String){
				try {
					dataSet.setDate(colId, new java.sql.Date( DateUtil.str2Date("yyyyMMdd",value.toString()).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIME){
			if(value instanceof Date){
				//				return value;
				dataSet.setTime(colId, 	new java.sql.Time(((Date)value).getTime()));

			}else if(value instanceof java.sql.Date){
				dataSet.setTime(colId, 	new java.sql.Time(((java.sql.Date)value).getTime()));
			}else if(value instanceof java.sql.Time){
				dataSet.setTime(colId, 	(java.sql.Time)value);
			}
			else if(value instanceof String){
				try {
					dataSet.setTime(colId, 	new java.sql.Time((DateUtil.str2Date("HHmmss",value.toString())).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIMESTAMP){
			if(value instanceof Date){
				dataSet.setTimestamp(colId, new Timestamp(((Date)value).getTime()));
			}else if(value instanceof java.sql.Date){
				java.sql.Date oldDate=(java.sql.Date)value;
				dataSet.setTimestamp(colId, new Timestamp(oldDate.getTime()));
			}else if(value instanceof String){
				try {
					dataSet.setTimestamp(colId, DateUtil.str2Date("yyyYMMddHHmmss",value.toString()).getTime());

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.STRING){
			dataSet.setString(colId, 	value.toString());
		}else if(type==Variant.OBJECT){
			dataSet.setObject(colId, 	value);
		}else if(type==Variant.BYTE_ARRAY){
			dataSet.setByteArray(colId, objectToByte(value), 0);
		}
	}

	public static Object parseValue(int type ,Object value){
		if(type==Variant.BYTE){
			return objectToByte(value);
		}else if(type==Variant.SHORT){
			return	Short.parseShort(value.toString());
		}else if(type==Variant.INT){
			return	Integer.parseInt(value.toString());
		}else if(type==Variant.LONG){
			return	Long.parseLong(value.toString());
		}else if(type==Variant.FLOAT){
			return	Float.parseFloat(value.toString());
		}else if(type==Variant.DOUBLE){
			return	Float.parseFloat(value.toString());
		}else if(type==Variant.BIGDECIMAL){
			if(value instanceof BigDecimal){
				return	value;
			}
			return	new BigDecimal(value.toString());

		}else if(type==Variant.BOOLEAN){
			//暂时先不管
		}else if(type==Variant.INPUTSTREAM){
			//暂时先不管
		}else if(type==Variant.DATE){
			if(value instanceof Date){
				return value;
			}else if(value instanceof java.sql.Date){
				java.sql.Date oldDate=(java.sql.Date)value;
				return new Date( oldDate.getTime());
			}else if(value instanceof String){
				try {
					return DateUtil.str2Date("yyyyMMdd",value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIME){
			if(value instanceof Date){
				return value;
			}else if(value instanceof java.sql.Date){
				java.sql.Date oldDate=(java.sql.Date)value;
				return new Date( oldDate.getTime());
			}else if(value instanceof String){
				try {
					return DateUtil.str2Date("HHmmss",value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIMESTAMP){
			if(value instanceof Date){
				return ((Date)value).getTime();
			}else if(value instanceof java.sql.Date){
				java.sql.Date oldDate=(java.sql.Date)value;
				return  oldDate.getTime();
			}else if(value instanceof String){
				try {
					return DateUtil.str2Date("yyyYMMddHHmmss",value.toString()).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.STRING){
			return value.toString();
		}else if(type==Variant.OBJECT){
			return value;
		}else if(type==Variant.BYTE_ARRAY){
			return objectToByte(value);
		}
		return  null;
	}



	public static void setObjectValue(Variant dataSet, int type ,Object value){
		if(type==Variant.BYTE){
			//			return objectToByte(value);
			dataSet.setByte((Byte) value);
		}else if(type==Variant.SHORT){
			dataSet.setShort(Short.parseShort(value.toString()));
		}else if(type==Variant.INT){
			dataSet.setInt( Integer.parseInt(value.toString()));

		}else if(type==Variant.LONG){
			//			return	Long.parseLong(value.toString());
			dataSet.setLong( Long.parseLong(value.toString()));

		}else if(type==Variant.FLOAT){
			dataSet.setFloat( Float.parseFloat(value.toString()));

		}else if(type==Variant.DOUBLE){
			dataSet.setDouble(Double.parseDouble(value.toString()));
		}else if(type==Variant.BIGDECIMAL){
			if(value instanceof BigDecimal){
				dataSet.setBigDecimal((BigDecimal)value);
			}
//			dataSet.setBigDecimal(colId, new BigDecimal(value.toString()));


		}else if(type==Variant.BOOLEAN){
			//暂时先不管
		}else if(type==Variant.INPUTSTREAM){
			//暂时先不管
		}else if(type==Variant.DATE){
			if(value instanceof Date){

				dataSet.setDate(	new java.sql.Date(((Date)value).getTime()));
			}else if(value instanceof java.sql.Date){
				dataSet.setDate( 	(java.sql.Date)value);
			}else if(value instanceof String){
				try {
					dataSet.setDate(new java.sql.Date( DateUtil.str2Date("yyyyMMdd",value.toString()).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIME){
			if(value instanceof Date){
				//				return value;
				dataSet.setTime( 	new java.sql.Time(((Date)value).getTime()));

			}else if(value instanceof java.sql.Date){
				dataSet.setTime(	new java.sql.Time(((java.sql.Date)value).getTime()));
			}else if(value instanceof java.sql.Time){
				dataSet.setTime(	(java.sql.Time)value);
			}
			else if(value instanceof String){
				try {
					dataSet.setTime( 	new java.sql.Time((DateUtil.str2Date("HHmmss",value.toString())).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.TIMESTAMP){
			if(value instanceof Date){
				dataSet.setTimestamp( new Timestamp(((Date)value).getTime()));
			}else if(value instanceof java.sql.Date){
				java.sql.Date oldDate=(java.sql.Date)value;
				dataSet.setTimestamp(new Timestamp(oldDate.getTime()));
			}else if(value instanceof String){
				try {
					dataSet.setTimestamp( DateUtil.str2Date("yyyYMMddHHmmss",value.toString()).getTime());

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type==Variant.STRING){
			dataSet.setString( 	value.toString());
		}else if(type==Variant.OBJECT){
			dataSet.setObject(	value);
		}else if(type==Variant.BYTE_ARRAY){
			dataSet.setByteArray( objectToByte(value), 0);
		}
	}




	public static byte[] objectToByte(java.lang.Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}


}
