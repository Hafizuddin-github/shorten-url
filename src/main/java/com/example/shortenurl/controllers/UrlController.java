package com.example.shortenurl.controllers;

import com.example.shortenurl.dtos.requests.ShortenUrlRequest;
import com.example.shortenurl.dtos.response.ShortenUrlResponse;
import com.example.shortenurl.services.ShortenUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/url")
@RequiredArgsConstructor
public class UrlController {
    private final ShortenUrlService shortenUrlService;

    @PostMapping("")
    public void addNewUrl(@RequestBody ShortenUrlRequest request) {
        shortenUrlService.addShortenUrl(request.getUrl());
    }

    @GetMapping("")
    public ShortenUrlResponse getUrl(@RequestParam String url) {
        return shortenUrlService.getUrl(url);
    }
}
