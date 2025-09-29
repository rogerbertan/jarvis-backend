CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    description VARCHAR(500),
    date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT fk_transactions_category FOREIGN KEY (category_id) REFERENCES category(id)
);