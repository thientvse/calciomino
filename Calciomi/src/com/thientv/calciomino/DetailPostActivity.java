package com.thientv.calciomino;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class DetailPostActivity extends Activity implements OnTouchListener{
	ImageButton btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_post);
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		
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
