package pl.pwr.football.entity.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Immutable
@Table(name = "WidokTrenerow")
public class CoachView {

    @Id
    @Column(name = "ListaTrenerowTrenerID")
    private Integer id;

    @Column(name = "ListaTrenerowTrenerImie")
    private String name;

    @Column(name = "ListaTrenerowTrenerNazwisko")
    private String surname;

    @Column(name = "ListaTrenerowTrenerNarodowosc")
    private String nationality;

    @Column(name = "ListaTrenerowTrenerDataUrodzenia")
    private LocalDate birthDate;

    @Column(name = "ListaTrenerowDruzynaNazwa")
    private String teamName;

    @Column(name = "ListaTrenerowDruzynaID")
    private Integer teamId;

    @Column(name = "ListaTrenerowLigaSezonID")
    private Integer leagueSeasonId;

    public CoachView() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getNationality() { return nationality; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getTeamName() { return teamName; }
    public Integer getTeamId() { return teamId; }
    public Integer getLeagueSeasonId() { return leagueSeasonId; }

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}