# Extras schema

# --- !Ups

CREATE TABLE Extras (
id serial PRIMARY KEY,
name varchar(255) NOT NULL,
price numeric(10, 2)
);

INSERT INTO Extras values (1, 'Keine', 0);
INSERT INTO Extras values (2, 'Käse', 0.50);
INSERT INTO Extras values (3, 'Soße', 0.50);