package com.felipe.gestaoativosti.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo o ID do equipamento recém-criado")
public class EquipamentoPostResponse {
    @Schema(description = "ID do equipamento criado", example = "1")
    private Long id;
}
