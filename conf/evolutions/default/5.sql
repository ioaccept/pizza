# Extras schema

# --- !Ups

CREATE TABLE Extras (
id serial PRIMARY KEY,
name varchar(255) NOT NULL,
price number(10, 2)
);

INSERT INTO Extras values (1, 'Käse', 0.50);
INSERT INTO Extras values (2, 'Soße', 0.50);