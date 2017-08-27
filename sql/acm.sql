/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

CREATE database acm;

USE acm;

CREATE TABLE IF NOT EXISTS admin(
	id int auto_increment,
	username VARCHAR(20),
	password VARCHAR(200),
	CONSTRAINT PK PRIMARY KEY(id)
);

# Dumping into admin
INSERT INTO admin(username,password) VALUES('test',md5('password'));

CREATE TABLE IF NOT EXISTS queries(
	id int auto_increment,
	query VARCHAR(2000),
	department VARCHAR(200),
	faculty VARCHAR(200),
	CONSTRAINT PK PRIMARY KEY(id)
);

# Dumping sample data into queries.
INSERT INTO queries (query, department, faculty) VALUES
('Do you like mind stressor games?', 'Mathematics', 'Science'),
('Do you love writing?', 'Literature', 'Arts'),
('Do you love handling money?', 'Accounting', 'Management Science'),
('Do you like telling stories?', 'Literature', 'Arts'),
('Do you like chemical reaction analysis?', 'Chemical Engineering', 'Engineering'),
('Are you a lover of Music', 'Music', 'Arts'),
('Do you enjoy learning new languages?', 'Languages', 'Arts'),
('Are you a huge fan of acting?', 'Theatre Arts', 'Arts'),
('Do you dance?', 'Theatre Arts', 'Arts'),
('Do you love puzzles?', 'Computer Science', 'Science'),
('Do you love Computations?', 'Computer Science', 'Science'),
('Do you represent realities in equations easily?', 'Mathematics', 'Science'),
('Do you like encoding things?', 'Computer Science', 'Science'),
('Are you an enthusiastic reporter of events?', 'Mass Communications', 'Arts'),
('Are you an avid reader?', 'Literature', 'Arts'),
('Do you love Numbers', 'Mathematics', 'Science'),
('Do you like studying people?', 'Psychology', 'Social Science'),
('Do people''s behaviour influence you?', 'Psychology', 'Social Science'),
('Do you like being around people?', 'Psychology', 'Social Science'),
('Are you good at Saving Money?', 'Banking and Finance', 'Management Science'),
('Are you always interested in the chemical composition of objects?', 'Chemistry', 'Science'),
('Do you like interviewing people?', 'Mass Communications', 'Arts'),
('Do you like debating?', 'English Language', 'Arts'),
('Do you like politics?', 'Political Science', 'Social Science'),
('Are you funny?', 'Theatre Arts', 'Arts'),
('Do you enjoy playing with number puzzles?', 'Computer Science', 'Science'),
('Do you easily tune to music?', 'Music', 'Arts'),
('Do you have a good speaking skill?', 'Mass Communications', 'Arts');

CREATE TABLE IF NOT EXISTS users(
	id int auto_increment,
	username VARCHAR(20),
	password VARCHAR(200),
	advised_department VARCHAR(200),
	CONSTRAINT PK PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS logged(
	id int auto_increment,
	username VARCHAR(20),
	logged CHAR(1),
	CONSTRAINT PK PRIMARY KEY(id)
);
# Dumping into logged
INSERT INTO logged(username,logged) VALUES('','0');

CREATE TABLE IF NOT EXISTS departments(
	id int auto_increment,
	faculty VARCHAR(200),
	department VARCHAR(200),
	CONSTRAINT PK PRIMARY KEY(id)
);
# Dumping into departments
INSERT INTO departments(faculty,department) VALUES
('Arts','Literature'),
('Arts','English Language'),
('Arts','Philosophy'),
('Arts','Mass Communications'),
('Arts','Theatre Arts'),
('Arts','Music'),
('Arts','Languages'),
('Engineering','Chemical Engineering'),
('Engineering','Computer Engineering'),
('Engineering','Electrical Engineering'),
('Engineering','Mechanical Engineering'),
('Management Science','Accounting'),
('Management Science','Marketing'),
('Management Science','Banking and Finance'),
('Science','Biochemistry'),
('Science','Botany'),
('Science','Chemistry'),
('Science','Computer Science'),
('Science','Mathematics'),
('Science','Microbiology'),
('Science','Physics'),
('Science','Zoology'),
('Social Science','Economics'),
('Social Science','Psychology'),
('Social Science','Sociology'),
('Social Science','Political Science')
;
