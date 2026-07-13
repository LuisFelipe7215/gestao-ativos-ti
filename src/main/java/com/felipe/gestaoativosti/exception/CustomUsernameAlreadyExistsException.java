package com.felipe.gestaoativosti.exception;

import java.time.LocalDateTime;

public record CustomUsernameAlreadyExistsException(Integer status, String message, LocalDateTime timestamp, String path) {
}
