CREATE TABLE card (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    card_limit DECIMAL(19, 2) DEFAULT 0.00,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    account_id VARCHAR(36),
    tenant_id VARCHAR(36) NOT NULL,

    CONSTRAINT fk_card_account FOREIGN KEY (account_id)
    REFERENCES accounts(id) ON DELETE SET NULL,
    CONSTRAINT fk_card_tenant FOREIGN KEY (tenant_id)
    REFERENCES tenants(id) ON DELETE CASCADE,

    INDEX idx_card_account_id (account_id),
    INDEX idx_card_tenant_id (tenant_id),
    INDEX idx_card_active (active),
    INDEX idx_card_create_at (create_at)
);