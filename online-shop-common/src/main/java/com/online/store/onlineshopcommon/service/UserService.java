package com.online.store.onlineshopcommon.service;


import com.online.store.onlineshopcommon.dto.UpdateUserRequest;
import com.online.store.onlineshopcommon.dto.UserDto;
import com.online.store.onlineshopcommon.entity.User;

import java.util.Optional;

public interface UserService {
    void update(UpdateUserRequest request, User user);

    UserDto updateUser(int id, UpdateUserRequest request);

    User registerUser(User user);

    User login(User user);

    User save(User user);

    Optional<User> findByEmail(String email);
}
