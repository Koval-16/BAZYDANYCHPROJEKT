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
    private Integer sezonId;

    @Column(name = "WidokLigSezonRok")
    private Integer sezonRok;

    @Column(name = "WidokLigSezonDataPoczatku")
    private LocalDate sezonDataPoczatku;

    @Column(name = "WidokLigSezonDataKonca")
    private LocalDate sezonDataKonca;

    @Column(name = "WidokLigSezonCzyAktywny")
    private boolean sezonCzyAktywny;

    @Column(name = "WidokLigLigaID")
    private Integer ligaId;

    @Column(name = "WidokLigLigaNazwa")
    private String ligaNazwa;

    @Column(name = "WidokLigLigaSkrot")
    private String ligaSkrot;

    public LeagueSeasonView() {}

    public Integer getId() {
        return id;
    }
    public Integer getLigaId() {
        return ligaId;
    }
    public Integer getSezonId() {
        return sezonId;
    }
    public Integer getSezonRok() {
        return sezonRok;
    }
    public LocalDate getSezonDataKonca() {
        return sezonDataKonca;
    }
    public LocalDate getSezonDataPoczatku() {
        return sezonDataPoczatku;
    }
    public String getLigaNazwa() {
        return ligaNazwa;
    }
    public String getLigaSkrot() {
        return ligaSkrot;
    }
    public boolean getSezonCzyAktywny(){return sezonCzyAktywny;}
}
