-- uzytkownik sie loguje
SELECT UzytkownikHaslo FROM Uzytkownicy WHERE UzytkownikLogin='wojjab0209';

-- konczymy sezon 2025
UPDATE Sezony
SET SezonCzyAktywny = FALSE
WHERE SezonID = 1
  AND CURRENT_DATE > SezonDataKonca;

SELECT * FROM Sezony;

-- zaczynamy sezon 2026
INSERT INTO Sezony (SezonRok, SezonDataPoczatku, SezonDataKonca, SezonCzyAktywny)
VALUES (2026, '2026-03-15', '2026-07-15', TRUE);

SELECT * FROM Sezony;

-- tworzymy ligi w sezonie
INSERT INTO LigaSezon (SezonID, LigaID)
VALUES 
    (2, 1),
    (2, 2);

SELECT * FROM LigaSezon;

-- dodajemy nowa druzyne
INSERT INTO Druzyny (
    DruzynaNazwa, DruzynaSkrot, DruzynaDataZalozenia, DruzynaStrojeDom, DruzynaStrojeWyjazd, DruzynaStadion, DruzynaAdres) 
VALUES (
    '1. KS Ślęza Wrocław', 'SLE', CURRENT_DATE, 'Żółto-Czerwony', 'Czarny', 'Centrum Piłkarskie Ślęza', 'ul. Kłokoczycka 5, Wrocław'
);

SELECT * FROM Druzyny;

-- dodajemy trenera
INSERT INTO Uzytkownicy (
    RolaID, UzytkownikLogin, UzytkownikHaslo, UzytkownikImie, UzytkownikNazwisko, UzytkownikDataUrodzenia, 
    UzytkownikNarodowosc, UzytkownikAdres,UzytkownikSzukaKlubu 
)
VALUES (
    3, 'edwand2108', MD5('edwand2108'), 
    'Edward', 'Andrzejewski', '1988-08-21', 'Polska', 'ul. Złota 44, Warszawa',FALSE
);

-- dodajemy kilku piłkarzy
INSERT INTO Uzytkownicy (
    RolaID, UzytkownikLogin, UzytkownikHaslo, UzytkownikImie, UzytkownikNazwisko, UzytkownikDataUrodzenia, 
    UzytkownikNarodowosc, UzytkownikAdres,UzytkownikSzukaKlubu 
)
VALUES (
    4, 'tobopl2107', MD5('tobopl2107'), 
    'Tobiasz', 'Opłatek', '1999-07-21', 'Polska', 'ul. Czarna 44, Wrocław',TRUE
),
(
    4, 'igokro2107', MD5('igokro2107'), 
    'Igor', 'Królewski', '2003-07-21', 'Polska', 'ul. Zielona 4, Wrocław',FALSE
),
(
    4, 'arksus0101', MD5('arksus0101'), 
    'Arkadiusz', 'Suski', '2000-01-01', 'Polska', 'ul. Czerwona 74, Wrocław',FALSE
);

-- dodajemy sędziego
INSERT INTO Uzytkownicy (
    RolaID, UzytkownikLogin, UzytkownikHaslo, UzytkownikImie, UzytkownikNazwisko, UzytkownikDataUrodzenia, 
    UzytkownikNarodowosc, UzytkownikAdres,UzytkownikSzukaKlubu 
)
VALUES (
    2, 'blatra0202', MD5('blatra0202'), 
    'Błażej', 'Traczyk', '1978-02-02', 'Polska', 'ul. Trzecia 3, Wrocław',FALSE
);

-- zobaczmy wszystkich piłkarzy
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    UzytkownikNarodowosc, 
    COALESCE(DruzynaNazwa, 'BRAK KLUBU') AS Klub
FROM WidokListyPilkarzy
ORDER BY UzytkownikNazwisko;

-- zobaczmy wszystkich trenerów
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    UzytkownikNarodowosc, 
    COALESCE(DruzynaNazwa, 'BRAK KLUBU') AS Klub
FROM WidokTrenerow
ORDER BY UzytkownikNazwisko;

-- zobaczmy wszystkich sędziów
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    LiczbaSedziowanychMeczy
FROM WidokSedziow
ORDER BY LiczbaSedziowanychMeczy DESC, UzytkownikNazwisko ASC;

-- zobaczmy wszystkie drużyny
SELECT 
    DruzynaNazwa, 
    DruzynaSkrot, 
    DruzynaAdres,
    DruzynaStadion
FROM Druzyny
ORDER BY DruzynaNazwa;

-- dodanie druzyn do ligi
INSERT INTO DruzynyWLidze(
    LigaSezonID, DruzynaID
)
VALUES (3, 1),(3, 3), (3, 4), (3, 8),(4, 2), (4, 5),(4, 7), (4, 9);
-- 1=9 ; 2=13 ; 3=10 ; 4=11 ; 5 = 14 ; 6=? ; 7=15 ; 8=12, 9=16

-- dodanie trenerów do drużyn na ten sezon
INSERT INTO PrzynaleznosciTrenerow(
    TrenerID, DruzynaWLidzeID
)
VALUES (6, 9), (7, 13), (8, 10), (9, 11),  (10, 14), (74, 16), (12, 15), (13, 12);

-- dodanie piłkarzy do druzyn na ten sezon
-- 14 - 73
INSERT INTO PrzynaleznosciPilkarzy(
    PilkarzID, DruzynaWLidzeID
)
VALUES(14,9), (15,9), (16,9), (17,9), (18,9), (19,9), (20,9),
(21,10), (22,10), (23,10), (24,10), (25,10), (26,10), (27,10),
(28,9),(29,9), (30,9), (31,9), (32,9), (33,9), (34,9);

-- podgląd tabeli z sezonu 2025 (ID=1,2) oraz 2026 (ID=3,4)
SELECT 
    ROW_NUMBER() OVER (ORDER BY Pkt DESC, Bilans DESC) AS Miejsce,
    DruzynaNazwa,
    M, Pkt, W, R, P, GZ, GS, Bilans
FROM WidokTabeliLigowej
WHERE LigaSezonID = 1;

-- dodanie meczów
INSERT INTO Mecze(
    MeczLigaSezonID, MeczGospodarzID, MeczGoscID
)
VALUES (3,9,10), (3,11,12), (4,13,14), (4,15,16);

-- wyświetlanie meczów
-- 1) mecze zagrane, Liga 1 2025
SELECT MeczData, Gospodarz, MeczWynikGospodarz, MeczWynikGosc, Gosc 
FROM WidokMeczy
WHERE MeczLigaSezonID = 1 
AND MeczCzyZagrany = TRUE;
-- 2) mecze do rozegrania, Liga
SELECT MeczData, Gospodarz, Gosc 
FROM WidokMeczy
WHERE MeczLigaSezonID = 1 
  AND MeczCzyZagrany = FALSE;
-- 3) mecze jednego klubu
SELECT MeczData, Gospodarz, MeczWynikGospodarz, MeczWynikGosc, Gosc, MeczCzyZagrany
FROM WidokMeczy
WHERE (GospodarzID = 1 OR GoscID = 1)
ORDER BY MeczData;
-- 4) mecze sędziowane przez jednego sędziego

-- USTALANIE DATY MECZU

-- 1) trener gosp sklada propozycje daty
UPDATE Mecze
SET 
    MeczDataProponowane = '2026-04-04',
    MeczStatusPropozycji = 1
WHERE MeczID = 25;
-- 2) trener gosci akcept
UPDATE Mecze
SET 
    MeczStatusPropozycji = 2
WHERE MeczID = 25 
AND MeczStatusPropozycji = 1;
-- 3) admin potwierdza
UPDATE Mecze
SET 
    MeczData = MeczDataProponowane,
    MeczStatusPropozycji = 3
WHERE MeczID = 25 
AND MeczStatusPropozycji = 2;

--przydzielenie sedziego do meczu
UPDATE Mecze
SET MeczSedziaID = 5
WHERE MeczID = 25;

-- WYNIK MECZU
UPDATE Mecze
SET 
    MeczWynikGospodarz = 3,
    MeczWynikGosc = 0,
    MeczCzyZagrany = TRUE
WHERE MeczID = 25;

-- dodanie zupełnie nowej ligi
INSERT INTO Ligi (LigaNazwa, LigaSkrot)
VALUES ('Liga 3', 'L3');

