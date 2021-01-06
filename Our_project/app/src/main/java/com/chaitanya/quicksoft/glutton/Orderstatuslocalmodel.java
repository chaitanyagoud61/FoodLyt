package com.chaitanya.quicksoft.glutton;

public class Orderstatuslocalmodel {
    String food_name="";
    String price="";

    public Orderstatuslocalmodel(String food_name, String price, String quantity) {
        this.food_name = food_name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    String quantity="";
}
