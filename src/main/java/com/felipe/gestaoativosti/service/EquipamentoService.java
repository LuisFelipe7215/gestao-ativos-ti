package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {
    private final EquipamentoRepository repository;

    @Transactional(readOnly = true)
    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Equipamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado com o ID: " + id));
    }

    @Transactional
    public Equipamento salvar(Equipamento equipamento) {
        return repository.save(equipamento);
    }

    @Transactional
    public Equipamento atualizar(Long id, Equipamento equipamentoAtualizado) {
        buscarPorId(id);
        Equipamento equipamentoParaSalvar = equipamentoAtualizado.toBuilder().id(id).build();
        return repository.save(equipamentoParaSalvar);
    }

    @Transactional
    public void deletar(Long id) {
        Equipamento equipamento = buscarPorId(id);
        repository.delete(equipamento);
    }

    public Page<Equipamento> listAvailableEquipamentos(Pageable pageable){
        return repository.findByStatus(Status.DISPONIVEL, pageable);
    }


}
