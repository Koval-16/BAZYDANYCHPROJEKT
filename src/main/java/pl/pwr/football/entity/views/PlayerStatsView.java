package pl.pwr.football.entity.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "WidokStatystykPilkarzy")
public class PlayerStatsView {

    @Id
    @Column(name = "StatystykiPilkarzID")
    private Integer id;

    @Column(name = "StatystykiLigaSezonID")
    private Integer leagueSeasonId;

    @Column(name = "StatystykiDruzynaID")
    private Integer teamId;

    @Column(name = "StatystykiDruzynaNazwa")
    private String teamName;

    @Column(name = "StatystykiPilkarzImie")
    private String name;

    @Column(name = "StatystykiPilkarzNazwisko")
    private String surname;

    @Column(name = "StatystykiPilkarzGole")
    private Integer goals;

    @Column(name = "StatystykiPilkarzZolteKartki")
    private Integer yellowCards;

    @Column(name = "StatystykiPilkarzCzerwoneKartki")
    private Integer redCards;

    public PlayerStatsView() {}

    public Integer getId() { return id; }
    public Integer getLeagueSeasonId() { return leagueSeasonId; }
    public Integer getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Integer getGoals() { return goals; }
    public Integer getYellowCards() { return yellowCards; }
    public Integer getRedCards() { return redCards; }
}