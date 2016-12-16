# Orders schema

# --- !Ups

CREATE TABLE Orders (
id serial PRIMARY KEY,
userId number(10) NOT NULL,
item varchar(255) NOT NULL,
extras varchar(255) NOT NULL,
quantity number(10),
size number(10),
distance number(10),
price number(10, 2),
ordertime number(10),
time timestamp
);