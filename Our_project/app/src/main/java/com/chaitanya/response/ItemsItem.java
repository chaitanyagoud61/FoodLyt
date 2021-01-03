package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("food_id")
	private String foodId;

	@SerializedName("category")
	private String category;

	@SerializedName("status")
	private String status;

	public String getImage(){
		return image;
	}

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getFoodId(){
		return foodId;
	}

	public String getCategory(){
		return category;
	}

	public String getStatus(){
		return status;
	}
}