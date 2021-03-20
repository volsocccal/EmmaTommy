    CREATE DATABASE IF NOT EXISTS servizi_db ;
    USE servizi_db;

    DROP TABLE IF EXISTS soreuAlpina;
    CREATE TABLE IF NOT EXISTS soreuAlpina (
    codice_servizio varchar(12) NOT NULL,
    codice_mezzo varchar(45) NOT NULL,
    km int(11) DEFAULT NULL,
    servizio_start_date DATE NOT NULL,
    servizio_start_time TIME NOT NULL,
    servizio_end_date DATE DEFAULT NULL,
    servizio_end_time TIME DEFAULT NULL,
    servizio_timestamp TIMESTAMP NOT NULL,
    servizio_json BLOB NOT NULL,
    PRIMARY KEY (codice_servizio)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1; 
    
    DROP TABLE IF EXISTS soreuLaghi;
    CREATE TABLE IF NOT EXISTS soreuLaghi (
    codice_servizio varchar(12) NOT NULL,
    codice_mezzo varchar(45) NOT NULL,
    km int(11) DEFAULT NULL,
    servizio_start_date DATE NOT NULL,
    servizio_start_time TIME NOT NULL,
    servizio_end_date DATE DEFAULT NULL,
    servizio_end_time TIME DEFAULT NULL,
    servizio_timestamp TIMESTAMP NOT NULL,
    servizio_json BLOB NOT NULL,
    PRIMARY KEY (codice_servizio)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1; 
    
    DROP TABLE IF EXISTS soreuMetropolitana;
    CREATE TABLE IF NOT EXISTS soreuMetropolitana (
    codice_servizio varchar(12) NOT NULL,
    codice_mezzo varchar(45) NOT NULL,
    km int(11) DEFAULT NULL,
    servizio_start_date DATE NOT NULL,
    servizio_start_time TIME NOT NULL,
    servizio_end_date DATE DEFAULT NULL,
    servizio_end_time TIME DEFAULT NULL,
    servizio_timestamp TIMESTAMP NOT NULL,
    servizio_json BLOB NOT NULL,
    PRIMARY KEY (codice_servizio)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1; 
    
    DROP TABLE IF EXISTS soreuPianura;
    CREATE TABLE IF NOT EXISTS soreuPianura (
    codice_servizio varchar(12) NOT NULL,
    codice_mezzo varchar(45) NOT NULL,
    km int(11) DEFAULT NULL,
    servizio_start_date DATE NOT NULL,
    servizio_start_time TIME NOT NULL,
    servizio_end_date DATE DEFAULT NULL,
    servizio_end_time TIME DEFAULT NULL,
    servizio_timestamp TIMESTAMP NOT NULL,
    servizio_json BLOB NOT NULL,
    PRIMARY KEY (codice_servizio)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1; 
