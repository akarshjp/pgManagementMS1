package com.pg.MS1.user.service;

import com.pg.MS1.user.dto.CreateUserRequest;
import com.pg.MS1.user.entity.User;
import com.pg.MS1.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser (CreateUserRequest newUser) {

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(newUser.getRole());

        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setRoomNumber(newUser.getRoomNumber());
        user.setBedNumber(newUser.getBedNumber());
        user.setMoveInDate(newUser.getMoveInDate());
        user.setEmergencyContactName(newUser.getEmergencyContactName());
        user.setEmergencyContactPhone(newUser.getEmergencyContactPhone());

        return userRepository.save(user);

    }
}
