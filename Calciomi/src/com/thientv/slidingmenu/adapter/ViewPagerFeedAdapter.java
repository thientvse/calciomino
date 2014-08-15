package com.thientv.slidingmenu.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.thientv.calciomino.R;
import com.thientv.calciomino.YoutubeActivity;
import com.thientv.slidingmenu.bean.ObjPost;
import com.thientv.slidingmenu.utils.TextViewEx;
import com.thientv.slidingmenu.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		TextViewEx content;
		FrameLayout frYoutube;
		ImageView image_thumbnail;
		ImageButton btnPlay;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.item_viewpager, container, false);
		
		txtType = (TextView) v.findViewById(R.id.txt_type);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		dataTime = (TextView) v.findViewById(R.id.txt_time);
		content = (TextViewEx) v.findViewById(R.id.txt_content);
		frYoutube = (FrameLayout) v.findViewById(R.id.frame_youtube);
		
		image_thumbnail = (ImageView) v.findViewById(R.id.img_thumnail);
		
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
		
		
		txtType.setText(Utils.toOnlyFirstUpcase(objPosts.get(position).getType()));
		if (objPosts.get(position).getType().equals("articles")){
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_la));
		} else if (objPosts.get(position).getType().equals("breves")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_xanh_dam));
		} else if (objPosts.get(position).getType().equals("videos")) {
			txtType.setTextColor(context.getResources().getColor(R.color.color_do));
		}
		
		txtTitle.setText(Html.fromHtml(objPosts.get(position).getTitle()));
		dataTime.setText(objPosts.get(position).getDateDay()+" - "+objPosts.get(position).getDateHour()+" - "+ objPosts.get(position).getAuthor());
		
		URLImageParser p = new URLImageParser(content, context);
		
		Spanned htmlSpan = Html.fromHtml(objPosts.get(position).getContent(), p, null);
		
		content.setText(htmlSpan.toString(), true);
		
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
		
		txtTitle.setTypeface(Utils.getTypefaceBold(context));
		txtType.setTypeface(Utils.getTypefaceBold(context));
		dataTime.setTypeface(Utils.getTypefaceRegular(context));
		content.setTypeface(Utils.getTypefaceRegular(context));
		
		// add viewpager item to viewpager
		((ViewPager) container).addView(v);
		
		return v;
	}
	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}

	public class URLImageParser implements ImageGetter {
	    Context c;
	    View container;

	    /***
	     * Construct the URLImageParser which will execute AsyncTask and refresh the container
	     * @param t
	     * @param c
	     */
	    public URLImageParser(View t, Context c) {
	        this.c = c;
	        this.container = t;
	    }

	    public Drawable getDrawable(String source) {
	        URLDrawable urlDrawable = new URLDrawable();

	        // get the actual source
	        ImageGetterAsyncTask asyncTask = 
	            new ImageGetterAsyncTask( urlDrawable);

	        asyncTask.execute(source);

	        // return reference to URLDrawable where I will change with actual image from
	        // the src tag
	        return urlDrawable;
	    }

	    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
	        URLDrawable urlDrawable;

	        public ImageGetterAsyncTask(URLDrawable d) {
	            this.urlDrawable = d;
	        }

	        @Override
	        protected Drawable doInBackground(String... params) {
	            String source = params[0];
	            return fetchDrawable(source);
	        }

	        @Override
	        protected void onPostExecute(Drawable result) {
	            // set the correct bound according to the result from HTTP call
	            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 
	                    + result.getIntrinsicHeight()); 

	            // change the reference of the current drawable to the result
	            // from the HTTP call
	            urlDrawable.drawable = result;

	            // redraw the image by invalidating the container
	            URLImageParser.this.container.invalidate();
	        }

	        /***
	         * Get the Drawable from URL
	         * @param urlString
	         * @return
	         */
	        public Drawable fetchDrawable(String urlString) {
	            try {
	                InputStream is = fetch(urlString);
	                Drawable drawable = Drawable.createFromStream(is, "src");
	                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
	                        + drawable.getIntrinsicHeight()); 
	                return drawable;
	            } catch (Exception e) {
	                return null;
	            } 
	        }

	        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpGet request = new HttpGet(urlString);
	            HttpResponse response = httpClient.execute(request);
	            return response.getEntity().getContent();
	        }
	    }
	}
	
	public class URLDrawable extends BitmapDrawable {
	    // the drawable that you need to set, you could set the initial drawing
	    // with the loading image if you need to
	    protected Drawable drawable;

	    @Override
	    public void draw(Canvas canvas) {
	        // override the draw to facilitate refresh function later
	        if(drawable != null) {
	            drawable.draw(canvas);
	        }
	    }
	}
	

}
