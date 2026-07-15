package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.domain.User;
import com.felipe.gestaoativosti.exception.UsernameAlreadyExistsException;
import com.felipe.gestaoativosti.repository.UserRepository;
import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.response.TokenResponse;
import com.felipe.gestaoativosti.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Should successfully signup a new user and return a JWT token")
    void testSignupSuccess() {
        SignupRequest request = SignupRequest.builder()
                .username("novo.usuario")
                .password("senha123")
                .build();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(tokenService.gerarToken(request.getUsername())).thenReturn("mockedToken");

        TokenResponse response = authService.signup(request);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());

        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).gerarToken(request.getUsername());
    }

    @Test
    @DisplayName("Should throw UsernameAlreadyExistsException when signing up a duplicate username")
    void testSignupDuplicateUsername() {
        SignupRequest request = SignupRequest.builder()
                .username("usuario.existente")
                .password("senha123")
                .build();

        User existingUser = User.builder()
                .username("usuario.existente")
                .password("encodedPassword")
                .roles("ROLE_USER")
                .build();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(existingUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> authService.signup(request));

        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(userRepository, never()).save(any(User.class));
        verify(tokenService, never()).gerarToken(anyString());
    }
}
