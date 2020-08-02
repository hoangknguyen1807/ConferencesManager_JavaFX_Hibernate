package com.confmanage.entities;

import javax.persistence.*;

@Entity
@Table(name = "conference_user")
public class Conference_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "confId")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private boolean approved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User participant) {
        this.user = participant;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
