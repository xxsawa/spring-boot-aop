package com.auth.auth.aspects;

import com.auth.auth.models.User;
import com.auth.auth.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

public aspect RegionAspect {
    private static final Logger logger = LoggerFactory.getLogger(RegionAspect.class);


    pointcut cuckoosConstructor():
            call(com.auth.auth.models.Guest.new());

    Object around(): cuckoosConstructor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = UserStorage.userExists(new User(username,"", new HashSet<>() ) );
                return user;
            } else {
                logger.info("User is not logged in!");
            }
        }

        return proceed();
    }
}
