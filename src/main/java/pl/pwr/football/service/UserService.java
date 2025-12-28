package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.dto.UserDto;
import pl.pwr.football.entity.User;
import pl.pwr.football.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}