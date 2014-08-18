package com.thientv.calciomino;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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
	
	Runnable rS = new Runnable() {
		
		@Override
		public void run() {
			showAd();
		}
	};
	
	private InterstitialAd interstitial;
	private static final String AD_UNIT_ID_INTER = "ca-app-pub-1074033836229689/5368824653";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro_screen);
		
		mHandler.removeCallbacks(rS);
		mHandler.postDelayed(rS, 2000);
	}
	
	void showAd(){
		// Create the interstitial.
	    interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId(AD_UNIT_ID_INTER);

	    // Create ad request.
	    AdRequest adRequest = new AdRequest.Builder()
	    						.addTestDevice("87F3274F1BC1CC1EB640DFA9E6293717")
	    						.build();

	    // Begin loading your interstitial.
	    interstitial.loadAd(adRequest);
	    
	    interstitial.setAdListener(new AdListener() {

			@Override
			public void onAdLoaded() {
				displayInterstitial();
//				mHandler.removeCallbacks(r);
//				mHandler.postDelayed(r, 7000);
			}

			@Override
			public void onAdClosed() {
				mHandler.removeCallbacks(r);
				mHandler.post(r);
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				mHandler.removeCallbacks(r);
				mHandler.post(r);
			}
		});
	    
	   
	}
	
	// Invoke displayInterstitial() when you are ready to display an interstitial.
	  public void displayInterstitial() {
	    if (interstitial.isLoaded()) {
	      interstitial.show();
	    }
	  }
	
	

	void gotoMain(){
		Intent t = new Intent(IntroScreenActivity.this, MainActivity.class);
		finish();
		startActivity(t);
	}
}
