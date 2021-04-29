package com.example.actuatorservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LifeEventsModel {

    public static int[] EVENT_OFFSETS = {0,3,10,17,25,35,45,55,65,75,85,95};

    List<YearEventsModel> lifeEvents;

    public LifeEventsModel(int year) {
        lifeEvents = new ArrayList<>();

        for(int i = 0; i < Arrays.stream(EVENT_OFFSETS).count(); i++) {
            try{
                lifeEvents.add(YearEventsModel.createFromYear(year + (EVENT_OFFSETS[i])));
            }
            catch (IOException ignored) { }
        }
    }

    public List<YearEventsModel> getLifeEventsEvents() {
        return lifeEvents;
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
