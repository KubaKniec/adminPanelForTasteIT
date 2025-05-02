package com.example.adminpanel.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDto {
    private String postId;
    private UserDto postAuthorDto;
    private String postType;
    private PostMediaDto postMedia;
    private List<String> recipe;
    private Boolean isAlcoholic;
    private List<TagDto> tags = new ArrayList<>();
    private Date createdDate;
    private Long likesCount;
    private Long commentsCount;
    private Boolean likedByCurrentUser;

    public PostDto(String postId, UserDto postAuthorDto, String postType, PostMediaDto postMedia, List<String> recipe, Boolean isAlcoholic, List<TagDto> tags, Date createdDate, Long likesCount, Long commentsCount, Boolean likedByCurrentUser) {
        this.postId = postId;
        this.postAuthorDto = postAuthorDto;
        this.postType = postType;
        this.postMedia = postMedia;
        this.recipe = recipe;
        this.isAlcoholic = isAlcoholic;
        this.tags = tags;
        this.createdDate = createdDate;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public PostDto() {
    }

    public String getPostId() {
        return postId;
    }

    public UserDto getPostAuthorDto() {
        return postAuthorDto;
    }

    public String getPostType() {
        return postType;
    }

    public PostMediaDto getPostMedia() {
        return postMedia;
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public Boolean getAlcoholic() {
        return isAlcoholic;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public Boolean getLikedByCurrentUser() {
        return likedByCurrentUser;
    }
}
