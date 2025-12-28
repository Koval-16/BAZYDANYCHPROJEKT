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
    private Integer sezonID;

    @Getter
    @Column(name = "SezonRok")
    private Integer sezonRok;

    @Column(name = "SezonDataPoczatku")
    private LocalDate sezonDataPoczatku;

    @Column(name = "SezonDataKonca")
    private LocalDate sezonDataKonca;

    @Column(name = "SezonCzyAktywny")
    private boolean SezonCzyAktywny;

    public Season() {
    }

    public Integer getSezonID() {return sezonID;}
    public Integer getSezonRok() {return sezonRok;}
    public LocalDate getSezonDataPoczatku() {return sezonDataPoczatku;}
    public LocalDate getSezonDataKonca() {return sezonDataKonca;}
    public boolean getSezonCzyAktywny() {return SezonCzyAktywny;}
}
