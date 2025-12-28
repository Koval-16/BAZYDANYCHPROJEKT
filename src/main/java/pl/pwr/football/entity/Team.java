package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "Druzyny")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DruzynaID")
    private Integer id;

    @Getter
    @Column(name = "DruzynaNazwa")
    private String name;

    @Column(name = "DruzynaSkrot")
    private String abbreviation;

    @Column(name = "DruzynaDataZalozenia")
    private LocalDate foundation_date;

    @Column(name = "DruzynaStrojeDom")
    private String kits_home;

    @Column(name = "DruzynaStrojeWyjazd")
    private String kits_away;

    @Column(name = "DruzynaStadion")
    private String stadium;

    @Column(name = "DruzynaAdres")
    private String address;

    public Team() {
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getAbbreviation() { return abbreviation; }
    public LocalDate getFoundationDate() { return foundation_date; }
    public String getKitsHome() { return kits_home; }
    public String getKitsAway() { return kits_away; }
    public String getStadium() { return stadium; }
    public String getAddress() { return address; }
}
