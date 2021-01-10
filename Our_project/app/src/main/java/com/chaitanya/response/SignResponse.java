package com.chaitanya.response;

import com.google.gson.annotations.SerializedName;

public class SignResponse{

	@SerializedName("address")
	private String address;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public String getStatus() {
		return status;
	}

	@SerializedName("status")
	private String status;

	public String getAddress(){
		return address;
	}

	public String getName(){
		return name;
	}

	public String getMobile(){
		return mobile;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}
}