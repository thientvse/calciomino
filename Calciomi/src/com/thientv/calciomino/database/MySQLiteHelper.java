package com.thientv.calciomino.database;

import java.util.ArrayList;

import com.thientv.slidingmenu.bean.ObjPost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = MySQLiteHelper.class.getSimpleName();
//	public static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+"/vChat.db";
	public static final String DATABASE_NAME = "Calciomino.db";
	public static final int DATABASE_VERSION = 20;

	
	// tao cac truong
	public static final String TABLE_NEWS = "home";
	
	public static final String ID = "_id";
	public static final String KEY_ID_NEW = "id_new"; // id_new
	public static final String KEY_TYPE = "type"; // type
	public static final String KEY_URL = "url"; // url post
	public static final String KEY_URL_IMAGE = "image"; // url post
	public static final String KEY_AUTHOR = "author"; // author
	public static final String KEY_DATE_UNIX = "date_unix"; // date_day
	public static final String KEY_DATE_DAY = "date_day"; // date_day
	public static final String KEY_DATE_HOUR = "date_hour"; // date_hour
	
	public static final String KEY_TITLE = "title"; // title
	public static final String KEY_CONTENT = "content"; // content
	public static final String KEY_VIDEO_URL = "video"; // content
	
	// tao bang message
	String CREATE_TABLE_NEW = "CREATE TABLE IF NOT EXISTS " + TABLE_NEWS + "("+ ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_ID_NEW+ " INTEGER, "+KEY_TYPE+ " TEXT, " +KEY_URL+ " TEXT, " + KEY_URL_IMAGE + " TEXT, "
             + KEY_AUTHOR + " TEXT, "+KEY_DATE_UNIX + " INTEGER, " + KEY_DATE_DAY + " TEXT, " + KEY_DATE_HOUR + " TEXT, "+ KEY_TITLE+ " TEXT, "+KEY_CONTENT+ " TEXT, "+KEY_VIDEO_URL+ " TEXT"+")";
	
	
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NEW);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
		onCreate(db);
	}
	
	//------------------------------------------insert------------//
	public void insertNew(ObjPost objPost){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID_NEW, objPost.getId());
		values.put(KEY_TYPE, objPost.getType());
		values.put(KEY_URL, objPost.getUrlPost());
		values.put(KEY_URL_IMAGE, objPost.getUrlImage());
		values.put(KEY_AUTHOR, objPost.getAuthor());
		values.put(KEY_DATE_UNIX, objPost.getDateUnix());
		values.put(KEY_DATE_DAY, objPost.getDateDay());
		values.put(KEY_DATE_HOUR, objPost.getDateHour());
		values.put(KEY_TITLE, objPost.getTitle());
		values.put(KEY_CONTENT, objPost.getContent());
		values.put(KEY_VIDEO_URL, objPost.getUrlVideo());
		
		int count = db.update(TABLE_NEWS, values, KEY_ID_NEW + " = '"+objPost.getId()+"'", null);
		
		if (count == 0){
			db.insert(TABLE_NEWS, null, values);
		}
		db.close();
		
	}
	
	//-------------------getdata-----------------//
	public ArrayList<ObjPost> getNew(String type, int limit){
		
		ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM "+TABLE_NEWS + " WHERE "+KEY_TYPE+" = '"+type+"'"+ " ORDER BY "+KEY_DATE_UNIX+ " DESC LIMIT "+limit ;
		Log.i("SQL", "SQL: "+query);
		
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()){
			do {
				ObjPost objPost = new ObjPost();
				
				objPost.setId(c.getInt(c.getColumnIndex(KEY_ID_NEW)));
				objPost.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
				objPost.setUrlPost(c.getString(c.getColumnIndex(KEY_URL)));
				objPost.setUrlImage(c.getString(c.getColumnIndex(KEY_URL_IMAGE)));
				objPost.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
				objPost.setDateUnix(c.getInt(c.getColumnIndex(KEY_DATE_UNIX)));
				objPost.setDateDay(c.getString(c.getColumnIndex(KEY_DATE_DAY)));
				objPost.setDateHour(c.getString(c.getColumnIndex(KEY_DATE_HOUR)));
				objPost.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
				objPost.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
				objPost.setUrlVideo(c.getString(c.getColumnIndex(KEY_VIDEO_URL)));
				
				objPosts.add(objPost);
				
			} while (c.moveToNext());
			
		}
		c.close();
		db.close();
		
		return objPosts;
	}
	
	public ArrayList<ObjPost> getHome(int limit){
		
		ArrayList<ObjPost> objPosts = new ArrayList<ObjPost>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM "+TABLE_NEWS + " ORDER BY "+KEY_DATE_UNIX+ " DESC LIMIT "+limit;
		Log.i("SQL", "SQL: "+query);
		
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()){
			do {
				ObjPost objPost = new ObjPost();
				
				objPost.setId(c.getInt(c.getColumnIndex(KEY_ID_NEW)));
				objPost.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
				objPost.setUrlPost(c.getString(c.getColumnIndex(KEY_URL)));
				objPost.setUrlImage(c.getString(c.getColumnIndex(KEY_URL_IMAGE)));
				objPost.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
				objPost.setDateUnix(c.getInt(c.getColumnIndex(KEY_DATE_UNIX)));
				objPost.setDateDay(c.getString(c.getColumnIndex(KEY_DATE_DAY)));
				objPost.setDateHour(c.getString(c.getColumnIndex(KEY_DATE_HOUR)));
				objPost.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
				objPost.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
				objPost.setUrlVideo(c.getString(c.getColumnIndex(KEY_VIDEO_URL)));
				
				objPosts.add(objPost);
				
			} while (c.moveToNext());
			
		}
		c.close();
		db.close();
		
		return objPosts;
	}
	
	//------------------------ get 5 tin ---------------//
	
	
	
}
