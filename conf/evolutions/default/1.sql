# Users schema
 
# --- !Ups
 
CREATE TABLE Users (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    distance number(10),
    admin boolean
);

INSERT INTO Users values (1, 'Padrone', 'Padrone', 10, true);
INSERT INTO Users values (2, 'Emil', 'Emil', 5, false);

# --- !Downs
 
DROP TABLE Users;
