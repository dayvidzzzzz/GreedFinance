CREATE TABLE credit_transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(19, 2),
    transaction_status VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tenant_id VARCHAR(36) NOT NULL,
    category_id BIGINT NOT NULL,
    card_id VARCHAR(36) NOT NULL,

    CONSTRAINT fk_credit_transaction_tenant FOREIGN KEY (tenant_id)
        REFERENCES tenants(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_credit_transaction_category FOREIGN KEY (category_id)
        REFERENCES categories(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_credit_transaction_card FOREIGN KEY (card_id)
        REFERENCES cards(id)
            ON DELETE CASCADE
);