package com.thientv.slidingmenu.httpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpClientHelper {
	// khai bao duong dan api chung
	private static final String BASE_URL = "http://www.calciomio.fr";
	
	//update device id
	private static final String UPDATE_DEVICEID = "/service/adevice.php";
	
	// get thong tin long
	private static final String GET_LONG = "/json-exports/articles";
	// get thong tin short
	private static final String GET_SHORT = "/json-exports/breves";
	
	// get thong tin video
	private static final String GET_VIDEO = "/json-exports/videos";
	
	// dang ki gcm
	private static final String REG_GCM = "/PushAppCustom00/register.php";
	
	
	// khai bao context
	private Context context;
	
	// khai bao cac thanh phan thao tac client va server
	private static AsyncHttpClient client = new AsyncHttpClient();
	private AsyncHttpResponseHandler  asyncHttpResponseHandler;
	
	/*// khai bao file luu tam
	private PreferenceHelper preferenceHelper;
	
	// contructor
	public HttpClientHelper(AsyncHttpResponseHandler asyncHttpResponseHandler, Context context) {
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
		this.context = context;
		preferenceHelper = new PreferenceHelper(context);
	}*/
	
	public HttpClientHelper(AsyncHttpResponseHandler asyncHttpResponseHandler) {
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
	}
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(BASE_URL, url), params, responseHandler);
	}
	// phan rieng
	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		Log.i("URL", url);
		client.get(url, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		Log.i("URL", url);
		client.post(getAbsoluteUrl(BASE_URL,url), params, responseHandler);
	}
	
	// get url voi tham so truyen len
	private static String getUrlWithParams(String base, String relativeUrl, List<BasicNameValuePair> params) {
		String url = getAbsoluteUrl(base, relativeUrl);
		for (int i = 0; i < params.size(); i++) {
			BasicNameValuePair b = params.get(i);
			url+="&"+b.getName()+"="+b.getValue();
		}
		url = url.replace(" ", "%20");
		return url;
	}
	
	// get url post
	private static String getUrlPostWithParams(String base, String relativeUrl, RequestParams params) {
		String url = getAbsoluteUrl(base, relativeUrl);
		RequestParams r = params;
		url+="?"+r;
		url = url.replace(" ", "%20");
				
		return url;
	}
	
	
	// tao duong dan api 
	private static String getAbsoluteUrl(String base, String relativeUrl) {
		return base + relativeUrl + "/";
	}
	
	// get long
	public void getLongPost() {
		get(getAbsoluteUrl(BASE_URL, GET_LONG), asyncHttpResponseHandler);
		Log.i("URL", "URL: "+getUrlPostWithParams(BASE_URL, GET_LONG, null));
	}
	
	// get short
	public void getShortPost() {
		get(getAbsoluteUrl(BASE_URL, GET_SHORT), asyncHttpResponseHandler);
		Log.i("URL", "URL: "+getUrlPostWithParams(BASE_URL, GET_SHORT, null));
	}
	
	// get short
	public void getVideoPost() {
		get(getAbsoluteUrl(BASE_URL, GET_VIDEO), asyncHttpResponseHandler);
		Log.i("URL", "URL: "+getUrlPostWithParams(BASE_URL, GET_VIDEO, null));
	}
	
	//register gcm
	public void registerGCM(String regId, String name, String email) {
		RequestParams params = new RequestParams();
		params.add("regId", regId);
		params.add("name", name);
		params.add("email", email);
		post(REG_GCM, params, asyncHttpResponseHandler);
		Log.i("URL", "URL: "+getUrlPostWithParams(BASE_URL, REG_GCM, params));
	}
	
	
}

