package com.example.actuatorservice;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventsController {

    @GetMapping("/event")
    @ResponseBody
    public YearEventsModel event(@RequestParam(name="year") Integer year) throws IOException {
        System.out.println("year: " + year);
        return YearEventsModel.createFromYear(year);
    }

    @GetMapping("/life-events")
    @ResponseBody
    public LifeEventsModel lifeEvents(@RequestParam(name="year") Integer year) {
        LifeEventsModel life = new LifeEventsModel(year);
        System.out.println(life);
        return life;
    }
}