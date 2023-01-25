create table resultado_horario (
  id varchar(30) not null,
  ordem int not null,
  resultado varchar(50) not null,
  resultado_dia_id date not null,
  codigo_horario varchar(30) not null,
  primary key(id),
  foreign key (resultado_dia_id) references resultado_dia(sorteado_em)
);