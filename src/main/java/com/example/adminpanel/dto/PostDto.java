package com.example.adminpanel.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDto {
    private String postId;
    private UserDto postAuthorDto;
    private String postType;
    private PostMediaDto postMedia;
    private RecipeDto recipe;
    private Boolean isAlcoholic;
    private List<TagDto> tags = new ArrayList<>();
    private Date createdDate;
    private Long likesCount;
    private Long commentsCount;
    private Boolean likedByCurrentUser;


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

    public RecipeDto getRecipe() {
        return recipe;
    }

    public Boolean getIsAlcoholic() {
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

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setRecipe(RecipeDto recipe) {
        this.recipe = recipe;
    }

    public void setPostMedia(PostMediaDto postMedia) {
        this.postMedia = postMedia;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "postId='" + postId + '\'' +
                ", postAuthorDto=" + postAuthorDto +
                ", postType='" + postType + '\'' +
                ", postMedia=" + postMedia +
                ", recipe=" + recipe +
                ", isAlcoholic=" + isAlcoholic +
                ", tags=" + tags +
                ", createdDate=" + createdDate +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", likedByCurrentUser=" + likedByCurrentUser +
                '}';
    }
}
