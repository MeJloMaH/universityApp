INSERT INTO groups (id, name)
values (11000, 'fk student');

INSERT INTO users (id, user_type, name, login, password, role, status, group_ref) 
values 	(1, 'Student', 'find me', 'login', 'password', 'STUDENT', 'ACTIVE', 11000),
		(2, 'Student', 'find me', 'login2', 'password', 'STUDENT', 'ACTIVE', 11000);