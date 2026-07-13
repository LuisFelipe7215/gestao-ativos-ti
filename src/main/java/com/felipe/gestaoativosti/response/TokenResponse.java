package com.felipe.gestaoativosti.response;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
}
