package com.thientv.slidingmenu;

import java.util.ArrayList;

import com.slidingmenu.lib.app.SlidingActivity;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.thientv.calciomino.R;
import com.thientv.slidingmenu.adapter.MenuAdapter;
import com.thientv.slidingmenu.bean.MenuItem;
import com.thientv.slidingmenu.fragment.Home;
import com.thientv.slidingmenu.fragment.LongPost;
import com.thientv.slidingmenu.fragment.ShortPost;
import com.thientv.slidingmenu.fragment.VideoPost;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends SlidingFragmentActivity {

	ListView listMenu;
	MenuAdapter adapter;
	ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	Fragment frag = null;
	
	ImageButton btnback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setBehindContentView(R.layout.activity_menu);

		getSlidingMenu().setBehindOffset(100);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setShadowWidth((int) 10f);
		
		
		addDataMenu();
		
		
		btnback = (ImageButton) findViewById(R.id.btn_back);
		listMenu = (ListView) findViewById(R.id.list_menu);
		adapter = new MenuAdapter(MainActivity.this, menuItems);
		listMenu.setAdapter(adapter);
		
		frag = new Home();
    	replaceFragment(frag);
		
		
		listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				

		        switch(position)
		        {
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

		        if (frag != null){
		        	replaceFragment(frag);	
		        } else {
				}
		            
			}
			
		});
		
		init();
	}
	
	void init(){
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				toggle(); 	
			}
		});
	}
	
	protected void replaceFragment(Fragment frag)
    {
        android.support.v4.app.FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, frag);
        fragmentTransaction.commit();
    }

	void addDataMenu() {
		menuItems.add(new MenuItem("Home", R.color.color_den));
		menuItems.add(new MenuItem("Long Post", R.color.color_xanh));
		menuItems.add(new MenuItem("Short Post", R.color.color_xanh_la));
		menuItems.add(new MenuItem("Video Post", R.color.color_do));
	}
	
	public void showMenu(){
		showMenu();
	}
}
