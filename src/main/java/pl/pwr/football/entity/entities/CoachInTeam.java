package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "PrzynaleznosciTrenerow")
public class CoachInTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrzynaleznoscTreneraID")
    private Integer id;

    @Column(name = "TrenerID", nullable = false)
    private Integer coachId;

    @Column(name = "DruzynaWLidzeID", nullable = false)
    private Integer teamInLeagueId;

    public CoachInTeam() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCoachId() { return coachId; }
    public void setCoachId(Integer coachId) { this.coachId = coachId; }
    public Integer getTeamInLeagueId() { return teamInLeagueId; }
    public void setTeamInLeagueId(Integer teamInLeagueId) { this.teamInLeagueId = teamInLeagueId; }
}