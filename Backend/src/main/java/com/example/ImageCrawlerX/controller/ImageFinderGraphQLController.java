package com.example.ImageCrawlerX.controller;


import com.example.ImageCrawlerX.model.Image;
import com.example.ImageCrawlerX.model.Website;
import com.example.ImageCrawlerX.service.ImageFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;


@Controller
public class ImageFinderGraphQLController {

    @Autowired
    private ImageFinderService imageFinderService;

    @MutationMapping
    public Website crawlWebsite(@Argument String url, @Argument String name, @Argument int levels) throws IOException {
        return imageFinderService.crawlWebsite(url, name, levels);
    }

    @QueryMapping
    public List<Image> allImages() {
        return imageFinderService.getAllImages();
    }

    @QueryMapping
    public Website websiteById(Long id) {
        return imageFinderService.getWebsiteById(id);
    }
}