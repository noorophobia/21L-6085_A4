package com.example.a21l_6085_a4.Model;

public class ShoppingItem {
    private String name;
    private String quantity;
    private String price;

    public ShoppingItem() {
     }

    public ShoppingItem(String name, String quantity, String price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
