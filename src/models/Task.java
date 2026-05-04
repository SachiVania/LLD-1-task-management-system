package models;

import State.TaskState;
import State.TodoState;
import enums.TaskPriority;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private String description;
    private User assignee;
    private TaskPriority priority;
//    private TaskStatus status; // don't use it directly, instead use state
    private TaskState currentState;
    private LocalDate dueDate;

    private final User createdBy;

    // Because of the builder pattern, we need only one ctor
    private Task( TaskBuilder builder ) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.priority = builder.priority;
        this.createdBy = builder.createdBy;
        this.assignee = builder.assignee;
        this.currentState = new TodoState(); // Initial state
    }

    public void setState(TaskState state) {
        this.currentState = state;
    }

    // Getters and Setters

    
    // Builder -------------------
    // 1. builder class created inside the main class, and as static
    public static class TaskBuilder {

        // 2. copy all the fields from the main class. decide which fields are mandatory.
        private final String id;
        private String title;
        private String description;
        private User assignee;
        private TaskPriority priority;
//        private TaskState currentState; not used here
        private LocalDate dueDate;

        // Here this field is not final
        private User createdBy;

        // 3. mandatory fields set in the ctor
        public TaskBuilder(String title) {
            id = UUID.randomUUID().toString();
            this.title = title;
        }

        // 4. create setters for optional fields
        // these methods should return builder object
        public TaskBuilder description(String desc) { this.description = desc; return this;}
        public TaskBuilder assignee(User assignee) { this.assignee = assignee; return this;}
        public TaskBuilder priority(String desc) { this.description = desc; return this;}
        public TaskBuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this;}
        public TaskBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this;}

        // 5. build method to call private ctor of main class
        public Task build() {
            return new Task(this);
        }
    }

}
