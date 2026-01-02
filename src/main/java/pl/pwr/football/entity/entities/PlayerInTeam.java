package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "PrzynaleznosciPilkarzy")
public class PlayerInTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrzynaleznoscPilkarzaID")
    private Integer id;

    @Column(name = "PilkarzID", nullable = false)
    private Integer playerId;

    @Column(name = "DruzynaWLidzeID", nullable = false)
    private Integer teamInLeagueId;

    @Column(name = "PilkarzLiczbaGoli")
    private int goals = 0;

    @Column(name = "PilkarzZolteKartki")
    private int yellowCards = 0;

    @Column(name = "PilkarzCzerwoneKartki")
    private int redCards = 0;

    @Column(name = "PilkarzCzyZawieszony")
    private boolean suspended = false;

    @Column(name = "PilkarzPotwierdzony")
    private boolean confirmed = false;

    @Column(name = "PilkarzAktywny")
    private boolean active = false;

    public PlayerInTeam() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public Integer getTeamInLeagueId() { return teamInLeagueId; }
    public void setTeamInLeagueId(Integer teamInLeagueId) { this.teamInLeagueId = teamInLeagueId; }

    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }

    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }

    public int getRedCards() { return redCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }

    public boolean isSuspended() { return suspended; }
    public void setSuspended(boolean suspended) { this.suspended = suspended; }

    public boolean isConfirmed() {
        return confirmed;
    }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}