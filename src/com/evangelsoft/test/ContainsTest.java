package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContainsTest {
	public static void main(String[] args) {

//		long listStart = System.currentTimeMillis();
//		List<String> list = new ArrayList<String>();
//		for(int i = 0; i <= 100000; i++){
//			list.add("str1"+i);
//
//		}
//
//
//		for(int i = 0; i<= 100000; i++){
//			list.contains("str1"+i);
//		}
//		System.out.println("List查询100000个对象 \n共耗费时间："+(System.currentTimeMillis()-listStart)+ "毫秒");


		Set<String> set = new HashSet<String>();
		Map<String,String> map = new HashMap<String,String>();




		long mapStart = System.currentTimeMillis();
		for(int i = 0; i <= 1000000; i++){
			map.put("str1"+i, null);
		}


		for(int i = 0; i<= 10000000; i++){
			map.containsKey("str1"+i);
		}
		System.out.println("Map查询100000个对象 \n共耗费时间："+(System.currentTimeMillis()-mapStart)+ "毫秒");



		long setStart = System.currentTimeMillis();
		for(int i = 0; i <= 1000000; i++){

			set.add( "str1"+i);
		}

		for(int i = 0; i<= 10000000; i++){
			set.contains("str1"+i);
		}
		System.out.println("Set查询100000个对象 \n共耗费时间："+(System.currentTimeMillis()-setStart)+ "毫秒");




	}
}
