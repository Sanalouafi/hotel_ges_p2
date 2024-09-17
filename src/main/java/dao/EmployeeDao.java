package main.java.dao;

import main.java.entities.Employee;

import java.util.List;

public abstract class EmployeeDao {
    public abstract List<Employee> getAllEmployees();
    public abstract Employee getEmployeeById(int employeeId);
    public abstract void saveEmployee(Employee employee);
    public abstract void updateEmployee(Employee employee);
    public abstract void deleteEmployee(int employeeId);
}
