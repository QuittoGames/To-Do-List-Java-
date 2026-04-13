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