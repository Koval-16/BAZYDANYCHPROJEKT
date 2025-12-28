package pl.pwr.football.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Uzytkownicy")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UzytkownikID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RolaID", nullable = false)
    private Role role;

    @Column(name = "UzytkownikLogin", unique = true, nullable = false)
    private String login;

    @Column(name = "UzytkownikHaslo", nullable = false)
    private String haslo;

    @Column(name = "UzytkownikImie")
    private String imie;

    @Column(name = "UzytkownikNazwisko")
    private String nazwisko;

    @Column(name = "UzytkownikDataUrodzenia")
    private LocalDate dataUrodzenia;

    @Column(name = "UzytkownikNarodowosc")
    private String narodowosc;

    @Column(name = "UzytkownikAdres")
    private String adres;

    @Column(name = "UzytkownikSzukaKlubu")
    private Boolean szukaKlubu;

    // --- Pusty konstruktor (wymagany przez JPA) ---
    public User() {
    }

    // --- RĘCZNE GETTERY (Naprawa błędów) ---
    public Integer getId() { return id; }
    public Role getRole() { return role; }       // To naprawia error: method getRole()
    public String getLogin() { return login; }   // To naprawia error: method getLogin()
    public String getHaslo() { return haslo; }   // To naprawia error: method getHaslo()
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public LocalDate getDataUrodzenia() { return dataUrodzenia; }
    public String getNarodowosc() { return narodowosc; }
    public String getAdres() { return adres; }
    public Boolean getSzukaKlubu() { return szukaKlubu; }

    // --- RĘCZNE SETTERY (Potrzebne, żeby Spring mógł wpisać dane z bazy do obiektu) ---
    public void setId(Integer id) { this.id = id; }
    public void setRole(Role role) { this.role = role; }
    public void setLogin(String login) { this.login = login; }
    public void setHaslo(String haslo) { this.haslo = haslo; }
    public void setImie(String imie) { this.imie = imie; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }
    public void setDataUrodzenia(LocalDate dataUrodzenia) { this.dataUrodzenia = dataUrodzenia; }
    public void setNarodowosc(String narodowosc) { this.narodowosc = narodowosc; }
    public void setAdres(String adres) { this.adres = adres; }
    public void setSzukaKlubu(Boolean szukaKlubu) { this.szukaKlubu = szukaKlubu; }
}