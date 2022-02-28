package com.example.shortenurl.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Document("url")
@TypeAlias("url")
public class ShortenUrl implements Serializable {

    @Id
    private String id;
    private String url;
    private String shortenUrl;
    private Long creationDate;
}
