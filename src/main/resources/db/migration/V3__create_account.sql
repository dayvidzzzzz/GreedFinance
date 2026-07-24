CREATE TABLE accounts (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(255),
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    type VARCHAR(50) NOT NULL,
    account_number VARCHAR(50),
    agency_number VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(36) NOT NULL,

    CONSTRAINT fk_account_tenant
        FOREIGN KEY (tenant_id)
            REFERENCES tenants(id) ON DELETE CASCADE
);

CREATE INDEX idx_accounts_tenant_id ON accounts(tenant_id);
CREATE INDEX idx_accounts_type ON accounts(type);
CREATE INDEX idx_accounts_active ON accounts(is_active);
CREATE INDEX idx_accounts_default ON accounts(is_default);
CREATE INDEX idx_accounts_balance ON accounts(balance);

CREATE TABLE account_holders (
    account_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (account_id, user_id),

    CONSTRAINT fk_holder_account
        FOREIGN KEY (account_id)
            REFERENCES accounts(id) ON DELETE CASCADE,

    CONSTRAINT fk_holder_user
        FOREIGN KEY (user_id)
            REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_account_holders_account ON account_holders(account_id);
CREATE INDEX idx_account_holders_user ON account_holders(user_id);
