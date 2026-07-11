package com.felipe.gestaoativosti.exception;

import java.time.LocalDateTime;

public record CustomPatrimonioAlreadyExistsException(Integer status, String message, LocalDateTime timestamp, String path) {
}
