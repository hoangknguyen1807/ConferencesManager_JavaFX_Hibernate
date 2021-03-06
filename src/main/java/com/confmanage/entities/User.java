package com.confmanage.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 100)
    private String fullName;

    @Column(length = 30, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50)
    private String email;

    @OneToOne
    private Permission permission;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Conference_User> enrolledConferences;

    public User() {
        isActive = true;
    }

    public User(Permission pms) {
        this();
        this.permission = pms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean state) {
        isActive = state;
    }

    public List<Conference_User> getEnrolledConferences() {
        return enrolledConferences;
    }

    public void setEnrolledConferences(List<Conference_User> enrolledConferences) {
        this.enrolledConferences = enrolledConferences;
    }
}
