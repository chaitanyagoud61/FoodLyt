package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class FoodItemsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("food_id")
	private int foodId;

	@SerializedName("status")
	private String status;

	public String getName(){
		return name;
	}

	public int getFoodId(){
		return foodId;
	}

	public String getStatus(){
		return status;
	}
}