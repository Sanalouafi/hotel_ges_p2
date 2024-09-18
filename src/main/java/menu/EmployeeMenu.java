package main.java.menu;


import main.java.service.PersonService;

import java.util.Scanner;

public class EmployeeMenu {
    private Scanner scanner;
    private PersonService personService;

    public EmployeeMenu(PersonService personService) {
        this.scanner = new Scanner(System.in);
        this.personService = personService;
    }

    public void employeeMenu() {
        while (true) {
            System.out.println("Employee Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Show Employee By Id");
            System.out.println("5. Back to Main Menu");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    personService.saveEmployee();
                    break;
                case 2:
                    personService.updateEmployee();
                    break;
                case 3:
                    personService.deleteEmployee();
                    break;
                case 4:
                    personService.getEmployeeById();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

