package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.views.PlayerView;
import pl.pwr.football.entity.views.RefereeView;
import pl.pwr.football.repository.views.PlayerViewRepository;
import pl.pwr.football.repository.views.RefereeViewRepository;

import java.util.List;

@Service
public class PersonViewService {

    private final PlayerViewRepository playerViewRepo;
    private final RefereeViewRepository refereeViewRepo;

    public PersonViewService(PlayerViewRepository playerViewRepo, RefereeViewRepository refereeViewRepo) {
        this.playerViewRepo = playerViewRepo;
        this.refereeViewRepo = refereeViewRepo;
    }

    public List<PlayerView> getAllPlayers() {
        return playerViewRepo.findAll();
    }

    public List<RefereeView> getAllReferees() {
        return refereeViewRepo.findAll();
    }

    public List<PlayerView> get_team_players(Integer id){
        return playerViewRepo.findByTeamId(id);
    }
}