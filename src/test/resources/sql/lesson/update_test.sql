INSERT INTO groups (id, name) 
values 
	(1, 'fk lesson before'),
	(22, 'fk lesson after');


INSERT INTO rooms (id, name, location) 
values 
	(1, 'fk lesson before', '1'),
	(22, 'fk lesson after', '2');


	
INSERT INTO users (id, user_type, name, login, password, role, status) 
values 	(1, 'Teacher', 'fk lesson before', 'login before', 'password before', 'TEACHER', 'ACTIVE'),
		(22, 'Teacher', 'fk lesson after', 'login after', 
			'password after', 'TEACHER', 'ACTIVE');	



INSERT INTO subjects (id, name) 
values 
	(1, 'fk lesson before'),
	(22, 'fk lesson after');


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
values (1000, 'change me!', 1, '01-01-2000', 1, 1, 1, 1);
