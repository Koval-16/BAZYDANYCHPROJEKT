package pl.pwr.football.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Entity
@Table(name = "WidokMeczy")
@Immutable
public class MatchView {

    @Id
    @Column(name = "WidokMeczyMeczID")
    private Integer id;

    @Column(name = "WidokMeczyLigaSezonID")
    private Integer leagueSeasonId;

    @Column(name = "WidokMeczyData")
    private LocalDate date;

    @Column(name = "WidokMeczyCzyZagrany")
    private Boolean isPlayed;

    @Column(name = "WidokMeczyGospodarzNazwa")
    private String hostName;

    @Column(name = "WidokMeczyGospodarzID")
    private Integer hostId;

    @Column(name = "WidokMeczyWynikGospodarz")
    private Integer hostResult;

    @Column(name = "WidokMeczyWynikGosc")
    private Integer awayResult;

    @Column(name = "WidokMeczyGoscNazwa")
    private String awayName;

    @Column(name = "WidokMeczyGoscID")
    private Integer awayId;

    @Column(name = "WidokMeczySedziaNazwa")
    private String refereeName;

    @Column(name = "WidokMeczySedziaID")
    private Integer refereeId;

    public MatchView() {}

    public Integer getId() { return id; }
    public Integer getLeagueSeasonId() { return leagueSeasonId; }
    public LocalDate getDate() { return date; } // This fixes your error!
    public Boolean getIsPlayed() { return isPlayed; }

    public String getHostName() { return hostName; }
    public Integer getHostId() { return hostId; }
    public Integer getHostResult() { return hostResult; }
    public Integer getAwayResult() { return awayResult; }
    public String getAwayName() { return awayName; }
    public Integer getAwayId() { return awayId; }
    public String getRefereeName() { return refereeName; }
    public Integer getRefereeId() { return refereeId; }
}