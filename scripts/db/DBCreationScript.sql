-- SCRIPT CREACIÓN DB

-- ACLARACIONES
-- * Las convenciones de los nombres de tablas en plural: basado en el idioma inglés

-- CREAR ESQUEMAS 
	
	-- Esquema app
create schema app;


-- CREAR TABLAS
	-- Tabla organizations
create table app.organizations (
	organization_id integer generated always as identity primary key,
	name varchar(100) not null unique, 
	email varchar(100) not null unique,
	description varchar(140)
);
	-- Tabla levels
create table app.levels (
	level_id integer generated always as identity primary key, 
	name varchar(100) not null unique,
	description varchar (140), 
	organization integer default 0 references app.organizations on delete set default
);
	-- Es necesario hacerlo en dos pasos porque si no se puede referenciar algo que no existe (father)
alter table app.levels add column parent integer default 0 references app.levels on delete set default;

	-- Fué necesaria la modificación de default a null, porque 0 no existe
alter table app.levels alter column organization set default null;
alter table app.levels alter column parent set default null; 

	-- Tabla roles
create table app.roles (
	roles_id integer generated always as identity primary key, 
	name varchar(100) not null unique
);

	-- Tabla users (luego definimos la contraseña)
create table app.users (
	user_id integer generated always as identity primary key,
	email varchar(100) not null unique,
	username varchar(100) not null unique, 
	pass_word varchar(100) not null -- Luego guardamos hash (ver crypto)
);

	-- Tabla references
create table app.reference (
	reference_id integer generated always as identity primary key, 
	title varchar(100) not null, 
	url varchar(350) not null, 
	description varchar (140), 
	level_id integer references app.levels on delete cascade not null
);

	-- Tabla tags
create table app.tags (
	tag_id integer generated always as identity primary key, 
	name varchar(50) not null unique 
);

	-- Tabla questions
create table app.questions (
	question_id integer generated always as identity primary key, 
	description varchar(140) not null, 
	date date default CURRENT_DATE -- siempre entre comillas simples
);

	-- Tabla activity_types
create table app.activity_types (
	activity_type_id integer generated always as identity primary key, 
	name varchar(100) not null,
	description varchar(255) not null, 
	functional_template bytea not null, -- archivo .js funcional
	parameter_model json not null -- archivo json con los parámetros necesarios para el template
);

----------------------
--| EJEMPLO BITEA  |--
----------------------
--CREATE TABLE stored_files (
  --  id SERIAL PRIMARY KEY,
    --file_name VARCHAR(255) NOT NULL,
    --content BYTEA
--);

-- Insert a file
--INSERT INTO stored_files (file_name, content)
--VALUES ('example.js', pg_read_binary_file('path/to/your/example.js'));

-- Retrieve the content of a file
--SELECT file_name, content FROM stored_files WHERE file_name = 'example.js';

----------------------
--| EJEMPLO BITEA  |--
----------------------

	-- Tabla activities
create table app.activities (
	activity_id integer generated always as identity primary key, 
	description varchar(140) not null,
	level_id integer references app.levels on delete cascade, 
	activity_type integer default 0 references app.activity_types on delete set default 
);

	-- Tabla answers
create table app.answers(
	answer_id integer generated always as identity primary key, 
	date date default CURRENT_DATE,
	activity integer default 0 references app.activities on delete set default, 
	status varchar(50), -- cambiar luego a una nueva entidad categorizada quizás 
	user_id integer default 0 references app.users on delete set default  
);

	-- Tabla Intermedia URO (users, roles, organizations)
create table app.users_roles_organizations(
	uro_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	role_id integer default 0 references app.roles on delete set default not null, 
	organization integer references app.organizations on delete cascade not null, 
	unique(user_id, role_id, organization)
);

	-- Tabla Intermedia URL (users, roles, levels) no es necesaria porque el level tiene la organización?
create table app.users_roles_levels (
	url_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	role_id integer references app.roles on delete cascade not null, 
	level_id integer references app.levels on delete cascade not null,
	unique(user_id, role_id, level_id)
);

	-- Tabla Intermedia votes (users, questions)
create table app.votes (
	vote_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	question integer references app.questions on delete cascade not null, 
	active boolean default true not null, -- por si cambia de opinion varias veces
	date date default CURRENT_DATE, 
	unique(user_id, question)
);

	-- Tabla Intermedia question_tag
create table app.questions_tags (
	questions_tag_id integer generated always as identity primary key, 
	question integer references app.questions on delete cascade not null, 
	tag integer references app.tags on delete cascade not null, 
	unique(question, tag)
); 

