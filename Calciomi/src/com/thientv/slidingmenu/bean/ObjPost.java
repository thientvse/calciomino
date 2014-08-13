package com.thientv.slidingmenu.bean;

public class ObjPost {
	String type;
	String urlImage;
	String time;
	String shortContent;

	
	public ObjPost(){
		
	}
	
	public ObjPost(String type, String time, String shortContent){
		super();
		this.type = type;
		this.time = time;
		this.shortContent = shortContent;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

}
