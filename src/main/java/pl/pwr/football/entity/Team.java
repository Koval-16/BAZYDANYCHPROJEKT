package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Druzyny")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DruzynaID")
    private Integer id;

    @Setter
    @Column(name = "DruzynaNazwa")
    private String name;

    @Setter
    @Column(name = "DruzynaSkrot")
    private String abbreviation;

    @Setter
    @Column(name = "DruzynaDataZalozenia")
    private LocalDate foundationDate;

    @Setter
    @Column(name = "DruzynaStrojeDom")
    private String kitsHome;

    @Setter
    @Column(name = "DruzynaStrojeWyjazd")
    private String kitsAway;

    @Setter
    @Column(name = "DruzynaStadion")
    private String stadium;

    @Setter
    @Column(name = "DruzynaAdres")
    private String address;

    public Team() {
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getAbbreviation() { return abbreviation; }
    public LocalDate getFoundationDate() { return foundationDate; }
    public String getKitsHome() { return kitsHome; }
    public String getKitsAway() { return kitsAway; }
    public String getStadium() { return stadium; }
    public String getAddress() { return address; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public void setKitsAway(String kitsAway) {
        this.kitsAway = kitsAway;
    }

    public void setKitsHome(String kitsHome) {
        this.kitsHome = kitsHome;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
}
