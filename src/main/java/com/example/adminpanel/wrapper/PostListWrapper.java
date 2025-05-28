package com.example.adminpanel.wrapper;

import com.example.adminpanel.dto.PostDto;

import java.util.List;

public class PostListWrapper{
    private List<PostDto> posts;

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public PostListWrapper(List<PostDto> posts) {
        this.posts = posts;
    }
}
