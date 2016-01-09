package com.thucdx;

public class Product extends Box {
    int id, price, weight;

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public Product(int id, int price, int dim1, int dim2, int dim3, int weight) {
        super(dim1, dim2, dim3);
        this.id = id;
        this.price = price;
        this.weight = weight;
    }
}
