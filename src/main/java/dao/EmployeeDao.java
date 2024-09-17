package main.java.dao;

import main.java.entities.Employee;

import java.util.List;

public interface EmployeeDao {
    public  List<Employee> getAllEmployees();
    public  Employee getEmployeeById(int employeeId);
    public  void saveEmployee(Employee employee);
    public  void updateEmployee(Employee employee);
    public  void deleteEmployee(int employeeId);
}
