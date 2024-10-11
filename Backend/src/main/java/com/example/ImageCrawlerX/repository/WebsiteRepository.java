package com.example.ImageCrawlerX.repository;

import com.example.ImageCrawlerX.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
    Website findByUrl(String url);
}
