package com.evangelsoft.easyui.type;

import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordField;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;

/** * @author  杨永钦 E-mail: *
 * @date 创建时间：2016-2-29 下午10:48:32
 * * @version 1.0 * @parameter
 *  * @since
 *  * @return  */
public class FieldRecord {
	public static int TABLE_TYPE=1;
	public static int FIELD_TYPE=2;
	public static RecordFormat format=new RecordFormat("@");
	static{
		format.appendField(new RecordFieldFormat("SYS_TABLE.TABLE_ID"));
		format.appendField(new RecordFieldFormat("SYS_TABLE.TABLE_NAME"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.TEMPLATE_ID"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.TABS_ID"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.COLUMN_ID"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.COLUMN_NAME"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.SEQUENCE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.COL"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.ROW"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DEFAULT_WIDTH"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.CHAR_CASE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.LENGTH"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.PRECISE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.IS_MUST"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.EDIT_IS_MUST"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DEFAULT_VALUE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.VIEW_COLOR"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DATA_TYPE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.VIEW_TYPE"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.VIEW_FORMULA"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.EDITOR_FORMULA"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.VALIDATE_FORMULA"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.IS_TOTAL"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.IS_CARD_SHOW"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.IS_LIST_SHOW"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DEF1"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DEF2"));
		format.appendField(new RecordFieldFormat("SYS_ATTRIBUTE.DEF3"));
	}
	private int type;
	Record record;
	public FieldRecord(Record record,int type){
		this.record=record;
		this.type=type;
	}
	FieldRecord(){
		record=new Record(format);
	}
	public int getType() {
		return type;
	}

	public Record getRecord() {
		return record;
	}
	//提供公用方法供返回
	public RecordField getField(String field){
		return record.getField(field);
	}

	public static FieldRecord[] getFields(RecordSet set,int type){
		FieldRecord[] fields=new FieldRecord[set.recordCount()];
		for(int i=0;i<set.recordCount();i++){
			fields[i]=new FieldRecord(  set.getRecord(i),type);
		}
		return fields;
	}
	//关键是重写toString方法
	@Override
	public String toString() {
		if(type==TABLE_TYPE){
			return record.getField("TABLE_NAME").getString()+"<"+record.getField("TABLE_ID").getString()+">";
		}else{
			return record.getField("COLUMN_NAME").getString()+"<"+record.getField("COLUMN_ID").getString()+">";
		}
	}

}
