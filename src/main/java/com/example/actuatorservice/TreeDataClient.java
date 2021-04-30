package com.example.actuatorservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class TreeDataClient {
    static final String ONE_HOP_URL = "https://sg31b0.familysearch.org/service/tree/tree-data/families/one-hop/";
    static final String BORN_YEAR_REGEX_1 = "\"id\":\"";
    static final String BORN_YEAR_REGEX_2 = "\".*?\"lifeSpan\":\"(\\d*)";
    static final int BORN_YEAR_REGEX_GROUP = 1;
    static final int FAILURE = 0;

    public static int getBirthYearById(String id, HttpServletRequest request){
        String authHeader =  request.getHeader(HttpHeaders.AUTHORIZATION);
        if(id != null && !authHeader.isEmpty()){
            return getBirthYearById(id, authHeader);
        }
        return FAILURE;
    }
    private static int getBirthYearById(String id, String authHeader) {
        RestTemplate restTemplate = authenticatedTemplate(authHeader);
        try {
            String response = restTemplate.getForObject(ONE_HOP_URL + id, String.class);
            return parseBirthYear(id, response);

        }
        catch (RestClientException | NumberFormatException e) {
            e.printStackTrace();
        }
        return FAILURE;
    }

    private static int parseBirthYear(String id, String response) throws NumberFormatException {
        int birthYear = 0;
        if(StringUtils.hasLength(response)){
            Matcher matcher = getRegexPatternForId(id).matcher(response);
            if(matcher.find()){
                birthYear = Integer.parseInt(matcher.group(BORN_YEAR_REGEX_GROUP));
            }
        }
        return birthYear;
    }

    private static RestTemplate authenticatedTemplate(String authHeader){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
              (outReq, bytes, clientHttpReqExec) -> {
                  outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
                  return clientHttpReqExec.execute(outReq, bytes);
              });
        return restTemplate;
    }

    private static Pattern getRegexPatternForId(String id){
        return Pattern.compile(BORN_YEAR_REGEX_1 + id + BORN_YEAR_REGEX_2);
    }
}
