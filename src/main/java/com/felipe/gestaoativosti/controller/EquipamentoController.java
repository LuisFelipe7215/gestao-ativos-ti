package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.mapper.EquipamentoMapper;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {
    private final EquipamentoService service;
    private final EquipamentoMapper mapper;

    @GetMapping
    public ResponseEntity<Page<EquipamentoGetResponse>> listarTodos(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarTodos(pageable);
        Page<EquipamentoGetResponse> responsePage = equipamentos.map(mapper::toEquipamentoResponse);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<Page<EquipamentoGetResponse>> listarDisponiveis(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarDisponiveis(pageable);
        Page<EquipamentoGetResponse> equipamentoResponsePage = equipamentos.map(mapper::toEquipamentoResponse);
        return ResponseEntity.ok(equipamentoResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoGetResponse> buscarPorId(@PathVariable Long id){
        Equipamento equipamento = service.buscarPorId(id);
        EquipamentoGetResponse equipamentoGetResponse = mapper.toEquipamentoResponse(equipamento);
        return ResponseEntity.ok(equipamentoGetResponse);
    }

    @PostMapping
    public ResponseEntity<EquipamentoGetResponse> salvar(@RequestBody @Valid Equipamento equipamento) {
        Equipamento salvo = service.salvar(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toEquipamentoResponse(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoGetResponse> atualizar(@PathVariable Long id, @RequestBody @Valid Equipamento equipamento) {
        Equipamento atualizado = service.atualizar(id, equipamento);
        return ResponseEntity.ok(mapper.toEquipamentoResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
