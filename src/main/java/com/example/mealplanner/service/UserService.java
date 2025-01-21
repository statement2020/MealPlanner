package com.example.mealplanner.service;

import com.example.mealplanner.entity.User;
import com.example.mealplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);  // Or return a default value or throw exception
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
