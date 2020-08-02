package com.confmanage.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "permission")
public class Permission {

    public static final int USER = 0;
    public static final int ADMIN = 1;

    public static Map<Integer, String> getMap() {
        return map;
    }

    private static final Map<Integer, String> map = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;

    @Column(length = 30, nullable = false)
    private String name;

    static {
        map.put(USER, "User");
        map.put(ADMIN, "Admin");
    }

    public Permission() {

    }

    public Permission(Integer pId) {
        this();
        if (map.containsKey(pId)) {
            permissionId = pId;
            name = map.get(pId);
        }
    }

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
