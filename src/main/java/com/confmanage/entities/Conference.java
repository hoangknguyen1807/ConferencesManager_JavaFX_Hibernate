package com.confmanage.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conference")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int confId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200)
    private String brief;

    @Column(length = 1000)
    private String detail;

    @Column(length = 100)
    private String image;

    private Date date;

    @OneToOne
    private Venue venue;

//    @OneToMany
//    private List<User> participants;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Conference_User> participants;

    public int getConfId() {
        return confId;
    }

    public void setConfId(int confId) {
        this.confId = confId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

//    public List<User> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(List<User> participants) {
//        this.participants = participants;
//    }

    public boolean isFull() {
        return participants.size() >= venue.getCapacity();
    }

    public List<Conference_User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Conference_User> participants) {
        this.participants = participants;
    }
}
