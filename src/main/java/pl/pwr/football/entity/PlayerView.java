package pl.pwr.football.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Immutable // To jest widok, tylko do odczytu
@Table(name = "WidokListyPilkarzy")
public class PlayerView {

    @Id
    @Column(name = "ListaPilkarzyPilkarzID")
    private Integer id;

    @Column(name = "ListaPilkarzyPilkarzImie")
    private String imie;

    @Column(name = "ListaPilkarzyPilkarzNazwisko")
    private String nazwisko;

    @Column(name = "ListaPilkarzyPilkarzNarodowosc")
    private String narodowosc;

    @Column(name = "ListaPilkarzyPilkarzDataUrodzenia")
    private LocalDate dataUrodzenia;

    @Column(name = "ListaPilkarzyDruzynaNazwa")
    private String druzynaNazwa; // Mamy to gotowe z widoku!

    @Column(name = "ListaPilkarzyDruzynaID")
    private Integer druzynaID;

    // Konstruktor
    public PlayerView() {}

    // --- GETTERY ---
    public Integer getId() { return id; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public String getNarodowosc() { return narodowosc; }
    public String getDruzynaNazwa() { return druzynaNazwa; }
    public Integer getDruzynaID() {return druzynaID;}

    // --- LOGIKA BIZNESOWA W WIDOKU ---
    // Obliczamy wiek na podstawie daty urodzenia z bazy
    public int getWiek() {
        if (dataUrodzenia == null) return 0;
        return Period.between(dataUrodzenia, LocalDate.now()).getYears();
    }
}
