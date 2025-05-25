CREATE TABLE inventory_items (
                                 id UUID PRIMARY KEY,
                                 product_id UUID NOT NULL UNIQUE,
                                 stock_quantity INTEGER NOT NULL,
                                 created_at TIMESTAMP,
                                 updated_at TIMESTAMP
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
