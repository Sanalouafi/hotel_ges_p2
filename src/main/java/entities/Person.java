package main.java.entities;

import java.sql.Timestamp;

public class Person {
    private int id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private Timestamp createdAt;

    public Person(int id, String fullname, String username, String password, String email, String phoneNumber, String address, Timestamp createdAt) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
    }
    public int getId(){
        return id;
    }
    public String getFullname(){
        return fullname;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getAddress(){
        return address;
    }
    public Timestamp getCreatedAt(){
        return createdAt;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setFullname(String fullname){
        this.fullname = fullname;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setCreatedAt(Timestamp createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
