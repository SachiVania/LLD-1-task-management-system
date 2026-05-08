package state;

import enums.TaskStatus;
import models.Task;

public class TodoState implements TaskState {
    @Override
    public void startProgress(Task task) {
        task.setState(new InProgressState());
    }

    @Override
    public void completeTask(Task task) {
        System.out.println("cannot complete a task that is not in progress");
    }

    @Override
    public void reopenTask(Task task) {
        System.out.println("task is already in To-Do state");
    }

    @Override
    public TaskStatus getTaskStatus() {
        return TaskStatus.TODO;
    }
}
