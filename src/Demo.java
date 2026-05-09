import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;
import models.User;

import java.time.LocalDate;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        TaskManagementSystem instance = TaskManagementSystem.getInstance();

        // create users
        User user1 = instance.createUser("John Doe", "john@example.com");
        User user2 = instance.createUser("Jane Smith", "jane@example.com");

        // create tasks
        Task task1 = instance.createTask("Enhancement Task", "Launch New Feature",
                LocalDate.now().plusDays(2), TaskPriority.LOW, user1.getId());
        Task task2 = instance.createTask("Bug Fix Task", "Fix API Bug",
                LocalDate.now().plusDays(3), TaskPriority.HIGH, user2.getId());

        // assign task
        task1.setAssignee(user2);

        // Filter tasks by status
        List<Task> filteredTasks = instance.listTasksByStatus(TaskStatus.TODO);
        System.out.println("\nTODO Tasks:");
        for (Task task : filteredTasks) {
            System.out.println(task.getTitle());
        }

        // Get tasks assigned to a user
        List<Task> userTaskList = instance.listTasksByUser(user1.getId());
        System.out.println("\nTask for " + user1.getName() + ":");
        for (Task task : userTaskList) {
            System.out.println(task.getTitle());
        }

        // update task status
        task1.completeTask();

    }
}
