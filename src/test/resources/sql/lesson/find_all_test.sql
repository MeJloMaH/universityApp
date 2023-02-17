INSERT INTO groups (id, name) 
values(1, 'fk lesson');

INSERT INTO users (id, user_type, name, login, password, role, status) 
values (1, 'Teacher', 'fk lesson', 'login', 'password', 'TEACHER', 'ACTIVE');

INSERT INTO subjects (id, name)
VALUES(1, 'fk lesson');

INSERT INTO rooms (id, name, location)
VALUES(1, 'fk lesson', '1');


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
values 
	(11000, 'find all 1!', 1, '01-01-666', 1, 1, 1, 1),	
	(22000, 'find all 2!', 2, '01-01-777', 1, 1, 1, 1),
	(33000, 'find all 3!', 3, '01-01-888', 1, 1, 1, 1),
	(44000, 'find all 4!', 4, '01-01-999', 1, 1, 1, 1)
;
