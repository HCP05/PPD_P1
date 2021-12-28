DROP TABE IF EXISTS VanzariLocuri;
DROP TABLE IF EXISTS Vanzari;
DROP TABLE IF EXISTS Spectacole;
DROP TABLE IF EXISTS Sali;

CREATE TABLE Sali(
	id_sala INTEGER
		CONSTRAINT Sali_pk PRIMARY KEY
		AUTOINCREMENT,
	nr_locuri INTEGER NOT NULL);

CREATE TABLE Spectacole(
	id_spectacol INTEGER
		CONSTRAINT Spectacole_pk PRIMARY KEY
		AUTOINCREMENT,
	id_sala INTEGER,
	data_spectacol VARCHAR(50) NOT NULL,
	titlu VARCHAR(50) NOT NULL,
	pret_bilet REAL NOT NULL);

CREATE TABLE Vanzari(
	id_vanzare INTEGER
		CONSTRAINT Vanzari_pk PRIMARY KEY
		AUTOINCREMENT,
	id_spectacol INTEGER NOT NULL,
	data_vanzare VARCHAR(50) NOT NULL);

CREATE TABLE VanzariLocuri(
	id_vanzare INTEGER,
	nr_loc INTEGER,
	PRIMARY KEY (id_vanzare, nr_loc));
