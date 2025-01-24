package com.example.retornosAPI.models;

public enum Categoria {
    ELETRONICO("Eletrônicos"),
    ROUPA("Roupas"),
    ALIMENTO("Alimento");

    private final String categoriaProduto;

    Categoria(String categoriaProduto){
        this.categoriaProduto = categoriaProduto;
    }

    public String getCategoriaProduto(){
        return categoriaProduto;
    }
}
