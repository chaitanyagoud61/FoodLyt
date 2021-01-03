package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class Getotpresp{

	@SerializedName("id")
	private int id;

	public int getId(){
		return id;
	}
}