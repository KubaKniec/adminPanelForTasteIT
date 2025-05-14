package com.example.adminpanel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostMediaDto {
    private String title;
    private String description;
    private List<String> pictures;

    public PostMediaDto() { }

    public PostMediaDto(String title, String description, List<String> pictures) {
        this.title = title;
        this.description = description;
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public List<String> getPictures() {
        return pictures;
    }
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "PostMediaDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pictures=" + pictures +    // ← dorzuć do toString, ułatwi debug
                '}';
    }
}