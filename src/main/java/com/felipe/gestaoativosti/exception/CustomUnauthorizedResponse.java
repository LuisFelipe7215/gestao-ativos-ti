package com.felipe.gestaoativosti.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Resposta de erro para requisições não autorizadas (401)")
public record CustomUnauthorizedResponse(
    @Schema(description = "Código de status HTTP", example = "401")
    Integer status,
    @Schema(description = "Mensagem descritiva do erro", example = "Credenciais inválidas")
    String message,
    @Schema(description = "Data e hora do erro")
    LocalDateTime timestamp,
    @Schema(description = "Caminho da requisição", example = "/api/v1/login")
    String path
) {}
