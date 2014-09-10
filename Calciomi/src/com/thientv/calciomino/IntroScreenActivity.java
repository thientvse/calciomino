package com.thientv.calciomino;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.thientv.slidingmenu.utils.PreferenceHelper;

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
	
	PreferenceHelper preferenceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro_screen);
		
		mHandler.removeCallbacks(rS);
		mHandler.postDelayed(rS, 2000);
		preferenceHelper = new PreferenceHelper(IntroScreenActivity.this);
		
		registerGCM();
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
				/*mHandler.removeCallbacks(r);
				mHandler.postDelayed(r, 5000);*/
			}

			@Override
			public void onAdClosed() {
				gotoMain();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				gotoMain();
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
	
	
	void registerGCM() {
		if (Build.FINGERPRINT.startsWith("generic")) {
			 // running on an emulator
				Log.e("EMULATOR", "======================");
			} else {
			// running on a device
			// Make sure the device has the proper dependencies.
				GCMRegistrar.checkDevice(IntroScreenActivity.this);
				
			// Make sure the manifest was properly set - comment out this line
			// while developing the app, then uncomment it when it's ready.	
				GCMRegistrar.checkManifest(IntroScreenActivity.this);
				
				String regId = GCMRegistrar.getRegistrationId(IntroScreenActivity.this);
				
				// Check if regid already presents
				if (regId.equals("")) {
					GCMRegistrar.register(this, com.thientv.slidingmenu.utils.Common.PROJECT_NUMBER);
				}
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String regId="";
						
						while (regId.equals("")) {
							regId = GCMRegistrar.getRegistrationId(IntroScreenActivity.this);
							Log.i("vChat", "Registered with id "+regId);
							preferenceHelper.setRegId(regId);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
	}
	
}
