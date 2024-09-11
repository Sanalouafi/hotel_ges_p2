package main.java.entities;

import main.java.enums.RoomType;
import java.math.BigDecimal;
import java.sql.Date;

public class Pricing {
    private int id;
    private RoomType roomType;
    private Season season;
    private Event event;
    private BigDecimal basePrice;
    private BigDecimal priceMultiplier;
    private Date startDate;
    private Date endDate;

    public Pricing(int id, RoomType roomType, Season season, Event event, BigDecimal basePrice, BigDecimal priceMultiplier, Date startDate, Date endDate) {
        this.id = id;
        this.roomType = roomType;
        this.season = season;
        this.event = event;
        this.basePrice = basePrice;
        this.priceMultiplier = priceMultiplier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;  // Nullable
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;    // Nullable
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Pricing{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", season=" + (season != null ? season.toString() : "None") +
                ", event=" + (event != null ? event.toString() : "None") +
                ", basePrice=" + basePrice +
                ", priceMultiplier=" + priceMultiplier +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}



