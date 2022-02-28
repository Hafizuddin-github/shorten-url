package com.example.shortenurl.services;

import com.example.shortenurl.dtos.response.ShortenUrlResponse;
import org.springframework.web.server.ResponseStatusException;

public interface ShortenUrlService {
    ShortenUrlResponse addShortenUrl(String url);
    ShortenUrlResponse getUrl(String shortenUrl) throws ResponseStatusException;
}
