package com.evangelsoft.easyui.print.client;

import java.util.ArrayList;
import java.util.List;

/**
 * <b> 可设置阀值的对象缓存池  </b>
 *
 * <p>  
 *
 * </p>
 *
 * Create at 2008-10-6 下午10:18:52
 * 
 * @author bq 
 * @since V5.5
 */

public class ThreshholdPool<K, V> {

	private List<Item<K, V>> pool = new ArrayList<Item<K, V>>();
	
	private int threshhold = 10;
	
	public ThreshholdPool(){
		
	}
	
	public ThreshholdPool(int threshhold){
		this.threshhold = threshhold;
	}
	
	public void put(K key, V value){
		// 始终保留第一个，因为预览时可首页尾页预览
		if(pool.size() >= threshhold)
			pool.remove(1);
		
		pool.add(new Item(key, value));
	}

	public V get(K key){
		V value = null;
		
		for(Item<K, V> item : pool){
			if(item.getKey() == key)
				return item.getValue();
		}
		
		return value;
	}
	
	public void clear(){
		pool.clear();
	}
	
	class Item<K, V>{
		K key;
		V value;
		
		public Item(K key, V value){
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}		
	}
}
