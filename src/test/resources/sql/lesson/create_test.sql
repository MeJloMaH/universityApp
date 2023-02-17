INSERT INTO groups (id, name) 
values (1, 'fk lesson');

INSERT INTO rooms (id, name, location) 
values (1, 'fk lesson', '1');

INSERT INTO users (id, user_type, name, login, password, role, status) 
values (1, 'Teacher', 'fk lesson', 'login', 'password', 'TEACHER', 'ACTIVE');

INSERT INTO subjects (id, name) 
values (1, 'fk lesson');