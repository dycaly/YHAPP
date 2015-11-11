package com.example.myskety.my_application.data;

public class ImageItem {

	private String name;
	private String url;
	
	public ImageItem(String name,String url){
		this.name= name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
