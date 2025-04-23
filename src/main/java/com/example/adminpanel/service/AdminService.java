package com.example.adminpanel.service;

import com.example.adminpanel.dto.TagDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private static final String URL = "http://localhost:8080/api/v1/";
    private RestTemplate restTemplate;

    public AdminService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TagDto> getTags() {
        TagDto[] tagDtos = restTemplate.getForObject(URL+"tag/", TagDto[].class);
        for (TagDto tagDto : tagDtos) {
            System.out.println(tagDto);
        }
        return new ArrayList<>();
    }
}
