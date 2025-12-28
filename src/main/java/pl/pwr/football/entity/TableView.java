package pl.pwr.football.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "WidokTabeliLigowej")
public class TableView {

    @Id
    @Column(name = "TabelaDruzynaID")
    private Integer id;

    @Column(name = "TabelaLigaSezonID")
    private Integer ligaSezonID;

    @Column(name = "TabelaDruzynaNazwa")
    private String druzyna;

    @Column(name = "TabeleMeczeZagrane")
    private Integer meczeZagrane;

    @Column(name = "TabelaPunkty")
    private Integer punkty;

    @Column(name = "TabelaWygrane")
    private Integer wygrane;

    @Column(name = "TabelaRemisy")
    private Integer remisy;

    @Column(name = "TabelaPorazki")
    private Integer porazki;

    @Column(name = "TabelaGoleZdobyte")
    private Integer goleZdobyte;

    @Column(name = "TabelaGoleStracone")
    private Integer goleStracone;

    @Column(name = "TabelaGoleRoznica")
    private Integer goleRoznica;

    public TableView(){}

    public Integer getId() {return id;}
    public Integer getLigaSezonID() {return ligaSezonID;}
    public String getDruzyna() {return druzyna;}
    public Integer getWygrane() {return wygrane;}
    public Integer getRemisy() {return remisy;}
    public Integer getPorazki() {return porazki;}
    public Integer getPunkty() {return punkty;}
    public Integer getMecze_zagrane() {return meczeZagrane;}
    public Integer getGole_zdobyte() {return goleZdobyte;}
    public Integer getGole_stracone() {return goleStracone;}
    public Integer getGole_roznica() {return goleRoznica;}
}
