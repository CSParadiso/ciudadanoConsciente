--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Ubuntu 16.1-1.pgdg22.04+1)
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


-- Cambiamos a la DB Keycloak
\c keycloak;

--
-- Name: keycloak; Type: SCHEMA; Schema: -; Owner: saimon
--

ALTER SCHEMA keycloak OWNER TO saimon;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64)
);


ALTER TABLE keycloak.admin_event_entity OWNER TO saimon;

--
-- Name: associated_policy; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.associated_policy OWNER TO saimon;

--
-- Name: authentication_execution; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE keycloak.authentication_execution OWNER TO saimon;

--
-- Name: authentication_flow; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE keycloak.authentication_flow OWNER TO saimon;

--
-- Name: authenticator_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE keycloak.authenticator_config OWNER TO saimon;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.authenticator_config_entry OWNER TO saimon;

--
-- Name: broker_link; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE keycloak.broker_link OWNER TO saimon;

--
-- Name: client; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE keycloak.client OWNER TO saimon;

--
-- Name: client_attributes; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE keycloak.client_attributes OWNER TO saimon;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE keycloak.client_auth_flow_bindings OWNER TO saimon;

--
-- Name: client_initial_access; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE keycloak.client_initial_access OWNER TO saimon;

--
-- Name: client_node_registrations; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.client_node_registrations OWNER TO saimon;

--
-- Name: client_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE keycloak.client_scope OWNER TO saimon;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.client_scope_attributes OWNER TO saimon;

--
-- Name: client_scope_client; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE keycloak.client_scope_client OWNER TO saimon;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_scope_role_mapping OWNER TO saimon;

--
-- Name: client_session; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_session (
    id character varying(36) NOT NULL,
    client_id character varying(36),
    redirect_uri character varying(255),
    state character varying(255),
    "timestamp" integer,
    session_id character varying(36),
    auth_method character varying(255),
    realm_id character varying(255),
    auth_user_id character varying(36),
    current_action character varying(36)
);


ALTER TABLE keycloak.client_session OWNER TO saimon;

--
-- Name: client_session_auth_status; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_session_auth_status (
    authenticator character varying(36) NOT NULL,
    status integer,
    client_session character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_session_auth_status OWNER TO saimon;

--
-- Name: client_session_note; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_session_note (
    name character varying(255) NOT NULL,
    value character varying(255),
    client_session character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_session_note OWNER TO saimon;

--
-- Name: client_session_prot_mapper; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_session_prot_mapper (
    protocol_mapper_id character varying(36) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_session_prot_mapper OWNER TO saimon;

--
-- Name: client_session_role; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_session_role (
    role_id character varying(255) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_session_role OWNER TO saimon;

--
-- Name: client_user_session_note; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.client_user_session_note (
    name character varying(255) NOT NULL,
    value character varying(2048),
    client_session character varying(36) NOT NULL
);


ALTER TABLE keycloak.client_user_session_note OWNER TO saimon;

--
-- Name: component; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE keycloak.component OWNER TO saimon;

--
-- Name: component_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE keycloak.component_config OWNER TO saimon;

--
-- Name: composite_role; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE keycloak.composite_role OWNER TO saimon;

--
-- Name: credential; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE keycloak.credential OWNER TO saimon;

--
-- Name: databasechangelog; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE keycloak.databasechangelog OWNER TO saimon;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE keycloak.databasechangeloglock OWNER TO saimon;

--
-- Name: default_client_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE keycloak.default_client_scope OWNER TO saimon;

--
-- Name: event_entity; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE keycloak.event_entity OWNER TO saimon;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024)
);


ALTER TABLE keycloak.fed_user_attribute OWNER TO saimon;

--
-- Name: fed_user_consent; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE keycloak.fed_user_consent OWNER TO saimon;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.fed_user_consent_cl_scope OWNER TO saimon;

--
-- Name: fed_user_credential; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE keycloak.fed_user_credential OWNER TO saimon;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE keycloak.fed_user_group_membership OWNER TO saimon;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE keycloak.fed_user_required_action OWNER TO saimon;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE keycloak.fed_user_role_mapping OWNER TO saimon;

--
-- Name: federated_identity; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.federated_identity OWNER TO saimon;

--
-- Name: federated_user; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.federated_user OWNER TO saimon;

--
-- Name: group_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.group_attribute OWNER TO saimon;

--
-- Name: group_role_mapping; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.group_role_mapping OWNER TO saimon;

--
-- Name: identity_provider; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL
);


ALTER TABLE keycloak.identity_provider OWNER TO saimon;

--
-- Name: identity_provider_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.identity_provider_config OWNER TO saimon;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.identity_provider_mapper OWNER TO saimon;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.idp_mapper_config OWNER TO saimon;

--
-- Name: keycloak_group; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36)
);


ALTER TABLE keycloak.keycloak_group OWNER TO saimon;

--
-- Name: keycloak_role; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE keycloak.keycloak_role OWNER TO saimon;

--
-- Name: migration_model; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE keycloak.migration_model OWNER TO saimon;

--
-- Name: offline_client_session; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL
);


ALTER TABLE keycloak.offline_client_session OWNER TO saimon;

--
-- Name: offline_user_session; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL
);


ALTER TABLE keycloak.offline_user_session OWNER TO saimon;

--
-- Name: policy_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE keycloak.policy_config OWNER TO saimon;

--
-- Name: protocol_mapper; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE keycloak.protocol_mapper OWNER TO saimon;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.protocol_mapper_config OWNER TO saimon;

--
-- Name: realm; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE keycloak.realm OWNER TO saimon;

--
-- Name: realm_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE keycloak.realm_attribute OWNER TO saimon;

--
-- Name: realm_default_groups; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.realm_default_groups OWNER TO saimon;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.realm_enabled_event_types OWNER TO saimon;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.realm_events_listeners OWNER TO saimon;

--
-- Name: realm_localizations; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE keycloak.realm_localizations OWNER TO saimon;

--
-- Name: realm_required_credential; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.realm_required_credential OWNER TO saimon;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.realm_smtp_config OWNER TO saimon;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.realm_supported_locales OWNER TO saimon;

--
-- Name: redirect_uris; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.redirect_uris OWNER TO saimon;

--
-- Name: required_action_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.required_action_config OWNER TO saimon;

--
-- Name: required_action_provider; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE keycloak.required_action_provider OWNER TO saimon;

--
-- Name: resource_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.resource_attribute OWNER TO saimon;

--
-- Name: resource_policy; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.resource_policy OWNER TO saimon;

--
-- Name: resource_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.resource_scope OWNER TO saimon;

--
-- Name: resource_server; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE keycloak.resource_server OWNER TO saimon;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE keycloak.resource_server_perm_ticket OWNER TO saimon;

--
-- Name: resource_server_policy; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE keycloak.resource_server_policy OWNER TO saimon;

--
-- Name: resource_server_resource; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE keycloak.resource_server_resource OWNER TO saimon;

--
-- Name: resource_server_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE keycloak.resource_server_scope OWNER TO saimon;

--
-- Name: resource_uris; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.resource_uris OWNER TO saimon;

--
-- Name: role_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE keycloak.role_attribute OWNER TO saimon;

--
-- Name: scope_mapping; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.scope_mapping OWNER TO saimon;

--
-- Name: scope_policy; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.scope_policy OWNER TO saimon;

--
-- Name: user_attribute; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL
);


ALTER TABLE keycloak.user_attribute OWNER TO saimon;

--
-- Name: user_consent; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE keycloak.user_consent OWNER TO saimon;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.user_consent_client_scope OWNER TO saimon;

--
-- Name: user_entity; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE keycloak.user_entity OWNER TO saimon;

--
-- Name: user_federation_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.user_federation_config OWNER TO saimon;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.user_federation_mapper OWNER TO saimon;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE keycloak.user_federation_mapper_config OWNER TO saimon;

--
-- Name: user_federation_provider; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE keycloak.user_federation_provider OWNER TO saimon;

--
-- Name: user_group_membership; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.user_group_membership OWNER TO saimon;

--
-- Name: user_required_action; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE keycloak.user_required_action OWNER TO saimon;

--
-- Name: user_role_mapping; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE keycloak.user_role_mapping OWNER TO saimon;

--
-- Name: user_session; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_session (
    id character varying(36) NOT NULL,
    auth_method character varying(255),
    ip_address character varying(255),
    last_session_refresh integer,
    login_username character varying(255),
    realm_id character varying(255),
    remember_me boolean DEFAULT false NOT NULL,
    started integer,
    user_id character varying(255),
    user_session_state integer,
    broker_session_id character varying(255),
    broker_user_id character varying(255)
);


ALTER TABLE keycloak.user_session OWNER TO saimon;

--
-- Name: user_session_note; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.user_session_note (
    user_session character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(2048)
);


ALTER TABLE keycloak.user_session_note OWNER TO saimon;

--
-- Name: username_login_failure; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.username_login_failure (
    realm_id character varying(36) NOT NULL,
    username character varying(255) NOT NULL,
    failed_login_not_before integer,
    last_failure bigint,
    last_ip_failure character varying(255),
    num_failures integer
);


ALTER TABLE keycloak.username_login_failure OWNER TO saimon;

--
-- Name: web_origins; Type: TABLE; Schema: keycloak; Owner: saimon
--

CREATE TABLE keycloak.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE keycloak.web_origins OWNER TO saimon;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
cdc488cb-ec59-45e7-9300-66fac680d16a	\N	auth-cookie	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2d12047a-f1c7-4bab-8fa0-a8115d761ef8	2	10	f	\N	\N
5234b480-01e6-483e-8729-52829584d1fe	\N	auth-spnego	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2d12047a-f1c7-4bab-8fa0-a8115d761ef8	3	20	f	\N	\N
195c0f32-7a5a-4025-a232-9b130f6d613f	\N	identity-provider-redirector	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2d12047a-f1c7-4bab-8fa0-a8115d761ef8	2	25	f	\N	\N
49cca163-238f-4850-abd7-e97d17ddd022	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2d12047a-f1c7-4bab-8fa0-a8115d761ef8	2	30	t	02ce521f-ed64-4e2a-95c3-9d073e3873f3	\N
7f5dd822-04bd-411e-8e60-1cc0d5f67713	\N	auth-username-password-form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	02ce521f-ed64-4e2a-95c3-9d073e3873f3	0	10	f	\N	\N
592dc9f5-e777-4de7-9fae-1c4b5d5effcc	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	02ce521f-ed64-4e2a-95c3-9d073e3873f3	1	20	t	4ecd6cc4-fc45-4af2-8a95-f38ce5d0aa38	\N
55274e5c-4dac-497e-9576-3cf5946141c8	\N	conditional-user-configured	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	4ecd6cc4-fc45-4af2-8a95-f38ce5d0aa38	0	10	f	\N	\N
fd401b6f-ef5f-4c2f-acc6-ea5fcea218f1	\N	auth-otp-form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	4ecd6cc4-fc45-4af2-8a95-f38ce5d0aa38	0	20	f	\N	\N
ad423fb2-3488-4ee2-a4ab-77a27a83dbba	\N	direct-grant-validate-username	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	78eba7ad-34c6-4fdb-b8b8-8e738b613ab5	0	10	f	\N	\N
70828342-913c-45c7-84fa-50f59e42b837	\N	direct-grant-validate-password	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	78eba7ad-34c6-4fdb-b8b8-8e738b613ab5	0	20	f	\N	\N
5c5126c8-432a-43f6-8403-cb808eac1581	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	78eba7ad-34c6-4fdb-b8b8-8e738b613ab5	1	30	t	554b9913-17e6-44e1-9f79-e0b4ba34e1bb	\N
296d1fd0-36ef-49f0-aadb-87b4d545e8fb	\N	conditional-user-configured	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	554b9913-17e6-44e1-9f79-e0b4ba34e1bb	0	10	f	\N	\N
f935fab2-46ab-40a3-879e-5d89fd69877e	\N	direct-grant-validate-otp	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	554b9913-17e6-44e1-9f79-e0b4ba34e1bb	0	20	f	\N	\N
b15c344e-622d-4671-8c3d-a71d7313e251	\N	registration-page-form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	98e4eb4d-742b-457e-a74c-ac350d2bc602	0	10	t	c5714d84-e6bf-4df4-912c-e5576021178d	\N
065ef4fa-f07e-46bd-a12a-8a66a9285288	\N	registration-user-creation	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c5714d84-e6bf-4df4-912c-e5576021178d	0	20	f	\N	\N
5e1922e5-e956-42a7-ad3f-e6bbaf314dea	\N	registration-password-action	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c5714d84-e6bf-4df4-912c-e5576021178d	0	50	f	\N	\N
5b0de9c8-853e-4b6e-890e-2faa7c664db2	\N	registration-recaptcha-action	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c5714d84-e6bf-4df4-912c-e5576021178d	3	60	f	\N	\N
9d01460f-26a0-4eda-892e-755fa55b5ec2	\N	registration-terms-and-conditions	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c5714d84-e6bf-4df4-912c-e5576021178d	3	70	f	\N	\N
002a587a-d8a1-41f1-9867-738c16cebe0d	\N	reset-credentials-choose-user	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	43bb1bfb-ee38-40f3-af0b-069781ca98f3	0	10	f	\N	\N
e48d3376-5563-485d-8387-c5d25e067f4a	\N	reset-credential-email	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	43bb1bfb-ee38-40f3-af0b-069781ca98f3	0	20	f	\N	\N
199cd4d6-b2cd-45a8-8797-2a8a1b131279	\N	reset-password	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	43bb1bfb-ee38-40f3-af0b-069781ca98f3	0	30	f	\N	\N
d8bd249b-dac7-4f42-8cf0-9f6806ffec82	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	43bb1bfb-ee38-40f3-af0b-069781ca98f3	1	40	t	399d40df-5304-4a27-84b6-cae91940fff6	\N
b4de8638-a5e0-4a1a-ac7e-e03426638bc5	\N	conditional-user-configured	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	399d40df-5304-4a27-84b6-cae91940fff6	0	10	f	\N	\N
45d6f44d-1ad1-49cd-ae4e-2f751012c01f	\N	reset-otp	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	399d40df-5304-4a27-84b6-cae91940fff6	0	20	f	\N	\N
dc85cdbb-53c6-4fa1-bd51-38fbd3e5822e	\N	client-secret	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	2	10	f	\N	\N
69d477d7-9c20-4b54-933a-f1732fe768bb	\N	client-jwt	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	2	20	f	\N	\N
275f8bae-0622-491f-80f4-c31f256b20e5	\N	client-secret-jwt	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	2	30	f	\N	\N
93480be6-dfe1-4aa2-8f71-96f7d2472b6e	\N	client-x509	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	2	40	f	\N	\N
d559a471-01c7-4f7b-a362-129dc16a803b	\N	idp-review-profile	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	00f89eeb-0465-4c96-8592-258c5fc05db7	0	10	f	\N	33995cba-6ff3-438c-98b3-fa8279d25f3c
98cbedd3-8495-4dda-94af-97c04155fede	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	00f89eeb-0465-4c96-8592-258c5fc05db7	0	20	t	de32dd79-0598-4597-912c-2d5e66f9534e	\N
24249a60-de51-424d-a86c-0caa85b33dde	\N	idp-create-user-if-unique	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	de32dd79-0598-4597-912c-2d5e66f9534e	2	10	f	\N	cfe7bb61-067a-40e9-b6c9-2695256907f0
54aafd28-b5dc-4e74-a5ab-052691dd97f5	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	de32dd79-0598-4597-912c-2d5e66f9534e	2	20	t	51617ec4-74a2-4e7d-b2ad-267b57764319	\N
53dc3a65-7506-415b-99e3-44e1691469c9	\N	idp-confirm-link	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	51617ec4-74a2-4e7d-b2ad-267b57764319	0	10	f	\N	\N
7c923429-77f5-4a3a-9bcc-604be6d77b6d	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	51617ec4-74a2-4e7d-b2ad-267b57764319	0	20	t	b522d5fc-e69d-4755-9b8f-4f3c77e22065	\N
a1f25154-3838-40df-b6a2-cba0f2982615	\N	idp-email-verification	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	b522d5fc-e69d-4755-9b8f-4f3c77e22065	2	10	f	\N	\N
d0b09244-06bc-4414-bf68-74d4513c51ef	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	b522d5fc-e69d-4755-9b8f-4f3c77e22065	2	20	t	365a9a54-2cc5-4abe-96be-3f41612b2d34	\N
2436fb5d-7c97-4f93-8919-1658585943df	\N	idp-username-password-form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	365a9a54-2cc5-4abe-96be-3f41612b2d34	0	10	f	\N	\N
b6edbe54-c4f3-4a98-b0e0-11eb9d7e2aa2	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	365a9a54-2cc5-4abe-96be-3f41612b2d34	1	20	t	112548f5-b203-413e-a3f8-ef7cb9c79afd	\N
4ec4a43a-bd3b-4721-9d8b-9394370acaae	\N	conditional-user-configured	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	112548f5-b203-413e-a3f8-ef7cb9c79afd	0	10	f	\N	\N
91d8f993-8114-470e-8587-7cc3fc3c7a79	\N	auth-otp-form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	112548f5-b203-413e-a3f8-ef7cb9c79afd	0	20	f	\N	\N
3bb49218-d92b-47f8-bb82-de8b54e4c904	\N	http-basic-authenticator	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	93e34677-db01-4bdb-8e4c-0b136e0960b1	0	10	f	\N	\N
2248d608-3007-4242-b159-7bdad83e3153	\N	docker-http-basic-authenticator	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	37795034-59ea-4f5d-b11f-ff4712ee13d1	0	10	f	\N	\N
8499b6a2-482e-41d6-b9d3-cd4dadf1f3cd	\N	auth-cookie	a8889a90-1082-4a86-b119-30a5eb902930	e4103d92-fe3b-4b91-81d2-beee05354144	2	10	f	\N	\N
a5700b42-8e26-45c5-947a-e7b44a210b1d	\N	auth-spnego	a8889a90-1082-4a86-b119-30a5eb902930	e4103d92-fe3b-4b91-81d2-beee05354144	3	20	f	\N	\N
2a5f04fa-ca80-4fab-87e6-5176568e7a6a	\N	identity-provider-redirector	a8889a90-1082-4a86-b119-30a5eb902930	e4103d92-fe3b-4b91-81d2-beee05354144	2	25	f	\N	\N
d2ab2ef1-45e1-4bce-a318-e2f8a31234c0	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	e4103d92-fe3b-4b91-81d2-beee05354144	2	30	t	226deb1c-4d34-4597-b1f9-26ebe85aa475	\N
0e9700b6-0b02-4279-8818-8eaf224ec38e	\N	auth-username-password-form	a8889a90-1082-4a86-b119-30a5eb902930	226deb1c-4d34-4597-b1f9-26ebe85aa475	0	10	f	\N	\N
f527977b-3820-4d1e-ab3a-f14084ee3520	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	226deb1c-4d34-4597-b1f9-26ebe85aa475	1	20	t	8a596f43-fff6-45ba-bea7-728afcba4379	\N
213b441e-df02-4572-b698-01ab15b03429	\N	conditional-user-configured	a8889a90-1082-4a86-b119-30a5eb902930	8a596f43-fff6-45ba-bea7-728afcba4379	0	10	f	\N	\N
d71f9678-d83f-4f71-902c-d0cce36291d1	\N	auth-otp-form	a8889a90-1082-4a86-b119-30a5eb902930	8a596f43-fff6-45ba-bea7-728afcba4379	0	20	f	\N	\N
67640206-cbdb-4620-b08b-e7f232456679	\N	direct-grant-validate-username	a8889a90-1082-4a86-b119-30a5eb902930	9fcab498-da29-4c7a-b727-927438341bfe	0	10	f	\N	\N
92967c07-e9c3-4c11-87ba-addee809f88b	\N	direct-grant-validate-password	a8889a90-1082-4a86-b119-30a5eb902930	9fcab498-da29-4c7a-b727-927438341bfe	0	20	f	\N	\N
fea76ff2-36ce-42cf-9f1f-bbbe0deb35ab	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	9fcab498-da29-4c7a-b727-927438341bfe	1	30	t	f0c97622-8c55-4618-b7d1-ed1b0e08b662	\N
b8c86e98-ad75-4076-bd1f-0b3cb4b643fe	\N	conditional-user-configured	a8889a90-1082-4a86-b119-30a5eb902930	f0c97622-8c55-4618-b7d1-ed1b0e08b662	0	10	f	\N	\N
379a767d-cd7d-4e7c-9fcc-6f991efdb463	\N	direct-grant-validate-otp	a8889a90-1082-4a86-b119-30a5eb902930	f0c97622-8c55-4618-b7d1-ed1b0e08b662	0	20	f	\N	\N
206c1ae9-e8fa-4d9c-a930-8051b1dafca5	\N	registration-page-form	a8889a90-1082-4a86-b119-30a5eb902930	d67ce31b-20ff-4560-ac7e-ccd3ba9421db	0	10	t	f62fae59-3ecd-4787-9b27-a22c5f020e66	\N
58ebedab-b96a-46a2-9fe7-9b2aef8656ae	\N	registration-user-creation	a8889a90-1082-4a86-b119-30a5eb902930	f62fae59-3ecd-4787-9b27-a22c5f020e66	0	20	f	\N	\N
e759ab8f-9f66-4370-b2c4-858e54e56dd4	\N	registration-password-action	a8889a90-1082-4a86-b119-30a5eb902930	f62fae59-3ecd-4787-9b27-a22c5f020e66	0	50	f	\N	\N
4f7de017-260b-41d3-96b3-2b3b184cbf4e	\N	registration-recaptcha-action	a8889a90-1082-4a86-b119-30a5eb902930	f62fae59-3ecd-4787-9b27-a22c5f020e66	3	60	f	\N	\N
4be8585c-eb67-47fa-a755-83b59ef30100	\N	reset-credentials-choose-user	a8889a90-1082-4a86-b119-30a5eb902930	5729bc24-e294-48bd-90fa-5b741f2c4194	0	10	f	\N	\N
927e6685-022d-4600-bea5-119f477f53d2	\N	reset-credential-email	a8889a90-1082-4a86-b119-30a5eb902930	5729bc24-e294-48bd-90fa-5b741f2c4194	0	20	f	\N	\N
14815704-d3dd-4fc7-9a7b-7e91316a9e17	\N	reset-password	a8889a90-1082-4a86-b119-30a5eb902930	5729bc24-e294-48bd-90fa-5b741f2c4194	0	30	f	\N	\N
df1394f9-7579-42fd-90bf-b366c80198a2	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	5729bc24-e294-48bd-90fa-5b741f2c4194	1	40	t	dda28d1b-594d-41c3-9002-4bbf679bd94d	\N
9c64df52-92a6-4261-8451-ba3a915a6e9c	\N	conditional-user-configured	a8889a90-1082-4a86-b119-30a5eb902930	dda28d1b-594d-41c3-9002-4bbf679bd94d	0	10	f	\N	\N
2751cc98-2b85-4b77-b538-b8965f26509b	\N	reset-otp	a8889a90-1082-4a86-b119-30a5eb902930	dda28d1b-594d-41c3-9002-4bbf679bd94d	0	20	f	\N	\N
f74a115e-0947-411c-958f-aba14c2af9a6	\N	client-secret	a8889a90-1082-4a86-b119-30a5eb902930	92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	2	10	f	\N	\N
d5f649e9-6a38-46a4-bb7d-07b453f7936f	\N	client-jwt	a8889a90-1082-4a86-b119-30a5eb902930	92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	2	20	f	\N	\N
87bb9246-7dd5-4ce0-b3ce-1834107cb637	\N	client-secret-jwt	a8889a90-1082-4a86-b119-30a5eb902930	92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	2	30	f	\N	\N
50955580-0d0b-45f8-b3ab-734dc30fab66	\N	client-x509	a8889a90-1082-4a86-b119-30a5eb902930	92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	2	40	f	\N	\N
7339d0d3-8780-48c7-a7c6-3de2d74572a2	\N	idp-review-profile	a8889a90-1082-4a86-b119-30a5eb902930	db725d8d-e0c1-47a4-99a6-b46d3aeea410	0	10	f	\N	b4ad80e7-a1e4-4011-ac13-4477f42f24a9
c58751c3-3590-429d-b579-a796bcab01fd	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	db725d8d-e0c1-47a4-99a6-b46d3aeea410	0	20	t	1151c081-affc-4bb8-be12-7bc2ec9e0e71	\N
9eb835e2-3d0c-4f82-a693-f1f531444c62	\N	idp-create-user-if-unique	a8889a90-1082-4a86-b119-30a5eb902930	1151c081-affc-4bb8-be12-7bc2ec9e0e71	2	10	f	\N	12d6b9b4-aa45-4792-b956-f65eb792d399
6a975c14-f941-42d5-b10c-8360bd1aeeb4	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	1151c081-affc-4bb8-be12-7bc2ec9e0e71	2	20	t	78082eee-2c51-4e28-8ebe-8091e7b92dc7	\N
80a00102-aa6e-4942-ba0f-3046f48f98ad	\N	idp-confirm-link	a8889a90-1082-4a86-b119-30a5eb902930	78082eee-2c51-4e28-8ebe-8091e7b92dc7	0	10	f	\N	\N
35af1f35-b31e-4903-9f71-e189cd167cc9	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	78082eee-2c51-4e28-8ebe-8091e7b92dc7	0	20	t	2edcefa3-e312-48b6-b944-cf6faa3d16e2	\N
f917cb0a-a241-4774-91a3-d2de55b475f7	\N	idp-email-verification	a8889a90-1082-4a86-b119-30a5eb902930	2edcefa3-e312-48b6-b944-cf6faa3d16e2	2	10	f	\N	\N
6862f65b-d851-4a87-9814-e22f5ce54469	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	2edcefa3-e312-48b6-b944-cf6faa3d16e2	2	20	t	b16bc743-d4eb-43bc-8546-450352f302a5	\N
6face1be-1296-44df-a00d-e4bb027a75f9	\N	idp-username-password-form	a8889a90-1082-4a86-b119-30a5eb902930	b16bc743-d4eb-43bc-8546-450352f302a5	0	10	f	\N	\N
f4a950b7-0816-4f25-86a8-72bc36fd4c22	\N	\N	a8889a90-1082-4a86-b119-30a5eb902930	b16bc743-d4eb-43bc-8546-450352f302a5	1	20	t	9c277a60-6191-4147-90df-4077c4c829fb	\N
2351c893-8ffc-4b85-8421-0a33e7cd3f8d	\N	conditional-user-configured	a8889a90-1082-4a86-b119-30a5eb902930	9c277a60-6191-4147-90df-4077c4c829fb	0	10	f	\N	\N
8cd3b1fc-ed2f-4c6e-8629-464ca224a644	\N	auth-otp-form	a8889a90-1082-4a86-b119-30a5eb902930	9c277a60-6191-4147-90df-4077c4c829fb	0	20	f	\N	\N
b76b782f-9725-4a0a-af11-f2a931a5388f	\N	http-basic-authenticator	a8889a90-1082-4a86-b119-30a5eb902930	f1a9547e-8985-4750-92f8-742d855b0fe6	0	10	f	\N	\N
36040ba5-75dc-4042-976f-1d58dbbfc97c	\N	docker-http-basic-authenticator	a8889a90-1082-4a86-b119-30a5eb902930	c57124a2-1771-4de1-9630-71d4595db326	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
2d12047a-f1c7-4bab-8fa0-a8115d761ef8	browser	browser based authentication	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
02ce521f-ed64-4e2a-95c3-9d073e3873f3	forms	Username, password, otp and other auth forms.	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
4ecd6cc4-fc45-4af2-8a95-f38ce5d0aa38	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
78eba7ad-34c6-4fdb-b8b8-8e738b613ab5	direct grant	OpenID Connect Resource Owner Grant	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
554b9913-17e6-44e1-9f79-e0b4ba34e1bb	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
98e4eb4d-742b-457e-a74c-ac350d2bc602	registration	registration flow	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
c5714d84-e6bf-4df4-912c-e5576021178d	registration form	registration form	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	form-flow	f	t
43bb1bfb-ee38-40f3-af0b-069781ca98f3	reset credentials	Reset credentials for a user if they forgot their password or something	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
399d40df-5304-4a27-84b6-cae91940fff6	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	clients	Base authentication for clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	client-flow	t	t
00f89eeb-0465-4c96-8592-258c5fc05db7	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
de32dd79-0598-4597-912c-2d5e66f9534e	User creation or linking	Flow for the existing/non-existing user alternatives	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
51617ec4-74a2-4e7d-b2ad-267b57764319	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
b522d5fc-e69d-4755-9b8f-4f3c77e22065	Account verification options	Method with which to verity the existing account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
365a9a54-2cc5-4abe-96be-3f41612b2d34	Verify Existing Account by Re-authentication	Reauthentication of existing account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
112548f5-b203-413e-a3f8-ef7cb9c79afd	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	f	t
93e34677-db01-4bdb-8e4c-0b136e0960b1	saml ecp	SAML ECP Profile Authentication Flow	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
37795034-59ea-4f5d-b11f-ff4712ee13d1	docker auth	Used by Docker clients to authenticate against the IDP	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	basic-flow	t	t
e4103d92-fe3b-4b91-81d2-beee05354144	browser	browser based authentication	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
226deb1c-4d34-4597-b1f9-26ebe85aa475	forms	Username, password, otp and other auth forms.	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
8a596f43-fff6-45ba-bea7-728afcba4379	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
9fcab498-da29-4c7a-b727-927438341bfe	direct grant	OpenID Connect Resource Owner Grant	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
f0c97622-8c55-4618-b7d1-ed1b0e08b662	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
d67ce31b-20ff-4560-ac7e-ccd3ba9421db	registration	registration flow	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
f62fae59-3ecd-4787-9b27-a22c5f020e66	registration form	registration form	a8889a90-1082-4a86-b119-30a5eb902930	form-flow	f	t
5729bc24-e294-48bd-90fa-5b741f2c4194	reset credentials	Reset credentials for a user if they forgot their password or something	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
dda28d1b-594d-41c3-9002-4bbf679bd94d	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	clients	Base authentication for clients	a8889a90-1082-4a86-b119-30a5eb902930	client-flow	t	t
db725d8d-e0c1-47a4-99a6-b46d3aeea410	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
1151c081-affc-4bb8-be12-7bc2ec9e0e71	User creation or linking	Flow for the existing/non-existing user alternatives	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
78082eee-2c51-4e28-8ebe-8091e7b92dc7	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
2edcefa3-e312-48b6-b944-cf6faa3d16e2	Account verification options	Method with which to verity the existing account	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
b16bc743-d4eb-43bc-8546-450352f302a5	Verify Existing Account by Re-authentication	Reauthentication of existing account	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
9c277a60-6191-4147-90df-4077c4c829fb	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	f	t
f1a9547e-8985-4750-92f8-742d855b0fe6	saml ecp	SAML ECP Profile Authentication Flow	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
c57124a2-1771-4de1-9630-71d4595db326	docker auth	Used by Docker clients to authenticate against the IDP	a8889a90-1082-4a86-b119-30a5eb902930	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.authenticator_config (id, alias, realm_id) FROM stdin;
33995cba-6ff3-438c-98b3-fa8279d25f3c	review profile config	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b
cfe7bb61-067a-40e9-b6c9-2695256907f0	create unique user config	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b
b4ad80e7-a1e4-4011-ac13-4477f42f24a9	review profile config	a8889a90-1082-4a86-b119-30a5eb902930
12d6b9b4-aa45-4792-b956-f65eb792d399	create unique user config	a8889a90-1082-4a86-b119-30a5eb902930
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
33995cba-6ff3-438c-98b3-fa8279d25f3c	missing	update.profile.on.first.login
cfe7bb61-067a-40e9-b6c9-2695256907f0	false	require.password.update.after.registration
12d6b9b4-aa45-4792-b956-f65eb792d399	false	require.password.update.after.registration
b4ad80e7-a1e4-4011-ac13-4477f42f24a9	missing	update.profile.on.first.login
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
65689d62-dc02-448a-b9d9-03ef1cc13408	t	f	master-realm	0	f	\N	\N	t	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
9601986b-5fb5-4321-8bf5-fae31332ac78	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
c16b6f5e-b668-47ea-867b-c2c7890d87b0	t	f	broker	0	f	\N	\N	t	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
52386ca8-8246-4307-9a08-f59db387c7df	t	f	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
2d329ef4-36b7-476f-807b-76146a967cff	t	f	admin-cli	0	t	\N	\N	f	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
d68566c6-8172-4bb1-aa7b-26f1df1c5346	t	f	account-console	0	t	\N	/realms/Ciudadano/account/	f	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	f	realm-management	0	f	\N	\N	t	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
cf17bdb0-f988-4363-b0c9-4a3af34411ff	t	f	broker	0	f	\N	\N	t	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
f07fae5e-cd31-437b-9cdb-8892c673d027	t	t	api-ciudadano-consciente	0	t	\N	http://localhost:3705/	f		f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	-1	t	f	ciudadano-consciente-backend	f	client-secret		Backend de la API Ciudadano Consciente	\N	t	f	t	f
5abc18c6-e21c-4029-bd4d-2b1829184881	t	f	admin-cli	0	t	\N	\N	f	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
9e460505-75f0-4a8d-8872-363be35570f0	t	f	Ciudadano-realm	0	f	\N	\N	t	\N	f	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	0	f	f	ciudadano-consciente Realm	f	client-secret	\N	\N	\N	t	f	f	f
782ab103-aafa-4993-90f2-05b1cdb47a9e	t	f	security-admin-console	0	t	\N	/admin/Ciudadano/console/	f	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
fe9505a3-aa97-4b00-b592-a91755146d6b	t	f	account	0	t	\N	/realms/Ciudadano/account/	f	\N	f	a8889a90-1082-4a86-b119-30a5eb902930	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_attributes (client_id, name, value) FROM stdin;
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	post.logout.redirect.uris	+
9601986b-5fb5-4321-8bf5-fae31332ac78	post.logout.redirect.uris	+
9601986b-5fb5-4321-8bf5-fae31332ac78	pkce.code.challenge.method	S256
52386ca8-8246-4307-9a08-f59db387c7df	post.logout.redirect.uris	+
52386ca8-8246-4307-9a08-f59db387c7df	pkce.code.challenge.method	S256
fe9505a3-aa97-4b00-b592-a91755146d6b	post.logout.redirect.uris	+
d68566c6-8172-4bb1-aa7b-26f1df1c5346	post.logout.redirect.uris	+
d68566c6-8172-4bb1-aa7b-26f1df1c5346	pkce.code.challenge.method	S256
782ab103-aafa-4993-90f2-05b1cdb47a9e	post.logout.redirect.uris	+
782ab103-aafa-4993-90f2-05b1cdb47a9e	pkce.code.challenge.method	S256
f07fae5e-cd31-437b-9cdb-8892c673d027	client.secret.creation.time	1706055386
f07fae5e-cd31-437b-9cdb-8892c673d027	oauth2.device.authorization.grant.enabled	false
f07fae5e-cd31-437b-9cdb-8892c673d027	oidc.ciba.grant.enabled	false
f07fae5e-cd31-437b-9cdb-8892c673d027	backchannel.logout.session.required	true
f07fae5e-cd31-437b-9cdb-8892c673d027	backchannel.logout.revoke.offline.tokens	false
f07fae5e-cd31-437b-9cdb-8892c673d027	display.on.consent.screen	false
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_scope (id, name, realm_id, description, protocol) FROM stdin;
7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	offline_access	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect built-in scope: offline_access	openid-connect
1bf2757c-511e-47e7-9163-812e33e53388	role_list	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	SAML role list	saml
498d649e-0fa2-4151-b9f8-99a88d123a1b	profile	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect built-in scope: profile	openid-connect
98b97d33-bb00-4670-baf5-b31fd9d07979	email	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect built-in scope: email	openid-connect
66265ecf-1071-4cf7-a47c-30a95449366b	address	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect built-in scope: address	openid-connect
08e82c07-d990-46d5-a520-90c23f73a65b	phone	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect built-in scope: phone	openid-connect
c55315b3-af64-48b8-8249-506ec504279e	roles	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect scope for add user roles to the access token	openid-connect
940da7b1-fa8c-4efb-85eb-e501bade836c	web-origins	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect scope for add allowed web origins to the access token	openid-connect
f700edc9-6f03-45f6-8337-7d952327bfb0	microprofile-jwt	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	Microprofile - JWT built-in scope	openid-connect
624dda8a-a872-4ec5-9c7f-b7d611afe5f1	acr	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
8dce157d-6e04-47f9-a2f1-454354eb0c38	offline_access	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect built-in scope: offline_access	openid-connect
f6fcda75-9013-49dc-8709-942cae10193c	role_list	a8889a90-1082-4a86-b119-30a5eb902930	SAML role list	saml
4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	profile	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect built-in scope: profile	openid-connect
78298940-83da-4217-b6db-6ded30249986	email	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect built-in scope: email	openid-connect
224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	address	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect built-in scope: address	openid-connect
d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	phone	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect built-in scope: phone	openid-connect
35686e6c-6fed-44d1-b464-29ba0835dfa7	roles	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect scope for add user roles to the access token	openid-connect
06c8e679-b22c-40e7-a162-4647618940b5	web-origins	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect scope for add allowed web origins to the access token	openid-connect
23591340-67c1-4838-982d-63786c93f989	microprofile-jwt	a8889a90-1082-4a86-b119-30a5eb902930	Microprofile - JWT built-in scope	openid-connect
60c7d281-0d13-4a7f-9eab-54eb511b8122	acr	a8889a90-1082-4a86-b119-30a5eb902930	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_scope_attributes (scope_id, value, name) FROM stdin;
7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	true	display.on.consent.screen
7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	${offlineAccessScopeConsentText}	consent.screen.text
1bf2757c-511e-47e7-9163-812e33e53388	true	display.on.consent.screen
1bf2757c-511e-47e7-9163-812e33e53388	${samlRoleListScopeConsentText}	consent.screen.text
498d649e-0fa2-4151-b9f8-99a88d123a1b	true	display.on.consent.screen
498d649e-0fa2-4151-b9f8-99a88d123a1b	${profileScopeConsentText}	consent.screen.text
498d649e-0fa2-4151-b9f8-99a88d123a1b	true	include.in.token.scope
98b97d33-bb00-4670-baf5-b31fd9d07979	true	display.on.consent.screen
98b97d33-bb00-4670-baf5-b31fd9d07979	${emailScopeConsentText}	consent.screen.text
98b97d33-bb00-4670-baf5-b31fd9d07979	true	include.in.token.scope
66265ecf-1071-4cf7-a47c-30a95449366b	true	display.on.consent.screen
66265ecf-1071-4cf7-a47c-30a95449366b	${addressScopeConsentText}	consent.screen.text
66265ecf-1071-4cf7-a47c-30a95449366b	true	include.in.token.scope
08e82c07-d990-46d5-a520-90c23f73a65b	true	display.on.consent.screen
08e82c07-d990-46d5-a520-90c23f73a65b	${phoneScopeConsentText}	consent.screen.text
08e82c07-d990-46d5-a520-90c23f73a65b	true	include.in.token.scope
c55315b3-af64-48b8-8249-506ec504279e	true	display.on.consent.screen
c55315b3-af64-48b8-8249-506ec504279e	${rolesScopeConsentText}	consent.screen.text
c55315b3-af64-48b8-8249-506ec504279e	false	include.in.token.scope
940da7b1-fa8c-4efb-85eb-e501bade836c	false	display.on.consent.screen
940da7b1-fa8c-4efb-85eb-e501bade836c		consent.screen.text
940da7b1-fa8c-4efb-85eb-e501bade836c	false	include.in.token.scope
f700edc9-6f03-45f6-8337-7d952327bfb0	false	display.on.consent.screen
f700edc9-6f03-45f6-8337-7d952327bfb0	true	include.in.token.scope
624dda8a-a872-4ec5-9c7f-b7d611afe5f1	false	display.on.consent.screen
624dda8a-a872-4ec5-9c7f-b7d611afe5f1	false	include.in.token.scope
8dce157d-6e04-47f9-a2f1-454354eb0c38	true	display.on.consent.screen
8dce157d-6e04-47f9-a2f1-454354eb0c38	${offlineAccessScopeConsentText}	consent.screen.text
f6fcda75-9013-49dc-8709-942cae10193c	true	display.on.consent.screen
f6fcda75-9013-49dc-8709-942cae10193c	${samlRoleListScopeConsentText}	consent.screen.text
4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	true	display.on.consent.screen
4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	${profileScopeConsentText}	consent.screen.text
4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	true	include.in.token.scope
78298940-83da-4217-b6db-6ded30249986	true	display.on.consent.screen
78298940-83da-4217-b6db-6ded30249986	${emailScopeConsentText}	consent.screen.text
78298940-83da-4217-b6db-6ded30249986	true	include.in.token.scope
224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	true	display.on.consent.screen
224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	${addressScopeConsentText}	consent.screen.text
224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	true	include.in.token.scope
d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	true	display.on.consent.screen
d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	${phoneScopeConsentText}	consent.screen.text
d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	true	include.in.token.scope
35686e6c-6fed-44d1-b464-29ba0835dfa7	true	display.on.consent.screen
35686e6c-6fed-44d1-b464-29ba0835dfa7	${rolesScopeConsentText}	consent.screen.text
35686e6c-6fed-44d1-b464-29ba0835dfa7	false	include.in.token.scope
06c8e679-b22c-40e7-a162-4647618940b5	false	display.on.consent.screen
06c8e679-b22c-40e7-a162-4647618940b5		consent.screen.text
06c8e679-b22c-40e7-a162-4647618940b5	false	include.in.token.scope
23591340-67c1-4838-982d-63786c93f989	false	display.on.consent.screen
23591340-67c1-4838-982d-63786c93f989	true	include.in.token.scope
60c7d281-0d13-4a7f-9eab-54eb511b8122	false	display.on.consent.screen
60c7d281-0d13-4a7f-9eab-54eb511b8122	false	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	98b97d33-bb00-4670-baf5-b31fd9d07979	t
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	940da7b1-fa8c-4efb-85eb-e501bade836c	t
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	c55315b3-af64-48b8-8249-506ec504279e	t
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	66265ecf-1071-4cf7-a47c-30a95449366b	f
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	08e82c07-d990-46d5-a520-90c23f73a65b	f
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	f700edc9-6f03-45f6-8337-7d952327bfb0	f
9601986b-5fb5-4321-8bf5-fae31332ac78	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
9601986b-5fb5-4321-8bf5-fae31332ac78	98b97d33-bb00-4670-baf5-b31fd9d07979	t
9601986b-5fb5-4321-8bf5-fae31332ac78	940da7b1-fa8c-4efb-85eb-e501bade836c	t
9601986b-5fb5-4321-8bf5-fae31332ac78	c55315b3-af64-48b8-8249-506ec504279e	t
9601986b-5fb5-4321-8bf5-fae31332ac78	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
9601986b-5fb5-4321-8bf5-fae31332ac78	66265ecf-1071-4cf7-a47c-30a95449366b	f
9601986b-5fb5-4321-8bf5-fae31332ac78	08e82c07-d990-46d5-a520-90c23f73a65b	f
9601986b-5fb5-4321-8bf5-fae31332ac78	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
9601986b-5fb5-4321-8bf5-fae31332ac78	f700edc9-6f03-45f6-8337-7d952327bfb0	f
2d329ef4-36b7-476f-807b-76146a967cff	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
2d329ef4-36b7-476f-807b-76146a967cff	98b97d33-bb00-4670-baf5-b31fd9d07979	t
2d329ef4-36b7-476f-807b-76146a967cff	940da7b1-fa8c-4efb-85eb-e501bade836c	t
2d329ef4-36b7-476f-807b-76146a967cff	c55315b3-af64-48b8-8249-506ec504279e	t
2d329ef4-36b7-476f-807b-76146a967cff	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
2d329ef4-36b7-476f-807b-76146a967cff	66265ecf-1071-4cf7-a47c-30a95449366b	f
2d329ef4-36b7-476f-807b-76146a967cff	08e82c07-d990-46d5-a520-90c23f73a65b	f
2d329ef4-36b7-476f-807b-76146a967cff	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
2d329ef4-36b7-476f-807b-76146a967cff	f700edc9-6f03-45f6-8337-7d952327bfb0	f
c16b6f5e-b668-47ea-867b-c2c7890d87b0	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
c16b6f5e-b668-47ea-867b-c2c7890d87b0	98b97d33-bb00-4670-baf5-b31fd9d07979	t
c16b6f5e-b668-47ea-867b-c2c7890d87b0	940da7b1-fa8c-4efb-85eb-e501bade836c	t
c16b6f5e-b668-47ea-867b-c2c7890d87b0	c55315b3-af64-48b8-8249-506ec504279e	t
c16b6f5e-b668-47ea-867b-c2c7890d87b0	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
c16b6f5e-b668-47ea-867b-c2c7890d87b0	66265ecf-1071-4cf7-a47c-30a95449366b	f
c16b6f5e-b668-47ea-867b-c2c7890d87b0	08e82c07-d990-46d5-a520-90c23f73a65b	f
c16b6f5e-b668-47ea-867b-c2c7890d87b0	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
c16b6f5e-b668-47ea-867b-c2c7890d87b0	f700edc9-6f03-45f6-8337-7d952327bfb0	f
65689d62-dc02-448a-b9d9-03ef1cc13408	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
65689d62-dc02-448a-b9d9-03ef1cc13408	98b97d33-bb00-4670-baf5-b31fd9d07979	t
65689d62-dc02-448a-b9d9-03ef1cc13408	940da7b1-fa8c-4efb-85eb-e501bade836c	t
65689d62-dc02-448a-b9d9-03ef1cc13408	c55315b3-af64-48b8-8249-506ec504279e	t
65689d62-dc02-448a-b9d9-03ef1cc13408	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
65689d62-dc02-448a-b9d9-03ef1cc13408	66265ecf-1071-4cf7-a47c-30a95449366b	f
65689d62-dc02-448a-b9d9-03ef1cc13408	08e82c07-d990-46d5-a520-90c23f73a65b	f
65689d62-dc02-448a-b9d9-03ef1cc13408	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
65689d62-dc02-448a-b9d9-03ef1cc13408	f700edc9-6f03-45f6-8337-7d952327bfb0	f
52386ca8-8246-4307-9a08-f59db387c7df	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
52386ca8-8246-4307-9a08-f59db387c7df	98b97d33-bb00-4670-baf5-b31fd9d07979	t
52386ca8-8246-4307-9a08-f59db387c7df	940da7b1-fa8c-4efb-85eb-e501bade836c	t
52386ca8-8246-4307-9a08-f59db387c7df	c55315b3-af64-48b8-8249-506ec504279e	t
52386ca8-8246-4307-9a08-f59db387c7df	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
52386ca8-8246-4307-9a08-f59db387c7df	66265ecf-1071-4cf7-a47c-30a95449366b	f
52386ca8-8246-4307-9a08-f59db387c7df	08e82c07-d990-46d5-a520-90c23f73a65b	f
52386ca8-8246-4307-9a08-f59db387c7df	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
52386ca8-8246-4307-9a08-f59db387c7df	f700edc9-6f03-45f6-8337-7d952327bfb0	f
fe9505a3-aa97-4b00-b592-a91755146d6b	78298940-83da-4217-b6db-6ded30249986	t
fe9505a3-aa97-4b00-b592-a91755146d6b	06c8e679-b22c-40e7-a162-4647618940b5	t
fe9505a3-aa97-4b00-b592-a91755146d6b	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
fe9505a3-aa97-4b00-b592-a91755146d6b	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
fe9505a3-aa97-4b00-b592-a91755146d6b	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
fe9505a3-aa97-4b00-b592-a91755146d6b	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
fe9505a3-aa97-4b00-b592-a91755146d6b	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
fe9505a3-aa97-4b00-b592-a91755146d6b	23591340-67c1-4838-982d-63786c93f989	f
fe9505a3-aa97-4b00-b592-a91755146d6b	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
d68566c6-8172-4bb1-aa7b-26f1df1c5346	78298940-83da-4217-b6db-6ded30249986	t
d68566c6-8172-4bb1-aa7b-26f1df1c5346	06c8e679-b22c-40e7-a162-4647618940b5	t
d68566c6-8172-4bb1-aa7b-26f1df1c5346	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
d68566c6-8172-4bb1-aa7b-26f1df1c5346	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
d68566c6-8172-4bb1-aa7b-26f1df1c5346	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
d68566c6-8172-4bb1-aa7b-26f1df1c5346	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
d68566c6-8172-4bb1-aa7b-26f1df1c5346	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
d68566c6-8172-4bb1-aa7b-26f1df1c5346	23591340-67c1-4838-982d-63786c93f989	f
d68566c6-8172-4bb1-aa7b-26f1df1c5346	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
5abc18c6-e21c-4029-bd4d-2b1829184881	78298940-83da-4217-b6db-6ded30249986	t
5abc18c6-e21c-4029-bd4d-2b1829184881	06c8e679-b22c-40e7-a162-4647618940b5	t
5abc18c6-e21c-4029-bd4d-2b1829184881	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
5abc18c6-e21c-4029-bd4d-2b1829184881	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
5abc18c6-e21c-4029-bd4d-2b1829184881	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
5abc18c6-e21c-4029-bd4d-2b1829184881	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
5abc18c6-e21c-4029-bd4d-2b1829184881	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
5abc18c6-e21c-4029-bd4d-2b1829184881	23591340-67c1-4838-982d-63786c93f989	f
5abc18c6-e21c-4029-bd4d-2b1829184881	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
cf17bdb0-f988-4363-b0c9-4a3af34411ff	78298940-83da-4217-b6db-6ded30249986	t
cf17bdb0-f988-4363-b0c9-4a3af34411ff	06c8e679-b22c-40e7-a162-4647618940b5	t
cf17bdb0-f988-4363-b0c9-4a3af34411ff	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
cf17bdb0-f988-4363-b0c9-4a3af34411ff	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
cf17bdb0-f988-4363-b0c9-4a3af34411ff	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
cf17bdb0-f988-4363-b0c9-4a3af34411ff	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
cf17bdb0-f988-4363-b0c9-4a3af34411ff	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
cf17bdb0-f988-4363-b0c9-4a3af34411ff	23591340-67c1-4838-982d-63786c93f989	f
cf17bdb0-f988-4363-b0c9-4a3af34411ff	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	78298940-83da-4217-b6db-6ded30249986	t
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	06c8e679-b22c-40e7-a162-4647618940b5	t
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	23591340-67c1-4838-982d-63786c93f989	f
ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
782ab103-aafa-4993-90f2-05b1cdb47a9e	78298940-83da-4217-b6db-6ded30249986	t
782ab103-aafa-4993-90f2-05b1cdb47a9e	06c8e679-b22c-40e7-a162-4647618940b5	t
782ab103-aafa-4993-90f2-05b1cdb47a9e	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
782ab103-aafa-4993-90f2-05b1cdb47a9e	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
782ab103-aafa-4993-90f2-05b1cdb47a9e	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
782ab103-aafa-4993-90f2-05b1cdb47a9e	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
782ab103-aafa-4993-90f2-05b1cdb47a9e	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
782ab103-aafa-4993-90f2-05b1cdb47a9e	23591340-67c1-4838-982d-63786c93f989	f
782ab103-aafa-4993-90f2-05b1cdb47a9e	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
f07fae5e-cd31-437b-9cdb-8892c673d027	78298940-83da-4217-b6db-6ded30249986	t
f07fae5e-cd31-437b-9cdb-8892c673d027	06c8e679-b22c-40e7-a162-4647618940b5	t
f07fae5e-cd31-437b-9cdb-8892c673d027	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
f07fae5e-cd31-437b-9cdb-8892c673d027	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
f07fae5e-cd31-437b-9cdb-8892c673d027	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
f07fae5e-cd31-437b-9cdb-8892c673d027	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
f07fae5e-cd31-437b-9cdb-8892c673d027	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
f07fae5e-cd31-437b-9cdb-8892c673d027	23591340-67c1-4838-982d-63786c93f989	f
f07fae5e-cd31-437b-9cdb-8892c673d027	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_scope_role_mapping (scope_id, role_id) FROM stdin;
7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	d73dc66d-e96c-472a-9ba0-be8cd7c66018
8dce157d-6e04-47f9-a2f1-454354eb0c38	c11316fa-9f53-4094-a37f-27e447c9dc6b
\.


--
-- Data for Name: client_session; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_session (id, client_id, redirect_uri, state, "timestamp", session_id, auth_method, realm_id, auth_user_id, current_action) FROM stdin;
\.


--
-- Data for Name: client_session_auth_status; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_session_auth_status (authenticator, status, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_note; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_prot_mapper; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_session_prot_mapper (protocol_mapper_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_role; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_session_role (role_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_user_session_note; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.client_user_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
4dd6024e-7988-43d8-838d-f9f7d0e0bacc	Trusted Hosts	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
43e74387-7211-4c15-bdd1-4dd3bece82fb	Consent Required	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
96a7cc1c-f3ca-4989-a9f3-3376588d6034	Full Scope Disabled	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
33cd716c-0fc7-4138-983e-8dd88b277d43	Max Clients Limit	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
1bc562d7-9d01-458e-9715-be72662f2abf	Allowed Protocol Mapper Types	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
3c1d3698-8e92-4e6a-ba90-1bd5f5e91eca	Allowed Client Scopes	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	anonymous
e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	Allowed Protocol Mapper Types	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	authenticated
e12d4ac0-e940-479b-8d94-aa51f71f85d4	Allowed Client Scopes	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	authenticated
5a6f7a44-67d0-473d-87cc-c31753361ac5	rsa-generated	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	rsa-generated	org.keycloak.keys.KeyProvider	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N
aec175d0-c6e4-4457-a0d1-14c912c4ec0e	rsa-enc-generated	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	rsa-enc-generated	org.keycloak.keys.KeyProvider	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N
a8e47435-ce7f-4a2c-ae4c-2356bb4dcd17	hmac-generated	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	hmac-generated	org.keycloak.keys.KeyProvider	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N
87a65ba6-2259-4680-9ab3-cc4586376b18	aes-generated	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	aes-generated	org.keycloak.keys.KeyProvider	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N
0b122cc6-0675-47c3-ac4a-0dc6aeddc905	rsa-generated	a8889a90-1082-4a86-b119-30a5eb902930	rsa-generated	org.keycloak.keys.KeyProvider	a8889a90-1082-4a86-b119-30a5eb902930	\N
23ffd168-4f2b-4327-b68b-56b8a04e9479	rsa-enc-generated	a8889a90-1082-4a86-b119-30a5eb902930	rsa-enc-generated	org.keycloak.keys.KeyProvider	a8889a90-1082-4a86-b119-30a5eb902930	\N
69e6c84d-3da0-431d-b1c8-86244c4e20e1	hmac-generated	a8889a90-1082-4a86-b119-30a5eb902930	hmac-generated	org.keycloak.keys.KeyProvider	a8889a90-1082-4a86-b119-30a5eb902930	\N
31ad9fdf-f29e-47e1-a45e-54b1db16baac	aes-generated	a8889a90-1082-4a86-b119-30a5eb902930	aes-generated	org.keycloak.keys.KeyProvider	a8889a90-1082-4a86-b119-30a5eb902930	\N
04d1a8fe-dc0d-4340-ad26-23d537b98a9d	Trusted Hosts	a8889a90-1082-4a86-b119-30a5eb902930	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
4d3877a0-9de1-4cf6-a033-dba440de630c	Consent Required	a8889a90-1082-4a86-b119-30a5eb902930	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
0b65c1ad-06ff-4805-8745-d69255fcad59	Full Scope Disabled	a8889a90-1082-4a86-b119-30a5eb902930	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
4d0b5f49-5e82-4ed9-8005-49027104a2d0	Max Clients Limit	a8889a90-1082-4a86-b119-30a5eb902930	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
beaedafe-b7e4-47ad-99a0-41ce7d22ab12	Allowed Protocol Mapper Types	a8889a90-1082-4a86-b119-30a5eb902930	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
4212f346-a2f0-4687-ba51-dea5688a55c1	Allowed Client Scopes	a8889a90-1082-4a86-b119-30a5eb902930	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	anonymous
33f79920-bdf8-454b-ba71-eec838ff4894	Allowed Protocol Mapper Types	a8889a90-1082-4a86-b119-30a5eb902930	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	authenticated
a7841559-1e94-4eed-be62-f99bcc0ae2be	Allowed Client Scopes	a8889a90-1082-4a86-b119-30a5eb902930	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	a8889a90-1082-4a86-b119-30a5eb902930	authenticated
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.component_config (id, component_id, name, value) FROM stdin;
7337e6d7-2c98-4ea1-be38-d74114a2969f	33cd716c-0fc7-4138-983e-8dd88b277d43	max-clients	200
10119488-b2e8-4b32-a499-74a6ca27c8ba	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	oidc-full-name-mapper
53cc55e3-9059-46e9-b891-f12325f254df	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	oidc-address-mapper
48e43ef2-eed1-496c-a29f-10317f01acf6	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
4457730f-fe8e-40ce-9bb6-721b124e6208	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	saml-role-list-mapper
c29130f2-089e-4b0d-bc1f-a15f95c3b0f0	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
d1c40b98-14d4-4b71-8005-c682e8df4679	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
7d73b194-de62-4d9f-a545-e100369586c3	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	saml-user-attribute-mapper
ac8bf0cd-0028-47d0-ae71-68d5695bf00f	1bc562d7-9d01-458e-9715-be72662f2abf	allowed-protocol-mapper-types	saml-user-property-mapper
4b48e1df-58d4-45ee-af92-bdea31ec8749	4dd6024e-7988-43d8-838d-f9f7d0e0bacc	client-uris-must-match	true
791c42a6-4a91-405a-9385-8cef3535c6e5	4dd6024e-7988-43d8-838d-f9f7d0e0bacc	host-sending-registration-request-must-match	true
f905baa7-5ea2-4e99-8a1f-5cb839444d0d	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	saml-user-attribute-mapper
8a983913-4113-4bad-ae8c-2f9b176f78ee	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	saml-user-property-mapper
41c8ec20-8f66-480c-a672-29ff9ed1be63	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	oidc-full-name-mapper
8ac307f5-eb89-48aa-b3db-e89cae5cd716	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
0e63a78f-dcc2-452e-b0c7-b0f8aea72b53	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
3f7e251f-8c05-413d-9566-446ea1e2b755	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	oidc-address-mapper
ecb5cfd2-86f3-42c3-8260-ad80b29447ec	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	saml-role-list-mapper
9934f6aa-5590-4875-8014-d4a533895cfb	e8ddfb9e-aa6e-4424-83bb-4a6c0b6164b3	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
1867cf90-46f1-4712-8a1a-0fe6d140ae02	e12d4ac0-e940-479b-8d94-aa51f71f85d4	allow-default-scopes	true
0b73f5f0-1b75-472b-b03d-dbd56f6c4270	3c1d3698-8e92-4e6a-ba90-1bd5f5e91eca	allow-default-scopes	true
b748eaec-5600-4e48-9b09-b75d017a4941	aec175d0-c6e4-4457-a0d1-14c912c4ec0e	keyUse	ENC
8ea361bb-eef5-4063-9525-09e6bc1224b2	aec175d0-c6e4-4457-a0d1-14c912c4ec0e	privateKey	MIIEpQIBAAKCAQEAwjvk7cSDFjTSoxmiunhcOe7n2wecK+OW5cZ3FILgbOITJPUnsoZgowQcV0jvrikkQ/GJvK0r/1KNGobP6H06zJ9ax7KB1NaJ91FtbsUEfreCOvIprYTlxz/HptfqMFFYbQq+f+Wla5pV5G7lxXBCKjym4Z2VAyPBO11tHPgi56n0ySbTTGMiB7DegEvhL5xTU9EQmEHBBRxN56Y534uiPnR5/tRiZmOU/r331mdsl9/h0VCfBNjlbK7cyWWiM++kRtgCWKq1JS4CWBEY9ZSOH6+fXUk9WcGAvhDnyImUnBuje3/Oi5QBmi1jz/3QBnFdm4LZZ2chhdgI6JtsBLZWEQIDAQABAoIBAATQBsTsv2dqSU/OP6Me1ADWzmWLGOUDf3EpC46gDDSBHi1RFQ8413sii8Xj4kBuojM8GwgS7bSmqdAJnWwqx4Kww3Hllo7x/pAjXd1Gl20WWe02ToRkocGKmdqkne0IOZ3WDCX6HYZULXhQAR8fwBvwzce9p6a2GOsXeQUkkXornPvuWM981Q53vFQpStyOvGZsHEhjZGwRKXGpk6CEwYYrIMOlxTDzga37Yn73Q0CNf4cFEjkGHcC0La/7hvp4HMo2Q0UHFXHrcKNAvONjzTxxLxy0Fs4K46yY80+MLR3J4IuZaCwNaTjjfciIx92i1pt9Zxssxvmoa/a/KFv8XVkCgYEA6RgdnHCstwf/4ThO0nS7Tt3d/OIkYrk9sGRbLx9bVcsMqEIGNg08UTqsMob9kL9+2gcU/+y1OuTpz/ywWBCLQqCHh2XFgPz5Px1YHxKLOYjBA6+7m+Uaq589hehAMdLhhXdi9VgypuzUKbdBzqE1YQkPkzxMvWE38rd1X0bqM00CgYEA1VIuv/ZiwCLNNv951dCsDZsZ4Shv5xDKHmu4kHNaGIrzU0ijDf3eQFoXUXxvUODM2f1FLHdwWKif6fWjMS1jf/JLTpOeNlZdFgt/zh5PTtK4jSOg/oc5VeXJ6VNSZhN0qJG+c37bJjysOs1Qc0JjLV221sZ1/UkclgVa2Krew9UCgYEAosr360l6Jr6Mg/Q6gJuMMdGcd0BNXzG2ltoQIQIKXQnZxi2OfvORe725g/RpZU2Iv6cQkIj2LbKwZVU6p9auHBNXdEWswaBsRxssb08ZM06CVxvFsGQ60z/8zOfe5a09WmR+FZHKlnBy4MufRzuptyKH4THM+rrKQ1bDv44chUUCgYEAtodsoyri0n6008OEb5TvM3EsksF0BuYR3DfWJBvf0rJKhE/Z3WYFn1C3NS1hXnYMMtaJMwQ8KFCZDKr3aWPqCJe9Ik4jiWEao3tRb3/14n9iFLqQzlnMkJhfzXtTPS6WmoqiyNaaFDzELkhx9z2t+SqAAYStfv3pLOEGG/ERTcUCgYEA4ROPD//ci7AFVZKnoWlBvg6cW8ZIdkB6wLvjPUAwSyK0/ynlPNuyAiQ+ZhAJ1uZy0msiDOCt31gDVBj4Bn4hG2o8D1/3HXGuA6t1YDoqRAlNORiOuo5lSaRzD0mWOoIvI1jYFddyOLXUbuHllOHfTU8/EvrzOQvMtSdlWQH9lhs=
8c2708af-77ab-429c-986d-4576850d21e0	aec175d0-c6e4-4457-a0d1-14c912c4ec0e	priority	100
575bdce7-a83e-4ef0-ad8a-f100bbbfd4a4	aec175d0-c6e4-4457-a0d1-14c912c4ec0e	certificate	MIICmzCCAYMCBgGNOLPyjjANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjQwMTIzMjM0MDMzWhcNMzQwMTIzMjM0MjEzWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDCO+TtxIMWNNKjGaK6eFw57ufbB5wr45blxncUguBs4hMk9SeyhmCjBBxXSO+uKSRD8Ym8rSv/Uo0ahs/ofTrMn1rHsoHU1on3UW1uxQR+t4I68imthOXHP8em1+owUVhtCr5/5aVrmlXkbuXFcEIqPKbhnZUDI8E7XW0c+CLnqfTJJtNMYyIHsN6AS+EvnFNT0RCYQcEFHE3npjnfi6I+dHn+1GJmY5T+vffWZ2yX3+HRUJ8E2OVsrtzJZaIz76RG2AJYqrUlLgJYERj1lI4fr59dST1ZwYC+EOfIiZScG6N7f86LlAGaLWPP/dAGcV2bgtlnZyGF2Ajom2wEtlYRAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGuNVaH1lc3DilD63CvcOCI4qbYI74XYoUzJ2yADHWgbzyCve9O1YI00xSCWVamQObif9g/IyaCX5BltPAAkZoiz5bMsSLZMsMava8dD1Ia2hvpNVztkj/qkNtcm/QCIIlK+L4sgmw8rLSOxucX3J/LfPQsHQ31CWPX24G8Z8xDjU/M68bk/ncgmzjYlembdrTvTeYj6Js5vAX5/aAAh8EeGoxN+sGS+g0nY5/6Vs/CspaFyJ+t275QVIgRcR+/ZHCcI4E+KBlFz5/HR4gwx2uhC/5tvrdbSIu4GA/GNLzZJt2uKYgU/jk808ghj5sqDW/fwEv6pXPOpA45+Cc71btQ=
e399da18-a375-4d47-97bd-46f90f99d040	aec175d0-c6e4-4457-a0d1-14c912c4ec0e	algorithm	RSA-OAEP
4ca9585e-bb7b-495c-a61e-26ee0b34d7c3	a8e47435-ce7f-4a2c-ae4c-2356bb4dcd17	secret	WIfTNqWieVx4cpgB9nL2LUwctqDflIg8g06e8nmbm-vJK_F56pm3k-yi40NbCALylTk9zsAT_0ee_OTT-24wng
573a111f-f355-47a0-8a63-2a17be64aaa3	a8e47435-ce7f-4a2c-ae4c-2356bb4dcd17	kid	e2363888-67ee-4ce3-b20d-300df4625ca4
4ce102c0-ee9e-46c6-bbe6-eb1f0cf66b0d	a8e47435-ce7f-4a2c-ae4c-2356bb4dcd17	algorithm	HS256
82232f70-92f4-408a-88e1-5a280a43e74f	a8e47435-ce7f-4a2c-ae4c-2356bb4dcd17	priority	100
e3b69f73-8902-4586-a2d4-3060558574e2	0b122cc6-0675-47c3-ac4a-0dc6aeddc905	certificate	MIICtzCCAZ8CBgGNOMUm1TANBgkqhkiG9w0BAQsFADAfMR0wGwYDVQQDDBRjaXVkYWRhbm8tY29uc2NpZW50ZTAeFw0yNDAxMjMyMzU5MjFaFw0zNDAxMjQwMDAxMDFaMB8xHTAbBgNVBAMMFGNpdWRhZGFuby1jb25zY2llbnRlMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA79RCEoONTEUv2VIkZtxPQSUVsz0XKD+pQCCcJd4J2k29JUpxmr1SYYj9c0eT1SkE1jhU2AVd8xhW0uz1f4Q+cLnddtfxdMfrMU5HmY1EDH5Bm7xSudekGoVINRKfh1zhc2gEO6eqxiNLTeDoMMjz/TRd/dRp96IgF+Tey3RznO88i7VXHK0wXekK3Mw6y3kMj4GPFGBcbf3w0dZwJBqpJP02j0caWDgueR3B+rWFMi6nDSNAKNvoSQ6YmZSHwLw95ZEymTlT5qcWaK51Nl9vIoZN26SlS/apkda+LkoFSeVq1E66SGVkgvtpVE37tuC26QWxIIK9VdCjQP7On8eXSwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAR+ph+FlEnwSb5PofVkeIwim7YErbWa54gl/8oZYFjPu3qQgRrrEUFE9NmQrVpNYVWWo34eYpuspFeHe6mqdXSo0JFDszmpH2mXd+Uy4hO1mSBg2uFqrMRjBo+e0E2nQ3ij6PcIXDs1KQfT1KQdpYuJTHNbLaqSYQhok3v8YLIYPZQuG5pz3uB2Y9PGnVvcnuVw1UvMnLgdmpeCtSJEGzkHI+chmN3Nja0cFWoTxmiuquPoEB7pyjHm8YoNrIQ59LHx8GIFurUO+0kUAEmpkcQoWsP+epukHxDOqTEosf2qSrniS7DSrxSSN24KvQh7BUOgG1OIzz7z+bbQtNOL7Xg
aeae4585-0351-4cea-9a6a-fb8af18f245a	5a6f7a44-67d0-473d-87cc-c31753361ac5	privateKey	MIIEpAIBAAKCAQEAkAONZ7gzlgqB/gcs7mRhqi/ASIkUHrW6YIirYtrvBc79pUUgNF4fOuomIDCbqs5XHLN1s/owWYcFNZ41oucsPkkTd57gneRSiQkbogGv6888FbzfvfK1W32aYVyZZRDH2P1xAAH44sGtMOVsHXfhAy0zPQrpMlPMXkF+MifmM3pqqP32eCfmIROByeY/2MMC2E2wf8vi6vK9cAC4EtjxP/f5AMbZNh3mfROgkFPC+L3fJEv6SQoQ6NaYRlNhnGv81xo/pAbsPsO84mq4JTadvwQFNDpZqOQUvaVtCDX+RYIKMGy6F1+j7cIoqKJFwLCTEMKQ50fcqrW61Z60VBCm1QIDAQABAoIBAAce7diBjqduGJRfdXTKKNwZ5VeQ05pbIP6R82t9aNEwNXF9FAUS3Fd06XKxBJly/3KvWvQNvGNI3lSS4JMP/CuOQHSrOe8EsaIN/bxKehDeW9m1T5jcTeRatw32Xq1VIZSUn4hBELXhnrcquOhSG6GlFAICLpqq8xF8ConfWC4WXf8R/yeUcILPHQYL1EXLEygS/Q9zU7dpURmouPEivv4YZP+XERSew1fjXfnazHvea7pZ1TC4pBjRhEs4dRP10Y8L9cCzXe7DpHzKWMMpV9FQW2s5Aay6UmNd6gUXpXi/YN5znu8B6Ln+zoPcKIQrSVVRmSuD36FEfltYU7MFSwECgYEAw04AfJNMg1nW/CNgub7SAbOhy3JT11Hmo33pmh+PceYVpqOnoRw+LMpJ+UnNGrdQGsLXCoJRAy6c9bPnKq5dc5+NMQrO9hQ6GaHFMHrIL3tOPcnjON/RF84Vp5Jfss1X4Chz2qcMHQht002F0YYCP78jx4XJiwAOOE3zOlRkN4ECgYEAvMT4KlWzhenoXFIfHUxGzXci27xEArEFbMd71Y7S1D/SrBvxEc+MES3ac6Ghh7G8Kshtvo4Nt3Zk/0ylTt+boPeZUWFlQfTZ4clUeLvr7JLGgjR315zftTBRXsJ0QCQo7jOzFdH7nutezNSZhWLKA8v/NQyQmJj88FA8mKmuuVUCgYBD04sHuIAR/dlTp1WoLETyolfGN+Y2GRNoJApEpEFE5QsP3DYTFaPiGj6mfKLmbFM9d/0GgUtxN3b7PYdCo1uBFCAMsBxACbZDwJKiy4FKPCBoBP0Me246Pi/3CjaB+h5SkrnpbaofSxYZFAstQMT3LoJWgHgF9fb6Rtf5oeVMAQKBgQCmA8CRUt/YxRdCokKU/LFEICO7Bn71swOnYGVLMxm96wd3r/c6h8KSlTYK/bpW4XwwUCccMEhl8izuzlSP4l43manwBW0NLa61QOAWcocRfyAwAL7pFCE2c3EMmo2sn2CT5Un5xbu6TxwHnm4J/hDWeAFRgWRloB7LpnU0ef6dJQKBgQC+/h7x0nImzfs0vO5tVCEzM+MS5Uu/JdVx12298VmbPsT/RFEjziJ1rIUsZ+AjUMxrw4hwIulyiCVqEXG6qiAyNjDXPGVyVNFwLKs2Nu68DpoZXQ/na/3/w0JXbUQJJehjaIFq1Y7+Yhsp5F+7YBS4asz6qWRiezvPcjFZ7VOobg==
c178b1f4-4681-44ce-9b99-66ae87c348ea	5a6f7a44-67d0-473d-87cc-c31753361ac5	certificate	MIICmzCCAYMCBgGNOLPvpzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjQwMTIzMjM0MDMyWhcNMzQwMTIzMjM0MjEyWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCQA41nuDOWCoH+ByzuZGGqL8BIiRQetbpgiKti2u8Fzv2lRSA0Xh866iYgMJuqzlccs3Wz+jBZhwU1njWi5yw+SRN3nuCd5FKJCRuiAa/rzzwVvN+98rVbfZphXJllEMfY/XEAAfjiwa0w5Wwdd+EDLTM9CukyU8xeQX4yJ+Yzemqo/fZ4J+YhE4HJ5j/YwwLYTbB/y+Lq8r1wALgS2PE/9/kAxtk2HeZ9E6CQU8L4vd8kS/pJChDo1phGU2Gca/zXGj+kBuw+w7ziarglNp2/BAU0Olmo5BS9pW0INf5FggowbLoXX6PtwiiookXAsJMQwpDnR9yqtbrVnrRUEKbVAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAH9mNlGgOr5MdyWuhqTMRRQtck4Y86H/izWk/XQSGTNY3cqK/vXcSFZXx9LIRHCBDctC+zuiOgW4rkpYKHVwFe5pPC65nf1fQrb/bm7pBYyOXSaujKNFFg+xId6+dwH9EfMKvl79MoVUgL/Zbqk3W658buC+CMXKYTKaq+3miXZTggWP8HEmSUWF6WP2btjhboIY1NcGimE5wegRsZaahabk1hJ8TxoaxeoAcH8xwtMyiJ3w3z2/KCOPpK/5/rNvEAXkVpi0oXG4E1TgJ1H2zvqektgI6/quZUzx5kD7rDghR1oxaaWUZEDhR/VOIg9I0oXJhEzpnvNiDHBqoJQ+DnQ=
01448a9e-dbd8-4205-99f1-ce94f61f7afe	5a6f7a44-67d0-473d-87cc-c31753361ac5	keyUse	SIG
0e356094-765e-4065-b86a-3924a751cd5d	5a6f7a44-67d0-473d-87cc-c31753361ac5	priority	100
b710883f-edd8-470e-8a71-7a2d8bcc6530	87a65ba6-2259-4680-9ab3-cc4586376b18	priority	100
d906665e-04f7-4abe-b500-e011b05f1ba4	87a65ba6-2259-4680-9ab3-cc4586376b18	secret	L02H3glgtyE-KiLCoh18mQ
8dd799fa-d289-4ff9-ba00-0abae519fa9e	87a65ba6-2259-4680-9ab3-cc4586376b18	kid	a7fd528e-845e-499b-8aee-1ed683dc809a
4d3f33de-4467-4eb9-9912-908c54e43dc8	69e6c84d-3da0-431d-b1c8-86244c4e20e1	kid	d196dbb3-b893-4959-823b-286cfbb299d9
bf9dfbd8-10b1-4b1d-bfea-f33e516395f2	69e6c84d-3da0-431d-b1c8-86244c4e20e1	priority	100
eebbc9ab-2e21-4080-b074-121c23623a50	69e6c84d-3da0-431d-b1c8-86244c4e20e1	algorithm	HS256
ffff9717-a552-471e-bfbd-529cc4405576	69e6c84d-3da0-431d-b1c8-86244c4e20e1	secret	fghw05dTzQE_TNRbccG5XTGMGkVNHQnuzQjGfxVjm4-Z9nxx3BFiE08hmTy0D5x_FV4QhBuQaqPFzXde_LT63Q
e38129a8-7283-4998-98e5-22e27db02f6d	31ad9fdf-f29e-47e1-a45e-54b1db16baac	kid	98b91761-dc93-4b3d-b771-10f41bb5a1b8
08d0ceaf-48d5-4dd7-b28b-4d5abc112eb7	31ad9fdf-f29e-47e1-a45e-54b1db16baac	priority	100
a2775377-e1ab-4694-98aa-f8b42f82dcab	31ad9fdf-f29e-47e1-a45e-54b1db16baac	secret	a98-OKEuJqGKBzoDbzpKpg
8adab7fa-426b-43a2-99a4-47f33a8d3d57	23ffd168-4f2b-4327-b68b-56b8a04e9479	certificate	MIICtzCCAZ8CBgGNOMUn0jANBgkqhkiG9w0BAQsFADAfMR0wGwYDVQQDDBRjaXVkYWRhbm8tY29uc2NpZW50ZTAeFw0yNDAxMjMyMzU5MjFaFw0zNDAxMjQwMDAxMDFaMB8xHTAbBgNVBAMMFGNpdWRhZGFuby1jb25zY2llbnRlMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwdd/BvqIUmAEQU7YBKQ0GzxbqBM4h/8Kn2kNunl9sKQDhwhU+Sk04C178tL5d1AE7Y6MWe3FzTv2dKxDRL95cj6DqdO4MZGXDWpD+TLUMaCBYLlDNH8YvoXc+YUPCk/xRtwNe+oprHM1r6nHbONYlwsF2FDcxtDleiFbo24EtnrK98KoypsdPzZIT9Y1ITmmuTg61C6w53aK6vd9hbAt+hzOyDK+I1LHJSJSSg/69UaAbmcgNAPE0rE2C8JROn7+RZVxZihVlMbb99nQiEqgiVxGKauiutFmM1htxV9cEfF+4Z3j94648DbuG2rCNiUASxlgoqCxeiNmsuJ8U0ZngQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAlb20OrCAnZP03SI7MzkfnhZfWFwU8nAKlzq/WdGkfc0ZlKS/zVR0VqUiRzbTARd+XGQ7btvAgpu8L/H1pwQX8FPlMEBXk425uD1gcn0Ap4iOgf2/TguwOnbK042fsG6n+LDCquKdUSdAuf4qXOB+ovFnZ1rRyMnHCFF5vZmAfNWLRysyAGvluaKxorQjH+pTZSNneBbGDN8agpy/IA8pJrTZQKbhKi9Bt/tiIijkCQBcsKLcNkowK9ciBQ08Z2Ctioxbhaj8PoPTR5QjjY6xhTj3nIw0obbEj6sVGml9plJ2vrgj0E/+BEYKAkcgk86tyt9NgCJMaVh3m3USEHDiN
7e994ed9-4348-441d-931e-f4d052b9711e	23ffd168-4f2b-4327-b68b-56b8a04e9479	privateKey	MIIEowIBAAKCAQEAwdd/BvqIUmAEQU7YBKQ0GzxbqBM4h/8Kn2kNunl9sKQDhwhU+Sk04C178tL5d1AE7Y6MWe3FzTv2dKxDRL95cj6DqdO4MZGXDWpD+TLUMaCBYLlDNH8YvoXc+YUPCk/xRtwNe+oprHM1r6nHbONYlwsF2FDcxtDleiFbo24EtnrK98KoypsdPzZIT9Y1ITmmuTg61C6w53aK6vd9hbAt+hzOyDK+I1LHJSJSSg/69UaAbmcgNAPE0rE2C8JROn7+RZVxZihVlMbb99nQiEqgiVxGKauiutFmM1htxV9cEfF+4Z3j94648DbuG2rCNiUASxlgoqCxeiNmsuJ8U0ZngQIDAQABAoIBAB89XamAQuwTsVvdGw9Ub1lcVuE1h8zTG6EpSx8caVdId5ERlLA7JdxjODCgM7g42zJ14MS1f0VePo/7GzQ/L5Fr5cg+6NaEQjXVRHMq7+u3lGJEOUKCoThDVqOYAB6HV7k1ya6IxIKYZrSSxiMUWLiu/BgqoAoirHkMWzpxhqzpwWlOlH2AWXGef1rL7SRKiSIbmcrhqXWLNreSznIsWoXyCJVDAo0meOMAYCm/RzYCRLjmPHoWRo8vAXFVwBoIgEtzlS69kUlX++WTh0fMeJycUk3HTFCR2gSNlg42i4NYLJoNoewS+uISt4izz+zwhR57E5XgUYvDA2FbuD0LhGECgYEA5gmvk3Suw1M2itEkZKT6YzFK3mYtF1aKO2Ph1OioN9DqbxsucX3Eh5m3vlo4coh7GvhcpJWUk5XM3XC8YiOKHmi40AbyAHEwYUFe11o/uTc2QpkTuKGVwcyK2CZfQegcvSc8ZhrZw6zh3oh+xKb/vEY08w9lax229SyJ6LLi6u0CgYEA17gGPgE3RZAXLcgaqzPIqGSq50CZB5OfFSTWAnaq3xRKTtLKCUL61TOlD03yXX4pUw993r5o+MbdQOSIJzrQXUU7oJamdMWDpAZVbrOAi0mw7EtIaX7uTyvqK5wTyjrOHexagwH2eVVsYxyPYwAhrVUdYqUfE3WqdvHvcVXvmGUCgYEAmzpuAFL8m1HLvGDk7gVl8QmSPAE27Ei5ftonykQ1P3T6vX7aGDuGIvrXwtueu6rdZ5Ww7l8Bo5WyaLtqY88XpO2FyNrm8uVugDJcjXWZCqL3OnmRlUV0iRI9nx5FqXO38OxJFZ2I1UIUKNZytMD8UVAO6vW4uk+S5l7nsN8FwQECgYArZwlKfHAkfONNAM/s1lRvHSINuT+Q+LCvTmQIxtuGWszAo0SnLGTPdL6nH6W+Dd4+qgVHRcRVGZ32qoSCcCDwQzfGDcmcAQmFfQi/ZzpbzA3e3R7KqxFDuLA66gJJVB+Yga+MYF649ukB4AMxhhtAhrmM2XAxOCF+IRtuED5OyQKBgFjmWg4h3Ar9i4mEFCBNYo+V9Ut5u4OGcQI2JFWJu1uq6OjC2vx7NgUtluk8yi3zjl7jPFKNS3hEbF7fD96cVlZip0pB0VcxUWJ5359zWi7cwtBnsqP/UUz8wBM+T/ZPrxEltR+BicK+OXToo+Dq7WtliyEd4S7ntsfXOFnbxYJd
d5324d61-da80-48fb-b535-72529f2215f3	23ffd168-4f2b-4327-b68b-56b8a04e9479	keyUse	ENC
06d467a4-9c21-4f64-ad15-2673a16a822c	23ffd168-4f2b-4327-b68b-56b8a04e9479	algorithm	RSA-OAEP
22dcf21f-112c-48ca-b801-41907b53394d	23ffd168-4f2b-4327-b68b-56b8a04e9479	priority	100
c1eb8427-f561-489c-a37b-2369f7ff93a8	0b122cc6-0675-47c3-ac4a-0dc6aeddc905	privateKey	MIIEpAIBAAKCAQEA79RCEoONTEUv2VIkZtxPQSUVsz0XKD+pQCCcJd4J2k29JUpxmr1SYYj9c0eT1SkE1jhU2AVd8xhW0uz1f4Q+cLnddtfxdMfrMU5HmY1EDH5Bm7xSudekGoVINRKfh1zhc2gEO6eqxiNLTeDoMMjz/TRd/dRp96IgF+Tey3RznO88i7VXHK0wXekK3Mw6y3kMj4GPFGBcbf3w0dZwJBqpJP02j0caWDgueR3B+rWFMi6nDSNAKNvoSQ6YmZSHwLw95ZEymTlT5qcWaK51Nl9vIoZN26SlS/apkda+LkoFSeVq1E66SGVkgvtpVE37tuC26QWxIIK9VdCjQP7On8eXSwIDAQABAoIBAHKIia1Rye78s6czhVLiL69KyJw6to5wKKIJcMjcyxPIQGZIsQxXEbqms3OUYVNRLZAogsxPc9iebQh6RZvKSg+Hk+4nnpZIy1dWrPb18eONgEOholhCVYqc0CkYZbs9biWxLSRYJERPoqzklb6GmY74rVKFWL38XRtMH+JGENiGs0fiOmCm11k782RqQWEp09JrgqO02T2rhUvOkUW31pCnN/UN0v4fdCU7p8SLqpwQROraYeIfHJGomj1i7r9nTzr+dF1CRRsfc7vg/C2faZO0SdfJb2xr/l7gquTmWOFtb8vAA6i73yV8jskfdzNzTu17EEsmbE4mZTucRhGOvEECgYEA/VVHfw9mP7b9T+FSbzrWZCAktTz2eAY2RwQT1+BzGKPynnkKwjm2nK9kvK+myg3dmqR6l6SucMBvUuh5nPUQf5nV1Yfbg6sibNQbLJd1bI0ExbCGnNh7SWf5Qyt9OAH4FgaMUoL2WeaNv8jtB4hGQX7gAfzQ9wZSmy6ZrDpyv1ECgYEA8lqWEXsNMXccDKSQX0H0qCvWt0PkvVYOmpRCgyiz0KB38rqpGP2fA2UNRuxghBSBuXXM/DymAoMqQg9rupl/bAZKgklfwdNGYI9ReLHLq8IiU7R0uaKd2omByngyjk0YCMY8YccbL9UuAfd+Eeo8RwwRQPXuzDYdLPo4fl3l3dsCgYEAwU9GzF0a3/mpmD/O4JOcLzEb8bSAOpw65LvmzG84RJAuPeENZaIWh7+K9jRu6HXmKO/joD2I4v7ks/YzfqK+ITBpLJSL3BBFp5e+2KsGbnu5LYUDY0IJ6bzORwQze/58qYn7n6bRh05X0gXnYT0Kp0XCd6HMbwNDG6lWmEbNctECgYEA5vfB8WLuTegQCVSEauQ2wFcXSI0h3/e170fAo7MUu41TAzc1lKWQ9/FEWAVKblYE07Ua6ID8TYSnr1F2/9zt/Qt50vUOl3A8sCWmJXp8enKHuHJQk+IQwoct9MHcnJWIhEQ4o47XsBxgvmsZ8QTk8WjkwJ4hOOmRoJ3PXU8rlOUCgYA4VkHVlBS0wIaAL/7zdhFhcft4hwUanE3Kw+NgeT3NzbHje7V+hFMMkjaci5kGFWDJY15n+3EnSqHvH0LIQmUz5VppdxGnboTkP65HqVE9/xvat8Xoddd3ddH4W8Vsbny5gW8fY8Qal0rMvw9ckYhgFbH2rhNFSXhBPCsA0h65BA==
6fad3a68-579a-4dca-935e-5ce8ebc53c24	0b122cc6-0675-47c3-ac4a-0dc6aeddc905	keyUse	SIG
9fe7b9c0-2add-4914-ba8f-e6c85abc04ae	0b122cc6-0675-47c3-ac4a-0dc6aeddc905	priority	100
c7aee394-89f2-4dd6-81ed-67cc5a93c4d7	4212f346-a2f0-4687-ba51-dea5688a55c1	allow-default-scopes	true
5839b3a4-e979-4921-8ebc-7396b2584b8a	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
d43e7594-aa56-4d35-822f-3b8bd310cbfc	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	oidc-address-mapper
82f4d1fd-fd20-4de5-9e3f-8c1eee7df0ea	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	saml-role-list-mapper
28958888-c426-4523-a1e1-6c65cfa6359b	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
3bc1e6d9-6dc7-43b5-bd14-43a7b4cea95f	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	saml-user-property-mapper
46a8ceda-f711-48f2-80f3-72852d072bae	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
4285f337-f71c-4232-b616-553885862a63	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	oidc-full-name-mapper
10e852e1-18cb-48ae-90a6-c0dccfbf1efc	beaedafe-b7e4-47ad-99a0-41ce7d22ab12	allowed-protocol-mapper-types	saml-user-attribute-mapper
e51717e4-825a-4ac3-8f62-1fe035fc79d3	a7841559-1e94-4eed-be62-f99bcc0ae2be	allow-default-scopes	true
21736cf5-1516-4d6a-b861-f2137da317f7	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	oidc-address-mapper
32d13e5b-3e61-471a-ba22-f38f281c0f0f	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
6efcce6b-9bf5-422d-bd4d-ea3a3ddba0c8	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	saml-user-attribute-mapper
91916dcb-1929-4a70-9b47-49595ac8d5fe	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	saml-user-property-mapper
47003d8c-d8c8-4965-8a46-6b932d518ca6	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	oidc-full-name-mapper
6c1f00c2-f830-4479-8a3d-a5abe23b979e	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	saml-role-list-mapper
6e4f848d-ae83-44c1-8b02-c3c1b170de16	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
35fb8586-3562-4fc3-a3e9-5060d643f926	33f79920-bdf8-454b-ba71-eec838ff4894	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
6769d6e1-4703-4322-b39a-65c379d7b232	4d0b5f49-5e82-4ed9-8005-49027104a2d0	max-clients	200
1b35cd39-6056-48eb-affa-60a5b090983d	04d1a8fe-dc0d-4340-ad26-23d537b98a9d	client-uris-must-match	true
daff9795-8dfd-4ac8-a3d8-21dcc44deb5c	04d1a8fe-dc0d-4340-ad26-23d537b98a9d	host-sending-registration-request-must-match	true
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.composite_role (composite, child_role) FROM stdin;
d7e887cc-d10d-48f5-86ac-66d339b9584a	46c90b18-262a-488a-9e23-039cb69c9d11
d7e887cc-d10d-48f5-86ac-66d339b9584a	6fd559f3-b7ba-47f5-944a-f0a830f703bd
d7e887cc-d10d-48f5-86ac-66d339b9584a	654b0a6e-c1d5-45b0-ba49-46826b93813b
d7e887cc-d10d-48f5-86ac-66d339b9584a	8df7e8cd-282e-4ed6-be9f-5cdf68699bd7
d7e887cc-d10d-48f5-86ac-66d339b9584a	b1a347bb-f3ea-4e3b-a334-ef41aab974ca
d7e887cc-d10d-48f5-86ac-66d339b9584a	018a7cea-fdda-4914-ae3c-dcb313761138
d7e887cc-d10d-48f5-86ac-66d339b9584a	180eee6d-ea20-4ca2-9d9c-d1d8b982dae2
d7e887cc-d10d-48f5-86ac-66d339b9584a	81821bb8-4084-4589-9f73-bafcb38dae53
d7e887cc-d10d-48f5-86ac-66d339b9584a	e40ca0a0-0981-426f-98eb-904ff86e76b4
d7e887cc-d10d-48f5-86ac-66d339b9584a	f80a6dfc-2afe-4a6f-8911-70ab128bc7a8
d7e887cc-d10d-48f5-86ac-66d339b9584a	a377f591-7cbe-4cea-9274-ecb5a1a9d511
d7e887cc-d10d-48f5-86ac-66d339b9584a	3102787a-dbfe-4e03-9288-26f36c10f2ed
d7e887cc-d10d-48f5-86ac-66d339b9584a	7b070f77-823e-4e94-9396-fa9797a96f31
d7e887cc-d10d-48f5-86ac-66d339b9584a	d04391cb-dfd5-4eb0-b99b-5948bc665419
d7e887cc-d10d-48f5-86ac-66d339b9584a	73530eeb-3813-4239-a872-efeb420d477b
d7e887cc-d10d-48f5-86ac-66d339b9584a	22e124b7-06d3-4a5f-bf6a-36cee4fabb7d
d7e887cc-d10d-48f5-86ac-66d339b9584a	e39507d9-1956-4923-8dc8-d49eb1703ef6
d7e887cc-d10d-48f5-86ac-66d339b9584a	c37bfe11-0fc3-477c-9b59-26b7d6f7cf75
8df7e8cd-282e-4ed6-be9f-5cdf68699bd7	73530eeb-3813-4239-a872-efeb420d477b
8df7e8cd-282e-4ed6-be9f-5cdf68699bd7	c37bfe11-0fc3-477c-9b59-26b7d6f7cf75
afc61b6f-3563-4725-8519-353c90af1649	b6f20c14-26de-40e9-82fb-ba01fd527ad5
b1a347bb-f3ea-4e3b-a334-ef41aab974ca	22e124b7-06d3-4a5f-bf6a-36cee4fabb7d
afc61b6f-3563-4725-8519-353c90af1649	a4cd6249-d1b7-41e1-b982-02d4d9f07e02
a4cd6249-d1b7-41e1-b982-02d4d9f07e02	3af26b15-2950-46c8-90bc-09422108d641
c1aed06e-bd7c-4177-92f9-b458b5502d70	6ed4adfb-dbca-4bcf-a4e3-d1c2854a6e35
d7e887cc-d10d-48f5-86ac-66d339b9584a	d9ad708c-85dd-463a-90f9-843dd1130c36
afc61b6f-3563-4725-8519-353c90af1649	d73dc66d-e96c-472a-9ba0-be8cd7c66018
afc61b6f-3563-4725-8519-353c90af1649	c85f4104-aea1-4e91-b91f-5a6c75030df7
d7e887cc-d10d-48f5-86ac-66d339b9584a	433e4b73-7e4b-4393-8f0b-9d483198ec17
d7e887cc-d10d-48f5-86ac-66d339b9584a	eb4d6363-1f44-4347-9fdd-70666252932d
d7e887cc-d10d-48f5-86ac-66d339b9584a	d62cdae5-3f58-4e45-9faa-e57e4989a9e5
d7e887cc-d10d-48f5-86ac-66d339b9584a	df45c74a-c1b1-4a82-a277-2a3e06d5cab5
d7e887cc-d10d-48f5-86ac-66d339b9584a	25f417e2-84ee-42d9-9491-47c843fdfb6a
d7e887cc-d10d-48f5-86ac-66d339b9584a	5a846985-eee4-4385-a832-fc4cd11ac929
d7e887cc-d10d-48f5-86ac-66d339b9584a	73998982-3ff5-4f22-a0ef-a9160c415f09
d7e887cc-d10d-48f5-86ac-66d339b9584a	66564d6a-2814-49e2-a39c-450f5369e92b
d7e887cc-d10d-48f5-86ac-66d339b9584a	75efba1e-0d39-42f8-b847-c1a220026404
d7e887cc-d10d-48f5-86ac-66d339b9584a	66f87c3a-3c94-4391-a579-e383d753be56
d7e887cc-d10d-48f5-86ac-66d339b9584a	72a2ab55-e318-4c84-98fe-5e7f4a460ac1
d7e887cc-d10d-48f5-86ac-66d339b9584a	ce3b6433-f462-4169-94a7-99816cd665a7
d7e887cc-d10d-48f5-86ac-66d339b9584a	ec1f0c20-bff5-453d-9976-c6c71637b057
d7e887cc-d10d-48f5-86ac-66d339b9584a	349b1b34-184e-4522-9577-25e2730f180f
d7e887cc-d10d-48f5-86ac-66d339b9584a	7d6fb956-0961-4c4b-935a-a4085388e40a
d7e887cc-d10d-48f5-86ac-66d339b9584a	ee360ae4-b864-4027-a94e-f4cd5d4d1a50
d7e887cc-d10d-48f5-86ac-66d339b9584a	bcf4775c-0ea9-46f5-ae9c-3a17a9ac8c98
d62cdae5-3f58-4e45-9faa-e57e4989a9e5	bcf4775c-0ea9-46f5-ae9c-3a17a9ac8c98
d62cdae5-3f58-4e45-9faa-e57e4989a9e5	349b1b34-184e-4522-9577-25e2730f180f
df45c74a-c1b1-4a82-a277-2a3e06d5cab5	7d6fb956-0961-4c4b-935a-a4085388e40a
a241695d-7d26-4dc2-81ac-6c955cdf85be	c3c5d2c3-520d-4ca2-8c45-4bb2feadbb22
a241695d-7d26-4dc2-81ac-6c955cdf85be	acfd7a83-3767-4ed5-b228-4ab8e40e32b1
a241695d-7d26-4dc2-81ac-6c955cdf85be	547c259d-7460-41eb-9df0-1c2944bbe9f9
a241695d-7d26-4dc2-81ac-6c955cdf85be	3d714d77-f36c-40e7-bf6e-00d327c6ae5c
a241695d-7d26-4dc2-81ac-6c955cdf85be	dc255097-6f22-46c0-8c23-e0796a52253b
a241695d-7d26-4dc2-81ac-6c955cdf85be	e431886d-665d-4f2e-a327-85634ede5b6b
a241695d-7d26-4dc2-81ac-6c955cdf85be	1979e94e-e165-4027-bca2-8523c5fec0a8
a241695d-7d26-4dc2-81ac-6c955cdf85be	691c58b3-1be1-4a21-9881-23943e355fc4
a241695d-7d26-4dc2-81ac-6c955cdf85be	e210d24e-243f-4b3a-bfa5-1327437af14c
a241695d-7d26-4dc2-81ac-6c955cdf85be	88db8d7e-4394-4f10-a384-eaba5d31d997
a241695d-7d26-4dc2-81ac-6c955cdf85be	2da10312-dd98-4bc5-8c18-c6496fc2f76a
a241695d-7d26-4dc2-81ac-6c955cdf85be	4f58c4bf-da86-46ad-8f57-047e5901a54d
a241695d-7d26-4dc2-81ac-6c955cdf85be	edd9cba3-0393-4905-b3ff-9d02b033859f
a241695d-7d26-4dc2-81ac-6c955cdf85be	34a0c541-1412-453b-88b3-32e7f9bb2905
a241695d-7d26-4dc2-81ac-6c955cdf85be	0e1a32b0-96e6-4e07-9a73-7a664f00f688
a241695d-7d26-4dc2-81ac-6c955cdf85be	1870ae9a-991c-40a3-9dfb-ac859ce5f79b
a241695d-7d26-4dc2-81ac-6c955cdf85be	111d4193-199d-4e70-b1e3-29156d8d66a1
3d714d77-f36c-40e7-bf6e-00d327c6ae5c	0e1a32b0-96e6-4e07-9a73-7a664f00f688
547c259d-7460-41eb-9df0-1c2944bbe9f9	111d4193-199d-4e70-b1e3-29156d8d66a1
547c259d-7460-41eb-9df0-1c2944bbe9f9	34a0c541-1412-453b-88b3-32e7f9bb2905
a70201c2-473b-4de0-ae31-dc5d227f0640	f5208b63-6e4c-4110-81c0-7a7706d7fa3b
a70201c2-473b-4de0-ae31-dc5d227f0640	5a39fd04-5966-4c98-80cc-905c0b505f74
5a39fd04-5966-4c98-80cc-905c0b505f74	3d1b6bac-b5d4-4589-bd6b-c01baa86538f
249a56a3-0578-442b-b660-c57dd7dcb613	676861ad-fb6c-4bb5-b077-a4d7b42982c6
d7e887cc-d10d-48f5-86ac-66d339b9584a	4d8423f9-20af-4a69-9444-4fb6e828d569
a241695d-7d26-4dc2-81ac-6c955cdf85be	692797e7-3049-4af2-a671-874d3409960d
a70201c2-473b-4de0-ae31-dc5d227f0640	c11316fa-9f53-4094-a37f-27e447c9dc6b
a70201c2-473b-4de0-ae31-dc5d227f0640	56387382-557a-4c4f-bd95-25983202372c
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
463ee70c-8ccd-4487-873b-d43452dbc676	\N	password	cdc98447-1125-4e96-8857-572aeb3489a1	1706065115147	\N	{"value":"2qvZ+0t8odWKJ5700rUVS1KOTwI0MjC2J0JnoG5WnvI=","salt":"J95JGo836npmQ2SpbmWYFw==","additionalParameters":{}}	{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}	10
6ab2e395-a229-43a6-8236-0609361bfff7	\N	password	703ed113-42d1-49f3-ac15-4b1160502dbf	1706128095988	\N	{"value":"odI70cT4368achneVgfEk2zgf1bvqVlhhAu4hx9OsZA=","salt":"sN/FyfetUDZgBaOsAgANdA==","additionalParameters":{}}	{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2024-01-23 23:42:04.07725	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.23.2	\N	\N	6053321947
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2024-01-23 23:42:04.189654	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.23.2	\N	\N	6053321947
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2024-01-23 23:42:04.294826	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.23.2	\N	\N	6053321947
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2024-01-23 23:42:04.306623	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.23.2	\N	\N	6053321947
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2024-01-23 23:42:04.591183	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.23.2	\N	\N	6053321947
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2024-01-23 23:42:04.631943	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.23.2	\N	\N	6053321947
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2024-01-23 23:42:04.89917	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.23.2	\N	\N	6053321947
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2024-01-23 23:42:04.939837	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.23.2	\N	\N	6053321947
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2024-01-23 23:42:04.961682	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.23.2	\N	\N	6053321947
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2024-01-23 23:42:05.169912	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.23.2	\N	\N	6053321947
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2024-01-23 23:42:05.336257	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.23.2	\N	\N	6053321947
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2024-01-23 23:42:05.360348	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.23.2	\N	\N	6053321947
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2024-01-23 23:42:05.443597	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.23.2	\N	\N	6053321947
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-01-23 23:42:05.492082	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.23.2	\N	\N	6053321947
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-01-23 23:42:05.498644	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.23.2	\N	\N	6053321947
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-01-23 23:42:05.513863	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.23.2	\N	\N	6053321947
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-01-23 23:42:05.526781	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.23.2	\N	\N	6053321947
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2024-01-23 23:42:05.664599	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.23.2	\N	\N	6053321947
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2024-01-23 23:42:05.821247	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.23.2	\N	\N	6053321947
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2024-01-23 23:42:05.840607	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.23.2	\N	\N	6053321947
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2024-01-23 23:42:05.867613	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.23.2	\N	\N	6053321947
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2024-01-23 23:42:05.881505	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.23.2	\N	\N	6053321947
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2024-01-23 23:42:05.969404	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.23.2	\N	\N	6053321947
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2024-01-23 23:42:05.994055	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.23.2	\N	\N	6053321947
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2024-01-23 23:42:06.000032	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.23.2	\N	\N	6053321947
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2024-01-23 23:42:06.074532	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.23.2	\N	\N	6053321947
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2024-01-23 23:42:06.245988	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.23.2	\N	\N	6053321947
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2024-01-23 23:42:06.256796	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.23.2	\N	\N	6053321947
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2024-01-23 23:42:06.365316	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.23.2	\N	\N	6053321947
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2024-01-23 23:42:06.398263	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.23.2	\N	\N	6053321947
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2024-01-23 23:42:06.442995	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.23.2	\N	\N	6053321947
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2024-01-23 23:42:06.453602	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.23.2	\N	\N	6053321947
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-01-23 23:42:06.465376	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.23.2	\N	\N	6053321947
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-01-23 23:42:06.471762	34	MARK_RAN	9:3a32bace77c84d7678d035a7f5a8084e	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.23.2	\N	\N	6053321947
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-01-23 23:42:06.533617	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.23.2	\N	\N	6053321947
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2024-01-23 23:42:06.543922	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.23.2	\N	\N	6053321947
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-01-23 23:42:06.557449	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.23.2	\N	\N	6053321947
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2024-01-23 23:42:06.565484	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.23.2	\N	\N	6053321947
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2024-01-23 23:42:06.572779	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.23.2	\N	\N	6053321947
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-01-23 23:42:06.576575	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.23.2	\N	\N	6053321947
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-01-23 23:42:06.582881	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.23.2	\N	\N	6053321947
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2024-01-23 23:42:06.595294	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.23.2	\N	\N	6053321947
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-01-23 23:42:06.798366	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.23.2	\N	\N	6053321947
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2024-01-23 23:42:06.808608	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.23.2	\N	\N	6053321947
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-01-23 23:42:06.819767	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.23.2	\N	\N	6053321947
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-01-23 23:42:06.830535	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.23.2	\N	\N	6053321947
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-01-23 23:42:06.833803	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.23.2	\N	\N	6053321947
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-01-23 23:42:06.930885	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.23.2	\N	\N	6053321947
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-01-23 23:42:06.942691	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.23.2	\N	\N	6053321947
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2024-01-23 23:42:07.031137	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.23.2	\N	\N	6053321947
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2024-01-23 23:42:07.08704	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.23.2	\N	\N	6053321947
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2024-01-23 23:42:07.09774	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2024-01-23 23:42:07.106857	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.23.2	\N	\N	6053321947
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2024-01-23 23:42:07.114933	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.23.2	\N	\N	6053321947
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-01-23 23:42:07.130141	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.23.2	\N	\N	6053321947
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-01-23 23:42:07.143941	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.23.2	\N	\N	6053321947
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-01-23 23:42:07.191704	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.23.2	\N	\N	6053321947
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-01-23 23:42:07.433437	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.23.2	\N	\N	6053321947
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2024-01-23 23:42:07.485898	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.23.2	\N	\N	6053321947
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2024-01-23 23:42:07.498671	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.23.2	\N	\N	6053321947
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2024-01-23 23:42:07.518423	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.23.2	\N	\N	6053321947
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2024-01-23 23:42:07.529298	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.23.2	\N	\N	6053321947
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2024-01-23 23:42:07.535676	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.23.2	\N	\N	6053321947
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2024-01-23 23:42:07.543248	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.23.2	\N	\N	6053321947
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2024-01-23 23:42:07.550113	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.23.2	\N	\N	6053321947
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2024-01-23 23:42:07.573318	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.23.2	\N	\N	6053321947
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2024-01-23 23:42:07.583512	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.23.2	\N	\N	6053321947
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2024-01-23 23:42:07.594599	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.23.2	\N	\N	6053321947
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2024-01-23 23:42:07.615715	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.23.2	\N	\N	6053321947
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2024-01-23 23:42:07.627627	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.23.2	\N	\N	6053321947
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2024-01-23 23:42:07.63498	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.23.2	\N	\N	6053321947
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-01-23 23:42:07.649153	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.23.2	\N	\N	6053321947
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-01-23 23:42:07.665512	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.23.2	\N	\N	6053321947
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-01-23 23:42:07.669894	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.23.2	\N	\N	6053321947
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-01-23 23:42:07.720093	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.23.2	\N	\N	6053321947
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-01-23 23:42:07.733754	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.23.2	\N	\N	6053321947
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-01-23 23:42:07.744779	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.23.2	\N	\N	6053321947
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-01-23 23:42:07.748633	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.23.2	\N	\N	6053321947
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-01-23 23:42:07.790358	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.23.2	\N	\N	6053321947
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-01-23 23:42:07.801405	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.23.2	\N	\N	6053321947
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-01-23 23:42:07.815894	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.23.2	\N	\N	6053321947
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-01-23 23:42:07.819435	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.23.2	\N	\N	6053321947
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-01-23 23:42:07.831985	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.23.2	\N	\N	6053321947
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-01-23 23:42:07.836549	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.23.2	\N	\N	6053321947
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-01-23 23:42:07.847203	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.23.2	\N	\N	6053321947
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2024-01-23 23:42:07.85691	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.23.2	\N	\N	6053321947
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2024-01-23 23:42:07.871942	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.23.2	\N	\N	6053321947
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2024-01-23 23:42:07.884971	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.23.2	\N	\N	6053321947
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.897688	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.23.2	\N	\N	6053321947
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.909029	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.23.2	\N	\N	6053321947
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.919188	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.23.2	\N	\N	6053321947
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.936363	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.23.2	\N	\N	6053321947
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.939514	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.23.2	\N	\N	6053321947
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.953754	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.23.2	\N	\N	6053321947
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.958939	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.23.2	\N	\N	6053321947
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-01-23 23:42:07.977174	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.23.2	\N	\N	6053321947
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:07.995202	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.23.2	\N	\N	6053321947
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:07.998696	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:08.015973	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:08.028043	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:08.031613	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:08.042869	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.23.2	\N	\N	6053321947
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-01-23 23:42:08.05215	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.23.2	\N	\N	6053321947
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2024-01-23 23:42:08.064608	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.23.2	\N	\N	6053321947
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2024-01-23 23:42:08.074504	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.23.2	\N	\N	6053321947
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2024-01-23 23:42:08.084449	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.23.2	\N	\N	6053321947
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2024-01-23 23:42:08.09626	107	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.23.2	\N	\N	6053321947
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-01-23 23:42:08.107391	108	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.23.2	\N	\N	6053321947
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-01-23 23:42:08.111831	109	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.23.2	\N	\N	6053321947
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-01-23 23:42:08.127265	110	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.23.2	\N	\N	6053321947
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2024-01-23 23:42:08.139916	111	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.23.2	\N	\N	6053321947
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2024-01-23 23:42:08.176655	112	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.23.2	\N	\N	6053321947
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2024-01-23 23:42:08.184528	113	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.23.2	\N	\N	6053321947
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2024-01-23 23:42:08.203827	114	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.23.2	\N	\N	6053321947
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2024-01-23 23:42:08.207002	115	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.23.2	\N	\N	6053321947
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2024-01-23 23:42:08.223027	116	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.23.2	\N	\N	6053321947
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2024-01-23 23:42:08.229971	117	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.23.2	\N	\N	6053321947
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
1001	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	7b3e4e5a-60f2-4ac1-b64b-71144f7990a1	f
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	1bf2757c-511e-47e7-9163-812e33e53388	t
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	498d649e-0fa2-4151-b9f8-99a88d123a1b	t
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	98b97d33-bb00-4670-baf5-b31fd9d07979	t
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	66265ecf-1071-4cf7-a47c-30a95449366b	f
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	08e82c07-d990-46d5-a520-90c23f73a65b	f
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c55315b3-af64-48b8-8249-506ec504279e	t
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	940da7b1-fa8c-4efb-85eb-e501bade836c	t
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f700edc9-6f03-45f6-8337-7d952327bfb0	f
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	624dda8a-a872-4ec5-9c7f-b7d611afe5f1	t
a8889a90-1082-4a86-b119-30a5eb902930	8dce157d-6e04-47f9-a2f1-454354eb0c38	f
a8889a90-1082-4a86-b119-30a5eb902930	f6fcda75-9013-49dc-8709-942cae10193c	t
a8889a90-1082-4a86-b119-30a5eb902930	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4	t
a8889a90-1082-4a86-b119-30a5eb902930	78298940-83da-4217-b6db-6ded30249986	t
a8889a90-1082-4a86-b119-30a5eb902930	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c	f
a8889a90-1082-4a86-b119-30a5eb902930	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9	f
a8889a90-1082-4a86-b119-30a5eb902930	35686e6c-6fed-44d1-b464-29ba0835dfa7	t
a8889a90-1082-4a86-b119-30a5eb902930	06c8e679-b22c-40e7-a162-4647618940b5	t
a8889a90-1082-4a86-b119-30a5eb902930	23591340-67c1-4838-982d-63786c93f989	f
a8889a90-1082-4a86-b119-30a5eb902930	60c7d281-0d13-4a7f-9eab-54eb511b8122	t
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.keycloak_group (id, name, parent_group, realm_id) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
afc61b6f-3563-4725-8519-353c90af1649	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	${role_default-roles}	default-roles-master	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	\N
46c90b18-262a-488a-9e23-039cb69c9d11	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	${role_create-realm}	create-realm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	\N
d7e887cc-d10d-48f5-86ac-66d339b9584a	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	${role_admin}	admin	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	\N
6fd559f3-b7ba-47f5-944a-f0a830f703bd	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_create-client}	create-client	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
654b0a6e-c1d5-45b0-ba49-46826b93813b	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-realm}	view-realm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
8df7e8cd-282e-4ed6-be9f-5cdf68699bd7	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-users}	view-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
b1a347bb-f3ea-4e3b-a334-ef41aab974ca	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-clients}	view-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
018a7cea-fdda-4914-ae3c-dcb313761138	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-events}	view-events	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
180eee6d-ea20-4ca2-9d9c-d1d8b982dae2	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-identity-providers}	view-identity-providers	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
81821bb8-4084-4589-9f73-bafcb38dae53	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_view-authorization}	view-authorization	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
e40ca0a0-0981-426f-98eb-904ff86e76b4	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-realm}	manage-realm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
f80a6dfc-2afe-4a6f-8911-70ab128bc7a8	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-users}	manage-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
a377f591-7cbe-4cea-9274-ecb5a1a9d511	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-clients}	manage-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
3102787a-dbfe-4e03-9288-26f36c10f2ed	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-events}	manage-events	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
7b070f77-823e-4e94-9396-fa9797a96f31	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-identity-providers}	manage-identity-providers	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
d04391cb-dfd5-4eb0-b99b-5948bc665419	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_manage-authorization}	manage-authorization	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
73530eeb-3813-4239-a872-efeb420d477b	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_query-users}	query-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
22e124b7-06d3-4a5f-bf6a-36cee4fabb7d	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_query-clients}	query-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
e39507d9-1956-4923-8dc8-d49eb1703ef6	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_query-realms}	query-realms	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
c37bfe11-0fc3-477c-9b59-26b7d6f7cf75	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_query-groups}	query-groups	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
b6f20c14-26de-40e9-82fb-ba01fd527ad5	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_view-profile}	view-profile	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
a4cd6249-d1b7-41e1-b982-02d4d9f07e02	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_manage-account}	manage-account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
3af26b15-2950-46c8-90bc-09422108d641	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_manage-account-links}	manage-account-links	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
3982dd2e-a1a0-4726-8187-180d60ea46d0	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_view-applications}	view-applications	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
6ed4adfb-dbca-4bcf-a4e3-d1c2854a6e35	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_view-consent}	view-consent	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
c1aed06e-bd7c-4177-92f9-b458b5502d70	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_manage-consent}	manage-consent	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
aacd4fa8-4a92-49b6-9190-feb69bf387d2	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_view-groups}	view-groups	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
1919fe44-1763-4b58-a2be-727f552030d7	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	t	${role_delete-account}	delete-account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
0b3f4744-8001-47c1-a68b-305059e46fed	c16b6f5e-b668-47ea-867b-c2c7890d87b0	t	${role_read-token}	read-token	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	c16b6f5e-b668-47ea-867b-c2c7890d87b0	\N
d9ad708c-85dd-463a-90f9-843dd1130c36	65689d62-dc02-448a-b9d9-03ef1cc13408	t	${role_impersonation}	impersonation	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	65689d62-dc02-448a-b9d9-03ef1cc13408	\N
d73dc66d-e96c-472a-9ba0-be8cd7c66018	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	${role_offline-access}	offline_access	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	\N
c85f4104-aea1-4e91-b91f-5a6c75030df7	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	${role_uma_authorization}	uma_authorization	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	\N	\N
a70201c2-473b-4de0-ae31-dc5d227f0640	a8889a90-1082-4a86-b119-30a5eb902930	f	${role_default-roles}	default-roles-ciudadano-consciente	a8889a90-1082-4a86-b119-30a5eb902930	\N	\N
433e4b73-7e4b-4393-8f0b-9d483198ec17	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_create-client}	create-client	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
eb4d6363-1f44-4347-9fdd-70666252932d	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-realm}	view-realm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
d62cdae5-3f58-4e45-9faa-e57e4989a9e5	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-users}	view-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
df45c74a-c1b1-4a82-a277-2a3e06d5cab5	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-clients}	view-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
25f417e2-84ee-42d9-9491-47c843fdfb6a	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-events}	view-events	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
5a846985-eee4-4385-a832-fc4cd11ac929	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-identity-providers}	view-identity-providers	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
73998982-3ff5-4f22-a0ef-a9160c415f09	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_view-authorization}	view-authorization	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
66564d6a-2814-49e2-a39c-450f5369e92b	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-realm}	manage-realm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
75efba1e-0d39-42f8-b847-c1a220026404	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-users}	manage-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
66f87c3a-3c94-4391-a579-e383d753be56	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-clients}	manage-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
72a2ab55-e318-4c84-98fe-5e7f4a460ac1	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-events}	manage-events	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
ce3b6433-f462-4169-94a7-99816cd665a7	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-identity-providers}	manage-identity-providers	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
ec1f0c20-bff5-453d-9976-c6c71637b057	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_manage-authorization}	manage-authorization	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
349b1b34-184e-4522-9577-25e2730f180f	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_query-users}	query-users	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
7d6fb956-0961-4c4b-935a-a4085388e40a	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_query-clients}	query-clients	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
ee360ae4-b864-4027-a94e-f4cd5d4d1a50	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_query-realms}	query-realms	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
bcf4775c-0ea9-46f5-ae9c-3a17a9ac8c98	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_query-groups}	query-groups	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
a241695d-7d26-4dc2-81ac-6c955cdf85be	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_realm-admin}	realm-admin	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
c3c5d2c3-520d-4ca2-8c45-4bb2feadbb22	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_create-client}	create-client	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
acfd7a83-3767-4ed5-b228-4ab8e40e32b1	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-realm}	view-realm	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
547c259d-7460-41eb-9df0-1c2944bbe9f9	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-users}	view-users	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
3d714d77-f36c-40e7-bf6e-00d327c6ae5c	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-clients}	view-clients	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
dc255097-6f22-46c0-8c23-e0796a52253b	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-events}	view-events	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
e431886d-665d-4f2e-a327-85634ede5b6b	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-identity-providers}	view-identity-providers	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
1979e94e-e165-4027-bca2-8523c5fec0a8	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_view-authorization}	view-authorization	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
691c58b3-1be1-4a21-9881-23943e355fc4	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-realm}	manage-realm	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
e210d24e-243f-4b3a-bfa5-1327437af14c	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-users}	manage-users	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
88db8d7e-4394-4f10-a384-eaba5d31d997	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-clients}	manage-clients	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
2da10312-dd98-4bc5-8c18-c6496fc2f76a	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-events}	manage-events	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
4f58c4bf-da86-46ad-8f57-047e5901a54d	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-identity-providers}	manage-identity-providers	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
edd9cba3-0393-4905-b3ff-9d02b033859f	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_manage-authorization}	manage-authorization	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
34a0c541-1412-453b-88b3-32e7f9bb2905	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_query-users}	query-users	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
0e1a32b0-96e6-4e07-9a73-7a664f00f688	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_query-clients}	query-clients	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
1870ae9a-991c-40a3-9dfb-ac859ce5f79b	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_query-realms}	query-realms	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
111d4193-199d-4e70-b1e3-29156d8d66a1	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_query-groups}	query-groups	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
f5208b63-6e4c-4110-81c0-7a7706d7fa3b	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_view-profile}	view-profile	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
5a39fd04-5966-4c98-80cc-905c0b505f74	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_manage-account}	manage-account	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
3d1b6bac-b5d4-4589-bd6b-c01baa86538f	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_manage-account-links}	manage-account-links	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
55f5b210-b8e8-410a-b6c5-5a46acb6ccd6	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_view-applications}	view-applications	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
676861ad-fb6c-4bb5-b077-a4d7b42982c6	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_view-consent}	view-consent	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
249a56a3-0578-442b-b660-c57dd7dcb613	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_manage-consent}	manage-consent	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
9ad4ee24-991c-4fac-b118-4f0f65e2c3e0	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_view-groups}	view-groups	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
3628fd04-3b0a-4317-a3de-8484b220a8bb	fe9505a3-aa97-4b00-b592-a91755146d6b	t	${role_delete-account}	delete-account	a8889a90-1082-4a86-b119-30a5eb902930	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
4d8423f9-20af-4a69-9444-4fb6e828d569	9e460505-75f0-4a8d-8872-363be35570f0	t	${role_impersonation}	impersonation	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	9e460505-75f0-4a8d-8872-363be35570f0	\N
692797e7-3049-4af2-a671-874d3409960d	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	t	${role_impersonation}	impersonation	a8889a90-1082-4a86-b119-30a5eb902930	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
86b60f39-a131-45d0-a851-c97a40df53be	cf17bdb0-f988-4363-b0c9-4a3af34411ff	t	${role_read-token}	read-token	a8889a90-1082-4a86-b119-30a5eb902930	cf17bdb0-f988-4363-b0c9-4a3af34411ff	\N
c11316fa-9f53-4094-a37f-27e447c9dc6b	a8889a90-1082-4a86-b119-30a5eb902930	f	${role_offline-access}	offline_access	a8889a90-1082-4a86-b119-30a5eb902930	\N	\N
56387382-557a-4c4f-bd95-25983202372c	a8889a90-1082-4a86-b119-30a5eb902930	f	${role_uma_authorization}	uma_authorization	a8889a90-1082-4a86-b119-30a5eb902930	\N	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.migration_model (id, version, update_time) FROM stdin;
33pdt	23.0.4	1706053328
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
58d9a687-7848-4904-871a-803e752f3755	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	2cdcb225-507d-46c8-9eac-e0f844e2d9ce	\N
e6919eaf-8cd3-4980-9518-8f28aebfa845	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	9601986b-5fb5-4321-8bf5-fae31332ac78	\N
811498a8-4219-4131-9aed-df5b20dda0de	audience resolve	openid-connect	oidc-audience-resolve-mapper	9601986b-5fb5-4321-8bf5-fae31332ac78	\N
a2177361-7466-4032-9304-51ef40930120	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	c16b6f5e-b668-47ea-867b-c2c7890d87b0	\N
d40aee31-9dbc-4249-88c8-65ae5f8d1112	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	52386ca8-8246-4307-9a08-f59db387c7df	\N
8dadcb1a-a384-40cd-a6e8-08c356b90a34	locale	openid-connect	oidc-usermodel-attribute-mapper	52386ca8-8246-4307-9a08-f59db387c7df	\N
e6c7c492-74ef-405d-bc46-392909b860b6	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	2d329ef4-36b7-476f-807b-76146a967cff	\N
f83f9258-21e7-45fb-bac4-83791a32f9d5	role list	saml	saml-role-list-mapper	\N	1bf2757c-511e-47e7-9163-812e33e53388
e0e75208-4d53-44f9-a60b-1a404a24f133	full name	openid-connect	oidc-full-name-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
f66d53d8-6cfa-4d61-97a9-0258ac652730	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
8460b560-8c95-461f-9c74-454519474c7a	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
8966aa22-567f-46c6-a93c-a0d9e4621e9e	username	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
3800ef24-c361-491e-9d43-4891f90f82f0	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
b0422591-776d-4b05-a44e-1ffd6920b5c5	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	website	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
8bca98a9-1818-4245-8308-3c831899d973	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
b6d26e49-a5ba-4212-972d-64ca234f9dac	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
067bd3f6-119f-4965-91dd-7b83a9c44fe7	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
d8d15a0a-6eaa-4967-995f-670bab239875	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
69821730-c53b-40e1-83b8-e369269d0b1e	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	498d649e-0fa2-4151-b9f8-99a88d123a1b
c5f13035-6bb9-4b58-a510-05f64a813740	email	openid-connect	oidc-usermodel-attribute-mapper	\N	98b97d33-bb00-4670-baf5-b31fd9d07979
71c485f1-b37e-4301-8426-993120b585df	email verified	openid-connect	oidc-usermodel-property-mapper	\N	98b97d33-bb00-4670-baf5-b31fd9d07979
699ec287-8f40-4bac-9771-80659dab6fe5	address	openid-connect	oidc-address-mapper	\N	66265ecf-1071-4cf7-a47c-30a95449366b
89a977cd-5229-401f-8a8f-9b04aae24e30	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	08e82c07-d990-46d5-a520-90c23f73a65b
5aaa46d3-a575-42e0-90cb-c697afc21f16	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	08e82c07-d990-46d5-a520-90c23f73a65b
2990a2af-b9f9-4232-98d1-4ae7128bbc87	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	c55315b3-af64-48b8-8249-506ec504279e
585787a7-f859-4cb2-aea7-f1e02afea996	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	c55315b3-af64-48b8-8249-506ec504279e
c5e3849f-4341-4c55-a797-ba3fb9c9f631	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	c55315b3-af64-48b8-8249-506ec504279e
6533567c-6692-4af6-bef1-21f08e656a23	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	940da7b1-fa8c-4efb-85eb-e501bade836c
2bf71313-f30e-47d5-a6de-abc864a59b6e	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	f700edc9-6f03-45f6-8337-7d952327bfb0
1e154f84-9036-41cf-929f-ec1714d81c65	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	f700edc9-6f03-45f6-8337-7d952327bfb0
36eeee3d-ebdf-46c3-a821-db33b845eb3e	acr loa level	openid-connect	oidc-acr-mapper	\N	624dda8a-a872-4ec5-9c7f-b7d611afe5f1
a0b1e928-b2a2-48ac-a4e9-a87bebe9190d	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	ec0e0a3f-6fca-4e8d-a824-60a7d753f1be	\N
acb1a8e1-756d-4d8e-9ce0-ab21ae0b4f2d	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	fe9505a3-aa97-4b00-b592-a91755146d6b	\N
78e0fd39-6220-431d-a79e-1c84aff0dc28	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	d68566c6-8172-4bb1-aa7b-26f1df1c5346	\N
d148fdca-c9a9-4f80-96f2-03ca96d45bea	audience resolve	openid-connect	oidc-audience-resolve-mapper	d68566c6-8172-4bb1-aa7b-26f1df1c5346	\N
3bc033c4-0d7c-4fea-b9de-747c8afdb4db	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	cf17bdb0-f988-4363-b0c9-4a3af34411ff	\N
dc9e81c2-fd5a-4680-b963-272129fb0865	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	782ab103-aafa-4993-90f2-05b1cdb47a9e	\N
59c7aa49-0a89-4091-bd1d-0efb86f73f46	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	5abc18c6-e21c-4029-bd4d-2b1829184881	\N
a79e9003-1fa9-433f-b3ba-8ca64e0a95b0	role list	saml	saml-role-list-mapper	\N	f6fcda75-9013-49dc-8709-942cae10193c
0fa52234-e83f-48a2-bbbe-2a755010c5a6	full name	openid-connect	oidc-full-name-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
1437bd53-03e4-4624-b073-32fe7b40971b	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
a272cabb-ae0a-4ef3-83b5-b6027296b241	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
593cf661-3405-43aa-ab81-1e22c9c3a4e7	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
9ed5b297-391b-4929-9a86-baa3a54dc6e3	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	username	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
d2a043e3-f991-4464-b20c-8a2efe4d5559	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
4721c2d7-6607-49b7-9b14-32d4d8e305e7	website	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
0298c131-d7af-4165-a61c-25da558ce225	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
c430abe5-5402-481f-b659-44b4e902807e	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	4f5fc994-fe0b-42cc-b5ab-fb6ea958e3b4
96ffa946-62eb-4ca5-858c-e635ef6cac83	email	openid-connect	oidc-usermodel-attribute-mapper	\N	78298940-83da-4217-b6db-6ded30249986
28ee1146-eb24-418b-b141-9eefe05113f1	email verified	openid-connect	oidc-usermodel-property-mapper	\N	78298940-83da-4217-b6db-6ded30249986
93637523-3d54-4234-9f27-cfebfb39451f	address	openid-connect	oidc-address-mapper	\N	224d3d2a-c43f-44ad-b8ba-b74a8fb15c5c
734dd2a8-a185-40ac-9b86-8e7c96a4e125	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9
8a41269c-6cc4-45ab-b1dd-861c7762c484	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	d589727d-3cbb-4d42-b2de-1c8e70f8d9e9
2725436d-dc3a-40e3-a1f2-3f09b9902324	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	35686e6c-6fed-44d1-b464-29ba0835dfa7
7c2e292f-f121-498c-91d9-d14ef395ba3f	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	35686e6c-6fed-44d1-b464-29ba0835dfa7
e1de5dbe-22cb-4abc-aeaa-3acc7930d15c	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	35686e6c-6fed-44d1-b464-29ba0835dfa7
884afa5b-04ad-492d-a741-a64ad2734bc2	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	06c8e679-b22c-40e7-a162-4647618940b5
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	23591340-67c1-4838-982d-63786c93f989
e940f727-6c3d-481e-baa5-5330f929f5f5	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	23591340-67c1-4838-982d-63786c93f989
576895b3-4d0d-4a89-9cf7-5cbbbd6a647a	acr loa level	openid-connect	oidc-acr-mapper	\N	60c7d281-0d13-4a7f-9eab-54eb511b8122
72a46ccf-7096-4e10-9147-27873450ab42	locale	openid-connect	oidc-usermodel-attribute-mapper	782ab103-aafa-4993-90f2-05b1cdb47a9e	\N
9a7b2e04-3308-4549-b2ee-23f89eb60f9b	docker-v2-allow-all-mapper	docker-v2	docker-v2-allow-all-mapper	f07fae5e-cd31-437b-9cdb-8892c673d027	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
8dadcb1a-a384-40cd-a6e8-08c356b90a34	true	introspection.token.claim
8dadcb1a-a384-40cd-a6e8-08c356b90a34	true	userinfo.token.claim
8dadcb1a-a384-40cd-a6e8-08c356b90a34	locale	user.attribute
8dadcb1a-a384-40cd-a6e8-08c356b90a34	true	id.token.claim
8dadcb1a-a384-40cd-a6e8-08c356b90a34	true	access.token.claim
8dadcb1a-a384-40cd-a6e8-08c356b90a34	locale	claim.name
8dadcb1a-a384-40cd-a6e8-08c356b90a34	String	jsonType.label
f83f9258-21e7-45fb-bac4-83791a32f9d5	false	single
f83f9258-21e7-45fb-bac4-83791a32f9d5	Basic	attribute.nameformat
f83f9258-21e7-45fb-bac4-83791a32f9d5	Role	attribute.name
067bd3f6-119f-4965-91dd-7b83a9c44fe7	true	introspection.token.claim
067bd3f6-119f-4965-91dd-7b83a9c44fe7	true	userinfo.token.claim
067bd3f6-119f-4965-91dd-7b83a9c44fe7	zoneinfo	user.attribute
067bd3f6-119f-4965-91dd-7b83a9c44fe7	true	id.token.claim
067bd3f6-119f-4965-91dd-7b83a9c44fe7	true	access.token.claim
067bd3f6-119f-4965-91dd-7b83a9c44fe7	zoneinfo	claim.name
067bd3f6-119f-4965-91dd-7b83a9c44fe7	String	jsonType.label
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	true	introspection.token.claim
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	true	userinfo.token.claim
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	middleName	user.attribute
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	true	id.token.claim
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	true	access.token.claim
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	middle_name	claim.name
27cbbb4b-01eb-4a7b-8cf9-bb0e57e8c174	String	jsonType.label
3800ef24-c361-491e-9d43-4891f90f82f0	true	introspection.token.claim
3800ef24-c361-491e-9d43-4891f90f82f0	true	userinfo.token.claim
3800ef24-c361-491e-9d43-4891f90f82f0	profile	user.attribute
3800ef24-c361-491e-9d43-4891f90f82f0	true	id.token.claim
3800ef24-c361-491e-9d43-4891f90f82f0	true	access.token.claim
3800ef24-c361-491e-9d43-4891f90f82f0	profile	claim.name
3800ef24-c361-491e-9d43-4891f90f82f0	String	jsonType.label
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	true	introspection.token.claim
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	true	userinfo.token.claim
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	lastName	user.attribute
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	true	id.token.claim
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	true	access.token.claim
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	family_name	claim.name
4baee967-4bf0-4f15-bde4-c2c2f0418ea5	String	jsonType.label
69821730-c53b-40e1-83b8-e369269d0b1e	true	introspection.token.claim
69821730-c53b-40e1-83b8-e369269d0b1e	true	userinfo.token.claim
69821730-c53b-40e1-83b8-e369269d0b1e	updatedAt	user.attribute
69821730-c53b-40e1-83b8-e369269d0b1e	true	id.token.claim
69821730-c53b-40e1-83b8-e369269d0b1e	true	access.token.claim
69821730-c53b-40e1-83b8-e369269d0b1e	updated_at	claim.name
69821730-c53b-40e1-83b8-e369269d0b1e	long	jsonType.label
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	true	introspection.token.claim
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	true	userinfo.token.claim
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	website	user.attribute
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	true	id.token.claim
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	true	access.token.claim
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	website	claim.name
6b023e90-c7ef-40c8-b848-32d7c0ed72fd	String	jsonType.label
8460b560-8c95-461f-9c74-454519474c7a	true	introspection.token.claim
8460b560-8c95-461f-9c74-454519474c7a	true	userinfo.token.claim
8460b560-8c95-461f-9c74-454519474c7a	nickname	user.attribute
8460b560-8c95-461f-9c74-454519474c7a	true	id.token.claim
8460b560-8c95-461f-9c74-454519474c7a	true	access.token.claim
8460b560-8c95-461f-9c74-454519474c7a	nickname	claim.name
8460b560-8c95-461f-9c74-454519474c7a	String	jsonType.label
8966aa22-567f-46c6-a93c-a0d9e4621e9e	true	introspection.token.claim
8966aa22-567f-46c6-a93c-a0d9e4621e9e	true	userinfo.token.claim
8966aa22-567f-46c6-a93c-a0d9e4621e9e	username	user.attribute
8966aa22-567f-46c6-a93c-a0d9e4621e9e	true	id.token.claim
8966aa22-567f-46c6-a93c-a0d9e4621e9e	true	access.token.claim
8966aa22-567f-46c6-a93c-a0d9e4621e9e	preferred_username	claim.name
8966aa22-567f-46c6-a93c-a0d9e4621e9e	String	jsonType.label
8bca98a9-1818-4245-8308-3c831899d973	true	introspection.token.claim
8bca98a9-1818-4245-8308-3c831899d973	true	userinfo.token.claim
8bca98a9-1818-4245-8308-3c831899d973	gender	user.attribute
8bca98a9-1818-4245-8308-3c831899d973	true	id.token.claim
8bca98a9-1818-4245-8308-3c831899d973	true	access.token.claim
8bca98a9-1818-4245-8308-3c831899d973	gender	claim.name
8bca98a9-1818-4245-8308-3c831899d973	String	jsonType.label
b0422591-776d-4b05-a44e-1ffd6920b5c5	true	introspection.token.claim
b0422591-776d-4b05-a44e-1ffd6920b5c5	true	userinfo.token.claim
b0422591-776d-4b05-a44e-1ffd6920b5c5	picture	user.attribute
b0422591-776d-4b05-a44e-1ffd6920b5c5	true	id.token.claim
b0422591-776d-4b05-a44e-1ffd6920b5c5	true	access.token.claim
b0422591-776d-4b05-a44e-1ffd6920b5c5	picture	claim.name
b0422591-776d-4b05-a44e-1ffd6920b5c5	String	jsonType.label
b6d26e49-a5ba-4212-972d-64ca234f9dac	true	introspection.token.claim
b6d26e49-a5ba-4212-972d-64ca234f9dac	true	userinfo.token.claim
b6d26e49-a5ba-4212-972d-64ca234f9dac	birthdate	user.attribute
b6d26e49-a5ba-4212-972d-64ca234f9dac	true	id.token.claim
b6d26e49-a5ba-4212-972d-64ca234f9dac	true	access.token.claim
b6d26e49-a5ba-4212-972d-64ca234f9dac	birthdate	claim.name
b6d26e49-a5ba-4212-972d-64ca234f9dac	String	jsonType.label
d8d15a0a-6eaa-4967-995f-670bab239875	true	introspection.token.claim
d8d15a0a-6eaa-4967-995f-670bab239875	true	userinfo.token.claim
d8d15a0a-6eaa-4967-995f-670bab239875	locale	user.attribute
d8d15a0a-6eaa-4967-995f-670bab239875	true	id.token.claim
d8d15a0a-6eaa-4967-995f-670bab239875	true	access.token.claim
d8d15a0a-6eaa-4967-995f-670bab239875	locale	claim.name
d8d15a0a-6eaa-4967-995f-670bab239875	String	jsonType.label
e0e75208-4d53-44f9-a60b-1a404a24f133	true	introspection.token.claim
e0e75208-4d53-44f9-a60b-1a404a24f133	true	userinfo.token.claim
e0e75208-4d53-44f9-a60b-1a404a24f133	true	id.token.claim
e0e75208-4d53-44f9-a60b-1a404a24f133	true	access.token.claim
f66d53d8-6cfa-4d61-97a9-0258ac652730	true	introspection.token.claim
f66d53d8-6cfa-4d61-97a9-0258ac652730	true	userinfo.token.claim
f66d53d8-6cfa-4d61-97a9-0258ac652730	firstName	user.attribute
f66d53d8-6cfa-4d61-97a9-0258ac652730	true	id.token.claim
f66d53d8-6cfa-4d61-97a9-0258ac652730	true	access.token.claim
f66d53d8-6cfa-4d61-97a9-0258ac652730	given_name	claim.name
f66d53d8-6cfa-4d61-97a9-0258ac652730	String	jsonType.label
71c485f1-b37e-4301-8426-993120b585df	true	introspection.token.claim
71c485f1-b37e-4301-8426-993120b585df	true	userinfo.token.claim
71c485f1-b37e-4301-8426-993120b585df	emailVerified	user.attribute
71c485f1-b37e-4301-8426-993120b585df	true	id.token.claim
71c485f1-b37e-4301-8426-993120b585df	true	access.token.claim
71c485f1-b37e-4301-8426-993120b585df	email_verified	claim.name
71c485f1-b37e-4301-8426-993120b585df	boolean	jsonType.label
c5f13035-6bb9-4b58-a510-05f64a813740	true	introspection.token.claim
c5f13035-6bb9-4b58-a510-05f64a813740	true	userinfo.token.claim
c5f13035-6bb9-4b58-a510-05f64a813740	email	user.attribute
c5f13035-6bb9-4b58-a510-05f64a813740	true	id.token.claim
c5f13035-6bb9-4b58-a510-05f64a813740	true	access.token.claim
c5f13035-6bb9-4b58-a510-05f64a813740	email	claim.name
c5f13035-6bb9-4b58-a510-05f64a813740	String	jsonType.label
699ec287-8f40-4bac-9771-80659dab6fe5	formatted	user.attribute.formatted
699ec287-8f40-4bac-9771-80659dab6fe5	country	user.attribute.country
699ec287-8f40-4bac-9771-80659dab6fe5	true	introspection.token.claim
699ec287-8f40-4bac-9771-80659dab6fe5	postal_code	user.attribute.postal_code
699ec287-8f40-4bac-9771-80659dab6fe5	true	userinfo.token.claim
699ec287-8f40-4bac-9771-80659dab6fe5	street	user.attribute.street
699ec287-8f40-4bac-9771-80659dab6fe5	true	id.token.claim
699ec287-8f40-4bac-9771-80659dab6fe5	region	user.attribute.region
699ec287-8f40-4bac-9771-80659dab6fe5	true	access.token.claim
699ec287-8f40-4bac-9771-80659dab6fe5	locality	user.attribute.locality
5aaa46d3-a575-42e0-90cb-c697afc21f16	true	introspection.token.claim
5aaa46d3-a575-42e0-90cb-c697afc21f16	true	userinfo.token.claim
5aaa46d3-a575-42e0-90cb-c697afc21f16	phoneNumberVerified	user.attribute
5aaa46d3-a575-42e0-90cb-c697afc21f16	true	id.token.claim
5aaa46d3-a575-42e0-90cb-c697afc21f16	true	access.token.claim
5aaa46d3-a575-42e0-90cb-c697afc21f16	phone_number_verified	claim.name
5aaa46d3-a575-42e0-90cb-c697afc21f16	boolean	jsonType.label
89a977cd-5229-401f-8a8f-9b04aae24e30	true	introspection.token.claim
89a977cd-5229-401f-8a8f-9b04aae24e30	true	userinfo.token.claim
89a977cd-5229-401f-8a8f-9b04aae24e30	phoneNumber	user.attribute
89a977cd-5229-401f-8a8f-9b04aae24e30	true	id.token.claim
89a977cd-5229-401f-8a8f-9b04aae24e30	true	access.token.claim
89a977cd-5229-401f-8a8f-9b04aae24e30	phone_number	claim.name
89a977cd-5229-401f-8a8f-9b04aae24e30	String	jsonType.label
2990a2af-b9f9-4232-98d1-4ae7128bbc87	true	introspection.token.claim
2990a2af-b9f9-4232-98d1-4ae7128bbc87	true	multivalued
2990a2af-b9f9-4232-98d1-4ae7128bbc87	foo	user.attribute
2990a2af-b9f9-4232-98d1-4ae7128bbc87	true	access.token.claim
2990a2af-b9f9-4232-98d1-4ae7128bbc87	realm_access.roles	claim.name
2990a2af-b9f9-4232-98d1-4ae7128bbc87	String	jsonType.label
585787a7-f859-4cb2-aea7-f1e02afea996	true	introspection.token.claim
585787a7-f859-4cb2-aea7-f1e02afea996	true	multivalued
585787a7-f859-4cb2-aea7-f1e02afea996	foo	user.attribute
585787a7-f859-4cb2-aea7-f1e02afea996	true	access.token.claim
585787a7-f859-4cb2-aea7-f1e02afea996	resource_access.${client_id}.roles	claim.name
585787a7-f859-4cb2-aea7-f1e02afea996	String	jsonType.label
c5e3849f-4341-4c55-a797-ba3fb9c9f631	true	introspection.token.claim
c5e3849f-4341-4c55-a797-ba3fb9c9f631	true	access.token.claim
6533567c-6692-4af6-bef1-21f08e656a23	true	introspection.token.claim
6533567c-6692-4af6-bef1-21f08e656a23	true	access.token.claim
1e154f84-9036-41cf-929f-ec1714d81c65	true	introspection.token.claim
1e154f84-9036-41cf-929f-ec1714d81c65	true	multivalued
1e154f84-9036-41cf-929f-ec1714d81c65	foo	user.attribute
1e154f84-9036-41cf-929f-ec1714d81c65	true	id.token.claim
1e154f84-9036-41cf-929f-ec1714d81c65	true	access.token.claim
1e154f84-9036-41cf-929f-ec1714d81c65	groups	claim.name
1e154f84-9036-41cf-929f-ec1714d81c65	String	jsonType.label
2bf71313-f30e-47d5-a6de-abc864a59b6e	true	introspection.token.claim
2bf71313-f30e-47d5-a6de-abc864a59b6e	true	userinfo.token.claim
2bf71313-f30e-47d5-a6de-abc864a59b6e	username	user.attribute
2bf71313-f30e-47d5-a6de-abc864a59b6e	true	id.token.claim
2bf71313-f30e-47d5-a6de-abc864a59b6e	true	access.token.claim
2bf71313-f30e-47d5-a6de-abc864a59b6e	upn	claim.name
2bf71313-f30e-47d5-a6de-abc864a59b6e	String	jsonType.label
36eeee3d-ebdf-46c3-a821-db33b845eb3e	true	introspection.token.claim
36eeee3d-ebdf-46c3-a821-db33b845eb3e	true	id.token.claim
36eeee3d-ebdf-46c3-a821-db33b845eb3e	true	access.token.claim
a79e9003-1fa9-433f-b3ba-8ca64e0a95b0	false	single
a79e9003-1fa9-433f-b3ba-8ca64e0a95b0	Basic	attribute.nameformat
a79e9003-1fa9-433f-b3ba-8ca64e0a95b0	Role	attribute.name
0298c131-d7af-4165-a61c-25da558ce225	true	introspection.token.claim
0298c131-d7af-4165-a61c-25da558ce225	true	userinfo.token.claim
0298c131-d7af-4165-a61c-25da558ce225	gender	user.attribute
0298c131-d7af-4165-a61c-25da558ce225	true	id.token.claim
0298c131-d7af-4165-a61c-25da558ce225	true	access.token.claim
0298c131-d7af-4165-a61c-25da558ce225	gender	claim.name
0298c131-d7af-4165-a61c-25da558ce225	String	jsonType.label
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	true	introspection.token.claim
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	true	userinfo.token.claim
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	locale	user.attribute
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	true	id.token.claim
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	true	access.token.claim
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	locale	claim.name
0a7275eb-b3d4-4d02-8c59-de41f6dc2f22	String	jsonType.label
0fa52234-e83f-48a2-bbbe-2a755010c5a6	true	introspection.token.claim
0fa52234-e83f-48a2-bbbe-2a755010c5a6	true	userinfo.token.claim
0fa52234-e83f-48a2-bbbe-2a755010c5a6	true	id.token.claim
0fa52234-e83f-48a2-bbbe-2a755010c5a6	true	access.token.claim
1437bd53-03e4-4624-b073-32fe7b40971b	true	introspection.token.claim
1437bd53-03e4-4624-b073-32fe7b40971b	true	userinfo.token.claim
1437bd53-03e4-4624-b073-32fe7b40971b	lastName	user.attribute
1437bd53-03e4-4624-b073-32fe7b40971b	true	id.token.claim
1437bd53-03e4-4624-b073-32fe7b40971b	true	access.token.claim
1437bd53-03e4-4624-b073-32fe7b40971b	family_name	claim.name
1437bd53-03e4-4624-b073-32fe7b40971b	String	jsonType.label
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	true	introspection.token.claim
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	true	userinfo.token.claim
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	profile	user.attribute
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	true	id.token.claim
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	true	access.token.claim
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	profile	claim.name
1d31d5a3-abe6-4320-b0e4-6878d7fd6beb	String	jsonType.label
4721c2d7-6607-49b7-9b14-32d4d8e305e7	true	introspection.token.claim
4721c2d7-6607-49b7-9b14-32d4d8e305e7	true	userinfo.token.claim
4721c2d7-6607-49b7-9b14-32d4d8e305e7	website	user.attribute
4721c2d7-6607-49b7-9b14-32d4d8e305e7	true	id.token.claim
4721c2d7-6607-49b7-9b14-32d4d8e305e7	true	access.token.claim
4721c2d7-6607-49b7-9b14-32d4d8e305e7	website	claim.name
4721c2d7-6607-49b7-9b14-32d4d8e305e7	String	jsonType.label
593cf661-3405-43aa-ab81-1e22c9c3a4e7	true	introspection.token.claim
593cf661-3405-43aa-ab81-1e22c9c3a4e7	true	userinfo.token.claim
593cf661-3405-43aa-ab81-1e22c9c3a4e7	middleName	user.attribute
593cf661-3405-43aa-ab81-1e22c9c3a4e7	true	id.token.claim
593cf661-3405-43aa-ab81-1e22c9c3a4e7	true	access.token.claim
593cf661-3405-43aa-ab81-1e22c9c3a4e7	middle_name	claim.name
593cf661-3405-43aa-ab81-1e22c9c3a4e7	String	jsonType.label
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	true	introspection.token.claim
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	true	userinfo.token.claim
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	birthdate	user.attribute
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	true	id.token.claim
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	true	access.token.claim
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	birthdate	claim.name
7c1ef216-c09e-47bd-9bc4-05c3c544a4e8	String	jsonType.label
9ed5b297-391b-4929-9a86-baa3a54dc6e3	true	introspection.token.claim
9ed5b297-391b-4929-9a86-baa3a54dc6e3	true	userinfo.token.claim
9ed5b297-391b-4929-9a86-baa3a54dc6e3	nickname	user.attribute
9ed5b297-391b-4929-9a86-baa3a54dc6e3	true	id.token.claim
9ed5b297-391b-4929-9a86-baa3a54dc6e3	true	access.token.claim
9ed5b297-391b-4929-9a86-baa3a54dc6e3	nickname	claim.name
9ed5b297-391b-4929-9a86-baa3a54dc6e3	String	jsonType.label
a272cabb-ae0a-4ef3-83b5-b6027296b241	true	introspection.token.claim
a272cabb-ae0a-4ef3-83b5-b6027296b241	true	userinfo.token.claim
a272cabb-ae0a-4ef3-83b5-b6027296b241	firstName	user.attribute
a272cabb-ae0a-4ef3-83b5-b6027296b241	true	id.token.claim
a272cabb-ae0a-4ef3-83b5-b6027296b241	true	access.token.claim
a272cabb-ae0a-4ef3-83b5-b6027296b241	given_name	claim.name
a272cabb-ae0a-4ef3-83b5-b6027296b241	String	jsonType.label
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	true	introspection.token.claim
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	true	userinfo.token.claim
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	zoneinfo	user.attribute
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	true	id.token.claim
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	true	access.token.claim
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	zoneinfo	claim.name
a32ea6c2-97b4-4a80-a8a6-1a265ecd05fd	String	jsonType.label
c430abe5-5402-481f-b659-44b4e902807e	true	introspection.token.claim
c430abe5-5402-481f-b659-44b4e902807e	true	userinfo.token.claim
c430abe5-5402-481f-b659-44b4e902807e	updatedAt	user.attribute
c430abe5-5402-481f-b659-44b4e902807e	true	id.token.claim
c430abe5-5402-481f-b659-44b4e902807e	true	access.token.claim
c430abe5-5402-481f-b659-44b4e902807e	updated_at	claim.name
c430abe5-5402-481f-b659-44b4e902807e	long	jsonType.label
d2a043e3-f991-4464-b20c-8a2efe4d5559	true	introspection.token.claim
d2a043e3-f991-4464-b20c-8a2efe4d5559	true	userinfo.token.claim
d2a043e3-f991-4464-b20c-8a2efe4d5559	picture	user.attribute
d2a043e3-f991-4464-b20c-8a2efe4d5559	true	id.token.claim
d2a043e3-f991-4464-b20c-8a2efe4d5559	true	access.token.claim
d2a043e3-f991-4464-b20c-8a2efe4d5559	picture	claim.name
d2a043e3-f991-4464-b20c-8a2efe4d5559	String	jsonType.label
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	true	introspection.token.claim
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	true	userinfo.token.claim
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	username	user.attribute
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	true	id.token.claim
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	true	access.token.claim
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	preferred_username	claim.name
d3f0a2b1-7e77-4620-a4b9-161438faa1f7	String	jsonType.label
28ee1146-eb24-418b-b141-9eefe05113f1	true	introspection.token.claim
28ee1146-eb24-418b-b141-9eefe05113f1	true	userinfo.token.claim
28ee1146-eb24-418b-b141-9eefe05113f1	emailVerified	user.attribute
28ee1146-eb24-418b-b141-9eefe05113f1	true	id.token.claim
28ee1146-eb24-418b-b141-9eefe05113f1	true	access.token.claim
28ee1146-eb24-418b-b141-9eefe05113f1	email_verified	claim.name
28ee1146-eb24-418b-b141-9eefe05113f1	boolean	jsonType.label
96ffa946-62eb-4ca5-858c-e635ef6cac83	true	introspection.token.claim
96ffa946-62eb-4ca5-858c-e635ef6cac83	true	userinfo.token.claim
96ffa946-62eb-4ca5-858c-e635ef6cac83	email	user.attribute
96ffa946-62eb-4ca5-858c-e635ef6cac83	true	id.token.claim
96ffa946-62eb-4ca5-858c-e635ef6cac83	true	access.token.claim
96ffa946-62eb-4ca5-858c-e635ef6cac83	email	claim.name
96ffa946-62eb-4ca5-858c-e635ef6cac83	String	jsonType.label
93637523-3d54-4234-9f27-cfebfb39451f	formatted	user.attribute.formatted
93637523-3d54-4234-9f27-cfebfb39451f	country	user.attribute.country
93637523-3d54-4234-9f27-cfebfb39451f	true	introspection.token.claim
93637523-3d54-4234-9f27-cfebfb39451f	postal_code	user.attribute.postal_code
93637523-3d54-4234-9f27-cfebfb39451f	true	userinfo.token.claim
93637523-3d54-4234-9f27-cfebfb39451f	street	user.attribute.street
93637523-3d54-4234-9f27-cfebfb39451f	true	id.token.claim
93637523-3d54-4234-9f27-cfebfb39451f	region	user.attribute.region
93637523-3d54-4234-9f27-cfebfb39451f	true	access.token.claim
93637523-3d54-4234-9f27-cfebfb39451f	locality	user.attribute.locality
734dd2a8-a185-40ac-9b86-8e7c96a4e125	true	introspection.token.claim
734dd2a8-a185-40ac-9b86-8e7c96a4e125	true	userinfo.token.claim
734dd2a8-a185-40ac-9b86-8e7c96a4e125	phoneNumber	user.attribute
734dd2a8-a185-40ac-9b86-8e7c96a4e125	true	id.token.claim
734dd2a8-a185-40ac-9b86-8e7c96a4e125	true	access.token.claim
734dd2a8-a185-40ac-9b86-8e7c96a4e125	phone_number	claim.name
734dd2a8-a185-40ac-9b86-8e7c96a4e125	String	jsonType.label
8a41269c-6cc4-45ab-b1dd-861c7762c484	true	introspection.token.claim
8a41269c-6cc4-45ab-b1dd-861c7762c484	true	userinfo.token.claim
8a41269c-6cc4-45ab-b1dd-861c7762c484	phoneNumberVerified	user.attribute
8a41269c-6cc4-45ab-b1dd-861c7762c484	true	id.token.claim
8a41269c-6cc4-45ab-b1dd-861c7762c484	true	access.token.claim
8a41269c-6cc4-45ab-b1dd-861c7762c484	phone_number_verified	claim.name
8a41269c-6cc4-45ab-b1dd-861c7762c484	boolean	jsonType.label
2725436d-dc3a-40e3-a1f2-3f09b9902324	true	introspection.token.claim
2725436d-dc3a-40e3-a1f2-3f09b9902324	true	multivalued
2725436d-dc3a-40e3-a1f2-3f09b9902324	foo	user.attribute
2725436d-dc3a-40e3-a1f2-3f09b9902324	true	access.token.claim
2725436d-dc3a-40e3-a1f2-3f09b9902324	realm_access.roles	claim.name
2725436d-dc3a-40e3-a1f2-3f09b9902324	String	jsonType.label
7c2e292f-f121-498c-91d9-d14ef395ba3f	true	introspection.token.claim
7c2e292f-f121-498c-91d9-d14ef395ba3f	true	multivalued
7c2e292f-f121-498c-91d9-d14ef395ba3f	foo	user.attribute
7c2e292f-f121-498c-91d9-d14ef395ba3f	true	access.token.claim
7c2e292f-f121-498c-91d9-d14ef395ba3f	resource_access.${client_id}.roles	claim.name
7c2e292f-f121-498c-91d9-d14ef395ba3f	String	jsonType.label
e1de5dbe-22cb-4abc-aeaa-3acc7930d15c	true	introspection.token.claim
e1de5dbe-22cb-4abc-aeaa-3acc7930d15c	true	access.token.claim
884afa5b-04ad-492d-a741-a64ad2734bc2	true	introspection.token.claim
884afa5b-04ad-492d-a741-a64ad2734bc2	true	access.token.claim
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	true	introspection.token.claim
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	true	userinfo.token.claim
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	username	user.attribute
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	true	id.token.claim
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	true	access.token.claim
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	upn	claim.name
23ae74f0-7272-4e58-afa2-f16d4aa2cb02	String	jsonType.label
e940f727-6c3d-481e-baa5-5330f929f5f5	true	introspection.token.claim
e940f727-6c3d-481e-baa5-5330f929f5f5	true	multivalued
e940f727-6c3d-481e-baa5-5330f929f5f5	foo	user.attribute
e940f727-6c3d-481e-baa5-5330f929f5f5	true	id.token.claim
e940f727-6c3d-481e-baa5-5330f929f5f5	true	access.token.claim
e940f727-6c3d-481e-baa5-5330f929f5f5	groups	claim.name
e940f727-6c3d-481e-baa5-5330f929f5f5	String	jsonType.label
576895b3-4d0d-4a89-9cf7-5cbbbd6a647a	true	introspection.token.claim
576895b3-4d0d-4a89-9cf7-5cbbbd6a647a	true	id.token.claim
576895b3-4d0d-4a89-9cf7-5cbbbd6a647a	true	access.token.claim
72a46ccf-7096-4e10-9147-27873450ab42	true	introspection.token.claim
72a46ccf-7096-4e10-9147-27873450ab42	true	userinfo.token.claim
72a46ccf-7096-4e10-9147-27873450ab42	locale	user.attribute
72a46ccf-7096-4e10-9147-27873450ab42	true	id.token.claim
72a46ccf-7096-4e10-9147-27873450ab42	true	access.token.claim
72a46ccf-7096-4e10-9147-27873450ab42	locale	claim.name
72a46ccf-7096-4e10-9147-27873450ab42	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
a8889a90-1082-4a86-b119-30a5eb902930	60	300	300	\N	\N	\N	t	f	0	\N	Ciudadano	0	\N	t	t	f	f	EXTERNAL	1800	36000	f	f	9e460505-75f0-4a8d-8872-363be35570f0	1800	t	en	f	f	f	f	0	1	30	6	HmacSHA1	totp	e4103d92-fe3b-4b91-81d2-beee05354144	d67ce31b-20ff-4560-ac7e-ccd3ba9421db	9fcab498-da29-4c7a-b727-927438341bfe	5729bc24-e294-48bd-90fa-5b741f2c4194	92ed5684-4ce4-4897-ab55-2e1f9f7cadf4	2592000	f	900	t	f	c57124a2-1771-4de1-9630-71d4595db326	0	f	0	0	a70201c2-473b-4de0-ae31-dc5d227f0640
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	65689d62-dc02-448a-b9d9-03ef1cc13408	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	2d12047a-f1c7-4bab-8fa0-a8115d761ef8	98e4eb4d-742b-457e-a74c-ac350d2bc602	78eba7ad-34c6-4fdb-b8b8-8e738b613ab5	43bb1bfb-ee38-40f3-af0b-069781ca98f3	995dafb7-ebb2-4a5e-81ca-61f7d94e2dcf	2592000	f	900	t	f	37795034-59ea-4f5d-b11f-ff4712ee13d1	0	f	0	0	afc61b6f-3563-4725-8519-353c90af1649
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	
_browser_header.xContentTypeOptions	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	nosniff
_browser_header.referrerPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	no-referrer
_browser_header.xRobotsTag	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	none
_browser_header.xFrameOptions	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	SAMEORIGIN
_browser_header.contentSecurityPolicy	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	1; mode=block
_browser_header.strictTransportSecurity	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	max-age=31536000; includeSubDomains
bruteForceProtected	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	false
permanentLockout	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	false
maxFailureWaitSeconds	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	900
minimumQuickLoginWaitSeconds	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	60
waitIncrementSeconds	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	60
quickLoginCheckMilliSeconds	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	1000
maxDeltaTimeSeconds	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	43200
failureFactor	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	30
realmReusableOtpCode	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	false
displayName	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	Keycloak
displayNameHtml	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	RS256
offlineSessionMaxLifespanEnabled	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	false
offlineSessionMaxLifespan	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	5184000
realmReusableOtpCode	a8889a90-1082-4a86-b119-30a5eb902930	false
oauth2DeviceCodeLifespan	a8889a90-1082-4a86-b119-30a5eb902930	600
oauth2DevicePollingInterval	a8889a90-1082-4a86-b119-30a5eb902930	5
cibaBackchannelTokenDeliveryMode	a8889a90-1082-4a86-b119-30a5eb902930	poll
cibaExpiresIn	a8889a90-1082-4a86-b119-30a5eb902930	120
cibaInterval	a8889a90-1082-4a86-b119-30a5eb902930	5
cibaAuthRequestedUserHint	a8889a90-1082-4a86-b119-30a5eb902930	login_hint
parRequestUriLifespan	a8889a90-1082-4a86-b119-30a5eb902930	60
displayName	a8889a90-1082-4a86-b119-30a5eb902930	Bienvenido a Ciudadano Consciente
displayNameHtml	a8889a90-1082-4a86-b119-30a5eb902930	Bienvenido a Ciudadano Consciente
bruteForceProtected	a8889a90-1082-4a86-b119-30a5eb902930	false
permanentLockout	a8889a90-1082-4a86-b119-30a5eb902930	false
maxFailureWaitSeconds	a8889a90-1082-4a86-b119-30a5eb902930	900
minimumQuickLoginWaitSeconds	a8889a90-1082-4a86-b119-30a5eb902930	60
clientSessionIdleTimeout	a8889a90-1082-4a86-b119-30a5eb902930	0
clientSessionMaxLifespan	a8889a90-1082-4a86-b119-30a5eb902930	0
clientOfflineSessionIdleTimeout	a8889a90-1082-4a86-b119-30a5eb902930	0
clientOfflineSessionMaxLifespan	a8889a90-1082-4a86-b119-30a5eb902930	0
waitIncrementSeconds	a8889a90-1082-4a86-b119-30a5eb902930	60
quickLoginCheckMilliSeconds	a8889a90-1082-4a86-b119-30a5eb902930	1000
maxDeltaTimeSeconds	a8889a90-1082-4a86-b119-30a5eb902930	43200
failureFactor	a8889a90-1082-4a86-b119-30a5eb902930	30
actionTokenGeneratedByAdminLifespan	a8889a90-1082-4a86-b119-30a5eb902930	43200
actionTokenGeneratedByUserLifespan	a8889a90-1082-4a86-b119-30a5eb902930	300
defaultSignatureAlgorithm	a8889a90-1082-4a86-b119-30a5eb902930	RS256
offlineSessionMaxLifespanEnabled	a8889a90-1082-4a86-b119-30a5eb902930	false
offlineSessionMaxLifespan	a8889a90-1082-4a86-b119-30a5eb902930	5184000
webAuthnPolicyRpEntityName	a8889a90-1082-4a86-b119-30a5eb902930	keycloak
webAuthnPolicySignatureAlgorithms	a8889a90-1082-4a86-b119-30a5eb902930	ES256
webAuthnPolicyRpId	a8889a90-1082-4a86-b119-30a5eb902930	
webAuthnPolicyAttestationConveyancePreference	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyAuthenticatorAttachment	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyRequireResidentKey	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyUserVerificationRequirement	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyCreateTimeout	a8889a90-1082-4a86-b119-30a5eb902930	0
webAuthnPolicyAvoidSameAuthenticatorRegister	a8889a90-1082-4a86-b119-30a5eb902930	false
webAuthnPolicyRpEntityNamePasswordless	a8889a90-1082-4a86-b119-30a5eb902930	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	ES256
webAuthnPolicyRpIdPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	
webAuthnPolicyAttestationConveyancePreferencePasswordless	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyRequireResidentKeyPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	not specified
webAuthnPolicyCreateTimeoutPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	a8889a90-1082-4a86-b119-30a5eb902930	false
client-policies.profiles	a8889a90-1082-4a86-b119-30a5eb902930	{"profiles":[]}
frontendUrl	a8889a90-1082-4a86-b119-30a5eb902930	
acr.loa.map	a8889a90-1082-4a86-b119-30a5eb902930	{}
client-policies.policies	a8889a90-1082-4a86-b119-30a5eb902930	{"policies":[]}
_browser_header.contentSecurityPolicyReportOnly	a8889a90-1082-4a86-b119-30a5eb902930	
_browser_header.xContentTypeOptions	a8889a90-1082-4a86-b119-30a5eb902930	nosniff
_browser_header.referrerPolicy	a8889a90-1082-4a86-b119-30a5eb902930	no-referrer
_browser_header.xRobotsTag	a8889a90-1082-4a86-b119-30a5eb902930	none
_browser_header.xFrameOptions	a8889a90-1082-4a86-b119-30a5eb902930	SAMEORIGIN
_browser_header.contentSecurityPolicy	a8889a90-1082-4a86-b119-30a5eb902930	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	a8889a90-1082-4a86-b119-30a5eb902930	1; mode=block
_browser_header.strictTransportSecurity	a8889a90-1082-4a86-b119-30a5eb902930	max-age=31536000; includeSubDomains
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_events_listeners (realm_id, value) FROM stdin;
d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	jboss-logging
a8889a90-1082-4a86-b119-30a5eb902930	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b
password	password	t	t	a8889a90-1082-4a86-b119-30a5eb902930
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_smtp_config (realm_id, value, name) FROM stdin;
a8889a90-1082-4a86-b119-30a5eb902930		replyToDisplayName
a8889a90-1082-4a86-b119-30a5eb902930	false	starttls
a8889a90-1082-4a86-b119-30a5eb902930		auth
a8889a90-1082-4a86-b119-30a5eb902930	gmail.com	host
a8889a90-1082-4a86-b119-30a5eb902930		replyTo
a8889a90-1082-4a86-b119-30a5eb902930	ciudadanodeber@gmail.com	from
a8889a90-1082-4a86-b119-30a5eb902930		fromDisplayName
a8889a90-1082-4a86-b119-30a5eb902930		envelopeFrom
a8889a90-1082-4a86-b119-30a5eb902930	false	ssl
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.realm_supported_locales (realm_id, value) FROM stdin;
a8889a90-1082-4a86-b119-30a5eb902930	en
a8889a90-1082-4a86-b119-30a5eb902930	es
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.redirect_uris (client_id, value) FROM stdin;
2cdcb225-507d-46c8-9eac-e0f844e2d9ce	/realms/master/account/*
9601986b-5fb5-4321-8bf5-fae31332ac78	/realms/master/account/*
52386ca8-8246-4307-9a08-f59db387c7df	/admin/master/console/*
782ab103-aafa-4993-90f2-05b1cdb47a9e	/admin/Ciudadano/console/*
fe9505a3-aa97-4b00-b592-a91755146d6b	/realms/Ciudadano/account/*
d68566c6-8172-4bb1-aa7b-26f1df1c5346	/realms/Ciudadano/account/*
f07fae5e-cd31-437b-9cdb-8892c673d027	http://localhost:5909/q/dev-ui/io.quarkus.quarkus-oidc/keycloak-provider
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
b9772a48-28c7-4c83-9b59-096e9e2f9268	VERIFY_EMAIL	Verify Email	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	VERIFY_EMAIL	50
36d85d5d-9a0a-46a8-ad9a-289330face83	UPDATE_PROFILE	Update Profile	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	UPDATE_PROFILE	40
edc30578-f770-4d93-a349-3eec94bcb646	CONFIGURE_TOTP	Configure OTP	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	CONFIGURE_TOTP	10
14d3e156-4337-4cf6-9aa3-22a1b9a340c3	UPDATE_PASSWORD	Update Password	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	UPDATE_PASSWORD	30
5c31ea03-214e-46d1-8abb-7350e81ea0e5	TERMS_AND_CONDITIONS	Terms and Conditions	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	f	TERMS_AND_CONDITIONS	20
a069e45d-88f7-42f9-a8f7-72c3af9f1b26	delete_account	Delete Account	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	f	f	delete_account	60
454b90e4-984d-4ea9-bb65-40100049ce15	update_user_locale	Update User Locale	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	update_user_locale	1000
f6088a93-2fc2-45cc-9470-401cf1fb2a21	webauthn-register	Webauthn Register	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	webauthn-register	70
2f6d3d18-4063-4778-8c22-be23f9ae9c36	webauthn-register-passwordless	Webauthn Register Passwordless	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	t	f	webauthn-register-passwordless	80
be945760-6bc3-459f-9f06-d60982bf45ce	VERIFY_EMAIL	Verify Email	a8889a90-1082-4a86-b119-30a5eb902930	t	f	VERIFY_EMAIL	50
06350c90-bc3f-42f3-9c77-843f5c79f1a2	UPDATE_PROFILE	Update Profile	a8889a90-1082-4a86-b119-30a5eb902930	t	f	UPDATE_PROFILE	40
7d15dd5c-d8c2-4172-bb33-d89b49e5d118	CONFIGURE_TOTP	Configure OTP	a8889a90-1082-4a86-b119-30a5eb902930	t	f	CONFIGURE_TOTP	10
b0865957-94b9-48c9-b9f6-b5a8eba36d25	UPDATE_PASSWORD	Update Password	a8889a90-1082-4a86-b119-30a5eb902930	t	f	UPDATE_PASSWORD	30
c78e955f-7f87-4b8f-a335-23cb237e449a	TERMS_AND_CONDITIONS	Terms and Conditions	a8889a90-1082-4a86-b119-30a5eb902930	f	f	TERMS_AND_CONDITIONS	20
0c22728f-ca3b-4bfc-a9ad-0c7c4d88aa3e	delete_account	Delete Account	a8889a90-1082-4a86-b119-30a5eb902930	f	f	delete_account	60
3c364033-4893-4ad8-89a4-f22696a9c2c2	update_user_locale	Update User Locale	a8889a90-1082-4a86-b119-30a5eb902930	t	f	update_user_locale	1000
85d6d278-86df-44db-9254-cceb35259865	webauthn-register	Webauthn Register	a8889a90-1082-4a86-b119-30a5eb902930	t	f	webauthn-register	70
77144efd-534a-494d-b7c9-9df07a242a6b	webauthn-register-passwordless	Webauthn Register Passwordless	a8889a90-1082-4a86-b119-30a5eb902930	t	f	webauthn-register-passwordless	80
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.scope_mapping (client_id, role_id) FROM stdin;
9601986b-5fb5-4321-8bf5-fae31332ac78	aacd4fa8-4a92-49b6-9190-feb69bf387d2
9601986b-5fb5-4321-8bf5-fae31332ac78	a4cd6249-d1b7-41e1-b982-02d4d9f07e02
d68566c6-8172-4bb1-aa7b-26f1df1c5346	9ad4ee24-991c-4fac-b118-4f0f65e2c3e0
d68566c6-8172-4bb1-aa7b-26f1df1c5346	5a39fd04-5966-4c98-80cc-905c0b505f74
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_attribute (name, value, user_id, id) FROM stdin;
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
cdc98447-1125-4e96-8857-572aeb3489a1	cayetanosimonparadiso@protonmail.com	cayetanosimonparadiso@protonmail.com	f	t	\N	Cayetano Simón	Paradiso	a8889a90-1082-4a86-b119-30a5eb902930	saimon	1706065115108	\N	0
703ed113-42d1-49f3-ac15-4b1160502dbf	ciudadanodeber@gmail.com	ciudadanodeber@gmail.com	t	t	\N	\N	\N	d8f90faa-3ebd-4e75-9102-54f1c7f91d1b	admin	1706053334882	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_group_membership (group_id, user_id) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_role_mapping (role_id, user_id) FROM stdin;
afc61b6f-3563-4725-8519-353c90af1649	703ed113-42d1-49f3-ac15-4b1160502dbf
d7e887cc-d10d-48f5-86ac-66d339b9584a	703ed113-42d1-49f3-ac15-4b1160502dbf
433e4b73-7e4b-4393-8f0b-9d483198ec17	703ed113-42d1-49f3-ac15-4b1160502dbf
eb4d6363-1f44-4347-9fdd-70666252932d	703ed113-42d1-49f3-ac15-4b1160502dbf
d62cdae5-3f58-4e45-9faa-e57e4989a9e5	703ed113-42d1-49f3-ac15-4b1160502dbf
df45c74a-c1b1-4a82-a277-2a3e06d5cab5	703ed113-42d1-49f3-ac15-4b1160502dbf
25f417e2-84ee-42d9-9491-47c843fdfb6a	703ed113-42d1-49f3-ac15-4b1160502dbf
5a846985-eee4-4385-a832-fc4cd11ac929	703ed113-42d1-49f3-ac15-4b1160502dbf
73998982-3ff5-4f22-a0ef-a9160c415f09	703ed113-42d1-49f3-ac15-4b1160502dbf
66564d6a-2814-49e2-a39c-450f5369e92b	703ed113-42d1-49f3-ac15-4b1160502dbf
75efba1e-0d39-42f8-b847-c1a220026404	703ed113-42d1-49f3-ac15-4b1160502dbf
66f87c3a-3c94-4391-a579-e383d753be56	703ed113-42d1-49f3-ac15-4b1160502dbf
72a2ab55-e318-4c84-98fe-5e7f4a460ac1	703ed113-42d1-49f3-ac15-4b1160502dbf
ce3b6433-f462-4169-94a7-99816cd665a7	703ed113-42d1-49f3-ac15-4b1160502dbf
ec1f0c20-bff5-453d-9976-c6c71637b057	703ed113-42d1-49f3-ac15-4b1160502dbf
349b1b34-184e-4522-9577-25e2730f180f	703ed113-42d1-49f3-ac15-4b1160502dbf
7d6fb956-0961-4c4b-935a-a4085388e40a	703ed113-42d1-49f3-ac15-4b1160502dbf
ee360ae4-b864-4027-a94e-f4cd5d4d1a50	703ed113-42d1-49f3-ac15-4b1160502dbf
bcf4775c-0ea9-46f5-ae9c-3a17a9ac8c98	703ed113-42d1-49f3-ac15-4b1160502dbf
a70201c2-473b-4de0-ae31-dc5d227f0640	cdc98447-1125-4e96-8857-572aeb3489a1
\.


--
-- Data for Name: user_session; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_session (id, auth_method, ip_address, last_session_refresh, login_username, realm_id, remember_me, started, user_id, user_session_state, broker_session_id, broker_user_id) FROM stdin;
\.


--
-- Data for Name: user_session_note; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.user_session_note (user_session, name, value) FROM stdin;
\.


--
-- Data for Name: username_login_failure; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.username_login_failure (realm_id, username, failed_login_not_before, last_failure, last_ip_failure, num_failures) FROM stdin;
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: keycloak; Owner: saimon
--

COPY keycloak.web_origins (client_id, value) FROM stdin;
52386ca8-8246-4307-9a08-f59db387c7df	+
782ab103-aafa-4993-90f2-05b1cdb47a9e	+
f07fae5e-cd31-437b-9cdb-8892c673d027	/*
\.


--
-- Name: username_login_failure CONSTRAINT_17-2; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.username_login_failure
    ADD CONSTRAINT "CONSTRAINT_17-2" PRIMARY KEY (realm_id, username);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: client_user_session_note constr_cl_usr_ses_note; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_user_session_note
    ADD CONSTRAINT constr_cl_usr_ses_note PRIMARY KEY (client_session, name);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: client_session_role constraint_5; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_role
    ADD CONSTRAINT constraint_5 PRIMARY KEY (client_session, role_id);


--
-- Name: user_session constraint_57; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_session
    ADD CONSTRAINT constraint_57 PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client_session_note constraint_5e; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_note
    ADD CONSTRAINT constraint_5e PRIMARY KEY (client_session, name);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: client_session constraint_8; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session
    ADD CONSTRAINT constraint_8 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: client_session_auth_status constraint_auth_status_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_auth_status
    ADD CONSTRAINT constraint_auth_status_pk PRIMARY KEY (client_session, authenticator);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: client_session_prot_mapper constraint_cs_pmp_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_prot_mapper
    ADD CONSTRAINT constraint_cs_pmp_pk PRIMARY KEY (client_session, protocol_mapper_id);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: user_session_note constraint_usn_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_session_note
    ADD CONSTRAINT constraint_usn_pk PRIMARY KEY (user_session, name);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_jkuwuvd56ontgsuhogm8uewrt; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_consent
    ADD CONSTRAINT uk_jkuwuvd56ontgsuhogm8uewrt UNIQUE (client_id, client_storage_provider, external_client_id, user_id);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_admin_event_time ON keycloak.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON keycloak.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_auth_config_realm ON keycloak.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_auth_exec_flow ON keycloak.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_auth_exec_realm_flow ON keycloak.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_auth_flow_realm ON keycloak.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_cl_clscope ON keycloak.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_id; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_client_id ON keycloak.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_client_init_acc_realm ON keycloak.client_initial_access USING btree (realm_id);


--
-- Name: idx_client_session_session; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_client_session_session ON keycloak.client_session USING btree (session_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_clscope_attrs ON keycloak.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_clscope_cl ON keycloak.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_clscope_protmap ON keycloak.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_clscope_role ON keycloak.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_compo_config_compo ON keycloak.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_component_provider_type ON keycloak.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_component_realm ON keycloak.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_composite ON keycloak.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_composite_child ON keycloak.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_defcls_realm ON keycloak.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_defcls_scope ON keycloak.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_event_time ON keycloak.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fedidentity_feduser ON keycloak.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fedidentity_user ON keycloak.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_attribute ON keycloak.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_cnsnt_ext ON keycloak.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_consent ON keycloak.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_consent_ru ON keycloak.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_credential ON keycloak.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_credential_ru ON keycloak.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_group_membership ON keycloak.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_group_membership_ru ON keycloak.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_required_action ON keycloak.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_required_action_ru ON keycloak.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_role_mapping ON keycloak.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_fu_role_mapping_ru ON keycloak.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_group_att_by_name_value ON keycloak.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_group_attr_group ON keycloak.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_group_role_mapp_group ON keycloak.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_id_prov_mapp_realm ON keycloak.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_ident_prov_realm ON keycloak.identity_provider USING btree (realm_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_keycloak_role_client ON keycloak.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_keycloak_role_realm ON keycloak.keycloak_role USING btree (realm);


--
-- Name: idx_offline_css_preload; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_offline_css_preload ON keycloak.offline_client_session USING btree (client_id, offline_flag);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_offline_uss_by_user ON keycloak.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_offline_uss_by_usersess; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_offline_uss_by_usersess ON keycloak.offline_user_session USING btree (realm_id, offline_flag, user_session_id);


--
-- Name: idx_offline_uss_createdon; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_offline_uss_createdon ON keycloak.offline_user_session USING btree (created_on);


--
-- Name: idx_offline_uss_preload; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_offline_uss_preload ON keycloak.offline_user_session USING btree (offline_flag, created_on, user_session_id);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_protocol_mapper_client ON keycloak.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_attr_realm ON keycloak.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_clscope ON keycloak.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_def_grp_realm ON keycloak.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_evt_list_realm ON keycloak.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_evt_types_realm ON keycloak.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_master_adm_cli ON keycloak.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_realm_supp_local_realm ON keycloak.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_redir_uri_client ON keycloak.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_req_act_prov_realm ON keycloak.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_res_policy_policy ON keycloak.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_res_scope_scope ON keycloak.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_res_serv_pol_res_serv ON keycloak.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_res_srv_res_res_srv ON keycloak.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_res_srv_scope_res_srv ON keycloak.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_role_attribute ON keycloak.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_role_clscope ON keycloak.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_scope_mapping_role ON keycloak.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_scope_policy_policy ON keycloak.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_update_time ON keycloak.migration_model USING btree (update_time);


--
-- Name: idx_us_sess_id_on_cl_sess; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_us_sess_id_on_cl_sess ON keycloak.offline_client_session USING btree (user_session_id);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_usconsent_clscope ON keycloak.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_attribute ON keycloak.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_attribute_name ON keycloak.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_consent ON keycloak.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_credential ON keycloak.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_email ON keycloak.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_group_mapping ON keycloak.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_reqactions ON keycloak.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_role_mapping ON keycloak.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_user_service_account ON keycloak.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_usr_fed_map_fed_prv ON keycloak.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_usr_fed_map_realm ON keycloak.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_usr_fed_prv_realm ON keycloak.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: keycloak; Owner: saimon
--

CREATE INDEX idx_web_orig_client ON keycloak.web_origins USING btree (client_id);


--
-- Name: client_session_auth_status auth_status_constraint; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_auth_status
    ADD CONSTRAINT auth_status_constraint FOREIGN KEY (client_session) REFERENCES keycloak.client_session(id);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: client_session_note fk5edfb00ff51c2736; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_note
    ADD CONSTRAINT fk5edfb00ff51c2736 FOREIGN KEY (client_session) REFERENCES keycloak.client_session(id);


--
-- Name: user_session_note fk5edfb00ff51d3472; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_session_note
    ADD CONSTRAINT fk5edfb00ff51d3472 FOREIGN KEY (user_session) REFERENCES keycloak.user_session(id);


--
-- Name: client_session_role fk_11b7sgqw18i532811v7o2dv76; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_role
    ADD CONSTRAINT fk_11b7sgqw18i532811v7o2dv76 FOREIGN KEY (client_session) REFERENCES keycloak.client_session(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: client_session_prot_mapper fk_33a8sgqw18i532811v7o2dk89; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session_prot_mapper
    ADD CONSTRAINT fk_33a8sgqw18i532811v7o2dk89 FOREIGN KEY (client_session) REFERENCES keycloak.client_session(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES keycloak.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES keycloak.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES keycloak.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES keycloak.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: client_session fk_b4ao2vcvat6ukau74wbwtfqo1; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_session
    ADD CONSTRAINT fk_b4ao2vcvat6ukau74wbwtfqo1 FOREIGN KEY (session_id) REFERENCES keycloak.user_session(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES keycloak.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES keycloak.client_scope(id);


--
-- Name: client_user_session_note fk_cl_usr_ses_note; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_user_session_note
    ADD CONSTRAINT fk_cl_usr_ses_note FOREIGN KEY (client_session) REFERENCES keycloak.client_session(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES keycloak.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES keycloak.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES keycloak.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES keycloak.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES keycloak.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES keycloak.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES keycloak.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES keycloak.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES keycloak.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES keycloak.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES keycloak.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES keycloak.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES keycloak.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES keycloak.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES keycloak.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES keycloak.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES keycloak.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES keycloak.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES keycloak.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES keycloak.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES keycloak.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES keycloak.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES keycloak.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES keycloak.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES keycloak.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES keycloak.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES keycloak.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: keycloak; Owner: saimon
--

ALTER TABLE ONLY keycloak.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES keycloak.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

