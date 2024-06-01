package com.esprit.jobfinder.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private int views;
    private int downloads;

    @ElementCollection
    private List<String> skills;

    @Lob
    private String linkedInData;

}
