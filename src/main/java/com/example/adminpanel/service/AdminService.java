package com.example.adminpanel.service;

import com.example.adminpanel.dto.*;
import com.example.adminpanel.wrapper.PostListWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<PostDto> getPosts(String postIdFilter, String emailFilter) {
        // 1) fetch
        PostDto[] response = restTemplate.getForObject(URL + "post/all", PostDto[].class);
        List<PostDto> posts = response != null
                ? new ArrayList<>(Arrays.asList(response))
                : new ArrayList<>();

        // 2) filtr po postId
        if (postIdFilter != null && !postIdFilter.isBlank()) {
            posts = posts.stream()
                    .filter(p -> p.getPostId().contains(postIdFilter))
                    .collect(Collectors.toList());
        }

        // 3) filtr po email autora
        if (emailFilter != null && !emailFilter.isBlank()) {
            posts = posts.stream()
                    .filter(p -> {
                        var author = p.getPostAuthorDto();
                        return author != null
                                && author.getEmail() != null
                                && author.getEmail().contains(emailFilter);
                    })
                    .collect(Collectors.toList());
        }

        // 4) łączenie obrazków (Twoja istniejąca logika)
        for (PostDto post : posts) {
            Map<String, String> combined = new LinkedHashMap<>();
            if (post.getRecipe().getPictures() != null) {
                combined.putAll(post.getRecipe().getPictures());
            }
            if (post.getPostMedia() != null && post.getPostMedia().getPictures() != null) {
                List<String> mediaPics = post.getPostMedia().getPictures();
                for (int i = 0; i < mediaPics.size(); i++) {
                    combined.put("media" + (i + 1), mediaPics.get(i));
                }
            }
            post.getRecipe().setPictures(combined);
        }

        return posts;
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
