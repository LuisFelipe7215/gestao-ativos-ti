package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.mapper.EquipamentoMapper;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import com.felipe.gestaoativosti.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController implements EquipamentoApi {
    private final EquipamentoService service;
    private final EquipamentoMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<Page<EquipamentoGetResponse>> listarTodos(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarTodos(pageable);
        Page<EquipamentoGetResponse> responsePage = equipamentos.map(mapper::toEquipamentoGetResponse);
        return ResponseEntity.ok(responsePage);
    }

    @Override
    @GetMapping("/disponiveis")
    public ResponseEntity<Page<EquipamentoGetResponse>> listarDisponiveis(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarDisponiveis(pageable);
        Page<EquipamentoGetResponse> equipamentoResponsePage = equipamentos.map(mapper::toEquipamentoGetResponse);
        return ResponseEntity.ok(equipamentoResponsePage);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoGetResponse> buscarPorId(@PathVariable Long id){
        Equipamento equipamento = service.buscarPorId(id);
        EquipamentoGetResponse equipamentoGetResponse = mapper.toEquipamentoGetResponse(equipamento);
        return ResponseEntity.ok(equipamentoGetResponse);
    }

    @Override
    @PostMapping
    public ResponseEntity<EquipamentoPostResponse> salvar(@RequestBody @Valid EquipamentoPostRequest request) {
        Equipamento equipamentoToSave = mapper.toEquipamento(request);
        Equipamento salvo = service.salvar(equipamentoToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toEquipamentoPostResponse(salvo));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoPutResponse> atualizar(@PathVariable Long id, @RequestBody @Valid EquipamentoPutRequest request) {
        Equipamento equipamentoToUpdate = mapper.toEquipamento(request);
        Equipamento atualizado = service.atualizar(id, equipamentoToUpdate);
        return ResponseEntity.ok(mapper.toEquipamentoPutResponse(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
