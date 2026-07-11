package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import com.felipe.gestaoativosti.exception.NotFoundException;
import com.felipe.gestaoativosti.exception.PatrimonioAlreadyExistsException;
import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EquipamentoService {
    private final EquipamentoRepository repository;

    @Transactional(readOnly = true)
    public Page<Equipamento> listarDisponiveis(Pageable pageable){
        return repository.findByStatus(Status.DISPONIVEL, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Equipamento> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Equipamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipamento não encontrado com o ID: " + id));
    }

    public Equipamento salvar(Equipamento equipamento) {
        assertNumeroPatrimonioDoesNotExists(equipamento.getNumeroPatrimonio());
        return repository.save(equipamento);
    }

    @Transactional
    public Equipamento atualizar(Long id, Equipamento equipamentoAtualizado) {
        Equipamento equipamentoExistente = buscarPorId(id);
        assertNumeroPatrimonioDoesNotExistsParaAtualizacao(equipamentoAtualizado.getNumeroPatrimonio(), id);
        equipamentoAtualizado.setId(id);
        if (equipamentoAtualizado.getNumeroPatrimonio() == null) {
            equipamentoAtualizado.setNumeroPatrimonio(equipamentoExistente.getNumeroPatrimonio());
        }
        return repository.save(equipamentoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Equipamento equipamento = buscarPorId(id);
        repository.delete(equipamento);
    }

    private void assertNumeroPatrimonioDoesNotExists(String numeroPatrimonio){
        if (repository.existsByNumeroPatrimonio(numeroPatrimonio)){
            throw new PatrimonioAlreadyExistsException("Número de patrimônio já está sendo utilizado.");
        }
    }

    private void assertNumeroPatrimonioDoesNotExistsParaAtualizacao(String numeroPatrimonio, Long id){
        if (repository.existsByNumeroPatrimonioAndIdNot(numeroPatrimonio, id)){
            throw new PatrimonioAlreadyExistsException("Número de patrimônio já está sendo utilizado por outro equipamento.");
        }
    }
}
