---------------- INICIO SCRIPT -----------------

\connect keycloak;

-- Establecer horario local en la DB
SET TIMEZONE TO 'America/Argentina/Ushuaia';

-- Crear esquema y asignar permisos
DO $$
BEGIN
    -- Crear esquema 'keycloak' si no existe
    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'keycloak') THEN
        CREATE SCHEMA keycloak;
    END IF;
    
    -- Asignar permisos al usuario
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA keycloak TO citizen_admin;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA keycloak TO citizen_admin;
END $$;

-----------------------------------------------------------------------
