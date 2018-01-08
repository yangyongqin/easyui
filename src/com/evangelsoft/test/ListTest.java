package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {

    public static void main(String[] args){
        //实例化arrayList
        List<Integer> arrayList = new ArrayList<Integer>();
        //实例化linkList
        List<Integer> linkList = new LinkedList<Integer>();

        //插入10万条数据
        for (int i = 0; i < 1000000; i++) {
            arrayList.add(i);
            linkList.add(i);
        }

        int array = 0;
        //用for循环arrayList
        long arrayForStartTime = System.currentTimeMillis();
        for (int i = 0; i < arrayList.size(); i++) {
            array = arrayList.get(i);
        }
        long arrayForEndTime = System.currentTimeMillis();
        System.out.println("用for循环arrayList 10万次花费时间：" + (arrayForEndTime - arrayForStartTime) + "毫秒");

        //用foreach循环arrayList
        long arrayForeachStartTime = System.currentTimeMillis();
        for(Integer in : arrayList){
            array = in;
        }
        long arrayForeachEndTime = System.currentTimeMillis();
        System.out.println("用foreach循环arrayList 10万次花费时间：" + (arrayForeachEndTime - arrayForeachStartTime ) + "毫秒");

        //用for循环linkList

        int link = 0;
        long linkForStartTime = System.currentTimeMillis();
        for (int i = 0; i < linkList.size(); i++) {
            link = linkList.get(i);
        }
        long linkForEndTime = System.currentTimeMillis();
        System.out.println("用for循环linkList 10万次花费时间：" + (linkForEndTime - linkForStartTime) + "毫秒");

        //用froeach循环linkList
        long linkForeachStartTime = System.currentTimeMillis();
        for(Integer in : linkList){
            link = in;
        }
        long linkForeachEndTime = System.currentTimeMillis();
        System.out.println("用foreach循环linkList 10万次花费时间：" + (linkForeachEndTime - linkForeachStartTime ) + "毫秒");
    }
}