DELETE FROM VanzariLocuri;
DELETE FROM Vanzari;
DELETE FROM Spectacole;
DELETE FROM Sali;

UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Sali";
UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Spectacole";
UPDATE "sqlite_sequence" SET "seq" = 0 WHERE "name" = "Vanzari";

INSERT INTO Sali(nr_locuri) VALUES
	(100),
	(100),
	(100);

INSERT INTO Spectacole(id_sala, data_spectacol, titlu, pret_bilet) VALUES
	(1, '01-01-1970', 'S1', 100),
	(1, '01-02-1970', 'S2', 200),
	(2, '01-01-1970', 'S3', 150);
