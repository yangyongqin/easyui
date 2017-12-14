package com.evangelsoft.easyui.template.client.nc;

import java.util.HashMap;
import java.util.Map;

import com.evangelsoft.econnect.dataformat.CodeValue;

/** * @author  杨永钦
 E-mail:
@date ：2016-4-18 下午11:11:50
@version 1.0   * @since    */
public class InnerFunction {
	public final static Map<String,InnerFunction> functionMap=new HashMap<String,InnerFunction>();
	static{
		functionMap.put("ZEROIFNULL",new InnerFunction(IFormulaConstant.FUN_MATH,"zeroifnull","zeroifnull(var)表示如果var为空将返回0"));
		functionMap.put("LEFT",new InnerFunction(IFormulaConstant.FUN_STRING,"left","left(st, index) 求字符串st左边前index个字符组成的字符串"));
		functionMap.put("ENDWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"endwith","endswith(st, end)判断字符串st是否以字符串end结尾"));
		functionMap.put("GETENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getenglishcurrency","getEnglishCurrency(mark,number)将数字金额转为英文文本描述"));
		functionMap.put("TIME",new InnerFunction(IFormulaConstant.FUN_DATE,"time","time()取得当前时间,格式是HH:mm:SS"));
		functionMap.put("ASS",new InnerFunction(IFormulaConstant.FUN_DB,"ass","ass(freevalueID,checktype)是关于会计平台中辅助核算的函数,从gl_freevalue表中根据freevalueID及checktype返回checkvalue"));
//		functionMap.put("RAND",new InnerFunction(,"rand","函数使用方法的具体描述"));
		functionMap.put("CHARAT",new InnerFunction(IFormulaConstant.FUN_STRING,"charat","charat(st,index)得到字符串st中第index个字符"));
		functionMap.put("TOUPPERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"touppercase","toUpperCase(String st) 求字符串st的大写形式"));
		functionMap.put("RIGHT",new InnerFunction(IFormulaConstant.FUN_STRING,"right","right(String st, int index) 求字符串st右边前index个字符组成的字符串"));
		functionMap.put("ATAN",new InnerFunction(IFormulaConstant.FUN_MATH,"atan","atan(x)返回一个弧度x的反正切值,弧度值在-Pi/2到Pi/2之间"));
		functionMap.put("TOSTRING",new InnerFunction(IFormulaConstant.FUN_STRING,"tostring","toString(obj) 将对象obj转换为本解析器可识别的字符串形式"));
//		functionMap.put("TANH",new InnerFunction(,"tanh","函数使用方法的具体描述"));
		functionMap.put("TOLOWERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"tolowercase","toLowerCase(String st) 求字符串st的小写形式,比如toLowerCase(\"Abc\")返回\"abc\"."));
		functionMap.put("DATEFORMAT",new InnerFunction(IFormulaConstant.FUN_DATE,"dateformat","dateFormat(date, pattern)用于将时间格式化为期望的字符串,其中date可以是时间字符串,也可以是Date对象,pattern为格式化参数,yyyy表示年,MM表示月,dd表示天数,HH表示小时,mm表示分钟,ss表示秒.比如dateFormat(\"2006-07-04 12:12:12\", \"日期:yyyy-MM-dd HH:mm:ss\") 将返回\"日期:2006-07-04 12:12:12\"."));
		functionMap.put("EXP",new InnerFunction(IFormulaConstant.FUN_MATH,"exp","exp(x)e的x次方"));
		functionMap.put("SIN",new InnerFunction(IFormulaConstant.FUN_MATH,"sin","sin(x)返回给定角度x的正弦值"));
		functionMap.put("INDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"indexof","indexOf(st1, st2) 判断字符串st1中第一个字符串st2所在的位置,比如lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")返回3."));
		functionMap.put("DAYWEEKOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweekof","求出给定日期是星期几 Sunday-Monday-Saturday 0-6"));
//		functionMap.put("ACOSH",new InnerFunction(,"acosh","函数使用方法的具体描述"));
		functionMap.put("YEAR",new InnerFunction(IFormulaConstant.FUN_DATE,"year","year()求当前年"));
//		functionMap.put("SUM",new InnerFunction(,"sum","函数使用方法的具体描述"));
		functionMap.put("GETCOLSVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolsvalue","fieldname1,fieldname2->getColsValue(\"tablename\",\"fieldname1\",\"fieldname2\",\"pkfield\",pkvalue)根据主键从数据库查询多个字段的值,左边待赋值的字段要用逗号分割,注意里面的字段，表名要用双引号扩起来。"));
		functionMap.put("TOTIME",new InnerFunction(IFormulaConstant.FUN_DATE,"totime","toTime(str)将字符串格式的时间str转换成UFTime对象"));
		functionMap.put("SUB",new InnerFunction(IFormulaConstant.FUN_MATH,"sub","sub(num1,num2)用于高精度减法运算"));
		functionMap.put("LN",new InnerFunction(IFormulaConstant.FUN_MATH,"ln","ln(x)返回给定数值x的自然对数"));
		functionMap.put("DATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"datetime","datetime()返回当前日期和时间"));
		functionMap.put("LOGINDATE",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"logindate","loginDate()取系统当前登陆日期"));
		functionMap.put("ISEMPTY",new InnerFunction(IFormulaConstant.FUN_STRING,"isempty","isEmpty(变量)用于判断变量是否为空,包括空串(\"\")及空值(null)"));
		functionMap.put("GETBLOBCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getblobcolvalue","getBlobColValue(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询类型为blob字段的值"));
		functionMap.put("RIGHTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"rightstr","rightStr(st,len,defaultStr) 求字符串st右边后len个字符组成的字符串，如果字符串长度小于len，则用defaultStr补齐,比如rightStr(\"abc\",6,\"@\")将返回abc@@@."));
		functionMap.put("MIN",new InnerFunction(IFormulaConstant.FUN_MATH,"min","min(x, y) 求x,y两者中的最小值"));
		functionMap.put("ACOS",new InnerFunction(IFormulaConstant.FUN_MATH,"acos","acos(x)返回一个弧度x的反余弦,弧度值在0到Pi之间"));
		functionMap.put("GETCOLVALUE2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue2","getColValue2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)根据主键从数据库查询特定字段的值,其功能类似SQL语句:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("convertDoc",new InnerFunction(IFormulaConstant.FUN_STRING,"convertdoc","翻译基础档案"));
		functionMap.put("MON",new InnerFunction(IFormulaConstant.FUN_DATE,"mon","month()求当前月"));
		functionMap.put("GETCOLVALUEMORE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemore","getColValueMore(tablename,selectfield,field1,value1,field2,value2....)"));
		functionMap.put("GETCHINESECURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getchinesecurrency","getChineseCurrency(Object)将传入的字符串或数字转换为大写金额"));
		functionMap.put("MID",new InnerFunction(IFormulaConstant.FUN_STRING,"mid","mid(String st, int start, int end) 求字符串st左边前第start个字符至第end个字符之间的字符串"));
		functionMap.put("GETSAYENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getsayenglishcurrency","新版本将数字金额转换为英文函数, getSayEnglishCurrency(digit, lang-code);digit为要转换的数字,lang-code有几种选择 HKD(港币);MOP(澳门元);CNY(人民币);JPY(日元);SGD(新加坡元); USD(美元);EUR(欧元);THP(泰铢);AUD(澳大利亚元);GBP(英镑);CAD(加元);转换后的格式为getSayEnglishCurrency(125.25,\"USD\")->SAY US DOLLARS ONE HUNDRED AND TWENTY FIVE AND CENTS TWENTY FIVE ONLY "));
		functionMap.put("MUL",new InnerFunction(IFormulaConstant.FUN_MATH,"mul","mul(num1,num2)用于高精度乘法运算"));
//		functionMap.put("MOD",new InnerFunction(,"mod","函数使用方法的具体描述"));
		functionMap.put("TAN",new InnerFunction(IFormulaConstant.FUN_MATH,"tan","tan(x)返回给定角度x的正切值"));
		functionMap.put("STARTSWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"startswith","startsWith(String st, String start) 判断字符串st是否以字符串start开头"));
		functionMap.put("LOG",new InnerFunction(IFormulaConstant.FUN_MATH,"log","log(x)返回给定数n的以十为底的对数"));
//		functionMap.put("COSH",new InnerFunction(,"cosh","函数使用方法的具体描述"));
		functionMap.put("SGN",new InnerFunction(IFormulaConstant.FUN_MATH,"sgn","sgn(num) 当数num大于0时,返回1,等于0时,返回0,小于0时返回-1"));
//		functionMap.put("IIF",new InnerFunction(,"iif","函数使用方法的具体描述"));
		functionMap.put("CVS",new InnerFunction(IFormulaConstant.FUN_DB,"cvs","cvs(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为字符串使用"));
		functionMap.put("DATEADD",new InnerFunction(IFormulaConstant.FUN_DATE,"dateadd","dateAdd(date1, num, fieldchar)返回在指定日期的年、月或者日上增加某个值num,可增加的时间域fieldchar包括\"Y\"-增加年;\"M\"-增加月;\"D\"-增加日;\"H\"-增加小时;\"m\"-增加分钟;\"S\"-增加秒.比如dateAdd(\"23:13:23\", 1, \"H\")表示对前面的时间增加一小时."));
		functionMap.put("LOGINUSER",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"loginuser","loginUser(type)取系统当前登陆用户,type=0返回名称,type=1返回编码,type=2返回主键"));
		functionMap.put("EQUALSIGNORECASE",new InnerFunction(IFormulaConstant.FUN_STRING,"equalsignorecase","equalsIgnoreCase(st1, st2)判断忽略大小写字符串st1是否与字符串st2相等"));
		functionMap.put("CVN",new InnerFunction(IFormulaConstant.FUN_DB,"cvn","cvn(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用"));
		functionMap.put("GETCOLNMV2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv2","getColNmv2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用,其功能类似SQL语句:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("LEFTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"leftstr","leftStr(st,len,defaultStr) 求字符串st左边前len个字符组成的字符串，如果字符串长度小于len，则用defaultStr补齐,比如leftStr(\"abc\",6,\"@\")将返回abc@@@."));
		functionMap.put("ADD",new InnerFunction(IFormulaConstant.FUN_MATH,"add","add(num1,num2)用于高精度加法运算 "));
		functionMap.put("DIV",new InnerFunction(IFormulaConstant.FUN_MATH,"div","div(num1,num2)用于高精度除法运算"));
		functionMap.put("MAX",new InnerFunction(IFormulaConstant.FUN_MATH,"max","max(x, y) 求数字x,y两者中的最大值"));
//		functionMap.put("ASINH",new InnerFunction(,"asinh","函数使用方法的具体描述"));
		functionMap.put("INT",new InnerFunction(IFormulaConstant.FUN_MATH,"int","int(数字或者字符串) 将变量转换为int类型"));
		functionMap.put("LENGTH",new InnerFunction(IFormulaConstant.FUN_STRING,"length","length(st) 求字符串st的长度"));
		functionMap.put("LASTINDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"lastindexof","lastIndexOf(st1, st2) 判断字符串st1中最后一个字符串st2所在的位置,比如lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")返回11."));
		functionMap.put("TONUMBER",new InnerFunction(IFormulaConstant.FUN_MATH,"tonumber","toNumber(String st) 将字符串st转换为本解析器可识别的数字,比如toNumber(\"45.0\")将返回一个数字型45.0,经过转化后可参与各种数值计算."));
		functionMap.put("COS",new InnerFunction(IFormulaConstant.FUN_MATH,"cos","cos(x)返回给定角度x的余弦值"));
		functionMap.put("COMPAREDATE",new InnerFunction(IFormulaConstant.FUN_DATE,"comparedate","compareDate(date1, date2, field)用于日期比较,返回两个日期指定时间域的差值,可比较的时间域包括\"Y\"-比较年;\"M\"-比较月;\"D\"-比较日;\"H\"-比较小时;\"m\"-比较分钟;\"S\"-比较秒.比如:compareDate(\"2005-12-27 23:12:10\", toDateTime(\"2005-12-27 23:12:08\"), \"S\")将返回两个日期相差的秒数."));
		functionMap.put("GETLANGRES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getlangres","getlangres(modelname,resID)根据模块名及资源ID号,取得相应的多语言资源"));
		functionMap.put("TODATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"todatetime","toDateTime(str)将字符串格式的时间str转换成UFDateTime对象,比如toDateTime(\"2006-10-15 21:01:01\")."));
		functionMap.put("GETCOLVALUEMOREWITHCOND",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemorewithcond","getColValueMoreWithCond(tablename,selectfield,field1,value1,field2,value2...,whereCondition)"));
		functionMap.put("GETCOLNMV",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv","getColNmv(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用,其功能类似SQL语句:select fieldname from tablename where pkfield = pkvalue 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("DAYWEEK",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweek","求当天是星期几 Sunday-Monday-Saturday 0-6"));
		functionMap.put("DATE",new InnerFunction(IFormulaConstant.FUN_DATE,"date","date()返回当前日期"));
		functionMap.put("DEBUG",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"debug","debug(变量)用于打印出变量的值,以便进行调试"));
		functionMap.put("TOCHINESE",new InnerFunction(IFormulaConstant.FUN_GL,"tochinese","toChinese(Object number,int flag1,int flag2)将传入的字符串或数字转换为中文"));
//		functionMap.put("IF",new InnerFunction(,"if","函数使用方法的具体描述"));
//		functionMap.put("YEAROF",new InnerFunction(,"yearof","函数使用方法的具体描述"));
		functionMap.put("TOBOOLEAN",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"toboolean","toBoolean(Object st) 将对象st转换为UFBoolean类型"));
		functionMap.put("ABS",new InnerFunction(IFormulaConstant.FUN_MATH,"abs","abs(num)求数num的绝对值"));
		functionMap.put("ASIN",new InnerFunction(IFormulaConstant.FUN_MATH,"asin","asin(x)返回一个弧度x的反正弦,弧度值在-Pi/2到Pi/2之间"));
//		functionMap.put("SINH",new InnerFunction(,"sinh","函数使用方法的具体描述"));
		functionMap.put("DAY",new InnerFunction(IFormulaConstant.FUN_DATE,"day","day()求当前天数"));
//		functionMap.put("ANGLE",new InnerFunction(,"angle","函数使用方法的具体描述"));
//		functionMap.put("ATANH",new InnerFunction(,"atanh","函数使用方法的具体描述"));
		functionMap.put("SETTHMARK",new InnerFunction(IFormulaConstant.FUN_GL,"setthmark","setThMark(String)将传入的字符串或数字转为金额后加入千分位标志,如果希望保留数字后面的0,则需要先将数字转为字符创,然后再用setThMark()函数,比如setThMark(toString(56510.000))."));
		functionMap.put("GETCOLVALUERES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getcolvalueres","getColValueRes(tablename,fieldname,pkfield,pkvalue)函数用于取得单据类型多语言名称"));
		functionMap.put("TRIMZERO",new InnerFunction(IFormulaConstant.FUN_STRING,"trimzero","trimZero(var,[decimal])剪除末位无效的0"));
		functionMap.put("SQRT",new InnerFunction(IFormulaConstant.FUN_MATH,"sqrt","sqrt(x)返回数值x的平方根"));
		functionMap.put("GETCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue","getColValue(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其功能类似SQL语句:select fieldname from tablename where pkfield = pkvalue 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("DAYOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayof","dayOf(date)求日期date的天数"));
		functionMap.put("MONOF",new InnerFunction(IFormulaConstant.FUN_DATE,"monof","month(date)得到指定日期内的月份"));
		functionMap.put("ROUND",new InnerFunction(IFormulaConstant.FUN_MATH,"round","round(double num, int index) 对num保留index位小数(四舍五入)"));
		functionMap.put("TODATE",new InnerFunction(IFormulaConstant.FUN_DATE,"todate","toDate(str)将字符串格式的时间str转换成UFDate对象"));
//		functionMap.put("GETRESULT",new InnerFunction(,"getresult","函数使用方法的具体描述"));
	}

	private int type;//类型
	private String formula;//公式
	private String desc;//描述
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
		functionMap.put("ZEROIFNULL",new InnerFunction(IFormulaConstant.FUN_MATH,"zeroifnull","zeroifnull(var)表示如果var为空将返回0"));
		functionMap.put("LEFT",new InnerFunction(IFormulaConstant.FUN_STRING,"left","left(st, index) 求字符串st左边前index个字符组成的字符串"));
		functionMap.put("ENDWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"endwith","endswith(st, end)判断字符串st是否以字符串end结尾"));
		functionMap.put("GETENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getenglishcurrency","getEnglishCurrency(mark,number)将数字金额转为英文文本描述"));
		functionMap.put("TIME",new InnerFunction(IFormulaConstant.FUN_DATE,"time","time()取得当前时间,格式是HH:mm:SS"));
		functionMap.put("ASS",new InnerFunction(IFormulaConstant.FUN_DB,"ass","ass(freevalueID,checktype)是关于会计平台中辅助核算的函数,从gl_freevalue表中根据freevalueID及checktype返回checkvalue"));
//		functionMap.put("RAND",new InnerFunction(,"rand","函数使用方法的具体描述"));
		functionMap.put("CHARAT",new InnerFunction(IFormulaConstant.FUN_STRING,"charat","charat(st,index)得到字符串st中第index个字符"));
		functionMap.put("TOUPPERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"touppercase","toUpperCase(String st) 求字符串st的大写形式"));
		functionMap.put("RIGHT",new InnerFunction(IFormulaConstant.FUN_STRING,"right","right(String st, int index) 求字符串st右边前index个字符组成的字符串"));
		functionMap.put("ATAN",new InnerFunction(IFormulaConstant.FUN_MATH,"atan","atan(x)返回一个弧度x的反正切值,弧度值在-Pi/2到Pi/2之间"));
		functionMap.put("TOSTRING",new InnerFunction(IFormulaConstant.FUN_STRING,"tostring","toString(obj) 将对象obj转换为本解析器可识别的字符串形式"));
//		functionMap.put("TANH",new InnerFunction(,"tanh","函数使用方法的具体描述"));
		functionMap.put("TOLOWERCASE",new InnerFunction(IFormulaConstant.FUN_STRING,"tolowercase","toLowerCase(String st) 求字符串st的小写形式,比如toLowerCase(\"Abc\")返回\"abc\"."));
		functionMap.put("DATEFORMAT",new InnerFunction(IFormulaConstant.FUN_DATE,"dateformat","dateFormat(date, pattern)用于将时间格式化为期望的字符串,其中date可以是时间字符串,也可以是Date对象,pattern为格式化参数,yyyy表示年,MM表示月,dd表示天数,HH表示小时,mm表示分钟,ss表示秒.比如dateFormat(\"2006-07-04 12:12:12\", \"日期:yyyy-MM-dd HH:mm:ss\") 将返回\"日期:2006-07-04 12:12:12\"."));
		functionMap.put("EXP",new InnerFunction(IFormulaConstant.FUN_MATH,"exp","exp(x)e的x次方"));
		functionMap.put("SIN",new InnerFunction(IFormulaConstant.FUN_MATH,"sin","sin(x)返回给定角度x的正弦值"));
		functionMap.put("INDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"indexof","indexOf(st1, st2) 判断字符串st1中第一个字符串st2所在的位置,比如lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")返回3."));
		functionMap.put("DAYWEEKOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweekof","求出给定日期是星期几 Sunday-Monday-Saturday 0-6"));
//		functionMap.put("ACOSH",new InnerFunction(,"acosh","函数使用方法的具体描述"));
		functionMap.put("YEAR",new InnerFunction(IFormulaConstant.FUN_DATE,"year","year()求当前年"));
//		functionMap.put("SUM",new InnerFunction(,"sum","函数使用方法的具体描述"));
		functionMap.put("GETCOLSVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolsvalue","fieldname1,fieldname2->getColsValue(\"tablename\",\"fieldname1\",\"fieldname2\",\"pkfield\",pkvalue)根据主键从数据库查询多个字段的值,左边待赋值的字段要用逗号分割,注意里面的字段，表名要用双引号扩起来。"));
		functionMap.put("TOTIME",new InnerFunction(IFormulaConstant.FUN_DATE,"totime","toTime(str)将字符串格式的时间str转换成UFTime对象"));
		functionMap.put("SUB",new InnerFunction(IFormulaConstant.FUN_MATH,"sub","sub(num1,num2)用于高精度减法运算"));
		functionMap.put("LN",new InnerFunction(IFormulaConstant.FUN_MATH,"ln","ln(x)返回给定数值x的自然对数"));
		functionMap.put("DATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"datetime","datetime()返回当前日期和时间"));
		functionMap.put("LOGINDATE",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"logindate","loginDate()取系统当前登陆日期"));
		functionMap.put("ISEMPTY",new InnerFunction(IFormulaConstant.FUN_STRING,"isempty","isEmpty(变量)用于判断变量是否为空,包括空串(\"\")及空值(null)"));
		functionMap.put("GETBLOBCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getblobcolvalue","getBlobColValue(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询类型为blob字段的值"));
		functionMap.put("RIGHTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"rightstr","rightStr(st,len,defaultStr) 求字符串st右边后len个字符组成的字符串，如果字符串长度小于len，则用defaultStr补齐,比如rightStr(\"abc\",6,\"@\")将返回abc@@@."));
		functionMap.put("MIN",new InnerFunction(IFormulaConstant.FUN_MATH,"min","min(x, y) 求x,y两者中的最小值"));
		functionMap.put("ACOS",new InnerFunction(IFormulaConstant.FUN_MATH,"acos","acos(x)返回一个弧度x的反余弦,弧度值在0到Pi之间"));
		functionMap.put("GETCOLVALUE2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue2","getColValue2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)根据主键从数据库查询特定字段的值,其功能类似SQL语句:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("convertDoc",new InnerFunction(IFormulaConstant.FUN_STRING,"convertdoc","翻译基础档案"));
		functionMap.put("MON",new InnerFunction(IFormulaConstant.FUN_DATE,"mon","month()求当前月"));
		functionMap.put("GETCOLVALUEMORE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemore","getColValueMore(tablename,selectfield,field1,value1,field2,value2....)"));
		functionMap.put("GETCHINESECURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getchinesecurrency","getChineseCurrency(Object)将传入的字符串或数字转换为大写金额"));
		functionMap.put("MID",new InnerFunction(IFormulaConstant.FUN_STRING,"mid","mid(String st, int start, int end) 求字符串st左边前第start个字符至第end个字符之间的字符串"));
		functionMap.put("GETSAYENGLISHCURRENCY",new InnerFunction(IFormulaConstant.FUN_GL,"getsayenglishcurrency","新版本将数字金额转换为英文函数, getSayEnglishCurrency(digit, lang-code);digit为要转换的数字,lang-code有几种选择 HKD(港币);MOP(澳门元);CNY(人民币);JPY(日元);SGD(新加坡元); USD(美元);EUR(欧元);THP(泰铢);AUD(澳大利亚元);GBP(英镑);CAD(加元);转换后的格式为getSayEnglishCurrency(125.25,\"USD\")->SAY US DOLLARS ONE HUNDRED AND TWENTY FIVE AND CENTS TWENTY FIVE ONLY "));
		functionMap.put("MUL",new InnerFunction(IFormulaConstant.FUN_MATH,"mul","mul(num1,num2)用于高精度乘法运算"));
//		functionMap.put("MOD",new InnerFunction(,"mod","函数使用方法的具体描述"));
		functionMap.put("TAN",new InnerFunction(IFormulaConstant.FUN_MATH,"tan","tan(x)返回给定角度x的正切值"));
		functionMap.put("STARTSWITH",new InnerFunction(IFormulaConstant.FUN_STRING,"startswith","startsWith(String st, String start) 判断字符串st是否以字符串start开头"));
		functionMap.put("LOG",new InnerFunction(IFormulaConstant.FUN_MATH,"log","log(x)返回给定数n的以十为底的对数"));
//		functionMap.put("COSH",new InnerFunction(,"cosh","函数使用方法的具体描述"));
		functionMap.put("SGN",new InnerFunction(IFormulaConstant.FUN_MATH,"sgn","sgn(num) 当数num大于0时,返回1,等于0时,返回0,小于0时返回-1"));
//		functionMap.put("IIF",new InnerFunction(,"iif","函数使用方法的具体描述"));
		functionMap.put("CVS",new InnerFunction(IFormulaConstant.FUN_DB,"cvs","cvs(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为字符串使用"));
		functionMap.put("DATEADD",new InnerFunction(IFormulaConstant.FUN_DATE,"dateadd","dateAdd(date1, num, fieldchar)返回在指定日期的年、月或者日上增加某个值num,可增加的时间域fieldchar包括\"Y\"-增加年;\"M\"-增加月;\"D\"-增加日;\"H\"-增加小时;\"m\"-增加分钟;\"S\"-增加秒.比如dateAdd(\"23:13:23\", 1, \"H\")表示对前面的时间增加一小时."));
		functionMap.put("LOGINUSER",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"loginuser","loginUser(type)取系统当前登陆用户,type=0返回名称,type=1返回编码,type=2返回主键"));
		functionMap.put("EQUALSIGNORECASE",new InnerFunction(IFormulaConstant.FUN_STRING,"equalsignorecase","equalsIgnoreCase(st1, st2)判断忽略大小写字符串st1是否与字符串st2相等"));
		functionMap.put("CVN",new InnerFunction(IFormulaConstant.FUN_DB,"cvn","cvn(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用"));
		functionMap.put("GETCOLNMV2",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv2","getColNmv2(tablename,fieldname,pkfield1,pkvalue1,pkfield2,pkvalue2)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用,其功能类似SQL语句:select fieldname from tablename where pkfield1 = pkvalue1 and pkfield2 = pkvalue2. 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("LEFTSTR",new InnerFunction(IFormulaConstant.FUN_STRING,"leftstr","leftStr(st,len,defaultStr) 求字符串st左边前len个字符组成的字符串，如果字符串长度小于len，则用defaultStr补齐,比如leftStr(\"abc\",6,\"@\")将返回abc@@@."));
		functionMap.put("ADD",new InnerFunction(IFormulaConstant.FUN_MATH,"add","add(num1,num2)用于高精度加法运算 "));
		functionMap.put("DIV",new InnerFunction(IFormulaConstant.FUN_MATH,"div","div(num1,num2)用于高精度除法运算"));
		functionMap.put("MAX",new InnerFunction(IFormulaConstant.FUN_MATH,"max","max(x, y) 求数字x,y两者中的最大值"));
//		functionMap.put("ASINH",new InnerFunction(,"asinh","函数使用方法的具体描述"));
		functionMap.put("INT",new InnerFunction(IFormulaConstant.FUN_MATH,"int","int(数字或者字符串) 将变量转换为int类型"));
		functionMap.put("LENGTH",new InnerFunction(IFormulaConstant.FUN_STRING,"length","length(st) 求字符串st的长度"));
		functionMap.put("LASTINDEXOF",new InnerFunction(IFormulaConstant.FUN_STRING,"lastindexof","lastIndexOf(st1, st2) 判断字符串st1中最后一个字符串st2所在的位置,比如lastIndexOf(\"HI,UAP2006,UAP\",\"UAP\")返回11."));
		functionMap.put("TONUMBER",new InnerFunction(IFormulaConstant.FUN_MATH,"tonumber","toNumber(String st) 将字符串st转换为本解析器可识别的数字,比如toNumber(\"45.0\")将返回一个数字型45.0,经过转化后可参与各种数值计算."));
		functionMap.put("COS",new InnerFunction(IFormulaConstant.FUN_MATH,"cos","cos(x)返回给定角度x的余弦值"));
		functionMap.put("COMPAREDATE",new InnerFunction(IFormulaConstant.FUN_DATE,"comparedate","compareDate(date1, date2, field)用于日期比较,返回两个日期指定时间域的差值,可比较的时间域包括\"Y\"-比较年;\"M\"-比较月;\"D\"-比较日;\"H\"-比较小时;\"m\"-比较分钟;\"S\"-比较秒.比如:compareDate(\"2005-12-27 23:12:10\", toDateTime(\"2005-12-27 23:12:08\"), \"S\")将返回两个日期相差的秒数."));
		functionMap.put("GETLANGRES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getlangres","getlangres(modelname,resID)根据模块名及资源ID号,取得相应的多语言资源"));
		functionMap.put("TODATETIME",new InnerFunction(IFormulaConstant.FUN_DATE,"todatetime","toDateTime(str)将字符串格式的时间str转换成UFDateTime对象,比如toDateTime(\"2006-10-15 21:01:01\")."));
		functionMap.put("GETCOLVALUEMOREWITHCOND",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvaluemorewithcond","getColValueMoreWithCond(tablename,selectfield,field1,value1,field2,value2...,whereCondition)"));
		functionMap.put("GETCOLNMV",new InnerFunction(IFormulaConstant.FUN_DB,"getcolnmv","getColNmv(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其返回的值将直接作为数字使用,其功能类似SQL语句:select fieldname from tablename where pkfield = pkvalue 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("DAYWEEK",new InnerFunction(IFormulaConstant.FUN_DATE,"dayweek","求当天是星期几 Sunday-Monday-Saturday 0-6"));
		functionMap.put("DATE",new InnerFunction(IFormulaConstant.FUN_DATE,"date","date()返回当前日期"));
		functionMap.put("DEBUG",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"debug","debug(变量)用于打印出变量的值,以便进行调试"));
		functionMap.put("TOCHINESE",new InnerFunction(IFormulaConstant.FUN_GL,"tochinese","toChinese(Object number,int flag1,int flag2)将传入的字符串或数字转换为中文"));
//		functionMap.put("IF",new InnerFunction(,"if","函数使用方法的具体描述"));
//		functionMap.put("YEAROF",new InnerFunction(,"yearof","函数使用方法的具体描述"));
		functionMap.put("TOBOOLEAN",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"toboolean","toBoolean(Object st) 将对象st转换为UFBoolean类型"));
		functionMap.put("ABS",new InnerFunction(IFormulaConstant.FUN_MATH,"abs","abs(num)求数num的绝对值"));
		functionMap.put("ASIN",new InnerFunction(IFormulaConstant.FUN_MATH,"asin","asin(x)返回一个弧度x的反正弦,弧度值在-Pi/2到Pi/2之间"));
//		functionMap.put("SINH",new InnerFunction(,"sinh","函数使用方法的具体描述"));
		functionMap.put("DAY",new InnerFunction(IFormulaConstant.FUN_DATE,"day","day()求当前天数"));
//		functionMap.put("ANGLE",new InnerFunction(,"angle","函数使用方法的具体描述"));
//		functionMap.put("ATANH",new InnerFunction(,"atanh","函数使用方法的具体描述"));
		functionMap.put("SETTHMARK",new InnerFunction(IFormulaConstant.FUN_GL,"setthmark","setThMark(String)将传入的字符串或数字转为金额后加入千分位标志,如果希望保留数字后面的0,则需要先将数字转为字符创,然后再用setThMark()函数,比如setThMark(toString(56510.000))."));
		functionMap.put("GETCOLVALUERES",new InnerFunction(IFormulaConstant.FUN_CUSTOM,"getcolvalueres","getColValueRes(tablename,fieldname,pkfield,pkvalue)函数用于取得单据类型多语言名称"));
		functionMap.put("TRIMZERO",new InnerFunction(IFormulaConstant.FUN_STRING,"trimzero","trimZero(var,[decimal])剪除末位无效的0"));
		functionMap.put("SQRT",new InnerFunction(IFormulaConstant.FUN_MATH,"sqrt","sqrt(x)返回数值x的平方根"));
		functionMap.put("GETCOLVALUE",new InnerFunction(IFormulaConstant.FUN_DB,"getcolvalue","getColValue(tablename,fieldname,pkfield,pkvalue)根据主键从数据库查询特定字段的值,其功能类似SQL语句:select fieldname from tablename where pkfield = pkvalue 从这条SQL语句可以看出各个参数的含义."));
		functionMap.put("DAYOF",new InnerFunction(IFormulaConstant.FUN_DATE,"dayof","dayOf(date)求日期date的天数"));
		functionMap.put("MONOF",new InnerFunction(IFormulaConstant.FUN_DATE,"monof","month(date)得到指定日期内的月份"));
		functionMap.put("ROUND",new InnerFunction(IFormulaConstant.FUN_MATH,"round","round(double num, int index) 对num保留index位小数(四舍五入)"));
		functionMap.put("TODATE",new InnerFunction(IFormulaConstant.FUN_DATE,"todate","toDate(str)将字符串格式的时间str转换成UFDate对象"));
//		functionMap.put("GETRESULT",new InnerFunction(,"getresult","函数使用方法的具体描述"));
	}
	public static void main(String[] args) {
		String xxx="toLowerCase(String st) 求字符串st的小写形式,比如toLowerCase(\"Abc\")返回\"abc\".";
		System.out.println(xxx);
		System.out.println(xxx.replace("\"", "\\\""));
	}
}
