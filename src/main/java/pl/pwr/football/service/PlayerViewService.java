package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.PlayerView;
import pl.pwr.football.entity.RefereeView;
import pl.pwr.football.repository.PlayerViewRepository;
import pl.pwr.football.repository.RefereeViewRepository;

import java.util.List;

@Service
public class PlayerViewService {

    private final PlayerViewRepository playerViewRepo;

    public PlayerViewService(PlayerViewRepository playerViewRepo) {
        this.playerViewRepo = playerViewRepo;
    }

    public List<PlayerView> getAllPlayers() {
        return playerViewRepo.findAll();
    }

    public List<PlayerView> get_team_players(Integer id){
        return playerViewRepo.findByDruzynaID(id);
    }
}