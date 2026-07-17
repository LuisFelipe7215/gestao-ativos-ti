package com.felipe.gestaoativosti.response;

import com.felipe.gestaoativosti.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta contendo os detalhes de um equipamento")
public class EquipamentoGetResponse {
    @Schema(description = "Categoria do equipamento", example = "Notebook")
    private String categoria;

    @Schema(description = "Número do patrimônio único do equipamento", example = "PAT-001")
    private String numeroPatrimonio;

    @Schema(description = "Marca e modelo do equipamento", example = "Dell Latitude 5420")
    private String marcaModelo;

    @Schema(description = "Sistema operacional instalado", example = "Windows 11 Pro")
    private String sistemaOperacional;

    @Schema(description = "Capacidade de memória RAM em GB", example = "16")
    private Integer memoriaRam;

    @Schema(description = "Tipo e capacidade de armazenamento", example = "SSD 512GB")
    private String armazenamento;

    @Schema(description = "Status atual do equipamento", example = "DISPONIVEL")
    private Status status;

    @Schema(description = "URL da foto do equipamento", example = "https://exemplo.com/foto.jpg")
    private String urlFoto;
}
