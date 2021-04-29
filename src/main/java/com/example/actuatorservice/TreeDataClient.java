package com.example.actuatorservice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TreeDataClient {
    static final String ONE_HOP_URL = "https://sg32i0.familysearch.org/service/tree/tree-data/families/one-hop/";

    static int getBirthYearById(String id, String authToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(ONE_HOP_URL+id, HttpMethod.GET, entity, String.class);

        System.out.println(result);
        return 1960;
    }
}
