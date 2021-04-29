package com.example.actuatorservice;

import org.springframework.web.client.RestTemplate;

public class TreeDataClient {
    static final String ONE_HOP_URL = "https://sg32i0.familysearch.org/service/tree/tree-data/families/one-hop/";

    static int getBirthYearById(String id, String authToken){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(ONE_HOP_URL, String.class);

        System.out.println(result);
        return 1960;
    }
}
