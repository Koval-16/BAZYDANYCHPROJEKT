package pl.pwr.football.entity.entities;

import jakarta.persistence.*;
import pl.pwr.football.entity.entities.Role;

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
    private String password;

    @Column(name = "UzytkownikImie")
    private String name;

    @Column(name = "UzytkownikNazwisko")
    private String surname;

    @Column(name = "UzytkownikDataUrodzenia")
    private LocalDate birthDate;

    @Column(name = "UzytkownikNarodowosc")
    private String nationality;

    @Column(name = "UzytkownikAdres")
    private String address;

    @Column(name = "UzytkownikSzukaKlubu")
    private Boolean lookingForClub;

    public User() {
    }

    public Integer getId() { return id; }
    public Role getRole() { return role; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getNationality() { return nationality; }
    public String getAddress() { return address; }
    public Boolean getLookingForClub() { return lookingForClub; }

    public void setId(Integer id) { this.id = id; }
    public void setRole(Role role) { this.role = role; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setAddress(String address) { this.address = address; }
    public void setLookingForClub(Boolean lookingForClub) { this.lookingForClub = lookingForClub; }
}