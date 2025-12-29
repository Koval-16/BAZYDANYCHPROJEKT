package pl.pwr.football.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Ligi")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LigaID")
    private Integer leagueID;

    @Column(name = "LigaNazwa")
    private String leagueName;

    @Column(name = "LigaSkrot")
    private String leagueAbbreviation;

    public League(){
    }

    public Integer getLeagueID() {return leagueID;}
    public String getLeagueName() {return leagueName;}
    public String getLeagueAbbreviation() {return leagueAbbreviation;}
}
