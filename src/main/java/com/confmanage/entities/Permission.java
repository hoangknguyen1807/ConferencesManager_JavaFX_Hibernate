package com.confmanage.entities;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {

    public static final int USER = 0;
    public static final int ADMIN = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;

    @Column(length = 30, nullable = false)
    private String name;

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String permissionName) {
        this.name = permissionName;
    }
}
