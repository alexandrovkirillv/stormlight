package com.secretNet.secNet.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity

public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String anons;
    private String full_text;
    @Column(nullable = false)
    private String time;
    private int views;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss 'Томск'");
    public static final String ASIA_NOVOSIBIRSK = "Asia/Novosibirsk";

    public Post() {
    }

    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ASIA_NOVOSIBIRSK));
        this.time = DATE_FORMAT.format(new Date());
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public int compareTo(Post o) {
        return time.compareTo(o.getTime());
    }
}
