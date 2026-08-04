ALTER TABLE credit_transaction ADD COLUMN transaction_id BIGINT;

ALTER TABLE credit_transaction
    ADD CONSTRAINT fk_credit_transactions_transaction
        FOREIGN KEY (transaction_id)
            REFERENCES transactions(id);