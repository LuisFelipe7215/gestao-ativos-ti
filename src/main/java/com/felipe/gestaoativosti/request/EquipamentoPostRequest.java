package com.felipe.gestaoativosti.request;

import com.felipe.gestaoativosti.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criação de um novo equipamento")
public class EquipamentoPostRequest {
    @NotBlank(message = "O numero do patrimonio não pode ser nulo ou vazio")
    @Schema(description = "Número do patrimônio único do equipamento", example = "PAT-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroPatrimonio;

    @NotBlank(message = "Categoria não pode ser nulo ou vazia")
    @Schema(description = "Categoria do equipamento", example = "Notebook", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoria;

    @NotBlank(message = "MarcaModelo não pode ser nulo ou vazia")
    @Schema(description = "Marca e modelo do equipamento", example = "Dell Latitude 5420", requiredMode = Schema.RequiredMode.REQUIRED)
    private String marcaModelo;

    @Schema(description = "Sistema operacional instalado", example = "Windows 11 Pro")
    private String sistemaOperacional;

    @Schema(description = "Capacidade de memória RAM em GB", example = "16")
    private Integer memoriaRam;

    @Schema(description = "Tipo e capacidade de armazenamento", example = "SSD 512GB")
    private String armazenamento;

    @NotNull(message = "Status não pode ser nulo")
    @Schema(description = "Status inicial do equipamento", example = "DISPONIVEL", requiredMode = Schema.RequiredMode.REQUIRED)
    private Status status;

    @Schema(description = "URL da foto do equipamento", example = "https://exemplo.com/foto.jpg")
    private String urlFoto;
}
