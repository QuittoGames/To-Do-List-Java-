package com.project.DTO;

import com.project.Model.Task;

public class BinarySeachRenponderDTO {
    private Task task;
    private int index;
    
    public BinarySeachRenponderDTO(Task task, int index) {
        this.task = task;
        this.index = index;
    }
    public BinarySeachRenponderDTO() {
    }
    public Task getTask() {
        return task;
    }
    public int getIndex() {
        return index;
    }
    
}
