package pl.pwr.football.dto;

import java.time.LocalDate;
import java.time.Period;

public class UserDto {
    private String name;
    private String surname;
    private String nationality;
    private int age;

    // Konstruktor, który przyjmuje surowe dane i oblicza wiek
    public UserDto(String imie, String nazwisko, String narodowosc, LocalDate dataUrodzenia) {
        this.name = imie;
        this.surname = nazwisko;
        this.nationality = narodowosc;

        // Obliczanie wieku
        if (dataUrodzenia != null) {
            this.age = Period.between(dataUrodzenia, LocalDate.now()).getYears();
        } else {
            this.age = 0;
        }
    }

    // Gettery (wymagane przez Thymeleaf)
    public String getImie() { return name; }
    public String getNazwisko() { return surname; }
    public String getNarodowosc() { return nationality; }
    public int getWiek() { return age; }
}