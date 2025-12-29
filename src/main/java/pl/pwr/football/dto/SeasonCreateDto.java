package pl.pwr.football.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class SeasonCreateDto {

    @NotNull(message = "Rok sezonu jest wymagany")
    private Integer year; // SezonRok

    @NotNull(message = "Data początku jest wymagana")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // SezonDataPoczatku

    @NotNull(message = "Data końca jest wymagana")
    @Future(message = "Data końca musi być w przyszłości")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // SezonDataKonca

    public Integer getYear() {
        return year;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}