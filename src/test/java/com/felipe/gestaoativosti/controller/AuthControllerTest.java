package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.exception.UsernameAlreadyExistsException;
import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.response.TokenResponse;
import com.felipe.gestaoativosti.security.TokenService;
import com.felipe.gestaoativosti.service.AuthService;
import com.felipe.gestaoativosti.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/v1/signup should return 201 and token when signup is successful")
    void testSignupSuccess() throws Exception {
        SignupRequest request = SignupRequest.builder()
                .username("novo.usuario")
                .password("senha123")
                .build();

        TokenResponse tokenResponse = new TokenResponse("mockedToken");

        when(authService.signup(any(SignupRequest.class))).thenReturn(tokenResponse);

        mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mockedToken"));

        verify(authService, times(1)).signup(any(SignupRequest.class));
    }

    @Test
    @DisplayName("POST /api/v1/signup should return 409 when username already exists")
    void testSignupDuplicateUsername() throws Exception {
        SignupRequest request = SignupRequest.builder()
                .username("usuario.existente")
                .password("senha123")
                .build();

        when(authService.signup(any(SignupRequest.class)))
                .thenThrow(new UsernameAlreadyExistsException("O username 'usuario.existente' já está em uso."));

        mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("O username 'usuario.existente' já está em uso."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.path").value("/api/v1/signup"));

        verify(authService, times(1)).signup(any(SignupRequest.class));
    }

    @Test
    @DisplayName("POST /api/v1/signup should return 400 when request body is invalid")
    void testSignupInvalidBody() throws Exception {
        SignupRequest request = SignupRequest.builder()
                .username("")
                .password("123")
                .build();

        mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.username").exists())
                .andExpect(jsonPath("$.errors.password").exists());

        verify(authService, never()).signup(any(SignupRequest.class));
    }
}
