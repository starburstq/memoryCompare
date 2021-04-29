package com.example.actuatorservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YearEventsModel {

    List<EventModel> events;
    int year;

    public YearEventsModel(int year) {
        File rawEvents = EventDAO.getEvents(year);
        this.year = year;
        events = new ArrayList<>();
    }
    public int getYear() {
        return year;
    }

    public List<EventModel> getEvents() {
        return events;
    }

}
