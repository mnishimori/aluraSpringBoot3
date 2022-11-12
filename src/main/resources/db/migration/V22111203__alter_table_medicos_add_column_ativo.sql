alter table medicos add column ativo tinyint null;

update medicos set ativo = 1;
