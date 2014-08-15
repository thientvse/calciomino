package com.thientv.calciomino;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

	 public static final String API_KEY = "AIzaSyBfbZVPMbOBp2Z9Q-RBTAOxgZaHdvjhXRU";
	 public String VIDEO_ID = "";
	 YouTubePlayerView youTubePlayerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youtube);
		
		VIDEO_ID = getIntent().getStringExtra("video_id");
		
		Log.i("DATA", "Video id: "+VIDEO_ID);
		
		youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeplayerview);
        youTubePlayerView.initialize(API_KEY, this);
		
	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		Toast.makeText(getApplicationContext(), 
			    "onInitializationFailure()", 
			    Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean wasRestored) {
		
		if (!wasRestored) {
	        player.cueVideo(VIDEO_ID);
	      }
		
	}
}
