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
    private String name;

    @Column(name = "ListaSedziowSedziaNazwisko")
    private String surname;

    @Column(name = "ListaSedziowSedziaNarodowosc")
    private String nationality;

    @Column(name = "ListaSedziowSedziaLiczbaMeczow")
    private Integer matchCount;

    public RefereeView() {}

    // --- GETTERY ---
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getNationality() { return nationality; }
    public Integer getMatchCount() { return matchCount; }
}