package com.thientv.slidingmenu.bean;

public class MenuItem {

	String name;
	int color;

	public MenuItem() {

	}

	public MenuItem(String name, int color) {
		super();
		this.name = name;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
