package com.thientv.slidingmenu.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	private GestureDetector mGestureDetector;
	private boolean mScrollable = true;
	
	// set cho scroll cuon hay khong cuon
	public void setScrollEnable(boolean enable) {
		mScrollable = enable;
	}
	
	public boolean isScrollable() {
		 return mScrollable;
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // if we can scroll pass the event to the superclass
            if (mScrollable) return super.onTouchEvent(ev);
            // only continue to handle the touch event if scrolling enabled
            return mScrollable; // mScrollable is always false at this point
        default:
            return super.onTouchEvent(ev);
    }
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!mScrollable) return false;
		else {
			return super.onInterceptTouchEvent(ev)
					&& mGestureDetector.onTouchEvent(ev);
		}
		
	}

	// Return false if we're scrolling in the x direction
	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return (Math.abs(distanceY) > Math.abs(distanceX));
		}
	}
}
