package com.scrum.training.controller;

import com.scrum.training.entity.Image;
import com.scrum.training.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public String get(Model model) {
        List<Image> images = imageService.listImages();
        model.addAttribute("images", images);
        return "index";
    }

}
