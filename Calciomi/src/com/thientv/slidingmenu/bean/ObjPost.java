package com.thientv.slidingmenu.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ObjPost implements Parcelable {

	int id;
	String type;
	String urlPost;

	String author;

	int dateUnix;
	String dateDay;
	String dateHour;

	String title;
	String content;
	String urlImage;
	String urlVideo;

	public ObjPost() {

	}

	public ObjPost(String type, String time, String title) {
		super();
		this.type = type;
		this.dateDay = time;
		this.title = title;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public int getDateUnix() {
		return dateUnix;
	}

	public void setDateUnix(int dateUnix) {
		this.dateUnix = dateUnix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrlPost() {
		return urlPost;
	}

	public void setUrlPost(String urlPost) {
		this.urlPost = urlPost;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDateDay() {
		return dateDay;
	}

	public void setDateDay(String dateDay) {
		this.dateDay = dateDay;
	}

	public String getDateHour() {
		return dateHour;
	}

	public void setDateHour(String dateHour) {
		this.dateHour = dateHour;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public ObjPost(Parcel in) {
		id = in.readInt();
		type = in.readString();
		urlPost = in.readString();
		author = in.readString();
		dateUnix = in.readInt();
		dateDay = in.readString();
		dateHour = in.readString();
		title = in.readString();
		content = in.readString();
		urlImage = in.readString();
		urlVideo = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(type);
		dest.writeString(urlPost);
		dest.writeString(author);
		dest.writeInt(dateUnix);
		dest.writeString(dateDay);
		dest.writeString(dateHour);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(urlImage);
		dest.writeString(urlVideo);
	}

	public static final Parcelable.Creator<ObjPost> CREATOR = new Parcelable.Creator<ObjPost>() {
		public ObjPost createFromParcel(Parcel in) {
			return new ObjPost(in);
		}

		public ObjPost[] newArray(int size) {
			return new ObjPost[size];
		}
	};

}
