package com.thientv.slidingmenu.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thientv.calciomino.DetailPostActivity;
import com.thientv.calciomino.R;
import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.NewPostAdapter;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.httpclient.HttpClientHelper;
import com.thientv.slidingmenu.httpclient.JsonParser;
import com.thientv.slidingmenu.httpclient.MyJsonHttpResponseHandler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LongPost extends Fragment {

	ImageButton btnBack;
	ListView listHome;
	NewPostAdapter adapter;
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	MySQLiteHelper db;
	
	int countUpdate = 0;
	
	Handler mHandler = new Handler();
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			if (countUpdate == 1){
				getDataFromDB();
			} else {
				mHandler.removeCallbacks(r);
				mHandler.postDelayed(r, 300);
			}
		}
	};
	
	void getDataFromDB(){
		objPosts = db.getNew("breves", 50);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frg_long_post, container, false);
		btnBack = (ImageButton) v.findViewById(R.id.btn_back);
		
		db = new MySQLiteHelper(getActivity());
		
//		addDataDemo();
		
//		getArticle();
		
		getDataFromDB();
		
//		new AsyncTaskParseJson().execute();
		listHome = (ListView) v.findViewById(R.id.list_long);
		adapter = new NewPostAdapter(getActivity(), objPosts);
		listHome.setAdapter(adapter);

		listHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent t = new Intent(getActivity(), DetailPostActivity.class);
				t.putExtra("type", "breves");
				t.putExtra("postion", position);
				t.putExtra("post", objPosts.get(position));
				startActivity(t);
			}

		});

		return v;
	}
	
	void addDataDemo(){
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Retour sur la performance du Milan AC face à Manchester City : "));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
	}
	
	void getArticle(){
		
		mHandler.post(r);
		
		Log.i("DATA", "get article-----");
		HttpClientHelper httpClientHelper = new HttpClientHelper(new MyJsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable error) {
				countUpdate++;
			}

			@Override
			public void onSuccess(JSONArray re) {
				// insert
				countUpdate++;
				for (int i = 0; i < re.length(); i++) {
					try {
						JSONObject jo = re.getJSONObject(i);
						Log.i("DATA", "NEWS: "+jo.toString());
						JSONObject news = jo.getJSONObject("news");
						
						ObjPost objPost = new ObjPost();
						
						int id = news.getInt("id");
						String type = jo.getString("type");
						String urlImage = news.getString("image");
						String urlPost = news.getString("url");
						String author = news.getString("author");
						String dateDay = news.getString("date_day");
						String dateHour = news.getString("date_hour");
						String title = news.getString("title");
						String content = news.getString("content");
						
						
						objPost.setId(id);
						objPost.setType(type);
						objPost.setUrlImage(urlImage);
						objPost.setUrlPost(urlPost);
						objPost.setAuthor(author);
						objPost.setDateDay(dateDay);
						objPost.setDateHour(dateHour);
						objPost.setTitle(title);
						objPost.setContent(content);
						
						db.insertNew(objPost);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}

			
		});
		
		httpClientHelper.getLongPost();
	}
	
	// you can make this class as another java file so it will be separated from your main activity.
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
 
        final String TAG = "AsyncTaskParseJson.java";
 
        // set your json string url here
        String yourJsonStringUrl = "http://www.calciomio.fr/json-exports/articles";
 
        // contacts JSONArray
        JSONArray dataJsonArr = null;
 
        @Override
        protected void onPreExecute() {}
 
        @Override
        protected String doInBackground(String... arg0) {
 
            // instantiate our json parser
			JsonParser jParser = new JsonParser();
			
			JSONArray json = jParser.getJSONAFromUrl(yourJsonStringUrl);
			
			
			for (int i = 0; i < json.length(); i++) {
				try {
					
					
					JSONObject news = json.getJSONObject(i);
					Log.i("DATA", "NEWS: "+news.toString());
					ObjPost objPost = new ObjPost();
					
					int id = news.getInt("id");
					String type = news.getString("type");
					String urlImage = news.getString("image");
					String urlPost = news.getString("url");
					String author = news.getString("author");
					String dateDay = news.getString("date_day");
					String dateHour = news.getString("date_hour");
					String title = news.getString("title");
					String content = news.getString("content");
					
					
					objPost.setId(id);
					objPost.setType(type);
					objPost.setUrlImage(urlImage);
					objPost.setUrlPost(urlPost);
					objPost.setAuthor(author);
					objPost.setDateDay(dateDay);
					objPost.setDateHour(dateHour);
					objPost.setTitle(title);
					objPost.setContent(content);
					
					db.insertNew(objPost);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
 
 
            return null;
        }
 
        @Override
        protected void onPostExecute(String strFromDoInBg) {
        }
    }
	

}
