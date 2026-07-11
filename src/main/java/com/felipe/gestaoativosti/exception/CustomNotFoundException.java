package com.felipe.gestaoativosti.exception;

import java.time.LocalDateTime;

public record CustomNotFoundException(Integer status, String message, LocalDateTime timestamp, String path) {
}
