package com.chaitanya.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderstatusResp{

	@SerializedName("fooddetails")
	private List<FooddetailsItem> fooddetails;


	@SerializedName("isfoodprepared")
	private boolean isfoodprepared;


	public boolean Isfoodprepared() {
		return isfoodprepared;
	}

	public boolean Delivered() {
		return delivered;
	}

	public boolean Dispatched() {
		return dispatched;
	}

	@SerializedName("delivered")
	private boolean delivered;


	@SerializedName("dispatched")
	private boolean dispatched;

	public List<FooddetailsItem> getFooddetails(){
		return fooddetails;
	}


}