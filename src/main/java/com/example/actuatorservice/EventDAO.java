package com.example.actuatorservice;

import java.io.File;

public class EventDAO {

    static File getEvents(int year){
        return new File("events_of_"+year);
    }
}
