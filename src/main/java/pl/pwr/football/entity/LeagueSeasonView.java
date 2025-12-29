package pl.pwr.football.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Immutable
@Table(name = "WidokLig")
public class LeagueSeasonView {

    @Id
    @Column(name = "WidokLigLigaSezonID")
    private Integer id;

    @Column(name = "WidokLigSezonID")
    private Integer seasonId;

    @Column(name = "WidokLigSezonRok")
    private Integer seasonYear;

    @Column(name = "WidokLigSezonDataPoczatku")
    private LocalDate seasonDateStart;

    @Column(name = "WidokLigSezonDataKonca")
    private LocalDate seasonDateEnd;

    @Column(name = "WidokLigSezonCzyAktywny")
    private boolean seasonIsActive;

    @Column(name = "WidokLigLigaID")
    private Integer leagueId;

    @Column(name = "WidokLigLigaNazwa")
    private String leagueName;

    @Column(name = "WidokLigLigaSkrot")
    private String leagueAbbreviation;

    public LeagueSeasonView() {}

    public Integer getId() {
        return id;
    }
    public Integer getLeagueId() {
        return leagueId;
    }
    public Integer getSeasonId() {
        return seasonId;
    }
    public Integer getSeasonYear() {
        return seasonYear;
    }
    public LocalDate getSeasonDateEnd() {
        return seasonDateEnd;
    }
    public LocalDate getSeasonDateStart() {
        return seasonDateStart;
    }
    public String getLeagueName() {
        return leagueName;
    }
    public String getLeagueAbbreviation() {
        return leagueAbbreviation;
    }
    public boolean getSeasonIsActive(){return seasonIsActive;}
}
