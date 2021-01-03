package com.chaitanya.quicksoft.glutton;

public class OrderListModel {

    String restaurant_name="",restaurant_address="",
            order_price="",order_list_food_name_nd_qnty="",
            order_date_nd_time="",order_list_status="";

    public OrderListModel(String restaurant_name, String restaurant_address, String order_price, String order_list_food_name_nd_qnty, String order_date_nd_time, String order_list_status) {
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.order_price = order_price;
        this.order_list_food_name_nd_qnty = order_list_food_name_nd_qnty;
        this.order_date_nd_time = order_date_nd_time;
        this.order_list_status = order_list_status;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_list_food_name_nd_qnty() {
        return order_list_food_name_nd_qnty;
    }

    public void setOrder_list_food_name_nd_qnty(String order_list_food_name_nd_qnty) {
        this.order_list_food_name_nd_qnty = order_list_food_name_nd_qnty;
    }

    public String getOrder_date_nd_time() {
        return order_date_nd_time;
    }

    public void setOrder_date_nd_time(String order_date_nd_time) {
        this.order_date_nd_time = order_date_nd_time;
    }

    public String getOrder_list_status() {
        return order_list_status;
    }

    public void setOrder_list_status(String order_list_status) {
        this.order_list_status = order_list_status;
    }
}