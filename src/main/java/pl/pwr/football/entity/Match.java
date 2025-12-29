package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Mecze")
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MeczID")
    private Integer id;

    @Column(name = "MeczLigaSezonID", nullable = false)
    private Integer leagueSeasonId;

    // Relacja do DRUŻYNY W LIDZE (nie do samej Drużyny)
    @Column(name = "MeczGospodarzID", nullable = false)
    private Integer hostId;

    @Column(name = "MeczGoscID", nullable = false)
    private Integer awayId;

    // Sędzia na razie null, bo F18 tego nie wymaga
    @Column(name = "MeczSedziaID")
    private Integer refereeId;

    @Column(name = "MeczData")
    private LocalDate date;

    @Column(name = "MeczDataProponowane")
    private LocalDate proposedDate;

    @Column(name = "MeczCzyZagrany")
    private boolean played = false; // Domyślnie false

    // Wyniki (Integer, bo mogą być nulle przed meczem)
    @Column(name = "MeczWynikGospodarz")
    private Integer homeScore;

    @Column(name = "MeczWynikGosc")
    private Integer awayScore;

    public Integer getLeagueSeasonId() {
        return leagueSeasonId;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getProposedDate() {
        return proposedDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAwayId() {
        return awayId;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setLeagueSeasonId(Integer leagueSeasonId) {
        this.leagueSeasonId = leagueSeasonId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }


    public void setPlayed(boolean played) {
        this.played = played;
    }

    public void setProposedDate(LocalDate proposedDate) {
        this.proposedDate = proposedDate;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }
}