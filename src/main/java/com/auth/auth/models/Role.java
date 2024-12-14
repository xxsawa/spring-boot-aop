package com.auth.auth.models;

import com.auth.auth.models.enums.Right;

import java.util.Set;

public class Role {
    private Long id;
    private String name;

    private Set<Right> rights;

    public Role(String name, Set<Right> rights) {
        this.name = name;
        this.rights = rights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public void addRight(Right right) {
        if (right == null) {
            throw new IllegalArgumentException("Right cannot be null");
        }
        if (rights.add(right)) {
            System.out.println("Right " + right + " added successfully.");
        } else {
            System.out.println("Right " + right + " already exists.");
        }
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }
}

