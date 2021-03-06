package com.pe.exchange.utils;

import java.util.HashMap;
import java.util.Map;

public class OderQueueUtil extends Thread{

	//付款超时集合
	private static final Map<String,Long> queues = new HashMap<String,Long>();
	
	private static final Map<String,Long> paymentQueues = new HashMap<String,Long>();
	
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

	public static Map<String, Long> getPaymentqueues() {
		return paymentQueues;
	}
	
	
}
