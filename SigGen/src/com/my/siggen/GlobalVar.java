package com.my.siggen;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class GlobalVar {
	Map<String , WeakReference<Object>> variables = new HashMap<String, WeakReference<Object>>();
	
	private static final GlobalVar instance = new GlobalVar();
	private GlobalVar() {
	}
	
	public static GlobalVar getInstance() {
		return instance;
	}
	public void save(String id, Object object) {
		variables.put(id, new WeakReference<Object>(object));
	}

	public Object retrieve(String id) {
		WeakReference<Object> objectWeakReference = variables.get(id);
		if (objectWeakReference == null ) {
			return null;
		} else {
			return objectWeakReference.get();
		}
	}

	public void remove(String id) {
		variables.remove(id);
	}
	
	public void flushAll() {
		variables.clear();
	}
}
