package com.auth.auth.storage;

import com.auth.auth.models.Role;
import com.auth.auth.models.User;
import com.auth.auth.models.enums.Right;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class InMemoryRBACStorage {

    private static final Map<String, Role> roles = new HashMap<>();

    @PostConstruct
    public void seedData() {
        Role trainDriverRole = new Role("TRAINDRIVER", new HashSet<>());
        trainDriverRole.getRights().add(Right.DriverAssistanceSystemsService);
        trainDriverRole.getRights().add(Right.ShiftSchedulingService);
        trainDriverRole.getRights().add(Right.SafetyAlertService);
        roles.put("trainDriver", trainDriverRole);

        Role trainTicketPersonRole = new Role("TRAINTICKETPERSON", new HashSet<>());
        trainTicketPersonRole.getRights().add(Right.MobileTicketScannersService);
        trainTicketPersonRole.getRights().add(Right.ShiftSchedulingService);
        trainTicketPersonRole.getRights().add(Right.SafetyAlertService);
        roles.put("trainTicketPerson", trainTicketPersonRole);

        Role trainTravelerRole = new Role("TRAINTRAVELER", new HashSet<>());
        trainTravelerRole.getRights().add(Right.BaggageService);
        trainTravelerRole.getRights().add(Right.SafetyAlertService);
        roles.put("trainTraveler", trainTravelerRole);

        User user1 = new User("Rene", "Rusnovodic", new HashSet<>());
        user1.getRoles().add(trainDriverRole);
        UserStorage.addUser(user1);

        User user2 = new User("Vlado", "Vlakveduci", new HashSet<>());
        user2.getRoles().add(trainTicketPersonRole);
        UserStorage.addUser(user2);

        User user3 = new User("Celestina", "Cestujuca", new HashSet<>());
        user3.getRoles().add(trainTravelerRole);
        UserStorage.addUser(user3);
    }

    public static Role getRole(String roleName) {
        return roles.get(roleName);
    }

    public boolean hasPermission(User user, String requiredRight) {
        for (Role role : user.getRoles()) {
            for (Right right : role.getRights()) {
                if (right.toString().equals(requiredRight)) {
                    return true;
                }
            }
        }
        return false;
    }
}