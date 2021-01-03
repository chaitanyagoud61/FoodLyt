package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class RestaurantsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("rest_id")
	private int restId;

	@SerializedName("status")
	private String status;

	public String getImage(){
		return image;
	}

	public String getAddress(){
		return address;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public int getRestId(){
		return restId;
	}

	public String getStatus(){
		return status;
	}
}