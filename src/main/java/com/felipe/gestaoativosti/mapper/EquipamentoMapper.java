package com.felipe.gestaoativosti.mapper;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    @Mapping(target = "sistemaOperacional", source = "sistemaOperacional", defaultValue = "Não informado")
    @Mapping(target = "armazenamento", source = "armazenamento", defaultValue = "Não informado")
    @Mapping(target = "urlFoto", source = "urlFoto", defaultValue = "Sem foto")
    EquipamentoGetResponse toEquipamentoResponse(Equipamento equipamento);
}
