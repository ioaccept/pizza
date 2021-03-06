# Items schema

# --- !Ups

CREATE TABLE Items (
id serial PRIMARY KEY,
name varchar(255) NOT NULL,
cat_id numeric(10),
price numeric(10, 2),
active boolean DEFAULT TRUE
);

INSERT INTO Items values (1, 'Margerita', 1, 0.50, true);
INSERT INTO Items values (2, 'Funghi', 1, 0.50, true);
INSERT INTO Items values (3, 'Cola', 2, 0.50, true);
INSERT INTO Items values (4, 'Tiramisu', 3, 0.50, true);