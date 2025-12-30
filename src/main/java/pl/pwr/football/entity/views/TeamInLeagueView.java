package pl.pwr.football.entity.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "WidokKlubowWLidze")
public class TeamInLeagueView {

    @Id
    @Column(name = "KlubWLidzeID")
    private Integer id;

    @Column(name = "KlubWLidzeLigaSezonID")
    private Integer leagueSeasonId;

    @Column(name = "KlubWLidzeLigaNazwa")
    private String leagueName;

    @Column(name = "KlubWLidzeSezonRok")
    private Integer seasonYear;

    @Column(name = "KlubWLidzeDruzynaNazwa")
    private String teamName;

    @Column(name = "KlubWLidzeDruzynaSkrot")
    private String teamAbbreviation;

    @Column(name = "KlubWLidzeDruzynaStadion")
    private String stadium;

    @Column(name = "KlubWLidzeDruzynaAdres")
    private String address;

    @Column(name = "KlubWLidzeDruzynaDataZalozenia")
    private LocalDate foundationDate;

    public TeamInLeagueView() {}

    public Integer getId() { return id; }
    public Integer getLeagueSeasonId() { return leagueSeasonId; }
    public String getLeagueName() { return leagueName; }
    public Integer getSeasonYear() { return seasonYear; }
    public String getTeamName() { return teamName; }
    public String getTeamAbbreviation() { return teamAbbreviation; }
    public String getStadium() { return stadium; }
    public String getAddress() { return address; }
    public LocalDate getFoundationDate() { return foundationDate; }
}