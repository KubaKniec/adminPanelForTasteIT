package com.example.adminpanel.service;

import com.example.adminpanel.dto.IngredientDto;
import com.example.adminpanel.dto.TagDto;
import com.example.adminpanel.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void deleteTagById(String id) {
        restTemplate.delete(URL+"tag/" + id);
    }
    public void deleteIngredientsById(String id) {
        restTemplate.delete(URL+"ingredient/" + id);
    }

    public void deleteUserById(String id) {
        if (!getUserById(id).getEmail().equals(currentEmail))
            restTemplate.delete(URL+"user/"+id);
    }
}
