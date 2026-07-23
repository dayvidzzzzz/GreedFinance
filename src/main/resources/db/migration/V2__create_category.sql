CREATE TABLE  categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    tenant_id VARCHAR(36) NOT NULL,
    CONSTRAINT uk_category_tenant_name UNIQUE (tenant_id, name)
);

ALTER TABLE categories
    ADD CONSTRAINT fk_category_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE;

CREATE INDEX idx_categories_tenant_id ON categories(tenant_id);
CREATE INDEX idx_categories_name ON categories(name);

INSERT IGNORE INTO categories (name, tenant_id) VALUES
    ('Eletrônicos', '11111111-1111-1111-1111-111111111111'),
    ('Roupas', '11111111-1111-1111-1111-111111111111'),
    ('Alimentos', '11111111-1111-1111-1111-111111111111'),
    ('Livros', '11111111-1111-1111-1111-111111111111'),
    ('Móveis', '11111111-1111-1111-1111-111111111111'),
    ('Esportes', '11111111-1111-1111-1111-111111111111'),
    ('Saúde', '11111111-1111-1111-1111-111111111111'),
    ('Automóveis', '11111111-1111-1111-1111-111111111111');