package com.scrum.training.form;

import com.scrum.training.validation.FileRequired;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ImageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @FileRequired
    private MultipartFile file;

    // TODO nanashi ボイラー排除 (2019/06/19)
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
