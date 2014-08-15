package com.thientv.slidingmenu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.slidingmenu.lib.app.SlidingActivity;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.thientv.calciomino.R;
import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.MenuAdapter;
import com.thientv.slidingmenu.bean.MenuItem;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.fragment.Home;
import com.thientv.slidingmenu.fragment.LongPost;
import com.thientv.slidingmenu.fragment.ShortPost;
import com.thientv.slidingmenu.fragment.VideoPost;
import com.thientv.slidingmenu.httpclient.HttpClientHelper;
import com.thientv.slidingmenu.httpclient.MyJsonHttpResponseHandler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends SlidingFragmentActivity {

	ListView listMenu;
	MenuAdapter adapter;
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	Fragment frag = null;
	
	ImageButton btnback;
	
	public static String mType = "home";
	
	MySQLiteHelper db;
	
	public static int mCurrent = 0;
	
	Handler mHandler = new Handler();
	
	Runnable rLong = new Runnable() {
		
		@Override
		public void run() {
			getArticle();
		}
	};
	Runnable rShort = new Runnable() {
		
		@Override
		public void run() {
			getShort();
		}
	};
	
	Runnable rVideo = new Runnable() {
		
		@Override
		public void run() {
			getVideo();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setBehindContentView(R.layout.activity_menu);

		getSlidingMenu().setBehindOffset(100);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setShadowWidth((int) 10f);
		
		mCurrent = 0;
		
		db = new MySQLiteHelper(MainActivity.this);
		
		addDataMenu();
		
//		mHandler.post(rLong);
//		mHandler.post(rShort);
//		mHandler.post(rVideo);
		
		Thread thr = new Thread(new Runnable() {
			
			@Override
			public void run() {
				getArticle();
				getShort();
				getVideo();
			}
		});
		
		thr.start();
		
		
		btnback = (ImageButton) findViewById(R.id.btn_back);
		listMenu = (ListView) findViewById(R.id.list_menu);
		adapter = new MenuAdapter(MainActivity.this, menuItems);
		listMenu.setAdapter(adapter);
		
		frag = new Home();
    	replaceFragment(frag);
		
		
		listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position,
					long id) {
				mCurrent = position;
				adapter.notifyDataSetChanged();
		        switch(position)
		        {
		            case 0:
		            	toggle();
		                frag = new Home();
		                break;
		            case 1:
		            	toggle();
		                frag = new LongPost();
		                break;
		            case 2:
		            	toggle();
		                frag = new ShortPost();
		                break;
		            case 3:
		            	toggle();
		                frag = new VideoPost();
		                break;
		        }

		        if (frag != null){
		        	replaceFragment(frag);	
		        } else {
				}
		            
			}
			
		});
    	
		
		init();
	}
	
	void init(){
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				toggle(); 	
			}
		});
	}
	
	protected void replaceFragment(Fragment frag)
    {
        android.support.v4.app.FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, frag);
        fragmentTransaction.commit();
    }

	void addDataMenu() {
		menuItems.add(new MenuItem("Accueil", R.color.color_den));
		menuItems.add(new MenuItem("Brèves", R.color.color_xanh));
		menuItems.add(new MenuItem("Articles", R.color.color_xanh_la));
		menuItems.add(new MenuItem("Vidéos", R.color.color_do));
	}
	
	public void showMenu(){
		showMenu();
	}
	
	//---------------- get article --------------------//
	public void getArticle(){
		HttpClientHelper httpClientHelper = new HttpClientHelper(new MyJsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable error) {
			}

			@Override
			public void onSuccess(JSONArray re) {
				// insert
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
						int dateUnix = news.getInt("date_unix");
						String dateDay = news.getString("date_day");
						String dateHour = news.getString("date_hour");
						String title = news.getString("title");
						String content = news.getString("content");
						String video = "";
						
						
						
						objPost.setId(id);
						objPost.setType(type);
						objPost.setUrlImage(urlImage);
						objPost.setUrlPost(urlPost);
						objPost.setAuthor(author);
						objPost.setDateUnix(dateUnix);
						objPost.setDateDay(dateDay);
						objPost.setDateHour(dateHour);
						objPost.setTitle(title);
						objPost.setContent(content);
						objPost.setUrlVideo(video);
						
						db.insertNew(objPost);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}

			
		});
		
		httpClientHelper.getLongPost();
	}
	
	//-------------- get short --------------------//
	public void getShort(){
		HttpClientHelper httpClientHelper = new HttpClientHelper(new MyJsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable error) {
			}

			@Override
			public void onSuccess(JSONArray re) {
				// insert
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
						int dateUnix = news.getInt("date_unix");
						String dateDay = news.getString("date_day");
						String dateHour = news.getString("date_hour");
						String title = news.getString("title");
						String content = news.getString("content");
						String video = "";
						
						
						
						objPost.setId(id);
						objPost.setType(type);
						objPost.setUrlImage(urlImage);
						objPost.setUrlPost(urlPost);
						objPost.setAuthor(author);
						objPost.setDateUnix(dateUnix);
						objPost.setDateDay(dateDay);
						objPost.setDateHour(dateHour);
						objPost.setTitle(title);
						objPost.setContent(content);
						objPost.setUrlVideo(video);
						
						db.insertNew(objPost);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}

			
		});
		
		httpClientHelper.getShortPost();
	}
	
	//-------------- get video --------------------//
		public void getVideo(){
			HttpClientHelper httpClientHelper = new HttpClientHelper(new MyJsonHttpResponseHandler(){

				@Override
				public void onFailure(Throwable error) {
				}

				@Override
				public void onSuccess(JSONArray re) {
					// insert
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
							int dateUnix = news.getInt("date_unix");
							String dateDay = news.getString("date_day");
							String dateHour = news.getString("date_hour");
							String title = news.getString("title");
							String content = news.getString("content");
							String video = news.getString("video_url");
							objPost.setUrlVideo(video);
							
							
							objPost.setId(id);
							objPost.setType(type);
							objPost.setUrlImage(urlImage);
							objPost.setUrlPost(urlPost);
							objPost.setAuthor(author);
							objPost.setDateUnix(dateUnix);
							objPost.setDateDay(dateDay);
							objPost.setDateHour(dateHour);
							objPost.setTitle(title);
							objPost.setContent(content);
							objPost.setUrlVideo(video);
							
							db.insertNew(objPost);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}

				
			});
			
			httpClientHelper.getVideoPost();
		}
}
