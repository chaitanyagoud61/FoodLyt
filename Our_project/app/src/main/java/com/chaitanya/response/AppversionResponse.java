package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class AppversionResponse{

	@SerializedName("Appversion")
	private String appversion;

	public String getAppversion(){
		return appversion;
	}
}