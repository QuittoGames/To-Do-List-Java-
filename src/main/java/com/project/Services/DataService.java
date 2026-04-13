package com.project.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.Model.Task;

public class DataService {
    private Path jsonFilePath = Path.of("D:\\Projects\\Java\\Itau\\ToDoList\\src\\main\\java\\com\\project\\tasks.json");

    private ObjectMapper objectMapper = new ObjectMapper();

    public Path getFilePath(){
        return jsonFilePath;
    }

    public File read(){
        try {
            File file = jsonFilePath.toFile();

            if(!file.exists()){
                throw new FileNotFoundException("Tasks file not found");
            }
            
            return file;
        } catch (FileNotFoundException E) {
            System.out.println("[ERROR] Error reading tasks file: " + E.getMessage());
            return null;
        }   
    }

    public void saveTasks(List<Task> tasks) {
        try {
            if (tasks == null){
                throw new Exception("Tasks list is null");
            }

            String json = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(tasks);

            Files.writeString(jsonFilePath, json);

        } catch (Exception e) {
            System.out.println("[ERROR] Error saving tasks: " + e.getMessage());
        }
    }

    public void removeTask(UUID targetID){
        try{
            File file = read();
            if (file == null) {
                throw new IllegalStateException("Tasks file could not be read");
            }

            if (targetID == null){
                throw new IllegalArgumentException("Target ID is null");
            }

            ObjectNode root = (ObjectNode) objectMapper.readTree(file);
            ArrayNode tasksJSON = (ArrayNode) root.get("tasks");
            Iterator<JsonNode> it = tasksJSON.iterator();
            
            while (it.hasNext()) {
                JsonNode node = it.next();

                if(node.get("id").asText().equals(targetID.toString())){
                    it.remove();
                }                
            }

            objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(file, root);

        } catch(NullPointerException e){
            System.out.println("[ERROR] Null value encountered while removing task. ID: " + targetID + ", Details: " + e.getMessage());
        } catch(IllegalStateException e){
            System.out.println("[ERROR] Invalid state while removing task. ID: " + targetID + ", Details: " + e.getMessage());
        } catch(IllegalArgumentException e){
            System.out.println("[ERROR] Invalid argument while removing task. ID: " + targetID + ", Details: " + e.getMessage());
        } catch(Exception e){
            System.out.println("[ERROR] Unexpected error while removing task. ID: " + targetID + ", Details: " + e.getMessage());
        }
    }
     
}
