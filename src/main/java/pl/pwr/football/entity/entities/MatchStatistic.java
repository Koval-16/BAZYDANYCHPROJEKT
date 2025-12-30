package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "StatystykiMeczu")
public class MatchStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatystykaMeczuID")
    private Integer id;

    @Column(name = "MeczID", nullable = false)
    private Integer matchId;

    @Column(name = "PilkarzID", nullable = false)
    private Integer playerInTeamId;

    @Column(name = "StatystykaMeczuRodzaj", nullable = false)
    private Integer type;

    public MatchStatistic() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMatchId() { return matchId; }
    public void setMatchId(Integer matchId) { this.matchId = matchId; }

    public Integer getPlayerInTeamId() { return playerInTeamId; }
    public void setPlayerInTeamId(Integer playerInTeamId) { this.playerInTeamId = playerInTeamId; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
}