package com.example.actuatorservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventsController {

    @GetMapping("/event")
    @ResponseBody
    public YearEventsModel event(@RequestParam(name="year", required=true) Integer year) {
        System.out.println("year: " + year);
        return new YearEventsModel(EventDAO.getEvents(year));
    }

    @GetMapping("/lifeEvents")
    @ResponseBody
    public LifeEventsModel lifeEvents(@RequestParam(name="year", required=true) Integer year) {
        System.out.println("year: " + year);
        return new LifeEventsModel(year);
    }
}