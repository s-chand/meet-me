package com.iqube.app.meetme;

import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

public class StoreManager {
	public static PersistentObject persistentObject = PersistentStore.getPersistentObject(0xa1a781590238dae4L);
	
	private Hashtable storeHashtable;
	
	public StoreManager() {
		synchronized(persistentObject) {
			storeHashtable = (Hashtable)persistentObject.getContents();
			if (storeHashtable == null) {
				storeHashtable = new Hashtable();
				persistentObject.setContents(storeHashtable);
				persistentObject.commit();
			}
		}
	}
	
	public void commit() {
		persistentObject.commit();
	}
	
	public void set(String key, Object value) {
		storeHashtable.put(key, value);
	}
	
	public Object get(String key) {
		return storeHashtable.get(key);
	}
}
