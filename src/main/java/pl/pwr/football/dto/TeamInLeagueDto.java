package pl.pwr.football.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamInLeagueDto {
    private Integer teamId;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}