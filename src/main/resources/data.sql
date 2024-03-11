INSERT INTO USERS (email, first_name, last_name)
VALUES ('user@yandex.ru', 'User', 'UserLastName'),
       ('admin@gmail.com', 'Admin', 'AdminLastName'),
       ('guest@gmail.com', 'Guest', 'GuestLastName');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);