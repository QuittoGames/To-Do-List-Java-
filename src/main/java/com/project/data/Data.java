package com.project.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Model.Task;
import com.project.Services.DataService;

public class Data {
    private static List<Task> taskData = new ArrayList<>();

    private static DataService dataService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Task> getTaskData() {
        return taskData;
    }
    
    public static String getTasksFormatted() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════╗\n");
        sb.append("║              TASKS                      ║\n");
        sb.append("╚════════════════════════════════════════════╝\n");
        
        if (taskData.isEmpty()) {
            sb.append("  No tasks yet.\n");
            return sb.toString();
        }
        
        for (Task task : taskData) {
            String status = task.isStatus() ? "✅ Done" : "⏳ Pending";
            sb.append(String.format("┌─────────────────────────────────────────┐\n"));
            sb.append(String.format("│ UUID: %s\n", task.getId()));
            sb.append(String.format("│ Name: %s\n", task.getName()));
            sb.append(String.format("│ Status: %s\n", status));
            sb.append(String.format("└─────────────────────────────────────────┘\n"));
        }
        return sb.toString();
    }

    public List<Task> readTasks() {
        try {
            File jsonFile = dataService.read();

            return objectMapper.readValue(
                jsonFile,
                new TypeReference<List<Task>>() {}
            );

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            return new ArrayList<>();
        }
    }
}