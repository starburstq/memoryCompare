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
    private static final String WIKIPEDIA = "https://en.wikipedia.org/";
    private static final String TOP_MOVIES = WIKIPEDIA + "wiki/List_of_highest-grossing_films";
    private static final String TOP_MUSIC = WIKIPEDIA + "wiki/List_of_Billboard_Hot_100_number_ones_of_";
    private static final String MOVIE_REGEX_1 = "High-grossing films by year of release.*?>";
    private static final String MOVIE_REGEX_2 = "<.*?<a.*?href=\"(.*?)\".*?>(.*?)</a>";
    private static final String MUSIC_REGEX = "(?:#FFFF99\"|#ff9)[^#]*?href=\"(.*?)\".*?>(.*?)<\\/a>";
    private static final String MOVIE_DETAILS_REGEX = "src=\"([^\"]*?\\.jpg)\".*href=\"([^\"]*?imdb[^\"]*?)\"";
    private static final String MUSIC_DETAILS_REGEX = "src=\"([^\"]*?\\.jpg)\"";
    private static final int SRC_GROUP = 1;
    private static final int IMDB_GROUP = 2;
    private static final int LYRICS_GROUP = 2;
    private static final int MOVIE_URL_REGEX_GROUP = 1;
    private static final int MOVIE_TITLE_REGEX_GROUP = 2;
    private static final int MUSIC_URL_REGEX_GROUP = 1;
    private static final int MUSIC_TITLE_REGEX_GROUP = 2;

    private static final String EVENT_FILE_NAME_PREFIX = "events_of_";

    static YearEventsModel getEvents(int year){
        File rawEvents;
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
            scrapeMusicData(yearEvents);

            writeEventFile(yearEvents);
        }
        catch (Exception ioException) {
            ioException.printStackTrace();
        }
        return yearEvents;
    }

    static void scrapeMovieData(YearEventsModel yearEvents) {
        setMovieData(yearEvents, getHTML(TOP_MOVIES));
    }
    static void scrapeMusicData(YearEventsModel yearEvents) {
        setMusicData(yearEvents, getHTML(TOP_MUSIC + yearEvents.getYear()));
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
                Matcher details = getDetails(matcher.group(MOVIE_URL_REGEX_GROUP), MOVIE_DETAILS_REGEX);
                if(details.find()){
                    event.setImgUrl(details.group(SRC_GROUP));
                    event.setMediaUrl(details.group(IMDB_GROUP));
                }
                event.setName(matcher.group(MOVIE_TITLE_REGEX_GROUP));
                yearEvents.getEvents().add(event);
            }
        }
    }
    private static void setMusicData(YearEventsModel yearEvents, String html) {
        if(org.springframework.util.StringUtils.hasLength(html)){
            Matcher matcher = getRegexPatternForMusic(yearEvents.getYear()).matcher(html);
            if(matcher.find()){
                EventModel event = new EventModel();
                event.setType(EventType.Music);
                Matcher details = getDetails(matcher.group(MUSIC_URL_REGEX_GROUP), MUSIC_DETAILS_REGEX);
                if(details.find()){
                    event.setImgUrl(details.group(SRC_GROUP));
                    event.setMediaUrl("unknown");
                }
                event.setName(matcher.group(MUSIC_TITLE_REGEX_GROUP));
                yearEvents.getEvents().add(event);
            }
        }
    }
    private static Matcher getDetails(String wikiPage, String regex) {
        String html = getHTML(WIKIPEDIA+wikiPage);
        return Pattern.compile(regex).matcher(html);
    }

    private static Pattern getRegexPatternForMovie(int year){
        return Pattern.compile(MOVIE_REGEX_1 + year + MOVIE_REGEX_2);
    }
    private static Pattern getRegexPatternForMusic(int year){
        return Pattern.compile(MUSIC_REGEX);
    }
}
