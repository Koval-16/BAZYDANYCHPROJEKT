INSERT INTO Role (RolaNazwa) 
VALUES 
    ('Administrator'), 
    ('Sedzia'), 
    ('Trener'), 
    ('Pilkarz');

INSERT INTO Sezony (SezonRok, SezonDataPoczatku, SezonDataKonca, SezonCzyAktywny) 
VALUES (
    2025, 
    '2025-03-15',
    '2025-10-22',
    TRUE
);

INSERT INTO Ligi (LigaNazwa, LigaSkrot) 
VALUES  ('Liga 1', 'L1'),
        ('Liga 2', 'L2');

INSERT INTO LigaSezon (SezonID, LigaID) 
VALUES  ((SELECT SezonID FROM Sezony WHERE SezonRok = 2025 LIMIT 1), (SELECT LigaID FROM Ligi WHERE LigaSkrot = 'L1' LIMIT 1)),
        ((SELECT SezonID FROM Sezony WHERE SezonRok = 2025 LIMIT 1), (SELECT LigaID FROM Ligi WHERE LigaSkrot = 'L2' LIMIT 1));


INSERT INTO Druzyny (
    DruzynaNazwa, DruzynaSkrot, DruzynaDataZalozenia, DruzynaStrojeDom, DruzynaStrojeWyjazd, DruzynaStadion, DruzynaAdres
) 
VALUES 
    ('KP Odra Nadodrze', 'ODR', CURRENT_DATE, 'Niebieski', 'Biały', 'Boisko Nadodrze', 'ul. Pomorska 10, Wrocław'),
    ('Pogoń Psie Pole', 'PPP', CURRENT_DATE, 'Zielony', 'Czarny', 'Stadion Lotnicza', 'ul. Lotnicza 72, Wrocław'),
    ('Błękitni Biskupin', 'BLE', CURRENT_DATE, 'Błękitny', 'Żółty', 'Boisko Wittiga', 'ul. Wittiga 4, Wrocław'),
    ('KS Oporów', 'OPO', CURRENT_DATE, 'Czerwony', 'Biały', 'Stadion Oporowska', 'ul. Oporowska 62, Wrocław'),

    ('Lechia Leśnica', 'LLE', CURRENT_DATE, 'Zielony', 'Biały', 'Stadion Leśnicki', 'ul. Kosmonautów 1, Wrocław'),
    ('Sparta Sępolno', 'SEP', CURRENT_DATE, 'Czerwony', 'Czarny', 'Pola Marsowe', 'al. Mickiewicza 15, Wrocław'),
    ('Gajowianka Gaj', 'GAJ', CURRENT_DATE, 'Pomarańczowy', 'Czarny', 'Orlik Gaj', 'ul. Orzechowa 20, Wrocław'),
    ('Krzyki United', 'KRZ', CURRENT_DATE, 'Fioletowy', 'Biały', 'Stadion Skarbowców', 'ul. Racławicka 9, Wrocław');


INSERT INTO DruzynyWLidze (LigaSezonID, DruzynaID)
SELECT 
    (SELECT LigaSezonID 
     FROM LigaSezon ls
     JOIN Ligi l ON ls.LigaID = l.LigaID
     JOIN Sezony s ON ls.SezonID = s.SezonID
     WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025 LIMIT 1),
    DruzynaID
FROM Druzyny 
WHERE DruzynaSkrot IN ('ODR', 'PPP', 'BLE', 'OPO');

INSERT INTO DruzynyWLidze (LigaSezonID, DruzynaID)
SELECT 
    (SELECT LigaSezonID 
     FROM LigaSezon ls
     JOIN Ligi l ON ls.LigaID = l.LigaID
     JOIN Sezony s ON ls.SezonID = s.SezonID
     WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025 LIMIT 1),
    DruzynaID
FROM Druzyny 
WHERE DruzynaSkrot IN ('LLE', 'SEP', 'GAJ', 'KRZ');

INSERT INTO Uzytkownicy (RolaID, UzytkownikLogin, UzytkownikHaslo, UzytkownikImie, UzytkownikNazwisko, UzytkownikDataUrodzenia, UzytkownikNarodowosc, UzytkownikAdres, UzytkownikSzukaKlubu)
VALUES
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Administrator'), 'fraste0211', MD5('fraste0211'), 'Franciszek', 'Stępień', '1987-11-02', 'Polska', 'ul. Brzozowa 23, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Sedzia'), 'wojjab0209', MD5('wojjab0209'), 'Wojciech', 'Jabłonowski', '1976-09-02', 'Polska', 'ul. Lipowa 64, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Sedzia'), 'jakols1101', MD5('jakols1101'), 'Jakub', 'Olszewski', '1978-01-11', 'Polska', 'ul. Słoneczna 16, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Sedzia'), 'alekoz2701', MD5('alekoz2701'), 'Aleksander', 'Kozłowski', '1982-01-27', 'Polska', 'ul. Ogrodowa 60, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Sedzia'), 'tomdab1706', MD5('tomddb1706'), 'Tomasz', 'Dąbrowski', '1977-06-17', 'Polska', 'ul. Krótka 91, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'pawzie2108', MD5('pawzie2108'), 'Paweł', 'Zieliński', '1984-08-21', 'Polska', 'ul. Słoneczna 104, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'rafmic3008', MD5('rafmic3008'), 'Rafał', 'Michalski', '1981-08-30', 'Polska', 'ul. Słoneczna 150, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'robwoj2203', MD5('robwoj2203'), 'Robert', 'Wójcik', '1981-03-22', 'Polska', 'ul. Brzozowa 97, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'janjan0612', MD5('janjan0612'), 'Jan', 'Jankowski', '1983-12-06', 'Polska', 'ul. Leśna 132, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'micmic2211', MD5('micmic2211'), 'Michał', 'Michalski', '1977-11-22', 'Polska', 'ul. Leśna 82, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'jakzie2303', MD5('jakzie2303'), 'Jakub', 'Zieliński', '1980-03-23', 'Polska', 'ul. Łąkowa 24, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'stakam3009', MD5('stakam3009'), 'Stanisław', 'Kamiński', '1976-09-30', 'Polska', 'ul. Kwiatowa 150, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Trener'), 'alepio2809', MD5('alepio2809'), 'Aleksander', 'Piotrowski', '1983-09-28', 'Polska', 'ul. Słoneczna 80, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'macwis0605', MD5('macwis0605'), 'Maciej', 'Wiśniewski', '2004-05-06', 'Polska', 'ul. Leśna 124, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'tomste2704', MD5('tomste2704'), 'Tomasz', 'Stępień', '2000-04-27', 'Polska', 'ul. Ogrodowa 97, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'wojjan0211', MD5('wojjan0211'), 'Wojciech', 'Jankowski', '1998-11-02', 'Polska', 'ul. Brzozowa 108, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'stazie1003', MD5('stazie1003'), 'Stanisław', 'Zieliński', '2003-03-10', 'Polska', 'ul. Lipowa 114, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'kacgra2203', MD5('kacgra2203'), 'Kacper', 'Grabowski', '2001-03-22', 'Polska', 'ul. Szkolna 112, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'mikwro1105', MD5('mikwro1105'), 'Mikołaj', 'Wróbel', '1998-05-11', 'Polska', 'ul. Kwiatowa 54, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'macwie0402', MD5('macwie0402'), 'Maciej', 'Wieczorek', '1997-02-04', 'Polska', 'ul. Leśna 118, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'mikwie1012', MD5('mikwie1012'), 'Mikołaj', 'Wieczorek', '1995-12-10', 'Polska', 'ul. Słoneczna 15, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'marpio1304', MD5('marpio1304'), 'Marcin', 'Piotrowski', '2002-04-13', 'Polska', 'ul. Słoneczna 53, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'filmaz2802', MD5('filmaz2802'), 'Filip', 'Mazur', '1997-02-28', 'Polska', 'ul. Kwiatowa 8, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'pawkac2111', MD5('pawkac2111'), 'Paweł', 'Kaczmarek', '1995-11-21', 'Polska', 'ul. Szkolna 36, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'piokro2305', MD5('piokro2305'), 'Piotr', 'Król', '1995-05-23', 'Polska', 'ul. Polna 74, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'darjan2106', MD5('darjan2106'), 'Dariusz', 'Jankowski', '2006-06-21', 'Polska', 'ul. Lipowa 85, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'robste0208', MD5('robste0208'), 'Robert', 'Stępień', '2005-08-02', 'Polska', 'ul. Polna 65, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'kamwro2710', MD5('kamwro2710'), 'Kamil', 'Wróbel', '2005-10-27', 'Polska', 'ul. Słoneczna 58, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'stakwi3009', MD5('stakwi3009'), 'Stanisław', 'Kwiatkowski', '1998-09-30', 'Polska', 'ul. Lipowa 32, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'luknow0307', MD5('luknow0307'), 'Łukasz', 'Nowak', '2006-07-03', 'Polska', 'ul. Polna 128, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'macpaw2506', MD5('macpaw2506'), 'Maciej', 'Pawłowski', '2006-06-25', 'Polska', 'ul. Ogrodowa 77, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'julwro2905', MD5('julwro2905'), 'Julian', 'Wróbel', '1999-05-29', 'Polska', 'ul. Szkolna 27, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'hendia1304', MD5('hendia1304'), 'Henry', 'Diabang', '2004-04-13', 'Kongo', 'ul. Brzozowa 53, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'robwie1808', MD5('robwie1808'), 'Robert', 'Wieczorek', '1998-08-18', 'Polska', 'ul. Lipowa 131, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'szykow2810', MD5('szykow2810'), 'Szymon', 'Kowalski', '2002-10-28', 'Polska', 'ul. Lipowa 46, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'filkoz2503', MD5('filkoz2503'), 'Filip', 'Kozłowski', '1995-03-25', 'Polska', 'ul. Brzozowa 12, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'mikmaj2311', MD5('mikmaj2311'), 'Mikołaj', 'Majewski', '1998-11-23', 'Polska', 'ul. Leśna 1, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'adalew0405', MD5('adalew0405'), 'Adam', 'Lewandowski', '1999-05-04', 'Polska', 'ul. Leśna 95, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'ivaale0902', MD5('ivaale0902'), 'Ivan', 'Aleksenko', '1999-02-09', 'Ukraina', 'ul. Szkolna 75, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'matkow1107', MD5('matkow1107'), 'Mateusz', 'Kowalski', '2004-07-11', 'Polska', 'ul. Łąkowa 135, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'matmic0301', MD5('matmic0301'), 'Mateusz', 'Michalski', '2001-01-03', 'Polska', 'ul. Krótka 31, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'tomkwi1602', MD5('tomkwi1602'), 'Tomasz', 'Kwiatkowski', '2005-02-16', 'Polska', 'ul. Kwiatowa 4, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'pawkwi2204', MD5('pawkwi2204'), 'Paweł', 'Kwiatkowski', '1999-04-22', 'Polska', 'ul. Szkolna 57, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'micpio1203', MD5('micpio1203'), 'Michał', 'Piotrowski', '1999-03-12', 'Polska', 'ul. Polna 25, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'szyjab0709', MD5('szyjab0709'), 'Szymon', 'Jabłoński', '2005-09-07', 'Polska', 'ul. Krótka 122, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'marmaz2810', MD5('marmaz2810'), 'Mariusz', 'Mazur', '1996-10-28', 'Polska', 'ul. Słoneczna 90, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'marwis1911', MD5('marwis1911'), 'Marcin', 'Wiśniewski', '1997-11-19', 'Polska', 'ul. Brzozowa 98, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'frapaw0403', MD5('frapaw0403'), 'Franciszek', 'Pawłowski', '1996-03-04', 'Polska', 'ul. Szkolna 7, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'staste1912', MD5('staste1912'), 'Stanisław', 'Stępień', '2002-12-19', 'Polska', 'ul. Ogrodowa 46, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'alemic2705', MD5('alemic2705'), 'Aleksander', 'Michalski', '2005-05-27', 'Polska', 'ul. Słoneczna 34, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'wojgra0708', MD5('wojgra0708'), 'Wojciech', 'Grabowski', '1995-08-07', 'Polska', 'ul. Polna 12, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'julwoj0405', MD5('julwoj0405'), 'Julian', 'Wójcik', '2002-05-04', 'Polska', 'ul. Szkolna 118, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'mikkow0901', MD5('mikkow0901'), 'Mikołaj', 'Kowalczyk', '1998-01-09', 'Polska', 'ul. Brzozowa 137, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'jakjan2807', MD5('jakjan2807'), 'Jakub', 'Jankowski', '2003-07-28', 'Polska', 'ul. Szkolna 122, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'wojste2506', MD5('wojste2506'), 'Wojciech', 'Stępień', '1998-06-25', 'Polska', 'ul. Łąkowa 143, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'krzwie2610', MD5('krzwie2610'), 'Krzysztof', 'Wieczorek', '1999-10-26', 'Polska', 'ul. Łąkowa 13, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'markro0406', MD5('markro0406'), 'Marcin', 'Król', '2005-06-04', 'Polska', 'ul. Polna 133, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'wojpio1807', MD5('wojpio1807'), 'Wojciech', 'Piotrowski', '2003-07-18', 'Polska', 'ul. Łąkowa 141, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'pawjan0412', MD5('pawjan0412'), 'Paweł', 'Jankowski', '1996-12-04', 'Polska', 'ul. Krótka 73, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'janlew1712', MD5('janlew1712'), 'Jan', 'Lewandowski', '2006-12-17', 'Polska', 'ul. Lipowa 108, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'darzaj1511', MD5('darzaj1511'), 'Dariusz', 'Zając', '1999-11-15', 'Polska', 'ul. Leśna 119, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'darwoz2107', MD5('darwoz2107'), 'Dariusz', 'Woźniak', '2005-07-21', 'Polska', 'ul. Słoneczna 76, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'szykoz0512', MD5('szykoz0512'), 'Szymon', 'Kozłowski', '2005-12-05', 'Polska', 'ul. Ogrodowa 82, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'filjan2703', MD5('filjan2703'), 'Filip', 'Jankowski', '1999-03-27', 'Polska', 'ul. Polna 31, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'grzkoz2705', MD5('grzkoz2705'), 'Grzegorz', 'Kozłowski', '1997-05-27', 'Polska', 'ul. Kwiatowa 37, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'mardab2208', MD5('mardab2208'), 'Marcin', 'Dąbrowski', '2005-08-22', 'Polska', 'ul. Łąkowa 136, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'wojwis0208', MD5('wojwis0208'), 'Wojciech', 'Wiśniewski', '2005-08-02', 'Polska', 'ul. Lipowa 79, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'filmaz0604', MD5('filmaz0604'), 'Filip', 'Mazur', '1996-04-06', 'Polska', 'ul. Brzozowa 135, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'ilyram1612', MD5('ilyram1612'), 'Ilya', 'Ramchuk', '2006-12-16', 'Ukraina', 'ul. Szkolna 34, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'marlew2212', MD5('marlew2212'), 'Marcin', 'Lewandowski', '1997-12-22', 'Polska', 'ul. Krótka 57, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'olemud0809', MD5('olemud0809'), 'Oleksandr', 'Mudryk', '2001-09-08', 'Ukraina', 'ul. Szkolna 17, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'piokoz0301', MD5('piokoz0301'), 'Piotr', 'Kozłowski', '1997-01-03', 'Polska', 'ul. Słoneczna 104, Wrocław', FALSE),
    ((SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'robkow0301', MD5('robkow0301'), 'Robert', 'Kowalczyk', '1995-01-03', 'Polska', 'ul. Ogrodowa 37, Wrocław', FALSE);


INSERT INTO PrzynaleznosciTrenerow (TrenerID, DruzynaWLidzeID)
SELECT T.UzytkownikID, D.DruzynaWLidzeID
FROM (
    SELECT UzytkownikID, ROW_NUMBER() OVER (ORDER BY UzytkownikID) as rn
    FROM Uzytkownicy u
    JOIN Role r ON u.RolaID = r.RolaID
    WHERE r.RolaNazwa = 'Trener'
) T
JOIN (
    SELECT DruzynaWLidzeID, ROW_NUMBER() OVER (ORDER BY DruzynaWLidzeID) as rn
    FROM DruzynyWLidze
) D ON T.rn = D.rn
WHERE T.rn <= 8;


INSERT INTO PrzynaleznosciPilkarzy (PilkarzID, DruzynaWLidzeID)
SELECT P.UzytkownikID, D.DruzynaWLidzeID
FROM (
    SELECT UzytkownikID, ROW_NUMBER() OVER (ORDER BY UzytkownikID) as rn
    FROM Uzytkownicy u
    JOIN Role r ON u.RolaID = r.RolaID
    WHERE r.RolaNazwa = 'Pilkarz'
) P
JOIN (
    SELECT DruzynaWLidzeID, ROW_NUMBER() OVER (ORDER BY DruzynaWLidzeID) as rn
    FROM DruzynyWLidze
) D ON D.rn = CEIL(P.rn / 7.0) 
WHERE P.rn <= 56;



INSERT INTO Mecze (MeczLigaSezonID, MeczGospodarzID, MeczGoscID, MeczSedziaID, MeczData, MeczWynikGospodarz, MeczWynikGosc, MeczCzyZagrany)
VALUES 
    -- Kolejka 1 (22.03.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'), -- Sędzia 1
        '2025-03-22', 2, 0, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'), -- Sędzia 2
        '2025-03-22', 3, 4, TRUE
    ),

    -- Kolejka 2 (29.03.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-03-29', 2, 2, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-03-29', 1, 1, TRUE
    ),

    -- Kolejka 3 (05.04.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'),
        '2025-04-05', 3, 1, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'),
        '2025-04-05', 4, 1, TRUE
    ),

    -- Rewanże: Kolejka 4 (12.04.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-04-12', 0, 2, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-04-12', 1, 1, TRUE
    ),

    -- Rewanże: Kolejka 5 (19.04.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'),
        '2025-04-19', 1, 0, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'),
        '2025-04-19', 2, 1, TRUE
    ),

    -- Rewanże: Kolejka 6 (26.04.2025)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'ODR'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'OPO'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-04-26', 3, 1, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L1' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'PPP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'BLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-04-26', 0, 0, TRUE
    );

-- Dodawanie meczów dla Ligi 2 (L2)
INSERT INTO Mecze (MeczLigaSezonID, MeczGospodarzID, MeczGoscID, MeczSedziaID, MeczData, MeczWynikGospodarz, MeczWynikGosc, MeczCzyZagrany)
VALUES 
    -- Kolejka 1
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'),
        '2025-03-23', 2, 0, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'),
        '2025-03-23', 3, 4, TRUE
    ),
    -- Kolejka 2
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-03-30', 2, 2, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-03-30', 1, 1, TRUE
    ),
    -- Kolejka 3
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'),
        '2025-04-06', 3, 1, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'),
        '2025-04-06', 4, 1, TRUE
    ),
    -- Kolejka 4 (Rewanże)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-04-13', 0, 2, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-04-13', 1, 1, TRUE
    ),
    -- Kolejka 5 (Rewanże)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'wojjab0209'),
        '2025-04-20', 1, 0, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'jakols1101'),
        '2025-04-20', 2, 1, TRUE
    ),
    -- Kolejka 6 (Rewanże)
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'LLE'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'KRZ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'alekoz2701'),
        '2025-04-27', 3, 1, TRUE
    ),
    (
        (SELECT LigaSezonID FROM LigaSezon ls JOIN Ligi l ON ls.LigaID = l.LigaID JOIN Sezony s ON ls.SezonID = s.SezonID WHERE l.LigaSkrot = 'L2' AND s.SezonRok = 2025),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'SEP'),
        (SELECT dwl.DruzynaWLidzeID FROM DruzynyWLidze dwl JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID WHERE d.DruzynaSkrot = 'GAJ'),
        (SELECT UzytkownikID FROM Uzytkownicy WHERE UzytkownikLogin = 'tomdab1706'),
        '2025-04-27', 0, 0, TRUE
    );

-- Aktualizacja LIGA 1
-- ODR: 6m, 10pkt, 3W, 1R, 2P, 10-7
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 10,
    DruzynaWLidzeWygrane = 3,
    DruzynaWLidzeRemisy = 1,
    DruzynaWLidzePorazki = 2,
    DruzynaWLidzeGoleZdobyte = 10,
    DruzynaWLidzeGoleStracone = 7
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'ODR');

-- PPP (w tabeli jako PPL): 6m, 2pkt, 0W, 2R, 4P, 3-11
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 2,
    DruzynaWLidzeWygrane = 0,
    DruzynaWLidzeRemisy = 2,
    DruzynaWLidzePorazki = 4,
    DruzynaWLidzeGoleZdobyte = 3,
    DruzynaWLidzeGoleStracone = 11
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'PPP');

-- BLE: 6m, 9pkt, 2W, 3R, 1P, 11-8
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 9,
    DruzynaWLidzeWygrane = 2,
    DruzynaWLidzeRemisy = 3,
    DruzynaWLidzePorazki = 1,
    DruzynaWLidzeGoleZdobyte = 11,
    DruzynaWLidzeGoleStracone = 8
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'BLE');

-- OPO: 6m, 11pkt, 3W, 2R, 1P, 12-10
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 11,
    DruzynaWLidzeWygrane = 3,
    DruzynaWLidzeRemisy = 2,
    DruzynaWLidzePorazki = 1,
    DruzynaWLidzeGoleZdobyte = 12,
    DruzynaWLidzeGoleStracone = 10
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'OPO');


-- Aktualizacja LIGA 2
-- LLE: 6m, 10pkt, 3W, 1R, 2P, 10-7
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 10,
    DruzynaWLidzeWygrane = 3,
    DruzynaWLidzeRemisy = 1,
    DruzynaWLidzePorazki = 2,
    DruzynaWLidzeGoleZdobyte = 10,
    DruzynaWLidzeGoleStracone = 7
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'LLE');

-- SEP: 6m, 2pkt, 0W, 2R, 4P, 3-11
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 2,
    DruzynaWLidzeWygrane = 0,
    DruzynaWLidzeRemisy = 2,
    DruzynaWLidzePorazki = 4,
    DruzynaWLidzeGoleZdobyte = 3,
    DruzynaWLidzeGoleStracone = 11
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'SEP');

-- GAJ: 6m, 9pkt, 2W, 3R, 1P, 11-8
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 9,
    DruzynaWLidzeWygrane = 2,
    DruzynaWLidzeRemisy = 3,
    DruzynaWLidzePorazki = 1,
    DruzynaWLidzeGoleZdobyte = 11,
    DruzynaWLidzeGoleStracone = 8
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'GAJ');

-- KRZ: 6m, 11pkt, 3W, 2R, 1P, 12-10
UPDATE DruzynyWLidze
SET DruzynaWLidzeMeczeZagrane = 6,
    DruzynaWLidzePunkty = 11,
    DruzynaWLidzeWygrane = 3,
    DruzynaWLidzeRemisy = 2,
    DruzynaWLidzePorazki = 1,
    DruzynaWLidzeGoleZdobyte = 12,
    DruzynaWLidzeGoleStracone = 10
WHERE DruzynaID = (SELECT DruzynaID FROM Druzyny WHERE DruzynaSkrot = 'KRZ');