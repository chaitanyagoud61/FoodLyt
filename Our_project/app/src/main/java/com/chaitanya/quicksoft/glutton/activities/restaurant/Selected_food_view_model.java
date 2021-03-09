package com.chaitanya.quicksoft.glutton.activities.restaurant;

public class Selected_food_view_model {

    public Selected_food_view_model(String selected_food, String selected_food_quantity) {
        this.selected_food = selected_food;
        this.selected_food_quantity = selected_food_quantity;
    }

    public String getSelected_food() {
        return selected_food;
    }

    public void setSelected_food(String selected_food) {
        this.selected_food = selected_food;
    }

    public String getselected_food_quantity() {
        return selected_food_quantity;
    }

    public void setselected_food_quantity(String selected_food_quantity) {
        this.selected_food_quantity = selected_food_quantity;
    }

    String selected_food="",selected_food_quantity="";
}
