package com.example.actuatorservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class TreeDataClient {
    static final String ONE_HOP_URL = "https://sg31b0.familysearch.org/service/tree/tree-data/families/one-hop/";
    static final String BORN_YEAR_REGEX_1 = "\"id\":\"";
    static final String BORN_YEAR_REGEX_2 = "\".*?\"lifeSpan\":\"(\\d*)";
    static final int BORN_YEAR_REGEX_GROUP = 1;

    static int getBirthYearById(String id, String authHeader) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
              (outReq, bytes, clientHttpReqExec) -> {
                  outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
                  return clientHttpReqExec.execute(outReq, bytes);
              });
        try {
            String response = restTemplate.getForObject(ONE_HOP_URL + id, String.class);
            int birthYear = 0;
            if(StringUtils.hasLength(response)){
                Matcher matcher = getRegexPatternForId(id).matcher(response);
                if(matcher.find()){
                    birthYear = Integer.parseInt(matcher.group(BORN_YEAR_REGEX_GROUP));
                }
            }
            return birthYear;

        }
        catch (RestClientException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 1960;
    }

    private static Pattern getRegexPatternForId(String id){
        return Pattern.compile(BORN_YEAR_REGEX_1 + id + BORN_YEAR_REGEX_2);
    }
}
