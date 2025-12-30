package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Ligi")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LigaID")
    private Integer id;

    @Column(name = "LigaNazwa")
    private String name;

    @Column(name = "LigaSkrot")
    private String abbreviation;

    public League(){
    }

    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getAbbreviation() {return abbreviation;}

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
