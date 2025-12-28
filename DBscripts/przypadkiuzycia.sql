-- PU01) Logowanie uzytkownika
SELECT UzytkownikHaslo FROM Uzytkownicy WHERE UzytkownikLogin='wojjab0209';

-- PU03) Edycja wlasnych danych
SELECT UzytkownikID, UzytkownikImie, UzytkownikNazwisko, UzytkownikAdres, UzytkownikHaslo, UzytkownikNarodowosc
FROM Uzytkownicy WHERE UzytkownikID=20;

UPDATE Uzytkownicy
SET 
    UzytkownikAdres = 'ul. Zwycięska 10, Wrocław',
    UzytkownikImie = 'Mac',
    UzytkownikNazwisko = 'Wiestal',
    UzytkownikNarodowosc = 'Szwecja',
    UzytkownikHaslo = MD5('macwie0204')
WHERE UzytkownikID=20;

SELECT UzytkownikID, UzytkownikImie, UzytkownikNazwisko, UzytkownikAdres, UzytkownikHaslo, UzytkownikNarodowosc
FROM Uzytkownicy WHERE UzytkownikID=20;

-- PU04) Przegladanie lig
SELECT l.LigaNazwa, l.LigaSkrot, s.SezonRok FROM Ligi l
JOIN LigaSezon ls ON l.LigaID = ls.LigaID
JOIN Sezony s ON ls.SezonID = s.SezonID
WHERE s.SezonRok = 2025;

-- PU05) Przeglądanie tabel ligowych
DROP VIEW IF EXISTS WidokTabeliLigowej;

CREATE OR REPLACE VIEW WidokTabeliLigowej AS
SELECT 
    dwl.LigaSezonID,
    d.DruzynaNazwa,
    dwl.DruzynaWLidzeMeczeZagrane AS M,
    dwl.DruzynaWLidzePunkty AS Pkt,
    dwl.DruzynaWLidzeWygrane AS W,
    dwl.DruzynaWLidzeRemisy AS R,
    dwl.DruzynaWLidzePorazki AS P,
    dwl.DruzynaWLidzeGoleZdobyte AS GZ,
    dwl.DruzynaWLidzeGoleStracone AS GS,
    (dwl.DruzynaWLidzeGoleZdobyte - dwl.DruzynaWLidzeGoleStracone) AS Bilans
FROM DruzynyWLidze dwl
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID;

SELECT 
    ROW_NUMBER() OVER (ORDER BY Pkt DESC, Bilans DESC) AS Miejsce,
    DruzynaNazwa,
    M, Pkt, W, R, P, GZ, GS, Bilans
FROM WidokTabeliLigowej
WHERE LigaSezonID = 1;

-- PU06) Przeglądanie meczów
CREATE OR REPLACE VIEW WidokMeczy AS
SELECT 
    m.MeczID,
    m.MeczLigaSezonID,
    m.MeczData,
    m.MeczCzyZagrany,
    d_gosp.DruzynaNazwa AS Gospodarz,
    d_gosp.DruzynaID AS GospodarzID,
    m.MeczWynikGospodarz,
    m.MeczWynikGosc,
    d_gosc.DruzynaNazwa AS Gosc,
    d_gosc.DruzynaID AS GoscID,
    u.UzytkownikImie || ' ' || u.UzytkownikNazwisko AS Sedzia,
    u.UzytkownikID AS SedziaID
FROM Mecze m
JOIN DruzynyWLidze dwl_gosp ON m.MeczGospodarzID = dwl_gosp.DruzynaWLidzeID
JOIN Druzyny d_gosp ON dwl_gosp.DruzynaID = d_gosp.DruzynaID
JOIN DruzynyWLidze dwl_gosc ON m.MeczGoscID = dwl_gosc.DruzynaWLidzeID
JOIN Druzyny d_gosc ON dwl_gosc.DruzynaID = d_gosc.DruzynaID
LEFT JOIN Uzytkownicy u ON m.MeczSedziaID = u.UzytkownikID;

-- 1. Wszystkie mecze w Lidze 1 (Sezon 2025)
SELECT MeczData, Gospodarz, MeczWynikGospodarz, MeczWynikGosc, Gosc, MeczCzyZagrany 
FROM WidokMeczy
WHERE MeczLigaSezonID = 1
ORDER BY MeczData;

-- 2. Tylko mecze ROZEGRANE (z wynikami)
SELECT MeczData, Gospodarz, MeczWynikGospodarz, MeczWynikGosc, Gosc 
FROM WidokMeczy
WHERE MeczLigaSezonID = 1 
  AND MeczCzyZagrany = TRUE;

-- 3. Tylko mecze NIEROZEGRANE (Terminarz przyszły)
SELECT MeczData, Gospodarz, Gosc 
FROM WidokMeczy
WHERE MeczLigaSezonID = 1 
  AND MeczCzyZagrany = FALSE;

-- Wszystkie mecze Odry (jako gospodarz LUB gość)
SELECT MeczData, Gospodarz, MeczWynikGospodarz, MeczWynikGosc, Gosc, MeczCzyZagrany
FROM WidokMeczy
WHERE (GospodarzID = 1 OR GoscID = 1)
ORDER BY MeczData;

-- Mecze sędziowane przez konkretnego sędziego (np. ID usera = 2)
SELECT MeczData, Gospodarz, Gosc, MeczWynikGospodarz, MeczWynikGosc, Sedzia
FROM WidokMeczy
WHERE SedziaID = 2;

-- PU07) Przeglądanie statystyk indywidualnych
CREATE OR REPLACE VIEW WidokStatystykPilkarzy AS
SELECT 
    dwl.LigaSezonID,
    d.DruzynaID,
    d.DruzynaNazwa,
    u.UzytkownikImie,
    u.UzytkownikNazwisko,
    pp.LiczbaGoli,
    pp.ZolteKartki,
    pp.CzerwoneKartki
FROM PrzynaleznosciPilkarzy pp
JOIN Uzytkownicy u ON pp.PilkarzID = u.UzytkownikID
JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID;

-- Ranking Strzelców w Lidze 1
SELECT 
    ROW_NUMBER() OVER (ORDER BY LiczbaGoli DESC) AS Miejsce,
    UzytkownikImie, 
    UzytkownikNazwisko, 
    DruzynaNazwa, 
    LiczbaGoli
FROM WidokStatystykPilkarzy
WHERE LigaSezonID = 1
  AND LiczbaGoli > 0
ORDER BY LiczbaGoli DESC;

-- Ranking Kartek w Lidze 1
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    DruzynaNazwa, 
    CzerwoneKartki,
    ZolteKartki
FROM WidokStatystykPilkarzy
WHERE LigaSezonID = 1
  AND (CzerwoneKartki > 0 OR ZolteKartki > 0)
ORDER BY CzerwoneKartki DESC, ZolteKartki DESC;

-- Statystyki wewnętrzne drużyny (ID = 1)
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    LiczbaGoli,
    ZolteKartki,
    CzerwoneKartki
FROM WidokStatystykPilkarzy
WHERE DruzynaID = 1
ORDER BY UzytkownikNazwisko;

-- PU08) Przeglądanie historii poprzednich sezonów
-- tak jak wyzej, tylko inne id sezonow

-- PU09) Przeglądanie listy piłkarzy - wiele możliwości:
CREATE OR REPLACE VIEW WidokListyPilkarzy AS
SELECT 
    u.UzytkownikID,
    u.UzytkownikImie, 
    u.UzytkownikNazwisko,
    u.UzytkownikNarodowosc,
    u.UzytkownikDataUrodzenia,
    d.DruzynaNazwa,
    d.DruzynaID,
    dwl.LigaSezonID
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
LEFT JOIN PrzynaleznosciPilkarzy pp ON u.UzytkownikID = pp.PilkarzID
LEFT JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
LEFT JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
WHERE r.RolaNazwa = 'Pilkarz';

CREATE OR REPLACE VIEW WidokTrenerow AS
SELECT 
    u.UzytkownikID,
    u.UzytkownikImie, 
    u.UzytkownikNazwisko,
    u.UzytkownikNarodowosc,
    u.UzytkownikDataUrodzenia,
    d.DruzynaNazwa,
    d.DruzynaID,
    dwl.LigaSezonID
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
-- LEFT JOIN, aby pokazać też trenerów, którzy nie mają przypisanego klubu
LEFT JOIN PrzynaleznosciTrenerow pt ON u.UzytkownikID = pt.TrenerID
LEFT JOIN DruzynyWLidze dwl ON pt.DruzynaWLidzeID = dwl.DruzynaWLidzeID
LEFT JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
WHERE r.RolaNazwa = 'Trener';

-- Test PU09: Lista wszystkich piłkarzy w systemie
SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    UzytkownikNarodowosc, 
    COALESCE(DruzynaNazwa, 'BRAK KLUBU') AS Klub
FROM WidokListyPilkarzy
ORDER BY UzytkownikNazwisko;

SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    UzytkownikDataUrodzenia,
    UzytkownikNarodowosc
FROM WidokListyPilkarzy
WHERE DruzynaID = 2
ORDER BY UzytkownikNazwisko;

-- Bonus: Lista piłkarzy bez klubu
SELECT UzytkownikImie, UzytkownikNazwisko
FROM WidokListyPilkarzy
WHERE DruzynaID IS NULL;

-- PU10) Przeglądanie listy klubów
CREATE OR REPLACE VIEW WidokKlubowWLidze AS
SELECT 
    ls.LigaSezonID,         -- Klucz do filtrowania (np. Liga 1 w 2025)
    l.LigaNazwa,
    s.SezonRok,
    d.DruzynaNazwa,
    d.DruzynaSkrot,
    d.DruzynaStadion,
    d.DruzynaAdres,
    d.DruzynaDataZalozenia
FROM DruzynyWLidze dwl
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
JOIN Ligi l ON ls.LigaID = l.LigaID
JOIN Sezony s ON ls.SezonID = s.SezonID;

-- Test PU10: Lista wszystkich klubów w bazie (Globalny rejestr)
SELECT 
    DruzynaNazwa, 
    DruzynaSkrot, 
    DruzynaAdres,
    DruzynaStadion
FROM Druzyny
ORDER BY DruzynaNazwa;

SELECT 
    DruzynaNazwa, 
    DruzynaSkrot, 
    DruzynaStadion, 
    DruzynaAdres
FROM WidokKlubowWLidze
WHERE LigaSezonID = 1 -- Tutaj zmieniasz ID w zależności od wyboru użytkownika
ORDER BY DruzynaNazwa;

-- PU11) Przeglądanie listy sedziow
CREATE OR REPLACE VIEW WidokSedziow AS
SELECT 
    u.UzytkownikID,
    u.UzytkownikImie,
    u.UzytkownikNazwisko,
    u.UzytkownikNarodowosc,
    COUNT(m.MeczID) AS LiczbaSedziowanychMeczy
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
LEFT JOIN Mecze m ON u.UzytkownikID = m.MeczSedziaID
WHERE r.RolaNazwa = 'Sedzia'
GROUP BY u.UzytkownikID, u.UzytkownikImie, u.UzytkownikNazwisko, u.UzytkownikNarodowosc;

SELECT 
    UzytkownikImie, 
    UzytkownikNazwisko, 
    LiczbaSedziowanychMeczy
FROM WidokSedziow
ORDER BY LiczbaSedziowanychMeczy DESC, UzytkownikNazwisko ASC;

-- PU12) Dodanie drużyny
INSERT INTO Druzyny (
    DruzynaNazwa, DruzynaSkrot, DruzynaDataZalozenia, DruzynaStrojeDom, DruzynaStrojeWyjazd, DruzynaStadion, DruzynaAdres) 
VALUES (
    '1. KS Ślęza Wrocław', 'SLE', CURRENT_DATE, 'Żółto-Czerwony', 'Czarny', 'Centrum Piłkarskie Ślęza', 'ul. Kłokoczycka 5, Wrocław'
);

-- PU13 PU14 PU15
INSERT INTO Uzytkownicy (
    RolaID, UzytkownikLogin, UzytkownikHaslo, UzytkownikImie, UzytkownikNazwisko, UzytkownikDataUrodzenia, 
    UzytkownikNarodowosc, UzytkownikAdres,UzytkownikSzukaKlubu 
)
VALUES (
    (SELECT RolaID FROM Role WHERE RolaNazwa = 'Pilkarz'), 'roblew2108', MD5('roblew2108'), 
    'Robert', 'Lewandowski', '1988-08-21', 'Polska', 'ul. Złota 44, Warszawa',TRUE
);

-- PU16) Rozpoczecie nowego sezonu
INSERT INTO Sezony (SezonRok, SezonDataPoczatku, SezonDataKonca, SezonCzyAktywny)
VALUES (2026, '2026-03-15', '2026-07-15', TRUE);

-- PU17) Dodanie drużyny do ligi
-- Funkcja sprawdzajaca: "Czy ta druzyna gra juz w JAKIEJKOLWIEK lidze tego sezonu?"
CREATE OR REPLACE FUNCTION sprawdz_duplikat_druzyny_w_sezonie()
RETURNS TRIGGER AS $$
DECLARE
    v_SezonID INT;
    v_Count INT;
BEGIN
    -- 1. Sprawdzamy, jakiego sezonu dotyczy LigaSezonID
    SELECT SezonID INTO v_SezonID 
    FROM LigaSezon 
    WHERE LigaSezonID = NEW.LigaSezonID;

    -- 2. Liczymy, w ilu ligach TEGO sezonu ta druzyna jest
    SELECT COUNT(*) INTO v_Count
    FROM DruzynyWLidze dwl
    JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
    WHERE dwl.DruzynaID = NEW.DruzynaID
      AND ls.SezonID = v_SezonID;

    -- 3. Jesli wynik > 0, blokujemy wstawienie
    IF v_Count > 0 THEN
        RAISE EXCEPTION 'Blad: Ta druzyna (ID=%) bierze juz udzial w rozgrywkach w tym sezonie!', NEW.DruzynaID;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Podpiecie funkcji do tabeli
CREATE TRIGGER trg_blokada_multiligi
BEFORE INSERT ON DruzynyWLidze
FOR EACH ROW
EXECUTE FUNCTION sprawdz_duplikat_druzyny_w_sezonie();

-- SCENARIUSZ: Admin dodaje druzyne(ID=12) do Ligi 1 (ID=1)
INSERT INTO DruzynyWLidze (LigaSezonID, DruzynaID)
VALUES (1, 12);

-- Weryfikacja:
SELECT * FROM DruzynyWLidze WHERE DruzynaID = 12;

-- PU18) Harmonogram, dodanie meczu
INSERT INTO Mecze (
    MeczLigaSezonID, 
    MeczGospodarzID, -- To jest ID z tabeli DruzynyWLidze, nie z Druzyny!
    MeczGoscID, 
    MeczData
) 
VALUES (1, 1, 2, '2025-05-10');

-- PU19) Przydzielenie sedziego do meczu
UPDATE Mecze
SET MeczSedziaID = 10
WHERE MeczID = 5;

-- PU20) Potwierdzenie daty rozegrania meczu
UPDATE Mecze
SET 
    MeczData = MeczDataProponowana,
    MeczStatusTerminu = 3
WHERE MeczID = 10 
  AND MeczStatusTerminu = 2;

-- PU21) Rozpatrzenie prośby o zawieszenie zawodnika
UPDATE WnioskiOZawieszenie
SET 
    WniosekOZawieszenieCzyRozpatrzony = TRUE,
    WniosekOZawieszenieDecyzja = 1,
    WniosekOZawieszenieDlugoscKary = 3 
WHERE WniosekOZawieszenieID = (SELECT MAX(WniosekOZawieszenieID) FROM WnioskiOZawieszenie) -- Bierzemy ostatni dodany wniosek
  AND WniosekOZawieszenieCzyRozpatrzony = FALSE;

-- PU22) Zakończenie i archiwizacja sezonu
UPDATE Sezony
SET SezonCzyAktywny = FALSE
WHERE SezonCzyAktywny = TRUE
  AND CURRENT_DATE > SezonDataKonca;

-- PU23) Dodanie piłkarza do drużyny
-- Funkcja sprawdzająca: Czy piłkarz ma już kontrakt w tym sezonie?
CREATE OR REPLACE FUNCTION sprawdz_multiklubowosc_pilkarza()
RETURNS TRIGGER AS $$
DECLARE
    v_TargetSezonID INT;
    v_Count INT;
BEGIN
    -- 1. Ustalmy, jakiego sezonu dotyczy drużyna, do której piłkarz chce dołączyć
    SELECT ls.SezonID INTO v_TargetSezonID
    FROM DruzynyWLidze dwl
    JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
    WHERE dwl.DruzynaWLidzeID = NEW.DruzynaWLidzeID;

    -- 2. Sprawdźmy, czy ten piłkarz (PilkarzID) jest już w tabeli przynależności
    --    w jakiejkolwiek drużynie powiązanej z TYM SAMYM sezonem
    SELECT COUNT(*) INTO v_Count
    FROM PrzynaleznosciPilkarzy pp
    JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
    JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
    WHERE pp.PilkarzID = NEW.PilkarzID
      AND ls.SezonID = v_TargetSezonID;

    -- 3. Jeśli tak, blokujemy transfer
    IF v_Count > 0 THEN
        RAISE EXCEPTION 'Błąd: Ten piłkarz (ID=%) gra już w innej drużynie w tym sezonie!', NEW.PilkarzID;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Podpięcie triggera
CREATE TRIGGER trg_jeden_klub_na_sezon
BEFORE INSERT ON PrzynaleznosciPilkarzy
FOR EACH ROW
EXECUTE FUNCTION sprawdz_multiklubowosc_pilkarza();

INSERT INTO PrzynaleznosciPilkarzy (PilkarzID, DruzynaWLidzeID)
VALUES (75, 5);

-- PU24) Edytowanie danych drużyny

-- PU25) Usunięcie zawodnika ze swojej drużyny

-- PU26) Przegląd zawodników szukających klubu
-- tutaj juz bylo

-- PU27) Złożenie propozycji daty meczu
UPDATE Mecze
SET 
    MeczDataProponowana = '2025-06-01',
    MeczStatusTerminu = 1
WHERE MeczID = 10;

-- PU28) Akceptacja propozycji daty meczu
UPDATE Mecze
SET 
    MeczStatusTerminu = 2
WHERE MeczID = 10 
  AND MeczStatusTerminu = 1;

-- PU29) Wprowadzenie wyniku meczu
UPDATE Mecze
SET 
    MeczWynikGospodarz = 3,
    MeczWynikGosc = 0,
    MeczCzyZagrany = TRUE
WHERE MeczID = (SELECT MAX(MeczID) FROM Mecze);

-- 1. Funkcja aktualizująca tabelę ligową
CREATE OR REPLACE FUNCTION aktualizuj_statystyki_po_meczu()
RETURNS TRIGGER AS $$
BEGIN
    -- Uruchamiamy logikę TYLKO, gdy mecz zmienia status z "Nierozegrany" na "Rozegrany"
    IF NEW.MeczCzyZagrany = TRUE AND OLD.MeczCzyZagrany = FALSE THEN
        
        -- A) Aktualizacja GOSPODARZA
        UPDATE DruzynyWLidze
        SET 
            DruzynaWLidzeMeczeZagrane = DruzynaWLidzeMeczeZagrane + 1,
            DruzynaWLidzeGoleZdobyte = DruzynaWLidzeGoleZdobyte + NEW.MeczWynikGospodarz,
            DruzynaWLidzeGoleStracone = DruzynaWLidzeGoleStracone + NEW.MeczWynikGosc,
            -- Punkty: 3 za wygraną, 1 za remis
            DruzynaWLidzePunkty = DruzynaWLidzePunkty + CASE 
                WHEN NEW.MeczWynikGospodarz > NEW.MeczWynikGosc THEN 3
                WHEN NEW.MeczWynikGospodarz = NEW.MeczWynikGosc THEN 1
                ELSE 0 END,
            -- Wygrane
            DruzynaWLidzeWygrane = DruzynaWLidzeWygrane + CASE 
                WHEN NEW.MeczWynikGospodarz > NEW.MeczWynikGosc THEN 1 ELSE 0 END,
            -- Remisy
            DruzynaWLidzeRemisy = DruzynaWLidzeRemisy + CASE 
                WHEN NEW.MeczWynikGospodarz = NEW.MeczWynikGosc THEN 1 ELSE 0 END,
            -- Porażki
            DruzynaWLidzePorazki = DruzynaWLidzePorazki + CASE 
                WHEN NEW.MeczWynikGospodarz < NEW.MeczWynikGosc THEN 1 ELSE 0 END
        WHERE DruzynaWLidzeID = NEW.MeczGospodarzID;

        -- B) Aktualizacja GOŚCIA (Analogicznie, ale odwrotne wyniki)
        UPDATE DruzynyWLidze
        SET 
            DruzynaWLidzeMeczeZagrane = DruzynaWLidzeMeczeZagrane + 1,
            DruzynaWLidzeGoleZdobyte = DruzynaWLidzeGoleZdobyte + NEW.MeczWynikGosc,
            DruzynaWLidzeGoleStracone = DruzynaWLidzeGoleStracone + NEW.MeczWynikGospodarz,
            DruzynaWLidzePunkty = DruzynaWLidzePunkty + CASE 
                WHEN NEW.MeczWynikGosc > NEW.MeczWynikGospodarz THEN 3
                WHEN NEW.MeczWynikGosc = NEW.MeczWynikGospodarz THEN 1
                ELSE 0 END,
            DruzynaWLidzeWygrane = DruzynaWLidzeWygrane + CASE 
                WHEN NEW.MeczWynikGosc > NEW.MeczWynikGospodarz THEN 1 ELSE 0 END,
            DruzynaWLidzeRemisy = DruzynaWLidzeRemisy + CASE 
                WHEN NEW.MeczWynikGosc = NEW.MeczWynikGospodarz THEN 1 ELSE 0 END,
            DruzynaWLidzePorazki = DruzynaWLidzePorazki + CASE 
                WHEN NEW.MeczWynikGosc < NEW.MeczWynikGospodarz THEN 1 ELSE 0 END
        WHERE DruzynaWLidzeID = NEW.MeczGoscID;
        
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_po_wpisaniu_wyniku
AFTER UPDATE ON Mecze
FOR EACH ROW
EXECUTE FUNCTION aktualizuj_statystyki_po_meczu();

-- PU30) Wprowadzenie statystyk indywidualnych meczu

-- PU31) Złożenie wniosku o zawieszenie zawodnika

-- PU32) Ustawienie statusu "szuka klubu"

-- PU33) Opuszczenie drużyny

-- PU34) Przyjęcie dodania do drużyny

-- 