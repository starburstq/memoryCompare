package com.example.actuatorservice;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/preload")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Boolean preload(HttpServletRequest request) throws IOException {
        for(int i=1959; i< 2022; i++){
            YearEventsModel.createFromYear(i);
        }
        return true;
    }


    private int getYear(String id, HttpServletRequest request){
        Integer year = tryParseInt(id);
        if(year != null){
            return year;
        }
        return TreeDataClient.getBirthYearById(id, request);
    }

    private Integer tryParseInt(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignore) {
            return null;
        }
    }
}