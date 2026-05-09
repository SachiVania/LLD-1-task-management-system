import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;
import models.User;
import observer.ActivityLogger;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TaskManagementSystem {

    private static TaskManagementSystem instance;
    private Map<String, User> users;
    private Map<String, Task> tasks;

    public TaskManagementSystem() {
        users = new ConcurrentHashMap<>();
        tasks = new ConcurrentHashMap<>();
    }

    // keeping it synchronized so that even if getInstance() is called
    // multiple times, only one object is created.
    public static synchronized TaskManagementSystem getInstance() {
        if (instance == null) {
            instance = new TaskManagementSystem();
        }
        return instance;
    }

    public User createUser(String name, String email) {
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    public Task createTask(
            String title,
            String description,
            LocalDate dueDate,
            TaskPriority priority,
            String createdById
            ) {
        User user = users.get(createdById);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Task task = new Task.TaskBuilder(title)
                .description(description)
                .dueDate(dueDate)
                .priority(priority)
                .createdBy(user)
                .build();

        task.addObserver(new ActivityLogger());
        return task;
    }

    public List<Task> listTasksByUser(String userId) {
        User user = users.get(userId);
        return tasks.values().stream()
                .filter(task -> user.equals(task.getAssignee()))
                .toList();  // java 16+ ; returns unmodifiable list
    }

    public List<Task> listTasksByStatus(TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList()); // java 8 ; returns modifiable list
    }
}
