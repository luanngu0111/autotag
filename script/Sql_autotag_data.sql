use ReconDB;

-- insert DATA TO TABLE ISSUE
insert into issue(report, trn_fmly, trn_grp, trn_type, sql_filter,correct_source,different_type,explanation,field_values) values('Core recon','EQD','OPT','OTC',' ','MX3','Behavior difference', 'Past cash issue xxx', 'PC');
insert into issue(report, trn_fmly, trn_grp, trn_type, sql_filter,correct_source,different_type,explanation,field_values) values('Core recon','EQD','FUT',' ',' ','MX3','Behavior difference', 'MV issue zzzz', 'MV');
insert into issue(report, trn_fmly, trn_grp, trn_type, sql_filter,correct_source,different_type,explanation,field_values) values('Core recon','EQD','OPT','OTC',' ','MX3','Behavior difference', 'PC issue zzzz', 'PC');


-- INSERT DATA TO TABLE TRADES
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12345,'SEC1','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12347,'SEC1','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12348,'SEC2','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12350,'SEC2','EUR','EQD PTF','EQD','OPT','OTC','LIVE','PC',1);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12351,'FUT1','EUR','FUT PTF','EQD','FUT',' ','LIVE','PC',3);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12351,'FUT1','EUR','FUT PTF','EQD','FUT',' ','LIVE','MV',2);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12352,'FUT2','USD','FUT PTF','EQD','FUT',' ','LIVE','MV',2);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12353,'FUT1','EUR','FUT PTF','EQD','FUT',' ','LIVE','PC',3);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12353,'FUT1','EUR','FUT PTF','EQD','FUT',' ','LIVE','MV',2);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12354,'FUT2','USD','FUT PTF','EQD','FUT',' ','LIVE','MV',2);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12356,'FUT3','USD','FUT PTF','EQD','FUT',' ','LIVE','MV',2);
insert into trades(NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) values(12357,'FUT1','USD','FUT PTF','EQD','FUT',' ','LIVE','MV',2);

