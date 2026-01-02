package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pwr.football.dto.UserRegisterDto;
import pl.pwr.football.entity.entities.Role;
import pl.pwr.football.entity.entities.User;
import pl.pwr.football.repository.entities.RoleRepository;
import pl.pwr.football.repository.entities.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDto dto) {
        // 1. Walidacja unikalności loginu
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Ten login jest już zajęty!");
        }

        // 2. Pobranie roli z bazy
        Role role = roleRepository.findByName(dto.getRoleName())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono roli: " + dto.getRoleName()));

        // 3. Tworzenie encji Uzytkownik
        User user = new User();
        user.setRole(role);
        user.setLogin(dto.getLogin());

        // Kodowanie hasła
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthDate(dto.getBirthDate());
        user.setNationality(dto.getNationality());
        user.setAddress(dto.getAddress());
        user.setLookingForClub(false); // Domyślnie false

        userRepository.save(user);
    }

    public List<User> getFreePlayers() {
        // Musisz upewnić się, że masz metodę w repozytorium:
        // List<User> findAllByRole_NameAndLookingForClubTrue(String roleName);
        // Jeśli nie, dodaj ją w UserRepository, albo filtruj w Javie (mniej wydajne, ale przy małej skali ok):

        return userRepository.findAllByRole_Name("Pilkarz").stream()
                .filter(u -> Boolean.TRUE.equals(u.getLookingForClub()))
                .toList();
    }

    public List<User> getFreeCoaches(){
        // Metoda korzysta z UserRepository.findAllByRole_Name
        return userRepository.findAllByRole_Name("Trener");
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika: " + login));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika: " + id));
    }

    public void updateLookingForClub(String login, boolean isLooking) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Brak użytkownika"));

        user.setLookingForClub(isLooking);
        userRepository.save(user);
    }
}