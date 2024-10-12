package com.example.ImageCrawlerX.controller;

import com.example.ImageCrawlerX.dtos.CrawlRequest; // Import the new DTO class
import com.example.ImageCrawlerX.model.Image;
import com.example.ImageCrawlerX.model.Website;
import com.example.ImageCrawlerX.service.ImageFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")  // Base URL for REST endpoints
public class ImageFinderRestController {

    @Autowired
    private ImageFinderService imageFinderService;

    // REST endpoint to start the crawling process
    @PostMapping("/crawl")
    public Website crawlWebsite(@RequestBody CrawlRequest request) throws IOException {
        return imageFinderService.crawlWebsite(request.getUrl(), request.getName(), request.getLevels());
    }

    // REST endpoint to get all images
    @GetMapping("/images")
    public List<Image> allImages() {
        return imageFinderService.getAllImages();
    }

    // REST endpoint to get a website by its ID
    @GetMapping("/website/{id}")
    public Website websiteById(@PathVariable Long id) {
        return imageFinderService.getWebsiteById(id);
    }
}
