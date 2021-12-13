drop table if exists orders CASCADE 
drop table if exists rates CASCADE 
drop table if exists traders CASCADE 

create table orders (id bigint generated by default as identity, rate double, source_currency varchar(255), source_currency_amount decimal(19,2), target_currency varchar(255), timestamp timestamp, trader_id bigint, primary key (id))

create table rates (id bigint generated by default as identity, base_currency varchar(255), currency varchar(255), date date, expired_time timestamp, rate double, reservation_code varchar(255), primary key (id))

create table traders (id bigint generated by default as identity, name varchar(255), ranking integer, primary key (id))

alter table orders add constraint FKmtgi2lwuvafkhm1h1roomrto3 foreign key (trader_id) references traders
