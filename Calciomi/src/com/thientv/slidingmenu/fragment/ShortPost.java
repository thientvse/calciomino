package com.thientv.slidingmenu.fragment;

import java.util.ArrayList;

import com.thientv.calciomino.DetailPostActivity;
import com.thientv.calciomino.R;
import com.thientv.calciomino.database.MySQLiteHelper;
import com.thientv.slidingmenu.adapter.NewPostAdapter;
import com.thientv.slidingmenu.bean.ObjPost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShortPost extends Fragment {

	ImageButton btnBack;
	ListView listHome;
	NewPostAdapter adapter;
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	
	MySQLiteHelper db;
	
	void getDataFromDB(){
		objPosts = db.getNew("breves");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frg_short, container, false);

		db = new MySQLiteHelper(getActivity());
		
		btnBack = (ImageButton) v.findViewById(R.id.btn_back);
//		addDataDemo();
		getDataFromDB();
		
		listHome = (ListView) v.findViewById(R.id.list_short);
		adapter = new NewPostAdapter(getActivity(), objPosts);
		listHome.setAdapter(adapter);

		listHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Intent t = new Intent(getActivity(), DetailPostActivity.class);
				startActivity(t);
			}

		});

		return v;
	}

	void addDataDemo() {
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014",
				"Retour sur la performance du Milan AC face à Manchester City : "));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014",
				"Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014",
				"Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014",
				"Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014",
				"Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014",
				"Tavecchio Ex-Futur président de la fédération italienne ?"));
	}

}
