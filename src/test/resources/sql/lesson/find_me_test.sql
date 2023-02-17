
INSERT INTO groups (id, name) 
values (1, 'fk lesson');

INSERT INTO rooms (id, name, location) 
values (1, 'fk lesson', '1');

INSERT INTO users (id, user_type, name, login, password, role, status) 
values (1, 'Teacher', 'fk lesson', 'login', 'password', 'TEACHER', 'ACTIVE');

INSERT INTO subjects (id, name) 
values (1, 'fk lesson');

INSERT INTO lessons 
	(
		id, 
		name, 
		number_per_day,
		date,
		group_ref, 
		teacher_ref, 
		subject_ref, 
		room_ref
	)
values (11000, 'find me!', 1, '01-01-2000', 1, 1, 1, 1);