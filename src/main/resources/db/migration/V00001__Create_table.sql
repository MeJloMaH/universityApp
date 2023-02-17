drop table if exists lessons;
drop table if exists rooms;
drop table if exists users;
drop table if exists subjects;
drop table if exists groups;

create table groups
(
    id   		SERIAL PRIMARY KEY,
    name 		text UNIQUE
);

create table users
(
    id			SERIAL PRIMARY KEY,
    user_type	varchar(35) not null,
    name 		text not null,
    login 		text UNIQUE not null,
    password 	text not null,
    role		varchar(25) not null,
    status		varchar(25) not null,
    group_ref	INT REFERENCES groups (id) on delete cascade
);

create table rooms
(
    id       	SERIAL PRIMARY KEY,
    name     	text not null,
    location 	text UNIQUE
);

create table subjects
(
    id   		SERIAL PRIMARY KEY,
    name 		text not null
);

create table lessons
(
    id				SERIAL PRIMARY KEY,
    name			text not null,
    number_per_day	int not null,
    date 			DATE not null,
    group_ref		int REFERENCES groups (id) 			on delete cascade not null,
    teacher_ref		int REFERENCES users (id)			on delete cascade not null,
    subject_ref		int REFERENCES subjects (id)		on delete cascade not null,
    room_ref		int REFERENCES rooms (id)			on delete cascade not null,
    
    UNIQUE (number_per_day, date, group_ref)
);



