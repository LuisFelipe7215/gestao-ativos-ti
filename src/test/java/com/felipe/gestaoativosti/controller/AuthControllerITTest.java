package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.domain.User;
import com.felipe.gestaoativosti.repository.UserRepository;
import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.security.TokenService;
import com.felipe.gestaoativosti.utils.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.restassured.RestAssured.given;

class AuthControllerITTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/v1/signup should register a user and return a token")
    void testSignupSuccess() {
        SignupRequest request = SignupRequest.builder()
                .username("newuser")
                .password("password123")
                .build();

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/signup")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("token", Matchers.notNullValue());
    }

    @Test
    @DisplayName("POST /api/v1/signup should return 409 when username already exists")
    void testSignupDuplicateUsername() {
        User user = User.builder()
                .username("existinguser")
                .password(passwordEncoder.encode("password123"))
                .roles("ROLE_USER")
                .build();
        userRepository.save(user);

        SignupRequest request = SignupRequest.builder()
                .username("existinguser")
                .password("anotherpassword")
                .build();

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/signup")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("status", Matchers.equalTo(409))
                .body("message", Matchers.equalTo("O username 'existinguser' já está em uso."))
                .body("path", Matchers.equalTo("/api/v1/signup"));
    }

    @Test
    @DisplayName("POST /api/v1/signup should return 400 when payload is invalid")
    void testSignupInvalidBody() {
        SignupRequest request = SignupRequest.builder()
                .username(null)
                .password("123")
                .build();

        given().contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/signup")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.username", Matchers.equalTo("O username não pode ser nulo ou vazio"))
                .body("errors.password", Matchers.equalTo("A senha deve ter no mínimo 6 caracteres"));
    }

    @Test
    @DisplayName("POST /api/v1/login should return 200 when accessing with a valid token")
    void testLoginSuccess() {
        User user = User.builder()
                .username("loginuser")
                .password(passwordEncoder.encode("password123"))
                .roles("ROLE_USER")
                .build();
        userRepository.save(user);

        String token = tokenService.gerarToken(user.getUsername());

        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/v1/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("message", Matchers.equalTo("Login realizado com sucesso."));
    }

    @Test
    @DisplayName("POST /api/v1/login should return 403 when accessing without token")
    void testLoginWithoutToken() {
        given().contentType(ContentType.JSON)
                .when()
                .post("/api/v1/login")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("POST /api/v1/login should return 403 when accessing with an invalid token")
    void testLoginWithInvalidToken() {
        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidtoken123")
                .when()
                .post("/api/v1/login")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
