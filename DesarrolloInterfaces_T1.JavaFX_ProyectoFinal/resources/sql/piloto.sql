drop database if exists ProyectoFinal;

create database ProyectoFinal;

use ProyectoFinal;

drop table if exists Admin;
create table Admin (
	idAdmin int auto_increment,
	usuario varchar(225) not null unique,
	password varchar(225) not null,
	Primary key(idAdmin)
	)ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;

insert into Admin(usuario,password) values('root','root');
	
drop table if exists Concurso;
create table Concurso (
	idConcurso int auto_increment,
	nombreConcurso varchar(225) not null,
	tipoConcurso varchar(225) not null,
	lugarConcurso varchar(225) not null,
	fechaConcurso Date not null,
	finRegistroConcurso Date not null,
	Primary Key(idConcurso)
	)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;
	
	
drop table if exists Piloto;
create table Piloto (
	idPiloto int auto_increment, 
	nombre varchar(225) not null, 
	apellido varchar(225) not null, 
	email varchar(225) not null unique, 
	password varchar(225) not null, 
	numLicencia int not null unique, 
	telefono int not null, 
	ciudad varchar(225) not null, 
	fechaInscripcion Date not null, 
	club varchar(225) not null, 
	idConcurso int null,
	PRIMARY KEY(idPiloto),
	FOREIGN KEY (idConcurso) REFERENCES Concurso(idConcurso)
	
	)ENGINE=InnoDB AUTO_INCREMENT=1000 default charset=utf8;
	
drop table if exists Manga;
create table Manga (
	idManga int auto_increment not null,
	numManga int,
	tiempo int,
	altura int,
	aterrizaje int,
	penalizaciones int,
	grupo int,
	punto int,
	puntoFinal int,
	idConcurso int,
	idPiloto int,
	PRIMARY KEY (idManga),
	FOREIGN KEY (idConcurso) REFERENCES Concurso(idConcurso),
	FOREIGN KEY (idPiloto) REFERENCES Piloto(idPiloto) 
	
	)ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;	
	


drop table if exists listaEspera;
create table listaEspera (
	idEspera int auto_increment not null,
	idConcurso int,
	idPiloto int,
	
	PRIMARY KEY (idEspera),
 	FOREIGN KEY (idConcurso) REFERENCES Concurso(idConcurso),
	FOREIGN KEY (idPiloto) REFERENCES Piloto(idPiloto) 
	
	)ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;	


drop table if exists CodigoVerificacion;
create table CodigoVerificacion (
	email varchar(225) not null,
	codigoVerificacion varchar(225),
	tiempoApuntado TIMESTAMP,
	
	PRIMARY KEY (email)
	
)ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;	
	