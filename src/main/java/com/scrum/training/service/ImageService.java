package com.scrum.training.service;

import com.scrum.training.entity.Image;
import com.scrum.training.exception.FileStorageException;
import com.scrum.training.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void storeImage(MultipartFile file) throws FileStorageException, IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Image image = new Image(file.getBytes());
        imageRepository.save(image);
    }

    public List<Image> listImages() {
        return imageRepository.findAll();
    }
}
