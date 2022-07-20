package com.secretNet.secNet.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Data
@EntityScan
@NoArgsConstructor
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
    @Column(name = "user_name")
    private String userName;
    @Value("${date-format}")
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss 'GMT'");
    public static String timeZone = "GMT";

    public Post(String title, String anons, String full_text, String userName) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
        this.time = DATE_FORMAT.format(new Date());
        this.userName = userName;
    }

    @Override
    public int compareTo(Post o) {
        return time.compareTo(o.getTime());
    }
}
