-- V3__add_price_indexes.sql
CREATE INDEX IF NOT EXISTS idx_price_lookup ON prices (brand_id, product_id, start_date, end_date, priority DESC);
CREATE INDEX IF NOT EXISTS idx_brand_product ON prices (brand_id, product_id);