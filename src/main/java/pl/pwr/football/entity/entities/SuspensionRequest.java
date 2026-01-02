package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "WnioskiOZawieszenie")
public class SuspensionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WniosekOZawieszenieID")
    private Integer id;

    @Column(name = "SedziaID", nullable = false)
    private Integer refereeId;

    @Column(name = "PilkarzID", nullable = false)
    private Integer playerInTeamId;

    @Column(name = "StatystykaMeczuID", nullable = false)
    private Integer matchStatisticId;

    @Column(name = "WniosekOZawieszenieStatus")
    private Integer status = 0;

    @Column(name = "MeczZawieszeniaID")
    private Integer suspensionMatchId;

    public SuspensionRequest() {}

    public SuspensionRequest(Integer refereeId, Integer playerInTeamId, Integer matchStatisticId) {
        this.refereeId = refereeId;
        this.playerInTeamId = playerInTeamId;
        this.matchStatisticId = matchStatisticId;
        this.status = 0;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getRefereeId() { return refereeId; }
    public void setRefereeId(Integer refereeId) { this.refereeId = refereeId; }

    public Integer getPlayerInTeamId() { return playerInTeamId; }
    public void setPlayerInTeamId(Integer playerInTeamId) { this.playerInTeamId = playerInTeamId; }

    public Integer getMatchStatisticId() { return matchStatisticId; }
    public void setMatchStatisticId(Integer matchStatisticId) { this.matchStatisticId = matchStatisticId; }

    public Integer getStatus() {
        return status;
    }

    public Integer getSuspensionMatchId() {
        return suspensionMatchId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSuspensionMatchId(Integer suspensionMatchId) {
        this.suspensionMatchId = suspensionMatchId;
    }
}