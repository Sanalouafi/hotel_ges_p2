package main.java.service;

import main.java.entities.Client;
import main.java.entities.Employee;
import main.java.dao.impl.ClientDaoImpl;
import main.java.dao.impl.EmployeeDaoImpl;
import main.java.utils.PasswordUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Scanner;
import java.math.BigDecimal;

public class PersonService {

    private ClientDaoImpl clientDaoImpl;
    private EmployeeDaoImpl employeeDaoImpl;
    private Scanner scanner;

    public PersonService() {
        this.clientDaoImpl = new ClientDaoImpl();
        this.employeeDaoImpl = new EmployeeDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    // Client Methods
    public void saveClient() {
        System.out.println("Enter Client name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Client username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Client password: ");
        String password = scanner.nextLine();
        System.out.println("Enter Client email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Client phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter Client address: ");
        String address = scanner.nextLine();
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        Client client = new Client(0, name, username, hashPassword(password), email, phone, address, createdAt);
        clientDaoImpl.saveClient(client);
    }

    public void updateClient() {
        System.out.println("Enter Client id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Client name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Client username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Client password: ");
        String password = scanner.nextLine();
        System.out.println("Enter Client email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Client phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter Client address: ");
        String address = scanner.nextLine();
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        Client client = new Client(id, name, username, hashPassword(password), email, phone, address, createdAt);
        clientDaoImpl.updateClient(client);
    }

    public void deleteClient() {
        System.out.println("Enter Client id: ");
        int id = scanner.nextInt();
        clientDaoImpl.deleteClient(id);
    }

    public Client getClientById() {
        System.out.println("Enter Client id: ");
        int id = scanner.nextInt();
        return clientDaoImpl.getClientById(id);
    }

    // Employee Methods
    public void saveEmployee() {
        System.out.println("Enter Employee name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Employee username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Employee password: ");
        String password = scanner.nextLine();
        System.out.println("Enter Employee email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Employee phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter Employee address: ");
        String address = scanner.nextLine();
        System.out.println("Enter Employee position: ");
        String position = scanner.nextLine();
        System.out.println("Enter Employee hire date (YYYY-MM-DD): ");
        Date hireDate = Date.valueOf(scanner.nextLine());
        System.out.println("Enter Employee salary: ");
        BigDecimal salary = scanner.nextBigDecimal();
        scanner.nextLine();

        
        Employee employee = new Employee(0, name, username, hashPassword(password), email, phone, address, new Timestamp(System.currentTimeMillis()), position, hireDate, salary);
        employeeDaoImpl.saveEmployee(employee);
    }

    public void updateEmployee() {
        System.out.println("Enter Employee id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Employee name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Employee username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Employee password: ");
        String password = scanner.nextLine();
        System.out.println("Enter Employee email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Employee phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter Employee address: ");
        String address = scanner.nextLine();
        System.out.println("Enter Employee position: ");
        String position = scanner.nextLine();
        System.out.println("Enter Employee hire date (YYYY-MM-DD): ");
        Date hireDate = Date.valueOf(scanner.nextLine());
        System.out.println("Enter Employee salary: ");
        BigDecimal salary = scanner.nextBigDecimal();
        scanner.nextLine();

        
        Employee employee = new Employee(id, name, username, hashPassword(password), email, phone, address, new Timestamp(System.currentTimeMillis()), position, hireDate, salary);
        employeeDaoImpl.updateEmployee(employee);
    }

    public void deleteEmployee() {
        System.out.println("Enter Employee id: ");
        int id = scanner.nextInt();
        employeeDaoImpl.deleteEmployee(id);
    }

    public Employee getEmployeeById() {
        System.out.println("Enter Employee id: ");
        int id = scanner.nextInt();
        return employeeDaoImpl.getEmployeeById(id);
    }

    
    private String hashPassword(String password) {
        return PasswordUtils.hashPassword(password);
    }
}
