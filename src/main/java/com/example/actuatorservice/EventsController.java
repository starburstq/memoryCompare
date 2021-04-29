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
        return YearEventsModel.createFromYear(year);
    }

    @GetMapping("/life-events")
    @ResponseBody
    public LifeEventsModel lifeEvents(@RequestParam(name="year") Integer year) {
        return new LifeEventsModel(year);
    }

    @GetMapping("/life-events")
    @ResponseBody
    public LifeEventsModel lifeEvents(@RequestParam(name="id") String id) {
        return new LifeEventsModel(TreeDataClient.getBirthYearById(id));
    }
}