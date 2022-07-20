package com.secretNet.secNet.models;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Date;

import static com.secretNet.secNet.models.Post.DATE_FORMAT;


@Entity
@Data
@Table(name = "pulse")
@EntityScan
public class Pulse implements Comparable<Pulse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer restPulse;
    private Integer wakeUpPulse;
    private Integer diffPulse;
    private Integer firstMinutePulse;
    private Integer secondMinutePulse;
    private Integer thirdMinutePulse;
    private Integer result;
    @Column(nullable = false)
    private String time;
    @Column (length = 10000)
    private String comment;
    private String userName;

    public Pulse() {
    }


    public Pulse(Integer restPulse, Integer wakeUpPulse, Integer firstMinutePulse,
                 Integer secondMinutePulse, Integer thirdMinutePulse, String comment, String userName) {
        this.restPulse = restPulse;
        this.wakeUpPulse = wakeUpPulse;
        this.diffPulse = Math.abs(restPulse - wakeUpPulse);
        this.firstMinutePulse = firstMinutePulse;
        this.secondMinutePulse = secondMinutePulse;
        this.thirdMinutePulse = thirdMinutePulse;
        this.time = DATE_FORMAT.format(new Date());
        this.comment = comment;
        this.userName = userName;
        this.result = restPulse + wakeUpPulse + diffPulse * 10 + firstMinutePulse + secondMinutePulse
                + thirdMinutePulse;
    }

    @Override
    public int compareTo(Pulse o) {
        return time.compareTo(o.getTime());
    }
}
