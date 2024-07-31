-- SCRIPT CREACIÓN DB

-- ACLARACIONES
-- * Las convenciones de los nombres de tablas en plural: basado en el idioma inglés

-- SETEAR TIMEZONE
ALTER SYSTEM SET timezone TO 'America/Argentina/Ushuaia';

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
	organization integer default 0 references app.organizations on delete set 1 not null
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
	title varchar(100) not null, -- En la lógica del negocio el title es unique para cada level
	url varchar(350) not null, 
	description varchar (140), 
	level_id integer references app.levels on delete cascade not null,
	unique(title, level_id) -- En un nivel el nombre de la referencia es único
);

	-- Tabla tags
create table app.tags (
	tag_id integer generated always as identity primary key, 
	name varchar(50) not null unique 
);

	-- Tabla concerns
create table app.concerns (
	concern_id integer generated always as identity primary key, 
	description varchar(140) not null, 
	url varchar(500), 
	date timestamp with time zone default CURRENT_DATE, -- siempre entre comillas simples
	user_id integer default 1 references app.users on delete set 1 not null
);

	-- Tabla activity_types
create table app.activity_types (
	activity_type_id integer generated always as identity primary key, 
	name varchar(100) not null unique,
	description varchar(255) not null, 
	creator integer default 1 references app.users on delete set default not null,
);


	
-- Tabla activity_type_version (it doesnt get deleted, just change its status to DELETED (10))
create table app.activity_type_version (
        activity_type_version_id integer generated always as identity primary key,
        activity_type_id integer default 1 references app.activity_types on delete set default not null,
        activity_type_version_status_id integer default 7 references app.activity_type_version_status on delete set default not null,
        version_number integer not null, -- disparado por trigger
        staged_date timestamp with time zone default CURRENT_DATE not null, -- cuando es posteado por vez primera
        last_modified_status_date timestamp with time zone default CURRENT_DATE, -- la última vez que se modificado el status de la versión 
        model jsonb not null,
        template text not null,
        readme text not null,
        unique (model, template, readme, activity_type_id) -- todos los campos de Github deben serun conjunto único
);


	-- Tabla file_name_required
create table app.file_name_required(
	file_name_required_id integer generated always as identity primary key,
	file_name varchar(100) unique not null
	extension varchar(10) not null,
	mime_type varchar(100) not null,
	alias varchar(20) not null,
	in_db boolean default true not null
);

	-- Tabla version_server
create table app.version_servers (
	version_server_id integer generated always as identity primary key,
	name varchar(50) unique not null,
	content_url varchar(500) not null
);

	-- Tabla contents
create table app.contents (
   content_id integer generated always as identity primary key,
   activity_type_version integer default 1 references app.activity_types_version on delete set default not null,-- it doesnt get deleted, the version just changes its status to DELETED
   model jsonb not null,
   creator integer references app.users not null,
   public boolean not null,
   organization integer references app.organizations
);


	-- Tabla images
create table app.images (
  image_id integer generated always as identity primary key,
  image_name varchar(50) not null,
  content integer references app.contents on delete cascade not null,
  unique(image_name, content) -- to make sure that the name of the image is unique in that particular content
);

	-- Tabla activities
create table app.activities (
	activity_id integer generated always as identity primary key, 
	description varchar(140) not null,
	level_id integer references app.levels on delete no action unique not null, 
	content integer references app.contents not null
	-- activityTypeVersion is inferred from content
);

	-- Tabla answers
create table app.answers(
	answer_id integer generated always as identity primary key, 
	created timestamp with time zone default CURRENT_DATE not null, 	-- Puede ser localDateTime
	activity integer default 1 references app.activities on delete set default not null, 
	status boolean default false not null;
        -- add percentage double default 0.0 not null, PARA DETERMINAR EL PORCENTAJE DE COMPLETADO TANTO PARA FALLO COMO PARA EXITO (quizás implica que la version explicite en el model el procentaje)	
	user_id integer default 1 references app.users on delete set default not null  
);

	-- Tabla streak (racha)
create table app.random_streak(
	streak_id integer generated always as identity primary key,
	max_streak integer not null,
	actual_streak integer not null,
	streak_count integer not null,
	user_id integer references app.users not null unique
	-- random boolean not null PODRÍAMOS USAR ESTE DOSCRIMINADOR PARA PERISTIR RACHAS DE PATHS TAMBIÉN, NO SOLO EN MODO RANDOM. REGISTRARÍAMOS LA CANTIDAD, NO LA RUTA
);

	-- Tabla app.activity_type_version_status (CATEGORíA NOMINAL)
create table app.activity_type_version_status(
	activity_type_version_status_id integer generated always as identity primary key,
	title varchar(50) not null unique,
	description varchar(500) not null
);

-- Tabla app.entities (CATEGORíA NOMINAL)
create table app.entity_types(
	entity_id integer generated always as identity primary key,
	title varchar(50) not null unique,
	votable boolean default true not null -- could be determined programatically tru properties
);

	-- Tabla Intermedia URO (users, roles, organizations)
create table app.users_roles_organizations(
	uro_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	role_id integer default 1 references app.roles on delete set cascade not null, 
	organization_id integer references app.organizations on delete cascade not null, 
	unique(user_id, organization) -- un único rol por organización 
);

	-- Tabla Intermedia URL (users, roles, levels) no es necesaria porque el level tiene la organización?
create table app.users_roles_levels (
	url_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	role_id integer references app.roles on delete cascade not null, 
	level_id integer references app.levels on delete cascade not null,
	unique(user_id, level_id) -- un único rol por nivel
);

	-- Tabla Intermedia votes (users, entity, entity_type)
create table app.votes (
	vote_id integer generated always as identity primary key, 
	user_id integer references app.users on delete cascade not null, 
	entity_id integer, 
	entity_type integer references app.entity_types on delete cascade not null, 
	active boolean default true not null, -- por si cambia de opinion varias veces
	date timestamp with time zone default CURRENT_DATE, 
	unique(user_id, entity_id, entity_type)
);

	-- Tabla Intermedia tagged_concern
create table app.tagged (
        tagged_id integer generated always as identity primary key,
        tag_id integer references app.tags not null,
        entity_type_id integer references app.entity_types not null,
	entity_id integer not null,
	unique(tag_id, entity_type_id, entity_id)
);

	-- Tabla intermedia file_names_required_version_server
create table app.file_names_required_version_server (
	fnrvs_id integer generated always as identity primary key,
	file_name_required integer references app.file_name_required on delete cascade not null,
	version_server integer references app.version_servers on delete cascade not null,
	purpose varchar(140) not null, -- why is the file needed in the version_server
	UNIQUE(file_name_required, version_server)
);

----------------------
-----| VIEWS |-----
----------------------

create view app.voted_organizations
(vote_id, user_id, user_name, organization_id, active, "date") as
	select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,   
	app.votes.active, app.votes."date"
	from app.votes, app.users
	where app.votes.user_id = app.users.user_id and app.votes.entity_type = 1;

create view app.voted_levels
	(vote_id, user_id, user_name, level_id, active, "date") as
	select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,   
	app.votes.active, app.votes."date"
	from app.votes, app.users
	where app.votes.user_id = app.users.user_id and app.votes.entity_type = 2;

create view app.voted_activity_types
	(vote_id, user_id, user_name, activity_type_id, active, "date") as
	select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,   
	app.votes.active, app.votes."date"
	from app.votes, app.users
	where app.votes.user_id = app.users.user_id and app.votes.entity_type = 3;

create view app.v_voted_concerns
        (vote_id, user_id, user_name, concern_id, active, "date") as
        select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,   
        app.votes.active, app.votes."date"
        from app.votes, app.users
        where app.votes.user_id = app.users.user_id and app.votes.entity_type = 4;

create view app.voted_references
        (vote_id, user_id, user_name, reference_id, active, "date") as
        select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,
        app.votes.active, app.votes."date"
        from app.votes, app.users
        where app.votes.user_id = app.users.user_id and app.votes.entity_type = 5;

create view app.voted_activity_type_versions
        (vote_id, user_id, user_name, activity_type_version_id, active, "date") as
        select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,
        app.votes.active, app.votes."date"
        from app.votes, app.users
        where app.votes.user_id = app.users.user_id and app.votes.entity_type = 6;

create view app.voted_contents
        (vote_id, user_id, user_name, content_id, active, "date") as
        select app.votes.vote_id, app.votes.user_id, app.users.username, app.votes.entity_id,
        app.votes.active, app.votes."date"
        from app.votes, app.users
        where app.votes.user_id = app.users.user_id and app.votes.entity_type = 7;

create view app.tagged_organizations
	(tagged_id, tag_id, tag_name, organization_id) as
	select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id   
	from app.tagged, app.tags
	where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 1;

create view app.tagged_levels
        (tagged_id, tag_id, tag_name, level_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 2;

create view app.tagged_activity_types
        (tagged_id, tag_id, tag_name, activity_type_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 3;

create view app.tagged_concerns
        (tagged_id, tag_id, tag_name, concern_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 4;


create view app.tagged_references
        (tagged_id, tag_id, tag_name, reference_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 5;

create view app.tagged_activity_type_versions
        (tagged_id, tag_id, tag_name, activity_type_version_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 6;

create view app.tagged_contents
        (tagged_id, tag_id, tag_name, content_id) as
        select app.tagged.tagged_id, app.tagged.tag_id, app.tags."name", app.tagged.entity_id
        from app.tagged, app.tags
        where app.tags.tag_id = app.tagged.tag_id and app.tagged.entity_type_id = 7;


create view app.v_uro 
	(uro_id, user_id, user_name, user_email, role_id, role_name, organization_id, organization_name, organization_email) as 
	select uro_id, uro.user_id, u.username, u.email, roles_id, r.name, uro.organization_id, o.name, o.email 
	from app.users_roles_organizations as uro, app.users as u, app.roles as r, app.organizations as o 
	where uro.user_id = u.user_id and uro.organization_id = o.organization_id and role_id = r.roles_id;
	
create view app.v_url 
        (uro_id, user_id, user_name, user_email, role_id, role_name, level_id, level_name, level_parent, level_organization_id, level_organization_name) as  
        select url_id, url.user_id, u.username, u.email, roles_id, r.name, url.level_id, l.name, l.parent, l.organization, o.name 
        from app.users_roles_levels as url, app.users as u, app.roles as r, app.levels as l, app.organizations as o 
        where url.user_id = u.user_id and url.level_id = l.level_id and role_id = r.roles_id and o.organization_id = l.organization;

----------------------
-----| FUNCTIONS |-----
----------------------

-- Autoincrement versions
CREATE OR REPLACE FUNCTION app.increment_activity_type_version()
RETURNS TRIGGER AS $$
BEGIN
    -- Calculate the next version number based on the number of existing versions for the same activity type.
    NEW.version_number = ( -- the new version_number
        SELECT COALESCE(MAX(version_number), 0) + 1 -- is equal to the max version number + 1
        FROM app.activity_types_version
        WHERE activity_type_id = NEW.activity_type_id -- when the activity_type is the same
    );
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Get FirstOfLevel of level
CREATE OR REPLACE FUNCTION get_ancestor(child_level INTEGER)
RETURNS INTEGER AS $$
DECLARE
    oldest INTEGER;
    parent INTEGER;
BEGIN
    -- Initialize the oldest level to the given child level
    oldest := child_level;

    -- Get the parent of the current level
    SELECT l.parent INTO parent FROM app.levels l WHERE l.level_id = child_level;

    -- Loop to find the top-most parent
    WHILE parent IS NOT NULL LOOP
        oldest := parent;
        SELECT l.parent INTO parent FROM app.levels l WHERE l.level_id = oldest;
    END LOOP;

    -- Return the highest ancestor level
    RETURN oldest;
END;
$$ LANGUAGE plpgsql;

-- Function Call
	-- select get_ancestor(23);
-- Some usages
	-- Get all descendency of level 12 
	-- select * from app.levels where get_ancestor(level_id) = 12;
	-- Get the ancestor of level 12
	-- select * from app.levels where level_id = get_ancestor(12);

-- Get the genalogy of a level: its parent and its parent parent and so on...
CREATE OR REPLACE FUNCTION get_genealogy(child_level INTEGER)
RETURNS table(child integer, parent integer) AS $$
BEGIN
    -- Initialize the parent_id with the given child_level_id
    return query
	with recursive parent_cte as (
		select l.level_id, l.parent from app.levels l where l.level_id = child_level
	UNION ALL
		select l.level_id, l.parent
		from app.levels l inner join parent_cte p on l.level_id = p.parent
)
 	select * from parent_cte order by parent nulls first;
END;
$$ LANGUAGE plpgsql;
-- Some usages
	-- Get the genealogy of level 18
	-- select * from get_genealogy(18);

----------------------
-----| TRIGGERS |----
----------------------

create trigger app.autoincrement_of_activity_type_version 
before insert on app.activity_types_version 
for each row 
execute function increment_activity_type_version();


-----------------------------------------------
------ FALLBACK ROWS FOR DELETED REFERENCES --- This is not apropiated, it should pesist everything and just mark it as deleted
-----------------------------------------------
   -- activity_types_version (default in app.contents)
insert into app.activity_type_version overriding system value values (1, 1, 7, 1, DEFAULT , DEFAULT , '{"fallback" : "DELETED_ACTIVITY_TYPE_VERSION"}', 'DELETED_ACTIVITY_TYPE_VERSION', 'DELETED_ACTIVITY_TYPE_VERSION');


----------------------------
--- SOME HELPFULL QUERIES---
----------------------------

-- Recuperar 
ciudadano_consciente=# select C.template from app.activities as A inner join app.contents as B on(a.content = b.content_id) inner join app.activity_type_version as C on(b.activity_type_version = c.activity_type_version_id);

