package com.chaitanya.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Orderlistresp{

	@SerializedName("orderlist")
	private List<OrderlistItem> orderlist;

	public List<OrderlistItem> getOrderlist(){
		return orderlist;
	}
}