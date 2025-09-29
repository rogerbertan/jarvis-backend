CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    initial_balance DECIMAL(19,2) NOT NULL,
    current_balance DECIMAL(19,2) NOT NULL
);