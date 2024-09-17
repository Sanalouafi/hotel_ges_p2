package main.java.dao;

import main.java.entities.Season;
import java.util.List;

public interface SeasonDao {
    List<Season> getAllSeasons();
    Season getSeasonById(int seasonId);
    void saveSeason(Season season);
    void updateSeason(Season season);
    void deleteSeason(int seasonId);
}
