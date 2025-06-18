package com.online.store.onlineshopcommon.service.serviceImpl;

import com.online.store.onlineshopcommon.dto.UpdateUserRequest;
import com.online.store.onlineshopcommon.dto.UserDto;
import com.online.store.onlineshopcommon.entity.User;
import com.online.store.onlineshopcommon.mapper.UserMapper;
import com.online.store.onlineshopcommon.repository.UserRepository;
import com.online.store.onlineshopcommon.service.MailService;
import com.online.store.onlineshopcommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    // todo: es amen inchy mapperov bdi exni, shat sxal e grac
    @Override
    public void update(UpdateUserRequest request, User user) {
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
    }

    @Override
    public UserDto updateUser(int id, UpdateUserRequest request) {
        // todo: erkrord angam findByID kenes u save
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        update(request, user);

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    public User registerUser(User request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        mailService.sendWelcomeMail(user);
        return userRepository.save(user);
    }

    @Override
    public User login(User request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }


    @Override
    public User save(User user) {
        mailService.sendMail(user.getEmail(), "Welcome", "You have successfully registered");

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
