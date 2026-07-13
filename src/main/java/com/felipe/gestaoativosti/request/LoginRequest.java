package com.felipe.gestaoativosti.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    private String username;

    @NotBlank(message = "A senha não pode ser nula ou vazia")
    private String password;
}
