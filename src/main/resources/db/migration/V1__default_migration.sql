CREATE TABLE tenants (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id CHAR(36) NOT NULL,

    CONSTRAINT fk_user_tenant
        FOREIGN KEY (tenant_id)
            REFERENCES tenants(id) ON DELETE CASCADE
);

CREATE TABLE user_role (
    user_id CHAR(36) NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES roles(id) ON DELETE CASCADE
);

CREATE INDEX idx_users_tenant_id ON users(tenant_id);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_user_role_user_id ON user_role(user_id);
CREATE INDEX idx_user_role_role_id ON user_role(role_id);


-- Inserir Tenants
INSERT IGNORE INTO tenants (id, name) VALUES
    ('11111111-1111-1111-1111-111111111111', 'DEFAULT_TENANT');

-- Inserir Roles
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_MASTER'), ('ROLE_USER');

-- Inserir Admin (senha: admin123)
INSERT IGNORE INTO users (id, name, username, email, password, is_active, created_at, tenant_id)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'Admin',
    'admin',
    'admin@system.com',
    '$2a$10$U5l76o.9SQ5o77MawDRn.ub4qaC/ZrCqAxpfZqrL.qhRhyOG3csR6',
    TRUE,
    CURRENT_TIMESTAMP,
    '11111111-1111-1111-1111-111111111111'
);

-- Atribuir Role ADMIN ao Admin
INSERT IGNORE INTO user_role (user_id, role_id) VALUES
    ('22222222-2222-2222-2222-222222222222', 1);