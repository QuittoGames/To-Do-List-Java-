package com.project.Controller;

import java.util.UUID;

import com.project.Model.Task;
import com.project.Services.TaskService;

public class TaskController {
    private TaskService taskService = new TaskService();

    public void addTask(Task task){
        try{
            this.taskService.addTask(task);
        }catch(NullPointerException NPE){
            System.out.println("[ERROR] Null Pointer exception in addTask, Error: " + NPE.getMessage());
        }catch(IllegalArgumentException IAE){
            System.out.println("[ERROR] Illegal Argument exception in addTask, Error: " + IAE.getMessage());
        }
    }
    
    public void completeTask(UUID id){
        try{
            this.taskService.completeTask(id);
        }catch(IllegalArgumentException IAE){
            System.out.println("[ERROR] Illegal Argument exception in completeTask, Error: " + IAE.getMessage());
        }
    }

    public void removeTask(UUID id){
        try{
            taskService.removeTask(id);
        }catch(IllegalArgumentException IAE){
            System.out.println("[ERROR] Illegal Argument exception in removeTask, Error: " + IAE.getMessage());
        }catch(NullPointerException NPE){
            System.out.println("[ERROR] Null Pointer exception in removeTask, Error: " + NPE.getMessage());
        }
    }
}
