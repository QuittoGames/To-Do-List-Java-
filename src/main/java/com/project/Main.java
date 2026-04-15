package com.project;

import java.util.Scanner;
import com.project.Services.AppsServices;

public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            AppsServices.menu();
            System.out.println("1. Add Task");
            System.out.println("2. Complete Task");
            System.out.println("3. Remove Task");
            System.out.println("4. Exit");

            System.out.print("Write your option: ");
            String input = sc.nextLine();

            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid input. Please enter a number.");
            }
            
            if (option > 4 || option <= 0 ){
                throw new RuntimeException("Option cannot be out of bonds");
            }

            switch (option) {
                case 1:
                    AppsServices.addTak();
                    AppsServices.sleep(1000L);
                    Main.main(args);
                    break;
                case 2:
                    AppsServices.completeTask();
                    AppsServices.sleep(1000L);
                    Main.main(args);
                default:
                    break;
            }
        }catch(RuntimeException e){
            System.out.println("[ERROR] " + e.getMessage());
            AppsServices.sleep(1000L);
            Main.main(args); // recurçao
            return;
        }
    }
}