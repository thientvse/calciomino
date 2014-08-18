package com.thientv.slidingmenu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
	private SharedPreferences sharedPreferences;
	public static final String PREFERENCE_FILE = "preference_file.com.thientv.calciomino";
	
	// user_id
	public static final String COUNT = "count";
	
	public static final String DONE = "done";
	
	
	public PreferenceHelper(Context context) {
		this.sharedPreferences = context.getSharedPreferences("calciomino", 0);
	}
	
	
	//------------------------------------------------//
	
	
	public int getCount(){
		return this.sharedPreferences.getInt(COUNT, -1);
	}
	
	public String getDone(){
		return this.sharedPreferences.getString(DONE, "");
	}
	
	
	//------------------------------------------------//
	public void setCount(int value){
		SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
		localEditor.putInt("COUNT", value);
		localEditor.commit();
	}
	
	public void setDone(String value){
		SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
		localEditor.putString(DONE, value);
		localEditor.commit();
	}


}
