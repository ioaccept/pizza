# Users schema
 
# --- !Ups
 
CREATE TABLE Users (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    distance number(10),
    admin bit default false
);

INSERT INTO Users values (1, 'Padrone', 10, 1);
INSERT INTO Users values (2, 'Andi', 5, 0);

# --- !Downs
 
DROP TABLE Users;
