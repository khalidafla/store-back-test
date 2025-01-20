INSERT INTO users (id, username, firstname, email, password) VALUES
(1, 'khalidafla', 'khalid', 'khalidafla@example.com', '$2a$10$6L04rOsJQihA5Vv39WNiyeQ6pQa0v9bmtY6oqPTFV/DnN0OPfK82y'), -- password123
(2, 'admin', 'admin', 'admin@admin.com', '$2a$10$WsV9V24T69HbAV2TFZhdTerIbhuo/8keo6CvPzyS/APdCC9Ir8Smm'); --password456

INSERT INTO product (product_id, code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_date, updated_date)
VALUES
(1001, 'PRD001', 'Wireless Headphones', 'Bluetooth-enabled wireless headphones with noise cancellation.', 'headphones.jpg', 'Electronics', 89.99, 50, 'REF001', 101, 'INSTOCK', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, 'PRD002', 'Smartphone', 'Latest 5G-enabled smartphone with high-resolution display.', 'smartphone.jpg', 'Electronics', 699.99, 30, 'REF002', 102, 'INSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1003, 'PRD003', 'Gaming Mouse', 'Ergonomic gaming mouse with adjustable DPI.', 'mouse.jpg', 'Accessories', 39.99, 100, 'REF003', 103, 'INSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1004, 'PRD004', 'Fitness Tracker', 'Waterproof fitness tracker with heart rate monitoring.', 'tracker.jpg', 'Wearables', 49.99, 20, 'REF004', 104, 'LOWSTOCK', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1005, 'PRD005', 'Laptop', 'High-performance laptop with 16GB RAM and 512GB SSD.', 'laptop.jpg', 'Computers', 1199.99, 15, 'REF005', 105, 'LOWSTOCK', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO wish_lists (id, user_id) VALUES
(1001, 1),
(1002, 2);

INSERT INTO wish_list_products (wish_list_id, product_id) VALUES
(1001, 1001), (1001, 1002),
(1002, 1003), (1002, 1004);

INSERT INTO carts (id, user_id) VALUES
(1001, 1),
(1002, 2);

INSERT INTO cart_products (cart_id, product_id, quantity) VALUES
(1001, 1001, 5), (1001, 1003, 3),
(1002, 1002, 11), (1002, 1004, 8);