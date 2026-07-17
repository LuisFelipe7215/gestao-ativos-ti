package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.exception.CustomUsernameAlreadyExistsException;
import com.felipe.gestaoativosti.exception.CustomValidationErrorResponse;
import com.felipe.gestaoativosti.request.SignupRequest;
import com.felipe.gestaoativosti.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Endpoints criação de usuário e token JWT")
public interface AuthApi {

    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário e retorna o token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Username já está em uso por outro usuário",
                    content = @Content(schema = @Schema(implementation = CustomUsernameAlreadyExistsException.class))),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco",
                    content = @Content(schema = @Schema(implementation = CustomValidationErrorResponse.class)))
    })
    ResponseEntity<TokenResponse> signup(SignupRequest request);
}
