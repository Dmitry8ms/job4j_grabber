create table if not exists post (
id serial primary key,
name varchar(256),
text text,
link varchar(256) unique,
created Timestamp);
select * from post;