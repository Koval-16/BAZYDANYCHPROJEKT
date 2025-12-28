package pl.pwr.football.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate; // Jeśli potrzebujesz wieku, sędziowie nie mają daty w tym widoku w Twoim SQL? A nie, widzę w SQL że nie pobrałeś daty.
// EDIT: Patrzę na Twój SQL WidokSedziow: "u.UzytkownikNarodowosc, COUNT...".
// Brakuje daty urodzenia w SELECT w Twoim SQL WidokSedziow!
// Jeśli chcesz wiek sędziego, musisz dodać "u.UzytkownikDataUrodzenia" do widoku SQL.
// Założę, że poprawisz SQL albo pominiesz wiek dla sędziego.

@Entity
@Immutable
@Table(name = "WidokSedziow")
public class RefereeView {

    @Id
    @Column(name = "ListaSedziowSedziaID")
    private Integer id;

    @Column(name = "ListaSedziowSedziaImie")
    private String imie;

    @Column(name = "ListaSedziowSedziaNazwisko")
    private String nazwisko;

    @Column(name = "ListaSedziowSedziaNarodowosc")
    private String narodowosc;

    @Column(name = "ListaSedziowSedziaLiczbaMeczow")
    private Integer liczbaMeczy;

    public RefereeView() {}

    // --- GETTERY ---
    public Integer getId() { return id; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public String getNarodowosc() { return narodowosc; }
    public Integer getLiczbaMeczy() { return liczbaMeczy; }
}