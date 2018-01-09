package com.evangelsoft.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {

    public static void main(String[] args){
        //ʵ����arrayList
        List<Integer> arrayList = new ArrayList<Integer>();
        //ʵ����linkList
        List<Integer> linkList = new LinkedList<Integer>();

        //����10��������
        for (int i = 0; i < 1000000; i++) {
            arrayList.add(i);
            linkList.add(i);
        }

        int array = 0;
        //��forѭ��arrayList
        long arrayForStartTime = System.currentTimeMillis();
        for (int i = 0; i < arrayList.size(); i++) {
            array = arrayList.get(i);
        }
        long arrayForEndTime = System.currentTimeMillis();
        System.out.println("��forѭ��arrayList 10��λ���ʱ�䣺" + (arrayForEndTime - arrayForStartTime) + "����");

        //��foreachѭ��arrayList
        long arrayForeachStartTime = System.currentTimeMillis();
        for(Integer in : arrayList){
            array = in;
        }
        long arrayForeachEndTime = System.currentTimeMillis();
        System.out.println("��foreachѭ��arrayList 10��λ���ʱ�䣺" + (arrayForeachEndTime - arrayForeachStartTime ) + "����");

        //��forѭ��linkList

        int link = 0;
        long linkForStartTime = System.currentTimeMillis();
        for (int i = 0; i < linkList.size(); i++) {
            link = linkList.get(i);
        }
        long linkForEndTime = System.currentTimeMillis();
        System.out.println("��forѭ��linkList 10��λ���ʱ�䣺" + (linkForEndTime - linkForStartTime) + "����");

        //��froeachѭ��linkList
        long linkForeachStartTime = System.currentTimeMillis();
        for(Integer in : linkList){
            link = in;
        }
        long linkForeachEndTime = System.currentTimeMillis();
        System.out.println("��foreachѭ��linkList 10��λ���ʱ�䣺" + (linkForeachEndTime - linkForeachStartTime ) + "����");
    }
}