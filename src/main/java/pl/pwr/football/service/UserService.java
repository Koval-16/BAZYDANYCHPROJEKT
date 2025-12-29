package pl.pwr.football.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwr.football.dto.UserRegisterDto;
import pl.pwr.football.entity.Role;
import pl.pwr.football.entity.User;
import pl.pwr.football.repository.RoleRepository;
import pl.pwr.football.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // Do kodowania haseł

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

        // WAŻNE: Kodujemy hasło!
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthDate(dto.getBirthDate());
        user.setNationality(dto.getNationality());
        user.setAddress(dto.getAddress());
        user.setLookingForClub(false);

        userRepository.save(user);
    }


}