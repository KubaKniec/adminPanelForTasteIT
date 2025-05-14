package com.example.adminpanel.service;

import com.example.adminpanel.dto.UserDto;
import com.example.adminpanel.filter.UserFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    @Test
    public void shouldReturnUserById() {
        //given
        String userId = "asdhoas";
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        AdminService adminService = new AdminService(restTemplate);

        //when
        adminService.getUserById(userId);

        //then
        Mockito.verify(restTemplate).getForObject(AdminService.URL + "/user/" + userId, UserDto.class);
    }

    @Test
    public void shouldFilterUsers() {
        //given
        UserDto[] users = {
                new UserDto("user1", "user1@example.com", "One", null, null, null),
                new UserDto("user2", "user2@example.com", "Two", null, null, null),
                new UserDto("user3", "user3@example.com", "Three", null, null, null),
                new UserDto("person4", "user4@example.com", "User Four", null, null, null),
                new UserDto("user5", "user5@example.com", "User Five", null, null, null),
                new UserDto("user6", "user6@example.pl", "User Six", null, null, null)
        };
        UserFilter userFilter = new UserFilter("user", ".com", "User");
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        AdminService adminService = new AdminService(restTemplate);

        //when
        List<UserDto> userDtos = adminService.filterUsers(userFilter, users);

        //then
        Assertions.assertEquals(users[4], userDtos.get(0));
    }

    @Test
    public void shouldDeletePostById() {
        //given
        String postId = "asdhoas";
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        AdminService adminService = new AdminService(restTemplate);

        //when
        adminService.deletePostById(postId);

        //then
        Mockito.verify(restTemplate).delete(adminService.URL + "post/" + postId);
    }

}