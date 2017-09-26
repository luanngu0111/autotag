create database ReconDB;
use ReconDB;

create table if not exists `Issue` 
(
	ID int(10) primary key auto_increment,
    report char(50),
    trn_fmly char(50),
    trn_grp char(50),
    trn_type char(50),
    sql_filter varchar(255),
    correct_source char(50),
    different_type char(50),
    explanation varchar(255),
    field_values char(50)
);

create table if not exists `Trades` 
(
	NB int(10),
    instrument char(20),
    currency char(3),
    portfolio char(50),
    trn_fmly char(5),
    trn_grp char(5),
    trn_type char(5),
    trn_status char(15),
    field char(15),
    issue_id int(10),
    foreign key (issue_id ) references Issue(ID)
);

-- alter table Trades 
-- add constraint pk_trades primary key (NB, field, issue_id)

