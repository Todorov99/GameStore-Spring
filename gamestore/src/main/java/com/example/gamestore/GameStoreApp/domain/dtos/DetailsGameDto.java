package com.example.gamestore.GameStoreApp.domain.dtos;

public class DetailsGameDto {

    private String title;

    public DetailsGameDto(){

    }

    public DetailsGameDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
