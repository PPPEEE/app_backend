package com.pe.exchange.utils;

import java.util.HashMap;
import java.util.Map;

public class OderQueueUtil extends Thread{

	private static final Map<String,Long> queues = new HashMap<String,Long>();
	
	//private OderQueueUtil() {}
	
	public static void setOderQueue(String key,Long times) {
		queues.put(key,times);
	}
	
	public static Long get(String key) {
		return queues.get(key);
	}

	public static Map<String, Long> getQueues() {
		return queues;
	}
	
	public static void remove(String key) {
		queues.remove(key);
		System.out.println("移除Key:"+key);
	}
	
	
}
