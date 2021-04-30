package com.example.actuatorservice;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

public class EventDAO {
    private static final String HIGHEST_GROSSING_MOVIES = "https://en.wikipedia.org/wiki/List_of_highest-grossing_films";
    private static final String MOVIE_REGEX_1 = "High-grossing films by year of release.*?>";
    private static final String MOVIE_REGEX_2 = "<.*?<a.*?>(.*?)<\\/a>";
    private static final int MOVIE_TITLE_REGEX_GROUP = 1;

    private static final String EVENT_FILE_NAME_PREFIX = "events_of_";

    static YearEventsModel getEvents(int year){
        File rawEvents = null;
        ClassPathResource resource = new ClassPathResource(EVENT_FILE_NAME_PREFIX + year);

        YearEventsModel model = null;
        try {
            if(resource.isFile()){
                rawEvents = resource.getFile();
                if(rawEvents.exists()){
                    model = new ObjectMapper().readValue(rawEvents,YearEventsModel.class);
                }
            }
            else {
                model = scrapeWebForData(year);
                writeEventFile(model);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    static YearEventsModel scrapeWebForData(int year){
        YearEventsModel yearEvents = new YearEventsModel();
        try {
            yearEvents.setYear(year);

            scrapeMovieData(yearEvents);

            writeEventFile(yearEvents);
        }
        catch (Exception ioException) {
            ioException.printStackTrace();
        }
        return yearEvents;
    }

    static void scrapeMovieData(YearEventsModel yearEvents) {
        setMovieData(yearEvents, getHTML(HIGHEST_GROSSING_MOVIES));
    }

    private static void writeEventFile(YearEventsModel model) {
        try {
            URL path = null;
            if (path != null) {
                File file = new File(path.toURI());
                new ObjectMapper().writeValue(file, model);
            }
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String getHTML(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String html = restTemplate.getForObject(url, String.class);
        if(StringUtils.isNotEmpty(html)) {
            html = html.replace("\n", "").replace("\r", "");
        }
        return html;
    }

    private static void setMovieData(YearEventsModel yearEvents, String html) {
        if(org.springframework.util.StringUtils.hasLength(html)){
            Matcher matcher = getRegexPatternForMovie(yearEvents.getYear()).matcher(html);
            if(matcher.find()){
                EventModel event = new EventModel();
                event.setType(EventType.Movie);
                event.setImgUrl("test");
                event.setMediaUrl("test");
                event.setName(matcher.group(MOVIE_TITLE_REGEX_GROUP));
                yearEvents.getEvents().add(event);
            }
        }
    }


    private static Pattern getRegexPatternForMovie(int year){
        return Pattern.compile(MOVIE_REGEX_1 + year + MOVIE_REGEX_2);
    }
}
