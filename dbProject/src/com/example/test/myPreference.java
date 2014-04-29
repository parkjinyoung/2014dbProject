package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class myPreference {
	 private final String PREF_NAME = "com.example.test";
	 
	    public final static String AUTO_LOGIN = "AUTO_LOGIN";
	    public final static String USER_ID = "USER_ID";
	    public final static String USER_PWD = "USER_PASSWORD";
	 
	    static Context mContext;
	 
	    public myPreference(Context c) {
	        mContext = c;
	    }
	 
	    public void put(String key, String value) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	        SharedPreferences.Editor editor = pref.edit();
	 
	        editor.putString(key, value);
	        editor.commit();
	    }
	 
	    public void put(String key, boolean value) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	        SharedPreferences.Editor editor = pref.edit();
	 
	        editor.putBoolean(key, value);
	        editor.commit();
	    }
	 
	    public void put(String key, int value) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	        SharedPreferences.Editor editor = pref.edit();
	 
	        editor.putInt(key, value);
	        editor.commit();
	    }
	 
	    public String getValue(String key, String dftValue) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	 
	        try {
	            return pref.getString(key, dftValue);
	        } catch (Exception e) {
	            return dftValue;
	        }
	 
	    }
	 
	    public int getValue(String key, int dftValue) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	 
	        try {
	            return pref.getInt(key, dftValue);
	        } catch (Exception e) {
	            return dftValue;
	        }
	 
	    }
	 
	    public boolean getValue(String key, boolean dftValue) {
	        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
	                Activity.MODE_PRIVATE);
	 
	        try {
	            return pref.getBoolean(key, dftValue);
	        } catch (Exception e) {
	            return dftValue;
	        }
	    }

}
