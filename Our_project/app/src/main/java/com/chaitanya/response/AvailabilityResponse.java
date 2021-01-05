package com.chaitanya.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AvailabilityResponse{

	@SerializedName("food_items")
	private List<FoodItemsItem> foodItems;

	@SerializedName("name")
	private String name;

	@SerializedName("rest_id")
	private int restId;

	@SerializedName("status")
	private String status;

	public List<FoodItemsItem> getFoodItems(){
		return foodItems;
	}

	public String getName(){
		return name;
	}

	public int getRestId(){
		return restId;
	}

	public String getStatus(){
		return status;
	}

}