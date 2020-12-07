-- create database
CREATE ROLE kwhadmin WITH CREATEDB CREATEROLE LOGIN PASSWORD 'pass';
CREATE ROLE kwhuser WITH LOGIN PASSWORD 'pass';
CREATE DATABASE kwhdb WITH OWNER=kwhadmin;

-- drop all

drop table app_user;
drop table article;
drop table category;
drop table databasechangelog;
drop table databasechangeloglock;
commit;