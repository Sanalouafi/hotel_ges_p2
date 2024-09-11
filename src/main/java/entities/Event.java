package main.java.entities;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private Date eventDate;

    public Event(int id, String name, Date eventDate) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getEventDate() {
        return eventDate;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
