package models;

import enums.TaskStatus;
import observer.TaskObserver;
import state.TaskState;
import state.TodoState;
import enums.TaskPriority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    private List<TaskObserver> observers;
    private List<Comment> comments;

    // we need to send notifications for change in priority, assignee, comment,


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
        this.observers = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void setState(TaskState state) {
        this.currentState = state;
        notifyObservers("status");
    }
    public void startProgress() { currentState.startProgress(this);}
    public void completeTask() { currentState.completeTask(this);}
    public void reopenTask() { currentState.reopenTask(this);}
    // Observers -----------------
    public void addObserver (TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver (TaskObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers (String changeType) {
        for (TaskObserver observer : observers) {
            observer.update(this, changeType);
        }
    }

    // notify observers ----------
    public synchronized void setAssignee(User user) {
        this.assignee = user;
        notifyObservers("assignee");
    }
    public synchronized void updatePriority(TaskPriority priority) {
        this.priority = priority;
        notifyObservers("priority");
    }
    public synchronized void addComment(Comment comment) {
        comments.add(comment);
        notifyObservers("comment");
    }

    // Getters and Setters ------------

    public String getTitle() {
        return this.title;
    }

    public User getAssignee() {
        return this.assignee;
    }

    public TaskStatus getStatus() {
        return this.currentState.getTaskStatus();
    }


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
        public TaskBuilder priority(TaskPriority priority) { this.priority = priority; return this;}
        public TaskBuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this;}
        public TaskBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this;}

        // 5. build method to call private ctor of main class
        public Task build() {
            return new Task(this);
        }
    }

}
