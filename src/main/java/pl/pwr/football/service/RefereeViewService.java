package pl.pwr.football.service;

import org.springframework.stereotype.Service;
import pl.pwr.football.entity.PlayerView;
import pl.pwr.football.entity.RefereeView;
import pl.pwr.football.repository.PlayerViewRepository;
import pl.pwr.football.repository.RefereeViewRepository;

import java.util.List;

@Service
public class RefereeViewService {

    private final RefereeViewRepository refereeViewRepo;

    public RefereeViewService(RefereeViewRepository refereeViewRepo) {
        this.refereeViewRepo = refereeViewRepo;
    }


    public List<RefereeView> getAllReferees() {
        return refereeViewRepo.findAll();
    }

}