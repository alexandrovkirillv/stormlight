package com.secretNet.secNet.models;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Date;

import static com.secretNet.secNet.models.Post.DATE_FORMAT;


@Entity
@Data
@Table(name = "run_post")
@EntityScan
public class RunPost implements Comparable<RunPost> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer trainNumber;
    private String runTime;
    @Column(nullable = false)
    private String time;
    private String ofpTime;
    private String pulse;
    @Column (length = 10000)
    private String comment;
    private String userName;

    public RunPost() {
    }

    public RunPost(Integer trainNumber, String runTime, String ofpTime, String pulse, String userName, String comment) {
        this.trainNumber = trainNumber;
        this.ofpTime = ofpTime;
        this.pulse = pulse;
        this.runTime = runTime;
        this.time = DATE_FORMAT.format(new Date());
        this.userName = userName;
        this.comment = comment;
    }

    @Override
    public int compareTo(RunPost o) {
        return trainNumber.compareTo(o.getTrainNumber());
    }
}
