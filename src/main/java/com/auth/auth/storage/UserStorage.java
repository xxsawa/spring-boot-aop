package com.auth.auth.storage;

import com.auth.auth.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserStorage{
    public static List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserStorage.class);

    public static List<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
        users.forEach(u ->
                logger.info("User: {}, Password: {}", u.getUsername(), u.getPassword())
        );
    }

    public static User userExists(User user) {
        User foundUser = users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst().orElse(null);
        logger.info("User exists called: {}", foundUser.equals(null) ? null : foundUser.getUsername());
        return foundUser;
    }
}