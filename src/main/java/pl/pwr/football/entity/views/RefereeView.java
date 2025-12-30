package pl.pwr.football.entity.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "WidokSedziow")
public class RefereeView {

    @Id
    @Column(name = "ListaSedziowSedziaID")
    private Integer id;

    @Column(name = "ListaSedziowSedziaImie")
    private String name;

    @Column(name = "ListaSedziowSedziaNazwisko")
    private String surname;

    @Column(name = "ListaSedziowSedziaNarodowosc")
    private String nationality;

    @Column(name = "ListaSedziowSedziaLiczbaMeczow")
    private Integer matchCount;

    public RefereeView() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getNationality() { return nationality; }
    public Integer getMatchCount() { return matchCount; }
}