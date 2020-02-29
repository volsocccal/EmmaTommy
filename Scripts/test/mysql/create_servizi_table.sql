    CREATE DATABASE IF NOT EXISTS servizi_db ;
    USE servizi_db;

    DROP TABLE IF EXISTS servizi;

    CREATE TABLE servizi (
    codice_servizio int(11) NOT NULL,
    codice_mezzo varchar(45) DEFAULT NULL,
    km int(11) DEFAULT NULL,
    email varchar(45) DEFAULT NULL,
    PRIMARY KEY (codice_servizio)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1; 
