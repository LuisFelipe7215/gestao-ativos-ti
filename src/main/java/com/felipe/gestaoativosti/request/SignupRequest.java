package com.felipe.gestaoativosti.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    private String username;

    @NotBlank(message = "A senha não pode ser nula ou vazia")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
}
