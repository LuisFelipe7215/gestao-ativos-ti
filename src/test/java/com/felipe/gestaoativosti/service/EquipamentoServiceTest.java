package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository repository;

    @InjectMocks
    private EquipamentoService service;

    private Equipamento equipamento;

    @BeforeEach
    void setUp() {
        equipamento = Equipamento.builder()
                .id(1L)
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();
    }

    @Test
    @DisplayName("Should list all available equipments")
    void testListarDisponiveis() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Equipamento> page = new PageImpl<>(List.of(equipamento));
        when(repository.findByStatus(Status.DISPONIVEL, pageable)).thenReturn(page);

        Page<Equipamento> result = service.listarDisponiveis(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(equipamento, result.getContent().get(0));
        verify(repository, times(1)).findByStatus(Status.DISPONIVEL, pageable);
    }

    @Test
    @DisplayName("Should list all equipments")
    void testListarTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Equipamento> page = new PageImpl<>(List.of(equipamento));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Equipamento> result = service.listarTodos(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should find equipment by id successfully")
    void testBuscarPorIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(equipamento));

        Equipamento result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(equipamento, result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when equipment not found by id")
    void testBuscarPorIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should save equipment successfully when patrimonio does not exist")
    void testSalvarSuccess() {
        when(repository.existsByNumeroPatrimonio("PAT-001")).thenReturn(false);
        when(repository.save(any(Equipamento.class))).thenReturn(equipamento);

        Equipamento result = service.salvar(equipamento);

        assertNotNull(result);
        assertEquals(equipamento, result);
        verify(repository, times(1)).existsByNumeroPatrimonio("PAT-001");
        verify(repository, times(1)).save(equipamento);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when saving equipment with duplicate patrimonio")
    void testSalvarDuplicatePatrimonio() {
        when(repository.existsByNumeroPatrimonio("PAT-001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(equipamento));
        verify(repository, times(1)).existsByNumeroPatrimonio("PAT-001");
        verify(repository, never()).save(any(Equipamento.class));
    }

    @Test
    @DisplayName("Should update equipment successfully")
    void testAtualizarSuccess() {
        Equipamento updatedData = Equipamento.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(repository.existsByNumeroPatrimonioAndIdNot(any(), eq(1L))).thenReturn(false);
        when(repository.save(any(Equipamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Equipamento result = service.atualizar(1L, updatedData);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PAT-001", result.getNumeroPatrimonio());
        assertEquals("Desktop", result.getCategoria());
        assertEquals("HP ProDesk", result.getMarcaModelo());
        assertEquals(Status.MANUTENCAO, result.getStatus());
        verify(repository, times(1)).save(any(Equipamento.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating equipment with duplicate patrimonio")
    void testAtualizarDuplicatePatrimonio() {
        Equipamento updatedData = Equipamento.builder()
                .numeroPatrimonio("PAT-002")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(repository.existsByNumeroPatrimonioAndIdNot("PAT-002", 1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(1L, updatedData));
        verify(repository, never()).save(any(Equipamento.class));
    }

    @Test
    @DisplayName("Should delete equipment successfully")
    void testDeletarSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(equipamento));
        doNothing().when(repository).delete(equipamento);

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository, times(1)).delete(equipamento);
    }
}
