package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ForTest {
	private String str1;
	private String str2;
	private String str3;
	private String str4;
	private String str5;
	private String str6;
	private String str7;
	private String str8;
	private String str9;
	private String str10;
	private String str11;
	private String str12;
	private String str13;
	private String str14;
	private String str15;

	public static void main(String[] args){

		//实例化arrayList
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			list.add(i);
		}
		for(int i:list){
			System.out.println(i);
		}

//		//实例化arrayList
//		List<ForTest> arrayList = new ArrayList<ForTest>();
//		//实例化linkList
//		List<ForTest> linkList = new LinkedList<ForTest>();
//
//		//插入500万条数据
//		for (int i = 0; i < 10000000; i++) {
//			ForTest test=new ForTest();
//			test.str1="str1";
//			test.str2="str2";
//			test.str3="str3";
//			test.str4="str4";
//			test.str5="str5";
//			test.str6="str6";
//			test.str7="str7";
//			test.str8="str8";
//			test.str9="str9";
//			test.str10="str10";
//			test.str11="str11";
//			test.str12="str12";
//			test.str13="str13";
//			test.str14="str14";
//			test.str15="str15";
//			arrayList.add(test);
//			linkList.add(test);
//		}
//
//
//
//
//
//
//
//		ForTest array = null;
//		String temp;
//		//用for循环arrayList
//		long arrayForStartTime = System.currentTimeMillis();
//		for (int i = 0; i < arrayList.size(); i++) {
//			array = arrayList.get(i);
//			temp= array.str1;
//			temp= array.str2;
//			temp= array.str3;
//			temp= array.str4;
//			temp= array.str5;
//			temp= array.str6;
//			temp= array.str7;
//			temp= array.str8;
//			temp= array.str9;
//			temp= array.str10;
//			temp= array.str11;
//			temp= array.str12;
//			temp= array.str13;
//			temp= array.str14;
//			temp= array.str15;
//		}
//		long arrayForEndTime = System.currentTimeMillis();
//		System.out.println("用for循环arrayList，声明一次对象 500万次花费时间 ：" + (arrayForEndTime - arrayForStartTime) + "毫秒");
//
//		//用for循环arrayList
//		long arrayForStartTime2 = System.currentTimeMillis();
//		for (int i = 0; i < arrayList.size(); i++) {
//			ForTest	array2 = arrayList.get(i);
//			String  temp2= array2.str1;
//			temp2= array2.str2;
//			temp2= array2.str3;
//			temp2= array2.str4;
//			temp2= array2.str5;
//			temp2= array2.str6;
//			temp2= array2.str7;
//			temp2= array2.str8;
//			temp2= array2.str9;
//			temp2= array2.str10;
//			temp2= array2.str11;
//			temp2= array2.str12;
//			temp2= array2.str13;
//			temp2= array2.str14;
//			temp2= array2.str15;
//		}
//		long arrayForEndTime2 = System.currentTimeMillis();
//		System.out.println("用for循环arrayList，声明多次对象 500万次花费时间 ：" + (arrayForEndTime2 - arrayForStartTime2) + "毫秒");
//
//
//		//用foreach循环arrayList
//		long arrayForeachStartTime = System.currentTimeMillis();
//		for(ForTest in : arrayList){
//			array = in;
//
//			temp= array.str1;
//			temp= array.str2;
//			temp= array.str3;
//			temp= array.str4;
//			temp= array.str5;
//			temp= array.str6;
//			temp= array.str7;
//			temp= array.str8;
//			temp= array.str9;
//			temp= array.str10;
//			temp= array.str11;
//			temp= array.str12;
//			temp= array.str13;
//			temp= array.str14;
//			temp= array.str15;
//
//		}
//		long arrayForeachEndTime = System.currentTimeMillis();
//		System.out.println("用foreach循环arrayList 500万次花费时间：" + (arrayForeachEndTime - arrayForeachStartTime ) + "毫秒");
//
//		//用for循环linkList
//
//		ForTest link = null;
////		long linkForStartTime = System.currentTimeMillis();
////		for (int i = 0; i < linkList.size(); i++) {
////		link = linkList.get(i);
////		}
////		long linkForEndTime = System.currentTimeMillis();
////		System.out.println("用for循环linkList 500万次花费时间：" + (linkForEndTime - linkForStartTime) + "毫秒");
//
//		//用froeach循环linkList
//		long linkForeachStartTime = System.currentTimeMillis();
//		for(ForTest in : linkList){
//			link = in;
//
//			temp= link.str1;
//			temp= link.str2;
//			temp= link.str3;
//			temp= link.str4;
//			temp= link.str5;
//			temp= link.str6;
//			temp= link.str7;
//			temp= link.str8;
//			temp= link.str9;
//			temp= link.str10;
//			temp= link.str11;
//			temp= link.str12;
//			temp= link.str13;
//			temp= link.str14;
//			temp= link.str15;
//		}
//		long linkForeachEndTime = System.currentTimeMillis();
//		System.out.println("用foreach循环linkList 500万次花费时间：" + (linkForeachEndTime - linkForeachStartTime ) + "毫秒");
//		int i=0;
//
//		long whileStartTime = System.currentTimeMillis();
//
//		while(i<arrayList.size()){
//			array = arrayList.get(i);
//			temp= array.str1;
//			temp= array.str2;
//			temp= array.str3;
//			temp= array.str4;
//			temp= array.str5;
//			temp= array.str6;
//			temp= array.str7;
//			temp= array.str8;
//			temp= array.str9;
//			temp= array.str10;
//			temp= array.str11;
//			temp= array.str12;
//			temp= array.str13;
//			temp= array.str14;
//			temp= array.str15;
//			i++;
//		}
//		long whileEndTime = System.currentTimeMillis();
//		System.out.println("用while循环arrayList 500万次花费时间：" + (whileEndTime-whileStartTime ) + "毫秒");
//
//
//		/*long arrayForStartTime = System.currentTimeMillis();
////		Map<String ,String> map=null;
//		for(int j=0;j<10;j++){
//			for(;i<100000000;i++){
//				HashMap<String ,String> map=new HashMap<String, String>();
//			}
//			long arrayForEndTime = System.currentTimeMillis();
//			System.out.println("用for循环arrayList，声明一次对象 500万次花费时间 ：" + (arrayForEndTime - arrayForStartTime) + "毫秒");
//		}*/
	}

}
