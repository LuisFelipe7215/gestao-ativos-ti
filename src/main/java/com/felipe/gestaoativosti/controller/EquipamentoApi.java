package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.exception.CustomNotFoundException;
import com.felipe.gestaoativosti.exception.CustomPatrimonioAlreadyExistsException;
import com.felipe.gestaoativosti.exception.CustomForbiddenResponse;
import com.felipe.gestaoativosti.exception.CustomValidationErrorResponse;
import com.felipe.gestaoativosti.request.EquipamentoPostRequest;
import com.felipe.gestaoativosti.request.EquipamentoPutRequest;
import com.felipe.gestaoativosti.response.EquipamentoGetResponse;
import com.felipe.gestaoativosti.response.EquipamentoPostResponse;
import com.felipe.gestaoativosti.response.EquipamentoPutResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Equipamentos", description = "Endpoints para cadastro, busca e atualização de hardwares e ativos de TI")
public interface EquipamentoApi {

    @Operation(
            summary = "Listar todos os equipamentos de uma forma paginada",
            description = "Retorna uma lista paginada de ativos de TI. Você pode controlar a página atual, a quantidade de itens por página e a ordenação através dos parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class)))
    })
    ResponseEntity<Page<EquipamentoGetResponse>> listarTodos(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Listar todos os equipamentos que possuem o status disponível de forma paginada",
            description = "Retorna uma lista paginada de ativos de TI que possuem o status disponível. Você pode controlar a página atual, a quantidade de itens por página e a ordenação através dos parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso."),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class)))
    })
    ResponseEntity<Page<EquipamentoGetResponse>> listarDisponiveis(@ParameterObject Pageable pageable);

    @Operation(
            summary = "Busca um equipamento pelo ID",
            description = "Recupera todos os detalhes do equipamento pelo seu identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipamento retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class))),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class)))
    })
    ResponseEntity<EquipamentoGetResponse> buscarPorId(Long id);

    @Operation(
            summary = "Cadastra um novo equipamento",
            description = "Salva um novo ativo de TI no banco de dados. O número de patrimônio deve ser único no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipamento cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Número de patrimônio já está sendo utilizado por outro equipamento.",
                    content = @Content(schema = @Schema(implementation = CustomPatrimonioAlreadyExistsException.class))),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco",
                    content = @Content(schema = @Schema(implementation = CustomValidationErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class)))
    })
    ResponseEntity<EquipamentoPostResponse> salvar(EquipamentoPostRequest request);

    @Operation(
            summary = "Atualiza os dados de um equipamento",
            description = "Atualiza os dados de um ativo de TI específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamento atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class))),
            @ApiResponse(responseCode = "400", description = "Número de patrimônio já está sendo utilizado por outro equipamento.",
                    content = @Content(schema = @Schema(implementation = CustomPatrimonioAlreadyExistsException.class))),
            @ApiResponse(responseCode = "401", description = "Campos obrigatórios nulos ou em branco",
                    content = @Content(schema = @Schema(implementation = CustomValidationErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class)))
    })
    ResponseEntity<EquipamentoPutResponse> atualizar(Long id, EquipamentoPutRequest request);

    @Operation(
            summary = "Remove um equipamento pelo ID",
            description = "Remove um equipamento do banco de dados pelo o id fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado com o id fornecido",
                    content = @Content(schema = @Schema(implementation = CustomNotFoundException.class))),
            @ApiResponse(responseCode = "403", description = "Token JWT ausente, inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = CustomForbiddenResponse.class)))
    })
    ResponseEntity<Void> deletar(Long id);
}
