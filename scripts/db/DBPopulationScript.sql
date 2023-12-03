-- POBLANDO LA DB

	-- USERS

	-- Insertar usuarios
insert into app.users (email, username, pass_word)
values 
	('mick@jagger.com', 'mick', 'jagger'), 
	('keith@richards.com', 'kiff', 'richards'), 
	('charlie@watts', 'charlie', 'watts'), 
	('ron@wood', 'ron', 'wood');

	-- Modificar usuarios
update app.users set 
email='charlie@watts.com'
where username = 'charlie';

update app.users  set 
email='ron@wood.com'
where username = 'ron';

-- Borrar todos los usuarios
delete from app.users 

	-- ORGANIZATIONS

	-- Insertar oganizaciones
insert into app.organizations (name, email, description)
values
	('Rolling Stones', 'rolling@stones.com', 'Banda de rock más longeva'),
	('The Beatles', 'the@beatles.com', 'Banda de rock más zarpada'),
	('Led Zeppelin', 'led@zeppelin', 'Banda de más rock legendaria');

	-- Modificar organizaciones
update app.organizations set 
email='led@zeppelin.com'
where "name" = 'Led Zeppelin';

-- Borrar todas las organizaciones
delete from app.organizations 

	-- LEVELS

	-- Insertar Niveles Paths
insert into app.levels (name, description, organization, parent)
values
	('Música', 'Path sobre la música de los Rolling Stones', 3, default);

	-- Insertar Niveles Branch
insert into app.levels (name, description, organization, parent)
values	
	('Discos de Estudio', 'Branch sobre la discografía de los Rolling Stones', 3, 2),
	('Discos en vivo', 'Branch sobre la discografía de los Rolling Stones en vivo', 3, 2);

	-- Insertar Niveles Level
insert into app.levels (name, description, organization, parent)
values	
	('Exile On Main Street', 'Level sobre el disco grabado en la mansión francesa', 3, 3);
 
	-- Insertar Nivel Base para busquedas de Niveles sin padre
insert into app.levels (level_id, name, description, organization, parent)
overriding system value
values(0, 'BaseLevel', 'Nivel fijo para ubicar niveles sin padre', null,null)

-- Averiguar nombres de secuencias
SELECT column_name, column_default
FROM information_schema.columns
WHERE table_name = 'users' AND column_name = 'user_id';
nextval('app.users_new_user_id_seq'::regclass)

SELECT pg_get_serial_sequence('app.users', 'user_id');
app.users_new_user_id_seq

SELECT * FROM pg_sequences;
users_new_user_id_seq

-- Step 2: Create a new column with serial type
ALTER TABLE app.users
ADD COLUMN new_user_id serial;

-- Step 3: Update existing data
UPDATE app.users
SET new_user_id = user_id;

-- Step 4: Drop the old column
ALTER TABLE app.users
DROP COLUMN user_id cascade;

-- Step 5: Rename the new column
ALTER TABLE app.users
RENAME COLUMN new_user_id TO user_id;

-- Step 6: Recreate indexes and constraints
ALTER TABLE app.users
ADD PRIMARY KEY (user_id);

-- Añadir clave foránea faltante a tabla
alter table app.users_roles_levels 
add constraint fk_new_user 
foreign key (user_id) 
references app.users(user_id);







