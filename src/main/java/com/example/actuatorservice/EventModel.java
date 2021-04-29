package com.example.actuatorservice;

public class EventModel {

    private final String name;
    private final String imgUrl;
    private final String mediaUrl;
    private final EventType type;

    public EventModel(String name, String imgUrl, String mediaUrl, EventType type) {
        this.name     = name;
        this.imgUrl   = imgUrl;
        this.mediaUrl = mediaUrl;
        this.type     = type;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public EventType getType() {
        return type;
    }

}
