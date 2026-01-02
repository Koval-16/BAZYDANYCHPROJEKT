package pl.pwr.football.entity.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "WidokWnioskowOZawieszenie")
public class SuspensionRequestView {

    @Id
    @Column(name = "WniosekID")
    private Integer id;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "SedziaImie")
    private String refereeName;

    @Column(name = "SedziaNazwisko")
    private String refereeSurname;

    @Column(name = "PilkarzImie")
    private String playerName;

    @Column(name = "PilkarzNazwisko")
    private String playerSurname;

    @Column(name = "DruzynaNazwa")
    private String teamName;

    @Column(name = "DataIncydentu")
    private LocalDate incidentDate;

    @Column(name = "GospodarzNazwa")
    private String hostName;

    @Column(name = "GoscNazwa")
    private String awayName;

    @Column(name = "KontraktID")
    private Integer contractId;

    public SuspensionRequestView() {}

    // Gettery
    public Integer getId() { return id; }
    public Integer getStatus() { return status; }
    public String getRefereeFullName() { return refereeName + " " + refereeSurname; }
    public String getPlayerFullName() { return playerName + " " + playerSurname; }
    public String getTeamName() { return teamName; }
    public Integer getContractId() { return contractId; }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAwayName() {
        return awayName;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public String getPlayerSurname() {
        return playerSurname;
    }

    public String getRefereeSurname() {
        return refereeSurname;
    }

    public String getMatchInfo() {
        return hostName + " vs " + awayName + " (" + incidentDate + ")";
    }
}