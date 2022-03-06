package com.example.shortenurl.services;

import com.example.shortenurl.BaseTestCase;
import com.example.shortenurl.dtos.response.ShortenUrlResponse;
import com.example.shortenurl.models.ShortenUrl;
import com.example.shortenurl.repositories.ShortenUrlRepository;
import com.example.shortenurl.services.impl.ShortenUrlServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ShortenUrlServiceTest extends BaseTestCase {

    @Value("${fe.url}")
    private String domainFE;

    @InjectMocks
    private ShortenUrlServiceImpl shortenUrlService;

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @Test
    void getUrl_whenShortenUrlIsValid_thenReturnShortenUrlResponse() {
        ShortenUrl shortenUrlObject = new ShortenUrl();
        shortenUrlObject.setShortenUrl("Mock");
        shortenUrlObject.setUrl("www.examplemock.com");

        ShortenUrlResponse result = ShortenUrlResponse.builder().url("http://www.examplemock.com").shortenUrl("Mock").build();

        when(shortenUrlRepository.findFirstByShortenUrl("Mock")).thenReturn(shortenUrlObject);
        ShortenUrlResponse shortenUrlResponse = shortenUrlService.getUrl("Mock");
        assertEquals(shortenUrlResponse, result);
    }

    @Test
    void getUrl_whenShortenUrlIsInValid_thenThrowResponseStatusException() {
        assertThrows(ResponseStatusException.class, () -> {
            shortenUrlService.getUrl("Mock");
        });
    }

    @Test
    void addShortenUrl_whenUrlIsNewAndValid_thenSuccess() throws Exception {
        String url = "www.google.com";
        UUID uuid = UUID.randomUUID();
        try (MockedStatic<UUID> utilities = Mockito.mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(uuid);
            assertEquals(UUID.randomUUID(), uuid);
            ShortenUrlResponse mockResponse = ShortenUrlResponse.builder()
                .url(url)
                .shortenUrl("nullnull-n")
                .build();

            ShortenUrlResponse result = shortenUrlService.addShortenUrl(url);
            assertEquals(mockResponse, result);
        }
    }

    @Test
    void addShortenUrl_whenUrlIsNewAndInValid_thenThrowException() {
        String url = "googlecom";
        assertThrows(Exception.class, () -> {
            shortenUrlService.addShortenUrl(url);
        });
    }

    @Test
    void addShortenUrl_whenUrlExist_thenSuccess() throws Exception {
        String url = "http://www.google.com";

        ShortenUrl mockShortenUrl = ShortenUrl.builder().shortenUrl("mock").url(url).build();
        ShortenUrlResponse mockResponse = ShortenUrlResponse.builder().shortenUrl(domainFE+"mock").url(url).build();

        when(shortenUrlRepository.findFirstByUrl(url)).thenReturn(mockShortenUrl);

        ShortenUrlResponse result = shortenUrlService.addShortenUrl(url);
        assertEquals(mockResponse, result);
    }
}
