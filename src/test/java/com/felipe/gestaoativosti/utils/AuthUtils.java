package com.felipe.gestaoativosti.utils;

import com.felipe.gestaoativosti.domain.User;
import com.felipe.gestaoativosti.repository.UserRepository;
import com.felipe.gestaoativosti.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    @Autowired
    public TokenService tokenService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public String getTokenAutenticado(){
        User user = userRepository.findByUsername("teste").orElseGet(() -> {
            User newUser = User.builder()
                    .username("teste")
                    .password(passwordEncoder.encode("teste12345"))
                    .roles("ROLE_USER")
                    .build();
            return userRepository.save(newUser);
        });

        String tokenReal = tokenService.gerarToken(user.getUsername());

        return "Bearer " + tokenReal;
    }
}
