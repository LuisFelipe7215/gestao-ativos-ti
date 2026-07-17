package com.felipe.gestaoativosti.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Resposta de erro para acesso proibido/negado (403)")
public record CustomForbiddenResponse(
    @Schema(description = "Código de status HTTP", example = "403")
    Integer status,
    @Schema(description = "Mensagem descritiva do erro", example = "Access Denied")
    String message,
    @Schema(description = "Data e hora do erro")
    LocalDateTime timestamp,
    @Schema(description = "Caminho da requisição", example = "/api/v1/equipamentos")
    String path
) {}
