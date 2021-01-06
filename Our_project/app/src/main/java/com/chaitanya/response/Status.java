package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class Status{

	@SerializedName("isfoodprepared")
	private boolean isfoodprepared;

	@SerializedName("dispatched")
	private boolean dispatched;

	@SerializedName("delivered")
	private boolean delivered;

	public boolean isIsfoodprepared(){
		return isfoodprepared;
	}

	public boolean isDispatched(){
		return dispatched;
	}

	public boolean isDelivered(){
		return delivered;
	}
}