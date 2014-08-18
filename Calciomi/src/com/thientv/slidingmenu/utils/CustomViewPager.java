package com.thientv.slidingmenu.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {

	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new Detector());
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {

		mGestureDetector.onTouchEvent(motionEvent);
		return super.onTouchEvent(motionEvent);
	}

	class Detector extends SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {

			requestDisallowInterceptTouchEvent(true);

			return false;
		}
	}
}
