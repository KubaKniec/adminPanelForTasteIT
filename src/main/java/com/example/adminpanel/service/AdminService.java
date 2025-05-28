package com.example.adminpanel.service;

import com.example.adminpanel.dto.IngredientDto;
import com.example.adminpanel.dto.PostDto;
import com.example.adminpanel.dto.TagDto;
import com.example.adminpanel.dto.UserDto;
import com.example.adminpanel.filter.UserFilter;
import com.example.adminpanel.wrapper.PostListWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {
    public static final String URL = "http://localhost:8080/api/v1/";
    private final RestTemplate restTemplate;

    @Value("${login}")
    private String currentEmail;

    public AdminService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TagDto> getTags(String idFilter, String nameFilter) {
        TagDto[] arr = restTemplate.getForObject(URL + "tag/", TagDto[].class);
        List<TagDto> list = arr != null
                ? new ArrayList<>(Arrays.asList(arr))
                : new ArrayList<>();
        return filterTags(list, idFilter, nameFilter);
    }

    private List<TagDto> filterTags(List<TagDto> tags, String idFilter, String nameFilter) {
        List<TagDto> result = tags;
        if (idFilter != null && !idFilter.isBlank()) {
            result = result.stream()
                    .filter(t -> t.getTagId().contains(idFilter))
                    .collect(Collectors.toList());
        }
        if (nameFilter != null && !nameFilter.isBlank()) {
            String low = nameFilter.toLowerCase();
            result = result.stream()
                    .filter(t -> t.getTagName().toLowerCase().contains(low))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<IngredientDto> getIngredients(String idFilter, String nameFilter) {
        IngredientDto[] arr = restTemplate.getForObject(URL + "ingredient/", IngredientDto[].class);
        List<IngredientDto> list = arr != null
                ? new ArrayList<>(Arrays.asList(arr))
                : new ArrayList<>();
        return filterIngredients(list, idFilter, nameFilter);
    }

    private List<IngredientDto> filterIngredients(List<IngredientDto> ingredients, String idFilter, String nameFilter) {
        List<IngredientDto> result = ingredients;
        if (idFilter != null && !idFilter.isBlank()) {
            result = result.stream()
                    .filter(i -> i.getIngredientId().contains(idFilter))
                    .collect(Collectors.toList());
        }
        if (nameFilter != null && !nameFilter.isBlank()) {
            String low = nameFilter.toLowerCase();
            result = result.stream()
                    .filter(i -> i.getName().toLowerCase().contains(low))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<UserDto> getUsers(UserFilter userFilter) {
        UserDto[] arr = restTemplate.getForObject(URL + "user/", UserDto[].class);
        return filterUsers(userFilter, arr);
    }

    private List<UserDto> filterUsers(UserFilter userFilter, UserDto[] arr) {
        String idFilter = userFilter.getId();
        String emailFilter = userFilter.getEmail();
        String displayNameFilter = userFilter.getDisplayName();
        List<UserDto> list = arr != null
                ? new ArrayList<>(Arrays.asList(arr))
                : new ArrayList<>();
        if (idFilter != null && !idFilter.isBlank()) {
            list = list.stream()
                    .filter(u -> u.getUserId().contains(idFilter))
                    .collect(Collectors.toList());
        }
        if (emailFilter != null && !emailFilter.isBlank()) {
            String lowEmail = emailFilter.toLowerCase();
            list = list.stream()
                    .filter(u -> u.getEmail() != null
                            && u.getEmail().toLowerCase().contains(lowEmail))
                    .collect(Collectors.toList());
        }
        if (displayNameFilter != null && !displayNameFilter.isBlank()) {
            String lowName = displayNameFilter.toLowerCase();
            list = list.stream()
                    .filter(u -> u.getDisplayName() != null
                            && u.getDisplayName().toLowerCase().contains(lowName))
                    .collect(Collectors.toList());
        }
        return list;
    }

    public UserDto getUserById(String id) {
        return restTemplate.getForObject(URL + "user/" + id, UserDto.class);
    }

    public List<PostDto> getPosts(String postIdFilter, String emailFilter) {
        PostDto[] response = restTemplate.getForObject(URL + "post/all", PostDto[].class);
        List<PostDto> posts = response != null
                ? new ArrayList<>(Arrays.asList(response))
                : new ArrayList<>();
        if (postIdFilter != null && !postIdFilter.isBlank()) {
            posts = posts.stream()
                    .filter(p -> p.getPostId().contains(postIdFilter))
                    .collect(Collectors.toList());
        }
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
        restTemplate.delete(URL + "tag/" + id);
    }

    public void deleteIngredientsById(String id) {
        restTemplate.delete(URL + "ingredient/" + id);
    }

    public void deleteUserById(String id) {
        if (!getUserById(id).getEmail().equals(currentEmail)) {
            deletePostsByUserId(id);
            restTemplate.delete(URL + "user/" + id);
        }
    }

    private void deletePostsByUserId(String id) {
        PostListWrapper response = restTemplate.getForObject(URL + "user/" + id + "/posts", PostListWrapper.class);
        if (response != null && response.getPosts() != null) {
            for (PostDto postDto : response.getPosts()) {
                restTemplate.delete(URL + "post/" + postDto.getPostId());
            }
        }
    }

    public void deletePostById(String id) {
        restTemplate.delete(URL + "post/" + id);
    }

    public void promoteUserByEmail(String email) {
        restTemplate.postForEntity(URL + "auth/promote?email=" + email, null, Void.class);
    }

    public void demoteUserByEmail(String email) {
        restTemplate.postForEntity(URL + "auth/demote?email=" + email, null, Void.class);
    }
}
