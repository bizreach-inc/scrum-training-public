package com.scrum.training;

import com.scrum.training.controller.ImageController;
import com.scrum.training.exception.FileStorageException;
import com.scrum.training.form.ImageForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageControllerTest {

    @Autowired
    private ImageController controller;

    @Test
    public void getImageInputPage() {
        assertThat(controller.get(new ImageForm())).isEqualTo("upload");
    }

    @Test
    public void addNewImageTest() throws Exception, FileStorageException {
        InputStream inputStream = ImageControllerTest.class.getClassLoader().getResourceAsStream("cute_cat.jpg");
        MockMultipartFile cuteCatImage = new MockMultipartFile("cuteCatImage", inputStream);
        ImageForm form = new ImageForm();
        form.setFile(cuteCatImage);
        assertThat(controller.add(form, new BeanPropertyBindingResult("test", "test"), UriComponentsBuilder.newInstance()))
                .isEqualTo("redirect:/");
    }
}
