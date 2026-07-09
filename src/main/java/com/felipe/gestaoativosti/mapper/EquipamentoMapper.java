package com.felipe.gestaoativosti.mapper;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    @Mapping(target = "sistemaOperacional", source = "sistemaOperacional", defaultValue = "Não informado")
    @Mapping(target = "armazenamento", source = "armazenamento", defaultValue = "Não informado")
    @Mapping(target = "urlFoto", source = "urlFoto", defaultValue = "Sem foto")
    EquipamentoGetResponse toEquipamentoGetResponse(Equipamento equipamento);

    @Mapping(target = "id", ignore = true)
    Equipamento toEquipamento(EquipamentoPostRequest equipamentoPostRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroPatrimonio", ignore = true)
    Equipamento toEquipamento(EquipamentoPutRequest equipamentoPutRequest);

    EquipamentoPostResponse toEquipamentoPostResponse(Equipamento equipamento);
    EquipamentoPutResponse toEquipamentoPutResponse(Equipamento equipamento);
}
