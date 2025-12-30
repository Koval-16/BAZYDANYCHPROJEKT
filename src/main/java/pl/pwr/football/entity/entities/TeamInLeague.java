package pl.pwr.football.entity.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DruzynyWLidze")
@Getter
@Setter
public class TeamInLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DruzynaWLidzeID")
    private Integer id;

    @Column(name = "LigaSezonID", nullable = false)
    private Integer leagueSeasonId;

    @Column(name = "DruzynaID", nullable = false)
    private Integer teamId;

    @Column(name = "DruzynaWLidzeMeczeZagrane")
    private int matchesPlayed = 0;

    @Column(name = "DruzynaWLidzePunkty")
    private int points = 0;

    @Column(name = "DruzynaWLidzeWygrane")
    private int wins = 0;

    @Column(name = "DruzynaWLidzeRemisy")
    private int draws = 0;

    @Column(name = "DruzynaWLidzePorazki")
    private int defeats = 0;

    @Column(name = "DruzynaWLidzeGoleZdobyte")
    private int goalsScored = 0;

    @Column(name = "DruzynaWLidzeGoleStracone")
    private int goalsConceded = 0;

    public Integer getId() {
        return id;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getDefeats() {
        return defeats;
    }

    public Integer getLeagueSeasonId() {
        return leagueSeasonId;
    }

    public int getPoints() {
        return points;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setLeagueSeasonId(Integer leagueSeasonId) {
        this.leagueSeasonId = leagueSeasonId;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}