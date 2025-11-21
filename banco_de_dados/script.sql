create database dbjogo;

use dbjogo;

create table tbjogador(
id int auto_increment primary key,
nomejogador varchar(120) not null,
total int not null default 0
);

desc tbjogador;

select * from tbjogador;