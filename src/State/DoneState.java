package State;

import enums.TaskStatus;
import models.Task;

public class DoneState implements TaskState {

    @Override
    public void startProgress(Task task) {
        System.out.println("Cannot start a completed task. Reopen it first");
    }

    @Override
    public void completeTask(Task task) {
        System.out.println("Task is already done");
    }

    @Override
    public void reopenTask(Task task) {
        task.setState(new TodoState());
    }

    @Override
    public TaskStatus getTaskStatus() {
        return TaskStatus.DONE;
    }
}
