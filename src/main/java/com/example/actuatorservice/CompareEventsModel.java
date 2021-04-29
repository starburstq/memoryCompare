package com.example.actuatorservice;

import java.util.ArrayList;
import java.util.List;

public class CompareEventsModel {

    List<EventModel> earlierEvents;
    List<EventModel> laterEvents;

    public CompareEventsModel(YearEventsModel earlier, YearEventsModel later) {
        earlierEvents = new ArrayList<>();
        laterEvents = new ArrayList<>();
    }

    public List<EventModel> getEarlierEvents() {
        return earlierEvents;
    }

    public List<EventModel> getLaterEvents() {
        return laterEvents;
    }
}
