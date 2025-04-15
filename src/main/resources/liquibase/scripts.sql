CREATE TABLE wallet
(
    id            BIGSERIAL PRIMARY KEY,
    wallet_number UUID UNIQUE NOT NULL,
    balance       NUMERIC
);