package com.auth.auth.models;

import java.util.HashSet;
import java.util.Set;

public class User extends Guest implements IInteractingClient{
    private Long id;

    private String username;

    private String password;

    private Set<Role> roles;

    public User(String username, String password, Set<Role> roles) {
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Set<Role> getRoles() {return roles;}

    @Override
    public String getRegion() {
        return "User region: CA";
    }
}
