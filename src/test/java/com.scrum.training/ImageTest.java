package com.scrum.training;

import com.scrum.training.entity.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageTest {

    @Test
    public void testGetId() throws NoSuchFieldException, IllegalAccessException {
        Image image = new Image();
        Long id = 1L;
        Field idField = image.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(image, id);
        assertThat(image.getId()).isEqualTo(id);
    }

    @Test
    public void testGetFile() throws Exception {
        // GIVE
        Image image = new Image();
        byte[] file = "hoge".getBytes();
        Field fileField = image.getClass().getDeclaredField("file");
        fileField.setAccessible(true);
        fileField.set(image, file);
        assertThat(image.getFile()).isEqualTo(file);

    }

}
