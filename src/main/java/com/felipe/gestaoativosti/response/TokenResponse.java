package com.felipe.gestaoativosti.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo o token JWT gerado após o cadastro")
public class TokenResponse {
    @Schema(description = "Token de autenticação JWT Bearer", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsdWlzLmZlbGlwZSIsImlzcyI6Imdlc3Rhby1hdGl2by10aSIsImV4cCI6MTc4MTkyNjA5M30...")
    private String token;
}
