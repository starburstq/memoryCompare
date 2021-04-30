package com.example.actuatorservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YearEventsModel {

    private List<EventModel> events;
    private int year;

    public YearEventsModel(){
        events = new ArrayList<>();
    }

    public static YearEventsModel createFromYear(int year) throws IOException {
        return EventDAO.getEvents(year);
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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
