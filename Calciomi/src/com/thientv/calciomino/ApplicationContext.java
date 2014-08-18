package com.thientv.calciomino;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ApplicationContext extends Application {
	/**
	 * Keeps a reference of the application context
	 */
	private static Context sContext;
	
	
	/*
	 * Google Analytics configuration values.
	 */
	// Placeholder property ID.
	private static final String GA_PROPERTY_ID = "UA-50113840-3";

	// Dispatch period in seconds.
	private static final int GA_DISPATCH_PERIOD = 30;

	// Prevent hits from being sent to reports, i.e. during testing.
	private static final boolean GA_IS_DRY_RUN = false;

	
	

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
		
		// config image loader
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		 if (!cacheDir.exists())
	            cacheDir.mkdir();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.defaultDisplayImageOptions(defaultOptions)
//		 	.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
	        .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 70, null)
	        .threadPoolSize(2) // default
	        .threadPriority(Thread.NORM_PRIORITY - 1) // default
	        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
	        .denyCacheImageMultipleSizesInMemory()
//	        .memoryCache(new WeakMemoryCache())
	        .memoryCache(new FIFOLimitedMemoryCache(15 * 1024 * 1024))
	        .discCache(new TotalSizeLimitedDiscCache(cacheDir, 300 * 1024 * 1024))
//	        .discCache(new UnlimitedDiscCache(cacheDir))
	        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
	        .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
	        .imageDecoder(new BaseImageDecoder(false)) // default
	        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
	        .writeDebugLogs()
	        .build();
		
		ImageLoader.getInstance().init(config);
		
	}

	/**
	 * Returns the application context
	 * 
	 * @return application context
	 */
	public static Context getContext() {
		return sContext;
	}
	
	
	
	
	
}
