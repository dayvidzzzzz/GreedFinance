CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    transaction_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id VARCHAR(36) NOT NULL,
    tenant_id VARCHAR(36) NOT NULL,
    category_id BIGINT NOT NULL,

    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
            REFERENCES accounts(id),

    CONSTRAINT fk_transaction_tenant
        FOREIGN KEY (tenant_id)
            REFERENCES tenants(id),

    CONSTRAINT fk_transaction_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id)
);

CREATE INDEX idx_transaction_account_id ON transactions(account_id);
CREATE INDEX idx_transaction_tenant_id ON transactions(tenant_id);
CREATE INDEX idx_transaction_category_id ON transactions(category_id);
CREATE INDEX idx_transaction_created_at ON transactions(created_at);
CREATE INDEX idx_transaction_type ON transactions(transaction_type);
CREATE INDEX idx_transaction_status ON transactions(transaction_status);