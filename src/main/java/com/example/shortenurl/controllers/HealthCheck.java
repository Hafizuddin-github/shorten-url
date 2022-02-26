package com.example.shortenurl.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheck {

    @GetMapping(value = "/hc")
    public ResponseEntity<Map> healthCheck() {
        HashMap map = new HashMap<String, Object>();
        map.put("status", HttpStatus.ACCEPTED);
        return new ResponseEntity<Map>(map,HttpStatus.ACCEPTED);
    }

}
