# Gestão de Ativos TI API

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-green?style=for-the-badge&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-29.6.1-blue?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white)

API RESTful robusta desenvolvida para inventário, gestão e controle de ativos de hardware de TI. Possui autenticação via token JWT, segurança com Spring Security, migração de banco de dados com Flyway e documentação automatizada com OpenAPI/Swagger.

---

## 🛠️ Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 4.1.0** (Web, Security, Data JPA, Validation)
- **MySQL** (Banco de dados de produção/desenvolvimento)
- **H2 Database** (Banco de dados em memória para a suíte de testes)
- **Flyway** (Migração e controle de versão do banco de dados)
- **Springdoc OpenAPI (Swagger)** (Documentação interativa da API com arquitetura de interfaces segregadas)
- **Java JWT (Auth0)** (Autenticação Stateless e geração de tokens seguros)
- **Lombok** (Redução de boilerplate code)
- **MapStruct** (Mapeamento performático entre Entidades e DTOs)

---

## 📋 Pré-requisitos

Para rodar e compilar este projeto, você precisará de:
- **Java JDK 25** instalado e configurado nas variáveis de ambiente.
- **Maven** ou utilizar o Maven Wrapper (`./mvnw` ou `mvnw.cmd`) fornecido na raiz do projeto.
- **Docker e Docker Compose** (opcional, para rodar o banco de dados MySQL via container).

---

## ⚙️ Configuração do Ambiente

A API consome variáveis de ambiente para conexão com o banco e criptografia. Siga os passos:

1. Duplique o arquivo `.envTemplate` na raiz do projeto e renomeie-o para `.env`.
2. Configure as seguintes variáveis de acordo com suas configurações locais ou container:
   - `MYSQL_HOST`: Endereço do MySQL (padrão: `localhost` ou o IP do container Docker).
   - `MYSQL_PORT`: Porta do MySQL (padrão: `3306`).
   - `MYSQL_DATABASE`: Nome do esquema no banco (ex: `gestao_ativos`).
   - `MYSQL_PASSWORD`: Senha do banco.
   - `JWT_SECRET`: Chave secreta de criptografia HMAC-SHA256 usada pelo JWT.

### Gerando um JWT Secret seguro
Você pode gerar uma chave segura de 256 bits rodando no seu terminal:
- **Linux/macOS (via OpenSSL):**
  ```bash
  openssl rand -base64 32
  ```
- **Windows (via PowerShell):**
  ```powershell
  $bytes = New-Object Byte[] 32; 
  (New-Object Security.Cryptography.RNGCryptoServiceProvider).GetBytes($bytes);
  [Convert]::ToBase64String($bytes)
  ```
Copie a chave gerada e defina-a na variável `JWT_SECRET` no seu arquivo `.env`.

---

## 🚀 Como Executar a Aplicação

### 1. Subir o Banco de Dados (Docker Compose)
Se optar por usar o banco de dados pré-configurado no Docker, execute:
```bash
docker compose up -d
```

### 2. Rodar a API Spring Boot
Utilize o Maven Wrapper para rodar o projeto localmente:
```bash
./mvnw spring-boot:run
```
*(ou `mvnw.cmd spring-boot:run` no Windows Command Prompt)*

A aplicação estará disponível em `http://localhost:8080`.

---

## 🧪 Como Executar os Testes

A suíte de testes de integração e testes unitários é executada usando o perfil `@ActiveProfiles("test")`, que configura automaticamente uma instância isolada do **H2 Database em memória**. Nenhuma configuração manual é necessária.

Para rodar todos os testes:
```bash
./mvnw clean test
```

---

## 📖 Documentação da API (Swagger + JWT)

A documentação detalhada e interativa está exposta através do Swagger UI.

1. **Acesse o Swagger UI:** http://localhost:8080/swagger-ui/index.html
2. **Cadastro e Obtenção do Token JWT:**
   - Procure o endpoint `POST /api/v1/signup` na seção **Auth**.
   - Use o botão **Try it out** para registrar um novo usuário com um body contendo `username` e `password`.
   - Copie o token retornado na propriedade `token` do JSON de resposta.
3. **Autenticação no Swagger:**
   - Role até o topo e clique no botão **Authorize** (com o ícone de cadeado).
   - Cole o token copiado no campo **Value** (o Swagger gerenciará o prefixo `Bearer` automaticamente).
   - Clique em **Authorize** e feche o modal.
4. **Utilização:**
   - Agora, todos os endpoints protegidos sob `/api/v1/equipamentos` estarão liberados para uso.

---

## 🗂️ Estrutura de Endpoints Principais

### Autenticação / Usuários
- `POST /api/v1/signup`: Cria um novo usuário e retorna o respectivo token JWT Bearer.

### Gestão de Equipamentos (Ativos)
*Estes endpoints requerem autenticação (header `Authorization: Bearer <token>`), exceto onde especificado de outra forma.*
- `GET /api/v1/equipamentos`: Lista paginada de todos os equipamentos cadastrados.
- `GET /api/v1/equipamentos/disponiveis`: Lista paginada de todos os equipamentos com status `DISPONIVEL` (**Público/Permitido sem autenticação**).
- `GET /api/v1/equipamentos/{id}`: Detalha um equipamento específico pelo ID.
- `POST /api/v1/equipamentos`: Cadastra um novo equipamento (patrimônio deve ser único).
- `PUT /api/v1/equipamentos/{id}`: Atualiza dados de um equipamento existente.
- `DELETE /api/v1/equipamentos/{id}`: Remove um equipamento.