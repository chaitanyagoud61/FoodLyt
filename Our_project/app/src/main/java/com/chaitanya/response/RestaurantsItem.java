package com.chaitanya.response;

public class RestaurantsItem{
	/**
	 * rest_id : 26
	 * name : FoodG
	 * offers : No Restaurant offers
	 * status : on
	 * image : https://www.dropbox.com/s/i167nbtkmp7qvjr/pizza.jpeg?raw=1
	 * address : Palakonda Road, Rajam
	 * description : Eat. Play. Repeat
	 * rest_commision : 0.1
	 * price_increase : 0
	 * discount_value : 30
	 */

	private int rest_id;
	private String name;
	private String offers;
	private String status;
	private String image;
	private String address;
	private String description;
	private double rest_commision;
	private int price_increase;
	private int discount_value;

	public int getRest_id() {
		return rest_id;
	}

	public void setRest_id(int rest_id) {
		this.rest_id = rest_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOffers() {
		return offers;
	}

	public void setOffers(String offers) {
		this.offers = offers;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRest_commision() {
		return rest_commision;
	}

	public void setRest_commision(double rest_commision) {
		this.rest_commision = rest_commision;
	}

	public int getPrice_increase() {
		return price_increase;
	}

	public void setPrice_increase(int price_increase) {
		this.price_increase = price_increase;
	}

	public int getDiscount_value() {
		return discount_value;
	}

	public void setDiscount_value(int discount_value) {
		this.discount_value = discount_value;
	}

	/*@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("rest_id")
	private int restId;

	@SerializedName("status")
	private String status;

	public String getOffers() {
		return offers;
	}

	@SerializedName("offers")
	private String offers;

	@SerializedName("discount_value")
	private int discount_value;

	public String getImage(){
		return image;
	}

	public String getAddress(){
		return address;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public int getRestId(){
		return restId;
	}

	public String getStatus(){
		return status;
	}

	public int getDiscount_Value() {return discount_value;}*/


}