package com.example.shortenurl.services.impl;

import com.example.shortenurl.dtos.response.ShortenUrlResponse;
import com.example.shortenurl.models.ShortenUrl;
import com.example.shortenurl.repositories.ShortenUrlRepository;
import com.example.shortenurl.services.ShortenUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.validator.routines.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortenUrlServiceImpl implements ShortenUrlService {

    @Value("${fe.url}")
    private String domainFE;

    private final ShortenUrlRepository shortenUrlRepository;

    @Override
    public ShortenUrlResponse addShortenUrl(String url) throws Exception {
        ShortenUrl oldShortenUrl = shortenUrlRepository.findFirstByUrl(url);
        String fullShortenUrl = domainFE;
        String tempUrl = url;

        if(Objects.isNull(oldShortenUrl)) {
            String shortenUrl = UUID.randomUUID().toString().substring(0,6);
            while(shortenUrlRepository.existsByShortenUrl(shortenUrl)) {
                shortenUrl = UUID.randomUUID().toString().substring(0,6);
            }

            if(!url.startsWith("http://") || !url.startsWith("https://")) {
                tempUrl = "http://" + url;
            }

            UrlValidator defaultValidator = new UrlValidator();
            if (!defaultValidator.isValid(tempUrl)) {
                throw new Exception("Not valid URL");
            }

            ShortenUrl newShortenUrl = ShortenUrl.builder()
                    .url(url)
                    .shortenUrl(shortenUrl)
                    .creationDate(System.currentTimeMillis())
                    .build();
            shortenUrlRepository.save(newShortenUrl);
            fullShortenUrl += shortenUrl;
        } else {
            fullShortenUrl += oldShortenUrl.getShortenUrl();
        }
        return ShortenUrlResponse.builder().url(url).shortenUrl(fullShortenUrl).build();
    }

    @Override
    public ShortenUrlResponse getUrl(String shortenUrl) throws ResponseStatusException {
        ShortenUrl shortenUrlObject = shortenUrlRepository.findFirstByShortenUrl(shortenUrl);
        if(Objects.nonNull(shortenUrlObject)) {
            String url = shortenUrlObject.getUrl();
            if(!url.startsWith("http://") || !url.startsWith("https://")) {
                url = "http://" + url;
            }
            return ShortenUrlResponse.builder().shortenUrl(shortenUrl).url(url).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Shorten URL not found");
        }
    }
}
