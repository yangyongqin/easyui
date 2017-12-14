package com.evangelsoft.easyui.tool;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.evangelsoft.crosslink.product.security.homeintf.UserBrandHome;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.workbench.security.homeintf.SysUserPaHome;

/**
 * 解析数据类型
 * */
public class VariableParser
{

	protected static Object getObject(String s, String s1)
	{
		Object s2=null;
		if (s.equalsIgnoreCase("PRIV_UNIT_LIST"))
			try
			{
				String s3 = s1;
				SysUserPaHome sysuserpahome = (SysUserPaHome)WaiterFactory.getWaiter(SysUserPaHome.class);
				Object aobj[] = {
					WaiterFactory.getSession().getContext().getTopic("USER_ID"), WaiterFactory.getSession().getContext().getTopic("OWNER_ID")
				};
				VariantHolder<String> variantholder = new VariantHolder<String>();
				VariantHolder<Boolean> variantholder1 = new VariantHolder<Boolean>();
				VariantHolder<Object> variantholder2 = new VariantHolder<Object>();
				if (!sysuserpahome.examine(((Object) (aobj)), s3, variantholder1, variantholder2, variantholder))
					throw new Exception((String)variantholder.value);
				if (((Boolean)variantholder1.value).booleanValue())
				{
					s2 = "";
				} else
				{
					BigDecimal abigdecimal[] = (BigDecimal[])variantholder2.value;
					if (abigdecimal.length == 0)
					{
						s2 = "#";
					} else
					{
						StringBuffer stringbuffer = new StringBuffer();
						for (int k2 = 0; k2 < abigdecimal.length; k2++)
						{
							if (k2 > 0)
								stringbuffer.append(';');
							stringbuffer.append(abigdecimal[k2]);
						}

						s2 = stringbuffer.toString();
					}
				}
			}
			catch (Exception exception)
			{
				throw new RuntimeException(exception.getMessage());
			}
		else if (s.equals("OWNER_ID"))
			s2 = ((BigDecimal)WaiterFactory.getSession().getContext().getTopic("OWNER_ID"));
		else
			if (s.equals("OWNER_NAME"))
				s2 = ((String)WaiterFactory.getSession().getContext().getTopic("OWNER_NAME")).toString();
		else
		if (s.equals("USER_ID"))
			s2 = ((BigDecimal)WaiterFactory.getSession().getContext().getTopic("USER_ID"));
		else
			if (s.equals("USER_NAME"))
				s2 = ((String)WaiterFactory.getSession().getContext().getTopic("USER_NAME")).toString();
		else
		if (s.substring(0, 4).equals("DATE"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			if(s.length()==4){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>4){
				if(s.substring(4, 5).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.DATE, + Integer.parseInt(s3));
//		             s2 = sf.format(cal.getTime());
		             try {
						s2=  sf.parseObject(sf.format(cal.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else if(s.substring(4, 5).equals("-")){
					 String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.DATE, - Integer.parseInt(s3));
//		             s2 = sf.format(cal.getTime());
		             try {
						s2=  sf.parseObject(sf.format(cal.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}else
		if (s.equals("TIME"))
		{
			SimpleDateFormat simpledateformat1 = new SimpleDateFormat("HHmmss");
//			s2 = simpledateformat1.format(new Date());
			try {
				s2=  simpledateformat1.parseObject(simpledateformat1.format(new Date()));
			} catch (ParseException e) {
			}
		} else
		if (s.equals("NOW"))
		{
//			SimpleDateFormat simpledateformat2 = new SimpleDateFormat("yyyyMMddHHmmss");
//			s2 = simpledateformat2.format(new Date());
			s2=new Timestamp(Calendar.getInstance().getTimeInMillis());
		} else
		if (s.substring(0, 4).equals("YEAR"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			if(s.length()==4){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>4){
				if(s.substring(4, 5).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.YEAR, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(4, 5).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		            cal.add(Calendar.YEAR, - Integer.parseInt(s3));
		            s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.substring(0, 5).equals("MONTH"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("MM");
			if(s.length()==5){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>5){
				if(s.substring(5, 6).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.MONTH, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(5, 6).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.MONTH, - Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.substring(0, 3).equals("DAY"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("dd");
			if(s.length()==3){
				s2 = sf.format(cal.getTime());
				System.out.println(s2);
			}else if(s.length()>3){
				if(s.substring(3, 4).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.DAY_OF_MONTH, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(3, 4).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.DAY_OF_MONTH, - Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.equals("FIRST_DAY_OF_YEAR"))
		{
			Calendar calendar = Calendar.getInstance();
			int i = calendar.get(1);
			calendar.clear();
			calendar.set(1, i);
			calendar.set(2, 0);
			calendar.set(5, 1);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.MILLISECOND,0);
			s2=calendar.getTime();
		} else
		if (s.equals("LAST_DAY_OF_YEAR"))
		{
			Calendar calendar1 = Calendar.getInstance();
			int j = calendar1.get(1);
			calendar1.clear();
			calendar1.set(1, j + 1);
			calendar1.set(2, 0);
			calendar1.set(5, 1);
			calendar1.add(5, -1);
			calendar1.set(Calendar.HOUR, 0);
			calendar1.set(Calendar.MINUTE, 0);
			calendar1.set(Calendar.SECOND,0);
			calendar1.set(Calendar.MILLISECOND,0);
//			SimpleDateFormat simpledateformat4 = new SimpleDateFormat("yyyyMMdd");
//			s2 = simpledateformat4.format(calendar1.getTime());
			s2=calendar1.getTime();
		} else
		if (s.equals("FIRST_DAY_OF_MONTH"))
		{
			Calendar calendar2 = Calendar.getInstance();
			int k = calendar2.get(1);
			int k1 = calendar2.get(2);
			calendar2.clear();
			calendar2.set(1, k);
			calendar2.set(2, k1);
			calendar2.set(5, 1);
			calendar2.set(Calendar.HOUR, 0);
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND,0);
			calendar2.set(Calendar.MILLISECOND,0);
//			SimpleDateFormat simpledateformat5 = new SimpleDateFormat("yyyyMMdd");
//			s2 = simpledateformat5.format(calendar2.getTime());
			s2=calendar2.getTime();
		} else
		if (s.equals("LAST_DAY_OF_MONTH"))
		{
			Calendar calendar3 = Calendar.getInstance();
			int l = calendar3.get(1);
			int l1 = calendar3.get(2);
			calendar3.clear();
			calendar3.set(1, l);
			calendar3.set(2, l1);
			calendar3.set(5, 1);
			calendar3.add(2, 1);
			calendar3.add(5, -1);
			calendar3.set(Calendar.HOUR, 0);
			calendar3.set(Calendar.MINUTE, 0);
			calendar3.set(Calendar.SECOND,0);
			calendar3.set(Calendar.MILLISECOND,0);
//			SimpleDateFormat simpledateformat6 = new SimpleDateFormat("yyyyMMdd");
//			s2 = simpledateformat6.format(calendar3.getTime());
			s2=calendar3.getTime();
		} else
		if (s.equals("YESTERDAY"))
		{
			Calendar calendar4 = Calendar.getInstance();
			int i1 = calendar4.get(1);
			int i2 = calendar4.get(2);
			calendar4.clear();
			calendar4.set(1, i1);
			calendar4.set(2, i2);
			calendar4.set(5, 1);
			calendar4.add(5, -1);
			calendar4.set(Calendar.HOUR, 0);
			calendar4.set(Calendar.MINUTE, 0);
			calendar4.set(Calendar.SECOND,0);
			calendar4.set(Calendar.MILLISECOND,0);
//			SimpleDateFormat simpledateformat7 = new SimpleDateFormat("yyyyMMdd");
//			s2 = simpledateformat7.format(calendar4.getTime());
			s2=calendar4.getTime();
		} else
		if (s.equals("TOMORROW"))
		{
			Calendar calendar5 = Calendar.getInstance();
			int j1 = calendar5.get(1);
			int j2 = calendar5.get(2);
			calendar5.clear();
			calendar5.set(1, j1);
			calendar5.set(2, j2);
			calendar5.set(5, 1);
			calendar5.add(5, 1);
			calendar5.set(Calendar.HOUR, 0);
			calendar5.set(Calendar.MINUTE, 0);
			calendar5.set(Calendar.SECOND,0);
			calendar5.set(Calendar.MILLISECOND,0);
			s2=calendar5.getTime();
//			SimpleDateFormat simpledateformat8 = new SimpleDateFormat("yyyyMMdd");
//			s2 = simpledateformat8.format(calendar5.getTime());
		} else if (s.equalsIgnoreCase("PRIV_BRAND_LIST")){
				try
				{
					UserBrandHome userbrandhome = (UserBrandHome)WaiterFactory.getWaiter(UserBrandHome.class);
					Object aobj[] = {
						WaiterFactory.getSession().getContext().getTopic("USER_ID"), WaiterFactory.getSession().getContext().getTopic("OWNER_ID")
					};
					VariantHolder<String> variantholder = new VariantHolder<String>();
					VariantHolder<Boolean> variantholder1 = new VariantHolder<Boolean>();
					VariantHolder<Object> variantholder2 = new VariantHolder<Object>();
					if (!userbrandhome.listValidated(((Object) (aobj)), variantholder1, variantholder2, variantholder))
						throw new Exception((String)variantholder.value);
					if (((Boolean)variantholder1.value).booleanValue())
					{
						s2 = "";
					} else
					{
						String as[] = (String[])variantholder2.value;
						if (as.length == 0)
						{
							s2 = "#";
						} else
						{
							StringBuffer stringbuffer = new StringBuffer();
							for (int i = 0; i < as.length; i++)
							{
								if (i > 0)
									stringbuffer.append(';');
								stringbuffer.append(as[i]);
							}

							s2 = stringbuffer.toString();
						}
					}
				}
				catch (Exception exception)
				{
					throw new RuntimeException(exception.getMessage());
				}
		}
		//默认当前组织编号
		else if (s.equals("OWNER_CODE"))
			{
				s2 = WaiterFactory.getSession().getContext().getTopic("OWNER_CODE").toString();
			}
		else if(s.substring(0, 1).equals("#")&&s.indexOf(";")!=-1){
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			s2= sf.format(cal.getTime())+s.substring(s.indexOf(";")+1);
		}
		else{
			throw new RuntimeException((new StringBuilder("Unknown variable: ")).append(s).toString());
		}
		return s2;
	}

	public static Object getObject(String s)
	{
		String s2 = "";
		int i = s.indexOf('(');
		String s1;
		if (i >= 0)
		{
			s1 = s.substring(0, i).toUpperCase();
			int j = s.indexOf(')');
			if (j > i)
				s2 = s.substring(i + 1, j);
		} else
		{
			s1 = s.toUpperCase();
		}
		return getObject(s1, s2);
	}



	public static String getString(String s, String s1)
	{
		String s2=null;
		if (s.equalsIgnoreCase("PRIV_UNIT_LIST"))
			try
			{
				String s3 = s1;
				SysUserPaHome sysuserpahome = (SysUserPaHome)WaiterFactory.getWaiter(SysUserPaHome.class);
				Object aobj[] = {
					WaiterFactory.getSession().getContext().getTopic("USER_ID"), WaiterFactory.getSession().getContext().getTopic("OWNER_ID")
				};
				VariantHolder<String> variantholder = new VariantHolder<String>();
				VariantHolder<Boolean> variantholder1 = new VariantHolder<Boolean>();
				VariantHolder<Object> variantholder2 = new VariantHolder<Object>();
				if (!sysuserpahome.examine(((Object) (aobj)), s3, variantholder1, variantholder2, variantholder))
					throw new Exception((String)variantholder.value);
				if (((Boolean)variantholder1.value).booleanValue())
				{
					s2 = "";
				} else
				{
					BigDecimal abigdecimal[] = (BigDecimal[])variantholder2.value;
					if (abigdecimal.length == 0)
					{
						s2 = "#";
					} else
					{
						StringBuffer stringbuffer = new StringBuffer();
						for (int k2 = 0; k2 < abigdecimal.length; k2++)
						{
							if (k2 > 0)
								stringbuffer.append(';');
							stringbuffer.append(abigdecimal[k2]);
						}

						s2 = stringbuffer.toString();
					}
				}
			}
			catch (Exception exception)
			{
				throw new RuntimeException(exception.getMessage());
			}
		else
		if (s.equals("OWNER_ID"))
			s2 = ((BigDecimal)WaiterFactory.getSession().getContext().getTopic("OWNER_ID")).toString();
		else
			if (s.equals("OWNER_NAME"))
				s2 = ((String)WaiterFactory.getSession().getContext().getTopic("OWNER_NAME")).toString();
		else
		if (s.equals("USER_ID"))
			s2 = ((BigDecimal)WaiterFactory.getSession().getContext().getTopic("USER_ID")).toString();
		else
			if (s.equals("USER_NAME"))
				s2 = ((String)WaiterFactory.getSession().getContext().getTopic("USER_NAME")).toString();
		else
		if (s.length()>=4&&  s.substring(0, 4).equals("DATE"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			if(s.length()==4){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>4){
				if(s.substring(4, 5).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.DATE, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(4, 5).equals("-")){
					 String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.DATE, - Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}
			}
		}else
		if (s.equals("TIME"))
		{
			SimpleDateFormat simpledateformat1 = new SimpleDateFormat("HHmmss");
			s2 = simpledateformat1.format(new Date());
		} else
		if (s.equals("NOW"))
		{
			SimpleDateFormat simpledateformat2 = new SimpleDateFormat("yyyyMMddHHmmss");
			s2 = simpledateformat2.format(new Date());
		} else
		if (s.length()>=4&& s.substring(0, 4).equals("YEAR"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyy");
			if(s.length()==4){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>4){
				if(s.substring(4, 5).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.YEAR, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(4, 5).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		            cal.add(Calendar.YEAR, - Integer.parseInt(s3));
		            s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.length()>=5&& s.substring(0, 5).equals("MONTH"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("MM");
			if(s.length()==5){
				s2 = sf.format(cal.getTime());
			}else if(s.length()>5){
				if(s.substring(5, 6).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.MONTH, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(5, 6).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.MONTH, - Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.length()>=3&& s.substring(0, 3).equals("DAY"))
		{
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("dd");
			if(s.length()==3){
				s2 = sf.format(cal.getTime());
				System.out.println(s2);
			}else if(s.length()>3){
				if(s.substring(3, 4).equals("+")){
					 String s3=s.substring(s.indexOf("+")+1, s.length());
		             cal.add(Calendar.DAY_OF_MONTH, + Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}else if(s.substring(3, 4).equals("-")){
					String s3=s.substring(s.indexOf("-")+1, s.length());
		             cal.add(Calendar.DAY_OF_MONTH, - Integer.parseInt(s3));
		             s2 = sf.format(cal.getTime());
				}
			}
		}
		else
		if (s.equals("FIRST_DAY_OF_YEAR"))
		{
			Calendar calendar = Calendar.getInstance();
			int i = calendar.get(1);
			calendar.clear();
			calendar.set(1, i);
			calendar.set(2, 0);
			calendar.set(5, 1);
			SimpleDateFormat simpledateformat3 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat3.format(calendar.getTime());
		} else
		if (s.equals("LAST_DAY_OF_YEAR"))
		{
			Calendar calendar1 = Calendar.getInstance();
			int j = calendar1.get(1);
			calendar1.clear();
			calendar1.set(1, j + 1);
			calendar1.set(2, 0);
			calendar1.set(5, 1);
			calendar1.add(5, -1);
			SimpleDateFormat simpledateformat4 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat4.format(calendar1.getTime());
		} else
		if (s.equals("FIRST_DAY_OF_MONTH"))
		{
			Calendar calendar2 = Calendar.getInstance();
			int k = calendar2.get(1);
			int k1 = calendar2.get(2);
			calendar2.clear();
			calendar2.set(1, k);
			calendar2.set(2, k1);
			calendar2.set(5, 1);
			SimpleDateFormat simpledateformat5 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat5.format(calendar2.getTime());
		} else
		if (s.equals("LAST_DAY_OF_MONTH"))
		{
			Calendar calendar3 = Calendar.getInstance();
			int l = calendar3.get(1);
			int l1 = calendar3.get(2);
			calendar3.clear();
			calendar3.set(1, l);
			calendar3.set(2, l1);
			calendar3.set(5, 1);
			calendar3.add(2, 1);
			calendar3.add(5, -1);
			SimpleDateFormat simpledateformat6 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat6.format(calendar3.getTime());
		} else
		if (s.equals("YESTERDAY"))
		{
			Calendar calendar4 = Calendar.getInstance();
			int i1 = calendar4.get(1);
			int i2 = calendar4.get(2);
			calendar4.clear();
			calendar4.set(1, i1);
			calendar4.set(2, i2);
			calendar4.set(5, 1);
			calendar4.add(5, -1);
			SimpleDateFormat simpledateformat7 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat7.format(calendar4.getTime());
		} else
		if (s.equals("TOMORROW"))
		{
			Calendar calendar5 = Calendar.getInstance();
			int j1 = calendar5.get(1);
			int j2 = calendar5.get(2);
			calendar5.clear();
			calendar5.set(1, j1);
			calendar5.set(2, j2);
			calendar5.set(5, 1);
			calendar5.add(5, 1);
			SimpleDateFormat simpledateformat8 = new SimpleDateFormat("yyyyMMdd");
			s2 = simpledateformat8.format(calendar5.getTime());
		} else if (s.equalsIgnoreCase("PRIV_BRAND_LIST")){
				try
				{
					UserBrandHome userbrandhome = (UserBrandHome)WaiterFactory.getWaiter(UserBrandHome.class);
					Object aobj[] = {
						WaiterFactory.getSession().getContext().getTopic("USER_ID"), WaiterFactory.getSession().getContext().getTopic("OWNER_ID")
					};
					VariantHolder<String> variantholder = new VariantHolder<String>();
					VariantHolder<Boolean> variantholder1 = new VariantHolder<Boolean>();
					VariantHolder<Object> variantholder2 = new VariantHolder<Object>();
					if (!userbrandhome.listValidated(((Object) (aobj)), variantholder1, variantholder2, variantholder))
						throw new Exception((String)variantholder.value);
					if (((Boolean)variantholder1.value).booleanValue())
					{
						s2 = "";
					} else
					{
						String as[] = (String[])variantholder2.value;
						if (as.length == 0)
						{
							s2 = "#";
						} else
						{
							StringBuffer stringbuffer = new StringBuffer();
							for (int i = 0; i < as.length; i++)
							{
								if (i > 0)
									stringbuffer.append(';');
								stringbuffer.append(as[i]);
							}

							s2 = stringbuffer.toString();
						}
					}
				}
				catch (Exception exception)
				{
					throw new RuntimeException(exception.getMessage());
				}
		}
		//默认当前组织编号
		else if (s.equals("OWNER_CODE"))
			{
				s2 = WaiterFactory.getSession().getContext().getTopic("OWNER_CODE").toString();
			}
		else if(s.substring(0, 1).equals("#")&&s.indexOf(";")!=-1){
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			s2= sf.format(cal.getTime())+s.substring(s.indexOf(";")+1);
			System.out.println(s2);
		}
		else{
//			return null;
			throw new RuntimeException((new StringBuilder("Unknown variable: ")).append(s).toString());
		}
		return s2;
	}

	public static String getString(String s)
	{
		String s2 = "";
		int i = s.indexOf('(');
		String s1;
		if (i >= 0)
		{
			s1 = s.substring(0, i).toUpperCase();
			int j = s.indexOf(')');
			if (j > i)
				s2 = s.substring(i + 1, j);
		} else
		{
			s1 = s.toUpperCase();
		}
		return getString(s1, s2);
	}
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(1);
		calendar.clear();
		calendar.set(1, i);
		calendar.set(2, 0);
		calendar.set(5, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		System.out.println(calendar.getTime());
		SimpleDateFormat simpledateformat2 = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println( simpledateformat2.format(calendar.getTime()));

	}
}
