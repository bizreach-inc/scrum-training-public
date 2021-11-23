package com.scrum.training.entity;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Image {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

    @Lob
    @Column(name = "fileData")
    private byte[] file;

    public Image() {}

    public Image(byte[] file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getBase64Image() {
        return  "data:image/jpeg;base64," + Base64.encodeBase64String(getFile());
    }
}
