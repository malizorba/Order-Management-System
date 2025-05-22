CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        customer_id VARCHAR(255) NOT NULL,
                        total_amount DOUBLE PRECISION NOT NULL,
                        order_status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP
);

CREATE TABLE outbox_event (
                              id UUID PRIMARY KEY,
                              aggregate_id  VARCHAR(255) NOT NULL,
                              aggregate_type VARCHAR(100),
                              event_type VARCHAR(100),
                              payload TEXT,
                              sent BOOLEAN DEFAULT FALSE,
                              created_at TIMESTAMP,
                              updated_at TIMESTAMP
);
