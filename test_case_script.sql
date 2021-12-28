DELETE FROM VanzariLocuri;
DELETE FROM Vanzari;
DELETE FROM Spectacole;
DELETE FROM Sali;

UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Sali";
UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Spectacole";
UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Vanzari";

INSERT INTO Sali(nr_locuri) VALUES
	(50),
	(1000),
	(100);

INSERT INTO Spectacole(id_sala, data_spectacol, titlu, pret_bilet) VALUES
	(1, '01-01-1970', 'First', 123.9),
	(1, '01-02-1970', 'Second', 321.9),
	(2, '01-01-1970', 'Third', 23.3),
	(3, '01-01-1970', 'Fourth', 123.4);
