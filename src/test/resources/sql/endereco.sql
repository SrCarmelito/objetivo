INSERT INTO elo.endereco VALUES(1, 2, 87005000, 'Rua das Flores', '65', 'Maringá', 'PR', 'Centro');
INSERT INTO elo.endereco VALUES(2, 2, 87550001, 'Av. Brasil', '30', 'Cianorte', 'PR', 'Boqueirão');
INSERT INTO elo.endereco VALUES(3, 1, 65945000, 'Av. Santos Dumont', '88', 'Jandaia do Sul', 'PR', 'Industrial');

alter sequence elo.s_endereco restart with (select max(id) from elo.endereco) + 1;