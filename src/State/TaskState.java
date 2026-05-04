package State;

import enums.TaskStatus;
import models.Task;

public interface TaskState {

    // handle in progress state
    void startProgress(Task task);

    // handle done state
    void completeTask(Task task);

    // handle to-do state
    void reopenTask(Task task);

    TaskStatus getTaskStatus();
}
