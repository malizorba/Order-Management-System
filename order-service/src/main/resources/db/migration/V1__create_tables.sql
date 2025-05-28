CREATE TABLE orders (
                                           id uuid NOT NULL,
                                           customer_id varchar(255) NULL,
                                           total_amount float8 NOT NULL,
                                           order_status varchar(50) NULL,
                                           created_at timestamp NULL,
                                           updated_at timestamp NULL
);
CREATE TABLE outbox_event (
                              id UUID PRIMARY KEY,
                              aggregate_id VARCHAR(100),
                              aggregate_type VARCHAR(100),
                              event_type VARCHAR(255),
                              payload TEXT,
                              sent BOOLEAN,
                              created_at TIMESTAMP,
                              updated_at TIMESTAMP
);