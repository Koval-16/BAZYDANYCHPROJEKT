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

    @Column(name = "WniosekOZawieszenieCzyRozpatrzony")
    private boolean reviewed = false;

    // np. 0=Odrzucony, 1=Zaakceptowany
    @Column(name = "WniosekOZawieszenieDecyzja")
    private Integer decision;

    @Column(name = "WniosekOZawieszenieDlugoscKary")
    private Integer penaltyLength;

    public SuspensionRequest() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getRefereeId() { return refereeId; }
    public void setRefereeId(Integer refereeId) { this.refereeId = refereeId; }

    public Integer getPlayerInTeamId() { return playerInTeamId; }
    public void setPlayerInTeamId(Integer playerInTeamId) { this.playerInTeamId = playerInTeamId; }

    public Integer getMatchStatisticId() { return matchStatisticId; }
    public void setMatchStatisticId(Integer matchStatisticId) { this.matchStatisticId = matchStatisticId; }

    public boolean isReviewed() { return reviewed; }
    public void setReviewed(boolean reviewed) { this.reviewed = reviewed; }

    public Integer getDecision() { return decision; }
    public void setDecision(Integer decision) { this.decision = decision; }

    public Integer getPenaltyLength() { return penaltyLength; }
    public void setPenaltyLength(Integer penaltyLength) { this.penaltyLength = penaltyLength; }
}