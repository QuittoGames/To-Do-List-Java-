package com.project;

import java.util.UUID;

import com.project.Controller.TaskController;
import com.project.Model.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        TaskController controller = new TaskController();
        UUID id = UUID.randomUUID();
        Task task = new Task(id, "Comp", false); // Provide a valid id
        System.out.println("ID 1: "+ task.getId() + "\n");
        System.out.println(task);
        controller.addTask(task);

        controller.addTask(new Task(UUID.randomUUID(), "Task 1", false));
        controller.addTask(new Task(UUID.randomUUID(), "Task 2", false));
        controller.addTask(new Task(UUID.randomUUID(), "Task 3", true));
        controller.addTask(new Task(UUID.randomUUID(), "Task 4", false));


        controller.completeTask(id);
    
    }
}