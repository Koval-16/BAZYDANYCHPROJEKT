DROP TABLE IF EXISTS WnioskiOZawieszenie CASCADE;
DROP TABLE IF EXISTS StatystykiMeczu CASCADE;
DROP TABLE IF EXISTS PrzynaleznosciTrenerow CASCADE;
DROP TABLE IF EXISTS PrzynaleznosciPilkarzy CASCADE;
DROP TABLE IF EXISTS Mecze CASCADE;
DROP TABLE IF EXISTS Uzytkownicy CASCADE;
DROP TABLE IF EXISTS DruzynyWLidze CASCADE;
DROP TABLE IF EXISTS LigaSezon CASCADE;
DROP TABLE IF EXISTS Ligi CASCADE;
DROP TABLE IF EXISTS Druzyny CASCADE;
DROP TABLE IF EXISTS Sezony CASCADE;
DROP TABLE IF EXISTS Role CASCADE;

CREATE TABLE Role (
    RolaID SERIAL PRIMARY KEY,
    RolaNazwa VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE Sezony (
    SezonID SERIAL PRIMARY KEY,
    SezonRok INT,
    SezonDataPoczatku DATE,
    SezonDataKonca DATE,
    SezonCzyAktywny BOOLEAN DEFAULT TRUE,
    CONSTRAINT ck_DatySezonu CHECK (SezonDataKonca > SezonDataPoczatku)
);

CREATE TABLE Druzyny (
    DruzynaID SERIAL PRIMARY KEY,
    DruzynaNazwa VARCHAR(100) NOT NULL UNIQUE,
    DruzynaSkrot VARCHAR(4) NOT NULL UNIQUE,
    DruzynaDataZalozenia DATE,
    DruzynaStrojeDom VARCHAR(30),
    DruzynaStrojeWyjazd VARCHAR(30),
    DruzynaStadion VARCHAR(64),
    DruzynaAdres VARCHAR(64)
);

CREATE TABLE Ligi (
    LigaID SERIAL PRIMARY KEY,
    LigaNazwa VARCHAR(100) NOT NULL UNIQUE,
    LigaSkrot VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE LigaSezon (
    LigaSezonID SERIAL PRIMARY KEY,
    SezonID INT NOT NULL REFERENCES Sezony(SezonID),
    LigaID INT NOT NULL REFERENCES Ligi(LigaID),
    UNIQUE(SezonID, LigaID)
);

CREATE TABLE DruzynyWLidze (
    DruzynaWLidzeID SERIAL PRIMARY KEY,
    LigaSezonID INT NOT NULL REFERENCES LigaSezon(LigaSezonID),
    DruzynaID INT NOT NULL REFERENCES Druzyny(DruzynaID),
    DruzynaWLidzeMeczeZagrane INT DEFAULT 0 CHECK (DruzynaWLidzeMeczeZagrane >= 0),
    DruzynaWLidzePunkty INT DEFAULT 0,
    DruzynaWLidzeWygrane INT DEFAULT 0 CHECK (DruzynaWLidzeWygrane >= 0),
    DruzynaWLidzeRemisy INT DEFAULT 0 CHECK (DruzynaWLidzeRemisy >= 0),
    DruzynaWLidzePorazki INT DEFAULT 0 CHECK (DruzynaWLidzePorazki >= 0),
    DruzynaWLidzeGoleZdobyte INT DEFAULT 0 CHECK (DruzynaWLidzeGoleZdobyte >= 0),
    DruzynaWLidzeGoleStracone INT DEFAULT 0 CHECK (DruzynaWLidzeGoleStracone >= 0),
    UNIQUE(LigaSezonID, DruzynaID)
);

CREATE TABLE Uzytkownicy (
    UzytkownikID SERIAL PRIMARY KEY,
    RolaID INT NOT NULL REFERENCES Role(RolaID),
    UzytkownikLogin VARCHAR(50) NOT NULL UNIQUE,
    UzytkownikHaslo VARCHAR(255) NOT NULL,
    UzytkownikImie VARCHAR(50) NOT NULL,
    UzytkownikNazwisko VARCHAR(50) NOT NULL,
    UzytkownikDataUrodzenia DATE NOT NULL,
    UzytkownikNarodowosc VARCHAR(50) NOT NULL,
    UzytkownikAdres VARCHAR(64) NOT NULL,
    UzytkownikSzukaKlubu BOOLEAN DEFAULT FALSE
);

CREATE TABLE Mecze(
    MeczID SERIAL PRIMARY KEY,
    MeczLigaSezonID INT NOT NULL REFERENCES LigaSezon(LigaSezonID),
    MeczGospodarzID INT NOT NULL REFERENCES DruzynyWLidze(DruzynaWLidzeID),
    MeczGoscID INT NOT NULL REFERENCES DruzynyWLidze(DruzynaWLidzeID),
    MeczSedziaID INT REFERENCES Uzytkownicy(UzytkownikID),
    MeczData DATE,
    MeczDataProponowane DATE,
    MeczStatusPropozycji INT,
    MeczWynikGospodarz INT CHECK (MeczWynikGospodarz >= 0),
    MeczWynikGosc INT CHECK (MeczWynikGosc >= 0),
    MeczCzyZagrany BOOLEAN DEFAULT FALSE,
    CONSTRAINT ck_RozneDruzyny CHECK (MeczGospodarzID != MeczGoscID)
);

CREATE TABLE PrzynaleznosciPilkarzy(
    PrzynaleznoscPilkarzaID SERIAL PRIMARY KEY,
    PilkarzID INT NOT NULL REFERENCES Uzytkownicy(UzytkownikID),
    DruzynaWLidzeID INT NOT NULL REFERENCES DruzynyWLidze(DruzynaWLidzeID),
    
    PilkarzLiczbaGoli INT DEFAULT 0 CHECK (PilkarzLiczbaGoli >= 0),
    PilkarzZolteKartki INT DEFAULT 0 CHECK (PilkarzZolteKartki >= 0),
    PilkarzCzerwoneKartki INT DEFAULT 0 CHECK (PilkarzCzerwoneKartki >= 0),
    PilkarzCzyZawieszony BOOLEAN DEFAULT FALSE,
    PilkarzPotwierdzony BOOLEAN DEFAULT FALSE,
    PilkarzAktywny BOOLEAN DEFAULT FALSE,

    UNIQUE(PilkarzID, DruzynaWLidzeID)
);

CREATE TABLE PrzynaleznosciTrenerow(
    PrzynaleznoscTreneraID SERIAL PRIMARY KEY,
    TrenerID INT NOT NULL REFERENCES Uzytkownicy(UzytkownikID),
    DruzynaWLidzeID INT NOT NULL REFERENCES DruzynyWLidze(DruzynaWLidzeID),
    UNIQUE(TrenerID, DruzynaWLidzeID)
);

CREATE TABLE StatystykiMeczu(
    StatystykaMeczuID SERIAL PRIMARY KEY,
    MeczID INT NOT NULL REFERENCES Mecze(MeczID),
    PilkarzID INT NOT NULL REFERENCES PrzynaleznosciPilkarzy(PrzynaleznoscPilkarzaID),
    StatystykaMeczuRodzaj INT NOT NULL
);

CREATE TABLE WnioskiOZawieszenie (
    WniosekOZawieszenieID SERIAL PRIMARY KEY,
    SedziaID INT NOT NULL REFERENCES Uzytkownicy(UzytkownikID),
    PilkarzID INT NOT NULL REFERENCES PrzynaleznosciPilkarzy(PrzynaleznoscPilkarzaID),
    StatystykaMeczuID INT NOT NULL REFERENCES StatystykiMeczu(StatystykaMeczuID),
    -- Status: 0=Oczekuje, 1=Zatwierdzony, 2=Odrzucony
    WniosekOZawieszenieStatus INT,
    MeczZawieszeniaID INT REFERNCES Mecze(MeczID)
);

CREATE OR REPLACE VIEW WidokTabeliLigowej AS
SELECT 
    dwl.LigaSezonID                                                 AS TabelaLigaSezonID,
    dwl.DruzynaWLidzeID                                             AS TabelaDruzynaID,
    d.DruzynaNazwa                                                  AS TabelaDruzynaNazwa,
    dwl.DruzynaWLidzeMeczeZagrane                                   AS TabeleMeczeZagrane,
    dwl.DruzynaWLidzePunkty                                         AS TabelaPunkty,
    dwl.DruzynaWLidzeWygrane                                        AS TabelaWygrane,
    dwl.DruzynaWLidzeRemisy                                         AS TabelaRemisy,
    dwl.DruzynaWLidzePorazki                                        AS TabelaPorazki,
    dwl.DruzynaWLidzeGoleZdobyte                                    AS TabelaGoleZdobyte,
    dwl.DruzynaWLidzeGoleStracone                                   AS TabelaGoleStracone,
    (dwl.DruzynaWLidzeGoleZdobyte - dwl.DruzynaWLidzeGoleStracone)  AS TabelaGoleRoznica
FROM DruzynyWLidze dwl
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID;

CREATE OR REPLACE VIEW WidokMeczy AS
SELECT 
    m.MeczID                                        AS WidokMeczyMeczID,
    m.MeczLigaSezonID                               AS WidokMeczyLigaSezonID,
    m.MeczData                                      AS WidokMeczyData,
    m.MeczCzyZagrany                                AS WidokMeczyCzyZagrany,
    d_gosp.DruzynaNazwa                             AS WidokMeczyGospodarzNazwa,
    d_gosp.DruzynaID                                AS WidokMeczyGospodarzID,
    m.MeczWynikGospodarz                            AS WidokMeczyWynikGospodarz,
    m.MeczWynikGosc                                 AS WidokMeczyWynikGosc,
    d_gosc.DruzynaNazwa                             AS WidokMeczyGoscNazwa,
    d_gosc.DruzynaID                                AS WidokMeczyGoscID,
    u.UzytkownikImie || ' ' || u.UzytkownikNazwisko AS WidokMeczySedziaNazwa,
    u.UzytkownikID                                  AS WidokMeczySedziaID,
    m.MeczDataProponowane                           AS WidokMeczyDataProponowane,
    m.MeczStatusPropozycji                          AS WidokMeczyStatusPropozycji
FROM Mecze m
JOIN DruzynyWLidze dwl_gosp ON m.MeczGospodarzID = dwl_gosp.DruzynaWLidzeID
JOIN Druzyny d_gosp ON dwl_gosp.DruzynaID = d_gosp.DruzynaID
JOIN DruzynyWLidze dwl_gosc ON m.MeczGoscID = dwl_gosc.DruzynaWLidzeID
JOIN Druzyny d_gosc ON dwl_gosc.DruzynaID = d_gosc.DruzynaID
LEFT JOIN Uzytkownicy u ON m.MeczSedziaID = u.UzytkownikID;

CREATE OR REPLACE VIEW WidokStatystykPilkarzy AS
SELECT 
    dwl.LigaSezonID             AS StatystykiLigaSezonID,
    d.DruzynaID                 AS StatystykiDruzynaID,
    d.DruzynaNazwa              AS StatystykiDruzynaNazwa,
    u.UzytkownikImie            AS StatystykiPilkarzImie,
    u.UzytkownikNazwisko        AS StatystykiPilkarzNazwisko,
    pp.PrzynaleznoscPilkarzaID  AS StatystykiPilkarzID,
    pp.PilkarzLiczbaGoli        AS StatystykiPilkarzGole,
    pp.PilkarzZolteKartki       AS StatystykiPilkarzZolteKartki,
    pp.PilkarzCzerwoneKartki    AS StatystykiPilkarzCzerwoneKartki
FROM PrzynaleznosciPilkarzy pp
JOIN Uzytkownicy u ON pp.PilkarzID = u.UzytkownikID
JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID;

CREATE OR REPLACE VIEW WidokListyPilkarzy AS
SELECT 
    u.UzytkownikID              AS ListaPilkarzyPilkarzID,
    u.UzytkownikImie            AS ListaPilkarzyPilkarzImie, 
    u.UzytkownikNazwisko        AS ListaPilkarzyPilkarzNazwisko,
    u.UzytkownikNarodowosc      AS ListaPilkarzyPilkarzNarodowosc,
    u.UzytkownikDataUrodzenia   AS ListaPilkarzyPilkarzDataUrodzenia,
    d.DruzynaNazwa              AS ListaPilkarzyDruzynaNazwa,
    d.DruzynaID                 AS ListaPilkarzyDruzynaID,
    dwl.LigaSezonID             AS ListaPilkarzyLigaSezonID,
    u.UzytkownikSzukaKlubu      AS ListaPilkarzySzukaKlubu, --nowe
    COALESCE(s.SezonCzyAktywny, FALSE) AS ListaPilkarzyCzySezonAktywny --nowe
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
LEFT JOIN PrzynaleznosciPilkarzy pp ON u.UzytkownikID = pp.PilkarzID
LEFT JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
LEFT JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
LEFT JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID --nowe
LEFT JOIN Sezony s ON ls.SezonID = s.SezonID --nowe
WHERE r.RolaID = 4;

CREATE OR REPLACE VIEW WidokTrenerow AS
SELECT 
    u.UzytkownikID              AS ListaTrenerowTrenerID,
    u.UzytkownikImie            AS ListaTrenerowTrenerImie, 
    u.UzytkownikNazwisko        AS ListaTrenerowTrenerNazwisko,
    u.UzytkownikNarodowosc      AS ListaTrenerowTrenerNarodowosc,
    u.UzytkownikDataUrodzenia   AS ListaTrenerowTrenerDataUrodzenia,
    d.DruzynaNazwa              AS ListaTrenerowDruzynaNazwa,
    d.DruzynaID                 AS ListaTrenerowDruzynaID,
    dwl.LigaSezonID             AS ListaTrenerowLigaSezonID
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
LEFT JOIN PrzynaleznosciTrenerow pt ON u.UzytkownikID = pt.TrenerID
LEFT JOIN DruzynyWLidze dwl ON pt.DruzynaWLidzeID = dwl.DruzynaWLidzeID
LEFT JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
WHERE r.RolaNazwa = 'Trener';

CREATE OR REPLACE VIEW WidokKlubowWLidze AS
SELECT 
    ls.LigaSezonID              AS KlubWLidzeLigaSezonID,
    l.LigaNazwa                 AS KlubWLidzeLigaNazwa,
    s.SezonRok                  AS KlubWLidzeSezonRok,
    dwl.DruzynaWLidzeID         AS KlubWLidzeID,
    d.DruzynaNazwa              AS KlubWLidzeDruzynaNazwa,
    d.DruzynaSkrot              AS KlubWLidzeDruzynaSkrot,
    d.DruzynaStadion            AS KlubWLidzeDruzynaStadion,
    d.DruzynaAdres              AS KlubWLidzeDruzynaAdres,
    d.DruzynaDataZalozenia      AS KlubWLidzeDruzynaDataZalozenia
FROM DruzynyWLidze dwl
JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
JOIN Ligi l ON ls.LigaID = l.LigaID
JOIN Sezony s ON ls.SezonID = s.SezonID;

CREATE OR REPLACE VIEW WidokSedziow AS
SELECT 
    u.UzytkownikID          AS ListaSedziowSedziaID,
    u.UzytkownikImie        AS ListaSedziowSedziaImie,
    u.UzytkownikNazwisko    AS ListaSedziowSedziaNazwisko,
    u.UzytkownikNarodowosc  AS ListaSedziowSedziaNarodowosc,
    COUNT(m.MeczID)         AS ListaSedziowSedziaLiczbaMeczow
FROM Uzytkownicy u
JOIN Role r ON u.RolaID = r.RolaID
LEFT JOIN Mecze m ON u.UzytkownikID = m.MeczSedziaID
WHERE r.RolaNazwa = 'Sedzia'
GROUP BY u.UzytkownikID, u.UzytkownikImie, u.UzytkownikNazwisko, u.UzytkownikNarodowosc;

CREATE OR REPLACE VIEW WidokLig AS
SELECT
    ls.LigaSezonID          AS WidokLigLigaSezonID,
    ls.SezonID              AS WidokLigSezonID,
    ls.LigaID               AS WidokLigLigaId,
    l.LigaNazwa             AS WidokLigLigaNazwa,
    l.LigaSkrot             AS WidokLigLigaSkrot,
    sez.SezonRok            AS WidokLigSezonRok,
    sez.SezonDataPoczatku   AS WidokLigSezonDataPoczatku,
    sez.SezonDataKonca      AS WidokLigSezonDataKonca,
    sez.SezonCzyAktywny     AS WidokLigSezonCzyAktywny
FROM LigaSezon ls
JOIN Ligi l ON ls.LigaID = l.LigaID
JOIN Sezony sez ON ls.sezonID = sez.SezonID;

CREATE OR REPLACE VIEW WidokWnioskowOZawieszenie AS
SELECT
    w.WniosekOZawieszenieID     AS WniosekID,
    w.WniosekOZawieszenieStatus AS Status,
    s.UzytkownikImie            AS SedziaImie,
    s.UzytkownikNazwisko        AS SedziaNazwisko,
    p.UzytkownikImie            AS PilkarzImie,
    p.UzytkownikNazwisko        AS PilkarzNazwisko,
    d.DruzynaNazwa              AS DruzynaNazwa,
    m.MeczData                  AS DataIncydentu,
    h_d.DruzynaNazwa            AS GospodarzNazwa,
    a_d.DruzynaNazwa            AS GoscNazwa,
    w.PilkarzID                 AS KontraktID,
    w.MeczZawieszeniaID         AS MeczZawieszeniaID
FROM WnioskiOZawieszenie w
         JOIN Uzytkownicy s ON w.SedziaID = s.UzytkownikID
         JOIN PrzynaleznosciPilkarzy pp ON w.PilkarzID = pp.PrzynaleznoscPilkarzaID
         JOIN Uzytkownicy p ON pp.PilkarzID = p.UzytkownikID
         JOIN DruzynyWLidze dwl ON pp.DruzynaWLidzeID = dwl.DruzynaWLidzeID
         JOIN Druzyny d ON dwl.DruzynaID = d.DruzynaID
         JOIN StatystykiMeczu sm ON w.StatystykaMeczuID = sm.StatystykaMeczuID
         JOIN Mecze m ON sm.MeczID = m.MeczID
         JOIN DruzynyWLidze h_dwl ON m.MeczGospodarzID = h_dwl.DruzynaWLidzeID
         JOIN Druzyny h_d ON h_dwl.DruzynaID = h_d.DruzynaID
         JOIN DruzynyWLidze a_dwl ON m.MeczGoscID = a_dwl.DruzynaWLidzeID
         JOIN Druzyny a_d ON a_dwl.DruzynaID = a_d.DruzynaID;


-- FUNKCJA KTÓRA UNIEMOŻLIWIA DWUKROTNE WSTAWIENIE DRUZYNY
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


-- FUNKCJA KTORA BLOKUJE DODANIE PILKARZA DO DWOCH KLUBOW
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
    -- 2. Sprawdźmy, czy ten piłkarz (PilkarzID) jest już w tabeli przynależności piłkarza
    --    w jakiejkolwiek drużynie powiązanej z tym samymsezonem
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

CREATE OR REPLACE FUNCTION sprawdz_multiklubowosc_trenera()
RETURNS TRIGGER AS $$
DECLARE
v_TargetSezonID INT;
    v_Count INT;
BEGIN
    -- 1. Ustalmy sezon docelowej drużyny
SELECT ls.SezonID INTO v_TargetSezonID
FROM DruzynyWLidze dwl
         JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
WHERE dwl.DruzynaWLidzeID = NEW.DruzynaWLidzeID;

-- 2. Sprawdźmy, czy ten trener ma już klub w tym samym sezonie
SELECT COUNT(*) INTO v_Count
FROM PrzynaleznosciTrenerow pt
         JOIN DruzynyWLidze dwl ON pt.DruzynaWLidzeID = dwl.DruzynaWLidzeID
         JOIN LigaSezon ls ON dwl.LigaSezonID = ls.LigaSezonID
WHERE pt.TrenerID = NEW.TrenerID
  AND ls.SezonID = v_TargetSezonID;

-- 3. Blokada
IF v_Count > 0 THEN
        RAISE EXCEPTION 'Ten trener prowadzi już inną drużynę w tym sezonie!';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_jeden_klub_trenera_na_sezon
    BEFORE INSERT ON PrzynaleznosciTrenerow
    FOR EACH ROW
    EXECUTE FUNCTION sprawdz_multiklubowosc_trenera();

-- AKTUALIZACJA TABELI PO MECZU
CREATE OR REPLACE FUNCTION aktualizuj_statystyki_po_meczu()
RETURNS TRIGGER AS $$
BEGIN
    -- Gdy z "Nierozegrany" na "rozegrany"
    IF NEW.MeczCzyZagrany = TRUE AND OLD.MeczCzyZagrany = FALSE THEN
        -- Aktualizacja Tabeli Gospodarz
        UPDATE DruzynyWLidze
        SET 
            DruzynaWLidzeMeczeZagrane = DruzynaWLidzeMeczeZagrane + 1,
            DruzynaWLidzeGoleZdobyte = DruzynaWLidzeGoleZdobyte + NEW.MeczWynikGospodarz,
            DruzynaWLidzeGoleStracone = DruzynaWLidzeGoleStracone + NEW.MeczWynikGosc,
            -- Punkty
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
        -- Aktualizacja Tabeli Goscia
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
-- Trigger
CREATE TRIGGER trg_po_wpisaniu_wyniku
AFTER UPDATE ON Mecze
FOR EACH ROW
EXECUTE FUNCTION aktualizuj_statystyki_po_meczu();