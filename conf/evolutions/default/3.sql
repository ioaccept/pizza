# Items schema

# --- !Ups

CREATE TABLE Items (
id serial PRIMARY KEY,
name varchar(255) NOT NULL,
cat_id number(10),
price number(10, 2)
);

INSERT INTO Items values (1, 'Margerita', 1, 0.50);
INSERT INTO Items values (2, 'Funghi', 1, 0.50);
INSERT INTO Items values (3, 'Cola', 2, 0.50);
INSERT INTO Items values (4, 'Tiramisu', 3, 0.50);