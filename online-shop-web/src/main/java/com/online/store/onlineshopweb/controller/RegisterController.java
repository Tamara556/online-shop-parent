package com.online.store.onlineshopweb.controller;

import com.online.store.onlineshopcommon.dto.RegisterUserRequest;
import com.online.store.onlineshopcommon.entity.User;
import com.online.store.onlineshopcommon.mapper.UserMapper;
import com.online.store.onlineshopcommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute RegisterUserRequest request){
        User user = userMapper.toEntity(request);
        userService.registerUser(user);
        return "redirect:/login";
    }

}
