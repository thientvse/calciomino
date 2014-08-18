package com.thientv.calciomino;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

	// admod
	/** The log tag. */
	private static final String LOG_TAG = "InterstitialSample";

	/** Your ad unit id. Replace with your actual ad unit id. */
	private static final String AD_UNIT_ID = "ca-app-pub-1074033836229689/7124759453";

	/** The interstitial ad. */
	private InterstitialAd interstitialAd;

	/** The view to show the ad. */
	private AdView adView;

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

		// mHandler.post(rLong);
		// mHandler.post(rShort);
		// mHandler.post(rVideo);

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
			public void onItemClick(AdapterView<?> adapterView, View v,
					int position, long id) {
				mCurrent = position;
				adapter.notifyDataSetChanged();
				switch (position) {
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

				if (frag != null) {
					replaceFragment(frag);
				} else {
				}

			}

		});

		init();
		
		// intersials
		// Create the interstitial.

//		initAd();
		initAdBanner();

		// Create an ad.
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		// Add the AdView to the view hierarchy. The view will have no size
		// until the ad is loaded.
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.linearLayout);
		layout.addView(adView);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		// phone test
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("87F3274F1BC1CC1EB640DFA9E6293717").build();
		// AC98C820A50B4AD8A2106EDE96FB87D4
		// Start loading the ad in the background.
		adView.loadAd(adRequest);

	}

	void initAdBanner() {

	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	void initAd() {
		// Create an ad.
		interstitialAd = new InterstitialAd(this);
//		interstitialAd.setAdUnitId(AD_UNIT_ID_INTER);

		// Set the AdListener.
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.d(LOG_TAG, "onAdLoaded");
				Toast.makeText(MainActivity.this, "onAdLoaded",
						Toast.LENGTH_SHORT).show();

				// Change the button text and enable the button.
				interstitialAd.show();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				String message = String.format("onAdFailedToLoad (%s)",
						getErrorReason(errorCode));
				Log.d(LOG_TAG, message);
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT)
						.show();

				// Change the button text and disable the button.
			}
		});
	}

	/** Called when the Load Interstitial button is clicked. */
	public void loadInterstitial(View unusedView) {
		// Disable the show button until the new ad is loaded.

		// Check the logcat output for your hashed device ID to get test ads on
		// a physical device.
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4").build();

		// Load the interstitial ad.
		interstitialAd.loadAd(adRequest);
	}

	/** Called when the Show Interstitial button is clicked. */
	public void showInterstitial(View unusedView) {
		// Disable the show button until another interstitial is loaded.

		if (interstitialAd.isLoaded()) {
			interstitialAd.show();
		} else {
			Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
		}
	}

	/** Gets a string error reason from an error code. */
	private String getErrorReason(int errorCode) {
		String errorReason = "";
		switch (errorCode) {
		case AdRequest.ERROR_CODE_INTERNAL_ERROR:
			errorReason = "Internal error";
			break;
		case AdRequest.ERROR_CODE_INVALID_REQUEST:
			errorReason = "Invalid request";
			break;
		case AdRequest.ERROR_CODE_NETWORK_ERROR:
			errorReason = "Network Error";
			break;
		case AdRequest.ERROR_CODE_NO_FILL:
			errorReason = "No fill";
			break;
		}
		return errorReason;
	}

	void init() {
		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toggle();
			}
		});
	}

	protected void replaceFragment(Fragment frag) {
		android.support.v4.app.FragmentTransaction fragmentTransaction = MainActivity.this
				.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, frag);
		fragmentTransaction.commit();
	}

	void addDataMenu() {
		menuItems.add(new MenuItem("Accueil", R.color.color_den));
		menuItems.add(new MenuItem("Brèves", R.color.color_xanh));
		menuItems.add(new MenuItem("Articles", R.color.color_xanh_la));
		menuItems.add(new MenuItem("Vidéos", R.color.color_do));
	}

	public void showMenu() {
		showMenu();
	}

	// ---------------- get article --------------------//
	public void getArticle() {
		HttpClientHelper httpClientHelper = new HttpClientHelper(
				new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error) {
					}

					@Override
					public void onSuccess(JSONArray re) {
						// insert
						for (int i = 0; i < re.length(); i++) {
							try {
								JSONObject jo = re.getJSONObject(i);
								Log.i("DATA", "NEWS: " + jo.toString());
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

	// -------------- get short --------------------//
	public void getShort() {
		HttpClientHelper httpClientHelper = new HttpClientHelper(
				new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error) {
					}

					@Override
					public void onSuccess(JSONArray re) {
						// insert
						for (int i = 0; i < re.length(); i++) {
							try {
								JSONObject jo = re.getJSONObject(i);
								Log.i("DATA", "NEWS: " + jo.toString());
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

	// -------------- get video --------------------//
	public void getVideo() {
		HttpClientHelper httpClientHelper = new HttpClientHelper(
				new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(Throwable error) {
					}

					@Override
					public void onSuccess(JSONArray re) {
						// insert
						for (int i = 0; i < re.length(); i++) {
							try {
								JSONObject jo = re.getJSONObject(i);
								Log.i("DATA", "NEWS: " + jo.toString());
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
