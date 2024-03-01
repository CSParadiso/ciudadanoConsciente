--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Ubuntu 16.1-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: app; Type: SCHEMA; Schema: -; Owner: saimon
--

CREATE SCHEMA app;


ALTER SCHEMA app OWNER TO saimon;

--
-- Name: increment_activity_type_version(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.increment_activity_type_version() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Calculate the next version number based on the number of existing versions for the same activity type.
    NEW.version_number = ( -- the new version_number
        SELECT COALESCE(MAX(version_number), 0) + 1 -- is equal to the max version number + 1
        FROM app.activity_type_version
        WHERE activity_type_id = NEW.activity_type_id -- when the activity_type is the same
    );

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.increment_activity_type_version() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: activities; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.activities (
    activity_id integer NOT NULL,
    description character varying(140) NOT NULL,
    level_id integer NOT NULL,
    activity_type integer DEFAULT 0
);


ALTER TABLE app.activities OWNER TO saimon;

--
-- Name: activities_activity_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.activities ALTER COLUMN activity_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.activities_activity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: activity_type_version; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.activity_type_version (
    activity_type_version_id integer NOT NULL,
    activity_type_id integer DEFAULT 1 NOT NULL,
    activity_type_version_status_id integer DEFAULT 7 NOT NULL,
    version_number integer NOT NULL,
    staged_date date DEFAULT CURRENT_DATE NOT NULL,
    last_modified_status_date date DEFAULT CURRENT_DATE,
    model jsonb NOT NULL,
    template text NOT NULL,
    readme text NOT NULL
);


ALTER TABLE app.activity_type_version OWNER TO postgres;

--
-- Name: activity_type_version_activity_type_version_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.activity_type_version ALTER COLUMN activity_type_version_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.activity_type_version_activity_type_version_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: activity_type_version_status; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.activity_type_version_status (
    activity_type_version_status_id integer NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(500) NOT NULL
);


ALTER TABLE app.activity_type_version_status OWNER TO saimon;

--
-- Name: activity_types; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.activity_types (
    activity_type_id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255) NOT NULL,
    creator integer DEFAULT 1 NOT NULL
);


ALTER TABLE app.activity_types OWNER TO saimon;

--
-- Name: activity_types_activity_type_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.activity_types ALTER COLUMN activity_type_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.activity_types_activity_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: answers; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.answers (
    answer_id integer NOT NULL,
    created date DEFAULT CURRENT_DATE,
    activity integer DEFAULT 0 NOT NULL,
    user_id integer DEFAULT 0 NOT NULL,
    last_modified date DEFAULT CURRENT_DATE,
    status boolean DEFAULT false NOT NULL
);


ALTER TABLE app.answers OWNER TO saimon;

--
-- Name: answers_answer_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.answers ALTER COLUMN answer_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.answers_answer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: answers_status_answers_status_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.activity_type_version_status ALTER COLUMN activity_type_version_status_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.answers_status_answers_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: concerns; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.concerns (
    concern_id integer NOT NULL,
    description character varying(140) NOT NULL,
    date date DEFAULT CURRENT_DATE,
    user_id integer DEFAULT 1 NOT NULL,
    explanation character varying(500)
);


ALTER TABLE app.concerns OWNER TO saimon;

--
-- Name: contents; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.contents (
    content_id integer NOT NULL,
    activity_type_version integer DEFAULT 1 NOT NULL,
    model jsonb NOT NULL
);


ALTER TABLE app.contents OWNER TO postgres;

--
-- Name: contents_content_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.contents ALTER COLUMN content_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.contents_content_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: entity_types; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.entity_types (
    entity_type_id integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE app.entity_types OWNER TO saimon;

--
-- Name: entities_allowed_votes_entities_allowed_votes_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.entity_types ALTER COLUMN entity_type_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.entities_allowed_votes_entities_allowed_votes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: file_name_required; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.file_name_required (
    file_name_required_id integer NOT NULL,
    file_name character varying(100) NOT NULL,
    extension character varying(10) NOT NULL,
    mime_type character varying(100) NOT NULL,
    alias character varying(20) NOT NULL,
    in_db boolean DEFAULT true NOT NULL
);


ALTER TABLE app.file_name_required OWNER TO postgres;

--
-- Name: file_name_required_file_name_required_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.file_name_required ALTER COLUMN file_name_required_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.file_name_required_file_name_required_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: file_names_required_version_server; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.file_names_required_version_server (
    fnrvs_id integer NOT NULL,
    file_name_required integer NOT NULL,
    version_server integer NOT NULL,
    purpose character varying(140) NOT NULL
);


ALTER TABLE app.file_names_required_version_server OWNER TO postgres;

--
-- Name: file_names_required_version_server_fnrvs_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.file_names_required_version_server ALTER COLUMN fnrvs_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.file_names_required_version_server_fnrvs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: images; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.images (
    image_id integer NOT NULL,
    image_name character varying(50) NOT NULL,
    content integer NOT NULL
);


ALTER TABLE app.images OWNER TO postgres;

--
-- Name: images_image_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.images ALTER COLUMN image_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.images_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: levels; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.levels (
    level_id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(140),
    organization integer DEFAULT 1 NOT NULL,
    parent integer DEFAULT 0
);


ALTER TABLE app.levels OWNER TO saimon;

--
-- Name: levels_level_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.levels ALTER COLUMN level_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.levels_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: organizations; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.organizations (
    organization_id integer NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    description character varying(140)
);


ALTER TABLE app.organizations OWNER TO saimon;

--
-- Name: organizations_organization_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.organizations ALTER COLUMN organization_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.organizations_organization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: questions_question_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.concerns ALTER COLUMN concern_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.questions_question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: questions_tags; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.questions_tags (
    questions_tag_id integer NOT NULL,
    question integer NOT NULL,
    tag integer NOT NULL
);


ALTER TABLE app.questions_tags OWNER TO saimon;

--
-- Name: questions_tags_questions_tag_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.questions_tags ALTER COLUMN questions_tag_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.questions_tags_questions_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: reference; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.reference (
    reference_id integer NOT NULL,
    title character varying(100) NOT NULL,
    url character varying(350) NOT NULL,
    description character varying(140),
    level_id integer NOT NULL
);


ALTER TABLE app.reference OWNER TO saimon;

--
-- Name: reference_reference_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.reference ALTER COLUMN reference_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.reference_reference_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: roles; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.roles (
    roles_id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE app.roles OWNER TO saimon;

--
-- Name: roles_roles_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.roles ALTER COLUMN roles_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.roles_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: tags; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.tags (
    tag_id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE app.tags OWNER TO saimon;

--
-- Name: tags_tag_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.tags ALTER COLUMN tag_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.tags_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.users (
    user_id integer NOT NULL,
    email character varying(100) NOT NULL,
    username character varying(100) NOT NULL,
    pass_word character varying(100) NOT NULL
);


ALTER TABLE app.users OWNER TO saimon;

--
-- Name: users_roles_levels; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.users_roles_levels (
    url_id integer NOT NULL,
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    level_id integer NOT NULL
);


ALTER TABLE app.users_roles_levels OWNER TO saimon;

--
-- Name: users_roles_levels_url_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.users_roles_levels ALTER COLUMN url_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.users_roles_levels_url_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users_roles_organizations; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.users_roles_organizations (
    uro_id integer NOT NULL,
    user_id integer NOT NULL,
    role_id integer DEFAULT 1 NOT NULL,
    organization_id integer NOT NULL
);


ALTER TABLE app.users_roles_organizations OWNER TO saimon;

--
-- Name: users_roles_organizations_uro_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.users_roles_organizations ALTER COLUMN uro_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.users_roles_organizations_uro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: version_servers; Type: TABLE; Schema: app; Owner: postgres
--

CREATE TABLE app.version_servers (
    version_server_id integer NOT NULL,
    name character varying(50) NOT NULL,
    content_url character varying(500) NOT NULL
);


ALTER TABLE app.version_servers OWNER TO postgres;

--
-- Name: version_severs_version_server_id_seq; Type: SEQUENCE; Schema: app; Owner: postgres
--

ALTER TABLE app.version_servers ALTER COLUMN version_server_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.version_severs_version_server_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: votes; Type: TABLE; Schema: app; Owner: saimon
--

CREATE TABLE app.votes (
    vote_id integer NOT NULL,
    user_id integer NOT NULL,
    entity_id integer NOT NULL,
    entity_type integer NOT NULL,
    active boolean DEFAULT true NOT NULL,
    date date DEFAULT CURRENT_DATE
);


ALTER TABLE app.votes OWNER TO saimon;

--
-- Name: votes_vote_id_seq; Type: SEQUENCE; Schema: app; Owner: saimon
--

ALTER TABLE app.votes ALTER COLUMN vote_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME app.votes_vote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: activities; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.activities (activity_id, description, level_id, activity_type) FROM stdin;
6	Multiple Choice	12	9
7	Prueba de Actividad	7	10
9	Prueba de actividad	12	10
5	Prueba de actualización	5	9
1	FK DELETED	1	1
\.


--
-- Data for Name: activity_type_version; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.activity_type_version (activity_type_version_id, activity_type_id, activity_type_version_status_id, version_number, staged_date, last_modified_status_date, model, template, readme) FROM stdin;
40	14	1	4	2024-02-19	2024-02-19	{"body": {"name": "string", "size": 12345.12, "valid": true}, "response": {"status": false, "description": "Success"}}	{\n\t"body": {\n\t\t"name": "string",\n\t\t"size": 12345.12,\n\t\t"valid": true\n\t},\n\t"response": {\n\t\t"status": false,\n\t\t"description": "Success" \n\t}\n}\n	function play(){\n\tconsole.log("Playing activityType");\n}\n\nfunction end(){\n\tconsole.log("End of game");\n}\n
1	1	7	1	2024-02-28	2024-02-28	{"fallback": "DELETED_ACTIVITY_TYPE_VERSION"}	DELETED_ACTIVITY_TYPE_VERSION	DELETED_ACTIVITY_TYPE_VERSION
\.


--
-- Data for Name: activity_type_version_status; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.activity_type_version_status (activity_type_version_status_id, title, description) FROM stdin;
1	STAGED	Version initial state. The user has subscribed the version for review.
2	APPROVED	The version has been approved by the app but not published by the user.
3	REJECTED	The version has NOT been approved by the app.
4	PUBLISHED	The version has been approved and exposed.
7	ACTIVITY_DELETED	The ActivityType corresponding to the version has been removed.
9	NOT_PUBLISHED	The version has been APPROVED, but is not published.
8	NOT_AVAILABLE	The files of the version are not reachable by fetching function.
\.


--
-- Data for Name: activity_types; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.activity_types (activity_type_id, name, description, creator) FROM stdin;
19	Pruab de creacion de actividad	Drag and drop	11
9	nuwerll	wer	9
10	Prueba	Prueba de Creacion de Tipo de Actividad	9
14	Nuevo nombre de prueba	Nueva descripcion solita	9
16	Click and bate	Clickear donde veas fake news	9
1	FK DELETED	The ActivityType has been deleted	11
20	Prueba de nivel	Ejecricio conla nueva estructira de AxctivityType	9
\.


--
-- Data for Name: answers; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.answers (answer_id, created, activity, user_id, last_modified, status) FROM stdin;
1	2024-01-09	5	5	2024-01-09	f
\.


--
-- Data for Name: concerns; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.concerns (concern_id, description, date, user_id, explanation) FROM stdin;
1	¿Cómo se podría mejorar la transparencia de un gobierno?	2024-01-12	5	\N
2	¿Cómo se podría repartir las tierras para garantizar una parcela a cada ciudadano?	2024-01-12	9	\N
3	¿Se puede hacer un dashboard público de algunos indicadores comerciales del gobierno	2024-01-12	9	\N
5	¿Cómo se podría hacer más transparente el ingreso del personal al empleo público?	2024-01-13	11	www.google.com.ar
6	¿Se puede agregar desde el contenedor a la db?	2024-01-27	11	https://www.docker.com
\.


--
-- Data for Name: contents; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.contents (content_id, activity_type_version, model) FROM stdin;
4	1	{"options": {"A": "", "B": "", "C": "", "D": ""}, "question": "", "correct_answer": ""}
5	1	{"body": {"name": "string", "size": 12345.12, "valid": true}, "response": {"status": false, "description": "Success"}}
\.


--
-- Data for Name: entity_types; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.entity_types (entity_type_id, title) FROM stdin;
1	Organization
4	Concern
5	Reference
2	Level
3	ActivityType
6	ActivityTypeVersion
7	Content
\.


--
-- Data for Name: file_name_required; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.file_name_required (file_name_required_id, file_name, extension, mime_type, alias, in_db) FROM stdin;
1	README	.md	text/markdown	markdown	t
2	model	.json	application/json	json	t
3	template	.js	text/javascript	js	t
4	thumbnail	.png	image/png	png	f
\.


--
-- Data for Name: file_names_required_version_server; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.file_names_required_version_server (fnrvs_id, file_name_required, version_server, purpose) FROM stdin;
1	1	1	Required to create a Version of ActivityType.
2	2	1	Required to create a Version of ActivityType.
3	3	1	Required to create a Version of ActivityType.
4	4	1	Required to create a Version of ActivityType.
\.


--
-- Data for Name: images; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.images (image_id, image_name, content) FROM stdin;
27	epep	5
29	pepito	5
\.


--
-- Data for Name: levels; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.levels (level_id, name, description, organization, parent) FROM stdin;
2	Música	Path sobre la música de los Rolling Stones	3	\N
3	Discos de Estudio	Branch sobre la discografía de los Rolling Stones	3	2
4	Exile On Main Street	Level sobre el disco grabado en la mansión francesa	3	3
7	Videoclips	Branch sobre los videoclips de los Rolling Stones	3	6
6	Videos	Path sobre la videografía de los Rolling Stones	3	\N
5	Discos en vivo	Branch sobre la discografía en directo de los Rolling Stones	3	2
9	Libros	Path sobre la bibliografía de los Rolling Stoenes	3	\N
12	LevelPrueba	Level de prueba de prueba	3	\N
25	LEvel sin Organizacion	asdsad	5	7
26	LEvel	asdsad	5	7
19	Nivel de Prueba con solo nombre	\N	7	\N
20	Nivel de Prueba	Retorna 201	7	\N
21	Nivel de Prueba de nuevo	Retorna 201	7	\N
22	Nivel de Prueba de nuevo Nuevo		7	\N
23	Led Zeppelin	asdsad	7	7
15	The Beatles	Path sobre los Beatles	7	\N
0	LevelBase	Nivel Base para anclar Niveles sin padre.	7	\N
18	Prueba	Prueba de nombre nulo	8	\N
1	FK DELETED	The Level has been deleted	1	0
14	Prueba Actualizada	Nivel Base para anclar Niveles sin padre.	3	5
\.


--
-- Data for Name: organizations; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.organizations (organization_id, name, email, description) FROM stdin;
3	Rolling Stones	rolling@stones.com	Banda de rock más longeva
5	Led Zeppelin	led@zeppelin.com	Banda de más rock legendaria
7	The Beatles	the@beatles.com	Banda de rock más zarpada
6	Pink Floyd	pink@floyd.com	La banda de rock psicodélica más popular
8	Nick Cave	nick@cave	Banda de rock bastante literaria
13	Jimi Hendrix	jimi@hendrix.com	\N
11	Dancing Mood	dancing@mood.com	string
14	John Lenon	jhon@lennon.com	Artista
15	Blind Faith	blind@faith.com	Una de las mejores bandas
16	System of a Down	system@down.com	La banda más disruptiva dentro del mainstream de los noventa.
1	FK DELETED	deleted@fk.com	The Organization has been deleted
\.


--
-- Data for Name: questions_tags; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.questions_tags (questions_tag_id, question, tag) FROM stdin;
\.


--
-- Data for Name: reference; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.reference (reference_id, title, url, description, level_id) FROM stdin;
5	Wiki de la discografía de estudio de los Rollings Stones en inglés.	https://en.wikipedia.org/wiki/The_Rolling_Stones#Discography	Wiki de la discografía de estudio de los Rollings Stones en inglés.	3
6	Wiki de los albumes en vivo de los Rolling Stones	https://en.wikipedia.org/wiki/Category:The_Rolling_Stones_live_albums	Wiki de la discografía en vivo de los Rollings Stones en inglés.	3
7	Official Page RS	https://rollingstones.com/	Sitio oficial de los Rollings Stones	2
10	Enlace a Youtube	https://www.youtube.com/watch?v=bVrUDqvLGbM&list=PLWutrmcj62y1C2CY5gMOEVmfkuxEqrbyL&index=15	description	7
11	Enlace a Youtube	https://www.youtube.com/watch?v=bVrUDqvLGbM&list=PLWutrmcj62y1C2CY5gMOEVmfkuxEqrbyL&index=15	description	19
19	Enlace a algo	https://www.postgresql.org/	Página web de PostreSQL	9
12	Prueba de referencia	https://www.google.com	description	4
8	Enlace a Youtube	https://www.youtube.com/watch?v=bVrUDqvLGbM&list=PLWutrmcj62y1C2CY5gMOEVmfkuxEqrbyL&index=15	description	2
21	NUeva Referencia	www.taringa.com		3
22	Taringa	www.taringa.com	salvaje	3
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.roles (roles_id, name) FROM stdin;
4	Divulgator
5	Moderator
3	Admin
7	Tester
\.


--
-- Data for Name: tags; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.tags (tag_id, name) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.users (user_id, email, username, pass_word) FROM stdin;
4	ron@wood.com	ron	wood
5	brian@jones.com	brian	jones
7	charlie@watts.com	charlie	watts
9	jimi@page.com	jimi	page
8	saimon@paradiso.com	saimon	guitarra
11	roger@waters.com	Roger	waters
14	ringo@star	Ringo	star
18	mick@jagger.com	mick	jagger
1	fk@deleted	FK DELETED	FE DELETED
\.


--
-- Data for Name: users_roles_levels; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.users_roles_levels (url_id, user_id, role_id, level_id) FROM stdin;
1	7	4	19
8	8	7	6
11	8	4	5
3	8	7	9
12	8	7	5
2	4	4	5
\.


--
-- Data for Name: users_roles_organizations; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.users_roles_organizations (uro_id, user_id, role_id, organization_id) FROM stdin;
5	5	7	5
6	5	7	8
7	5	5	5
8	5	4	5
9	8	4	5
\.


--
-- Data for Name: version_servers; Type: TABLE DATA; Schema: app; Owner: postgres
--

COPY app.version_servers (version_server_id, name, content_url) FROM stdin;
1	github	https://raw.githubusercontent.com/{user}/{repo}/{commit}/{path}/{filename}
\.


--
-- Data for Name: votes; Type: TABLE DATA; Schema: app; Owner: saimon
--

COPY app.votes (vote_id, user_id, entity_id, entity_type, active, date) FROM stdin;
1	7	15	1	f	2024-01-16
2	8	5	2	t	2024-01-16
3	9	7	1	t	2024-01-16
4	5	7	1	t	2024-01-16
5	4	3	1	t	2024-01-16
6	5	3	1	t	2024-01-16
7	9	9	3	t	2024-01-16
8	11	2	4	t	2024-01-16
9	9	5	5	t	2024-01-16
10	11	20	3	t	2024-01-30
11	14	9	3	t	2024-02-02
12	9	22	6	t	2024-02-02
13	14	5	7	t	2024-02-29
\.


--
-- Name: activities_activity_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.activities_activity_id_seq', 9, true);


--
-- Name: activity_type_version_activity_type_version_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.activity_type_version_activity_type_version_id_seq', 44, true);


--
-- Name: activity_types_activity_type_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.activity_types_activity_type_id_seq', 21, true);


--
-- Name: answers_answer_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.answers_answer_id_seq', 1, true);


--
-- Name: answers_status_answers_status_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.answers_status_answers_status_id_seq', 9, true);


--
-- Name: contents_content_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.contents_content_id_seq', 6, true);


--
-- Name: entities_allowed_votes_entities_allowed_votes_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.entities_allowed_votes_entities_allowed_votes_id_seq', 7, true);


--
-- Name: file_name_required_file_name_required_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.file_name_required_file_name_required_id_seq', 4, true);


--
-- Name: file_names_required_version_server_fnrvs_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.file_names_required_version_server_fnrvs_id_seq', 4, true);


--
-- Name: images_image_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.images_image_id_seq', 29, true);


--
-- Name: levels_level_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.levels_level_id_seq', 26, true);


--
-- Name: organizations_organization_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.organizations_organization_id_seq', 17, true);


--
-- Name: questions_question_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.questions_question_id_seq', 5, true);


--
-- Name: questions_tags_questions_tag_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.questions_tags_questions_tag_id_seq', 1, false);


--
-- Name: reference_reference_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.reference_reference_id_seq', 22, true);


--
-- Name: roles_roles_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.roles_roles_id_seq', 9, true);


--
-- Name: tags_tag_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.tags_tag_id_seq', 1, false);


--
-- Name: users_roles_levels_url_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.users_roles_levels_url_id_seq', 12, true);


--
-- Name: users_roles_organizations_uro_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.users_roles_organizations_uro_id_seq', 9, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.users_user_id_seq', 18, true);


--
-- Name: version_severs_version_server_id_seq; Type: SEQUENCE SET; Schema: app; Owner: postgres
--

SELECT pg_catalog.setval('app.version_severs_version_server_id_seq', 2, true);


--
-- Name: votes_vote_id_seq; Type: SEQUENCE SET; Schema: app; Owner: saimon
--

SELECT pg_catalog.setval('app.votes_vote_id_seq', 13, true);


--
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (activity_id);


--
-- Name: activity_type_version activity_type_version_model_template_readme_activ_key; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.activity_type_version
    ADD CONSTRAINT activity_type_version_model_template_readme_activ_key UNIQUE (model, template, readme, activity_type_id);


--
-- Name: activity_type_version activity_type_version_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.activity_type_version
    ADD CONSTRAINT activity_type_version_pkey PRIMARY KEY (activity_type_version_id);


--
-- Name: activity_types activity_types_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activity_types
    ADD CONSTRAINT activity_types_pkey PRIMARY KEY (activity_type_id);


--
-- Name: answers answers_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (answer_id);


--
-- Name: activity_type_version_status answers_status_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activity_type_version_status
    ADD CONSTRAINT answers_status_pkey PRIMARY KEY (activity_type_version_status_id);


--
-- Name: activity_type_version_status answers_status_title_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activity_type_version_status
    ADD CONSTRAINT answers_status_title_key UNIQUE (title);


--
-- Name: contents contents_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.contents
    ADD CONSTRAINT contents_pkey PRIMARY KEY (content_id);


--
-- Name: entity_types entities_allowed_votes_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.entity_types
    ADD CONSTRAINT entities_allowed_votes_pkey PRIMARY KEY (entity_type_id);


--
-- Name: entity_types entities_allowed_votes_title_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.entity_types
    ADD CONSTRAINT entities_allowed_votes_title_key UNIQUE (title);


--
-- Name: file_name_required file_name_required_file_name_key; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_name_required
    ADD CONSTRAINT file_name_required_file_name_key UNIQUE (file_name);


--
-- Name: file_name_required file_name_required_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_name_required
    ADD CONSTRAINT file_name_required_pkey PRIMARY KEY (file_name_required_id);


--
-- Name: file_names_required_version_server file_names_required_version_s_file_name_required_version_se_key; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_names_required_version_server
    ADD CONSTRAINT file_names_required_version_s_file_name_required_version_se_key UNIQUE (file_name_required, version_server);


--
-- Name: file_names_required_version_server file_names_required_version_server_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_names_required_version_server
    ADD CONSTRAINT file_names_required_version_server_pkey PRIMARY KEY (fnrvs_id);


--
-- Name: images images_image_name_content_key; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.images
    ADD CONSTRAINT images_image_name_content_key UNIQUE (image_name, content);


--
-- Name: images images_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (image_id);


--
-- Name: levels levels_name_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.levels
    ADD CONSTRAINT levels_name_key UNIQUE (name);


--
-- Name: levels levels_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.levels
    ADD CONSTRAINT levels_pkey PRIMARY KEY (level_id);


--
-- Name: organizations organizations_email_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.organizations
    ADD CONSTRAINT organizations_email_key UNIQUE (email);


--
-- Name: organizations organizations_name_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.organizations
    ADD CONSTRAINT organizations_name_key UNIQUE (name);


--
-- Name: organizations organizations_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (organization_id);


--
-- Name: concerns questions_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.concerns
    ADD CONSTRAINT questions_pkey PRIMARY KEY (concern_id);


--
-- Name: questions_tags questions_tags_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.questions_tags
    ADD CONSTRAINT questions_tags_pkey PRIMARY KEY (questions_tag_id);


--
-- Name: questions_tags questions_tags_question_tag_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.questions_tags
    ADD CONSTRAINT questions_tags_question_tag_key UNIQUE (question, tag);


--
-- Name: reference reference_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.reference
    ADD CONSTRAINT reference_pkey PRIMARY KEY (reference_id);


--
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (roles_id);


--
-- Name: tags tags_name_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.tags
    ADD CONSTRAINT tags_name_key UNIQUE (name);


--
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (tag_id);


--
-- Name: reference unique_level_name; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.reference
    ADD CONSTRAINT unique_level_name UNIQUE (level_id, title);


--
-- Name: activity_types unique_name; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activity_types
    ADD CONSTRAINT unique_name UNIQUE (name);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: users_roles_levels users_roles_levels_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_levels
    ADD CONSTRAINT users_roles_levels_pkey PRIMARY KEY (url_id);


--
-- Name: users_roles_levels users_roles_levels_user_id_role_id_level_id_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_levels
    ADD CONSTRAINT users_roles_levels_user_id_role_id_level_id_key UNIQUE (user_id, role_id, level_id);


--
-- Name: users_roles_organizations users_roles_organizations_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_organizations
    ADD CONSTRAINT users_roles_organizations_pkey PRIMARY KEY (uro_id);


--
-- Name: users_roles_organizations users_roles_organizations_user_id_role_id_organization_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_organizations
    ADD CONSTRAINT users_roles_organizations_user_id_role_id_organization_key UNIQUE (user_id, role_id, organization_id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: version_servers version_severs_name_key; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.version_servers
    ADD CONSTRAINT version_severs_name_key UNIQUE (name);


--
-- Name: version_servers version_severs_pkey; Type: CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.version_servers
    ADD CONSTRAINT version_severs_pkey PRIMARY KEY (version_server_id);


--
-- Name: votes votes_pkey; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.votes
    ADD CONSTRAINT votes_pkey PRIMARY KEY (vote_id);


--
-- Name: votes votes_user_id_entity_id_entity_type_key; Type: CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.votes
    ADD CONSTRAINT votes_user_id_entity_id_entity_type_key UNIQUE (user_id, entity_id, entity_type);


--
-- Name: activity_type_version autoincrement_of_activity_type_version; Type: TRIGGER; Schema: app; Owner: postgres
--

CREATE TRIGGER autoincrement_of_activity_type_version BEFORE INSERT ON app.activity_type_version FOR EACH ROW EXECUTE FUNCTION public.increment_activity_type_version();


--
-- Name: activities activities_activity_type_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activities
    ADD CONSTRAINT activities_activity_type_fkey FOREIGN KEY (activity_type) REFERENCES app.activity_types(activity_type_id) ON DELETE SET DEFAULT;


--
-- Name: activities activities_level_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activities
    ADD CONSTRAINT activities_level_id_fkey FOREIGN KEY (level_id) REFERENCES app.levels(level_id) ON DELETE CASCADE;


--
-- Name: activity_type_version activity_type_version_activity_type_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.activity_type_version
    ADD CONSTRAINT activity_type_version_activity_type_id_fkey FOREIGN KEY (activity_type_id) REFERENCES app.activity_types(activity_type_id) ON DELETE SET DEFAULT;


--
-- Name: activity_type_version activity_type_version_activity_type_version_status_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.activity_type_version
    ADD CONSTRAINT activity_type_version_activity_type_version_status_id_fkey FOREIGN KEY (activity_type_version_status_id) REFERENCES app.activity_type_version_status(activity_type_version_status_id) ON DELETE SET DEFAULT;


--
-- Name: activity_types activity_types_creator_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.activity_types
    ADD CONSTRAINT activity_types_creator_fkey FOREIGN KEY (creator) REFERENCES app.users(user_id) ON DELETE SET DEFAULT;


--
-- Name: answers answers_activity_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.answers
    ADD CONSTRAINT answers_activity_fkey FOREIGN KEY (activity) REFERENCES app.activities(activity_id) ON DELETE SET DEFAULT;


--
-- Name: answers answers_user_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.answers
    ADD CONSTRAINT answers_user_fkey FOREIGN KEY (user_id) REFERENCES app.users(user_id) ON DELETE SET DEFAULT;


--
-- Name: concerns concerns_user_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.concerns
    ADD CONSTRAINT concerns_user_id_fkey FOREIGN KEY (user_id) REFERENCES app.users(user_id) ON DELETE SET DEFAULT;


--
-- Name: contents contents_activity_type_version_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.contents
    ADD CONSTRAINT contents_activity_type_version_fkey FOREIGN KEY (activity_type_version) REFERENCES app.activity_type_version(activity_type_version_id) ON DELETE SET DEFAULT;


--
-- Name: file_names_required_version_server file_names_required_version_server_file_name_required_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_names_required_version_server
    ADD CONSTRAINT file_names_required_version_server_file_name_required_fkey FOREIGN KEY (file_name_required) REFERENCES app.file_name_required(file_name_required_id) ON DELETE CASCADE;


--
-- Name: file_names_required_version_server file_names_required_version_server_version_server_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.file_names_required_version_server
    ADD CONSTRAINT file_names_required_version_server_version_server_fkey FOREIGN KEY (version_server) REFERENCES app.version_servers(version_server_id) ON DELETE CASCADE;


--
-- Name: users_roles_levels fk_new_user; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_levels
    ADD CONSTRAINT fk_new_user FOREIGN KEY (user_id) REFERENCES app.users(user_id);


--
-- Name: users_roles_organizations fk_new_user; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_organizations
    ADD CONSTRAINT fk_new_user FOREIGN KEY (user_id) REFERENCES app.users(user_id);


--
-- Name: images images_content_fkey; Type: FK CONSTRAINT; Schema: app; Owner: postgres
--

ALTER TABLE ONLY app.images
    ADD CONSTRAINT images_content_fkey FOREIGN KEY (content) REFERENCES app.contents(content_id) ON DELETE CASCADE;


--
-- Name: levels levels_organization_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.levels
    ADD CONSTRAINT levels_organization_fkey FOREIGN KEY (organization) REFERENCES app.organizations(organization_id) ON DELETE SET DEFAULT;


--
-- Name: levels levels_parent_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.levels
    ADD CONSTRAINT levels_parent_fkey FOREIGN KEY (parent) REFERENCES app.levels(level_id) ON DELETE SET DEFAULT;


--
-- Name: questions_tags questions_tags_question_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.questions_tags
    ADD CONSTRAINT questions_tags_question_fkey FOREIGN KEY (question) REFERENCES app.concerns(concern_id) ON DELETE CASCADE;


--
-- Name: questions_tags questions_tags_tag_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.questions_tags
    ADD CONSTRAINT questions_tags_tag_fkey FOREIGN KEY (tag) REFERENCES app.tags(tag_id) ON DELETE CASCADE;


--
-- Name: reference reference_level_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.reference
    ADD CONSTRAINT reference_level_id_fkey FOREIGN KEY (level_id) REFERENCES app.levels(level_id) ON DELETE CASCADE;


--
-- Name: users_roles_levels users_roles_levels_level_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_levels
    ADD CONSTRAINT users_roles_levels_level_id_fkey FOREIGN KEY (level_id) REFERENCES app.levels(level_id) ON DELETE CASCADE;


--
-- Name: users_roles_levels users_roles_levels_role_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_levels
    ADD CONSTRAINT users_roles_levels_role_id_fkey FOREIGN KEY (role_id) REFERENCES app.roles(roles_id) ON DELETE CASCADE;


--
-- Name: users_roles_organizations users_roles_organizations_organization_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_organizations
    ADD CONSTRAINT users_roles_organizations_organization_fkey FOREIGN KEY (organization_id) REFERENCES app.organizations(organization_id) ON DELETE CASCADE;


--
-- Name: users_roles_organizations users_roles_organizations_role_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.users_roles_organizations
    ADD CONSTRAINT users_roles_organizations_role_id_fkey FOREIGN KEY (role_id) REFERENCES app.roles(roles_id) ON DELETE CASCADE;


--
-- Name: votes votes_entity_type_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.votes
    ADD CONSTRAINT votes_entity_type_fkey FOREIGN KEY (entity_type) REFERENCES app.entity_types(entity_type_id) ON DELETE CASCADE;


--
-- Name: votes votes_user_id_fkey; Type: FK CONSTRAINT; Schema: app; Owner: saimon
--

ALTER TABLE ONLY app.votes
    ADD CONSTRAINT votes_user_id_fkey FOREIGN KEY (user_id) REFERENCES app.users(user_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

