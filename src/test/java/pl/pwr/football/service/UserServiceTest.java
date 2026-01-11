package pl.pwr.football.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pwr.football.dto.UserRegisterDto;
import pl.pwr.football.entity.entities.Role;
import pl.pwr.football.entity.entities.User;
import pl.pwr.football.repository.entities.RoleRepository;
import pl.pwr.football.repository.entities.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_Success() {
        // Given
        UserRegisterDto dto = new UserRegisterDto();
        dto.setLogin("nowyUser");
        dto.setPassword("haslo123");
        dto.setRoleName("Pilkarz");
        dto.setName("Jan");

        Role role = new Role();
        role.setName("Pilkarz");

        // Mockowanie zachowań
        when(userRepository.existsByLogin("nowyUser")).thenReturn(false); // Login wolny
        when(roleRepository.findByName("Pilkarz")).thenReturn(Optional.of(role)); // Rola istnieje
        when(passwordEncoder.encode("haslo123")).thenReturn("zakodowaneHaslo###"); // Szyfrowanie

        // When
        userService.registerUser(dto);

        // Then
        // Sprawdzamy czy do metody save() trafił obiekt z zakodowanym hasłem
        verify(userRepository).save(argThat(user ->
                user.getLogin().equals("nowyUser") &&
                        user.getPassword().equals("zakodowaneHaslo###") &&
                        user.getRole().getName().equals("Pilkarz")
        ));
    }

    @Test
    void registerUser_Fail_LoginTaken() {
        // Given
        UserRegisterDto dto = new UserRegisterDto();
        dto.setLogin("zajetyLogin");

        when(userRepository.existsByLogin("zajetyLogin")).thenReturn(true); // Login ZAJĘTY

        // When & Then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("Ten login jest już zajęty!", ex.getMessage());
        // Upewniamy się, że NIE próbowano zapisać użytkownika
        verify(userRepository, never()).save(any());
    }
}