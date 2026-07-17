package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.response.TokenResponse;
import com.felipe.gestaoativosti.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints criação de usuário e token JWT")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário e retorna o token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Username já está em uso por outro usuário"),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco")
    })
    public ResponseEntity<TokenResponse> signup(@RequestBody @Valid SignupRequest request) {
        TokenResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
