package com.example.gamestore.GameStoreApp.domain.dtos;

import java.math.BigDecimal;

public class EditGameDto {

    private Integer id;
    private BigDecimal price;
    private double size;

    public EditGameDto(){

    }

    public EditGameDto(Integer id, BigDecimal price, double size) {
        this.id = id;
        this.price = price;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
