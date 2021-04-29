package com.example.actuatorservice;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventsController {

    @GetMapping("/events")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public YearEventsModel events(@RequestParam(name="year") Integer year) throws IOException {
        return YearEventsModel.createFromYear(year);
    }

    @GetMapping("/life-events")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public LifeEventsModel lifeEvents(@RequestParam(name="year", required=false) Integer year,
                                      @RequestParam(name="id", required=false) String id,
                                      @RequestHeader(name="Authorization", required=false) String authToken) {
        if(year !=null){
            return new LifeEventsModel(year);
        }
        if(id != null & authToken != null){
            return new LifeEventsModel(TreeDataClient.getBirthYearById(id, authToken));
        }
        return null;
    }
}