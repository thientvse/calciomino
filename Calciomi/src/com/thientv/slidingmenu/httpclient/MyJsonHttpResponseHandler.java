package com.thientv.slidingmenu.httpclient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyJsonHttpResponseHandler extends AsyncHttpResponseHandler{

	
	
	@Deprecated
	public void onFailure(Throwable error, String content) {
		onFailure(error);
	}

	public void onFailure(Throwable error) { }

	@Deprecated
	public void onSuccess(String content) {
		JSONObject response = new JSONObject();
		JSONArray response2 = new JSONArray();
		try {
			response = new JSONObject(content);
		} catch (Exception e) {
			try {
				response.put("success", 0);
				response.put("errorMessage", String.valueOf(content));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		try {
			response2 = new JSONArray(content);
		} catch (Exception e) {
			
		}
		/// ---------------------------///
		// cho vao 1 thread khac o day --------
		onSuccess(response2);
		onSuccess(response);
	}
	
	public void onSuccess(JSONObject re) {
		
	}
	
	public void onSuccess(JSONArray re) {
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
