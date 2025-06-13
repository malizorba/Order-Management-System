ALTER TABLE inventory_items
    ADD COLUMN reserved_quantity INTEGER NOT NULL DEFAULT 0;