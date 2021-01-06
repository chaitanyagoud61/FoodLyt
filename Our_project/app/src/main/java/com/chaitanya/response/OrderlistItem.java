package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class OrderlistItem{

	@SerializedName("rest_address")
	private String restAddress;

	@SerializedName("total_price")
	private double totalPrice;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("orderid")
	private String orderid;

	@SerializedName("inprogress")
	private boolean inprogress;

	@SerializedName("delivered")
	private boolean delivered;

	@SerializedName("rest_name")
	private String restName;

	@SerializedName("fd_item_count")
	private int fdItemCount;

	public String getRestAddress(){
		return restAddress;
	}

	public double getTotalPrice(){
		return totalPrice;
	}

	public String getDateTime(){
		return dateTime;
	}

	public String getOrderid(){
		return orderid;
	}

	public boolean isInprogress(){
		return inprogress;
	}

	public boolean isDelivered(){
		return delivered;
	}

	public String getRestName(){
		return restName;
	}

	public int getFdItemCount(){
		return fdItemCount;
	}
}