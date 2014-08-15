package com.thientv.slidingmenu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class Utils {
	public static Typeface tfTextView1;
	public static Typeface tfTextView2;
	public static Typeface tfTextView3;
	public static Typeface tfTextView4;
	public static Typeface tfTextView5;

	private static DisplayMetrics getDisplayMetrics(Context paramContext) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) paramContext).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics;
	}
	public static String convertDate(String paramString1, String paramString2) {
		return DateFormat.format(paramString2, Long.parseLong(paramString1))
				.toString();
	}

	public static String convert_Film_Duration(String paramString) {
		return "0";
	}

	public static String convert_long_to_date(long paramLong) {
		Date localDate = new Date(paramLong);
		return new SimpleDateFormat("dd-MM-yyyy").format(localDate);
	}

	public static AlertDialog.Builder createInfoDialog(Context paramContext,
			String paramString1, String paramString2,
			DialogInterface.OnClickListener paramOnClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder.setTitle(paramString1);
		localBuilder.setIcon(17301659);
		localBuilder.setMessage(paramString2);
		localBuilder.setPositiveButton(paramContext.getString(2130968621),
				paramOnClickListener);
		localBuilder.setCancelable(false);
		return localBuilder;
	}

	public static String durationInSecondsToString(int paramInt) {
		int i = paramInt / 3600;
		int j = paramInt / 60 - i * 60;
		int k = paramInt - i * 3600 - j * 60;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = Integer.valueOf(i);
		arrayOfObject[1] = Integer.valueOf(j);
		arrayOfObject[2] = Integer.valueOf(k);
		return String.format("%d:%02d:%02d", arrayOfObject);
	}

	public static String formatString(String paramString) {
		DecimalFormat localDecimalFormat = new DecimalFormat();
		DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols();
		localDecimalFormatSymbols.setGroupingSeparator('.');
		localDecimalFormat.setGroupingSize(3);
		localDecimalFormat.setGroupingUsed(true);
		localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
		return localDecimalFormat.format(Integer.valueOf(paramString));
	}

	public static String getClassNameFromFullName(String paramString) {
		return paramString.substring(1 + paramString.lastIndexOf("."));
	}

	public static float getDensity(Context paramContext) {
		return getDisplayMetrics(paramContext).density;
	}

	public static String getPriceBy(double paramDouble, String paramString) {
		DecimalFormat localDecimalFormat = (DecimalFormat) NumberFormat
				.getNumberInstance(Locale.GERMANY);
		localDecimalFormat.applyPattern("###,###.###");
		return localDecimalFormat.format(paramDouble) + paramString;
	}

	public static Bitmap getRoundedCornerBitmap(Context paramContext,
			Bitmap paramBitmap, int paramInt) {
		Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(),
				paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		RectF localRectF = new RectF(new Rect(0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight()));
		float f = paramInt;
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawRoundRect(localRectF, f, f, localPaint);
		localCanvas
				.drawRect(paramBitmap.getWidth() / 2, 0.0F,
						paramBitmap.getWidth(), paramBitmap.getHeight() / 2,
						localPaint);
		localCanvas.drawRect(paramBitmap.getWidth() / 2,
				paramBitmap.getWidth() / 2, paramBitmap.getWidth(),
				paramBitmap.getHeight(), localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
		return localBitmap;
	}

	public static Bitmap getRoundedCornerBitmap(Context paramContext,
			Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3,
			boolean paramBoolean1, boolean paramBoolean2,
			boolean paramBoolean3, boolean paramBoolean4) {
		Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt3,
				Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		float f1 = paramContext.getResources().getDisplayMetrics().density;
		Paint localPaint = new Paint();
		RectF localRectF = new RectF(new Rect(0, 0, paramInt2, paramInt3));
		float f2 = f1 * paramInt1;
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawRoundRect(localRectF, f2, f2, localPaint);
		if (paramBoolean1)
			localCanvas.drawRect(0.0F, 0.0F, paramInt2 / 2, paramInt3 / 2,
					localPaint);
		if (paramBoolean2)
			localCanvas.drawRect(paramInt2 / 2, 0.0F, paramInt2, paramInt3 / 2,
					localPaint);
		if (paramBoolean3)
			localCanvas.drawRect(0.0F, paramInt3 / 2, paramInt2 / 2, paramInt3,
					localPaint);
		if (paramBoolean4)
			localCanvas.drawRect(paramInt2 / 2, paramInt3 / 2, paramInt2,
					paramInt3, localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
		return localBitmap;
	}

	public static int getScreenHeigh(Context paramContext) {
		return getDisplayMetrics(paramContext).heightPixels;
	}

	public static float getScreenHeighY(Context paramContext) {
		return getDisplayMetrics(paramContext).ydpi;
	}

	public static int getScreenWidth(Context paramContext) {
		return getDisplayMetrics(paramContext).widthPixels;
	}

	public static Typeface getTypefaceBlack(Context paramContext) {
		if (tfTextView1 == null)
			tfTextView1 = Typeface.createFromAsset(paramContext.getAssets(),
					"fonts/Roboto-Black.ttf");
		return tfTextView1;
	}
	
	
	public static Typeface getTypefaceLight(Context paramContext) {
		if (tfTextView2 == null)
			tfTextView2 = Typeface.createFromAsset(paramContext.getAssets(),
					"fonts/Roboto-Light.ttf");
		return tfTextView2;
	}

	public static Typeface getTypefaceRegular(Context paramContext) {
		if (tfTextView3 == null)
			tfTextView3 = Typeface.createFromAsset(paramContext.getAssets(),
					"fonts/Roboto-Regular.ttf");
		return tfTextView3;
	}

	public static Typeface getTypefaceBold(Context paramContext) {
		if (tfTextView4 == null)
			tfTextView4 = Typeface.createFromAsset(paramContext.getAssets(),
					"fonts/Roboto-Bold.ttf");
		return tfTextView4;
	}

	public static Typeface getTypefaceMedium(Context paramContext) {
		if (tfTextView5 == null)
			tfTextView5 = Typeface.createFromAsset(paramContext.getAssets(),
					"fonts/Roboto-Medium.ttf");
		return tfTextView5;
	}

	public static boolean isEmpty(String paramString) {
		return (paramString == null) || (paramString.equals(""));
	}

	public static boolean isOnline(Context paramContext) {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext
				.getSystemService("connectivity");
		try {
			localConnectivityManager.getActiveNetworkInfo();
			NetworkInfo localNetworkInfo1 = localConnectivityManager
					.getNetworkInfo(0);
			NetworkInfo localNetworkInfo2 = localConnectivityManager
					.getNetworkInfo(1);
			if ((localNetworkInfo1 == null)
					|| (!localNetworkInfo1.isAvailable())
					|| (!localNetworkInfo1.isConnectedOrConnecting())) {
				if ((localNetworkInfo2 != null)
						&& (localNetworkInfo2.isAvailable())) {
					boolean bool = localNetworkInfo2.isConnectedOrConnecting();
					if (!bool)
						;
				}
			} else
				return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return false;
	}

	public static String md5(String paramString) {
		if (paramString == null)
			return "";
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			byte[] arrayOfByte = localMessageDigest.digest();
			StringBuffer localStringBuffer = new StringBuffer();
			int i = 0;
			if (i >= arrayOfByte.length)
				return localStringBuffer.toString();
			String str;
			for (Object localObject = Integer
					.toHexString(0xFF & arrayOfByte[i]);; localObject = str) {
				if (((String) localObject).length() >= 2) {
					localStringBuffer.append((String) localObject);
					i++;
					break;
				}
				str = "0" + (String) localObject;
			}
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			localNoSuchAlgorithmException.printStackTrace();
		}
		return "";
	}

	public static String setCash(Context paramContext, String paramString) {
		int i = 8 + formatString(paramString).length();
		SpannableString localSpannableString = new SpannableString(paramContext
				.getResources().getString(2130968616)
				+ " "
				+ formatString(paramString)
				+ " "
				+ paramContext.getResources().getString(2130968617));
		localSpannableString.setSpan(new StyleSpan(1), 8, i, 0);
		localSpannableString.setSpan(
				new ForegroundColorSpan(Color.parseColor("#933233")), 8, i, 0);
		return localSpannableString.toString();
	}

	public static String formatDate(String input) {
		SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy");
		Date dateObj = null;
		try {
			dateObj = curFormater.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
		String newInput = postFormater.format(dateObj);
		return newInput;
	}

	public static String checkDigit(int number) {
		return number <= 9 ? "0" + number : String.valueOf(number);
	}

	private static final int UNCONSTRAINED = -1;

	// Rotates the bitmap by the specified degree.
	// If a new bitmap is created, the original bitmap is recycled.
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	public static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, boolean scaleUp) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		/*
		 * float bitmapAspect = bitmapWidthF / bitmapHeightF; float viewAspect =
		 * (float) targetWidth / targetHeight;
		 * 
		 * if (bitmapAspect > viewAspect) { float scale = targetHeight /
		 * bitmapHeightF; if (scale < .9F || scale > 1F) {
		 * scaler.setScale(scale, scale); } else { scaler = null; } } else {
		 * float scale = targetWidth / bitmapWidthF; if (scale < .9F || scale >
		 * 1F) { scaler.setScale(scale, scale); } else { scaler = null; } }
		 */

		float sx = (float) targetWidth / bitmapWidthF;
		float sy = (float) targetHeight / bitmapHeightF;
		if ((sx == sy) && (sx == 1F)) {
			scaler = null;
		} else {
			scaler.setScale((float) targetWidth / bitmapWidthF,
					(float) targetHeight / bitmapHeightF);
		}
		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b1 != source) {
			b1.recycle();
		}

		return b2;
	}

	public static final Bitmap resizeBitmap(Bitmap bitmap, int maxSize) {
		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();
		int width = maxSize;
		int height = maxSize;
		boolean needsResize = false;
		if (srcWidth > srcHeight) {
			if (srcWidth > maxSize) {
				needsResize = true;
				height = ((maxSize * srcHeight) / srcWidth);
			}
		} else {
			if (srcHeight > maxSize) {
				needsResize = true;
				width = ((maxSize * srcWidth) / srcHeight);
			}
		}
		if (needsResize) {
			Bitmap retVal = Bitmap.createScaledBitmap(bitmap, width, height,
					true);
			return retVal;
		} else {
			return bitmap;
		}
	}

	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	public static void Copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);
		copyStream(in, out);
	}

	public static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/*
	 * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that is tolerable in terms of memory usage.
	 * 
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 * 
	 * Also, the function rounds up the sample size to a power of 2 or multiple
	 * of 8 because BitmapFactory only honors sample size this way. For example,
	 * BitmapFactory downsamples an image by 2 even though the request is 3. So
	 * we round up the sample size to avoid OOM.
	 */
	public static int computeSampleSize(InputStream stream, int maxResolutionX,
			int maxResolutionY) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(stream, null, options);
		int maxNumOfPixels = maxResolutionX * maxResolutionY;
		int minSideLength = Math.min(maxResolutionX, maxResolutionY) / 2;
		return computeSampleSize(options, minSideLength, maxNumOfPixels);
	}

	private static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
				.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
				.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED)
				&& (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static Bitmap decodeImage(String file) {
		if (file == null)
			return null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			options.outWidth = -1;
			options.outHeight = -1;
			options.inSampleSize = Utils.computeSampleSize(new FileInputStream(
					file), options.outWidth, options.outHeight);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		Bitmap b = null;
		try {
			b = BitmapFactory.decodeFile(file, options);
		} catch (java.lang.OutOfMemoryError e) {
			e.printStackTrace();
		}
		return b;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
	
	public static String createStringDate(String dateInString) {
		String dateOut = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MMM/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
		try {
	 
			Date date = sdf.parse(dateInString);
			dateOut = sdf1.format(date);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateOut;
	}
	
	public static long convertStringToLong(String input) {
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.");
		Date date = sdf.parse(input);*/
		long output = 0;
		try {
			output = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(input).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	
	public static String getTimeAgoAsString(long time) {
    	String timestr = "";
        if (time<60)
        	timestr = "vài giây trước";
        else if (time<60*60)
        	timestr = (time/60) + " phút trước";
        else if (time<60*60*24)
        	timestr = (time/(60*60)) + " gi�? trước";
       /* else if (time<60*60*24*7)
        	timestr = (time/(60*60*24)) + " ngÃ y trÆ°á»›c";*/
        else if (time<60*60*24*7*30)
        	timestr = (time/(60*60*24)) + " ngày trước";
        else if (time<60*60*24*7*30*12)
        	timestr = (time/(60*60*24*7*30)) + " tháng trước";
        else
            timestr = (time/(60*60*24*365)) + " năm trước";
    	return timestr;
	}
	
	
	public static long getCurrentTime() {
		return ((System.currentTimeMillis())/1000);
	 }
	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis());
	}
	
	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
	
	// viet hoa sau dau cach
	public static String toFirstLetterUpcase(String s) {
        String[] arr = s.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
        sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
        }          
      return sb.toString().trim();
    } 
	
	// viet hoa chu cai dau
	public static String toOnlyFirstUpcase( String s) {
		return  s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
	
	// dao nguoc chuoi
	public static String reverseNL(String s) {
	    if (s.length() <= 1) { 
	        return s;
	    }
	    return reverseNL(s.substring(1, s.length())) + s.charAt(0);
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
		.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
		}
	
	// time stamp to date
	public static String convertStringToDate(String time) throws ParseException{
		long dv = Long.valueOf(time)*1000;// its need to be in milisecond
		Date df = new java.util.Date(dv);
//		String vv = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);
		String vv = new SimpleDateFormat("dd/MM/yyyy").format(df);
		return vv;
	}
	
	public static String MD5(String text) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(text.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String checkTextHttp(String input){
		String output = null;
		if (input.startsWith("http://")){
			
		}
		return output;
	}
	
	//get imei
	public static String getIMEI(Context context){
	    TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); 
	    String imei = mngr.getDeviceId();
	    return imei;
	}
	
	
	
	
	
}