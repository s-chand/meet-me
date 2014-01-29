package com.iqube.app.meetme;

import java.util.Hashtable;
import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


public class MyJsonParser {
	public static Vector eventsJson(String json) {
		Vector ret = new Vector();
		
		try {
			JSONObject object = new JSONObject(json);
			
			
			
			JSONArray array = object.getJSONArray("200");
			for (int i = 0; i < array.length(); i++) {
				Hashtable curr = new Hashtable();
				JSONObject obj = array.getJSONObject(i);
				
				curr.put("event_name", obj.getString("event_name"));
				curr.put("distance", obj.getString("distance"));
				curr.put("event_id", obj.getString("event_id"));
				curr.put("event_location", obj.getString("event_location"));
				
				ret.addElement(curr);
			}
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		
		return ret;
	}
}
