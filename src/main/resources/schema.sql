CREATE TABLE IF NOT EXISTS user_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    image_path VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    is_email_verified BOOLEAN,
    phone_number VARCHAR(50) NOT NULL,
    is_phone_number_verified BOOLEAN,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(50) NOT NULL
);