package com.felipe.gestaoativosti.response;

import com.felipe.gestaoativosti.domain.Status;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoPutResponse {
    private String categoria;
    private String numeroPatrimonio;
    private String marcaModelo;
    private String sistemaOperacional;
    private Integer memoriaRam;
    private String armazenamento;
    private Status status;
    private String urlFoto;
}
