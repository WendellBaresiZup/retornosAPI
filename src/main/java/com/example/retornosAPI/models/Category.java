package com.example.retornosAPI.models;

public enum Category {
    ELECTRONIC("Eletrônicos"),
    CLOTHES("Roupas"),
    FOOD("Alimento");

    private final String categoriaProduto;

    Category(String categoriaProduto){
        this.categoriaProduto = categoriaProduto;
    }

    public String getCategoriaProduto(){
        return categoriaProduto;
    }
}
