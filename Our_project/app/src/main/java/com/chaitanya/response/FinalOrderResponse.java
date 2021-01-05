package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class FinalOrderResponse{

	@SerializedName("status")
	private String status;

	public String getStatus(){
		return status;
	}
}