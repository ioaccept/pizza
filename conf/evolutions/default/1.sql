# Users schema
 
# --- !Ups
 
CREATE TABLE Users (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    distance number(10),
    admin varchar(255) default 'nein'
);

INSERT INTO Users values (1, 'Padrone', 10, 'ja');
INSERT INTO Users values (2, 'Andi', 5, 'nein');

# --- !Downs
 
DROP TABLE Users;
