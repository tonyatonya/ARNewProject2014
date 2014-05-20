package com.athapong.arcodeapp;

public class Item {
	 
    private String title;
    private String url;
    private String logoPath;
 
    public Item(String title, String url, String logoPath ) {
        super();
        this.title = title;
        this.url = url;
        this.logoPath = logoPath;
    } 
    
    public String getTitle(){
		return this.title;
	}
	public String getBusUrl(){
		return this.url;
	}
	public String getLogoPath(){
		return this.logoPath;
	}

}