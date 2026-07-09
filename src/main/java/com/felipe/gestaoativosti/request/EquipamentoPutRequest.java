package com.felipe.gestaoativosti.request;

import com.felipe.gestaoativosti.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoPutRequest {
    @NotBlank(message = "Categoria não pode ser nulo ou vazia")
    private String categoria;

    @NotBlank(message = "MarcaModelo não pode ser nulo ou vazia")
    private String marcaModelo;

    private String sistemaOperacional;

    private Integer memoriaRam;

    private String armazenamento;

    @NotNull(message = "Status não pode ser nulo")
    private Status status;

    private String urlFoto;
}
