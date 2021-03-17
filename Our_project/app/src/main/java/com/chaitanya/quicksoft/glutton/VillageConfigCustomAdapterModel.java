package com.chaitanya.quicksoft.glutton;

public class VillageConfigCustomAdapterModel {

    public String getRestaurant_name() {
        return Restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        Restaurant_name = restaurant_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    String Restaurant_name = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description = "";

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    int restaurant_id=0;
    int discount_val = 0;
    String address = "";

    public int getDiscount_val() {
        return discount_val;
    }

    public void setDiscount_val(int discount_val) {
        this.discount_val = discount_val;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    String offers="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status="";


    public VillageConfigCustomAdapterModel(String Restaurant_name, String address, String image , String offers,String status,int restaurant_id,String description, int discount_val) {
        this.Restaurant_name = Restaurant_name;
        this.address = address;
        this.image = image;
        this.offers = offers;
        this.status = status;
        this.restaurant_id = restaurant_id;
        this.description = description;
        this.discount_val = discount_val;
    }



}
