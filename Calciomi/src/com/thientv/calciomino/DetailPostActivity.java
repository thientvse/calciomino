package com.thientv.calciomino;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.NewPostAdapter;
import com.thientv.slidingmenu.adapter.ViewPagerFeedAdapter;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DetailPostActivity extends Activity implements OnTouchListener{
	ImageButton btnBack;
	
	ObjPost objPost = new ObjPost();
	
	TextView txtType, txtTitle;
	
	ViewPager pager;
	ViewPagerFeedAdapter adapter;
	MySQLiteHelper db;
	
	ImageButton btnShare;
	
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	
	String type;
	int position;
	
	NewPostAdapter pAdapter;
	ListView lsNear;
	ArrayList<ObjPost> nears = new ArrayList<ObjPost>();
	
	Button btnPrev, btnNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_post);
		
		db = new MySQLiteHelper(this);
		
		objPost = getIntent().getParcelableExtra("post");
		type = getIntent().getStringExtra("type");
		
		position = getIntent().getIntExtra("postion", -1);
		
		if (type.equals("home")){
			objPosts = db.getHome(50);
			nears = db.getHome(5);
		} else if (type.equals("articles")) {
			objPosts = db.getNew(type, 50);
			nears = db.getNew(type, 5);
		} else if (type.equals("breves")) {
			objPosts = db.getNew(type, 50);
			nears = db.getNew(type, 5);
		} else if (type.equals("videos")) {
			objPosts = db.getNew(type, 50);
			nears = db.getNew(type, 5);
		}
		
		Log.i("DATA", "CONTENT: "+objPost.getContent());
		
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		txtType = (TextView) findViewById(R.id.txt_type);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		
		btnShare = (ImageButton) findViewById(R.id.btn_share);
		
		btnPrev = (Button) findViewById(R.id.btn_prev);
		btnNext = (Button) findViewById(R.id.btn_next);
		
		lsNear = (ListView) findViewById(R.id.list_near);
		pAdapter = new NewPostAdapter(DetailPostActivity.this, nears);
		lsNear.setAdapter(pAdapter);
		Utils.setListViewHeightBasedOnChildren(lsNear);
		
		
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new ViewPagerFeedAdapter(DetailPostActivity.this, objPosts);
		
		pager.setAdapter(adapter);
		
		pager.setCurrentItem(position);
		
		init();
	}
	
	void init(){
		btnBack.setOnTouchListener(this);
		btnShare.setOnTouchListener(this);
		btnNext.setOnTouchListener(this);
		btnPrev.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.btn_back:
			if (event.getAction() == MotionEvent.ACTION_UP){
				closeActivity();
			}
			break;

		case R.id.btn_share:
			if (event.getAction() == MotionEvent.ACTION_UP){
				shareTextUrl();
			}
			break;
		case R.id.btn_prev:
			if (event.getAction() == MotionEvent.ACTION_UP){
				if (position < 1){
					
				}else {
					pager.setCurrentItem(position-1);
					position = position-1;
				}
				
			}
			break;
		case R.id.btn_next:
			if (event.getAction() == MotionEvent.ACTION_UP){
				if (position > 48){
					
				}else {
					pager.setCurrentItem(position+1);
					position = position+1;
				}
				
			}
			break;
		}
		return false;
	}
	
	public void shareUrlFacebook() {
		/*
		 * Facebook - "com.facebook.katana" Twitter - "com.twitter.android"
		 * Instagram - "com.instagram.android" Pinterest - "com.pinterest"
		 */
		// getPackageName() from Context or Activity object
		final String appPackageName = "com.facebook.katana";
		Intent intent = getPackageManager().getLaunchIntentForPackage(
				"com.facebook.katana");
		
		
		if (intent != null) {
			Intent share = new Intent(android.content.Intent.ACTION_SEND);
			share.setType("text/plain");
			share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			// add data
			share.putExtra(Intent.EXTRA_TEXT, objPost.getUrlPost());
			startActivity(Intent.createChooser(share, "Enoguia"));

		} else {

			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appPackageName)));
			}
		}
	}
	
	private void shareTextUrl() {
	    Intent share = new Intent(android.content.Intent.ACTION_SEND);
	    share.setType("text/plain");
	    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	 
	    // Add data to the intent, the receiving app will decide
	    // what to do with it.
	    share.putExtra(Intent.EXTRA_SUBJECT, objPost.getTitle());
	    share.putExtra(Intent.EXTRA_TEXT, objPost.getUrlPost());
	 
	    startActivity(Intent.createChooser(share, "Share link!"));
	}
	
	
	void closeActivity(){
		finish();
		overridePendingTransition(R.anim.over_main_right, R.anim.trans_right_out);
	}
	
	
	@Override
	public void onBackPressed() {
		closeActivity();
	}
}
