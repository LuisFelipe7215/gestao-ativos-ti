package com.felipe.gestaoativosti.domain;

import jakarta.persistence.*;

@Entity
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_patrimonio", nullable = false, unique = true, length = 50)
    private String numeroPatrimonio;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(name = "marca_modelo", nullable = false, length = 100)
    private String marcaModelo;

    @Column(name = "sistema_operacional", length = 100)
    private String sistemaOperacional;

    @Column(name = "memoria_ram")
    private Integer memoriaRam;

    @Column(length = 100)
    private String armazenamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;

    @Column(name = "url_foto", length = 500)
    private String urlFoto;

}
