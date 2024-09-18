package main.java.menu;

import main.java.service.PersonService;

import java.util.Scanner;

public class ClientMenu {
    private Scanner scanner;
    private PersonService personService;

    public ClientMenu(PersonService personService) {
        this.scanner = new Scanner(System.in);
        this.personService = personService;
    }

    public void clientMenu() {
        while (true) {
            System.out.println("Client Menu:");
            System.out.println("1. Add Client");
            System.out.println("2. Update Client");
            System.out.println("3. Delete Client");
            System.out.println("4. Show Client By Id");
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
                    personService.saveClient();
                    break;
                case 2:
                    personService.updateClient();
                    break;
                case 3:
                    personService.deleteClient();
                    break;
                case 4:
                    personService.getClientById();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

