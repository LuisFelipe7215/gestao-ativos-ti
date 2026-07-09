CREATE TABLE equipamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_patrimonio VARCHAR(50) NOT NULL UNIQUE,
    categoria VARCHAR(50) NOT NULL,
    marca_modelo VARCHAR(100) NOT NULL,
    sistema_operacional VARCHAR(100),
    memoria_ram INT,
    armazenamento VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    url_foto VARCHAR(500)
);
