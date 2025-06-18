package com.example.adminpanel.controller;

import com.example.adminpanel.dto.IngredientDto;
import com.example.adminpanel.dto.PostDto;
import com.example.adminpanel.dto.TagDto;
import com.example.adminpanel.dto.UserDto;
import com.example.adminpanel.filter.UserFilter;
import com.example.adminpanel.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/tags")
    public ModelAndView showTags(
            @RequestParam(value = "id", required = false) String idFilter,
            @RequestParam(value = "name", required = false) String nameFilter
    ) {
        List<TagDto> tagsDtos = adminService.getTags(idFilter, nameFilter);
        ModelAndView modelAndView = new ModelAndView("tags");
        modelAndView.addObject("tags", tagsDtos);
        modelAndView.addObject("idFilter", idFilter);
        modelAndView.addObject("nameFilter", nameFilter);
        return modelAndView;
    }

    @GetMapping("/ingredients")
    public ModelAndView showIngredients(
            @RequestParam(value = "id", required = false) String idFilter,
            @RequestParam(value = "name", required = false) String nameFilter
    ) {
        List<IngredientDto> ingredients = adminService.getIngredients(idFilter, nameFilter);
        ModelAndView modelAndView = new ModelAndView("ingredients");
        modelAndView.addObject("ingredients", ingredients);
        modelAndView.addObject("idFilter", idFilter);
        modelAndView.addObject("nameFilter", nameFilter);
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView showUsers(UserFilter userFilter) {
        List<UserDto> usersDtos = adminService.getUsers(userFilter);
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", usersDtos);
        modelAndView.addObject("idFilter", userFilter.getId());
        modelAndView.addObject("emailFilter", userFilter.getEmail());
        modelAndView.addObject("displayNameFilter", userFilter.getDisplayName()); // TODO zmienic
        return modelAndView;
    }

//    @GetMapping("/users")
//    public ModelAndView showUsers(
//            @RequestParam(value = "id", required = false) String idFilter,
//            @RequestParam(value = "email", required = false) String emailFilter,
//            @RequestParam(value = "displayName", required = false) String displayNameFilter
//    ) {
//        List<UserDto> usersDtos = adminService.getUsers(idFilter, emailFilter, displayNameFilter);
//        ModelAndView modelAndView = new ModelAndView("users");
//        modelAndView.addObject("users", usersDtos);
//        modelAndView.addObject("idFilter", idFilter);
//        modelAndView.addObject("emailFilter", emailFilter);
//        modelAndView.addObject("displayNameFilter", displayNameFilter);
//        return modelAndView;
//    }

    @GetMapping("/posts")
    public ModelAndView showPosts(
            @RequestParam(value = "postId", required = false) String postIdFilter,
            @RequestParam(value = "email", required = false) String emailFilter
    ) {
        List<PostDto> postDtos = adminService.getPosts(postIdFilter, emailFilter);
        ModelAndView modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postDtos);
        modelAndView.addObject("postIdFilter", postIdFilter);
        modelAndView.addObject("emailFilter", emailFilter);
        return modelAndView;
    }

    @PostMapping("tags/{id}")
    public String deleteTagById(@PathVariable String id) {
        System.out.println(id);
        adminService.deleteTagById(id);
        return "redirect:/tags";
    }

    @PostMapping("ingredients/{id}")
    public String deleteIngredientsById(@PathVariable String id) {
        System.out.println(id);
        adminService.deleteIngredientsById(id);
        return "redirect:/ingredients";

    }

    @PostMapping("users/{id}")
    private String deleteUserById(@PathVariable String id) {
        adminService.deleteUserById(id);
        return "redirect:/users";
    }

    @PostMapping("users/promote")
    public String promoteUserByEmail(@RequestParam("email") String email) {
        adminService.promoteUserByEmail(email);
        return "redirect:/users";
    }

    @PostMapping("users/demote")
    public String demoteUserByEmail(@RequestParam String email) {
        adminService.demoteUserByEmail(email);
        return "redirect:/users";
    }

    @PostMapping("posts/{id}")
    private String deletePostById(@PathVariable String id) {
        System.out.println(id);
        adminService.deletePostById(id);
        return "redirect:/posts";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String adminToken = (String) session.getAttribute("AUTH_TOKEN");
            if (adminToken != null) {
                adminService.logout(adminToken);
            }
            session.invalidate();
        }
        return "redirect:/login?logout";
    }
}
