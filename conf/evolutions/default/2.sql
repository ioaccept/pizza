# Cat schema

# --- !Ups

CREATE TABLE Cat (
id serial PRIMARY KEY,
name varchar(255) NOT NULL
);

INSERT INTO Cat values (1, 'Pizza');
INSERT INTO Cat values (2, 'Getränke');
INSERT INTO Cat values (3, 'Dessert');

# --- !Downs

