package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.mapper.EquipamentoMapper;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import com.felipe.gestaoativosti.service.EquipamentoService;
import com.felipe.gestaoativosti.exception.CustomNotFoundException;
import com.felipe.gestaoativosti.exception.CustomPatrimonioAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Equipamentos", description = "Endpoints para cadastro, busca e atualização de hardwares e ativos de TI")
public class EquipamentoController {
    private final EquipamentoService service;
    private final EquipamentoMapper mapper;

    @GetMapping
    @Operation(
            summary = "Listar todos os equipamentos de uma forma paginada",
            description = "Retorna uma lista paginada de ativos de TI." +
                    " Você pode controlar a página atual, a quantidade de itens por página e" +
                    " a ordenação através dos parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado")
    })
    public ResponseEntity<Page<EquipamentoGetResponse>> listarTodos(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarTodos(pageable);
        Page<EquipamentoGetResponse> responsePage = equipamentos.map(mapper::toEquipamentoGetResponse);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/disponiveis")
    @Operation(
            summary = "Listar todos os equipamentos que possuem o status disponível de forma paginada",
            description = "Retorna uma lista paginada de ativos de TI que possuem o status disponível." +
                    " Você pode controlar a página atual, a quantidade de itens por página e" +
                    " a ordenação através dos parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado")
    })
    public ResponseEntity<Page<EquipamentoGetResponse>> listarDisponiveis(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<Equipamento> equipamentos = service.listarDisponiveis(pageable);
        Page<EquipamentoGetResponse> equipamentoResponsePage = equipamentos.map(mapper::toEquipamentoGetResponse);
        return ResponseEntity.ok(equipamentoResponsePage);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um equipamento pelo ID",
            description = "Recupera todos os detalhes do equipamento pelo seu identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class)))
    })
    public ResponseEntity<EquipamentoGetResponse> buscarPorId(@PathVariable Long id){
        Equipamento equipamento = service.buscarPorId(id);
        EquipamentoGetResponse equipamentoGetResponse = mapper.toEquipamentoGetResponse(equipamento);
        return ResponseEntity.ok(equipamentoGetResponse);
    }

    @PostMapping
    @Operation(
            summary = "Cadastra um novo equipamento",
            description = "Salva um novo ativo de TI no banco de dados. O número de patrimônio deve ser único no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipamento cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Número de patrimônio já está sendo utilizado por outro equipamento.",
                    content = @Content(schema = @Schema(implementation = CustomPatrimonioAlreadyExistsException.class))),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco"),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado")
    })
    public ResponseEntity<EquipamentoPostResponse> salvar(@RequestBody @Valid EquipamentoPostRequest request) {
        Equipamento equipamentoToSave = mapper.toEquipamento(request);
        Equipamento salvo = service.salvar(equipamentoToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toEquipamentoPostResponse(salvo));
    }

    @Operation(
            summary = "Atualiza os dados de um equipamento",
            description = "Atualiza os dados de um ativo de TI específico. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamento atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class))),
            @ApiResponse(responseCode = "400", description = "Número de patrimônio já está sendo utilizado por outro equipamento.",
                    content = @Content(schema = @Schema(implementation = CustomPatrimonioAlreadyExistsException.class))),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco"),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoPutResponse> atualizar(@PathVariable Long id, @RequestBody @Valid EquipamentoPutRequest request) {
        Equipamento equipamentoToUpdate = mapper.toEquipamento(request);
        Equipamento atualizado = service.atualizar(id, equipamentoToUpdate);
        return ResponseEntity.ok(mapper.toEquipamentoPutResponse(atualizado));
    }

    @Operation(
            summary = "Remove um equipamento pelo ID",
            description = "Remove um equipamento do banco de dados pelo o id fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class))),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
