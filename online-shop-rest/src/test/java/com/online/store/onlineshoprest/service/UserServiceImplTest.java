package com.online.store.onlineshoprest.service;

import com.online.store.onlineshopcommon.dto.UpdateUserRequest;
import com.online.store.onlineshopcommon.dto.UserDto;
import com.online.store.onlineshopcommon.entity.User;
import com.online.store.onlineshopcommon.mapper.UserMapper;
import com.online.store.onlineshopcommon.repository.UserRepository;
import com.online.store.onlineshopcommon.service.MailService;
import com.online.store.onlineshopcommon.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUser_shouldUpdateUserFields() {
        int userId = 1;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("newUsername");
        request.setEmail("new@email.com");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("old@email.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setUsername("newUsername");
        updatedUser.setEmail("new@email.com");

        UserDto userDto = new UserDto();
        userDto.setUsername("newUsername");
        userDto.setEmail("new@email.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(userDto);

        UserDto result = userService.updateUser(userId, request);

        assertEquals("newUsername", result.getUsername());
        assertEquals("new@email.com", result.getEmail());
    }

    @Test
    void registerUser_shouldEncodePasswordAndAssignRole() {
        User request = new User();
        request.setUsername("john");
        request.setEmail("john@example.com");
        request.setPassword("password");

        User savedUser = new User();
        savedUser.setUsername("john");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword("encodedPassword");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(request);

        assertEquals("john", result.getUsername());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void login_shouldReturnUserIfCredentialsAreValid() {
        User request = new User();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User storedUser = new User();
        storedUser.setEmail("test@example.com");
        storedUser.setPassword("hashedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);

        User result = userService.login(request);

        assertEquals(storedUser, result);
    }

    @Test
    void login_shouldThrowExceptionIfCredentialsInvalid() {
        User request = new User();
        request.setEmail("wrong@example.com");
        request.setPassword("wrongpass");

        when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login(request));
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void save_shouldSendEmailAndSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        verify(mailService).sendMail("test@example.com", "Welcome", "You have successfully registered");
        verify(userRepository).save(user);

        assertEquals(user, result);
    }

    @Test
    void findByEmail_shouldReturnUserIfFound() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }
}
