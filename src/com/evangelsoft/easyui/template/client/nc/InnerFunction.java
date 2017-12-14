package com.evangelsoft.easyui.template.client.nc;

import java.util.HashMap;
import java.util.Map;

import com.evangelsoft.econnect.dataformat.CodeValue;

/** * @author  ������
 E-mail:
@date ��2016-4-18 ����11:11:50
@version 1.0   * @since    */
public class InnerFunction {
	public final static Map<String,InnerFunction> functionMap=new HashMap<String,InnerFunction>();
	static{
		functionMap.put("ZEROIFNULL",new InnerFunction(IFormulaConstant.FUN_MATH,"zeroifnull","zeroifnull(var)��ʾ���varΪ�ս�����0"));
		functionMap.put("LEFT",new InnerFunction(IFormulaConstant.FUN_STRING,"left","left(st, index) ���ַ���st���ǰindex���ַ���ɵ��ַ���"));
		functionMap.put("ENDWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"endwith","endswith(st, end)�ж��ַ���st�Ƿ����ַ���end��β"));
		functionMap.put("GETENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getenglishcurrency","getEnglishCurrency(mark,number)�����ֽ��תΪӢ���ı�����"));
		functionMap.put("TIME",new InnerFunction(IFormulaConstant.FUN_DATE,"time","time()ȡ�õ�ǰʱ��,��ʽ��HH:mm:SS"));
		functionMap.put("ASS",new InnerFunction(IFormulaConstant.FUN_DB,"ass","ass(freevalueID,checktype)�ǹ��ڻ��ƽ̨�и�������ĺ���,��gl_freevalue���и���freevalueID��checktype����checkvalue"));
//		functionMap.put("RAND",new InnerFunction(,"rand","����ʹ�÷����ľ�������"));
		functionMap.put("CHARAT",new InnerFunction(IFormulaConstant.FUN_STRING,"charat","charat(st,index)�õ��ַ���st�е�index���ַ�"));
		functionMap.put("TOUPPERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"touppercase","toUpperCase(String st) ���ַ���st�Ĵ�д��ʽ"));
		functionMap.put("RIGHT",new InnerFunction(IFormulaConstant.FUN_STRING,"right","right(String st, int index) ���ַ���st�ұ�ǰindex���ַ���ɵ��ַ���"));
		functionMap.put("ATAN",new InnerFunction(IFormulaConstant.FUN_MATH,"atan","atan(x)����һ������x�ķ�����ֵ,����ֵ��-Pi/2��Pi/2֮��"));
		functionMap.put("TOSTRING",new InnerFunction(IFormulaConstant.FUN_STRING,"tostring","toString(obj) ������objת��Ϊ����������ʶ����ַ�����ʽ"));
//		functionMap.put("TANH",new InnerFunction(,"tanh","����ʹ�÷����ľ�������"));
		functionMap.put("TOLOWERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"tolowercase","toLowerCase(String st) ���ַ���st��Сд��ʽ,����toLowerCase(\"Abc\")����\"abc\"."));
		functionMap.put("DATEFORMAT",new InnerFunction(IFormulaConstant.FUN_DATE,"dateformat","dateFormat(date, pattern)���ڽ�ʱ���ʽ��Ϊ�������ַ���,����date������ʱ���ַ���,Ҳ������Date����,patternΪ��ʽ������,yyyy��ʾ��,MM��ʾ��,dd��ʾ����,HH��ʾСʱ,mm��ʾ����,ss��ʾ��.����dateFormat(\"2006-07-04 12:12:12\", \"����:yyyy-MM-dd HH:mm:ss\") ������\"����:2006-07-04 12:12:12\"."));
		functionMap.put("EXP",new InnerFunction(IFormulaConstant.FUN_MATH,"exp","exp(x)e��x�η�"));
		functionMap.put("SIN",new InnerFunction(IFormulaConstant.FUN_MATH,"sin","sin(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("INDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"indexof","indexOf(st1, st2) �ж��ַ���st1�е�һ���ַ���st2���ڵ�λ��,����lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")����3."));
		functionMap.put("DAYWEEKOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweekof","����������������ڼ� Sunday-Monday-Saturday 0-6"));
//		functionMap.put("ACOSH",new InnerFunction(,"acosh","����ʹ�÷����ľ�������"));
		functionMap.put("YEAR",new InnerFunction(IFormulaConstant.FUN_DATE,"year","year()��ǰ��"));
//		functionMap.put("SUM",new InnerFunction(,"sum","����ʹ�÷����ľ�������"));
		functionMap.put("GETCOLSVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolsvalue","fieldname1,fieldname2->getColsValue(\"tablename\",\"fieldname1\",\"fieldname2\",\"pkfield\",pkvalue)�������������ݿ��ѯ����ֶε�ֵ,��ߴ���ֵ���ֶ�Ҫ�ö��ŷָ�,ע��������ֶΣ�����Ҫ��˫������������"));
		functionMap.put("TOTIME",new InnerFunction(IFormulaConstant.FUN_DATE,"totime","toTime(str)���ַ�����ʽ��ʱ��strת����UFTime����"));
		functionMap.put("SUB",new InnerFunction(IFormulaConstant.FUN_MATH,"sub","sub(num1,num2)���ڸ߾��ȼ�������"));
		functionMap.put("LN",new InnerFunction(IFormulaConstant.FUN_MATH,"ln","ln(x)���ظ�����ֵx����Ȼ����"));
		functionMap.put("DATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"datetime","datetime()���ص�ǰ���ں�ʱ��"));
		functionMap.put("LOGINDATE",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"logindate","loginDate()ȡϵͳ��ǰ��½����"));
		functionMap.put("ISEMPTY",new InnerFunction(IFormulaConstant.FUN_STRING,"isempty","isEmpty(����)�����жϱ����Ƿ�Ϊ��,�����մ�(\"\")����ֵ(null)"));
		functionMap.put("GETBLOBCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getblobcolvalue","getBlobColValue(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ����Ϊblob�ֶε�ֵ"));
		functionMap.put("RIGHTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"rightstr","rightStr(st,len,defaultStr) ���ַ���st�ұߺ�len���ַ���ɵ��ַ���������ַ�������С��len������defaultStr����,����rightStr(\"abc\",6,\"@\")������abc@@@."));
		functionMap.put("MIN",new InnerFunction(IFormulaConstant.FUN_MATH,"min","min(x, y) ��x,y�����е���Сֵ"));
		functionMap.put("ACOS",new InnerFunction(IFormulaConstant.FUN_MATH,"acos","acos(x)����һ������x�ķ�����,����ֵ��0��Pi֮��"));
		functionMap.put("GETCOLVALUE2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue2","getColValue2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)�������������ݿ��ѯ�ض��ֶε�ֵ,�书������SQL���:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. ������SQL�����Կ������������ĺ���."));
		functionMap.put("convertDoc",new InnerFunction(IFormulaConstant.FUN_STRING,"convertdoc","�����������"));
		functionMap.put("MON",new InnerFunction(IFormulaConstant.FUN_DATE,"mon","month()��ǰ��"));
		functionMap.put("GETCOLVALUEMORE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemore","getColValueMore(tablename,selectfield,field1,value1,field2,value2....)"));
		functionMap.put("GETCHINESECURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getchinesecurrency","getChineseCurrency(Object)��������ַ���������ת��Ϊ��д���"));
		functionMap.put("MID",new InnerFunction(IFormulaConstant.FUN_STRING,"mid","mid(String st, int start, int end) ���ַ���st���ǰ��start���ַ�����end���ַ�֮����ַ���"));
		functionMap.put("GETSAYENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getsayenglishcurrency","�°汾�����ֽ��ת��ΪӢ�ĺ���, getSayEnglishCurrency(digit, lang-code);digitΪҪת��������,lang-code�м���ѡ�� HKD(�۱�);MOP(����Ԫ);CNY(�����);JPY(��Ԫ);SGD(�¼���Ԫ); USD(��Ԫ);EUR(ŷԪ);THP(̩��);AUD(�Ĵ�����Ԫ);GBP(Ӣ��);CAD(��Ԫ);ת����ĸ�ʽΪgetSayEnglishCurrency(125.25,\"USD\")->SAY US DOLLARS ONE HUNDRED AND TWENTY FIVE AND CENTS TWENTY FIVE ONLY "));
		functionMap.put("MUL",new InnerFunction(IFormulaConstant.FUN_MATH,"mul","mul(num1,num2)���ڸ߾��ȳ˷�����"));
//		functionMap.put("MOD",new InnerFunction(,"mod","����ʹ�÷����ľ�������"));
		functionMap.put("TAN",new InnerFunction(IFormulaConstant.FUN_MATH,"tan","tan(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("STARTSWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"startswith","startsWith(String st, String start) �ж��ַ���st�Ƿ����ַ���start��ͷ"));
		functionMap.put("LOG",new InnerFunction(IFormulaConstant.FUN_MATH,"log","log(x)���ظ�����n����ʮΪ�׵Ķ���"));
//		functionMap.put("COSH",new InnerFunction(,"cosh","����ʹ�÷����ľ�������"));
		functionMap.put("SGN",new InnerFunction(IFormulaConstant.FUN_MATH,"sgn","sgn(num) ����num����0ʱ,����1,����0ʱ,����0,С��0ʱ����-1"));
//		functionMap.put("IIF",new InnerFunction(,"iif","����ʹ�÷����ľ�������"));
		functionMap.put("CVS",new InnerFunction(IFormulaConstant.FUN_DB,"cvs","cvs(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ�ַ���ʹ��"));
		functionMap.put("DATEADD",new InnerFunction(IFormulaConstant.FUN_DATE,"dateadd","dateAdd(date1, num, fieldchar)������ָ�����ڵ��ꡢ�»�����������ĳ��ֵnum,�����ӵ�ʱ����fieldchar����\"Y\"-������;\"M\"-������;\"D\"-������;\"H\"-����Сʱ;\"m\"-���ӷ���;\"S\"-������.����dateAdd(\"23:13:23\", 1, \"H\")��ʾ��ǰ���ʱ������һСʱ."));
		functionMap.put("LOGINUSER",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"loginuser","loginUser(type)ȡϵͳ��ǰ��½�û�,type=0��������,type=1���ر���,type=2��������"));
		functionMap.put("EQUALSIGNORECASE",new InnerFunction(IFormulaConstant.FUN_STRING,"equalsignorecase","equalsIgnoreCase(st1, st2)�жϺ��Դ�Сд�ַ���st1�Ƿ����ַ���st2���"));
		functionMap.put("CVN",new InnerFunction(IFormulaConstant.FUN_DB,"cvn","cvn(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��"));
		functionMap.put("GETCOLNMV2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv2","getColNmv2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��,�书������SQL���:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. ������SQL�����Կ������������ĺ���."));
		functionMap.put("LEFTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"leftstr","leftStr(st,len,defaultStr) ���ַ���st���ǰlen���ַ���ɵ��ַ���������ַ�������С��len������defaultStr����,����leftStr(\"abc\",6,\"@\")������abc@@@."));
		functionMap.put("ADD",new InnerFunction(IFormulaConstant.FUN_MATH,"add","add(num1,num2)���ڸ߾��ȼӷ����� "));
		functionMap.put("DIV",new InnerFunction(IFormulaConstant.FUN_MATH,"div","div(num1,num2)���ڸ߾��ȳ�������"));
		functionMap.put("MAX",new InnerFunction(IFormulaConstant.FUN_MATH,"max","max(x, y) ������x,y�����е����ֵ"));
//		functionMap.put("ASINH",new InnerFunction(,"asinh","����ʹ�÷����ľ�������"));
		functionMap.put("INT",new InnerFunction(IFormulaConstant.FUN_MATH,"int","int(���ֻ����ַ���) ������ת��Ϊint����"));
		functionMap.put("LENGTH",new InnerFunction(IFormulaConstant.FUN_STRING,"length","length(st) ���ַ���st�ĳ���"));
		functionMap.put("LASTINDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"lastindexof","lastIndexOf(st1, st2) �ж��ַ���st1�����һ���ַ���st2���ڵ�λ��,����lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")����11."));
		functionMap.put("TONUMBER",new InnerFunction(IFormulaConstant.FUN_MATH,"tonumber","toNumber(String st) ���ַ���stת��Ϊ����������ʶ�������,����toNumber(\"45.0\")������һ��������45.0,����ת����ɲ��������ֵ����."));
		functionMap.put("COS",new InnerFunction(IFormulaConstant.FUN_MATH,"cos","cos(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("COMPAREDATE",new InnerFunction(IFormulaConstant.FUN_DATE,"comparedate","compareDate(date1, date2, field)�������ڱȽ�,������������ָ��ʱ����Ĳ�ֵ,�ɱȽϵ�ʱ�������\"Y\"-�Ƚ���;\"M\"-�Ƚ���;\"D\"-�Ƚ���;\"H\"-�Ƚ�Сʱ;\"m\"-�ȽϷ���;\"S\"-�Ƚ���.����:compareDate(\"2005-12-27 23:12:10\", toDateTime(\"2005-12-27 23:12:08\"), \"S\")����������������������."));
		functionMap.put("GETLANGRES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getlangres","getlangres(modelname,resID)����ģ��������ԴID��,ȡ����Ӧ�Ķ�������Դ"));
		functionMap.put("TODATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"todatetime","toDateTime(str)���ַ�����ʽ��ʱ��strת����UFDateTime����,����toDateTime(\"2006-10-15 21:01:01\")."));
		functionMap.put("GETCOLVALUEMOREWITHCOND",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemorewithcond","getColValueMoreWithCond(tablename,selectfield,field1,value1,field2,value2...,whereCondition)"));
		functionMap.put("GETCOLNMV",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv","getColNmv(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��,�书������SQL���:select fieldname from tablename where pkfield = pkvalue ������SQL�����Կ������������ĺ���."));
		functionMap.put("DAYWEEK",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweek","���������ڼ� Sunday-Monday-Saturday 0-6"));
		functionMap.put("DATE",new InnerFunction(IFormulaConstant.FUN_DATE,"date","date()���ص�ǰ����"));
		functionMap.put("DEBUG",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"debug","debug(����)���ڴ�ӡ��������ֵ,�Ա���е���"));
		functionMap.put("TOCHINESE",new InnerFunction(IFormulaConstant.FUN_GL,"tochinese","toChinese(Object number,int flag1,int flag2)��������ַ���������ת��Ϊ����"));
//		functionMap.put("IF",new InnerFunction(,"if","����ʹ�÷����ľ�������"));
//		functionMap.put("YEAROF",new InnerFunction(,"yearof","����ʹ�÷����ľ�������"));
		functionMap.put("TOBOOLEAN",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"toboolean","toBoolean(Object st) ������stת��ΪUFBoolean����"));
		functionMap.put("ABS",new InnerFunction(IFormulaConstant.FUN_MATH,"abs","abs(num)����num�ľ���ֵ"));
		functionMap.put("ASIN",new InnerFunction(IFormulaConstant.FUN_MATH,"asin","asin(x)����һ������x�ķ�����,����ֵ��-Pi/2��Pi/2֮��"));
//		functionMap.put("SINH",new InnerFunction(,"sinh","����ʹ�÷����ľ�������"));
		functionMap.put("DAY",new InnerFunction(IFormulaConstant.FUN_DATE,"day","day()��ǰ����"));
//		functionMap.put("ANGLE",new InnerFunction(,"angle","����ʹ�÷����ľ�������"));
//		functionMap.put("ATANH",new InnerFunction(,"atanh","����ʹ�÷����ľ�������"));
		functionMap.put("SETTHMARK",new InnerFunction(IFormulaConstant.FUN_GL,"setthmark","setThMark(String)��������ַ���������תΪ�������ǧ��λ��־,���ϣ���������ֺ����0,����Ҫ�Ƚ�����תΪ�ַ���,Ȼ������setThMark()����,����setThMark(toString(56510.000))."));
		functionMap.put("GETCOLVALUERES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getcolvalueres","getColValueRes(tablename,fieldname,pkfield,pkvalue)��������ȡ�õ������Ͷ���������"));
		functionMap.put("TRIMZERO",new InnerFunction(IFormulaConstant.FUN_STRING,"trimzero","trimZero(var,[decimal])����ĩλ��Ч��0"));
		functionMap.put("SQRT",new InnerFunction(IFormulaConstant.FUN_MATH,"sqrt","sqrt(x)������ֵx��ƽ����"));
		functionMap.put("GETCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue","getColValue(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�书������SQL���:select fieldname from tablename where pkfield = pkvalue ������SQL�����Կ������������ĺ���."));
		functionMap.put("DAYOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayof","dayOf(date)������date������"));
		functionMap.put("MONOF",new InnerFunction(IFormulaConstant.FUN_DATE,"monof","month(date)�õ�ָ�������ڵ��·�"));
		functionMap.put("ROUND",new InnerFunction(IFormulaConstant.FUN_MATH,"round","round(double num, int index) ��num����indexλС��(��������)"));
		functionMap.put("TODATE",new InnerFunction(IFormulaConstant.FUN_DATE,"todate","toDate(str)���ַ�����ʽ��ʱ��strת����UFDate����"));
//		functionMap.put("GETRESULT",new InnerFunction(,"getresult","����ʹ�÷����ľ�������"));
	}

	private int type;//����
	private String formula;//��ʽ
	private String desc;//����
	public InnerFunction(int type, String formula, String desc) {
		super();
		this.type = type;
		this.formula = formula;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	static void init(){
		functionMap.put("ZEROIFNULL",new InnerFunction(IFormulaConstant.FUN_MATH,"zeroifnull","zeroifnull(var)��ʾ���varΪ�ս�����0"));
		functionMap.put("LEFT",new InnerFunction(IFormulaConstant.FUN_STRING,"left","left(st, index) ���ַ���st���ǰindex���ַ���ɵ��ַ���"));
		functionMap.put("ENDWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"endwith","endswith(st, end)�ж��ַ���st�Ƿ����ַ���end��β"));
		functionMap.put("GETENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getenglishcurrency","getEnglishCurrency(mark,number)�����ֽ��תΪӢ���ı�����"));
		functionMap.put("TIME",new InnerFunction(IFormulaConstant.FUN_DATE,"time","time()ȡ�õ�ǰʱ��,��ʽ��HH:mm:SS"));
		functionMap.put("ASS",new InnerFunction(IFormulaConstant.FUN_DB,"ass","ass(freevalueID,checktype)�ǹ��ڻ��ƽ̨�и�������ĺ���,��gl_freevalue���и���freevalueID��checktype����checkvalue"));
//		functionMap.put("RAND",new InnerFunction(,"rand","����ʹ�÷����ľ�������"));
		functionMap.put("CHARAT",new InnerFunction(IFormulaConstant.FUN_STRING,"charat","charat(st,index)�õ��ַ���st�е�index���ַ�"));
		functionMap.put("TOUPPERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"touppercase","toUpperCase(String st) ���ַ���st�Ĵ�д��ʽ"));
		functionMap.put("RIGHT",new InnerFunction(IFormulaConstant.FUN_STRING,"right","right(String st, int index) ���ַ���st�ұ�ǰindex���ַ���ɵ��ַ���"));
		functionMap.put("ATAN",new InnerFunction(IFormulaConstant.FUN_MATH,"atan","atan(x)����һ������x�ķ�����ֵ,����ֵ��-Pi/2��Pi/2֮��"));
		functionMap.put("TOSTRING",new InnerFunction(IFormulaConstant.FUN_STRING,"tostring","toString(obj) ������objת��Ϊ����������ʶ����ַ�����ʽ"));
//		functionMap.put("TANH",new InnerFunction(,"tanh","����ʹ�÷����ľ�������"));
		functionMap.put("TOLOWERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"tolowercase","toLowerCase(String st) ���ַ���st��Сд��ʽ,����toLowerCase(\"Abc\")����\"abc\"."));
		functionMap.put("DATEFORMAT",new InnerFunction(IFormulaConstant.FUN_DATE,"dateformat","dateFormat(date, pattern)���ڽ�ʱ���ʽ��Ϊ�������ַ���,����date������ʱ���ַ���,Ҳ������Date����,patternΪ��ʽ������,yyyy��ʾ��,MM��ʾ��,dd��ʾ����,HH��ʾСʱ,mm��ʾ����,ss��ʾ��.����dateFormat(\"2006-07-04 12:12:12\", \"����:yyyy-MM-dd HH:mm:ss\") ������\"����:2006-07-04 12:12:12\"."));
		functionMap.put("EXP",new InnerFunction(IFormulaConstant.FUN_MATH,"exp","exp(x)e��x�η�"));
		functionMap.put("SIN",new InnerFunction(IFormulaConstant.FUN_MATH,"sin","sin(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("INDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"indexof","indexOf(st1, st2) �ж��ַ���st1�е�һ���ַ���st2���ڵ�λ��,����lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")����3."));
		functionMap.put("DAYWEEKOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweekof","����������������ڼ� Sunday-Monday-Saturday 0-6"));
//		functionMap.put("ACOSH",new InnerFunction(,"acosh","����ʹ�÷����ľ�������"));
		functionMap.put("YEAR",new InnerFunction(IFormulaConstant.FUN_DATE,"year","year()��ǰ��"));
//		functionMap.put("SUM",new InnerFunction(,"sum","����ʹ�÷����ľ�������"));
		functionMap.put("GETCOLSVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolsvalue","fieldname1,fieldname2->getColsValue(\"tablename\",\"fieldname1\",\"fieldname2\",\"pkfield\",pkvalue)�������������ݿ��ѯ����ֶε�ֵ,��ߴ���ֵ���ֶ�Ҫ�ö��ŷָ�,ע��������ֶΣ�����Ҫ��˫������������"));
		functionMap.put("TOTIME",new InnerFunction(IFormulaConstant.FUN_DATE,"totime","toTime(str)���ַ�����ʽ��ʱ��strת����UFTime����"));
		functionMap.put("SUB",new InnerFunction(IFormulaConstant.FUN_MATH,"sub","sub(num1,num2)���ڸ߾��ȼ�������"));
		functionMap.put("LN",new InnerFunction(IFormulaConstant.FUN_MATH,"ln","ln(x)���ظ�����ֵx����Ȼ����"));
		functionMap.put("DATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"datetime","datetime()���ص�ǰ���ں�ʱ��"));
		functionMap.put("LOGINDATE",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"logindate","loginDate()ȡϵͳ��ǰ��½����"));
		functionMap.put("ISEMPTY",new InnerFunction(IFormulaConstant.FUN_STRING,"isempty","isEmpty(����)�����жϱ����Ƿ�Ϊ��,�����մ�(\"\")����ֵ(null)"));
		functionMap.put("GETBLOBCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getblobcolvalue","getBlobColValue(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ����Ϊblob�ֶε�ֵ"));
		functionMap.put("RIGHTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"rightstr","rightStr(st,len,defaultStr) ���ַ���st�ұߺ�len���ַ���ɵ��ַ���������ַ�������С��len������defaultStr����,����rightStr(\"abc\",6,\"@\")������abc@@@."));
		functionMap.put("MIN",new InnerFunction(IFormulaConstant.FUN_MATH,"min","min(x, y) ��x,y�����е���Сֵ"));
		functionMap.put("ACOS",new InnerFunction(IFormulaConstant.FUN_MATH,"acos","acos(x)����һ������x�ķ�����,����ֵ��0��Pi֮��"));
		functionMap.put("GETCOLVALUE2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue2","getColValue2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)�������������ݿ��ѯ�ض��ֶε�ֵ,�书������SQL���:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. ������SQL�����Կ������������ĺ���."));
		functionMap.put("convertDoc",new InnerFunction(IFormulaConstant.FUN_STRING,"convertdoc","�����������"));
		functionMap.put("MON",new InnerFunction(IFormulaConstant.FUN_DATE,"mon","month()��ǰ��"));
		functionMap.put("GETCOLVALUEMORE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemore","getColValueMore(tablename,selectfield,field1,value1,field2,value2....)"));
		functionMap.put("GETCHINESECURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getchinesecurrency","getChineseCurrency(Object)��������ַ���������ת��Ϊ��д���"));
		functionMap.put("MID",new InnerFunction(IFormulaConstant.FUN_STRING,"mid","mid(String st, int start, int end) ���ַ���st���ǰ��start���ַ�����end���ַ�֮����ַ���"));
		functionMap.put("GETSAYENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getsayenglishcurrency","�°汾�����ֽ��ת��ΪӢ�ĺ���, getSayEnglishCurrency(digit, lang-code);digitΪҪת��������,lang-code�м���ѡ�� HKD(�۱�);MOP(����Ԫ);CNY(�����);JPY(��Ԫ);SGD(�¼���Ԫ); USD(��Ԫ);EUR(ŷԪ);THP(̩��);AUD(�Ĵ�����Ԫ);GBP(Ӣ��);CAD(��Ԫ);ת����ĸ�ʽΪgetSayEnglishCurrency(125.25,\"USD\")->SAY US DOLLARS ONE HUNDRED AND TWENTY FIVE AND CENTS TWENTY FIVE ONLY "));
		functionMap.put("MUL",new InnerFunction(IFormulaConstant.FUN_MATH,"mul","mul(num1,num2)���ڸ߾��ȳ˷�����"));
//		functionMap.put("MOD",new InnerFunction(,"mod","����ʹ�÷����ľ�������"));
		functionMap.put("TAN",new InnerFunction(IFormulaConstant.FUN_MATH,"tan","tan(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("STARTSWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"startswith","startsWith(String st, String start) �ж��ַ���st�Ƿ����ַ���start��ͷ"));
		functionMap.put("LOG",new InnerFunction(IFormulaConstant.FUN_MATH,"log","log(x)���ظ�����n����ʮΪ�׵Ķ���"));
//		functionMap.put("COSH",new InnerFunction(,"cosh","����ʹ�÷����ľ�������"));
		functionMap.put("SGN",new InnerFunction(IFormulaConstant.FUN_MATH,"sgn","sgn(num) ����num����0ʱ,����1,����0ʱ,����0,С��0ʱ����-1"));
//		functionMap.put("IIF",new InnerFunction(,"iif","����ʹ�÷����ľ�������"));
		functionMap.put("CVS",new InnerFunction(IFormulaConstant.FUN_DB,"cvs","cvs(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ�ַ���ʹ��"));
		functionMap.put("DATEADD",new InnerFunction(IFormulaConstant.FUN_DATE,"dateadd","dateAdd(date1, num, fieldchar)������ָ�����ڵ��ꡢ�»�����������ĳ��ֵnum,�����ӵ�ʱ����fieldchar����\"Y\"-������;\"M\"-������;\"D\"-������;\"H\"-����Сʱ;\"m\"-���ӷ���;\"S\"-������.����dateAdd(\"23:13:23\", 1, \"H\")��ʾ��ǰ���ʱ������һСʱ."));
		functionMap.put("LOGINUSER",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"loginuser","loginUser(type)ȡϵͳ��ǰ��½�û�,type=0��������,type=1���ر���,type=2��������"));
		functionMap.put("EQUALSIGNORECASE",new InnerFunction(IFormulaConstant.FUN_STRING,"equalsignorecase","equalsIgnoreCase(st1, st2)�жϺ��Դ�Сд�ַ���st1�Ƿ����ַ���st2���"));
		functionMap.put("CVN",new InnerFunction(IFormulaConstant.FUN_DB,"cvn","cvn(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��"));
		functionMap.put("GETCOLNMV2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv2","getColNmv2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��,�书������SQL���:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. ������SQL�����Կ������������ĺ���."));
		functionMap.put("LEFTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"leftstr","leftStr(st,len,defaultStr) ���ַ���st���ǰlen���ַ���ɵ��ַ���������ַ�������С��len������defaultStr����,����leftStr(\"abc\",6,\"@\")������abc@@@."));
		functionMap.put("ADD",new InnerFunction(IFormulaConstant.FUN_MATH,"add","add(num1,num2)���ڸ߾��ȼӷ����� "));
		functionMap.put("DIV",new InnerFunction(IFormulaConstant.FUN_MATH,"div","div(num1,num2)���ڸ߾��ȳ�������"));
		functionMap.put("MAX",new InnerFunction(IFormulaConstant.FUN_MATH,"max","max(x, y) ������x,y�����е����ֵ"));
//		functionMap.put("ASINH",new InnerFunction(,"asinh","����ʹ�÷����ľ�������"));
		functionMap.put("INT",new InnerFunction(IFormulaConstant.FUN_MATH,"int","int(���ֻ����ַ���) ������ת��Ϊint����"));
		functionMap.put("LENGTH",new InnerFunction(IFormulaConstant.FUN_STRING,"length","length(st) ���ַ���st�ĳ���"));
		functionMap.put("LASTINDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"lastindexof","lastIndexOf(st1, st2) �ж��ַ���st1�����һ���ַ���st2���ڵ�λ��,����lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")����11."));
		functionMap.put("TONUMBER",new InnerFunction(IFormulaConstant.FUN_MATH,"tonumber","toNumber(String st) ���ַ���stת��Ϊ����������ʶ�������,����toNumber(\"45.0\")������һ��������45.0,����ת����ɲ��������ֵ����."));
		functionMap.put("COS",new InnerFunction(IFormulaConstant.FUN_MATH,"cos","cos(x)���ظ����Ƕ�x������ֵ"));
		functionMap.put("COMPAREDATE",new InnerFunction(IFormulaConstant.FUN_DATE,"comparedate","compareDate(date1, date2, field)�������ڱȽ�,������������ָ��ʱ����Ĳ�ֵ,�ɱȽϵ�ʱ�������\"Y\"-�Ƚ���;\"M\"-�Ƚ���;\"D\"-�Ƚ���;\"H\"-�Ƚ�Сʱ;\"m\"-�ȽϷ���;\"S\"-�Ƚ���.����:compareDate(\"2005-12-27 23:12:10\", toDateTime(\"2005-12-27 23:12:08\"), \"S\")����������������������."));
		functionMap.put("GETLANGRES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getlangres","getlangres(modelname,resID)����ģ��������ԴID��,ȡ����Ӧ�Ķ�������Դ"));
		functionMap.put("TODATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"todatetime","toDateTime(str)���ַ�����ʽ��ʱ��strת����UFDateTime����,����toDateTime(\"2006-10-15 21:01:01\")."));
		functionMap.put("GETCOLVALUEMOREWITHCOND",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemorewithcond","getColValueMoreWithCond(tablename,selectfield,field1,value1,field2,value2...,whereCondition)"));
		functionMap.put("GETCOLNMV",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv","getColNmv(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�䷵�ص�ֵ��ֱ����Ϊ����ʹ��,�书������SQL���:select fieldname from tablename where pkfield = pkvalue ������SQL�����Կ������������ĺ���."));
		functionMap.put("DAYWEEK",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweek","���������ڼ� Sunday-Monday-Saturday 0-6"));
		functionMap.put("DATE",new InnerFunction(IFormulaConstant.FUN_DATE,"date","date()���ص�ǰ����"));
		functionMap.put("DEBUG",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"debug","debug(����)���ڴ�ӡ��������ֵ,�Ա���е���"));
		functionMap.put("TOCHINESE",new InnerFunction(IFormulaConstant.FUN_GL,"tochinese","toChinese(Object number,int flag1,int flag2)��������ַ���������ת��Ϊ����"));
//		functionMap.put("IF",new InnerFunction(,"if","����ʹ�÷����ľ�������"));
//		functionMap.put("YEAROF",new InnerFunction(,"yearof","����ʹ�÷����ľ�������"));
		functionMap.put("TOBOOLEAN",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"toboolean","toBoolean(Object st) ������stת��ΪUFBoolean����"));
		functionMap.put("ABS",new InnerFunction(IFormulaConstant.FUN_MATH,"abs","abs(num)����num�ľ���ֵ"));
		functionMap.put("ASIN",new InnerFunction(IFormulaConstant.FUN_MATH,"asin","asin(x)����һ������x�ķ�����,����ֵ��-Pi/2��Pi/2֮��"));
//		functionMap.put("SINH",new InnerFunction(,"sinh","����ʹ�÷����ľ�������"));
		functionMap.put("DAY",new InnerFunction(IFormulaConstant.FUN_DATE,"day","day()��ǰ����"));
//		functionMap.put("ANGLE",new InnerFunction(,"angle","����ʹ�÷����ľ�������"));
//		functionMap.put("ATANH",new InnerFunction(,"atanh","����ʹ�÷����ľ�������"));
		functionMap.put("SETTHMARK",new InnerFunction(IFormulaConstant.FUN_GL,"setthmark","setThMark(String)��������ַ���������תΪ�������ǧ��λ��־,���ϣ���������ֺ����0,����Ҫ�Ƚ�����תΪ�ַ���,Ȼ������setThMark()����,����setThMark(toString(56510.000))."));
		functionMap.put("GETCOLVALUERES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getcolvalueres","getColValueRes(tablename,fieldname,pkfield,pkvalue)��������ȡ�õ������Ͷ���������"));
		functionMap.put("TRIMZERO",new InnerFunction(IFormulaConstant.FUN_STRING,"trimzero","trimZero(var,[decimal])����ĩλ��Ч��0"));
		functionMap.put("SQRT",new InnerFunction(IFormulaConstant.FUN_MATH,"sqrt","sqrt(x)������ֵx��ƽ����"));
		functionMap.put("GETCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue","getColValue(tablename,fieldname,pkfield,pkvalue)�������������ݿ��ѯ�ض��ֶε�ֵ,�书������SQL���:select fieldname from tablename where pkfield = pkvalue ������SQL�����Կ������������ĺ���."));
		functionMap.put("DAYOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayof","dayOf(date)������date������"));
		functionMap.put("MONOF",new InnerFunction(IFormulaConstant.FUN_DATE,"monof","month(date)�õ�ָ�������ڵ��·�"));
		functionMap.put("ROUND",new InnerFunction(IFormulaConstant.FUN_MATH,"round","round(double num, int index) ��num����indexλС��(��������)"));
		functionMap.put("TODATE",new InnerFunction(IFormulaConstant.FUN_DATE,"todate","toDate(str)���ַ�����ʽ��ʱ��strת����UFDate����"));
//		functionMap.put("GETRESULT",new InnerFunction(,"getresult","����ʹ�÷����ľ�������"));
	}
	public static void main(String[] args) {
		String xxx="toLowerCase(String st) ���ַ���st��Сд��ʽ,����toLowerCase(\"Abc\")����\"abc\".";
		System.out.println(xxx);
		System.out.println(xxx.replace("\"", "\\\""));
	}
}
