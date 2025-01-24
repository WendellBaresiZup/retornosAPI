package com.example.retornosAPI.models;

public enum Category {
    ELECTRONIC("Eletrônicos"),
    CLOTHES("Roupas"),
    FOOD("Alimento");

    private final String productCategory;

    Category(String productCategory){
        this.productCategory = productCategory;
    }

    public String getProductCategory(){
        return productCategory;
    }
}
