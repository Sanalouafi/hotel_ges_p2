package main.java.entities;
import java.sql.Timestamp;

public class Client extends Person {


    public Client(int id, String fullname, String username, String password, String email, String phoneNumber, String address, Timestamp createdAt) {
        super(id, fullname, username, password, email, phoneNumber, address, createdAt);
    }

    @Override
    public String toString() {
        return "Client{" +
                ", fullname='" + super.getFullname() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", phoneNumber='" + super.getPhoneNumber() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", createdAt=" + super.getCreatedAt() +
                '}';
    }
}

