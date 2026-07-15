package com.felipe.gestaoativosti.utils;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoUtils {
    @Autowired
    public EquipamentoRepository equipamentoRepository;


    public Equipamento saveEquipamento(){
        Equipamento equipamento = Equipamento.builder()
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();

      return equipamentoRepository.save(equipamento);
    }

    public void deleteEquipamento(){
        equipamentoRepository.deleteAll();
    }

    public Equipamento getEquipamento(){
        return Equipamento.builder()
                .id(1L)
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();
    }

    public Equipamento equipamentoToUpdate(){
        return Equipamento.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();
    }

    public EquipamentoPostRequest getPostRequestDto(){
        return EquipamentoPostRequest.builder()
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();
    }

    public EquipamentoPutRequest getPutRequestDto(){
        return EquipamentoPutRequest.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();
    }

    public EquipamentoGetResponse getResponseDto(){
        return EquipamentoGetResponse.builder()
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();
    }

    public EquipamentoPostResponse getPostResponseDto(){
        return EquipamentoPostResponse.builder()
                .id(1L)
                .build();
    }

    public EquipamentoPutResponse getPutResponseDto(){
        return EquipamentoPutResponse.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();
    }


}
