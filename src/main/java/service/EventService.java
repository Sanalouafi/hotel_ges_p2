package main.java.service;

import main.java.entities.Event;
import main.java.dao.impl.EventDaoImpl;

import java.sql.Date;
import java.util.Scanner;

public class EventService {

    private EventDaoImpl eventDaoImpl;
    private Scanner scanner;

    public EventService() {
        this.eventDaoImpl = new EventDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void saveEvent() {
        System.out.println("Enter Event name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Event date (YYYY-MM-DD): ");
        Date eventDate = Date.valueOf(scanner.nextLine());

        Event event = new Event(0, name, eventDate);
        eventDaoImpl.saveEvent(event);
    }

    public void updateEvent() {
        System.out.println("Enter Event id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Event name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Event date (YYYY-MM-DD): ");
        Date eventDate = Date.valueOf(scanner.nextLine());
        Event event = new Event(id, name, eventDate);
        eventDaoImpl.updateEvent(event);
    }

    public void deleteEvent() {
        System.out.println("Enter Event id: ");
        int id = scanner.nextInt();
        eventDaoImpl.deleteEvent(id);
    }

    public Event getEventById() {
        System.out.println("Enter Event id: ");
        int id = scanner.nextInt();
        return eventDaoImpl.getEventById(id);
    }
}
