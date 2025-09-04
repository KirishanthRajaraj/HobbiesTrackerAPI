package com.kiri.hobby_tracker.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kiri.hobby_tracker.Model.User;
import com.kiri.hobby_tracker.Repository.IUserRepository;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String email, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, email, hashedPassword, Set.of("ROLE_USER"));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
