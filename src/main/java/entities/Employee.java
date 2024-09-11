package main.java.entities;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Employee extends Person {
    private String position;
    private Date hireDate;
    private BigDecimal salary;



    public Employee(int id, String fullname, String username, String password, String email, String phoneNumber, String address, Timestamp createdAt, String position, Date hireDate, BigDecimal salary) {
        super(id, fullname, username, password, email, phoneNumber, address, createdAt);
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", fullname='" + super.getFullname() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", phoneNumber='" + super.getPhoneNumber() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", createdAt=" + super.getCreatedAt() +
                ", position='" + position + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                '}';
    }
}

