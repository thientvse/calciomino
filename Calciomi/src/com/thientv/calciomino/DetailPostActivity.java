package com.thientv.calciomino;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.ViewPagerFeedAdapter;
import com.thientv.slidingmenu.bean.ObjPost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailPostActivity extends Activity implements OnTouchListener{
	ImageButton btnBack;
	
	ObjPost objPost = new ObjPost();
	
	TextView txtType, txtTitle;
	
	ViewPager pager;
	ViewPagerFeedAdapter adapter;
	MySQLiteHelper db;
	
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_post);
		
		db = new MySQLiteHelper(this);
		
		objPost = getIntent().getParcelableExtra("post");
		
		objPosts = db.getHome();
		
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		txtType = (TextView) findViewById(R.id.txt_type);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new ViewPagerFeedAdapter(DetailPostActivity.this, objPosts);
		
		pager.setAdapter(adapter);
		
		
		init();
	}
	
	void init(){
		btnBack.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.btn_back:
			if (event.getAction() == MotionEvent.ACTION_UP){
				finish();
			}
			break;

		default:
			break;
		}
		return false;
	}
}
