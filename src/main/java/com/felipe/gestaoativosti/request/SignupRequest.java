package com.felipe.gestaoativosti.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para cadastro de um novo usuário")
public class SignupRequest {
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome de usuário desejado (único)", example = "luis.felipe", minLength = 3, maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "A senha não pode ser nula ou vazia")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Schema(description = "Senha de acesso", example = "senha123", minLength = 6, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
