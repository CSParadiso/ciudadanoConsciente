-- init.sql

CREATE USER saimon WITH PASSWORD 'chipaParaguaya' SUPERUSER;

----------- KEYCLOAK ---------------------------------------------------
CREATE DATABASE IF NOT EXISTS keycloak; -- Tratar de crear la DB keycloak

-- Conectarse a la DB keycloak
\c keycloak;

-- Crear el esquema keycloak, si no existe
CREATE SCHEMA IF NOT EXISTS keycloak;
-----------------------------------------------------------------------

--------------------- CIUDADANO CONSCIENTE ---------------------------
CREATE DATABASE ciudadano_consciente;
\c  ciudadano_consciente;
CREATE SCHEMA app;
---------------------------------------------------------------------

