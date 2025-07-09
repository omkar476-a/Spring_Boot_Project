DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(255),
    description TEXT,
    price DOUBLE,
    stock_quantity INT,
    image_url VARCHAR(500),
    available BOOLEAN,
    brand VARCHAR(255),
    expiry_date DATE,
    listed_date DATE -- ✅ Add this line
);
