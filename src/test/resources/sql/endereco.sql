delete from elo.endereco where 1=1;

INSERT INTO elo.endereco VALUES(1, 2, 87005000, 'Rua das Flores', '65', 'Maringá', 'PR', 'Centro', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'junior', 'junior');
INSERT INTO elo.endereco VALUES(2, 2, 87550001, 'Av. Brasil', '30', 'Cianorte', 'PR', 'Boqueirão', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'junior', 'junior');
INSERT INTO elo.endereco VALUES(3, 1, 65945000, 'Av. Santos Dumont', '88', 'Jandaia do Sul', 'PR', 'Industrial', '2024-07-18 22:24:14.400', '2024-07-18 23:15:29.037', 'junior', 'junior');

alter sequence elo.s_endereco restart with (select max(id) from elo.endereco) + 1;