package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Sezony")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SezonID")
    private Integer id;

    @Column(name = "SezonRok")
    private Integer year;

    @Column(name = "SezonDataPoczatku")
    private LocalDate startDate;

    @Column(name = "SezonDataKonca")
    private LocalDate endDate;

    @Column(name = "SezonCzyAktywny")
    private boolean active;

    public Season() {
    }

    public Integer getId() {return id;}
    public Integer getYear() {return year;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public boolean isActive() {return active;}


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
