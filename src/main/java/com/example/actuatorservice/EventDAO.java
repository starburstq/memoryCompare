package com.example.actuatorservice;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

public class EventDAO {

    static File getEvents(int year) throws IOException {
        return new ClassPathResource("events_of_" + year).getFile();
    }
}
