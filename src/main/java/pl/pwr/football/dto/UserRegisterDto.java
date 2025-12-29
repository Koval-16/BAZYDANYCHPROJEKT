package pl.pwr.football.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserRegisterDto {

    @NotBlank(message = "Rola jest wymagana")
    private String roleName; // Np. "TRENER", "SEDZIA", "PILKARZ"

    @NotBlank(message = "Login jest wymagany")
    @Size(min = 3, max = 50, message = "Login musi mieć od 3 do 50 znaków")
    private String login;

    @NotBlank(message = "Hasło jest wymagane")
    @Size(min = 4, message = "Hasło musi mieć minimum 4 znaki")
    private String password;

    @NotBlank(message = "Imię jest wymagane")
    private String name;

    @NotBlank(message = "Nazwisko jest wymagane")
    private String surname;

    @NotNull(message = "Data urodzenia jest wymagana")
    @Past(message = "Data urodzenia musi być z przeszłości")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "Narodowość jest wymagana")
    private String nationality;

    @NotBlank(message = "Adres jest wymagany")
    private String address;

    // --- RĘCZNE GETTERY I SETTERY (Bezpiecznik) ---

    public String getRoleName() { return roleName; }
    public void setRoleName(String rolaNazwa) { this.roleName = rolaNazwa; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String haslo) { this.password = haslo; }

    public String getName() { return name; }
    public void setName(String imie) { this.name = imie; }

    public String getSurname() { return surname; }
    public void setSurname(String nazwisko) { this.surname = nazwisko; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate dataUrodzenia) { this.birthDate = dataUrodzenia; }

    public String getNationality() { return nationality; }
    public void setNationality(String narodowosc) { this.nationality = narodowosc; }

    public String getAddress() { return address; }
    public void setAddress(String adres) { this.address = adres; }
}