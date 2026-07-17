package com.felipe.gestaoativosti.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Resposta de erro de validação de campos (400)")
public record CustomValidationErrorResponse(
    @Schema(description = "Mapa de campos inválidos e suas respectivas mensagens de erro", example = "{\"username\": \"O username não pode ser nulo ou vazio\"}")
    Map<String, String> errors
) {}
