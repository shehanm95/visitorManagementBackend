INSERT INTO user_entity (
    id, first_name, last_name, email, is_email_verified,
    phone_number, is_phone_number_verified, password, user_role
)
SELECT
    1, 'Zincat', 'Admin', 'admin@zincat.com', true,
    '1234567890', true, '$2a$10$sF4h16I9s4YSm/w9RzQjeeLvdfbJNYxENkFZ5IlqMH.i44DkRwLSG', 'ROLE_MODERATOR'
WHERE NOT EXISTS (
    SELECT 1 FROM user_entity WHERE id = 1 OR email = 'admin@zincat.com'
);
-- Admin@123