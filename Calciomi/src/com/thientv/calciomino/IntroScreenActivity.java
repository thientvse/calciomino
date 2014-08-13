package com.thientv.calciomino;

import com.thientv.slidingmenu.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class IntroScreenActivity extends Activity {
	
	
	Handler mHandler = new Handler();
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			gotoMain();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro_screen);
		
		mHandler.removeCallbacks(r);
		mHandler.postDelayed(r, 3000);
	}
	
	
	void gotoMain(){
		Intent t = new Intent(IntroScreenActivity.this, MainActivity.class);
		finish();
		startActivity(t);
	}
}
