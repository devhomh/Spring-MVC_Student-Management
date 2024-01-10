package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public boolean exists(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                       .map(user -> user.getPassword().equals(password))
                       .orElse(false);
    }

    @Override
    public User getUser(String id) {
        if(exists(id)){
            return userMap.get(id);
        }

        throw new UserNotFoundException();
    }

    @Override
    public User addUser(String id, String password) {
        User user = User.create(id, password);
        userMap.put(id, user);

        return user;
    }
}
