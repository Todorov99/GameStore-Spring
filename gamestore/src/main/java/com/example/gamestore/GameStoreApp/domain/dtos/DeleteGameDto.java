package com.example.gamestore.GameStoreApp.domain.dtos;

public class DeleteGameDto {

    private Integer id;

    public DeleteGameDto(){

    }

    public DeleteGameDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
