# Project Guidelines

## Code Style
- **Language**: Pure Java 17+ with Maven. JSON mapping using Jackson Databind.
- **Error Handling**: Use explicit checks for validations and `NullPointerException`s locally. Catch exceptions at the Controller level and print them cleanly to the terminal with an `[ERROR]` prefix (e.g., in `com.project.Main.java` or `TaskController.java`).

## Architecture
This project follows a simplified MVC / Layered architecture:
- **Controller Layer** (e.g., `TaskController.java`): Intercepts flow, gracefully handles errors, and executes action logging.
- **Service Layer** (`TaskService.java`, `DataService.java`): Implements business rules, algorithmic searches, and delegates data formatting to the data layer. 
- **Data/Persistence Layer** (`Data.java` / `tasks.json`): Static list bridge in memory, ultimately persisting updates to `tasks.json` locally using `ObjectMapper` and `JsonNode`.

## Build and Test
- **Compile and Build**: `mvn clean install`
- **Run the Application**: `mvn exec:java -Dexec.mainClass="com.project.Main"`

## Conventions
- **Binary Search Priority**: Always use the manual `binarySearchTask` method in the Service to look up a task by `UUID` instead of standard iteration.
- **Pre-Sorting via Streams**: Because binary search requires sorted input, local lists *must* be explicitly ordered using Java Streams (e.g., `Comparator.comparing(Task::getId)`) prior to searching.
- **DTOs for Locators**: When finding items via UUID, return the custom `BinarySeachRenponderDTO` to pack both the located `Task` entity and its respective `index` so exact updates or deletions can be made safely.
- **No External DBs**: Do not suggest implementations requiring databases like PostgreSQL or MySQL; adhere to the local JSON file persistence model.