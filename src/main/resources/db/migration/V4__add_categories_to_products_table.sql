ALTER TABLE products ADD COLUMN categories_id BIGINT NOT NULL,
ADD CONSTRAINT fk_products_categories FOREIGN KEY (categories_id) REFERENCES categories(id) ON DELETE CASCADE;