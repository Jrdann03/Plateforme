CREATE TABLE commercant (
    id BIGSERIAL PRIMARY KEY,
    uid UUID NOT NULL UNIQUE,
    matricule VARCHAR(50) NOT NULL UNIQUE,

    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(30),
    adresse VARCHAR(255),

    sous_domaine VARCHAR(100) NOT NULL UNIQUE,
    database_name VARCHAR(100) NOT NULL UNIQUE,

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE site (
    id BIGSERIAL PRIMARY KEY,

    commercant_id BIGINT NOT NULL UNIQUE,

    nom VARCHAR(150) NOT NULL,
    titre VARCHAR(255),
    description TEXT,

    logo_url VARCHAR(500),
    favicon_url VARCHAR(500),

    theme VARCHAR(50) NOT NULL DEFAULT 'DEFAULT',
    couleur_principale VARCHAR(20),
    couleur_secondaire VARCHAR(20),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_site_commercant
        FOREIGN KEY (commercant_id)
        REFERENCES commercant(id)
        ON DELETE CASCADE
);