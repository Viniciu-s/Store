CREATE TABLE tb_product (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    size VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    brand_id CHAR(36) NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES tb_brand(id),
);