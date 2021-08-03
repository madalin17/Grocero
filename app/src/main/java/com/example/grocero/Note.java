package com.example.grocero;

public class Note {
    private long id;
    private String title;
    private String date;
    private String time;

    Note(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    Note(long id,String title, String date, String time) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
