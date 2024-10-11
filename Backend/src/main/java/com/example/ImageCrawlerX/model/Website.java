package com.example.ImageCrawlerX.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;
    private int levels;

    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL)
    private List<Image> images;

    public Website() {
    }

    public Website(String name, String url, int levels) {
        this.name = name;
        this.url = url;
        this.levels = levels;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
