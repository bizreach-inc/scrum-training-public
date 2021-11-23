package com.scrum.training.controller;

import com.scrum.training.exception.FileStorageException;
import com.scrum.training.service.ImageService;
import com.scrum.training.form.ImageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/upload")
    public String get(@ModelAttribute ImageForm form) {
        return "upload";
    }

    // POST image /add
    @PostMapping("/upload")
    public String add(@Valid @ModelAttribute ImageForm form, BindingResult result, UriComponentsBuilder builder)
            throws FileStorageException, IOException {
        // Storing file
        if (result.hasErrors()) {
            return "upload";
        }
        imageService.storeImage(form.getFile());
        URI location = builder.path("/").build().toUri();
        return "redirect:" + location.toString();
    }
}