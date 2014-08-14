package com.thientv.slidingmenu.adapter;

import java.util.ArrayList;
import java.util.List;

import com.thientv.calciomino.R;
import com.thientv.slidingmenu.bean.ObjPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewPostAdapter extends ArrayAdapter<ObjPost> {
	
	Context context;
	ArrayList<ObjPost> ObjPosts = new ArrayList<ObjPost>();

	public NewPostAdapter(Context context, ArrayList<ObjPost> ObjPosts) {
		super(context, R.layout.item_post, ObjPosts);
		this.context = context;
		this.ObjPosts = ObjPosts;
	}

	@Override
	public int getCount() {
		return ObjPosts.size();
	}

	@Override
	public ObjPost getItem(int position) {
		return ObjPosts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		Holder holder = null;
		if (v==null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.item_post, parent, false);
			holder = new Holder();

			holder.image = (ImageView) v.findViewById(R.id.img_avatar);
			holder.txtCategory = (TextView) v.findViewById(R.id.txt_category);
			holder.time = (TextView) v.findViewById(R.id.txt_time_ago);
			holder.shortContent = (TextView) v.findViewById(R.id.txt_short_content);
			
			
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		
		holder.txtCategory.setText(ObjPosts.get(position).getType());
		holder.time.setText(ObjPosts.get(position).getDateDay());
		holder.shortContent.setText(ObjPosts.get(position).getTitle());
		
		
		return v;
	}
	
	class Holder{
		TextView txtCategory, time, shortContent;
		ImageView image;
		
	}
	
	

}
