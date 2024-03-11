INSERT INTO USERS (email, first_name, last_name)
VALUES ('user@yandex.ru', 'User', 'UserLastName'),
       ('admin@gmail.com', 'Admin', 'AdminLastName'),
       ('guest@gmail.com', 'Guest', 'GuestLastName');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO REF (code, type, ref_order, endpoint)
VALUES ('C_TG', 'C', 10, null),  --telegram
       ('C_WA', 'C', 20, null),  --whatsapp
       ('C_PH', 'C', 30, null),  --phone
       ('C_SK', 'C', 40, now()); --skype
