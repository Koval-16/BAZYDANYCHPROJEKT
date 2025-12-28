package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Ligi")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LigaID")
    private Integer ligaID;

    @Column(name = "LigaNazwa")
    private String ligaNazwa;

    @Column(name = "LigaSkrot")
    private String LigaSkrot;

    public League(){
    }

    public Integer getLigaID() {return ligaID;}
    public String getLigaNazwa() {return ligaNazwa;}
    public String getLigaSkrot() {return LigaSkrot;}
}
