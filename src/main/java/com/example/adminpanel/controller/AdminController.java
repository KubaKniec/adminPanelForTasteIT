package com.example.adminpanel.controller;

import com.example.adminpanel.dto.IngredientDto;
import com.example.adminpanel.dto.TagDto;
import com.example.adminpanel.dto.UserDto;
import com.example.adminpanel.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/tags")
    public ModelAndView showTags() {
        List<TagDto> tagDtos = adminService.getTags();
        ModelAndView modelAndView = new ModelAndView("tags");
        modelAndView.addObject("tags",tagDtos);
        return modelAndView;
    }

    @GetMapping("/ingredients")
    public ModelAndView showIngredients() {
        List<IngredientDto> ingredientDtos = adminService.getIngredients();
        ModelAndView modelAndView = new ModelAndView("ingredients");
        modelAndView.addObject("ingredients",ingredientDtos);
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView showUsers() {
        List<UserDto> userDtos = adminService.getUsers();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users",userDtos);
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
        System.out.println(id);
        adminService.deleteUserById(id);
        return "redirect:/users";
    }



    //TODO zrobić usuwanie dla reszty danych
}
