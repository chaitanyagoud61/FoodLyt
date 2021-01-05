package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class GstResponse{

	@SerializedName("taxes")
	private int taxes;

	@SerializedName("delivery_partner_fee")
	private int deliveryPartnerFee;

	public int getTaxes(){
		return taxes;
	}

	public int getDeliveryPartnerFee(){
		return deliveryPartnerFee;
	}
}