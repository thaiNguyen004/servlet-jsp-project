INSERT INTO PRODUCT_SIZES (size_name, weight, height) VALUES ('Smallest', 100, 50), ('Small', 120, 60), ('Medium', 140, 70), ('Larger', 160, 80), ('Largest', 180, 100);

INSERT INTO CATEGORIES (name, created_at, updated_at) VALUES ('Pant', '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('T-Shirt', '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000');

INSERT INTO PRODUCTS (name, size_id, price, category_id, created_at, updated_at) VALUES ('Product 1', 1, 120000, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('Product 2', 2, 300000, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('Product 3', 3, 900000, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('Product 4', 4, 90000, 2, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000');

INSERT INTO PICTURES (name, link, product_id) VALUES ('Picture 1', 'Link 1', 1), ('Picture 2', 'Link 2', 1), ('Picture 3', 'Link 3', 2), ('Picture 4', 'Link 4', 2), ('Picture 5', 'Link 5', 3), ('Picture 6', 'Link 6', 3), ('Picture 7', 'Link 7', 4), ('Picture 8', 'Link 8', 4);

INSERT INTO USERS (firstName, lastName, username, role, email, password, active_at) VALUES ('John', 'Doe', 'admin', 'ADMIN', 'john@example.com', '1', '2024-03-17 22:18:06.844000'), ('Jane', 'Doe', 'user', 'CUSTOMER', 'jane@example.com', '1', '2024-03-17 22:18:06.844000');

INSERT INTO ADDRESS (province, district, ward, street, user_id) VALUES ('Hanoi', 'Cau Giay', 'Dich Vong', 'Nguyen Phong Sac', 1), ('Hanoi', 'Hoai Duc', 'Lai Xa', 'Kim Chung', 2);

INSERT INTO ORDERS (total_price, user_id, address_id, created_at, updated_at) VALUES (150, 1, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), (200, 2, 2, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000');

INSERT INTO LINE_ITEMS (product_id, quantity, price, order_id, created_at, updated_at) VALUES (1, 2, 100, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), (2, 3, 300, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), (1, 1, 50, 2, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000');

INSERT INTO PAYMENTS (payment_method, amount, order_id, created_at, updated_at) VALUES ('CAST', 50, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('ONLINE', 100, 1, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('CAST', 50, 2, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000'), ('ONLINE', 100, 2, '2024-03-17 22:18:06.844000', '2024-03-17 22:18:06.844000');
