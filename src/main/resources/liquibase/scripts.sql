CREATE TABLE wallet
(
    wallet_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operation_type VARCHAR(255)     NOT NULL,
    balance        DOUBLE PRECISION NOT NULL
);

