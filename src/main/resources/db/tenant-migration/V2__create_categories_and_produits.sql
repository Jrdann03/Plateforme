-- 1. Table des catégories (avec support de la hiérarchie infinie via parent_id)
CREATE TABLE categorie (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    description TEXT,
    parent_id BIGINT, -- Référence vers la catégorie parente (NULL si c'est une catégorie racine)

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_categorie_parent
        FOREIGN KEY (parent_id)
        REFERENCES categorie(id)
        ON DELETE CASCADE
);

-- 2. Table des produits
CREATE TABLE produit (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(200) NOT NULL,
    description TEXT,
    prix DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,

    categorie_id BIGINT, -- Un produit peut appartenir à une catégorie

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_produit_categorie
        FOREIGN KEY (categorie_id)
        REFERENCES categorie(id)
        ON DELETE SET NULL
);