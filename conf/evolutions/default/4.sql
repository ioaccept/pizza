# Orders schema

# --- !Ups

CREATE TABLE Orders (
id serial PRIMARY KEY,
userId numeric(10) NOT NULL,
item varchar(255) NOT NULL,
extras varchar(255) NOT NULL,
quantity numeric(10),
size numeric(10),
distance numeric(10),
price numeric(10, 2),
ordertime numeric(10),
time timestamp
);