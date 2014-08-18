package com.thientv.slidingmenu.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.thientv.calciomino.DetailPostActivity;
import com.thientv.calciomino.MainActivity;
import com.thientv.calciomino.R;
import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.NewPostAdapter;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.utils.PreferenceHelper;

public class Home extends Fragment {

	ImageButton btnBack;
	ListView listHome;
	NewPostAdapter adapter;
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	
	MySQLiteHelper db;
	
	void getDataFromDB(){
		objPosts = db.getHome(50);
	}
	
	private BroadcastReceiver mReceiver;
	
	ProgressDialog d;
	
	Handler mHandler = new Handler();
	PreferenceHelper preferenceHelper;
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			
			Log.i("COUNT", "DA load"+MainActivity.countCall);
			
			if (MainActivity.countCall == 3){
				d.dismiss();
				objPosts = db.getHome(50);
				adapter = new NewPostAdapter(getActivity(), objPosts);
				listHome.setAdapter(adapter);
			} else {
				mHandler.removeCallbacks(r);
				mHandler.postDelayed(r, 2000);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frg_home, container, false);

		db = new MySQLiteHelper(getActivity());
		
		
		btnBack = (ImageButton) v.findViewById(R.id.btn_back);
		
		listHome = (ListView) v.findViewById(R.id.list_home);
		
		preferenceHelper = new PreferenceHelper(getActivity());
		
		d = new ProgressDialog(getActivity());
		d.setMessage("Retrieving data for the first time...");
		if (db.getHome(50).size() < 1){
			d.show();
			mHandler.removeCallbacks(r);
			mHandler.postDelayed(r, 2000);
		} else {
			d.dismiss();
			getDataFromDB();
		}
		
		
		
//		addDataDemo();
		
		adapter = new NewPostAdapter(getActivity(), objPosts);
		listHome.setAdapter(adapter);
		
		listHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent t = new Intent(getActivity(), DetailPostActivity.class);
				t.putExtra("post", objPosts.get(position));
				t.putExtra("type", "home");
				t.putExtra("postion", position);
				startActivity(t);
				getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.over_main_left);
			}
			
		});
		

		return v;
	}
	
	
	


	private void recivedBroadcast(){
		
		Log.i("DATA", "Nhan duoc broadcast");
		
		 IntentFilter intentFilter = new IntentFilter();
		 intentFilter.addAction("RELOAD");
		    
		    mReceiver = new BroadcastReceiver() {
				
				@Override
				public void onReceive(Context context, Intent intent) {
					
					
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							d.dismiss();
							objPosts = db.getHome(50);
							
							adapter = new NewPostAdapter(getActivity(), objPosts);
							listHome.setAdapter(adapter);
							
						}
					});
				}
			};
			
			// register
			getActivity().registerReceiver(mReceiver, intentFilter);
	}
	

	void init() {
	}


}
