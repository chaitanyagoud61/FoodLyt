package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class FooddetailsItem{

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	@SerializedName("food_id")
	private int foodId;


	public String getQuantity() {
		return quantity;
	}

	@SerializedName("quantity")
	private String quantity;

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public int getFoodId(){
		return foodId;
	}
}