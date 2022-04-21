-- USERS

INSERT INTO users (id, email, name, password, decline_reason, reward_point, role_type)
VALUES (111, 'test@test.com', 'sayal adhikari', '$2a$10$zJIH0vqVbEiie5qXKLv2/OSSBhfOFcQeKG6jbShEFRKfom84vCuU2', 'no reason', 100, 'ADMIN'); -- 123

INSERT INTO users (id, email, name, password, decline_reason, reward_point, role_type)
VALUES (222, 'smaka@miu.edu', 'sujan', '$2a$10$7maL108rRqx01Q3qY5ee6e6E8G1t/u873fLUSrKfTzibNiFJp86sW', 'no reason', 100, 'ADMIN'); -- 123


-- ROLES
INSERT INTO role (id, role_type)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role_type)
VALUES (2, 'BUYER');
INSERT INTO role (id, role_type)
VALUES (3, 'SELLER');


INSERT INTO users_roles (user_id, roles_id)
VALUES (111, 1);
INSERT INTO users_roles (user_id, roles_id)
VALUES (222, 1);
INSERT INTO users_roles (user_id, roles_id)
VALUES (111, 2);
INSERT INTO users_roles (user_id, roles_id)
VALUES (111, 3);


