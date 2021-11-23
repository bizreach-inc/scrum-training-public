package com.scrum.training.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileRequiredValidator implements ConstraintValidator<FileRequired, MultipartFile> {

    @Override
    public void initialize(FileRequired constraint) {}

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.getOriginalFilename().isEmpty();
    }
}
