package com.thientv.slidingmenu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	private boolean enabled;
	private float xDistance, yDistance, lastX, lastY;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            xDistance = yDistance = 0f;
            lastX = event.getX();
            lastY = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float curX = event.getX();
            final float curY = event.getY();
            xDistance += Math.abs(curX - lastX);
            yDistance += Math.abs(curY - lastY);
            lastX = curX;
            lastY = curY;
            if(xDistance > yDistance)
                return false;
    }
		if (this.enabled) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptHoverEvent(event);
		}
		return false;
	}

	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
