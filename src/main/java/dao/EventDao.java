package main.java.dao;

import main.java.entities.Event;
import java.util.List;

public interface EventDao {
    List<Event> getAllEvents();
    Event getEventById(int eventId);
    void saveEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(int eventId);
}
