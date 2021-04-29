package com.example.actuatorservice;

public class EventModel {

    private String name;
    private String imgUrl;
    private String mediaUrl;
    private EventType type;

    public EventModel() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setType(EventType type) {
        this.type = type;
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
