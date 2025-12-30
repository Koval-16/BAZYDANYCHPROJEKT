package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "LigaSezon")
public class LeagueSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LigaSezonID")
    private Integer id;

    @Column(name = "SezonID", nullable = false)
    private Integer seasonId;

    @Column(name = "LigaID", nullable = false)
    private Integer leagueId;

    public Integer getLeagueId() {
        return leagueId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}