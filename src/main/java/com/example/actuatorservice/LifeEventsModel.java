package com.example.actuatorservice;

import java.util.ArrayList;
import java.util.List;

public class LifeEventsModel {

    public static int NUM_EVENTS = 5;
    public static int EVENT_OFFSET = 10;

    List<YearEventsModel> lifeEvents;

    public LifeEventsModel(int year) {
        lifeEvents = new ArrayList<>();
        lifeEvents.add(new YearEventsModel(EventDAO.getEvents(year)));

        for(int i=1; i<=NUM_EVENTS; i++) {
            lifeEvents.add(new YearEventsModel(EventDAO.getEvents(year + EVENT_OFFSET * i)));
        }
    }

    public List<YearEventsModel> getLifeEventsEvents() {
        return lifeEvents;
    }

}
