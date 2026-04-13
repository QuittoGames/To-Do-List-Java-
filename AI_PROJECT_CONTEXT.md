# AI Project Context - ToDoList (Java)

## 1. Project Summary

This is a simple Java 17 Maven project for task management.
It supports:

- Adding tasks
- Marking tasks as completed
- Removing tasks

Tasks are represented by a UUID, name, and status.
Persistence is done through a JSON file on disk.

---

## 2. Tech Stack

- Language: Java 17
- Build tool: Maven
- JSON library: Jackson Databind 2.17.2

From `pom.xml`:

- `groupId`: `com.project`
- `artifactId`: `project`
- `version`: `1.0-SNAPSHOT`

---

## 3. High-Level Architecture

Layered structure:

1. Entry point layer
2. Controller layer
3. Service layer
4. Data/persistence layer
5. Model and DTO layer

Main files and responsibilities:

- `src/main/java/com/project/Main.java`
  - Demonstrates the app flow (creates tasks and executes operations).
- `src/main/java/com/project/Controller/TaskController.java`
  - Wraps service calls with try/catch and logs errors.
- `src/main/java/com/project/Services/TaskService.java`
  - Business rules for add/complete/remove and binary search.
- `src/main/java/com/project/Services/DataService.java`
  - Reads/writes task JSON file.
- `src/main/java/com/project/Model/Task.java`
  - Domain entity (id, name, status).
- `src/main/java/com/project/DTO/BinarySeachRenponderDTO.java`
  - DTO with found task and index from search.
- `src/main/java/com/project/data/Data.java`
  - In-memory list holder and JSON read helper.

---

## 4. Domain Model

### Task

Fields:

- `UUID id`
- `String name`
- `boolean status`

Meaning:

- `id`: unique task identifier
- `name`: task title
- `status`: completion flag (`true` = completed)

Serialization annotations:

- `@JsonProperty("id")`
- `@JsonProperty("name")`
- `@JsonProperty("status")`

---

## 5. Runtime Flow

### 5.1 Add Task flow

1. `Main` creates a `Task`.
2. `TaskController.addTask(task)` is called.
3. `TaskService.addTask(task)` validates:
   - task not null
   - name not null and length < 100
   - id not null
4. If valid:
   - task is appended to in-memory list (`Data.getTaskData()`).
   - JSON is saved via `DataService.saveTasks(data)`.
5. Controller catches and logs known exceptions.

### 5.2 Complete Task flow

1. `TaskController.completeTask(id)` calls service.
2. `TaskService.completeTask(id)` validates id.
3. Uses `binarySearchTask(...)` to find task.
4. Sets status to true.
5. Saves updated list to JSON.

### 5.3 Remove Task flow

1. `TaskController.removeTask(id)` calls service.
2. `TaskService.removeTask(id)` validates id.
3. Calls `DataService.removeTask(id)` (file removal path).
4. Calls `removeTaskInArry(id)` (in-memory removal path).

---

## 6. Search Strategy

`TaskService.binarySearchTask(List<Task>, UUID)`:

1. Creates a sorted copy of tasks by `Task::getId`.
2. Runs binary search on sorted list.
3. Returns `BinarySeachRenponderDTO(task, index)` when found.
4. Returns `null` if not found.

Important detail:

- Returned `index` is from the sorted list, not necessarily the original in-memory list order.

---

## 7. Persistence Details

Primary JSON path is hardcoded in `DataService`:

- `D:\\Projects\\Java\\Itau\\ToDoList\\src\\main\\java\\com\\project\\tasks.json`

`DataService.saveTasks(List<Task>)` writes the entire task list as pretty JSON.

Current expected format used by `saveTasks` is a plain JSON array:

```json
[
  {
    "id": "uuid",
    "name": "Task name",
    "status": false
  }
]
```

---

## 8. Exception and Logging Behavior

Controller layer logs with `System.out.println`.
Service layer throws runtime exceptions when validation fails or entities are missing.

Examples:

- Null task on add -> `NullPointerException`
- Invalid name/id -> `IllegalArgumentException`
- Missing task in complete/remove path -> `NullPointerException` (current implementation)

---

## 9. Known Issues and Inconsistencies

These are important for any AI agent modifying this code.

1. `DataService.removeTask` expects a root object with `tasks` array.
   - It does `ObjectNode root = (ObjectNode) objectMapper.readTree(file);`
   - Then `root.get("tasks")`.
   - But persisted file is a plain array, not `{ "tasks": [...] }`.
   - This can cause class cast/null issues.

2. `TaskService.binarySearchTask` may return `null`, but callers do not null-check.
   - `completeTask` and `removeTaskInArry` immediately dereference result.

3. Binary search index mismatch risk.
   - Search index is from sorted copy, removal happens on original list.
   - Can remove wrong task if original list order differs.

4. `Data` class has uninitialized static dependency.
   - `private static DataService dataService;` is never assigned before use in `readTasks`.

5. `Task.toString()` is very verbose and recursive-like (includes super and class/hash details).
   - Not ideal for production logs.

6. Naming typos reduce readability and discoverability.
   - `BinarySeachRenponderDTO` (likely intended `BinarySearchResponderDTO`)
   - `removeTaskInArry` (likely intended `removeTaskInArray`)
   - Message typo: "ilegal".

7. Hardcoded absolute Windows path reduces portability.
   - Running in another machine/folder can break persistence.

---

## 10. Functional Invariants

Expected constraints for valid task operations:

- `Task` must not be null
- `Task.id` must not be null
- `Task.name` must not be null and must have fewer than 100 chars
- Operation ids must not be null

---

## 11. Practical Notes for AI Agents

When editing this project, preserve these behavior assumptions unless explicitly requested:

1. Keep UUID as task identity.
2. Keep JSON persistence with Jackson.
3. Keep controller-service separation.
4. Validate inputs in service layer.
5. Ensure add/complete/remove continue to work from `Main` demo path.

If refactoring is requested, safest order is:

1. Fix persistence format mismatch first.
2. Fix search/remove index logic second.
3. Add null safety around binary search result.
4. Replace hardcoded path with configurable relative path.
5. Improve naming and log style.

---

## 12. Suggested Next Tests

High-value tests to add:

1. Add task with valid data persists to JSON.
2. Add task with null task/name/id throws expected exceptions.
3. Complete existing task changes `status` to true.
4. Complete with unknown id should fail gracefully.
5. Remove existing task removes from memory and JSON consistently.
6. Remove with unknown id does not corrupt state.

---

## 13. Quick Run Context

The current `Main` method demonstrates a manual flow:

1. Create controller
2. Create one task with known `id`
3. Add multiple tasks
4. Mark first task as complete

It is useful as a smoke test but not a full test suite.
