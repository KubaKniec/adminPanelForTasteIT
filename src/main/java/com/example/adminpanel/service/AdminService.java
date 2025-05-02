package com.example.adminpanel.service;

import com.example.adminpanel.dto.*;
import com.example.adminpanel.wrapper.PostListWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;

@Service
public class AdminService {
    private static final String URL = "http://localhost:8080/api/v1/";
    private RestTemplate restTemplate;
    @Value("${login}")
    private String currentEmail;

    public AdminService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TagDto> getTags() {
        TagDto[] tagDtos = restTemplate.getForObject(URL+"tag/", TagDto[].class);
        return Arrays.stream(tagDtos)
                .toList();

    }

    public List<IngredientDto> getIngredients() {
        IngredientDto[] ingredientDtos = restTemplate.getForObject(URL+"ingredient/", IngredientDto[].class);
        return Arrays.stream(ingredientDtos)
                .toList();
    }

    public List<UserDto> getUsers() {
        UserDto[] userDtos = restTemplate.getForObject(URL+"user/", UserDto[].class);
        return Arrays.stream(userDtos)
                .toList();
    }

    public UserDto getUserById(String id) {
        return restTemplate.getForObject(URL+"/user/"+id, UserDto.class);
    }

    public List<PostDto> getPosts() {
        PostListWrapper response = restTemplate.getForObject(URL + "post/all", PostListWrapper.class);
        if (response != null) {
            return response.getPosts();
        }
        return new ArrayList<>();
        //TODO zmienić na wyjatek
        //TODO naprawić bo nie działa, cannot deserialize value of postListWrapper
    }

    public void deleteTagById(String id) {
        restTemplate.delete(URL+"tag/" + id);
    }
    public void deleteIngredientsById(String id) {
        restTemplate.delete(URL+"ingredient/" + id);
    }

    private void deletePostsByUserId(String id) {
        PostListWrapper response = restTemplate.getForObject(URL + "user/" + id + "/posts", PostListWrapper.class);
        if (response != null && response.getPosts() != null) {
            for (PostDto postDto : response.getPosts()) {
                restTemplate.delete(URL + "post/" + postDto.getPostId());
            }
        }

    }

    public void deleteUserById(String id) {
        if (!getUserById(id).getEmail().equals(currentEmail)) {
            deletePostsByUserId(id);
            restTemplate.delete(URL+"user/"+id);
        }
    }
}
