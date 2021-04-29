package com.example.actuatorservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YearEventsModel {

    List<EventModel> events;

    public YearEventsModel(File file) {
        events = new ArrayList<>();
    }

    public List<EventModel> getEvents() {
        return events;
    }

}
