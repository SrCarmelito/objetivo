delete from elo.endereco where 1=1;
delete from elo.pessoa where 1=1;

INSERT INTO elo.pessoa VALUES(1, 'Jon Yang', '1945-05-31', '81004138270', '44652344277', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'system', 'system');
INSERT INTO elo.pessoa VALUES(2, 'Lenny Kravitz', '1996-02-16', '45317125871', '45955324578', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'system', 'system');
INSERT INTO elo.pessoa VALUES(3, 'Phill Collins', '1960-11-28', '42166575595', '43965214578', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'system', 'system');

alter sequence elo.s_pessoa restart with (select max(id) from elo.pessoa) + 1;