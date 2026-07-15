package com.felipe.gestaoativosti.controller;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import com.felipe.gestaoativosti.utils.AuthUtils;
import com.felipe.gestaoativosti.utils.BaseIntegrationTest;
import com.felipe.gestaoativosti.utils.EquipamentoUtils;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

class EquipamentoControllerITTest extends BaseIntegrationTest {

    public static final String URL = "/api/v1/equipamentos";

    public String tokenAutenticado;

    @Autowired
    public AuthUtils authUtils;

    @Autowired
    public EquipamentoUtils equipamentoUtils;

    public Equipamento equipamento;

    @Autowired
    public EquipamentoRepository equipamentoRepository;

    @BeforeEach
    void setUp() {
        this.tokenAutenticado = authUtils.getTokenAutenticado();
        equipamento = equipamentoUtils.saveEquipamento();
    }

    @AfterEach
    void tearDown(){
       equipamentoUtils.deleteEquipamento();
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos should return paginated list of all equipments")
    void testListarTodos() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .when()
                .get(URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", Matchers.notNullValue())
                .body("page.size", Matchers.equalTo(10));
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/disponiveis should return paginated list of available equipments")
    void testListarDisponiveis() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .when()
                .get(URL + "/disponiveis")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", Matchers.notNullValue())
                .body("page.size", Matchers.equalTo(10));
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/{id} should return equipment by id")
    void testBuscarPorId() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .pathParam("id", equipamento.getId())
                .when()
                .get(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("numeroPatrimonio", Matchers.equalTo("PAT-001"))
                .body("categoria", Matchers.equalTo("Notebook"))
                .body("marcaModelo", Matchers.equalTo("Dell Latitude"))
                .body("status", Matchers.equalTo("DISPONIVEL"));
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos should save and return an equipamento")
    void testSalvar() {
        String requestBody = """
                {
                "numeroPatrimonio":"PAT-002",
                "categoria":"Desktop",
                "marcaModelo":"Lenovo 62",
                "status":"DISPONIVEL"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("PUT /api/v1/equipamentos/{id} should update and return put response")
    void testAtualizar() {
        String requestBody = """
                {
                "categoria":"Desktop",
                "marcaModelo":"HP ProDesk",
                "status":"MANUTENCAO"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .body(requestBody)
                .pathParam("id", equipamento.getId())
                .when()
                .put(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("numeroPatrimonio", Matchers.equalTo("PAT-001"))
                .body("categoria", Matchers.equalTo("Desktop"))
                .body("marcaModelo", Matchers.equalTo("HP ProDesk"))
                .body("status", Matchers.equalTo("MANUTENCAO"));
    }

    @Test
    @DisplayName("DELETE /api/v1/equipamentos/{id} should delete equipment and return noContent")
    void testDeletar() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .pathParam("id", equipamento.getId())
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/{id} should return 404 when equipment not found")
    void testBuscarPorIdNotFound() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .pathParam("id", 99)
                .when()
                .get(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", Matchers.equalTo(404))
                .body("message", Matchers.equalTo("Equipamento não encontrado com o ID: 99"))
                .body("timestamp", Matchers.notNullValue())
                .body("path", Matchers.equalTo("/api/v1/equipamentos/99"));;
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos should return 400 when post request body is invalid")
    void testSalvarInvalidBody() {
        String requestBody = """
                {
                "numeroPatrimonio":"",
                "categoria":"",
                "marcaModelo":"Dell Latitude",
                "status":null
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errors.numeroPatrimonio", Matchers.equalTo("O numero do patrimonio não pode ser nulo ou vazio"))
                .body("errors.categoria", Matchers.equalTo("Categoria não pode ser nulo ou vazia"))
                .body("errors.status", Matchers.equalTo("Status não pode ser nulo"));
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos should return 400 when saving duplicate patrimonio")
    void testSalvarDuplicatePatrimonio() {
        String requestBody = """
                {
                "numeroPatrimonio":"PAT-001",
                "categoria":"Desktop",
                "marcaModelo":"Lenovo 62",
                "status":"MANUTENCAO"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", Matchers.equalTo(400))
                .body("message", Matchers.equalTo("Número de patrimônio já está sendo utilizado."))
                .body("timestamp", Matchers.notNullValue())
                .body("path", Matchers.equalTo("/api/v1/equipamentos"));
    }

    @Test
    @DisplayName("PUT /api/v1/equipamentos/{id} should return 404 when updating non-existent equipment")
    void testAtualizarNotFound() {
        String requestBody = """
                {
                "categoria":"Desktop",
                "marcaModelo":"HP ProDesk",
                "status":"MANUTENCAO"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .body(requestBody)
                .pathParam("id", 99)
                .when()
                .put(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", Matchers.equalTo(404))
                .body("message", Matchers.equalTo("Equipamento não encontrado com o ID: 99"))
                .body("timestamp", Matchers.notNullValue())
                .body("path", Matchers.equalTo("/api/v1/equipamentos/99"));
    }

    @Test
    @DisplayName("DELETE /api/v1/equipamentos/{id} should return 404 when deleting non-existent equipment")
    void testDeletarNotFound() {
        given().contentType(ContentType.JSON)
                .header("Authorization", tokenAutenticado)
                .pathParam("id", 99)
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", Matchers.equalTo(404))
                .body("message", Matchers.equalTo("Equipamento não encontrado com o ID: 99"))
                .body("timestamp", Matchers.notNullValue())
                .body("path", Matchers.equalTo("/api/v1/equipamentos/99"));
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos without token should return 403 Forbidden")
    void testListarTodosWithoutToken() {
        given().contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos with invalid token should return 403 Forbidden")
    void testListarTodosWithInvalidToken() {
        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidToken123")
                .when()
                .get(URL)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/{id} without token should return 403 Forbidden")
    void testBuscarPorIdWithoutToken() {
        given().contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("GET /api/v1/equipamentos/{id} with invalid token should return 403 Forbidden")
    void testBuscarPorIdWithInvalidToken() {
        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidToken123")
                .pathParam("id", 1)
                .when()
                .get(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos without token should return 403 Forbidden")
    void testSalvarWithoutToken() {
        String requestBody = """
                {
                "numeroPatrimonio":"PAT-999",
                "categoria":"Notebook",
                "marcaModelo":"Dell Latitude",
                "status":"DISPONIVEL"
                }
                """;

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("POST /api/v1/equipamentos with invalid token should return 403 Forbidden")
    void testSalvarWithInvalidToken() {
        String requestBody = """
                {
                "numeroPatrimonio":"PAT-999",
                "categoria":"Notebook",
                "marcaModelo":"Dell Latitude",
                "status":"DISPONIVEL"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidToken123")
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("PUT /api/v1/equipamentos/{id} without token should return 403 Forbidden")
    void testAtualizarWithoutToken() {
        String requestBody = """
                {
                "categoria":"Desktop",
                "marcaModelo":"HP ProDesk",
                "status":"MANUTENCAO"
                }
                """;

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .pathParam("id", 1)
                .when()
                .put(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("PUT /api/v1/equipamentos/{id} with invalid token should return 403 Forbidden")
    void testAtualizarWithInvalidToken() {
        String requestBody = """
                {
                "categoria":"Desktop",
                "marcaModelo":"HP ProDesk",
                "status":"MANUTENCAO"
                }
                """;

        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidToken123")
                .body(requestBody)
                .pathParam("id", 1)
                .when()
                .put(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("DELETE /api/v1/equipamentos/{id} without token should return 403 Forbidden")
    void testDeletarWithoutToken() {
        given().contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("DELETE /api/v1/equipamentos/{id} with invalid token should return 403 Forbidden")
    void testDeletarWithInvalidToken() {
        given().contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalidToken123")
                .pathParam("id", 1)
                .when()
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
