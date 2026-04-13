package com.project.Services;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import com.project.DTO.BinarySeachRenponderDTO;
import com.project.Model.Task;
import com.project.data.Data;

public class TaskService {
    private List<Task> data = Data.getTaskData();
    protected static final DataService dataService = new DataService();

    public void addTask(Task task)throws IllegalArgumentException,NullPointerException{        
        if (task == null){
            throw new NullPointerException("Task is null");
        }
        String name = task.getName();
        if (name == null || name.length() >= 100){
            throw new IllegalArgumentException("Task is ilegal or null");
        }
        
        UUID id = task.getId();
        if (id == null){
            throw new IllegalArgumentException("Task id is null");
        }

        this.data.add(task);
        dataService.saveTasks(data);
    }
    
    public void completeTask(UUID id) throws NullPointerException{
        if (id == null){
            throw new IllegalArgumentException("Task id is null");
        }

        Task task = binarySearchTask(this.data, id).getTask();
        if (task == null){throw new NullPointerException("Task not found, id is null");}
        task.setTaskComplete(true);
        dataService.saveTasks(this.data);
    }

    public static BinarySeachRenponderDTO binarySearchTask(List<Task> dataTasks,UUID target){
        List<Task> sorted = dataTasks.stream()
            .sorted(Comparator.comparing(Task::getId))
            .toList();

        int left = 0;
        int right = sorted.size() - 1;
        
        while (left <= right) {
            int mid = (left + right) / 2; // Index value
            Task TaskValue = sorted.get(mid);
            UUID value = TaskValue.getId();

            int cmp = value.compareTo(target); // This verify with one value int based in value for UUID the size of UUID

            // 0 = equal, 1 = greater, -1 = smaller
            if (cmp == 0){
                return new BinarySeachRenponderDTO(TaskValue,mid);
            }
            else if (cmp > 0){
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }
        return null;
    }

    public void removeTask(UUID id) throws NullPointerException{
        if (id == null){
            throw new IllegalArgumentException("Task id is null");
        }
        dataService.removeTask(id);
        removeTaskInArry(id);
    }

    public void removeTaskInArry(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Task id is null");
        }

        int index = binarySearchTask(this.data, id).getIndex();
        if (index < 0 ){throw new NullPointerException("Task not found, id is null");}
        this.data.remove(index);
    }


}
