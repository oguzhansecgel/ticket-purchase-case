CREATE TABLE payment_details
(
    id           BIGSERIAL PRIMARY KEY,

    payment_id   VARCHAR(100)   NOT NULL,
    payment_conversation_id     VARCHAR(255)         NOT NULL,
    payment_status       VARCHAR(30)    NOT NULL
);