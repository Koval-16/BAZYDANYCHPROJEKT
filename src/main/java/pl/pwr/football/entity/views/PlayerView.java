package pl.pwr.football.entity.views;

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
    private String name;

    @Column(name = "ListaPilkarzyPilkarzNazwisko")
    private String surname;

    @Column(name = "ListaPilkarzyPilkarzNarodowosc")
    private String nationality;

    @Column(name = "ListaPilkarzyPilkarzDataUrodzenia")
    private LocalDate birthDate;

    @Column(name = "ListaPilkarzyDruzynaNazwa")
    private String teamName;

    @Column(name = "ListaPilkarzyDruzynaID")
    private Integer teamId;

    @Column(name = "ListaPilkarzySzukaKlubu")
    private Boolean isLookingForClub;

    @Column(name = "ListaPilkarzyCzySezonAktywny")
    private Boolean isSeasonActive;

    public PlayerView() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getNationality() { return nationality; }
    public String getTeamName() { return teamName; }
    public Integer getTeamId() {return teamId;}

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Boolean getIsLookingForClub() {
        return isLookingForClub;
    }

    public Boolean getIsSeasonActive() {
        return isSeasonActive;
    }

    public boolean hasActiveClub() {
        return teamName != null && isSeasonActive != null && isSeasonActive;
    }
}
