ALTER TABLE payment_details
    ADD COLUMN IF NOT EXISTS reservation_id BIGINT;
