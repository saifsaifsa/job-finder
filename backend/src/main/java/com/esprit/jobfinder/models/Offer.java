package com.esprit.jobfinder.models;

import java.util.Date;
import java.util.List;

import com.esprit.jobfinder.models.enums.ExperienceLevel;
import com.esprit.jobfinder.utiles.BadWordUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String type;
    private Date creationDate;
    private String status;
    private int nombreVu;
    private String experience;
    private String niveauEtude;
    private String tags;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    public void validate() throws Exception {
        if (BadWordUtils.containsBadWord(title) ||
            BadWordUtils.containsBadWord(description) ||
            BadWordUtils.containsBadWord(type) ||
            BadWordUtils.containsBadWord(status) ||
            BadWordUtils.containsBadWord(experience) ||
            BadWordUtils.containsBadWord(niveauEtude) ||
            BadWordUtils.containsBadWord(tags)) {
            throw new Exception("The offer contains inappropriate language.");
        }
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "offres")
    private Collection<User> users;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    Company company;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
