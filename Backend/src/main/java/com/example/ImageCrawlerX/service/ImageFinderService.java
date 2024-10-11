package com.example.ImageCrawlerX.service;

import com.example.ImageCrawlerX.model.Image;
import com.example.ImageCrawlerX.model.Website;
import com.example.ImageCrawlerX.repository.ImageRepository;
import com.example.ImageCrawlerX.repository.WebsiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImageFinderService {

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private ImageRepository imageRepository;

    private static final int THREAD_COUNT = 10;

    // Method to start crawling the website
    public Website crawlWebsite(String url, String name, int levels) throws IOException {
        Website website = new Website(name, url, levels);
        websiteRepository.save(website);

        Set<String> visitedUrls = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        // Use a queue for BFS traversal
        Queue<String> queue = new LinkedList<>();
        queue.offer(url);

        // Perform multi-threaded BFS crawling
        while (!queue.isEmpty() && levels > 0) {
            int currentLevelSize = queue.size();
            for (int i = 0; i < currentLevelSize; i++) {
                String currentUrl = queue.poll();
                if (!visitedUrls.contains(currentUrl)) {
                    visitedUrls.add(currentUrl);
                    final String urlToCrawl = currentUrl;

                    // Submit crawling task to thread pool
                    executorService.submit(() -> {
                        try {
                            crawlPage(website, urlToCrawl, queue, visitedUrls);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            levels--;
        }

        // Shutdown the executor service after all tasks are done
        executorService.shutdown();
        try {
            // Manually wait for termination (10 minutes = 600000 milliseconds)
            long timeoutMillis = 600000; // 10 minutes
            long pollInterval = 1000;    // Check every second
            long waitedTime = 0;

            while (!executorService.isTerminated() && waitedTime < timeoutMillis) {
                Thread.sleep(pollInterval); // Wait for the poll interval
                waitedTime += pollInterval;
            }

            // If not terminated after the timeout, force shutdown
            if (!executorService.isTerminated()) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        return websiteRepository.save(website);
    }

    // Method to crawl a single page and collect images and links
    private void crawlPage(Website website, String url, Queue<String> queue, Set<String> visitedUrls) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements images = document.select("img");

        // Save all images on the current page
        for (Element img : images) {
            String imgUrl = img.absUrl("src");
            Image image = new Image(imgUrl, website);
            imageRepository.save(image);
        }

        // Find all links on the page and add to the queue
        Elements links = document.select("a[href]");
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (!visitedUrls.contains(nextUrl)) {
                queue.offer(nextUrl);
            }
        }
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Website getWebsiteById(Long id) {
        return websiteRepository.findById(id).orElse(null);
    }

}
