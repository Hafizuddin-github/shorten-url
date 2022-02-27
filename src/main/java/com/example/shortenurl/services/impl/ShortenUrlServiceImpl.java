package com.example.shortenurl.services.impl;

import com.example.shortenurl.dtos.response.ShortenUrlResponse;
import com.example.shortenurl.models.ShortenUrl;
import com.example.shortenurl.repositories.ShortenUrlRepository;
import com.example.shortenurl.services.ShortenUrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShortenUrlServiceImpl implements ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    @Override
    public ShortenUrlResponse addShortenUrl(String url) {
        ShortenUrl oldShortenUrl = shortenUrlRepository.findFirstByUrl(url);

        if(Objects.isNull(oldShortenUrl)) {
            String shortenUrl = UUID.randomUUID().toString().substring(0,6);
            while(shortenUrlRepository.existsByShortenUrl(shortenUrl)) {
                shortenUrl = UUID.randomUUID().toString().substring(0,6);
            }
            ShortenUrl newShortenUrl = ShortenUrl.builder()
                    .url(url)
                    .shortenUrl(shortenUrl)
                    .creationDate(System.currentTimeMillis())
                    .build();
            shortenUrlRepository.save(newShortenUrl);
            return ShortenUrlResponse.builder().url(url).shortenUrl(shortenUrl).build();
        }
        return ShortenUrlResponse.builder().url(url).shortenUrl(oldShortenUrl.getShortenUrl()).build();

    }

    @Override
    public ShortenUrlResponse getUrl(String shortenUrl) throws ResponseStatusException {
        ShortenUrl shortenUrlObject = shortenUrlRepository.findFirstByShortenUrl(shortenUrl);
        if(Objects.nonNull(shortenUrlObject)) {
            return ShortenUrlResponse.builder().shortenUrl(shortenUrl).url(shortenUrlObject.getUrl()).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Shorten URL not found");
        }
    }
}
