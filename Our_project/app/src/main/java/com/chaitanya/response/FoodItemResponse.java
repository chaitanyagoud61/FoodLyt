package com.chaitanya.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FoodItemResponse{

	@SerializedName("rest_id")
	private int restId;

	@SerializedName("items")
	private List<ItemsItem> items;

	public int getRestId(){
		return restId;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}