package pl.pwr.football.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueCreateDto {

    @NotBlank(message = "Nazwa ligi jest wymagana")
    @Size(min = 3, max = 100, message = "Nazwa musi mieć od 3 do 100 znaków")
    private String name;

    @NotBlank(message = "Skrót jest wymagany")
    @Size(min = 2, max = 10, message = "Skrót musi mieć od 2 do 10 znaków")
    private String abbreviation;

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setName(String name) {
        this.name = name;
    }
}