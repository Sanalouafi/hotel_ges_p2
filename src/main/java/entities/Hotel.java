package main.java.entities;
public class Hotel {
    private int id;
    private String name;
    private String location;
    private String contactInfo;


    public Hotel(int id, String name, String location, String contactInfo) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}

