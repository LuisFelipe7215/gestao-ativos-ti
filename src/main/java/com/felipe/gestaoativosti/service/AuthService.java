package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.domain.User;
import com.felipe.gestaoativosti.exception.UsernameAlreadyExistsException;
import com.felipe.gestaoativosti.repository.UserRepository;
import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.response.TokenResponse;
import com.felipe.gestaoativosti.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("O username '" + request.getUsername() + "' já está em uso.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        String token = tokenService.gerarToken(user.getUsername());

        return new TokenResponse(token);
    }
}
