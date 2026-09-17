-- V1__initial_schema.sql

CREATE TABLE customers
(
    id    BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name  VARCHAR(255) NOT NULL
);

CREATE TABLE venues
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    city      VARCHAR(255) NOT NULL,
    capacity  INTEGER      NOT NULL,
    image_url VARCHAR(500)
);

CREATE TABLE events
(
    id                 BIGSERIAL PRIMARY KEY,
    name               VARCHAR(255)   NOT NULL,
    description        TEXT,
    event_image_url    VARCHAR(500),

    event_date         TIMESTAMPTZ    NOT NULL,

    price              NUMERIC(12, 2) NOT NULL,

    total_capacity     INTEGER        NOT NULL,
    available_capacity INTEGER        NOT NULL,

    status             VARCHAR(30)    NOT NULL,

    venue_id           BIGINT         NOT NULL,

    CONSTRAINT fk_events_venue
        FOREIGN KEY (venue_id)
            REFERENCES venues (id)
);

CREATE TABLE reservations
(
    id           BIGSERIAL PRIMARY KEY,

    customer_id  BIGINT         NOT NULL,
    event_id     BIGINT         NOT NULL,

    ticket_count INTEGER        NOT NULL,

    total_price  NUMERIC(12, 2) NOT NULL,

    status       VARCHAR(30)    NOT NULL,

    CONSTRAINT fk_reservations_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (id),

    CONSTRAINT fk_reservations_event
        FOREIGN KEY (event_id)
            REFERENCES events (id)
);