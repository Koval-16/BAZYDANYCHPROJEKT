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
    private Integer ligaSezonId;

    @Column(name = "WidokMeczyData")
    private LocalDate data;

    @Column(name = "WidokMeczyCzyZagrany")
    private Boolean czyZagrany;

    @Column(name = "WidokMeczyGospodarzNazwa")
    private String gospodarzNazwa;

    @Column(name = "WidokMeczyGospodarzID")
    private Integer gospodarzId;

    @Column(name = "WidokMeczyWynikGospodarz")
    private Integer wynikGospodarz;

    @Column(name = "WidokMeczyWynikGosc")
    private Integer wynikGosc;

    @Column(name = "WidokMeczyGoscNazwa")
    private String goscNazwa;

    @Column(name = "WidokMeczyGoscID")
    private Integer goscId;

    @Column(name = "WidokMeczySedziaNazwa")
    private String sedziaImieNazwisko;

    @Column(name = "WidokMeczySedziaID")
    private Integer sedziaId;

    public MatchView() {}

    // --- MANUAL GETTERS (Crucial for Thymeleaf) ---
    public Integer getId() { return id; }
    public Integer getLigaSezonId() { return ligaSezonId; }
    public LocalDate getData() { return data; } // This fixes your error!
    public Boolean getCzyZagrany() { return czyZagrany; }
    public String getGospodarzNazwa() { return gospodarzNazwa; }
    public Integer getGospodarzId() { return gospodarzId; }
    public Integer getWynikGospodarz() { return wynikGospodarz; }
    public Integer getWynikGosc() { return wynikGosc; }
    public String getGoscNazwa() { return goscNazwa; }
    public Integer getGoscId() { return goscId; }
    public String getSedziaImieNazwisko() { return sedziaImieNazwisko; }
    public Integer getSedziaId() { return sedziaId; }
}