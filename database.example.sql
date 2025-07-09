-- Criar a tabela de paciente com ID auto-incremento
CREATE TABLE paciente (
    id SERIAL PRIMARY KEY, -- SERIAL define a coluna como auto-incremento
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL
);

-- Criar a tabela de exame com ID auto-incremento
CREATE TABLE exame (
    id SERIAL PRIMARY KEY, -- SERIAL define a coluna como auto-incremento
    descricao VARCHAR(255) NOT NULL,
    data TIMESTAMP NOT NULL,
    paciente_id INT NOT NULL,
    CONSTRAINT fk_paciente FOREIGN KEY (paciente_id)
    REFERENCES paciente (id) ON DELETE CASCADE
);