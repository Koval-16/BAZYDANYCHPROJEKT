package pl.pwr.football.entity.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "WidokTabeliLigowej")
public class TableView {

    @Id
    @Column(name = "TabelaDruzynaID")
    private Integer id;

    @Column(name = "TabelaLigaSezonID")
    private Integer leagueSeasonId;

    @Column(name = "TabelaDruzynaNazwa")
    private String teamName;

    @Column(name = "TabeleMeczeZagrane")
    private Integer matchesPlayed;

    @Column(name = "TabelaPunkty")
    private Integer points;

    @Column(name = "TabelaWygrane")
    private Integer wins;

    @Column(name = "TabelaRemisy")
    private Integer draws;

    @Column(name = "TabelaPorazki")
    private Integer defeats;

    @Column(name = "TabelaGoleZdobyte")
    private Integer goalsScored;

    @Column(name = "TabelaGoleStracone")
    private Integer goalsConceded;

    @Column(name = "TabelaGoleRoznica")
    private Integer goalDifference;

    public TableView(){}

    public Integer getId() {return id;}
    public Integer getLeagueSeasonId() {return leagueSeasonId;}
    public String getTeamName() {return teamName;}
    public Integer getWins() {return wins;}
    public Integer getDraws() {return draws;}
    public Integer getDefeats() {return defeats;}
    public Integer getPoints() {return points;}
    public Integer getMatchesPlayed() {return matchesPlayed;}
    public Integer getGoalsScored() {return goalsScored;}
    public Integer getGoalsConceded() {return goalsConceded;}
    public Integer getGoalDifference() {return goalDifference;}
}
