package edu.eci.tdd.service;

import edu.eci.tdd.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>(); // Listado de Usuarios

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;  
    }

    public User getUserById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
