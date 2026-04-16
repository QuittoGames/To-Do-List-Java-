package com.project;

import java.util.Scanner;
import com.project.Services.AppsServices;

public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                AppsServices.menu();

                System.out.println("1. Add Task");
                System.out.println("2. Complete Task");
                System.out.println("3. Remove Task");
                System.out.println("4. Exit");

                System.out.print("Write your option: ");
                int option = Integer.parseInt(sc.nextLine());

                if (option < 1 || option > 4) {
                    System.out.println("Invalid option.");
                    continue;
                }

                switch (option) {
                    case 1:
                        AppsServices.addTak();
                        break;
                    case 2:
                        AppsServices.completeTask();
                        break;
                    case 3:
                        AppsServices.removeTask();
                        break;
                    case 4:
                        System.out.println("Bye!");
                        return;
                }

            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }
}