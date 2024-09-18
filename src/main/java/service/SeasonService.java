package main.java.service;

import main.java.entities.Season;
import main.java.dao.impl.SeasonDaoImpl;

import java.sql.Date;
import java.util.Scanner;

public class SeasonService {

    private SeasonDaoImpl seasonDaoImpl;
    private Scanner scanner;

    public SeasonService() {
        this.seasonDaoImpl = new SeasonDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void saveSeason() {
        System.out.println("Enter Season name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Season start date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.println("Enter Season end date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(scanner.nextLine());
        Season season = new Season(0, name, startDate, endDate);
        seasonDaoImpl.saveSeason(season);
    }

    public void updateSeason(int id, String name, Date startDate, Date endDate) {
        Season season = new Season(id, name, startDate, endDate);
        seasonDaoImpl.updateSeason(season);
    }

    public void deleteSeason(int id) {

        seasonDaoImpl.deleteSeason(id);
    }

    public Season getSeasonById(int id) {
        return seasonDaoImpl.getSeasonById(id);
    }
}
