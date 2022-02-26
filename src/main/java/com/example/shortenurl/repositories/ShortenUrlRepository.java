package com.example.shortenurl.repositories;

import com.example.shortenurl.models.ShortenUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenUrlRepository extends MongoRepository<ShortenUrl, String> {
    Boolean existsByShortenUrl(String shortenUrl);

    Boolean existsByUrl(String url);
}
