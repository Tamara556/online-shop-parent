package com.online.store.onlineshoprest.endpoint;

import com.online.store.onlineshopcommon.dto.*;
import com.online.store.onlineshopcommon.entity.User;
import com.online.store.onlineshopcommon.mapper.UserMapper;
import com.online.store.onlineshopcommon.repository.UserRepository;
import com.online.store.onlineshopcommon.service.UserService;
import com.online.store.onlineshoprest.util.JwtUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserEndpoint {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil tokenUtil;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestHeader(required = false, name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy
    ) {
        if (!Set.of("username", "email").contains(sortBy)) {
            sortBy = "username";
        }

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        System.out.println(request.getUsername());
        var user = userMapper.toEntity(request);
        System.out.println(user.getUsername());
        userRepository.save(user);

        var userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") int id,
            @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Email already in use"),
            @ApiResponse(responseCode = "200", description = "successful register")
    })
    public ResponseEntity<?> register(
            @RequestBody SaveUserRequest saveUserRequest
    ) {
        if (userService.findByEmail(saveUserRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        saveUserRequest.setPassword(passwordEncoder.encode(saveUserRequest.getPassword()));
        userService.save(userMapper.toEntity(saveUserRequest));
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
        Optional<User> byEmail = userService.findByEmail(request.getEmail());

        if (byEmail.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        User user = byEmail.get();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .ok(UserResponse.builder()
                            .token(tokenUtil.generateToken(user.getEmail()))
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .id(user.getId())
                            .build()
                    );
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }
}