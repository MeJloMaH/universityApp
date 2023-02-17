INSERT INTO groups (id, name)
values (15000, 'fk student');

INSERT INTO users (id, user_type, name, login, password, role, status, group_ref) 
values 	(15000, 'Student', 'change me!', 'change me!', 'change me!', 'STUDENT', 'ACTIVE', 15000);
