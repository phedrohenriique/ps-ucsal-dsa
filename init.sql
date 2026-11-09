-- init.sql
-- Creates the schema and seeds the five plants and 25 devices.
-- Run against an empty database, or use the reset section below if needed.

BEGIN;

-- 1. Create tables in foreign-key dependency order.

CREATE TABLE IF NOT EXISTS clients (
    id_client INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name_client VARCHAR(100),
    contact_client VARCHAR(100),
    document_client VARCHAR(14) UNIQUE NOT NULL,
    document_type_client VARCHAR(4) NOT NULL 
        CHECK (document_type_client IN (
            'CPF',
            'CNPJ'
        )),
        CHECK (
        (document_type_client = 'CPF'
            AND document_client ~ '^[0-9]{11}$')
        OR
        (document_type_client = 'CNPJ'
            AND document_client ~ '^[0-9]{14}$')
    ),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tickets (
    id_ticket INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_client INT NOT NULL,
    title_ticket VARCHAR(20) NOT NULL,
    description_ticket VARCHAR(400) NOT NULL,
    priority_ticket VARCHAR(10) NOT NULL 
        CHECK (priority_ticket IN (
            'HIGH',
            'MEDIUM',
            'LOW'
        )),
    status_ticket VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'
        CHECK (status_ticket IN (
            'ACTIVE',
            'FINISHED'
        )),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_client) REFERENCES clients(id_client) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
    id_message INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_ticket INT NOT NULL,
    content_message VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_ticket) REFERENCES tickets(id_ticket) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_document_client ON clients (document_client);


COMMIT;

-- The data table is created but no sensor readings are inserted because
-- no measurement values were provided.
