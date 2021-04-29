package com.example.actuatorservice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventsController {

    @GetMapping("/events/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public YearEventsModel events(@PathVariable("id") String id,
                                  HttpServletRequest request) throws IOException {
        return YearEventsModel.createFromYear(getYear(id, request));
    }

    @GetMapping("/life-events/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public LifeEventsModel lifeEvents(@PathVariable("id") String id,
                                      HttpServletRequest request) {
        return new LifeEventsModel(getYear(id,request));
    }

    private int getYear(String id, HttpServletRequest request){
        int year;
        try {
            year = Integer.parseInt(id);
            return year;
        } catch (NumberFormatException ignore) {}

        String authHeader =  request.getHeader(HttpHeaders.AUTHORIZATION);
        if(id != null && !authHeader.isEmpty()){
            return TreeDataClient.getBirthYearById(id, authHeader);
        }
        return 0;
    }
}