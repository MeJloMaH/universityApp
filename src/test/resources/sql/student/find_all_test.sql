INSERT INTO groups (id, name) 
values 
	(1, 'fk student 1'),
	(2, 'fk student 2'),
	(3, 'fk student 3')
;

INSERT INTO users (id, user_type, name, login, password, role, status, group_ref) 
values 	(1, 'Student', 'find me', 'login', 'password', 'STUDENT', 'ACTIVE', 1),
		(2, 'Student', 'find me', 'login2', 'password', 'STUDENT', 'ACTIVE', 2),
		(3, 'Student', 'find me3', 'login3', 'password', 'STUDENT', 'ACTIVE', 3)
;
