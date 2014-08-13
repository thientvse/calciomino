package com.thientv.slidingmenu.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.thientv.calciomino.R;
import com.thientv.slidingmenu.adapter.NewPostAdapter;
import com.thientv.slidingmenu.bean.ObjPost;

public class Home extends Fragment {

	ImageButton btnBack;
	ListView listHome;
	NewPostAdapter adapter;
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frg_home, container, false);

		
		addDataDemo();
		listHome = (ListView) v.findViewById(R.id.list_home);
		adapter = new NewPostAdapter(getActivity(), objPosts);
		listHome.setAdapter(adapter);

		return v;
	}
	
	void addDataDemo(){
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Retour sur la performance du Milan AC face à Manchester City : "));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("VIDEO", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
		objPosts.add(new ObjPost("SHORT", "13/08/2014", "Le splendide lob de Pjanic contre Manchester"));
		objPosts.add(new ObjPost("ARTICLE", "13/08/2014", "Tavecchio Ex-Futur président de la fédération italienne ?"));
	}

	void init() {
	}

}
