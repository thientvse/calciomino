package com.thientv.slidingmenu.adapter;

import java.util.ArrayList;

import com.thientv.calciomino.R;
import com.thientv.slidingmenu.bean.ObjPost;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerFeedAdapter extends PagerAdapter{
	
	Context context;
	
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	LayoutInflater inflater;
	
	public ViewPagerFeedAdapter(Context context, ArrayList<ObjPost> objPosts) {
		this.context = context;
		this.objPosts = objPosts;
	}
	
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout)object);
	}
	
	@Override
	public int getCount() {
		return objPosts.size();
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TextView txtType;
		TextView txtTitle;
		TextView dataTime;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_viewpager, container, false);
		
		txtType = (TextView) v.findViewById(R.id.txt_type);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		dataTime = (TextView) v.findViewById(R.id.txt_time);
		
		
		txtType.setText(objPosts.get(position).getType());
		if (objPosts.get(position).getType().equals("articles")){
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_la));
		} else if (objPosts.get(position).getType().equals("breves")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_dam));
		} else if (objPosts.get(position).getType().equals("videos")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_do));
		}
		
		txtTitle.setText(Html.fromHtml(objPosts.get(position).getTitle()));
		dataTime.setText(objPosts.get(position).getDateDay());
		
		
		// add viewpager item to viewpager
		((ViewPager) container).addView(v);
		
		return v;
	}
	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}

	
	

}
