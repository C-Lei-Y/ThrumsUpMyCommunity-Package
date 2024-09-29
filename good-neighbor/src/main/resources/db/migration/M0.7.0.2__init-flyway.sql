-- # FLYWAY # --

-- DROP TABLE flyway_schema_history;

CREATE TABLE flyway_schema_history (
    installed_rank int4 NOT NULL,
    "version" varchar(50) NULL,
    description varchar(200) NOT NULL,
    "type" varchar(20) NOT NULL,
    script varchar(1000) NOT NULL,
    checksum int4 NULL,
    installed_by varchar(100) NOT NULL,
    installed_on timestamp NOT NULL DEFAULT now(),
    execution_time int4 NOT NULL,
    success bool NOT NULL,
    CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);
CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history USING btree (success);

INSERT INTO flyway_schema_history (installed_rank,"version",description,"type",script,checksum,installed_by,installed_on,execution_time,success)
VALUES 
    (0,NULL,'<< Flyway Schema Creation >>','SCHEMA','"init"',NULL,'postit',NOW(),0,true),
    (1,'0.7.0.0','init-ddl','SQL','V0.7.0.0__init-ddl.sql',-175141424,'postit',NOW(),0,true),
    (2,'0.7.0.1','init-data','SQL','V0.7.0.1__init-data.sql',1509949286,'postit',NOW(),0,true);