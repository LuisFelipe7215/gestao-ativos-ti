package com.felipe.gestaoativosti.controller;

import tools.jackson.databind.ObjectMapper;
import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import com.felipe.gestaoativosti.mapper.EquipamentoMapper;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import com.felipe.gestaoativosti.service.EquipamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EquipamentoController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class EquipamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EquipamentoService service;

    @MockitoBean
    private EquipamentoMapper mapper;

    private Equipamento equipamento;
    private EquipamentoGetResponse responseDto;

    @BeforeEach
    void setUp() {
        equipamento = Equipamento.builder()
                .id(1L)
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();

        responseDto = EquipamentoGetResponse.builder()
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos should return paginated list of all equipments")
    void testListarTodos() throws Exception {
        Page<Equipamento> page = new PageImpl<>(List.of(equipamento));
        when(service.listarTodos(any(Pageable.class))).thenReturn(page);
        when(mapper.toEquipamentoGetResponse(any(Equipamento.class))).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].numeroPatrimonio").value("PAT-001"));

        verify(service, times(1)).listarTodos(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/disponiveis should return paginated list of available equipments")
    void testListarDisponiveis() throws Exception {
        Page<Equipamento> page = new PageImpl<>(List.of(equipamento));
        when(service.listarDisponiveis(any(Pageable.class))).thenReturn(page);
        when(mapper.toEquipamentoGetResponse(any(Equipamento.class))).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/equipamentos/disponiveis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].numeroPatrimonio").value("PAT-001"));
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/{id} should return equipment by id")
    void testBuscarPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(equipamento);
        when(mapper.toEquipamentoGetResponse(equipamento)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/equipamentos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroPatrimonio").value("PAT-001"));
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos should save and return post response")
    void testSalvar() throws Exception {
        EquipamentoPostRequest postRequest = EquipamentoPostRequest.builder()
                .numeroPatrimonio("PAT-001")
                .categoria("Notebook")
                .marcaModelo("Dell Latitude")
                .status(Status.DISPONIVEL)
                .build();

        EquipamentoPostResponse postResponse = EquipamentoPostResponse.builder()
                .id(1L)
                .build();

        when(mapper.toEquipamento(any(EquipamentoPostRequest.class))).thenReturn(equipamento);
        when(service.salvar(any(Equipamento.class))).thenReturn(equipamento);
        when(mapper.toEquipamentoPostResponse(equipamento)).thenReturn(postResponse);

        mockMvc.perform(post("/api/v1/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/equipamentos/{id} should update and return put response")
    void testAtualizar() throws Exception {
        EquipamentoPutRequest putRequest = EquipamentoPutRequest.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();

        EquipamentoPutResponse putResponse = EquipamentoPutResponse.builder()
                .categoria("Desktop")
                .marcaModelo("HP ProDesk")
                .status(Status.MANUTENCAO)
                .build();

        when(mapper.toEquipamento(any(EquipamentoPutRequest.class))).thenReturn(equipamento);
        when(service.atualizar(eq(1L), any(Equipamento.class))).thenReturn(equipamento);
        when(mapper.toEquipamentoPutResponse(equipamento)).thenReturn(putResponse);

        mockMvc.perform(put("/api/v1/equipamentos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria").value("Desktop"));
    }

    @Test
    @DisplayName("DELETE /api/v1/equipamentos/{id} should delete equipment and return noContent")
    void testDeletar() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/api/v1/equipamentos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deletar(1L);
    }
}
