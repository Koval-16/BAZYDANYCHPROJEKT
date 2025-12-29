package pl.pwr.football.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueSeasonAddedDto {
    private Integer leagueId;

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }
}
