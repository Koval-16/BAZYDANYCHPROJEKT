package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "Sezony")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SezonID")
    private Integer seasonId;

    @Getter
    @Column(name = "SezonRok")
    private Integer seasonYear;

    @Column(name = "SezonDataPoczatku")
    private LocalDate seasonStartDate;

    @Column(name = "SezonDataKonca")
    private LocalDate seasonEndDate;

    @Column(name = "SezonCzyAktywny")
    private boolean seasonIsActive;

    public Season() {
    }

    public Integer getSeasonId() {return seasonId;}
    public Integer getSeasonYear() {return seasonYear;}
    public LocalDate getSeasonStartDate() {return seasonStartDate;}
    public LocalDate getSeasonEndDate() {return seasonEndDate;}
    public boolean getSeasonIsActive() {return seasonIsActive;}

    public void setSeasonEndDate(LocalDate seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public void setSeasonIsActive(boolean seasonIsActive) {
        this.seasonIsActive = seasonIsActive;
    }

    public void setSeasonStartDate(LocalDate seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public void setSeasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
    }
}
