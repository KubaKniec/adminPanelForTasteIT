package com.example.adminpanel.service;

import com.example.adminpanel.dto.*;
import com.example.adminpanel.filter.UserFilter;
import com.example.adminpanel.wrapper.PostListWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;


public class AdminServiceTest {
    private static final String BASE_URL = AdminService.URL;

    @Test
    public void shouldReturnUserById() {
        String userId = "user123";
        RestTemplate restTemplate = mock(RestTemplate.class);
        AdminService service = new AdminService(restTemplate);

        service.getUserById(userId);

        verify(restTemplate).getForObject(BASE_URL + "user/" + userId, UserDto.class);
    }

    // ----- Tag tests -----

    @Test
    public void shouldGetTagsWithoutFilters() {
        TagDto[] tags = {
                new TagDto("t1", "One", "type"),
                new TagDto("t2", "Two", "type")
        };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "tag/", TagDto[].class)).thenReturn(tags);
        AdminService service = new AdminService(rest);

        List<TagDto> result = service.getTags(null, null);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void shouldFilterTagsById() {
        TagDto[] tags = {
                new TagDto("abc", "Alpha", "type"),
                new TagDto("bcd", "Beta", "type"),
                new TagDto("abcd", "Gamma", "type")
        };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "tag/", TagDto[].class)).thenReturn(tags);
        AdminService service = new AdminService(rest);

        List<TagDto> result = service.getTags("abc", null);

        Assertions.assertEquals(2, result.size());
        result.forEach(t -> Assertions.assertTrue(t.getTagId().contains("abc")));
    }

    @Test
    public void shouldFilterTagsByName() {
        TagDto[] tags = {
                new TagDto("1", "First", "type"),
                new TagDto("2", "Second", "type"),
                new TagDto("3", "Third", "type")
        };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "tag/", TagDto[].class)).thenReturn(tags);
        AdminService service = new AdminService(rest);

        List<TagDto> result = service.getTags(null, "ir");

        Assertions.assertEquals(2, result.size());
        result.forEach(t -> Assertions.assertTrue(t.getTagName().toLowerCase().contains("ir")));
    }

    @Test
    public void shouldFilterTagsByIdAndName() {
        TagDto[] tags = {
                new TagDto("x1", "apple", "type"),
                new TagDto("x2", "banana", "type"),
                new TagDto("x1x", "apricot", "type")
        };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "tag/", TagDto[].class)).thenReturn(tags);
        AdminService service = new AdminService(rest);

        List<TagDto> result = service.getTags("x1", "ap");

        Assertions.assertEquals(2, result.size());
        result.forEach(t -> {
            Assertions.assertTrue(t.getTagId().contains("x1"));
            Assertions.assertTrue(t.getTagName().toLowerCase().contains("ap"));
        });
    }

    // ----- Ingredient tests -----

    @Test
    public void shouldGetIngredientsWithoutFilters() {
        IngredientDto[] arr = { new IngredientDto("i1", "Salt"), new IngredientDto("i2", "Sugar") };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "ingredient/", IngredientDto[].class)).thenReturn(arr);
        AdminService service = new AdminService(rest);

        List<IngredientDto> result = service.getIngredients(null, null);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void shouldFilterIngredientsById() {
        IngredientDto[] arr = { new IngredientDto("abc", "A"), new IngredientDto("bcd", "B"), new IngredientDto("abcd", "C") };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "ingredient/", IngredientDto[].class)).thenReturn(arr);
        AdminService service = new AdminService(rest);

        List<IngredientDto> result = service.getIngredients("abc", null);

        Assertions.assertEquals(2, result.size());
        result.forEach(i -> Assertions.assertTrue(i.getIngredientId().contains("abc")));
    }

    @Test
    public void shouldFilterIngredientsByName() {
        IngredientDto[] arr = { new IngredientDto("1", "Tomato"), new IngredientDto("2", "Potato"), new IngredientDto("3", "Lettuce") };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "ingredient/", IngredientDto[].class)).thenReturn(arr);
        AdminService service = new AdminService(rest);

        List<IngredientDto> result = service.getIngredients(null, "to");

        Assertions.assertEquals(2, result.size());
        result.forEach(i -> Assertions.assertTrue(i.getName().toLowerCase().contains("to")));
    }

    @Test
    public void shouldFilterIngredientsByIdAndName() {
        IngredientDto[] arr = {
                new IngredientDto("id1", "apple"),
                new IngredientDto("id2", "banana"),
                new IngredientDto("id1x", "apricot")
        };
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "ingredient/", IngredientDto[].class)).thenReturn(arr);
        AdminService service = new AdminService(rest);

        List<IngredientDto> result = service.getIngredients("id1", "ap");

        Assertions.assertEquals(2, result.size());
        result.forEach(i -> {
            Assertions.assertTrue(i.getIngredientId().contains("id1"));
            Assertions.assertTrue(i.getName().toLowerCase().contains("ap"));
        });
    }

    // ----- getUsers tests -----

    @Test
    public void shouldGetUsersWithFilters() {
        UserDto[] users = {
                new UserDto("u1", "a@x.com", "Alice", null, null, null),
                new UserDto("u2", "b@y.com", "Bob", null, null, null),
                new UserDto("u3", "bob@x.com", "Bobby", null, null, null)
        };
        UserFilter filter = new UserFilter("u", "@x.com", "bo");
        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "user/", UserDto[].class)).thenReturn(users);
        AdminService service = new AdminService(rest);

        List<UserDto> result = service.getUsers(filter);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("u3", result.get(0).getUserId());
    }

    // ----- Delete operations -----

    @Test
    public void shouldDeleteTagById() {
        String id = "tag1";
        RestTemplate rest = mock(RestTemplate.class);
        AdminService service = new AdminService(rest);

        service.deleteTagById(id);
        verify(rest).delete(BASE_URL + "tag/" + id);
    }

    @Test
    public void shouldDeleteIngredientsById() {
        String id = "ing1";
        RestTemplate rest = mock(RestTemplate.class);
        AdminService service = new AdminService(rest);

        service.deleteIngredientsById(id);
        verify(rest).delete(BASE_URL + "ingredient/" + id);
    }

    @Test
    public void shouldNotDeleteCurrentUser() {
        String uid = "me";
        RestTemplate rest = mock(RestTemplate.class);
        AdminService service = new AdminService(rest);
        ReflectionTestUtils.setField(service, "currentEmail", "me@x.com");
        when(rest.getForObject(BASE_URL + "user/" + uid, UserDto.class))
                .thenReturn(new UserDto(uid, "me@x.com", "Me", null, null, null));

        service.deleteUserById(uid);
        verify(rest, never()).delete(BASE_URL + "user/" + uid);
    }

    @Test
    public void shouldDeleteOtherUserAndTheirPosts() {
        String uid = "other";
        RestTemplate rest = mock(RestTemplate.class);
        AdminService service = new AdminService(rest);
        ReflectionTestUtils.setField(service, "currentEmail", "me@x.com");
        when(rest.getForObject(BASE_URL + "user/" + uid, UserDto.class))
                .thenReturn(new UserDto(uid, "other@x.com", "Other", null, null, null));
        PostDto p1 = new PostDto(); p1.setPostId("p1");
        PostDto p2 = new PostDto(); p2.setPostId("p2");
        PostListWrapper wrap = new PostListWrapper(Arrays.asList(p1, p2));
        when(rest.getForObject(BASE_URL + "user/" + uid + "/posts", PostListWrapper.class))
                .thenReturn(wrap);

        service.deleteUserById(uid);
        verify(rest).delete(BASE_URL + "post/p1");
        verify(rest).delete(BASE_URL + "post/p2");
        verify(rest).delete(BASE_URL + "user/" + uid);
    }

    @Test
    public void shouldDeletePostById() {
        String pid = "post1";
        RestTemplate rest = mock(RestTemplate.class);
        AdminService service = new AdminService(rest);

        service.deletePostById(pid);
        verify(rest).delete(BASE_URL + "post/" + pid);
    }

    @Test
    public void shouldGetPostsAndMergePictures() {
        RecipeDto recipe = new RecipeDto();
        Map<String, String> recPics = new LinkedHashMap<>(); recPics.put("r1", "u1");
        recipe.setPictures(recPics);
        PostMediaDto media = new PostMediaDto();
        media.setPictures(Arrays.asList("u2", "u3"));
        PostDto post = new PostDto(); post.setPostId("x"); post.setRecipe(recipe); post.setPostMedia(media);

        RestTemplate rest = mock(RestTemplate.class);
        when(rest.getForObject(BASE_URL + "post/all", PostDto[].class))
                .thenReturn(new PostDto[]{post});
        AdminService service = new AdminService(rest);

        List<PostDto> out = service.getPosts(null, null);
        Map<String, String> merged = out.get(0).getRecipe().getPictures();

        Assertions.assertEquals(3, merged.size());
        Assertions.assertEquals("u1", merged.get("r1"));
        Assertions.assertEquals("u2", merged.get("media1"));
        Assertions.assertEquals("u3", merged.get("media2"));
    }
}