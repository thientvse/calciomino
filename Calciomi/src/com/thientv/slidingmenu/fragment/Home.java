package com.thientv.slidingmenu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.thientv.calciomino.R;

public class Home extends Fragment implements OnClickListener{
	
	ImageButton btnBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frg_home, container, false);
        
		btnBack = (ImageButton) v.findViewById(R.id.btn_back);
		
		init();
        return v;
	}
	
	
	void init(){
		btnBack.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			
			break;

		default:
			break;
		}
	}
	
	
	
}
