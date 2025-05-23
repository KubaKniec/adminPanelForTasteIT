package com.example.adminpanel.dto;

public class TagDto {
    private String tagId;
    private String tagName;
    private String tagType;

    public TagDto() {
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagType() {
        return tagType;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "tagId='" + tagId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", tagType='" + tagType + '\'' +
                '}';
    }
}
