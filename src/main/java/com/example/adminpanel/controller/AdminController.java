package com.example.adminpanel.controller;

import com.example.adminpanel.dto.TagDto;
import com.example.adminpanel.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        modelAndView.addObject("test","value");
        modelAndView.addObject("tags",tagDtos);
        return modelAndView;
    }

}
