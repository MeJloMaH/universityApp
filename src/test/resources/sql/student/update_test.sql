INSERT INTO groups (id, name)
values 	(10000, 'fk student before'),
		(777, 'fk student after')
;

INSERT INTO users (id, user_type, name, login, password, role, status, group_ref) 
values 	(1, 'Student', 'change me!', 'change me!', 'change me!', 'STUDENT', 'ACTIVE', 10000);
