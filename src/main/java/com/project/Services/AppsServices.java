package com.project.Services;

import java.io.Console;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.ConsoleHandler;

import com.project.Controller.TaskController;
import com.project.Model.Task;
import com.project.data.Data;

public class AppsServices {
    public static final TaskController controller = new TaskController();
    static Scanner sc = new  Scanner(System.in);

    public static void menu(){
        clear_scream();
        System.out.println("\n╔════════════════════════════════════════════╗\n║                   TASKS                    ║\n╚════════════════════════════════════════════╝\n");
        System.out.println("Current tasks:\n" + Data.getTasksFormatted());
    }

    public static void clear_scream(){
        String os = System.getProperty("os.name");
        ProcessBuilder pb;
        try {
            if (os.contains("Windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }
            pb.inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("[WARN] Não foi possível limpar o terminal neste ambiente.");
        }
    }

    public static void addTak() throws RuntimeException{
        Task task = inputTask();
        if (task == null){
            throw new RuntimeException("Error: Task title cannot be empty.");
        }
        controller.addTask(task);
    }

    public static void completeTask() throws RuntimeException{
        if (Data.getTaskData().isEmpty()){
            throw new RuntimeException("No tasks available. Try adding a task from the menu first.");
        }

        System.out.println("Select a task to complete by its ID:");
        System.out.println(Data.getTasksFormatted());
        System.out.print("Task ID: ");
        String input = sc.nextLine();

        if (input.trim().isEmpty()){
            throw new RuntimeException("Error: Task ID cannot be empty.");
        }

        try {
            UUID id = UUID.fromString(input.trim());
            controller.completeTask(id);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: Invalid Task ID format.");
        }
    }

    public static void removeTask(){
        if (Data.getTaskData().isEmpty()){
            throw new RuntimeException("No tasks available. Try adding a task from the menu first.");
        }

        System.out.println("Select a task to complete by its ID:");
        System.out.println(Data.getTasksFormatted());
        System.out.print("Task ID: ");
        String input = sc.nextLine();

        if (input.trim().isEmpty()){
            throw new RuntimeException("Error: Task ID cannot be empty.");
        }

        try {
            UUID id = UUID.fromString(input.trim());
            controller.removeTask(id);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: Invalid Task ID format.");
        }
    }
    
    public static Task inputTask(){
        UUID id = UUID.randomUUID();
        
        System.out.print("Enter the title of the task: ");
        String name = sc.nextLine();
        if(name.trim().isEmpty()){
            return null;
        }

        return new Task(id,name);
        
    }

    public static void sleep(Long time){
        try{
            Thread.sleep(time);
        }catch(Exception E){
            System.out.println("[ERROR] application sleep interrupted.");
        }
    }

    
}
