package com.thientv.slidingmenu.adapter;

import java.util.ArrayList;

import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.thientv.calciomino.R;
import com.thientv.calciomino.YoutubeActivity;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerFeedAdapter extends PagerAdapter{
	
	Context context;
	
	ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
	LayoutInflater inflater;
	
	String url = "http://img.youtube.com/vi/<insert-youtube-video-id-here>/maxresdefault.jpg";
	
	public static final String API_KEY = "AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
	
	DisplayImageOptions options = new DisplayImageOptions.Builder()
								.cacheInMemory(false).cacheOnDisc(true)
								.bitmapConfig(Config.RGB_565)
								.displayer(new FadeInBitmapDisplayer(300))
								.build();
	
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
	public Object instantiateItem(ViewGroup container, final int position) {
		TextView txtType;
		TextView txtTitle;
		TextView dataTime;
		TextView content;
		FrameLayout frYoutube;
		ImageView image_thumbnail;
		ImageButton btnPlay;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_viewpager, container, false);
		
		txtType = (TextView) v.findViewById(R.id.txt_type);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		dataTime = (TextView) v.findViewById(R.id.txt_time);
		content = (TextView) v.findViewById(R.id.txt_content);
		frYoutube = (FrameLayout) v.findViewById(R.id.frame_youtube);
		
		image_thumbnail = (ImageView) v.findViewById(R.id.img_thumnail);
		
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
		
		
		txtType.setText(Utils.toOnlyFirstUpcase(objPosts.get(position).getType()));
		if (objPosts.get(position).getType().equals("breves")){
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_la));
		} else if (objPosts.get(position).getType().equals("articles")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_dam));
		} else if (objPosts.get(position).getType().equals("videos")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_do));
		}
		
		txtTitle.setText(Html.fromHtml(objPosts.get(position).getTitle()));
		dataTime.setText(objPosts.get(position).getDateDay()+" - "+objPosts.get(position).getDateHour()+" - "+ objPosts.get(position).getAuthor());
		
		content.setText(Html.fromHtml(objPosts.get(position).getContent()));
		
		if (objPosts.get(position).getUrlVideo().equals("")){
			frYoutube.setVisibility(View.GONE);
		} else {
			frYoutube.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage("http://img.youtube.com/vi/"+objPosts.get(position).getUrlVideo()+"/mqdefault.jpg", image_thumbnail, options);
			
			btnPlay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent t = new Intent(context, YoutubeActivity.class);
					t.putExtra("video_id", objPosts.get(position).getUrlVideo());
					context.startActivity(t);
				}
			});
			
		}
		
		// add viewpager item to viewpager
		((ViewPager) container).addView(v);
		
		return v;
	}
	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}

	
	

}
