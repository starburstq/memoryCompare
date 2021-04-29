package com.example.actuatorservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YearEventsModel {

    List<EventModel> events;
    int year;

    public YearEventsModel(){
        events = new ArrayList<>();
    }

    public static YearEventsModel createFromYear(int year) throws IOException {
        File rawEvents = EventDAO.getEvents(year);
        if(rawEvents.exists()){
            return new ObjectMapper().readValue(rawEvents,YearEventsModel.class);
        }
        return null;
    }

    public int getYear() {
        return year;
    }

    public List<EventModel> getEvents() {
        return events;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setEvents(List<EventModel> events) {
        this.events = events;
    }
}
