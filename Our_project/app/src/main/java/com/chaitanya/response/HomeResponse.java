package com.chaitanya.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeResponse{

	@SerializedName("cityname")
	private String cityname;

	@SerializedName("restaurants")
	private List<RestaurantsItem> restaurants;

	@SerializedName("city_id")
	private int cityId;

	public String getCityname(){
		return cityname;
	}

	public List<RestaurantsItem> getRestaurants(){
		return restaurants;
	}

	public int getCityId(){
		return cityId;
	}
}