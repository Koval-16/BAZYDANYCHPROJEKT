package pl.pwr.football.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class TeamAddedDto {

    @NotBlank(message = "Nazwa drużyny jest wymagana")
    @Size(min = 3, max = 100, message = "Nazwa musi mieć od 3 do 100 znaków")
    private String name;

    @NotBlank(message = "Skrót jest wymagany")
    @Size(min = 3, max = 4, message = "Skrót musi mieć 3 lub 4 znaki")
    private String abbreviation;

    @NotBlank(message = "Stroje domowe są wymagane")
    @Size(max = 30, message = "Zbyt długa nazwa stroju")
    private String kitsHome;

    @NotBlank(message = "Stroje wyjazdowe są wymagane")
    @Size(max = 30, message = "Zbyt długa nazwa stroju")
    private String kitsAway;

    @NotBlank(message = "Stadion jest wymagany")
    @Size(max = 64, message = "Nazwa stadionu zbyt długa")
    private String stadium;

    @NotBlank(message = "Adres jest wymagany")
    @Size(max = 64, message = "Adres zbyt długi")
    private String address;

    // Brak pola dataZalozenia - system ustawi je sam!


    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getAddress() {
        return address;
    }

    public String getKitsAway() {
        return kitsAway;
    }

    public String getKitsHome() {
        return kitsHome;
    }

    public String getStadium() {
        return stadium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public void setKitsAway(String kitsAway) {
        this.kitsAway = kitsAway;
    }

    public void setKitsHome(String kitsHome) {
        this.kitsHome = kitsHome;
    }
}