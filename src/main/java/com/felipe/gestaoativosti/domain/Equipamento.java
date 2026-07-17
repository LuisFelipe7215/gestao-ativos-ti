package com.felipe.gestaoativosti.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipamentos")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Schema(description = "Entidade que representa um equipamento de TI")
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do equipamento", example = "1")
    private Long id;

    @Column(name = "numero_patrimonio", nullable = false, unique = true, length = 50)
    @Schema(description = "Número do patrimônio único do equipamento", example = "PAT-001")
    private String numeroPatrimonio;

    @Column(nullable = false, length = 50)
    @Schema(description = "Categoria do equipamento", example = "Notebook")
    private String categoria;

    @Column(name = "marca_modelo", nullable = false, length = 100)
    @Schema(description = "Marca e modelo do equipamento", example = "Dell Latitude 5420")
    private String marcaModelo;

    @Column(name = "sistema_operacional", length = 100)
    @Schema(description = "Sistema operacional instalado", example = "Windows 11 Pro")
    private String sistemaOperacional;

    @Column(name = "memoria_ram")
    @Schema(description = "Capacidade de memória RAM em GB", example = "16")
    private Integer memoriaRam;

    @Column(length = 100)
    @Schema(description = "Tipo e capacidade de armazenamento", example = "SSD 512GB")
    private String armazenamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Schema(description = "Status atual do equipamento", example = "DISPONIVEL")
    private Status status;

    @Column(name = "url_foto", length = 500)
    @Schema(description = "URL da foto do equipamento", example = "https://exemplo.com/foto.jpg")
    private String urlFoto;

}
