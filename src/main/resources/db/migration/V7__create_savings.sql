CREATE TABLE IF NOT EXISTS savings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    current_amount DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    target_amount DECIMAL(19,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    target_date DATETIME NOT NULL,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    concluded_at DATETIME NULL,
    saving_status VARCHAR(20) DEFAULT 'ACTIVE',
    contribution_type VARCHAR(20) DEFAULT 'RECURRENT',

    tenant_id VARCHAR(36),
    user_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36),

    allow_early_withdrawal BOOLEAN DEFAULT TRUE,

    INDEX idx_savings_tenant (tenant_id),
    INDEX idx_savings_user (user_id),
    INDEX idx_savings_account (account_id),
    INDEX idx_savings_status (saving_status),
    INDEX idx_savings_target_date (target_date),
    INDEX idx_savings_created_at (created_at),

    CONSTRAINT fk_savings_tenant FOREIGN KEY (tenant_id)
    REFERENCES tenants(id) ON DELETE SET NULL,
    CONSTRAINT fk_savings_user FOREIGN KEY (user_id)
    REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_savings_account FOREIGN KEY (account_id)
    REFERENCES accounts(id) ON DELETE SET NULL,

    CONSTRAINT chk_savings_current_amount CHECK (current_amount >= 0),
    CONSTRAINT chk_savings_target_amount CHECK (target_amount > 0),
    CONSTRAINT chk_savings_status CHECK (saving_status IN ('ACTIVE', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT chk_savings_contribution_type CHECK (contribution_type IN ('UNIQUE', 'RECURRENT'))
);

CREATE TABLE IF NOT EXISTS saving_transaction_ids (
    saving_id BIGINT NOT NULL,
    transaction_id BIGINT NOT NULL,

    PRIMARY KEY (saving_id, transaction_id),

    CONSTRAINT fk_sti_saving FOREIGN KEY (saving_id)
        REFERENCES savings(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_sti_transaction FOREIGN KEY (transaction_id)
        REFERENCES transactions(id)
            ON DELETE CASCADE,

    INDEX idx_sti_transaction (transaction_id)
) ;