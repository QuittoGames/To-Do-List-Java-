package com.project.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Task {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private boolean status;
    
    public Task(UUID id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Task() {
    }
    
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isStatus() {
        return status;
    }
    public void setTaskComplete(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", status=" + status + ", getId()=" + getId() + ", getName()="
                + getName() + ", isStatus()=" + isStatus() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }
}
