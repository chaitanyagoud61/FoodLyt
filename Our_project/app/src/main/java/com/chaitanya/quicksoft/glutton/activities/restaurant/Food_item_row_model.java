package com.chaitanya.quicksoft.glutton.activities.restaurant;

public class Food_item_row_model {

    public Food_item_row_model(String price, String food_name, String food_on_or_off, String food_image,String food_item_id,String food_category) {
        this.price = price;
        this.food_name = food_name;
        this.food_on_or_off = food_on_or_off;
        this.food_image = food_image;
        this.food_item_id = food_item_id;
        this.food_category = food_category;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_on_or_off() {
        return food_on_or_off;
    }

    public void setFood_on_or_off(String food_on_or_off) {
        this.food_on_or_off = food_on_or_off;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    String price="";
    String food_name="";
    String food_on_or_off="";

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    String food_category="";


    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    String food_item_id="";
    String food_image;
}
